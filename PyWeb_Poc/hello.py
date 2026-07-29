from flask import Flask
from movieChooser import MovieChooser
import json

app = Flask(__name__)

@app.route('/')
def hell_world():
    movieChooser = MovieChooser('http://www.imdb.com/list/ls062017175/')
    # moviesList = movieChooser.scrapWebPage()
    # movieChooser.insertToDB(moviesList)
    print(movieChooser.searchByUrl())
    # print(movieChooser.getAllData())
    return str(json.dumps(movieChooser.searchByUrl()))

if __name__ == '__main__':
        app.run()
