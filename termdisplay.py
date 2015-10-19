import sys


voc = {}
cates = {}
fo = open(sys.argv[1],'r')

for line in fo.readlines():
	token_id = line.split("\t")
	voc[int(token_id[1])] = token_id[0]
fo.close()


fo = open(sys.argv[2],'r')

for line in fo.readlines():
	class_id = line.split("\t")
	cates[int(class_id[1])] = class_id[0]
fo.close()

fo = open(sys.argv[3],'r')
for line in fo.readlines():
	#print line
	token_class_weight = line.split("\t") 
	output = voc.get(int(token_class_weight[0]))+"\t"
	class_weights = token_class_weight[1].strip().split(" ")
	#print token_class_weight[1]
	for c_w in class_weights:
		cw = c_w.split(":")
		#print c_w
		output += cates.get(int(cw[0]))+cw[1]+' '
	print output
