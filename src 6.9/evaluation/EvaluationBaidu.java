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

public class EvaluationBaidu {
	private Map<String, Integer> trueLabel;
	private Map<String, List<Integer>> predictions;
	private ArrayList<Integer> trainDoc;
	private Metrics metric;
	private int K;

	public EvaluationBaidu(int k) {
		trueLabel = new HashMap<String, Integer>();
		trainDoc = new ArrayList<Integer>();
		this.K = k;
	}

	public void readTrueLabel(String filePath, String trainFilePath) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line = "";
		while ((line = fin.readLine()) != null) {
			String[] parts = line.split("\t");
			trueLabel.put(parts[0], Integer.valueOf(parts[2]));
		}

		fin.readFile(trainFilePath);
		while ((line = fin.readLine()) != null) {
			String[] parts = line.split("\t");
			// System.out.println(parts[2]);
			trainDoc.add(Integer.valueOf(parts[2]));
		}

		fin.close();
	}

	public void readPrediction(String filePath) {
		predictions = new HashMap<String, List<Integer>>();
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line = "";

		while ((line = fin.readLine()) != null) {
			String[] parts = line.split("\t");
			ArrayList<Integer> prediction = new ArrayList<Integer>();
			// ArrayList<Integer> classLabel = new ArrayList<Integer>();
			// ArrayList<Double> similar = new ArrayList<Double>();
			int[] classLabel = new int[K];
			double[] similar = new double[K];
			int length = parts.length;
			for (int i = 1; i < length; i++) {
				String nerbor = parts[i];
				if (!nerbor.equals("null:null")) {
					try {
						String[] doc_class_sim = nerbor.split(":");
						// classLabel[i - 1] =
						// (Integer.valueOf(doc_class_sim[1]));
						classLabel[i - 1] = trainDoc.get(Integer
								.valueOf(doc_class_sim[0]));
						similar[i - 1] = (Double.valueOf(doc_class_sim[1]));
					} catch (Exception e) {
						System.out.println(nerbor);
						e.printStackTrace();
					}
				}
			}
			double[] simis = new double[34];
			for (int i = 0; i < K; i++) {
				simis[classLabel[i]] += similar[i];

				double maxValue = 0;
				int maxClass = 0;
				for (int ii = 1; ii < 34; ii++) {
					if (maxValue < simis[ii]) {
						maxValue = simis[ii];
						maxClass = ii;
					}
				}
				if (maxClass == 0) {
					maxClass = (int) (Math.random() * 33 + 1);
				}

				prediction.add(maxClass);
			}
			predictions.put(parts[0], prediction);
		}
		fin.close();
	}

	public void evaluation(String filePath) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath);
		int n = K;
		StringBuffer macrof1s = new StringBuffer();
		StringBuffer microf1s = new StringBuffer();
		StringBuffer recall = new StringBuffer();
		macrof1s.append("MacroF1:" + "\n");
		microf1s.append("MicroF1:" + "\n");
		recall.append("Recall:\n");

		for (int i = 0; i < n; i++) {
			metric = new Metrics(33);
			Set<String> keySet = trueLabel.keySet();
			for (Iterator<String> ite = keySet.iterator(); ite.hasNext();) {

				String testDoc = ite.next();
				try {
					Integer trueClass = trueLabel.get(testDoc);
					Integer predictionClass = predictions.get(testDoc).get(i);
					// if (predictionClass == null)
					// {
					// break;
					// }
					// else
					// {
					metric.setMatrix(trueClass, predictionClass);
					// }
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(testDoc);
					System.exit(0);
				}
			}
			metric.calculation();

			macrof1s.append(metric.getMacroF1() + "\t");
			microf1s.append(metric.getMicroF1() + "\t");
			recall.append(metric.getRecall() + "\t");

		}
		fout.append(macrof1s.toString() + "\n");
		fout.append(microf1s.toString() + "\n");
		fout.append(recall.toString() + "\n");
		fout.flush();
		fout.close();
		predictions = null;
		metric = null;
	}

	public static void main(String[] args)
	// public void runEvaluation(String filePath)
	{
		// String filePath = "corpus" + File.separator + 0;
		String filePath = "";
		EvaluationBaidu evaluation = new EvaluationBaidu(50);
		evaluation.readTrueLabel("corpus/0/data" + File.separator
				+ "class_test.txt", "corpus/0/data" + File.separator
				+ "class_train.txt");
		String[] weightFilePaths = {"binary.txt", "tf.txt", "tfidf.txt",
				"tfig.txt", "tfchi.txt", "tfrf.txt", "iqfqficf.txt",
				"tfdc.txt", "tfbdc.txt", "tfsdc.txt", "tfbsdc.txt",
				"tfbdctp.txt", "baiduE.txt"};
		int length = weightFilePaths.length;
		for (int i = length - 1; i < length; i++) {
			System.out.println(weightFilePaths[i]);
			evaluation.readPrediction("corpus/0/prediction" + File.separator
					+ weightFilePaths[i]);
			evaluation.evaluation("corpus/0/evaluation" + File.separator
					+ weightFilePaths[i]);
		}
	}
}
