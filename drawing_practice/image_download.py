import urllib.request
from selenium import webdriver

def getAllImgLinks():
	mainURL = 'https://www.drawingtutorials101.com/Comic-Characters-drawing-tutorials'
	browser = webdriver.Firefox(executable_path="/home/techierishi/selenium_drivers/geckodriver")
	browser.set_page_load_timeout(60*10)
	browser.get(mainURL)

	allAnchorTexts = browser.find_elements_by_class_name("block_category")
	allAnchorTextsLinks = []

	startsFrom = 'Spookley the Square Pumpkin'
	shouldResume = True

	for linkToKeep in allAnchorTexts:
		linkTextPair = {}
		tutUrl = linkToKeep.get_attribute("href")
		tutText = linkToKeep.text
		if(tutText == startsFrom and shouldResume == False):
			shouldResume = True

		if(shouldResume):
			linkTextPair["link"] = tutUrl
			linkTextPair["text"] = tutText
			allAnchorTextsLinks.append(linkTextPair)

	goToTutorialSublist(allAnchorTextsLinks,browser)
	
	# print('allAnchorTextsLinks',allAnchorTextsLinks)
def goToTutorialSublist(allAnchorTextsLinks, browser):
	myfile = open('Comic_Characters.html', 'w')

	for linkToGo in allAnchorTextsLinks:
		browser.get(linkToGo["link"])
		print(linkToGo["text"])
		paginationUl = browser.find_element_by_class_name("pagination")
		allPages = paginationUl.find_elements_by_tag_name("a")
		allPaginationSet = set()
		for pagi in allPages:
			paginationLink= pagi.get_attribute('href')
			if(paginationLink.endswith('#') != True):
				allPaginationSet.add(paginationLink)
		allPagination = list(allPaginationSet)
		allPagination.sort()
		print('allPagination',allPagination)

		for currentPage in allPagination:
			print('currentPage',currentPage)
			browser.get(currentPage)
			tutorialsToClick = browser.find_elements_by_xpath("//*[contains(text(), 'View this Tutorial')]")
			tutorialLinks = []
			for tutorialToClick in tutorialsToClick:
				tutUrl = tutorialToClick.get_attribute("href")
				tutorialLinks.append(tutUrl)
			gotToTutorial(tutorialLinks,browser,linkToGo,myfile)

	myfile.close()

def gotToTutorial(tutorialLinks,browser,linkToGo,myfile):
	# print('tutorialLinks',tutorialLinks)
	count = 1
	for tutUrl in tutorialLinks:
		# print('tutUrl',tutUrl)
		browser.get(tutUrl)
		image = browser.find_elements_by_tag_name("img")
		img_src = image[2].get_attribute("src")
		print(img_src)
		myfile.writelines('<a href="'+img_src+'"> '+linkToGo["text"]+' '+str(count)+' </a></br>')
		count +=1

getAllImgLinks()