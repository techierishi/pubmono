#!/bin/bash

# Rishell HTTP Reverse Shell - Test Script
# This script tests all available endpoints

set -e

# Configuration
SERVER_URL=${1:-"http://localhost:8080"}
TEST_FILE="test_upload.txt"
DOWNLOAD_PATH="/etc/hostname"

echo "🧪 Testing Rishell HTTP Reverse Shell"
echo "Server URL: $SERVER_URL"
echo "=================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print test results
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✅ PASS${NC}: $2"
    else
        echo -e "${RED}❌ FAIL${NC}: $2"
    fi
}

# Function to test HTTP endpoint
test_endpoint() {
    local method=$1
    local endpoint=$2
    local description=$3
    local expected_status=${4:-200}

    echo -e "\n${BLUE}Testing:${NC} $method $endpoint - $description"

    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "%{http_code}" -o /tmp/response.json "$SERVER_URL$endpoint")
    else
        response=$(curl -s -w "%{http_code}" -o /tmp/response.json -X "$method" "$SERVER_URL$endpoint")
    fi

    if [ "$response" = "$expected_status" ]; then
        echo -e "${GREEN}✅ Status: $response${NC}"
        if [ -f /tmp/response.json ] && [ -s /tmp/response.json ]; then
            echo "Response preview:"
            head -c 200 /tmp/response.json | jq . 2>/dev/null || cat /tmp/response.json
        fi
        return 0
    else
        echo -e "${RED}❌ Status: $response (expected $expected_status)${NC}"
        return 1
    fi
}

# Test 1: Health Check
echo -e "\n${YELLOW}1. Testing Health Check${NC}"
test_endpoint "GET" "/health" "Server health and system info"
print_result $? "Health endpoint"

# Test 2: Help
echo -e "\n${YELLOW}2. Testing Help${NC}"
test_endpoint "GET" "/help" "Endpoint documentation"
print_result $? "Help endpoint"

# Test 3: File Upload
echo -e "\n${YELLOW}3. Testing File Upload${NC}"
echo "This is a test file for upload" > "$TEST_FILE"
echo "Testing file upload..."

upload_response=$(curl -s -w "%{http_code}" -X POST -F "file=@$TEST_FILE" "$SERVER_URL/upload")
if [ "$upload_response" = "200" ]; then
    echo -e "${GREEN}✅ Upload Status: 200${NC}"
    print_result 0 "File upload"
else
    echo -e "${RED}❌ Upload Status: $upload_response${NC}"
    print_result 1 "File upload"
fi

# Clean up test file
rm -f "$TEST_FILE"

# Test 4: File Download
echo -e "\n${YELLOW}4. Testing File Download${NC}"
echo "Testing file download with path: $DOWNLOAD_PATH"

download_response=$(curl -s -w "%{http_code}" -o /tmp/downloaded_file "$SERVER_URL/download?path=$DOWNLOAD_PATH")
if [ "$download_response" = "200" ]; then
    echo -e "${GREEN}✅ Download Status: 200${NC}"
    if [ -f /tmp/downloaded_file ]; then
        file_size=$(wc -c < /tmp/downloaded_file)
        echo "Downloaded file size: $file_size bytes"
        rm -f /tmp/downloaded_file
    fi
    print_result 0 "File download"
else
    echo -e "${RED}❌ Download Status: $download_response${NC}"
    print_result 1 "File download"
fi

# Test 5: Command Execution
echo -e "\n${YELLOW}5. Testing Command Execution${NC}"
echo "Testing simple command execution..."

# Test simple command
echo "Executing: whoami"
response=$(curl -s -X POST -H "Content-Type: application/json" \
    -d '{"command":"whoami"}' "$SERVER_URL/run")

if echo "$response" | grep -q '"done":true'; then
    echo -e "${GREEN}✅ Command executed successfully${NC}"
    echo "Response preview:"
    echo "$response" | head -n 3
    print_result 0 "Simple command execution"
else
    echo -e "${RED}❌ Command execution failed${NC}"
    echo "$response"
    print_result 1 "Simple command execution"
fi

# Test 6: Multiline Command
echo -e "\n${YELLOW}6. Testing Multiline Command${NC}"
echo "Testing multiline command execution..."

multiline_cmd='for i in {1..3}; do echo "Line $i"; done'
echo "Executing: $multiline_cmd"

