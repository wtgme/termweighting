package data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import evaluation.Metrics;
import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class CateEvaluation {
	private Map<String, Integer> trueLabel;
	private Map<String, List<Integer>> predictions;
	private Metrics metric;
	private int K;
	private int C;
	private ArrayList<StringBuffer> f1s;

	private ArrayList<String> schemes;
	private int index;

	public CateEvaluation(int k, int c) {
		trueLabel = new HashMap<String, Integer>();
		this.K = k;
		this.C = c;
		f1s = new ArrayList<StringBuffer>();
		schemes = new ArrayList<String>();
		index = 0;
	}

	public void readTrueLabel(String filePath) {
		/*
		 * test Document ID:label
		 */
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line = "";
		while ((line = fin.readLine()) != null) {
			String[] parts = line.split("\t");
			trueLabel.put(parts[0], Integer.valueOf(parts[2]));
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
			// System.out.println(parts[0]);
			ArrayList<Integer> prediction = new ArrayList<Integer>();
			int[] classLabel = new int[K];
			double[] similar = new double[K];
			int len = parts.length;
			for (int i = 1; i < len; i++) {
				String nerbor = parts[i];
				try {
					String[] doc_class_sim = nerbor.split(":");
					classLabel[i - 1] = (Integer.valueOf(doc_class_sim[1]));
					similar[i - 1] = (Double.valueOf(doc_class_sim[2]));
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

	public void evaluation(String scheme, int i) {
		int n = K;
		schemes.add(scheme);
		index++;
		StringBuffer cateF1 = new StringBuffer();

		// for (int i = 0; i < n; i++)
		{
			metric = new Metrics(C);
			Set<String> keySet = predictions.keySet();
			for (Iterator<String> ite = keySet.iterator(); ite.hasNext();) {

				String testDoc = ite.next();
				try {
					Integer trueClass = trueLabel.get(testDoc);
					Integer predictionClass = predictions.get(testDoc).get(i);
					metric.setMatrix(trueClass, predictionClass);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
			metric.calculation();

			double[] fs = metric.getF1();
			for (double f : fs) {
				cateF1.append(f + "\t");
			}

		}

		// String value = macrof1.toString() + "\n" + microf1.toString() + "\n"
		// + precision.toString() + "\n" + recall.toString() + "\n";
		f1s.add(cateF1);
		metric = null;
		// return value;
	}

	public void outPut(String filePath) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath + "/evaluation/cate_evaluation.txt");
		fout.append("CateF1\n");
		for (int i = 0; i < index; i++) {
			fout.append(this.schemes.get(i) + "\t" + this.f1s.get(i) + "\n");
		}

		fout.flush();
		fout.close();
	}

	// public void run(String filePath)
	public static void main(String[] args) {
		String filePath = "dataSets/snippets";
		int C = 8;

		CateEvaluation ce = new CateEvaluation(50, C);
		ce.readTrueLabel(filePath + "/test.txt");
		File fileDir = new File(filePath + "/weights");

		File[] files = fileDir.listFiles();

		for (File file : files) {
			String weightFilePath = file.getName();
			// System.out.println(weightFilePath);
			ce.readPrediction(filePath + "/prediction/" + weightFilePath);

			ce.evaluation(weightFilePath, 15);
		}
		// transform(filePath + "/evaluation/evaluation.txt", filePath
		// + "/evaluation/" + filePath + ".txt");
		ce.outPut(filePath);
	}
}
