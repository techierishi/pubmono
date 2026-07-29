import os
from os import path

files = [f for f in os.listdir("E:\\xampp\\htdocs\\wedding\\images\\us") if path.isfile(f)]
for filename in files:        # Second Example
	if filename != "python.py":
		fname = filename.rsplit('.',1)[0];
		categ = fname.rsplit('_',1)[1];
   		print ',{category:"%s",image:"images/us/%s"}'% (categ,filename)
