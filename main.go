package main

import (
	"context"
	"palclip/pkg/clipm"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
	"fyne.io/fyne/v2/driver/desktop"
)

func main() {
	myApp := app.New()
	myApp.SetIcon(nil)

	// Create window with no title for borderless effect
	drv := myApp.Driver()
		if drv, ok := drv.(desktop.Driver); ok {
			myWindow := drv.CreateSplashWindow()
			myWindow.Resize(fyne.Size{Width: 500, Height: 400})
			myWindow.SetFixedSize(false)
			myWindow.CenterOnScreen()

			myWindow.SetPadded(true)



			appInstance := NewApp()

			content := appInstance.setupUI(myWindow)
			myWindow.SetContent(content)

			ctx := context.Background()
			go clipm.Record(ctx)

			go appInstance.RegisterHotKey(myWindow)

			myWindow.ShowAndRun()
	}
}
