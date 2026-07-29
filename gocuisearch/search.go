package gocuisearch

import (
	"context"
	"errors"
	"log/slog"
	"strings"
	"sync"
	"time"

	"github.com/awesome-gocui/gocui"
	"github.com/sahilm/fuzzy"
)

const exitCodeExecute = 111

func CuiSearch(records []SearchApp) (string, int, int) {

	g, err := gocui.NewGui(gocui.OutputNormal, false)
	if err != nil {
		slog.Error("Failed to launch TUI", err)
	}
	defer g.Close()

	g.Cursor = true
	g.Highlight = true
	st := state{
		gui:          g,
		cliRecords:   records,
		initialQuery: "",
	}

	layout := manager{
		s: &st,
	}
	g.SetManager(layout)

	errMsg := "Failed to set keybindings"
	if err := g.SetKeybinding("", gocui.KeyTab, gocui.ModNone, layout.Next); err != nil {
		slog.Error(errMsg, err)
	}
	if err := g.SetKeybinding("", gocui.KeyArrowDown, gocui.ModNone, layout.Next); err != nil {
		slog.Error(errMsg, err)
	}
	if err := g.SetKeybinding("", gocui.KeyCtrlN, gocui.ModNone, layout.Next); err != nil {
		slog.Error(errMsg, err)
	}
	if err := g.SetKeybinding("", gocui.KeyArrowUp, gocui.ModNone, layout.Prev); err != nil {
		slog.Error(errMsg, err)
	}
	if err := g.SetKeybinding("", gocui.KeyCtrlP, gocui.ModNone, layout.Prev); err != nil {
		slog.Error(errMsg, err)
	}
	if err := g.SetKeybinding("", gocui.KeyArrowRight, gocui.ModNone, layout.SelectPaste); err != nil {
		slog.Error(errMsg, err)
	}
	if err := g.SetKeybinding("", gocui.KeyEnter, gocui.ModNone, layout.SelectExecute); err != nil {
		slog.Error(errMsg, err)
	}
	if err := g.SetKeybinding("", gocui.KeyCtrlG, gocui.ModNone, layout.AbortPaste); err != nil {
		slog.Error(errMsg, err)
	}
	if err := g.SetKeybinding("", gocui.KeyCtrlC, gocui.ModNone, quit); err != nil {
		slog.Error(errMsg, err)
	}
	if err := g.SetKeybinding("", gocui.KeyCtrlD, gocui.ModNone, quit); err != nil {
		slog.Error(errMsg, err)
	}

	if err := g.SetKeybinding("", gocui.KeyCtrlR, gocui.ModNone, layout.SwitchModes); err != nil {
		slog.Error(errMsg, err)
	}

	ctx := context.Background()
	layout.updateRawData(ctx, "")
	err = g.MainLoop()
	if err != nil && !errors.Is(err, gocui.ErrQuit) {
		slog.Error("Main application loop finished with error", err)
	}
	return layout.s.output, layout.s.idx, layout.s.exitCode
}

type state struct {
	gui *gocui.Gui

	cliRecords []SearchApp

	lock sync.Mutex

	cancelUpdate        *context.CancelFunc
	data                []Item
	rawData             []RawItem
	highlightedItem     int
	displayedItemsCount int

	initialQuery string

	output   string
	idx      int
	exitCode int
}

type manager struct {
	sessionID       string
	host            string
	pwd             string
	gitOriginRemote string

	s *state
}

func (m manager) SelectExecute(g *gocui.Gui, v *gocui.View) error {
	m.s.lock.Lock()
	defer m.s.lock.Unlock()
	if m.s.highlightedItem < len(m.s.rawData) {
		m.s.output = m.s.rawData[m.s.highlightedItem].ContentOut
		m.s.idx = m.s.rawData[m.s.highlightedItem].Idx
		m.s.exitCode = exitCodeExecute
		return gocui.ErrQuit
	}

	return nil
}

func (m manager) SelectPaste(g *gocui.Gui, v *gocui.View) error {
	m.s.lock.Lock()
	defer m.s.lock.Unlock()
	if m.s.highlightedItem < len(m.s.rawData) {
		m.s.output = m.s.rawData[m.s.highlightedItem].ContentOut
		m.s.idx = m.s.rawData[m.s.highlightedItem].Idx
		m.s.exitCode = 0
		return gocui.ErrQuit
	}
	return nil
}

func (m manager) AbortPaste(g *gocui.Gui, v *gocui.View) error {
	m.s.lock.Lock()
	defer m.s.lock.Unlock()
	if m.s.highlightedItem < len(m.s.data) {
		m.s.output = v.Buffer()
		m.s.exitCode = 0
		return gocui.ErrQuit
	}
	return nil
}

