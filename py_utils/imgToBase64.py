import os
import re
from os import path
from os import listdir
from os.path import isfile, join
import sys

class SrtUtil:

	def __init__(self,filePath,imgExt):
		self.filePath = filePath
		self.imgExt = imgExt
		
	def getFileList(self):
		onlyFiles = [f for f in listdir(self.filePath) if isfile(join(self.filePath, f))]
		return onlyFiles;

	def seperateFiles(self):
		imgList = []
		allFiles = self.getFileList();
		for filename in allFiles:
			if filename.endswith('.'+self.imgExt):
				imgList.append(filename)
			
		return imgList

	def createJsn(self):
		imgList = self.seperateFiles()

		jsnStr = []
		jsnStr.append('{')
		for pngFile in imgList:
			pngFileNameNoExt = path.splitext(pngFile)
			forJson = self.filePath+'\\'+pngFile
			data = ''+open(forJson, "rb").read()
			jsnStr.append('"'+pngFileNameNoExt[0]+'":{"imgname":"'+pngFile+'","base64":"'+data.encode('base64')+'"},')
		jsnStr.append('}')
		finalJson = ''.join(jsnStr).rstrip()
		open('imgJson.json', 'a').write(finalJson)
		print ''.join(finalJson)
	
		
args = sys.argv[1:]
uitlObj = SrtUtil(args[0],args[1])
vidSrtTupleGlobal = uitlObj.createJsn()
