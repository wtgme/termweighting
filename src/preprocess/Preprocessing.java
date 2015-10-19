package preprocess;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

/*
 * Stemming words
 * removing stopword
 * removing url and email
 */
public class Preprocessing {
	private PorterStemmer pStemmer;
	private Removing removing;
	private StopWord stopWord;
	private int index;

	// private Map<String, Integer> vocabulary;

	public Preprocessing() {
		this.pStemmer = new PorterStemmer();
		this.removing = new Removing();
		this.stopWord = new StopWord();
		index = 1;
		// vocabulary = new HashMap<String, Integer>();
	}

	public String preprocess(String document) {
		document = document.toLowerCase();
		String remPunctuation = removing.punctuation(document);

		StringBuffer stermming = new StringBuffer();
		String[] tokens = remPunctuation.split(" ");
		for (String token : tokens) {
			if (removing.hasNum(token) || removing.isEmail(token)
					|| removing.isHttp(token) || stopWord.isStopWord(token)) {
				continue;
			} else {
				String stem = pStemmer.stemming(token);
				if (stopWord.isStopWord(stem)) {
					continue;
				} else {
					// Integer wordId = vocabulary.get(stem);
					// if (wordId == null) {
					// wordId = vocabulary.size() + 1;
					// vocabulary.put(stem, wordId);
					// }

					stermming.append(stem + " ");
				}
			}
		}

		return stermming.toString().trim();
	}

	public void processFile(String filePath, String outPutPath) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line = "";
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(outPutPath);
		while ((line = fin.readLine()) != null) {
			// System.out.println(line);
			String[] content_cate = line.split("\t");
			if (content_cate.length == 2) {
				String proprocess = preprocess(content_cate[1]);
				if (!"".equals(proprocess)) {
					fout.append(content_cate[0] + "\t" + proprocess + "\n");
				}
			}
		}
		fin.close();
		fout.flush();
		fout.close();
	}

	// public int vocSize() {
	// return this.vocabulary.size();
	// }
	//
	// public void outputVoca(String filePath) {
	// FileWriterInterface fout = new FileWriterInterface();
	// fout.writeFile(filePath);
	// Set<String> keySet = this.vocabulary.keySet();
	// for (Iterator<String> ite = keySet.iterator(); ite.hasNext();) {
	// String key = ite.next();
	// Integer value = this.vocabulary.get(key);
	// fout.append(key + "\t" + value + "\n");
	// }
	// fout.flush();
	// fout.close();
	// }

	public void run(String dataset) {
		processFile(dataset + "/train", dataset + "/trainPro");
		processFile(dataset + "/test", dataset + "/testPro");
	}

	public static void main(String[] args) {
		Preprocessing pf = new Preprocessing();
		// pf.processFile("newsgroup/newsgroup_test.txt",
		// "newsgroup/data/test.txt");
		// pf.processFile("newsgroup/newsgroup_train.txt",
		// "newsgroup/data/train.txt");
		pf.processFile("reuters/reuters_test.txt", "reuters/data/test.txt");
		pf.processFile("reuters/reuters_train.txt", "reuters/data/train.txt");
		// pf.outputVoca("reuters/data/vocabulary.txt");
		// System.out.println(pf.vocSize());
	}
}
