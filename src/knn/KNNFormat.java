package knn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

/*
 * get the weights from weighting files
 * prepare the number-version documents for KNN 
 */
public class KNNFormat {
	private Weights weights;
	// private Weights bdcWeights;
	private Boolean isiqf;

	public void setWeight(String weightFilePath, boolean isiqf) {
		weights = new Weights(weightFilePath);
		// bdcWeights = new Weights("dataSets/" + "r8" + "/weights/bdc.txt");
		this.isiqf = isiqf;
	}

	public void formatTranform(String filePath, String outputFile,
			String outConfidenceFile) throws IOException {

		// System.out.println(filePath);
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(outputFile);

		// BufferedWriter fout2;
		// fout2 = new BufferedWriter(new OutputStreamWriter(new
		// FileOutputStream(
		// outConfidenceFile), "UTF-8"));

		String line;
		while ((line = fin.readLine()) != null) {
			StringBuffer content = new StringBuffer();
			String[] tokens = line.split("\t");

			String index = tokens[0];
			Integer label = Integer.valueOf(tokens[2]);
			String[] words = tokens[1].split(" ");

			// get weight of a term in a given class
			int length = words.length;
			double[] weights = new double[length];
			double norm = 0.0;

			// get Document confidence in each class
			double sumConfidence = 0.0;
			double tfCount = 0.0;
			for (int i = 0; i < length; i++) {
				String word = words[i];
				String[] w_v = word.split(":");
				Integer w = Integer.valueOf(w_v[0]);
				Integer v = Integer.valueOf(w_v[1]);

				// get weight of a term in a given class
				Double weight = this.weights.getWeight(w, label);
				words[i] = w + "";
				if (isiqf == true) {
					v = 1;
				}
				// System.out.println(weight);
				weights[i] = v * weight;
				norm += Math.pow(weights[i], 2);
				// norm += weights[i];

				// get Confidence of a term in each class
				// Double confidence = this.bdcWeights.getMaxWeight(w) * v;
				// sumConfidence += confidence;
				tfCount += v;

				// Map<Integer, Double> map = this.weights.getWeightsIClass(w);
				// Set<Integer> keys = map.keySet();
				// for (Integer key : keys) {
				// Double value = map.get(key);
				// Double confi = value * v;
				// Double beforeConfi = class_confidence.get(key);
				// if (beforeConfi == null) {
				// beforeConfi = 0.0;
				// }
				// class_confidence.put(key, beforeConfi + confi);
				// sumConfidence += confi;
				// }
			}
			norm = Math.sqrt(norm);
			for (int i = 0; i < length; i++) {
				content.append(words[i] + ":" + (weights[i] / norm) + " ");
			}
			fout.append(index + "\t" + content.toString().trim() + "\t" + label
					+ "\n");

			// Set<Integer> keys = class_confidence.keySet();
			// // System.out.println(class_confidence);
			// StringBuffer confiString = new StringBuffer();
			// for (Integer key : keys) {
			// Double con = class_confidence.get(key);
			// confiString.append(key + ":" + (con / sumConfidence) + " ");
			// }
			// System.out.println(confiString);
			// fout2.append(index + "\t" + sumConfidence + "\n");

		}
		fin.close();
		fout.flush();
		fout.close();
		// fout2.flush();
		// fout2.close();
	}

