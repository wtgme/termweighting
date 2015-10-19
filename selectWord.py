import sys

voc = {}
fo = open(sys.argv[1],'r')
for line in fo.readlines():
	token_id = line.strip().split('\t')
	voc[int(token_id[1])] = token_id[0]
fo.close()

fo = open(sys.argv[2],'r')
for line in fo.readlines():
	token_weight = line.strip().split('\t')
	print voc.get(int(token_weight[0])) + '\t'+token_weight[1]

fo.close()