response=$(curl -s -X POST -H "Content-Type: application/json" \
    -d "{\"command\":\"$multiline_cmd\"}" "$SERVER_URL/run")

if echo "$response" | grep -q '"done":true'; then
    echo -e "${GREEN}✅ Multiline command executed successfully${NC}"
    echo "Response preview:"
    echo "$response" | head -n 5
    print_result 0 "Multiline command execution"
else
    echo -e "${RED}❌ Multiline command execution failed${NC}"
    echo "$response"
    print_result 1 "Multiline command execution"
fi

# Test 7: Long Running Command
echo -e "\n${YELLOW}7. Testing Long Running Command${NC}"
echo "Testing streaming output with ping command..."

ping_cmd='ping -c 3 127.0.0.1'
echo "Executing: $ping_cmd"

# Use timeout to prevent hanging
timeout 30s curl -s -X POST -H "Content-Type: application/json" \
    -d "{\"command\":\"$ping_cmd\"}" "$SERVER_URL/run" > /tmp/ping_output.json

if [ $? -eq 0 ] && grep -q '"done":true' /tmp/ping_output.json; then
    echo -e "${GREEN}✅ Long running command completed${NC}"
    line_count=$(wc -l < /tmp/ping_output.json)
    echo "Received $line_count lines of streaming output"
    print_result 0 "Long running command with streaming"
else
    echo -e "${RED}❌ Long running command failed or timed out${NC}"
    print_result 1 "Long running command with streaming"
fi

rm -f /tmp/ping_output.json

# Test 8: Error Handling
echo -e "\n${YELLOW}8. Testing Error Handling${NC}"

# Test invalid endpoint
echo "Testing invalid endpoint..."
invalid_response=$(curl -s -w "%{http_code}" -o /dev/null "$SERVER_URL/invalid")
if [ "$invalid_response" = "404" ]; then
    echo -e "${GREEN}✅ Invalid endpoint returns 404${NC}"
    print_result 0 "Invalid endpoint handling"
else
    echo -e "${RED}❌ Invalid endpoint returns $invalid_response (expected 404)${NC}"
    print_result 1 "Invalid endpoint handling"
fi

# Test invalid method
echo "Testing invalid method on /health..."
invalid_method_response=$(curl -s -w "%{http_code}" -o /dev/null -X POST "$SERVER_URL/health")
if [ "$invalid_method_response" = "405" ]; then
    echo -e "${GREEN}✅ Invalid method returns 405${NC}"
    print_result 0 "Invalid method handling"
else
    echo -e "${RED}❌ Invalid method returns $invalid_method_response (expected 405)${NC}"
    print_result 1 "Invalid method handling"
fi

# Test invalid JSON
echo "Testing invalid JSON on /run..."
invalid_json_response=$(curl -s -w "%{http_code}" -o /dev/null -X POST \
    -H "Content-Type: application/json" -d '{invalid json}' "$SERVER_URL/run")
if [ "$invalid_json_response" = "400" ]; then
    echo -e "${GREEN}✅ Invalid JSON returns 400${NC}"
    print_result 0 "Invalid JSON handling"
else
    echo -e "${RED}❌ Invalid JSON returns $invalid_json_response (expected 400)${NC}"
    print_result 1 "Invalid JSON handling"
fi

# Summary
echo -e "\n${BLUE}=================================="
echo -e "🏁 Test Summary"
echo -e "==================================${NC}"

# Clean up temporary files
rm -f /tmp/response.json /tmp/downloaded_file

echo -e "\n${GREEN}✅ All basic endpoint tests completed!${NC}"
echo -e "${YELLOW}💡 For interactive testing, use the Python client:${NC}"
echo -e "   python3 client_example.py $SERVER_URL -i"
echo -e "\n${YELLOW}💡 For manual testing, try these curl commands:${NC}"
echo -e "   curl $SERVER_URL/health"
echo -e "   curl $SERVER_URL/help"
echo -e "   curl -X POST -F \"file=@somefile.txt\" $SERVER_URL/upload"
echo -e "   curl \"$SERVER_URL/download?path=/etc/passwd\""
echo -e "   curl -X POST -H \"Content-Type: application/json\" -d '{\"command\":\"ls -la\"}' $SERVER_URL/run"
