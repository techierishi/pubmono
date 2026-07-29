package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"
	"os"
	"os/exec"
	"path/filepath"
	"runtime"
	"time"
)

// healthHandler handles GET /health - returns server health and system information
func healthHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	hostname, _ := os.Hostname()

	response := HealthResponse{
		Status:    "ok",
		Timestamp: time.Now().UTC().Format(time.RFC3339),
		OS:        runtime.GOOS,
		Arch:      runtime.GOARCH,
		Host:      hostname,
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

// helpHandler handles GET /help - returns list of available endpoints
func helpHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	endpoints := []EndpointInfo{
		{
			Path:        "/health",
			Method:      "GET",
			Description: "Check server health and system information",
		},
		{
			Path:        "/help",
			Method:      "GET",
			Description: "List all available endpoints",
		},
		{
			Path:        "/upload",
			Method:      "POST",
			Description: "Upload files to the server",
			Parameters:  "multipart/form-data with 'file' field",
		},
		{
			Path:        "/download",
			Method:      "GET",
			Description: "Download files from the server",
			Parameters:  "?path=/absolute/path/to/file",
		},
		{
			Path:        "/run",
			Method:      "POST",
			Description: "Execute commands with live streaming output (long polling)",
			Parameters:  "JSON body: {\"command\": \"your command here\"}",
		},
	}

	response := HelpResponse{
		Endpoints: endpoints,
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

// uploadHandler handles POST /upload - uploads files to the server
func uploadHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	// Parse multipart form with 32MB max memory
	err := r.ParseMultipartForm(32 << 20)
	if err != nil {
		http.Error(w, "Error parsing form: "+err.Error(), http.StatusBadRequest)
		return
	}

	file, handler, err := r.FormFile("file")
	if err != nil {
		http.Error(w, "Error retrieving file: "+err.Error(), http.StatusBadRequest)
		return
	}
	defer file.Close()

	// Create uploads directory if it doesn't exist
	uploadDir := "./uploads"
	if err := os.MkdirAll(uploadDir, 0755); err != nil {
		http.Error(w, "Error creating upload directory: "+err.Error(), http.StatusInternalServerError)
		return
	}

	// Create destination file
	filename := filepath.Join(uploadDir, handler.Filename)
	dst, err := os.Create(filename)
	if err != nil {
		http.Error(w, "Error creating file: "+err.Error(), http.StatusInternalServerError)
		return
	}
	defer dst.Close()

	// Copy file content
	size, err := io.Copy(dst, file)
	if err != nil {
		http.Error(w, "Error saving file: "+err.Error(), http.StatusInternalServerError)
		return
	}

	response := FileUploadResponse{
		Message:  "File uploaded successfully",
		Filename: handler.Filename,
		Size:     size,
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

// downloadHandler handles GET /download - downloads files from the server
func downloadHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	filePath := r.URL.Query().Get("path")
	if filePath == "" {
		http.Error(w, "Missing 'path' parameter", http.StatusBadRequest)
		return
	}

	// Check if file exists and is readable
	fileInfo, err := os.Stat(filePath)
	if err != nil {
		if os.IsNotExist(err) {
			http.Error(w, "File not found", http.StatusNotFound)
		} else {
			http.Error(w, "Error accessing file: "+err.Error(), http.StatusInternalServerError)
		}
		return
	}

	if fileInfo.IsDir() {
		http.Error(w, "Path is a directory, not a file", http.StatusBadRequest)
		return
	}

	file, err := os.Open(filePath)
	if err != nil {
		http.Error(w, "Error opening file: "+err.Error(), http.StatusInternalServerError)
		return
	}
	defer file.Close()

	// Set headers for file download
	w.Header().Set("Content-Disposition", "attachment; filename="+filepath.Base(filePath))
	w.Header().Set("Content-Type", "application/octet-stream")
	w.Header().Set("Content-Length", fmt.Sprintf("%d", fileInfo.Size()))

	// Stream file content
	_, err = io.Copy(w, file)
	if err != nil {
		log.Printf("Error streaming file: %v", err)
	}
}

// runHandler handles POST /run - executes commands with live streaming output
func runHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	var req CommandRequest
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		http.Error(w, "Invalid JSON: "+err.Error(), http.StatusBadRequest)
		return
	}

	if req.Command == "" {
		http.Error(w, "Missing 'command' field", http.StatusBadRequest)
		return
	}

	// Set headers for streaming response
	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Cache-Control", "no-cache")
	w.Header().Set("Connection", "keep-alive")

	// Determine shell and command execution method
	var cmd *exec.Cmd
	if runtime.GOOS == "windows" {
		cmd = exec.Command("cmd", "/C", req.Command)
	} else {
		cmd = exec.Command("/bin/bash", "-c", req.Command)
	}

	// Create pipes for stdout and stderr
	stdout, err := cmd.StdoutPipe()
	if err != nil {
		response := CommandResponse{
			Error: "Error creating stdout pipe: " + err.Error(),
			Done:  true,
		}
		json.NewEncoder(w).Encode(response)
		return
	}

	stderr, err := cmd.StderrPipe()
	if err != nil {
		response := CommandResponse{
			Error: "Error creating stderr pipe: " + err.Error(),
			Done:  true,
		}
		json.NewEncoder(w).Encode(response)
		return
	}

	// Start the command
	if err := cmd.Start(); err != nil {
		response := CommandResponse{
			Error: "Error starting command: " + err.Error(),
			Done:  true,
		}
		json.NewEncoder(w).Encode(response)
		return
	}

	// Store active command for potential cleanup
	commandID := fmt.Sprintf("%d", cmd.Process.Pid)
	commandMutex.Lock()
	activeCommands[commandID] = cmd
	commandMutex.Unlock()

	// Clean up when done
	defer func() {
		commandMutex.Lock()
		delete(activeCommands, commandID)
		commandMutex.Unlock()
	}()

	// Create channels for output
	outputChan := make(chan string, 100)
	errorChan := make(chan string, 100)
	doneChan := make(chan bool, 1)

	// Read stdout
	go func() {
		scanner := bufio.NewScanner(stdout)
		for scanner.Scan() {
			outputChan <- scanner.Text()
		}
		if err := scanner.Err(); err != nil {
			errorChan <- "Stdout scan error: " + err.Error()
		}
	}()

	// Read stderr
	go func() {
		scanner := bufio.NewScanner(stderr)
		for scanner.Scan() {
			errorChan <- scanner.Text()
		}
		if err := scanner.Err(); err != nil {
			errorChan <- "Stderr scan error: " + err.Error()
		}
	}()

	// Wait for command completion
	go func() {
		err := cmd.Wait()
		if err != nil {
			errorChan <- "Command execution error: " + err.Error()
		}
		doneChan <- true
	}()

	flusher, ok := w.(http.Flusher)
	if !ok {
		http.Error(w, "Streaming unsupported", http.StatusInternalServerError)
		return
	}

	// Stream output in real-time
	for {
		select {
		case output := <-outputChan:
			response := CommandResponse{
				Output: output,
				Done:   false,
			}
			if err := json.NewEncoder(w).Encode(response); err != nil {
				return
			}
			flusher.Flush()

		case errorMsg := <-errorChan:
			response := CommandResponse{
				Error: errorMsg,
				Done:  false,
			}
			if err := json.NewEncoder(w).Encode(response); err != nil {
				return
			}
			flusher.Flush()

		case <-doneChan:
			// Send any remaining output
			for {
				select {
				case output := <-outputChan:
					response := CommandResponse{
						Output: output,
						Done:   false,
					}
					json.NewEncoder(w).Encode(response)
					flusher.Flush()
				case errorMsg := <-errorChan:
					response := CommandResponse{
						Error: errorMsg,
						Done:  false,
					}
					json.NewEncoder(w).Encode(response)
					flusher.Flush()
				default:
					goto done
				}
			}
		done:
			// Send final completion response
			response := CommandResponse{
				Output: "",
				Done:   true,
			}
			json.NewEncoder(w).Encode(response)
			flusher.Flush()
			return

		case <-r.Context().Done():
			// Client disconnected, kill the command
			if cmd.Process != nil {
				cmd.Process.Kill()
			}
			return

		case <-time.After(30 * time.Second):
			// Heartbeat - send empty response to keep connection alive
			response := CommandResponse{
				Output: "",
				Done:   false,
			}
			if err := json.NewEncoder(w).Encode(response); err != nil {
				return
			}
			flusher.Flush()
		}
	}
}
