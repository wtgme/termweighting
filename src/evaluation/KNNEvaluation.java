package evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class KNNEvaluation {
	private Map<String, Integer> trueLabel;
	private Map<String, List<Integer>> predictions;
	private Metrics metric;
	private int K;
	private int C;
	private ArrayList<StringBuffer> macrof1s;
	private ArrayList<StringBuffer> microf1s;
	private ArrayList<StringBuffer> precisions;
	private ArrayList<StringBuffer> recalls;
	private ArrayList<String> schemes;
	private int index;
	private int r;
	private int n;

	public KNNEvaluation(int k, int c) {

		this.K = k;
		this.C = c;
		r = 0;
		n = 0;
		macrof1s = new ArrayList<StringBuffer>();
		microf1s = new ArrayList<StringBuffer>();
		precisions = new ArrayList<StringBuffer>();
		recalls = new ArrayList<StringBuffer>();
		schemes = new ArrayList<String>();
		index = 0;
	}

	// public void readTrueLabel(String filePath) {
	// /*
	// * test Document ID:label
	// */
	// FileReaderInterface fin = new FileReaderInterface();
	// fin.readFile(filePath);
	// String line = "";
	// while ((line = fin.readLine()) != null) {
	// String[] parts = line.split("\t");
	// trueLabel.put(parts[0], Integer.valueOf(parts[2]));
	// }
	// fin.close();
	// }

	public void readPrediction(String filePath) {
		trueLabel = new HashMap<String, Integer>();
		predictions = new HashMap<String, List<Integer>>();
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line = "";

		while ((line = fin.readLine()) != null) {
			String[] parts = line.split("\t");
			// System.out.println(parts[0]);

			String testDoc = parts[0];
			String[] test_label = testDoc.split(":");
			trueLabel.put(test_label[0], Integer.valueOf(test_label[1]));

			ArrayList<Integer> prediction = new ArrayList<Integer>();
			int[] classLabel = new int[K];
			double[] similar = new double[K];
			int len = parts.length;
			for (int i = 1; i < len; i++) {
				String nerbor = parts[i];
				try {
					String[] doc_class_sim = nerbor.split(":");
					classLabel[i - 1] = (Integer.valueOf(doc_class_sim[2]));
					// Doc:ID:label:confidence:simlairy
					similar[i - 1] = (Double.valueOf(doc_class_sim[3])
					// * Double.valueOf(doc_class_sim[4])
					);
				} catch (Exception e) {
					System.out.println(nerbor);
					e.printStackTrace();
				}
			}
			double[] simis = new double[C + 1];
			for (int i = 0; i < K; i++) {
				simis[classLabel[i]] += similar[i];

				double maxValue = 0;
				int maxClass = 0;
				for (int ii = 1; ii <= C; ii++) {
					if (maxValue < simis[ii]) {
						maxValue = simis[ii];
						maxClass = ii;
					}
				}
				if (maxClass == 0) {
					maxClass = (int) (Math.random() * C + 1);
				}

				prediction.add(maxClass);
			}
			String testID = parts[0].split(":")[0];
			predictions.put(testID, prediction);
		}
		fin.close();
	}

	public void evaluation(String scheme) {
		schemes.add(scheme);
		index++;
		StringBuffer macrof1 = new StringBuffer();
		StringBuffer microf1 = new StringBuffer();
		StringBuffer recall = new StringBuffer();
		StringBuffer precision = new StringBuffer();
		// macrof1.append("MacroF1:" + "\n");
		// microf1.append("MicroF1:" + "\n");
		// precision.append("Precision:\n");
		// recall.append("Recall:\n");
		// precisioncate.append("precisionCate:\n");
		// recallcate.append("recallCate:\n");

		for (int i = 0; i < K; i++) {

			metric = new Metrics(C);
			Set<String> keySet = predictions.keySet();
			for (Iterator<String> ite = keySet.iterator(); ite.hasNext();) {

				String testDoc = ite.next();
				try {
					int trueClass = trueLabel.get(testDoc);
					int predictionClass = predictions.get(testDoc).get(i);
					// System.out.println(trueClass + "\t" + predictionClass);
					if (trueClass == predictionClass) {
						r++;
					}
					n++;
					metric.setMatrix(trueClass, predictionClass);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
			metric.calculation();

			macrof1.append(metric.getMacroF1() + "\t");
			microf1.append(metric.getMicroF1() + "\t");
			precision.append(metric.getPrecision() + "\t");
			recall.append(metric.getRecall() + "\t");
			// precisioncate.append(metric.getCatePrecision(9) + "\t");
			// recallcate.append(metric.getCateRecall(9) + "\t");
			metric = null;
		}

		// String value = macrof1.toString() + "\n" + microf1.toString() + "\n"
		// + precision.toString() + "\n" + recall.toString() + "\n";
		macrof1s.add(macrof1);
		microf1s.add(microf1);
		precisions.add(precision);
		recalls.add(recall);
		// metric = null;
		System.out.println(r + "\t" + n + "\t" + (double) r / n);
		r = 0;
		n = 0;
		// return value;
	}

	public void outPut(String filePath, String in) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath + "/evaluation/" + in + "evaluation.txt");

		fout.append("MicroF1\n");
		for (int i = 0; i < index; i++) {
			fout.append(this.schemes.get(i) + "\t" + this.microf1s.get(i)
					+ "\n");
		}

		fout.append("MacroF1\n");
		for (int i = 0; i < index; i++) {
			fout.append(this.schemes.get(i) + "\t" + this.macrof1s.get(i)
					+ "\n");
		}
		fout.append("Precision\n");
		for (int i = 0; i < index; i++) {
			fout.append(this.schemes.get(i) + "\t" + this.precisions.get(i)
					+ "\n");
		}
		fout.append("Recall\n");
		for (int i = 0; i < index; i++) {
			fout.append(this.schemes.get(i) + "\t" + this.recalls.get(i) + "\n");
		}
		fout.flush();
		fout.close();
	}

	public void run(String filePath, String i)
	// public static void main(String[] args)
	{
		// readTrueLabel(filePath + "/test.txt");
		File fileDir = new File(filePath + "/weights");

		File[] files = fileDir.listFiles();

		String[] lists = {"tf", "tfidf", "tfnidf", "tfchi", "tfig", "eccd",
				"tfrf", "tfnrf", "iqfqficf", "dc", "bdc", "tfen", "tfben",
				"tflapsen", "tfjmsen", "tfdirsen", "tfadsen", "tftssen",
				"dctp", "bdctp", "tfentp", "tfbentp", "bsdc", "tfbsen",
				"bsdctp", "tfbsentp"};
		for (String scheme : lists) {
			readPrediction(filePath + "/prediction/" + i + scheme + ".txt");

			evaluation(scheme);
		}

		// for (File file : files) {
		// String weightFilePath = file.getName();
		// System.out.println(weightFilePath);
		// readPrediction(filePath + "/prediction/" + i + weightFilePath);
		//
		// evaluation(weightFilePath);
		// }
		// transform(filePath + "/evaluation/evaluation.txt", filePath
		// + "/evaluation/" + filePath + ".txt");
		outPut(filePath, i);
	}
}
