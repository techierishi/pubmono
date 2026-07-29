import os
import re
from os import path
from os import listdir
from os.path import isfile, join
import sys

class SrtUtil:

	def __init__(self,filePath,vidExt):
		self.filePath = filePath
		self.vidExt = vidExt
		
	def getFileList(self):
		onlyFiles = [f for f in listdir(self.filePath) if isfile(join(self.filePath, f))]
		return onlyFiles;

	def seperateFiles(self):
		vidSrtTuple=()
		vidList = []
		srtList = []
		allFiles = self.getFileList();
		for filename in allFiles:
			if filename.endswith('.'+self.vidExt):
				vidList.append(filename)
			elif filename.endswith('.srt'):
				srtList.append(filename)
		
		vidSrtTuple = (vidList,srtList)
		return vidSrtTuple
	def renameFile(self):
		vidSrtTupleLcl = self.seperateFiles()
		for vidFileName in vidSrtTupleLcl[0]:
			sXXeXX = self.findSrtForVid(vidFileName,vidSrtTupleLcl[1])
		
	def findSrtForVid(self,vidFileName,srtList):
		s00e00 = ''
		try:
			show_p=re.compile("^(.*)\.(?i)s(\d*)(?i)e(\d*).*?([^\.]*)$")
			strTuple = show_p.match(vidFileName).groups()
			#print(strTuple)
			s00e00 = 'S'+strTuple[1]+'E'+strTuple[2]
		except AttributeError:
			s00e00 = ''
		if s00e00:
			for srtFileName in srtList:
				if re.search(s00e00, srtFileName, re.IGNORECASE):
					vidFileNameNoExt = path.splitext(vidFileName);
					#print(vidFileNameNoExt[0])
					os.rename(self.filePath+'\\'+srtFileName,self.filePath+'\\'+vidFileNameNoExt[0]+'.srt');
				
		return
		
args = sys.argv[1:]
uitlObj = SrtUtil(args[0],args[1])
vidSrtTupleGlobal = uitlObj.renameFile()
