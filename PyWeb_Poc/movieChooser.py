# to run this file => pyhton3 <filename>
from urllib.request import urlopen
from bs4 import BeautifulSoup
from tinydb import TinyDB, Query, where
import json
import base64

# from functools import functools
class MovieChooser:
    def __init__(self, url):
        self.url = url
        self.db = TinyDB('db.json')

    def scrapWebPage(self):
        moviesList = []
        # optionsUrl = 'http://www.imdb.com/list/ls062017175/'
        optionsPage = urlopen(self.url)
        soup = BeautifulSoup(optionsPage)
        mydivs = soup.findAll("div", {"class": "lister-item"})
        for movieItem in mydivs:
            # print('-----------------------------------')
            # print(movieItem)
            moviesDict = {}
            movieName = movieItem.findAll("h3",{"class":"lister-item-header"})[0].find('a').text
            movieRating = movieItem.findAll("div",{"class":"ratings-imdb-rating"})[0].find('strong').text
            movieImg = movieItem.findAll("div",{"class":"lister-item-image"})[0].find('img')['src']
            # print(movieName)
            # print('#####################3')
            # print(movieImg)
            moviesDict['movieName'] = movieName
            moviesDict['movieRating'] = movieRating
            moviesDict['movieImg'] = movieImg
            moviesList.append(moviesDict)
        return moviesList

    def filterMovies(self,moviesList, filterCriteria):    
        def filterClosure(item):
            # print(item)
            return float(item['movieRating']) >= filterCriteria['rating']
        filteredMovieItr = filter(filterMovies ,moviesList)
        print(list(filteredMovieItr))
        return list(filteredMovieItr)
    
    def insertToDB(self,moviesList):
        urlAndList = {}
        urlAndList['moviesUrl'] = base64.b64encode(bytes(self.url, 'utf-8')).decode("utf-8")
        urlAndList['moviesList'] = moviesList
        # print(urlAndList)
        # jsonMovieList = json.dumps(urlAndList)
        # print(str(jsonMovieList))
        moviesByUrl = self.db.table('moviesByUrl')
        moviesByUrl.insert(urlAndList)
    def getAllData(self):
        moviesByUrl = self.db.table('moviesByUrl')
        allData = moviesByUrl.all()
        # print(allData)
        return allData

    def searchByUrl(self):
        moviesByUrl = self.db.table('moviesByUrl')
        return moviesByUrl.get(where('moviesUrl')=='aHR0cDovL3d3dy5pbWRiLmNvbS9saXN0L2xzMDYyMDE3MTc1Lw==')
        # moviesByUrl.search(Query()['moviesUrl'] == base64.b64encode(bytes(self.url, 'utf-8')).decode("utf-8"))
        

# movieChooser = MovieChooser('http://www.imdb.com/list/ls062017175/')
# moviesList = movieChooser.scrapWebPage()
# movieChooser.insertToDB(moviesList)
# print(movieChooser.searchByUrl())
# print(movieChooser.getAllData())

