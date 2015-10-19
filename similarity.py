import math
import sys

def RA(fileName):
	id_content, id_label, id_down, class_id, doc_simi = {},{},{},{},{}
	length = 0

	#read file
	fo = open(fileName,'r')
	for line in fo.readlines():
		length += 1
		parts = line.split("\t")
		tokens = parts[1].split(" ")
	
		token_value = {}
		norm = 0
		for token in tokens:
			t_v = token.split(":")
			#print token
			token_value[t_v[0]] = float(t_v[1])
			norm += pow(float(t_v[1]),2)
		norm = math.sqrt(norm)

		id_content[int(parts[0])] = token_value
		id_label[int(parts[0])] = int(parts[2])
		id_down[int(parts[0])] = norm

		doclist = class_id.get(int(parts[2]),[])
		doclist.append(int(parts[0]))
		class_id[int(parts[2])] = doclist

	fo.close()

	#calculate simility
	for i in id_content.keys():
		for j in id_content.keys():
			#print i
			if j > i:
				token_value1 = id_content.get(i)
				token_value2 = id_content.get(j)
				docSum = 0
				for t in token_value1:
					v1 = token_value1.get(t)
					if t in token_value2:
						v2 = token_value2.get(t)
						docSum += v1*v2

				simility = doc_simi.get(i,{})
				simility[j] = docSum/(id_down.get(i)*id_down.get(j))
				doc_simi[i] = simility


	#calculate intra and inter distance
	intra_cate_distance, inter_cate_distance = 0,0
	for i in class_id.keys():
		for j in class_id.keys():
			if j>i:
				doclist1 = class_id.get(i)
				doclist2 = class_id.get(j)
	
				simsum = 0
				for doc1 in doclist1:
					for doc2 in doclist2:
						firstid = min(doc1,doc2)
						lastid = doc1+doc2-firstid
						sim = doc_simi.get(firstid).get(lastid)
						simsum += sim
				simsum /= len(doclist1)*len(doclist2)
	
				intra_cate_distance += simsum

	intra_cate_distance /= len(class_id)*(len(class_id)-1)/2

	for i in xrange(1,len(class_id)+1):
		doclist = class_id.get(i)

		simsum = 0
		for d1id in xrange(len(doclist)):
			for d2id in xrange(d1id+1,len(doclist)):
				d1 = doclist[d1id]
				d2 = doclist[d2id]
				firstid = min(d1,d2)
				lastid = d1+d2-firstid
				sim = doc_simi.get(firstid).get(lastid)
				simsum += sim
		simsum /= len(doclist)*(len(doclist)-1)/2
		inter_cate_distance += simsum

	inter_cate_distance /= len(class_id)

	#print fileName
	print "inter_cate_distance:\t%f" %(intra_cate_distance)
	print "intra_cate_distance: \t%f" %(inter_cate_distance)
	print "AR: %f" %(inter_cate_distance/intra_cate_distance)


print "%s" %(sys.argv[1])

lists = ["tf","tfidf","tfchi","tfig","tfrf","iqfqficf","eccd","dc","bdc","tfen","tfben","dctp","tfentp"]

for name in lists:
	print name
	RA(sys.argv[1]+"/test"+name+".txt")
	


