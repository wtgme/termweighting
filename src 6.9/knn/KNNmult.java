package knn;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class KNNmult {
	// training set
	private ArrayList<Map<Integer, Double>> trainDoc;
	// private ArrayList<ArrayList<Integer>> trainRawDoc;
	// document label
	private ArrayList<Integer> documentLabel;
	private ArrayList<Integer> documentID;
	// training document down vals in Cosin similarity
	private ArrayList<Double> trainDocVals;
	// word indexes in documents
	private Map<Integer, Set<Integer>> wordOccuDocs;
	// private Weights weights;
	private Integer K;
	public Map<Integer, String> voc;
//	public Map<Integer, Double> documentConfidence;

	/**
	 * @param trainFilePath
	 * @param weightFilePath
	 * @param k
	 */
	public KNNmult(int k) {
		K = k;
	}

	public void intialKNN() {
		trainDoc = new ArrayList<Map<Integer, Double>>();
		// trainRawDoc = new ArrayList<ArrayList<Integer>>();
		documentLabel = new ArrayList<Integer>();
		documentID = new ArrayList<Integer>();
		trainDocVals = new ArrayList<Double>();
		wordOccuDocs = new HashMap<Integer, Set<Integer>>();
		voc = new HashMap<Integer, String>();
//		documentConfidence = new HashMap<Integer, Double>();
		/*
		 * binary, tf or others
		 */
		// weights = new Weights(weightFilePath);

	}

	public void readVoc(String filePath) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line;
		while ((line = fin.readLine()) != null) {
			String[] w_v = line.split("\t");
			voc.put(Integer.valueOf(w_v[1]), w_v[0]);
		}
		fin.close();
	}

	public void readConfidenceFile(String filePath) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line;

		while ((line = fin.readLine()) != null) {
			String[] w_v = line.split("\t");
			// String[] cs = w_v[1].split(" ");
			// for (String s : cs) {
			// String[] c_c = s.split(":");
			// class_confi
			// .put(Integer.valueOf(c_c[0]), Double.valueOf(c_c[1]));
			// }

//			documentConfidence.put(Integer.valueOf(w_v[0]),
//					Double.valueOf(w_v[1]));
		}
		fin.close();
	}

	/**
	 * @param filePath
	 */
	public void readTrainFile(String filePath) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line = null;
		while ((line = fin.readLine()) != null) {
			String[] labelDoc = line.split("\t");
			Integer label = Integer.parseInt(labelDoc[2]);
			documentLabel.add(label);
			documentID.add(Integer.parseInt(labelDoc[0]));

			// ArrayList<Integer> rawDoc = new ArrayList<Integer>();

			int index = documentLabel.size() - 1;
			String[] words = labelDoc[1].split(" ");
			Map<Integer, Double> word_value = new HashMap<Integer, Double>();
			Double trainDocVal = 0.0;
			for (String wordS : words) {
				String[] w_v = wordS.split(":");
				Integer word = Integer.valueOf(w_v[0]);
				double weight = Double.valueOf(w_v[1]);
				// Integer tf = 1;
				Set<Integer> docIDs = wordOccuDocs.get(word);
				if (docIDs == null) {
					docIDs = new HashSet<Integer>();
				}
				docIDs.add(index);
				wordOccuDocs.put(word, docIDs);

				/*
				 * for other schemes
				 */

				Double value = weight;
				word_value.put(word, value);
				trainDocVal += value * value;
				// rawDoc.add(word);

			}
			trainDocVals.add(trainDocVal);
			trainDoc.add(word_value);
			// trainRawDoc.add(rawDoc);
		}
		fin.close();
	}

	/**
	 * @param document
	 * @return
	 */
	public String prediction(String document) {
		// test document
		String[] words = document.split(" ");
		Map<Integer, Double> docVector = new HashMap<Integer, Double>();
		List<Integer> tokens = new ArrayList<Integer>();
		for (String word : words) {
			String[] w_v = word.split(":");
			Integer w = Integer.valueOf(w_v[0]);
			Double v = Double.valueOf(w_v[1]);
			docVector.put(w, v);
			tokens.add(w);
		}
		// test document downVal in similarity function
		Double testVal = 0.0;
		Set<Integer> docIDs = new HashSet<Integer>();
		for (Integer word : tokens) {

			// potential documents
			Set<Integer> doc_ids = wordOccuDocs.get(word);
			if (doc_ids != null) {
				docIDs.addAll(doc_ids);
			}
			Double val = docVector.get(word);
			if (val != null) {
				testVal += val * val;
			}
		}

		Integer[] ndoc = new Integer[K];
		Double[] nsim = new Double[K];
		for (Iterator<Integer> iteId = docIDs.iterator(); iteId.hasNext();) {

			Integer i = iteId.next();

			Double trainVal = trainDocVals.get(i);
			Map<Integer, Double> train_doc = trainDoc.get(i);
			
			Double simValU = 0.0;
			for (Integer word : tokens) {
				Double word_value = train_doc.get(word);
				if (word_value == null) {
					continue;
				} else {
					simValU += word_value * docVector.get(word);
				}
			}
			double down = Math.sqrt(trainVal * testVal);
			
//			Set<Integer> keys = new HashSet<Integer>();
//			Set<Integer> key1 = docVector.keySet();
//			Set<Integer> key2 = train_doc.keySet();
//			for(Integer k : key1){
//				keys.add(k);
//			}
//			for(Integer k : key2){
//				keys.add(k);
//			}
//			Double dis = 0.0;
//			for(Integer word : keys){
//				Double b1 = train_doc.get(word);
//				if(b1 == null){
//					b1 = 0.0;
//				}
//				Double b2 = docVector.get(word);
//				if(b2 == null){
//					b2 = 0.0;
//				}
//				dis += Math.pow((b1-b2), 2);
//			}
//			dis = Math.sqrt(dis);
			

//			Double confidence = this.documentConfidence.get(documentID.get(i));
			// Integer lable = documentLabel.get(i);
			// Double confidence = class_confidence.get(lable);

			// System.out.println(documentID.get(i) + "\t" +
			// documentLabel.get(i)
			// + "\t" + confidence);

			if (down == 0) {
				simValU = 0.0;
			} else {
				simValU = (simValU / down);
				// System.out.println(simValU);
			}
			
			topK(ndoc, nsim, i, simValU);
		}
		StringBuffer sf = new StringBuffer();
		for (int ii = 0; ii < K; ii++) {
			if (false == appendResults(ndoc, nsim, sf, ii)) {
				break;
			}
		}
		nsim = null;
		ndoc = null;
		return sf.toString();
	}
	private boolean appendResults(Integer[] ndoc, Double[] nsim,
			StringBuffer sf, int ii) {
		if (ndoc[ii] != null) {

			Map<Integer, Double> doc = trainDoc.get(ndoc[ii]);
			Set<Integer> keySet = doc.keySet();
			for (Integer key : keySet) {

				sf.append(this.voc.get(key) + " ");

				// sf.append(this.voc.get(key) + ":" + doc.get(key) + " ");
			}

			// sf.append(":" + documentLabel.get(ndoc[ii]));

//			Double confidence = this.documentConfidence.get(documentID
//					.get(ndoc[ii]));
			Integer lable = documentLabel.get(ndoc[ii]);
			// Double confidence = class_confidence.get(lable);

			sf.append(":" + documentID.get(ndoc[ii]) + ":" + lable 
//					+ ":"+ confidence
					);

			sf.append(":");
			sf.append(nsim[ii]);
			sf.append("\t");
			return true;
		} else {
			return false;
		}
	}

	private void topK(Integer[] ndoc, Double[] nsim, Integer i, Double simValU) {
		for (int ii = 0; ii < K; ii++) {
			if (nsim[ii] == null || simValU > nsim[ii]) {
				for (int iii = K - 1; iii > ii; iii--) {
					nsim[iii] = nsim[iii - 1];
					ndoc[iii] = ndoc[iii - 1];
				}
				nsim[ii] = simValU;
				ndoc[ii] = i;
				break;
			}
		}
	}

	/**
	 * @param doc
	 * @return
	 */
	public Map<Integer, Integer> testDocVectorize(String doc) {
		String[] words = doc.split(" ");
		Map<Integer, Integer> tf = new HashMap<Integer, Integer>();
		for (String word : words) {
			String[] w_v = word.split(":");
			Integer w = Integer.valueOf(w_v[0]);
			Integer v = Integer.valueOf(w_v[1]);
			tf.put(w, v);
		}
		return tf;
		// return binary.getBinary(words);
	}

	int index = 0;

	class SThread extends Thread {

		FileReaderInterface fin;
		FileWriterInterface fout;

		public SThread(FileReaderInterface fin, FileWriterInterface fout) {
			this.fin = fin;
			this.fout = fout;
		}

		@Override
		public void run() {
			String line = null;
			while ((line = fin.readLine()) != null) {
				// System.out.println(index++);
				String[] tokens = line.split("\t");
				String[] words = tokens[1].split(" ");
				StringBuffer sb = new StringBuffer();
				for (String word : words) {
					String[] w_v = word.split(":");
					// sb.append(voc.get(Integer.valueOf(w_v[0])) + ":" + w_v[1]
					// + " ");
					sb.append(voc.get(Integer.valueOf(w_v[0])) + " ");
				}
				// System.out.println(sb.toString());
				/*
				 * format: testDocID:testDocLabel testRawText
				 * trainText:TrainDocLabel:similarity
				 */
				fout.append(tokens[0] + ":" + tokens[2] + ":" + sb.toString()
						+ "\t" + prediction(tokens[1]) + "\n");

			}
		}
	}

	public void runKNN(String filePath, String trainFilePath,
			String confiFilePath, String threadNum, String testFilePath,
			String predictionFilePath) throws Exception {

		readTrainFile(trainFilePath);
		readVoc(filePath + "/vocabulary.txt");
//		readConfidenceFile(confiFilePath);

		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(testFilePath);
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(predictionFilePath);

		Thread[] tds = new SThread[Integer.valueOf(threadNum)];
		for (int i = 0; i < tds.length; i++) {
			tds[i] = new SThread(fin, fout);
			tds[i].start();
			// System.out.println(i);
		}
		for (int i = 0; i < tds.length; i++) {
			tds[i].join();
		}

		fin.close();
		fout.flush();
		fout.close();
	}
	// public static void main(String[] args)
	public void runClassifier(String filePath, String i) {

		System.out.println(filePath);

		String trainFile = i + "train";
		String testFile = i + "test";
		String confiFile = i + "confidbdc.txt";

		File fileDir = new File(filePath + "/weights");
		File[] files = fileDir.listFiles();
		for (File file : files) {
			try {
				String weightFilePath = file.getName();
				System.out.println(weightFilePath);
				Long begin = System.currentTimeMillis();
				intialKNN();
				runKNN(filePath, filePath + "/knndata/" + trainFile
						+ weightFilePath, filePath + "/knndata/" + confiFile,
						"4",
						filePath + "/knndata/" + testFile + weightFilePath,
						filePath + "/prediction/" + i + weightFilePath);
				index = 0;
				System.out.println((System.currentTimeMillis() - begin) / 1000);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

	}
	public void runClassifier(String filePath, String[] fileNames) {

		System.out.println(filePath);

		for (String weightFilePath : fileNames) {
			try {
				System.out.println(weightFilePath);
				Long begin = System.currentTimeMillis();
				intialKNN();
				runKNN(filePath, filePath + "/knndata/train" + weightFilePath,
						filePath + "/knndata/confid" + weightFilePath, "1",
						filePath + "/knndata/test" + weightFilePath, filePath
								+ "/prediction/" + weightFilePath);
				index = 0;
				System.out.println((System.currentTimeMillis() - begin) / 1000);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}
