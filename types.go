package main

import (
	"os/exec"
	"sync"
)

// CommandRequest represents the JSON payload for command execution requests
type CommandRequest struct {
	Command string `json:"command"`
}

// CommandResponse represents the JSON response for command execution
type CommandResponse struct {
	Output string `json:"output"`
	Error  string `json:"error,omitempty"`
	Done   bool   `json:"done"`
}

// FileUploadResponse represents the JSON response for file upload operations
type FileUploadResponse struct {
	Message  string `json:"message"`
	Filename string `json:"filename"`
	Size     int64  `json:"size"`
}

// HealthResponse represents the JSON response for health check endpoint
type HealthResponse struct {
	Status    string `json:"status"`
	Timestamp string `json:"timestamp"`
	OS        string `json:"os"`
	Arch      string `json:"arch"`
	Host      string `json:"host"`
}

// HelpResponse represents the JSON response for help endpoint
type HelpResponse struct {
	Endpoints []EndpointInfo `json:"endpoints"`
}

// EndpointInfo represents information about an API endpoint
type EndpointInfo struct {
	Path        string `json:"path"`
	Method      string `json:"method"`
	Description string `json:"description"`
	Parameters  string `json:"parameters,omitempty"`
}

// Global variables for managing active commands
var (
	activeCommands = make(map[string]*exec.Cmd)
	commandMutex   = &sync.RWMutex{}
)
