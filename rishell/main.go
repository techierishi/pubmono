package main

import (
	"fmt"
	"log"
	"net/http"
	"os"
)

func main() {
	// Register HTTP handlers
	http.HandleFunc("/health", healthHandler)
	http.HandleFunc("/help", helpHandler)
	http.HandleFunc("/upload", uploadHandler)
	http.HandleFunc("/download", downloadHandler)
	http.HandleFunc("/run", runHandler)

	// Get port from environment or use default
	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}

	// Print server startup information
	fmt.Printf("HTTP Reverse Shell Server starting on port %s\n", port)
	fmt.Printf("Available endpoints:\n")
	fmt.Printf("  GET  /health   - Server health check\n")
	fmt.Printf("  GET  /help     - List all endpoints\n")
	fmt.Printf("  POST /upload   - Upload files to server\n")
	fmt.Printf("  GET  /download - Download files from server\n")
	fmt.Printf("  POST /run      - Execute commands with live output\n")

	// Start the HTTP server
	log.Fatal(http.ListenAndServe(":"+port, nil))
}