	public void formatTestform(String filePath, String outputFile) {
		// System.out.println(filePath);
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(outputFile);
		String line;
		while ((line = fin.readLine()) != null) {
			StringBuffer content = new StringBuffer();
			String[] tokens = line.split("\t");

			String index = tokens[0];
			Integer label = Integer.valueOf(tokens[2]);
			String[] words = tokens[1].split(" ");

			int length = words.length;
			double[] weights = new double[length];
			double norm = 0.0;
			for (int i = 0; i < length; i++) {
				String word = words[i];
				String[] w_v = word.split(":");
				Integer w = Integer.valueOf(w_v[0]);
				Integer v = Integer.valueOf(w_v[1]);
				Double weight = this.weights.getMaxWeight(w);
				if (weight == null) {
					weight = this.weights.getAgaveWeight();
				}
				words[i] = w + "";
				if (isiqf == true) {
					v = 1;
				}
				weights[i] = v * weight;
				norm += Math.pow(weights[i], 2);
				// norm += weights[i];
			}
			norm = Math.sqrt(norm);
			for (int i = 0; i < length; i++) {
				content.append(words[i] + ":" + (weights[i] / norm) + " ");
			}
			fout.append(index + "\t" + content.toString().trim() + "\t" + label
					+ "\n");
		}
		fin.close();
		fout.flush();
		fout.close();
	}

	public void run(String dataset, String i) throws IOException
	// public static void main(String[] args)
	{
		// String filePath = "reuters";
		// String filePath = new String("dataSets/cade");
		// String filePath = new String("dataSets/r8");
		// String filePath = new String("dataSets/20ng");
		// String filePath = new String("dataSets/twitter");

		// System.out.println(dataset);

		// String[] weightFilePaths = {"tfidf.txt", "tfrf.txt", "iqfqficf.txt",
		// "tfsdc.txt", "tfdc.txt", "tfbdc.txt", "tfbsdc.txt", "tfig.txt",
		// "tfchi.txt", "tfbdctp.txt"};

		String trainFile = i + "train";
		String testFile = i + "test";
		String confiFile = i + "confidbdc.txt";

		File fileDir = new File(dataset + "/weights");
		File[] files = fileDir.listFiles();
		for (File file : files) {
			String weightFilePath = file.getName();

			// transform train files
			if (weightFilePath.contains("iqfqficf")
					|| weightFilePath.contains("dc"))
			// for iqfqficf and dc, there is no tf, just single factor
			{
				System.out.println("++++++++++++++++++" + weightFilePath);
				setWeight(dataset + "/weights/" + weightFilePath, true);
			} else {
				setWeight(dataset + "/weights/" + weightFilePath, false);
			}

			formatTranform(dataset + "/" + trainFile + ".txt", dataset
					+ "/knndata/" + trainFile + weightFilePath, dataset
					+ "/knndata/" + confiFile);

			// transform test files
			if (weightFilePath.equals("bdctp.txt"))
			// for tp, using bdc to replace the tpbdc, without tf
			{
				System.out.println("---------------------------"
						+ weightFilePath);
				setWeight(dataset + "/weights/bdc.txt", true);
			}
			if (weightFilePath.equals("dctp.txt"))
			// for tp, using bdc to replace the tpdc, without tf
			{
				System.out.println("---------------------------"
						+ weightFilePath);
				setWeight(dataset + "/weights/dc.txt", true);
			}
			if (weightFilePath.equals("tfbentp.txt"))
			// for tp, using bdc to replace the tpbdc, but with tf
			{
				System.out.println("---------------------------"
						+ weightFilePath);
				setWeight(dataset + "/weights/tfben.txt", false);
			}
			if (weightFilePath.equals("tfentp.txt"))
			// for tp, using bdc to replace the tpdc, but with tf
			{
				System.out.println("---------------------------"
						+ weightFilePath);
				setWeight(dataset + "/weights/tfen.txt", false);
			}

			if (weightFilePath.equals("tfbsentp.txt"))
			// for tp, using bdc to replace the tpbdc, but with tf
			{
				System.out.println("---------------------------"
						+ weightFilePath);
				setWeight(dataset + "/weights/bsdc.txt", false);
			}
			if (weightFilePath.equals("bsdctp.txt"))
			// for tp, using bdc to replace the tpbdc, but with tf
			{
				System.out.println("---------------------------"
						+ weightFilePath);
				setWeight(dataset + "/weights/bsdc.txt", true);
			}

			formatTestform(dataset + "/" + testFile + ".txt", dataset
					+ "/knndata/" + testFile + weightFilePath);
		}
	}
}
