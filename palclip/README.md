# PalClip

## About

Simple cross-platform clipboard manager built with Go and Fyne. 

Open the clipboard from anywhere by pressing `ctrl+shift+space`. It keeps a list of all copies and allows you to mark any text as secret to mask it.

## Features

- Cross-platform support (Linux, Windows, macOS)
- Global hotkey support (Ctrl+Shift+Space)
- Native GUI using Fyne framework
- Clipboard history with timestamps
- Mark sensitive items as secret
- Clear all clipboard history
- Persistent storage using BoltDB

## Migration from Wails

This project has been migrated from Wails to Fyne for better performance and native desktop integration. The core functionality remains the same, but the UI is now built with native Go widgets instead of web technologies.

## Prerequisites

### Linux
```bash
sudo apt-get install libgl1-mesa-dev xorg-dev
```

### macOS
No additional dependencies required.

### Windows
No additional dependencies required.

## Development

### Build
```bash
go build
```

### Run
```bash
./palclip
```

## Usage

1. Run the application
2. Use `Ctrl+Shift+Space` to show/hide the clipboard manager window
3. Click "Copy" to copy an item back to clipboard
4. Click "Mark Secret" to hide sensitive content
5. Use "Clear All" to remove all clipboard history
6. Click "Quit" to exit the application

## Technologies Used

- **Go** - Backend and core logic
- **Fyne v2** - Cross-platform GUI framework
- **BoltDB** - Embedded database for persistence
- **golang.design/x/clipboard** - Clipboard monitoring
- **golang.design/x/hotkey** - Global hotkey support

### Demo

<img src="demo/demo.gif" />
<!-- Updated by multi-gitter -->
