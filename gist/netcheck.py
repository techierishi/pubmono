import csv
import time
import datetime
import socket
import urllib.request

def check_internet(host="8.8.8.8", port=53, timeout=3):
    try:
        socket.setdefaulttimeout(timeout)
        socket.socket(socket.AF_INET, socket.SOCK_STREAM).connect((host, port))
        return True
    except socket.error:
        return False

def measure_latency(url="https://www.google.com", timeout=5):
    try:
        start = time.time()
        urllib.request.urlopen(url, timeout=timeout)
        return round((time.time() - start) * 1000, 2)
    except:
        return None

filename = "internet_log.csv"

with open(filename, mode="w", newline="") as file:
    writer = csv.writer(file)
    writer.writerow(["Timestamp", "Connected", "Latency_ms"])

print("Monitoring internet connectivity... Press Ctrl+C to stop.")

while True:
    timestamp = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    connected = check_internet()
    latency = measure_latency() if connected else None
    with open(filename, mode="a", newline="") as file:
        writer = csv.writer(file)
        writer.writerow([timestamp, connected, latency])
    time.sleep(1)
