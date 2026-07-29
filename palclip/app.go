package main

import (
	"encoding/json"
	"fmt"
	"palclip/pkg/clipm"
	"palclip/pkg/config"
	"strings"
	"time"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/widget"
	"golang.design/x/clipboard"
	"golang.design/x/hotkey"
)

type App struct {
	window       fyne.Window
	clipList     *widget.List
	clipData     []clipm.ClipInfo
	filteredData []clipm.ClipInfo
	refreshCh    chan bool
	currentPopup *widget.PopUp
	isVisible    bool
}

func NewApp() *App {
	return &App{
		clipData:     make([]clipm.ClipInfo, 0),
		filteredData: make([]clipm.ClipInfo, 0),
		refreshCh:    make(chan bool, 1),
		currentPopup: nil,
		isVisible:    true,
	}
}

func (a *App) setupUI(window fyne.Window) fyne.CanvasObject {
	a.window = window
	a.clipList = widget.NewList(
		func() int {
			return len(a.filteredData)
		},
		func() fyne.CanvasObject {
			label := widget.NewLabel("Template text here...")
			label.Wrapping = fyne.TextWrapWord

			popupBtn := widget.NewButton("📄", nil)
			secretBtn := widget.NewButton("👁", nil)
			buttonContainer := container.NewHBox(popupBtn, secretBtn)
			buttonContainer.Resize(fyne.NewSize(100, 28))

			return container.NewBorder(
				nil, nil, nil, buttonContainer,
				label,
			)
		},
		func(id widget.ListItemID, item fyne.CanvasObject) {
			if id >= len(a.filteredData) {
				return
			}

			clip := a.filteredData[id]
			borderContainer := item.(*fyne.Container)

			label := borderContainer.Objects[0].(*widget.Label)
			content := clip.Content
			if clip.IsSecret {
				content = "*** HIDDEN ***"
			} else if len(content) > 40 {
				content = content[:40] + "..."
			}

			content = strings.ReplaceAll(content, "\n", " ")
			content = strings.ReplaceAll(content, "\r", " ")
			content = strings.ReplaceAll(content, "\t", " ")
			content = strings.TrimLeft(content, " ")
			label.SetText(content)
			var buttonContainer *fyne.Container
			for _, obj := range borderContainer.Objects {
				if obj != label {
					buttonContainer = obj.(*fyne.Container)
					break
				}
			}

			popupBtn := buttonContainer.Objects[0].(*widget.Button)
			secretBtn := buttonContainer.Objects[1].(*widget.Button)
			currentHash := clip.Hash
			currentContent := clip.Content
			currentIsSecret := clip.IsSecret

			if clip.IsSecret {
				secretBtn.SetText("🔒")
			} else {
				secretBtn.SetText("👁")
			}

			popupBtn.OnTapped = func() {
				a.showContentPopup(currentContent, currentIsSecret)
			}

			secretBtn.OnTapped = func() {
				a.MarkSecret(currentHash)
				a.refreshClipData()
			}
		},
	)

	// Single tap to copy
	a.clipList.OnSelected = func(id widget.ListItemID) {
		if id < len(a.filteredData) {
			clip := a.filteredData[id]
			if !clip.IsSecret {
				a.CopyItemContent(clip.Content)
				a.hideWindow()
			}
		}
		a.clipList.UnselectAll()
	}

	menuBar := a.createMenuBar()
	content := container.NewBorder(
		menuBar,
		nil,
		nil,
		nil,
		a.clipList,
	)

	go a.refreshClipDataDoAndWait()
	go a.refreshListener()
	clipm.SetRefreshCallback(func() {
		select {
		case a.refreshCh <- true:
		default:
		}
	})

	return content
}

func (a *App) createMenuBar() *fyne.Container {
	searchEntry := widget.NewEntry()
	searchEntry.SetPlaceHolder("Search clipboard...")
	searchEntry.OnChanged = func(text string) {
		a.filterClipData(text)
	}

	menuButton := widget.NewButton("...", nil)
	menuButton.Resize(fyne.NewSize(40, 32))
	menuButton.Importance = widget.MediumImportance
	clearItem := fyne.NewMenuItem("Clear All", func() {
		a.ClearAll()
	})

	settingsItem := fyne.NewMenuItem("Settings", func() {
		fmt.Println("Settings clicked")
	})

	quitItem := fyne.NewMenuItem("Quit", func() {
		a.window.Close()
	})

	menu := fyne.NewMenu("", clearItem, settingsItem, quitItem)

	menuButton.OnTapped = func() {
		pos := fyne.NewPos(
			menuButton.Position().X,
			menuButton.Position().Y+menuButton.Size().Height,
		)
		widget.ShowPopUpMenuAtPosition(menu, a.window.Canvas(), pos)
	}
	return container.NewBorder(
		nil, nil, nil, menuButton,
		searchEntry,
	)
}

func (a *App) refreshListener() {
	for range a.refreshCh {
		a.refreshClipDataDoAndWait()
	}
}

