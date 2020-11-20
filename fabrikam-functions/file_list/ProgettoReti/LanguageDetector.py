import sys

from langdetect import detect

text =''
for i,arg in enumerate(sys.argv):
    if i>0:
        text+=' '+arg

print(text)
print(detect(text))