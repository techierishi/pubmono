import time
from flask import Flask
app = Flask(__name__)
  
def write_file():
    time_str = str(time.time())
    file_path= '/data/file'+time_str+'.txt'
    file1 = open(file_path, 'w')
    s = "File was written at: \n"+time_str
    file1.write(s)
    file1.close()
    file1 = open(file_path, 'r')
    print(file1.read())
    file1.close()
    return time_str

@app.route('/')
def hello():
    time_str = write_file()
    return "File was written at:: "+time_str
  
  
if __name__ == "__main__":
    app.run(host ='0.0.0.0', port = 5001, debug = True) 
