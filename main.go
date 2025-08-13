package main

import (
	"context"
	"palclip/pkg/clipm"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
	"fyne.io/fyne/v2/driver/desktop"
)

func main() {
	// Create Fyne app
	myApp := app.New()
	myApp.SetIcon(nil) // You can set an icon here if you have one

	// Create window with no title for borderless effect
	drv := myApp.Driver()
		if drv, ok := drv.(desktop.Driver); ok {
			myWindow := drv.CreateSplashWindow()
			myWindow.Resize(fyne.Size{Width: 500, Height: 400})
			myWindow.SetFixedSize(false)
			myWindow.CenterOnScreen()

			// Remove padding to make window content fill completely
			myWindow.SetPadded(true)

			// Disable close window button by intercepting close action
			myWindow.SetCloseIntercept(func() {

			})

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
}
