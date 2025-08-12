package main

import (
	"context"
	"palclip/pkg/clipm"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
)

func main() {
	// Create Fyne app
	myApp := app.New()
	myApp.SetIcon(nil) // You can set an icon here if you have one

	// Create window
	myWindow := myApp.NewWindow("PalClip")
	myWindow.Resize(fyne.Size{Width: 500, Height: 400})

	// Create app instance
	appInstance := NewApp()

	// Set up the UI
	content := appInstance.setupUI(myWindow)
	myWindow.SetContent(content)

	// Start clipboard monitoring in background
	ctx := context.Background()
	go clipm.Record(ctx)

	// Register global hotkey
	go appInstance.RegisterHotKey(myWindow)

	// Show window and run
	myWindow.ShowAndRun()
}
