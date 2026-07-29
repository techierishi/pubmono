import os
from os import path

files = [f for f in os.listdir("E:\\xampp\\htdocs\\wedding\\images\\friends") if path.isfile(f)]
for filename in files:        # Second Example
	if filename != "python.py":
		fname = filename.rsplit('.',1)[0].replace("_", " ").title()
	   	print ',{name:"%s",image:"images/friends/%s",message:" Hi %s please attend our wedding"}'% (fname,filename,fname)
