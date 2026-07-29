#to install bs4 => sudo apt-get install python3-bs4
# to run this file => pyhton3 <filename>
from urllib.request import urlopen
from bs4 import BeautifulSoup
# from functools import functools

moviesList = []
optionsUrl = 'http://www.imdb.com/list/ls062017175/'
optionsPage = urlopen(optionsUrl)
soup = BeautifulSoup(optionsPage)
mydivs = soup.findAll("div", {"class": "lister-item-content"})
for movieItem in mydivs:
    # print('-----------------------------------')
    # print(movieItem)
    moviesDict = {}
    movieName = movieItem.findAll("h3",{"class":"lister-item-header"})[0].find('a').text
    movieRating = movieItem.findAll("div",{"class":"ratings-imdb-rating"})[0].find('strong').text
    # print(movieName)
    # print('#####################3')
    # print(movieRating)
    moviesDict['movieName'] = movieName
    moviesDict['movieRating'] = movieRating
    moviesList.append(moviesDict)

def filterMovies(item):
    # print(item)
    return float(item['movieRating']) >= 7.5

filteredMovieItr = filter(filterMovies ,moviesList)  
print(list(filteredMovieItr))