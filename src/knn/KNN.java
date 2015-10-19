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

public class KNN {
	// training set
	private ArrayList<Map<Integer, Double>> trainDoc;
	private ArrayList<Integer> trainRawDoc;
	// document label
	private ArrayList<Integer> documentLabel;
	// training document down vals in Cosin similarity
	private ArrayList<Double> trainDocVals;
	// word indexes in documents
	private Map<Integer, Set<Integer>> wordOccuDocs;
	// private Weights weights;
	private Integer K;
	public Map<Integer, String> voc;

	/**
	 * @param trainFilePath
	 * @param weightFilePath
	 * @param k
	 */
	public KNN(int k) {
		K = k;
		trainDoc = new ArrayList<Map<Integer, Double>>();
		trainRawDoc = new ArrayList<Integer>();
		documentLabel = new ArrayList<Integer>();
		trainDocVals = new ArrayList<Double>();
		wordOccuDocs = new HashMap<Integer, Set<Integer>>();
		voc = new HashMap<Integer, String>();
		/*
		 * binary, tf or others
		 */
		// weights = new Weights(weightFilePath);

	}

	public void readVoc(String filePath) {
		FileReaderInterface fin= new FileReaderInterface();
		fin.readFile(filePath);
		String line;
		while ((line = fin.readLine()) != null) {
			String[] w_v = line.split("\t");
			voc.put(Integer.valueOf(w_v[1]), w_v[0]);
		}
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
			trainRawDoc.add(Integer.valueOf(labelDoc[0]));

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

			}
			trainDocVals.add(trainDocVal);
			trainDoc.add(word_value);
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

			if (down == 0) {
				simValU = 0.0;
			} else {
				simValU /= down;
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
			// sf.append(trainRawDoc.get(ndoc[ii]));
			// sf.append(ndoc[ii]);
			// sf.append(':');

			Map<Integer, Double> doc = trainDoc.get(ndoc[ii]);
			Set<Integer> keySet = doc.keySet();
			for (Integer key : keySet) {
				sf.append(this.voc.get(key) + " ");
			}
			sf.append(":" + documentLabel.get(ndoc[ii]));
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

	public void runKNN(String filePath, String trainFilePath, String k,
			String testFilePath, String predictionFilePath) {
		readTrainFile(trainFilePath);
		readVoc(filePath + "/vocabulary.txt");
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(testFilePath);
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(predictionFilePath);

		String line;
		while ((line = fin.readLine()) != null) {
			String[] tokens = line.split("\t");
			String[] words = tokens[1].split(" ");
			StringBuffer sb = new StringBuffer();
			for (String word : words) {
				// System.out.println(word);
				sb.append(voc.get(Integer.valueOf(word.split(":")[0])) + " ");
			}
			/*
			 * format: testDocID:testDocLabel testRawText
			 * trainText:TrainDocLabel:similarity
			 */
			fout.append(tokens[0] + ":" + tokens[2] + ":"
					+ sb.toString() + "\t" + prediction(tokens[1]) + "\n");
		}

		// while ((line = FileReaderInterface.readLine()) != null) {
		// String[] tokens = line.split("\t");
		// FileWriterInterface.append(tokens[0] + "\t" + prediction(tokens[1])
		// + "\n");
		// }

		fin.close();
		fout.flush();
		fout.close();
	}

	// public static void main(String[] args)
	public void runClassifier(String filePath) {

		System.out.println(filePath);

		Long begin = System.currentTimeMillis();
		File fileDir = new File(filePath + "/weights");
		File[] files = fileDir.listFiles();
		for (File file : files) {
			String weightFilePath = file.getName();
			System.out.println(weightFilePath);
			runKNN(filePath, filePath + "/knndata/train" + weightFilePath,
					"50", filePath + "/knndata/test" + weightFilePath, filePath
							+ "/prediction/" + weightFilePath);
		}
		System.out.println((System.currentTimeMillis() - begin) / 1000);
	}
}