func (a *App) GetClipData(name string) string {
	clipDb := config.GetInstance()

	clipm := &clipm.ClipM{
		DB: clipDb.DB,
	}

	clipList, err := clipm.ReadAll()
	if err != nil {
		fmt.Println("ReadAll", err)
		return "[]"
	}
	clipm.SortByTimestamp(*clipList)
	jsonClipList, err := json.Marshal(clipList)
	if err != nil {
		fmt.Println("Marshal", err)
	}
	return string(jsonClipList)
}

func (a *App) filterClipData(searchText string) {
	if searchText == "" {
		a.filteredData = a.clipData
	} else {
		filtered := make([]clipm.ClipInfo, 0)
		for _, clip := range a.clipData {
			if strings.Contains(strings.ToLower(clip.Content), strings.ToLower(searchText)) {
				filtered = append(filtered, clip)
			}
		}
		a.filteredData = filtered
	}

	fyne.DoAndWait(func(){
		a.clipList.Refresh()
	})
}

func (a *App) refreshClipDataDoAndWait() {
	fyne.DoAndWait(func(){
		a.refreshClipData()
	})
}

func (a *App) refreshClipData() {
	clipDb := config.GetInstance()

	clipm := &clipm.ClipM{
		DB: clipDb.DB,
	}

	clipList, err := clipm.ReadAll()
	if err != nil {
		fmt.Println("ReadAll", err)
		return
	}
	clipm.SortByTimestamp(*clipList)

	a.clipData = *clipList

	if len(a.filteredData) != len(a.clipData) {
		a.filteredData = *clipList
	} else {
		a.filteredData = *clipList
	}

	a.clipList.Refresh()
}

func (a *App) CopyItemContent(content string) {
	fmt.Println("Copied the content...")
	clipboard.Write(clipboard.FmtText, []byte(content))
}

func (a *App) MarkSecret(hash string) {
	clipDb := config.GetInstance()

	clipm := &clipm.ClipM{
		DB: clipDb.DB,
	}
	clipm.MarkSecret(hash)

	go func() {
		select {
		case a.refreshCh <- true:
		default:
		}
	}()
}

func (a *App) ClearAll() {
	clipDb := config.GetInstance()
	clipm := &clipm.ClipM{
		DB: clipDb.DB,
	}
	clipm.DeleteBucket()
	a.refreshClipData()
}

func (a *App) RegisterHotKey(window fyne.Window) {
	go func() {
		defer func() {
			if r := recover(); r != nil {
				fmt.Printf("Hotkey registration failed: %v\n", r)
			}
		}()
		registerHotkey(a, window)
	}()
}

func registerHotkey(a *App, window fyne.Window) {
	hk := hotkey.New([]hotkey.Modifier{hotkey.ModCtrl, hotkey.ModShift}, hotkey.KeySpace)
	err := hk.Register()
	if err != nil {
		fmt.Printf("Failed to register hotkey: %v\n", err)
		return
	}

	fmt.Printf("hotkey: %v is registered\n", hk)

	for {
		select {
		case <-hk.Keydown():
			fmt.Printf("hotkey: %v is down\n", hk)
		case <-hk.Keyup():
			fmt.Printf("hotkey: %v is up\n", hk)

			fyne.DoAndWait(func(){
				if a.isVisible {
					a.hideWindow()
				} else {
					a.showWindow()
				}
			})

			// Refresh clip data when hotkey is pressed
			select {
			case a.refreshCh <- true:
			default:
			}
		case <-time.After(time.Second * 30):
			continue
		}
	}
}

func (a *App) showContentPopup(content string, isSecret bool) {
	displayContent := content
	if isSecret {
		displayContent = "*** HIDDEN ***"
	}

	mainWindowSize := a.window.Canvas().Size()
	popupWidth := mainWindowSize.Width - 40
	popupHeight := mainWindowSize.Height - 80
	textEntry := widget.NewEntry()
	textEntry.SetText(displayContent)
	textEntry.MultiLine = true
	textEntry.Wrapping = fyne.TextWrapWord
	textEntry.Disable() // Make it read-only

	scroll := container.NewScroll(textEntry)


	closeBtn := widget.NewButton("Close", nil)

	mainContainer := container.NewBorder(
		nil,
		closeBtn,
		nil, nil,
		scroll,
	)

	mainContainer.Resize(fyne.NewSize(popupWidth, popupHeight))

	var popup *widget.PopUp
	popup = widget.NewPopUp(mainContainer, a.window.Canvas())

	closeBtn.OnTapped = func() {
		popup.Hide()
	}

	popup.Resize(fyne.NewSize(popupWidth, popupHeight))

	windowSize := a.window.Canvas().Size()
	x := (windowSize.Width - popupWidth) / 2
	y := (windowSize.Height - popupHeight) / 2
	popup.Move(fyne.NewPos(x, y))

	popup.Show()
}

func (a *App) showWindow() {
	a.isVisible = true
	a.window.Show()
	a.window.RequestFocus()
}

func (a *App) hideWindow() {
	a.isVisible = false
	a.window.Hide()
}