func (m manager) updateRawData(ctx context.Context, input string) {
	timeStart := time.Now()
	slog.Debug("Starting RAW data update ...",
		"recordCount", len(m.s.cliRecords),
		"itemCount", len(m.s.data),
	)
	query := GetRawTermsFromString(input, true)
	var data RawItems
	itemSet := make(map[string]bool)
	for idx, rec := range m.s.cliRecords {
		if shouldCancel(ctx) {
			timeEnd := time.Now()
			slog.Debug("Update got canceled",
				"duration", timeEnd.Sub(timeStart),
			)
			return
		}
		itm, err := NewRawItemFromRecordForQuery(idx, rec, query, true)
		if err != nil {
			continue
		}
		if itemSet[itm.Key] {
			continue
		}
		itemSet[itm.Key] = true
		data = append(data, itm)
	}
	slog.Debug("Got new RAW items from records for query, sorting items ...",
		"itemCount", len(data),
	)
	matches := fuzzy.FindFrom(input, data)
	m.s.lock.Lock()
	defer m.s.lock.Unlock()

	m.s.rawData = nil
	if matches == nil {
		m.s.rawData = data
	} else {
		for _, match := range matches {
			if len(m.s.rawData) > 420 {
				break
			}
			m.s.rawData = append(m.s.rawData, data[match.Index])
		}
	}

	m.s.highlightedItem = 0
	timeEnd := time.Now()
	slog.Debug("Done with RAW data update",
		"duration", timeEnd.Sub(timeStart),
		"recordCount", len(m.s.cliRecords),
		"itemCount", len(m.s.data),
	)
}

func shouldCancel(ctx context.Context) bool {
	select {
	case <-ctx.Done():
		return true
	default:
		return false
	}
}

func (m manager) getCtxAndCancel() context.Context {
	m.s.lock.Lock()
	defer m.s.lock.Unlock()
	if m.s.cancelUpdate != nil {
		(*m.s.cancelUpdate)()
	}
	ctx, cancel := context.WithCancel(context.Background())
	m.s.cancelUpdate = &cancel
	return ctx
}

func (m manager) update(input string) {
	ctx := m.getCtxAndCancel()
	m.updateRawData(ctx, input)
	m.flush()
}

func (m manager) flush() {
	f := func(_ *gocui.Gui) error { return nil }
	m.s.gui.Update(f)
}

func (m manager) Edit(v *gocui.View, key gocui.Key, ch rune, mod gocui.Modifier) {
	gocui.DefaultEditor.Edit(v, key, ch, mod)
	go m.update(v.Buffer())
}

func (m manager) Next(g *gocui.Gui, v *gocui.View) error {
	m.s.lock.Lock()
	defer m.s.lock.Unlock()
	if m.s.highlightedItem < m.s.displayedItemsCount-1 {
		m.s.highlightedItem++
	}
	return nil
}

func (m manager) Prev(g *gocui.Gui, v *gocui.View) error {
	m.s.lock.Lock()
	defer m.s.lock.Unlock()
	if m.s.highlightedItem > 0 {
		m.s.highlightedItem--
	}
	return nil
}

func (m manager) SwitchModes(g *gocui.Gui, v *gocui.View) error {
	m.s.lock.Lock()
	m.s.lock.Unlock()

	go m.update(v.Buffer())
	return nil
}

func (m manager) Layout(g *gocui.Gui) error {
	var b byte
	maxX, maxY := g.Size()

	v, err := g.SetView("input", 0, 0, maxX-1, 2, b)
	if err != nil && !errors.Is(err, gocui.ErrUnknownView) {
		slog.Error("Failed to set view 'input'", err)
	}

	v.Editable = true
	v.Editor = m
	v.Title = " SEARCH QUERY "
	g.SetCurrentView("input")

	m.s.lock.Lock()
	defer m.s.lock.Unlock()
	if len(m.s.initialQuery) > 0 {
		v.WriteString(m.s.initialQuery)
		v.SetCursor(len(m.s.initialQuery), 0)
		m.s.initialQuery = ""
	}

	v, err = g.SetView("body", 0, 2, maxX-1, maxY, b)
	if err != nil && !errors.Is(err, gocui.ErrUnknownView) {
		slog.Error("Failed to set view 'body'", err)
	}
	v.Frame = false
	v.Autoscroll = false
	v.Clear()
	v.Rewind()

	return m.rawMode(g, v)
}

func quit(g *gocui.Gui, v *gocui.View) error {
	return gocui.ErrQuit
}

const smallTerminalThresholdWidth = 110

func (m manager) rawMode(g *gocui.Gui, v *gocui.View) error {
	maxX, maxY := g.Size()
	topBoxSize := 3
	m.s.displayedItemsCount = maxY - topBoxSize

	for i, itm := range m.s.rawData {
		if i == maxY {
			break
		}
		displayStr := itm.ContentWithColor
		if m.s.highlightedItem == i {
			displayStr = DoHighlightString(displayStr, maxX*2)
		}
		if strings.Contains(displayStr, "\n") {
			displayStr = strings.ReplaceAll(displayStr, "\n", "#")
		}
		v.WriteString(displayStr + "\n")
	}
	slog.Debug("Done drawing page in RAW mode",
		"itemCount", len(m.s.data),
		"highlightedItemIndex", m.s.highlightedItem,
	)
	return nil
}
