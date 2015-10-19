import sys

schemes = ["tf","tfidf", "tfnidf","tfchi","tfig","eccd","tfrf", "tfnrf","iqfqficf", "dc", "bdc","tfen","tfben","tflapsen", "tfjmsen","tfdirsen","tfadsen", "tftssen","dctp","bdctp", "tfentp", "tfbentp", "bsdc", "tfbsen","bsdctp", "tfbsentp"]
matrics = ['MicroF1','MacroF1','Precision','Recall']

sizes = [50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,1500, 2000]

results = [[0.0 for col in xrange(len(sizes))] for raw in xrange(len(schemes)*len(matrics))]

for s in xrange(len(sizes)):
	i = sizes[s]
	fo = open(sys.argv[1]+str(i)+'evaluation.txt','r')
	index = 0
	for line in fo.readlines():
		if '\t' in line:
			values = line.strip().split('\t')
			maxv = 0.0
			for n in xrange(len(values[1:])):
				v = float(values[n+1])
				if maxv <= v:
					maxv = v
			results[index][s] = maxv
			index += 1
	

for i in xrange(len(matrics)):
	print matrics[i]
	for j in xrange(len(schemes)):
		values = results[i*len(schemes)+j]	
		s = schemes[j]+'\t'
		for k in xrange(len(values)):
			s += str(values[k])+'\t'
		print s 
