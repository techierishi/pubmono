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
	"fyne.io/fyne/v2/layout"
	"fyne.io/fyne/v2/widget"
	"golang.design/x/clipboard"
	"golang.design/x/hotkey"
)

// App struct
type App struct {
	window       fyne.Window
	clipList     *widget.List
	clipData     []clipm.ClipInfo
	filteredData []clipm.ClipInfo
	refreshCh    chan bool
}

// NewApp creates a new App application struct
func NewApp() *App {
	return &App{
		clipData:     make([]clipm.ClipInfo, 0),
		filteredData: make([]clipm.ClipInfo, 0),
		refreshCh:    make(chan bool, 1),
	}
}

// setupUI creates and returns the main UI content
func (a *App) setupUI(window fyne.Window) fyne.CanvasObject {
	a.window = window

	// Create the clip list widget
	a.clipList = widget.NewList(
		func() int {
			return len(a.filteredData)
		},
		func() fyne.CanvasObject {
			// Create label with fixed width
			label := widget.NewLabel("Template text here...")
			label.Wrapping = fyne.TextWrapWord

			// Create buttons with consistent sizing
			copyBtn := widget.NewButton("Copy", nil)
			secretBtn := widget.NewButton("Secret", nil)

			// Use grid layout for consistent sizing
			buttonGrid := container.New(layout.NewGridLayout(2), copyBtn, secretBtn)
			buttonGrid.Resize(fyne.NewSize(160, 32))

			// Use border layout for proper alignment
			return container.NewBorder(
				nil, nil, nil, buttonGrid,
				label,
			)
		},
		func(id widget.ListItemID, item fyne.CanvasObject) {
			if id >= len(a.filteredData) {
				return
			}

			clip := a.filteredData[id]
			borderContainer := item.(*fyne.Container)

			// Get the label (center object)
			label := borderContainer.Objects[0].(*widget.Label)
			content := clip.Content

			// Handle secret items
			if clip.IsSecret {
				content = "*** HIDDEN ***"
			} else if len(content) > 40 {
				content = content[:40] + "..."
			}

			// Replace newlines with spaces for display
			content = strings.ReplaceAll(content, "\n", " ")
			content = strings.ReplaceAll(content, "\r", " ")
			content = strings.ReplaceAll(content, "\t", " ")
			content = strings.TrimLeft(content, " ")
			label.SetText(content)

			// Get the button container (right object in border layout)
			var buttonGrid *fyne.Container
			for _, obj := range borderContainer.Objects {
				if obj != label {
					buttonGrid = obj.(*fyne.Container)
					break
				}
			}

			// Update copy button
			copyBtn := buttonGrid.Objects[0].(*widget.Button)
			currentClip := clip // Capture for closure
			copyBtn.OnTapped = func() {
				a.CopyItemContent(currentClip.Content)
			}

			// Update mark secret button
			secretBtn := buttonGrid.Objects[1].(*widget.Button)
			currentHash := clip.Hash // Capture for closure
			if clip.IsSecret {
				secretBtn.SetText("Shown")
			} else {
				secretBtn.SetText("Secret")
			}
			secretBtn.OnTapped = func() {
				a.MarkSecret(currentHash)
				a.refreshClipData()
			}
		},
	)

	// Create menu bar
	menuBar := a.createMenuBar()

	// Create main container
	content := container.NewBorder(
		menuBar,    // top
		nil,        // bottom
		nil,        // left
		nil,        // right
		a.clipList, // center
	)

	// Load initial data
	go a.refreshClipData()

	// Start refresh listener
	go a.refreshListener()

	// Setup clipboard event callback for auto-refresh
	clipm.SetRefreshCallback(func() {
		// Trigger refresh when clipboard changes
		select {
		case a.refreshCh <- true:
		default:
			// Channel full, skip refresh
		}
	})

	return content
}

// createMenuBar creates the application menu bar with search and three-dot menu
func (a *App) createMenuBar() *fyne.Container {
	// Create search input
	searchEntry := widget.NewEntry()
	searchEntry.SetPlaceHolder("Search clipboard...")
	searchEntry.OnChanged = func(text string) {
		a.filterClipData(text)
	}

	// Create three-dot menu with better styling (using safer character)
	menuButton := widget.NewButton("...", nil)
	menuButton.Resize(fyne.NewSize(40, 32))
	menuButton.Importance = widget.MediumImportance

	// Create popup menu items
	clearItem := fyne.NewMenuItem("Clear All", func() {
		a.ClearAll()
	})

	settingsItem := fyne.NewMenuItem("Settings", func() {
		// TODO: Implement settings dialog
		fmt.Println("Settings clicked")
	})

	quitItem := fyne.NewMenuItem("Quit", func() {
		a.window.Close()
	})

	menu := fyne.NewMenu("", clearItem, settingsItem, quitItem)

	menuButton.OnTapped = func() {
		// Position menu below the button
		pos := fyne.NewPos(
			menuButton.Position().X,
			menuButton.Position().Y+menuButton.Size().Height,
		)
		widget.ShowPopUpMenuAtPosition(menu, a.window.Canvas(), pos)
	}

	// Use border layout for proper 80/20 distribution
	return container.NewBorder(
		nil, nil, nil, menuButton,
		searchEntry,
	)
}

// refreshListener listens for refresh events
func (a *App) refreshListener() {
	for range a.refreshCh {
		a.refreshClipData()
	}
}

// GetClipData returns clipboard data as JSON string (keeping for compatibility)
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

// filterClipData filters clipboard data based on search text
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
	a.clipList.Refresh()
}

// refreshClipData refreshes the clipboard data in the UI
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

	// Maintain current search filter if any
	if len(a.filteredData) != len(a.clipData) {
		// Get current search text from the search entry if possible
		// For now, reset to show all data
		a.filteredData = *clipList
	} else {
		a.filteredData = *clipList
	}

	a.clipList.Refresh()
}

// CopyItemContent copies content to clipboard
func (a *App) CopyItemContent(content string) {
	fmt.Println("Copied the content...")
	clipboard.Write(clipboard.FmtText, []byte(content))
}

// MarkSecret marks an item as secret
func (a *App) MarkSecret(hash string) {
	clipDb := config.GetInstance()

	clipm := &clipm.ClipM{
		DB: clipDb.DB,
	}
	clipm.MarkSecret(hash)
}

// ClearAll clears all clipboard data
func (a *App) ClearAll() {
	clipDb := config.GetInstance()
	clipm := &clipm.ClipM{
		DB: clipDb.DB,
	}
	clipm.DeleteBucket()
	a.refreshClipData()
}

// RegisterHotKey registers global hotkey
func (a *App) RegisterHotKey(window fyne.Window) {
	go func() {
		defer func() {
			if r := recover(); r != nil {
				fmt.Printf("Hotkey registration failed: %v\n", r)
			}
		}()
		// registerHotkey(a, window)
	}()
}

func registerHotkey(a *App, window fyne.Window) {
	// the actual shortcut keybind - Ctrl + Shift + Space
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

			// Show/hide window on hotkey
			if window.Content().Visible() {
				window.Hide()
			} else {
				window.Show()
				window.RequestFocus()
			}

			// Refresh clip data when hotkey is pressed
			select {
			case a.refreshCh <- true:
			default:
			}
		case <-time.After(time.Second * 30):
			// Periodic check to ensure hotkey is still registered
			continue
		}
	}
}
