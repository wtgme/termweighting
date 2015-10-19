
def readTest(fileName):
	arr = []

	fo = open(fileName, 'r')
	for line in fo.readlines():
		tokens = line.split(' ')
		arr.append(int(tokens[0]))

	fo.close()
	return arr


def readPredict(fileName):
	arr = []

	fo = open(fileName, 'r')
	for line in fo.readlines():
		arr.append(int(line))

	fo.close()
	return arr


def flag(test, prediction):
	la = len(test)
	print len(test), len(prediction)
	flags = []
	for index in xrange(la):
		if test[index] == prediction[index]:
			flags.append(1)
		else:
			flags.append(0)
	return flag


ta = readTest('dataSets/r8/svm/data/testtfbentp.txt')
tb = readTest('dataSets/r8/svm/data/testtfrf.txt')
pa = readPredict('dataSets/r8/svm/prediction/tfbentp.txt')
pb = readPredict('dataSets/r8/svm/prediction/tfrf.txt')

fa = flag(ta, pa)
fb = flag(tb, pb)

n00, n01, n10, n11 = 0, 0, 0, 0

length = len(fa)
for index in xrange(length):
	a, b= fa[index], fb[index]
	
	if a == 0 and b == 0:
		n00 += 1
	elif a == 0 and b == 1:
		n01 += 1
	elif a == 1 and b == 0:
		n10 += 1
	else:
		n11 += 1
print n00, n01, n10, n11
chi = (float)(abs(n01-n10)-1)*(abs(n01-n10)-1)/(n01 + n10)

print chi
