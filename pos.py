import sys
import pickle
import sys 
import jieba
old_stdin=sys.stdin
old_stdout=sys.stdout
old_stderr=sys.stderr
reload(sys) 
sys.setdefaultencoding('utf8') 
sys.stdin=old_stdin
sys.stdout=old_stdout
sys.stderr=old_stderr



fi = open(r'class_train.txt','r',encoding='utf8').readlines()
fo = open(r'class_test.txt','w',encoding='utf-8')
l_m={}
for l in fi:
	ls=l.strip().split('\t')
	seg_list = jieba.cut(ls[0])  # 默认是精确模式
	string = " ".join(seg_list)
	fo.write(ls[0]+'\t'+string+'\t'+ls[2])
fi.close()
fo.close()
