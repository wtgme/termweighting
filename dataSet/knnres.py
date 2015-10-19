import sys

schemes = ["tf","tfidf", "tfnidf","tfchi","tfig","eccd","tfrf", "tfnrf","iqfqficf", "dc", "bdc","tfen","tfben","tflapsen", "tfjmsen","tfdirsen","tfadsen", "tftssen","dctp","bdctp", "tfentp", "tfbentp", "bsdc", "tfbsen","bsdctp", "tfbsentp"]
matrics = ['MicroF1','MacroF1','Precision','Recall']

matrix = [[0.0 for col in xrange(100)] for raw in xrange(len(schemes)*len(matrics))]


for i in xrange(1,6):
	fo = open(sys.argv[1]+str(i)+'evaluation.txt','r')
	index = 0
	for line in fo.readlines():
		if '\t' in line:
			values = line.strip().split('\t')
			for n in xrange(len(values[1:])):
				matrix[index][n] += float(values[n+1])
			index += 1
	

for i in xrange(len(matrics)):
	print matrics[i]
	for j in xrange(len(schemes)):
		values = matrix[i*len(schemes)+j]	
		s = schemes[j]+'\t'
		for k in xrange(len(values)):
			values[k] /= 5
			s += str(values[k])+'\t'
		s += 'max'+'\t'+str(max(values))
		print s 
