import sys

def flag(test, prediction):
	flags = {}
	for k,v in test.iteritems():
		if v == prediction[k]:
			flags[k] = 1
		else:
			flags[k] = 0
	return flags

def readFile(fileName):
	fo = open(fileName, 'r')
	trueL, predL = {},{}
	for line in fo.readlines():
		parts = line.split('\t')
		testDoc = parts[0]
		id_cl_con = testDoc.split(':')
		trueL[int(id_cl_con[0])]=int(id_cl_con[1])
			
		cl_sim = {}		
		for i in xrange(1,10):
			neibor = parts[i]
			doc_class_sim = neibor.split(':')
			cl = int(doc_class_sim[2])
			sim = float(doc_class_sim[3])
			bsim = cl_sim.get(cl,0.0)
			cl_sim[cl] = sim+bsim
		ml,maxs = 0,0.0
		for k,v in cl_sim.items():
			if maxs <= v:
				ml,maxs= k,v
		predL[int(id_cl_con[0])]=ml
	return trueL,predL

schemes = ["tfidf", "tfnidf", "tfrf", "tfnrf", "iqfqficf", "dc", "bdc", "tfen", "tfben"]


for s1 in xrange(0,len(schemes)):
	for s2 in xrange(s1,len(schemes)):
		scheme1, scheme2 = schemes[s1], schemes[s2]

		if scheme1!=scheme2:
			ta,pa = readFile(sys.argv[1]+'/prediction/'+scheme1+'.txt')
			tb,pb = readFile(sys.argv[1]+'/prediction/'+scheme2+'.txt')
			
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
	
	
