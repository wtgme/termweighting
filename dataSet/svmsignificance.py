import sys

def readID(fileName):
	arr = []
	fo = open(fileName,'r')
	for line in fo.readlines():
		arr.append(int(line))
	fo.close()
	#print fileName,'ids ',len(arr)
	return arr

def readTest(ids, fileName):
	arr = {}
	fo = open(fileName, 'r')
	index = 0
	for line in fo.readlines():
		tokens = line.split(' ')
		arr[ids[index]] = int(tokens[0])
		index += 1
	fo.close()
	return arr



def readPredict(ids, fileName):
	arr = {}
	index = 0
	fo = open(fileName, 'r')
	for line in fo.readlines():
		arr[ids[index]] = int(line)
		index += 1
	fo.close()
	return arr


'''def flag(test, prediction):
	la = len(test)
	#print len(test), len(prediction)
	flags = []
	for index in xrange(la):
		if test[index] == prediction[index]:
			flags.append(1)
		else:
			flags.append(0)
	return flags'''

def flag(test, prediction):
	flags = {}
	for k,v in test.iteritems():
		if v == prediction[k]:
			flags[k] = 1
		else:
			flags[k] = 0
	return flags

schemes = ["tfidf", "tfnidf", "tfrf", "tfnrf", "iqfqficf", "dc", "bdc", "tfen", "tfben"]


for s1 in xrange(0,len(schemes)):
	for s2 in xrange(s1,len(schemes)):
		scheme1, scheme2 = schemes[s1], schemes[s2]
		if scheme1!=scheme2:
			ida = readID(sys.argv[1]+'/svm/data/ID'+scheme1+'.txt')
			idb = readID(sys.argv[1]+'/svm/data/ID'+scheme2+'.txt')
			ta = readTest(ida,sys.argv[1]+'/svm/data/test'+scheme1+'.txt')
			tb = readTest(idb,sys.argv[1]+'/svm/data/test'+scheme2+'.txt')
			pa = readPredict(ida,sys.argv[1]+'/svm/prediction/'+scheme1+'.txt')
			pb = readPredict(idb,sys.argv[1]+'/svm/prediction/'+scheme2+'.txt')



			fa = flag(ta, pa)
			fb = flag(tb, pb)

			n00, n01, n10, n11 = 0, 0, 0, 0

			for k,v in fa.iteritems():
				
				a, b= fa[k], fb[k]
	
				if a == 0 and b == 0:
					n00 += 1
				elif a == 0 and b == 1:
					n01 += 1
				elif a == 1 and b == 0:
					n10 += 1
				else:
					n11 += 1
			#print n00, n01, n10, n11
			chi = (float)(abs(n01-n10)-1)*(abs(n01-n10)-1)/(n01 + n10)
			if chi >= 10.83:
				if n10>n01:
					print scheme1+' >>> ',scheme2+':',chi
				elif n10<=n01:
					print scheme2+' >>> ',scheme1+':',chi

			elif chi >= 6.64 and chi < 10.83:
				if n10>n01:
					print scheme1+' >> ',scheme2+':',chi
				elif n10<=n01:
					print scheme2+' >> ',scheme1+':',chi
			elif chi >= 3.84 and chi < 6.64:
				if n10>n01:
					print scheme1+' > ',scheme2+':',chi
				elif n10<=n01:
					print scheme2+' > ',scheme1+':',chi
			else:
				if n10>n01:
					print scheme1+' = ',scheme2+':',chi
				elif n10<=n01:
					print scheme2+' = ',scheme1+':',chi
	
	
