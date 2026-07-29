#!/usr/bin/env python3
"""
Rishell Client Example
A Python client for interacting with the Rishell HTTP reverse shell server.
"""

import requests
import json
import sys
import os
import argparse
from urllib.parse import urljoin

class RishellClient:
    def __init__(self, base_url):
        self.base_url = base_url.rstrip('/')
        self.session = requests.Session()

    def health_check(self):
        """Check server health and get system information."""
        try:
            response = self.session.get(f"{self.base_url}/health")
            response.raise_for_status()
            data = response.json()

            print("🟢 Server Health Check:")
            print(f"  Status: {data['status']}")
            print(f"  Timestamp: {data['timestamp']}")
            print(f"  OS: {data['os']}")
            print(f"  Architecture: {data['arch']}")
            print(f"  Hostname: {data['host']}")
            return True
        except requests.RequestException as e:
            print(f"❌ Health check failed: {e}")
            return False

    def get_help(self):
        """Get help information about available endpoints."""
        try:
            response = self.session.get(f"{self.base_url}/help")
            response.raise_for_status()
            data = response.json()

            print("📚 Available Endpoints:")
            for endpoint in data['endpoints']:
                print(f"  {endpoint['method']} {endpoint['path']}")
                print(f"    {endpoint['description']}")
                if endpoint.get('parameters'):
                    print(f"    Parameters: {endpoint['parameters']}")
                print()
        except requests.RequestException as e:
            print(f"❌ Failed to get help: {e}")

    def upload_file(self, file_path):
        """Upload a file to the server."""
        if not os.path.exists(file_path):
            print(f"❌ File not found: {file_path}")
            return False

        try:
            with open(file_path, 'rb') as f:
                files = {'file': (os.path.basename(file_path), f)}
                response = self.session.post(f"{self.base_url}/upload", files=files)
                response.raise_for_status()
                data = response.json()

                print(f"✅ Upload successful:")
                print(f"  Filename: {data['filename']}")
                print(f"  Size: {data['size']} bytes")
                print(f"  Message: {data['message']}")
                return True
        except requests.RequestException as e:
            print(f"❌ Upload failed: {e}")
            return False

    def download_file(self, remote_path, local_path=None):
        """Download a file from the server."""
        if local_path is None:
            local_path = os.path.basename(remote_path)

        try:
            params = {'path': remote_path}
            response = self.session.get(f"{self.base_url}/download", params=params, stream=True)
            response.raise_for_status()

            with open(local_path, 'wb') as f:
                for chunk in response.iter_content(chunk_size=8192):
                    f.write(chunk)

            file_size = os.path.getsize(local_path)
            print(f"✅ Download successful:")
            print(f"  Remote path: {remote_path}")
            print(f"  Local path: {local_path}")
            print(f"  Size: {file_size} bytes")
            return True
        except requests.RequestException as e:
            print(f"❌ Download failed: {e}")
            return False

    def execute_command(self, command, show_empty_lines=False):
        """Execute a command on the server with live output streaming."""
        try:
            payload = {'command': command}
            response = self.session.post(f"{self.base_url}/run",
                                       json=payload,
                                       stream=True)
            response.raise_for_status()

            print(f"🚀 Executing: {command}")
            print("=" * 50)

            for line in response.iter_lines():
                if line:
                    try:
                        data = json.loads(line.decode('utf-8'))

                        if data.get('output'):
                            if data['output'].strip() or show_empty_lines:
                                print(data['output'])

                        if data.get('error'):
                            print(f"❌ ERROR: {data['error']}", file=sys.stderr)

                        if data.get('done'):
                            print("=" * 50)
                            print("✅ Command completed")
                            break
                    except json.JSONDecodeError:
                        print(f"⚠️  Invalid JSON response: {line}")

            return True
        except requests.RequestException as e:
            print(f"❌ Command execution failed: {e}")
            return False

    def interactive_shell(self):
        """Start an interactive shell session."""
        print("🐚 Interactive Shell Mode")
        print("Type 'exit' to quit, 'help' for available commands")
        print("=" * 50)

        while True:
            try:
                command = input("rishell> ").strip()

                if command.lower() in ['exit', 'quit']:
                    print("👋 Goodbye!")
                    break

                if command.lower() == 'help':
                    print("\nBuilt-in commands:")
                    print("  help     - Show this help")
                    print("  exit     - Exit interactive mode")
                    print("  health   - Check server health")
                    print("  upload   - Upload a file (usage: upload /path/to/file)")
                    print("  download - Download a file (usage: download /remote/path [/local/path])")
                    print("  Any other command will be executed on the remote server\n")
                    continue

                if command.lower() == 'health':
                    self.health_check()
                    continue

                if command.startswith('upload '):
                    file_path = command[7:].strip()
                    self.upload_file(file_path)
                    continue

                if command.startswith('download '):
                    parts = command[9:].strip().split()
                    if len(parts) >= 1:
                        remote_path = parts[0]
                        local_path = parts[1] if len(parts) > 1 else None
                        self.download_file(remote_path, local_path)
                    else:
                        print("Usage: download /remote/path [/local/path]")
                    continue

                if command:
                    self.execute_command(command)

            except KeyboardInterrupt:
                print("\n👋 Goodbye!")
                break
            except EOFError:
                print("\n👋 Goodbye!")
                break

def main():
    parser = argparse.ArgumentParser(description='Rishell HTTP Reverse Shell Client')
    parser.add_argument('url', help='Server URL (e.g., http://localhost:8080)')
    parser.add_argument('-c', '--command', help='Execute a single command')
    parser.add_argument('-u', '--upload', help='Upload a file')
    parser.add_argument('-d', '--download', nargs='+', help='Download a file: remote_path [local_path]')
    parser.add_argument('-i', '--interactive', action='store_true', help='Start interactive shell')
    parser.add_argument('--health', action='store_true', help='Check server health')
    parser.add_argument('--help-server', action='store_true', help='Get server help')

    args = parser.parse_args()

    client = RishellClient(args.url)

    # Check server connectivity first
    print(f"🔗 Connecting to {args.url}")
    if not client.health_check():
        print("❌ Cannot connect to server. Please check the URL and ensure the server is running.")
        sys.exit(1)

    print()

    # Execute based on arguments
    if args.health:
        client.health_check()
    elif args.help_server:
        client.get_help()
    elif args.command:
        client.execute_command(args.command)
    elif args.upload:
        client.upload_file(args.upload)
    elif args.download:
        if len(args.download) >= 1:
            remote_path = args.download[0]
            local_path = args.download[1] if len(args.download) > 1 else None
            client.download_file(remote_path, local_path)
        else:
            print("Usage: -d remote_path [local_path]")
    elif args.interactive:
        client.interactive_shell()
    else:
        client.interactive_shell()

if __name__ == '__main__':
    main()
