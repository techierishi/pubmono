

def createFileArray():
	array = []
	with open("AllAnimalsDrawing.html", "r") as ins:
	    for line in ins:
	    	if(line.startswith('https')):
	    		array.append(line)
	return array

def createAnchors():
	linksArray = createFileArray()
	myfile = open('anchors.html', 'w')
	count = 1
	for link in linksArray:
		myfile.writelines('<a href="'+link+'"> Link'+str(count)+' </a></br>')
		count+=1
	myfile.close()

createAnchors()