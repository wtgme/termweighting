import sys

fo = open(sys.argv[1],'r')
classSet = set([])
size = 0
for line in fo.readlines():
	class_doc = line.split('\t')
	#print class_doc[0]
	classSet.add(class_doc[0])
	#size += int(class_doc[2])
fo.close()
print classSet
print len(classSet)
print size
