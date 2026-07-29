// Copyright 2014 The gocui Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package main

import (
	"errors"
	"fmt"
	"log"
	"strings"

	"github.com/awesome-gocui/gocui"
)

const delta = 0.2

type HelpWidget struct {
	name string
	x, y int
	w, h int
	body string
}

func NewHelpWidget(name string, x, y int, body string) *HelpWidget {
	lines := strings.Split(body, "\n")

	w := 0
	for _, l := range lines {
		if len(l) > w {
			w = len(l)
		}
	}
	h := len(lines) + 1
	w = w + 1

	return &HelpWidget{name: name, x: x, y: y, w: w, h: h, body: body}
}

func (w *HelpWidget) Layout(g *gocui.Gui) error {
	v, err := g.SetView(w.name, w.x, w.y, w.x+w.w, w.y+w.h, 0)
	if err != nil {
		if !errors.Is(err, gocui.ErrUnknownView) {
			return err
		}
		fmt.Fprint(v, w.body)
	}
	return nil
}

type InputWidget struct {
	name  string
	x, y  int
	w     int
	h     int
	label string
	val   float64
}

func NewInputWidget(name string, x, y, w, h int, label string) *InputWidget {
	return &InputWidget{name: name, x: x, y: y, w: w, h: h, label: label}
}

func (w *InputWidget) SetVal(val float64) error {
	if val < 0 || val > 1 {
		return errors.New("invalid value")
	}
	w.val = val
	return nil
}

func (w *InputWidget) Val() float64 {
	return w.val
}

func (w *InputWidget) Layout(g *gocui.Gui) error {
	v, err := g.SetView(w.name, w.x, w.y, w.x+w.w, w.y+w.h, 0)
	if err != nil && !errors.Is(err, gocui.ErrUnknownView) {
		return err
	}
	v.Title = w.label
	v.Editable = true
	v.Wrap = true

	return nil
}

type ButtonWidget struct {
	name    string
	x, y    int
	w       int
	h       int
	label   string
	handler func(g *gocui.Gui, v *gocui.View) error
}

func NewButtonWidget(name string, x, y int, label string, handler func(g *gocui.Gui, v *gocui.View) error) *ButtonWidget {
	return &ButtonWidget{name: name, x: x, y: y, w: len(label) + 1, label: label, handler: handler}
}

func (w *ButtonWidget) Layout(g *gocui.Gui) error {
	v, err := g.SetView(w.name, w.x, w.y, w.x+w.w, w.y+2, 0)
	if err != nil {
		if !errors.Is(err, gocui.ErrUnknownView) {
			return err
		}
		if _, err := g.SetCurrentView(w.name); err != nil {
			return err
		}
		if err := g.SetKeybinding(w.name, gocui.KeyEnter, gocui.ModNone, w.handler); err != nil {
			return err
		}
		fmt.Fprint(v, w.label)
	}
	return nil
}

func main() {
	g, err := gocui.NewGui(gocui.OutputNormal, true)
	if err != nil {
		log.Panicln(err)
	}
	defer g.Close()

	g.Highlight = true
	g.SelFrameColor = gocui.ColorRed

	help := NewHelpWidget("help", 1, 1, helpText)
	command := NewInputWidget("command", 1, 7, 100, 6, "Command")
	descr := NewInputWidget("description", 1, 14, 100, 4, "Description")
	butsave := NewButtonWidget("butsave", 1, 19, "SAVE", statusDown(command))
	butcancel := NewButtonWidget("butcancel", 10, 19, "CANCEL", statusUp(command))
	g.SetManager(help, command, descr, butsave, butcancel)
	g.SetCurrentView("command")

	if err := g.SetKeybinding("", gocui.KeyCtrlC, gocui.ModNone, quit); err != nil {
		log.Panicln(err)
	}
	if err := g.SetKeybinding("", gocui.KeyTab, gocui.ModNone, toggleButton); err != nil {
		log.Panicln(err)
	}

	if err := g.MainLoop(); err != nil && !errors.Is(err, gocui.ErrQuit) {
		log.Panicln(err)
	}
}

func quit(g *gocui.Gui, v *gocui.View) error {
	return gocui.ErrQuit
}

func toggleButton(g *gocui.Gui, v *gocui.View) error {
	nextview := "description"
	if v != nil && v.Name() == "command" {
		nextview = "description"
	} else if v != nil && v.Name() == "description" {
		nextview = "butsave"
	} else if v != nil && v.Name() == "butsave" {
		nextview = "butcancel"
	} else if v != nil && v.Name() == "butcancel" {
		nextview = "command"
	}
	_, err := g.SetCurrentView(nextview)
	return err
}

func statusUp(status *InputWidget) func(g *gocui.Gui, v *gocui.View) error {
	return func(g *gocui.Gui, v *gocui.View) error {
		return statusSet(status, delta)
	}
}

func statusDown(status *InputWidget) func(g *gocui.Gui, v *gocui.View) error {
	return func(g *gocui.Gui, v *gocui.View) error {
		return statusSet(status, -delta)
	}
}

func statusSet(sw *InputWidget, inc float64) error {
	val := sw.Val() + inc
	if val < 0 || val > 1 {
		return nil
	}
	return sw.SetVal(val)
}

const helpText = `KEYBINDINGS
Tab: Move between form items
Enter: Save or Cancel
^C: Exit`
