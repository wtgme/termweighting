import sys


def MaxSimilary(fileName):
	similaAll = 0.0
	count = 0
	fo = open(fileName,'r')
	for line in fo.readlines():
		count += 1
		documents = line.strip().split('\t')
		closest = documents[1].split(':')
		similaAll += float(closest[2])
	fo.close()
	return (similaAll/count)


def Accuracy(fileName):
	allCount = 0.0
	accurate = 0.0
	fo = open(fileName,'r')
	for line in fo.readlines():
		#print '------------------------------------'
		allCount += 1
		documents = line.strip().split('\t')
		orig = documents[0].strip().split(':')[2]
		origid = documents[0].strip().split(':')[0]
		origList = orig.strip().split(' ')

		
		closest = documents[1].strip().split(':')[0]
		closestid = documents[1].strip().split(':')[1]
		cloesList = closest.strip().split(' ')
		
		
		i1, i2 = 0,0
		for token in origList:
			if token in cloesList:
				i1+=1
				i2+=1
		#print origList
		#print cloesList
		if len(origList)-i1 == 0 and len(cloesList)-i2 == 0:
			accurate +=1
		else:
			print origid,origList, closestid,cloesList
			#print 'adfa'
	fo.close()
	#print '%f/%f' %(accurate,allCount)
	return (accurate/allCount)

print "%s" %(sys.argv[1])

lists = ["tf", "tfidf", "tfnidf", "tfchi", "tfig", "eccd", "tfrf", "tfnrf", "iqfqficf", "dc", "bdc", "sdc", "dctp", "bdctp", "tfen", "tfben", "tfsen", "tfentp", "tfbentp"]

for name in lists[len(lists)-7:]:
	avgSimi = Accuracy(sys.argv[1]+"/"+name+".txt")
	print '%s\t%f' %(name,avgSimi)
	#print name
	#MaxSimilary(sys.argv[1]+"/"+name+".txt")



