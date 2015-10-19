package evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class SVMEvaluation {
	private List<Integer> trueLabel;
	private List<Integer> predictionLabel;
	private int C;
	private ArrayList<StringBuffer> macrof1s;
	private ArrayList<StringBuffer> microf1s;
	private ArrayList<StringBuffer> precisions;
	private ArrayList<StringBuffer> recalls;
	private ArrayList<String> schemes;
	private int index;

	private Metrics metric;
	private int n;
	private int r;

	public SVMEvaluation(int c) {
		this.C = c;
		macrof1s = new ArrayList<StringBuffer>();
		microf1s = new ArrayList<StringBuffer>();
		precisions = new ArrayList<StringBuffer>();
		recalls = new ArrayList<StringBuffer>();
		schemes = new ArrayList<String>();
		index = 0;
	}

	public void readTrueLabel(String filePath) {
		FileReaderInterface fin = new FileReaderInterface();
		trueLabel = new ArrayList<Integer>();
		n = 0;
		fin.readFile(filePath);
		String line = "";
		while ((line = fin.readLine()) != null) {
			String[] parts = line.split(" ");
			// System.out.println(line);
			trueLabel.add(Integer.valueOf(parts[0]));
			n++;
		}
		fin.close();
	}

	public void readPrediction(String filePath) {
		FileReaderInterface fin = new FileReaderInterface();
		predictionLabel = new ArrayList<Integer>();
		fin.readFile(filePath);
		String line = "";
		while ((line = fin.readLine()) != null) {
			// predictionLabel.add(Integer.valueOf(line.substring(0,
			// line.indexOf("."))));
			predictionLabel.add(Integer.valueOf(line));
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
		// macrof1s.append("MacroF1:" + "\n");
		// microf1s.append("MicroF1:" + "\n");
		// recall.append("Recall:\n");
		// precision.append("Precision:\n");
		r = 0;
		metric = new Metrics(C);
		for (int i = 0; i < n; i++) {
			int t = trueLabel.get(i);
			int p = predictionLabel.get(i);
			if (t == p) {
				r++;
			}
			// System.out.println(t + "\t" + p);
			metric.setMatrix(t, p);
		}
		metric.calculation();

		double sum = 0.0;
		for (int i = 0; i < C; i++) {
			System.out.print(metric.getCatePrecision(i) + "\t");
			sum += metric.getCatePrecision(i);
		}
		System.out.println();
		System.out.println(sum / C);

		for (int i = 0; i < C; i++) {
			System.out.print(metric.getCateRecall(i) + "\t");
		}
		System.out.println();

		macrof1.append(metric.getMacroF1() + "\t");
		microf1.append(metric.getMicroF1() + "\t");
		recall.append(metric.getRecall() + "\t");
		precision.append(metric.getPrecision() + "\t");

		// String value = macrof1s.toString() + "\n" + microf1s.toString() +
		// "\n"
		// + recall.toString() + "\n" + precision.toString() + "\n";

		macrof1s.add(macrof1);
		microf1s.add(microf1);
		precisions.add(precision);
		recalls.add(recall);
		metric = null;
		System.out.println("Accurancy: " + (double) r / n);

	}

	public void outPut(String filePath) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath + "/svm/evaluation/evaluation.txt");
		fout.append("MacroF1\n");
		for (int i = 0; i < index; i++) {
			fout.append(this.schemes.get(i) + "\t" + this.macrof1s.get(i)
					+ "\n");
		}

		fout.append("MicroF1\n");
		for (int i = 0; i < index; i++) {
			fout.append(this.schemes.get(i) + "\t" + this.microf1s.get(i)
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

	public static void main(String[] args)
	// public void runEvaluation(String filePath)
	{

		// cade 12
		// r8 8
		// snippets 8
		// 20ng 20
		// r52 52
		String filePath = new String("dataSets/r8");
		int c = 8;
		SVMEvaluation evaluation = new SVMEvaluation(c);
		File dir = new File(filePath + "/svm/data");
		File[] files = dir.listFiles();

		String[] lists = {"tf", "tfidf", "tfnidf", "tfchi", "tfig", "eccd",
				"tfrf", "tfnrf", "iqfqficf", "dc", "bdc", "sdc", "dctp",
				"bdctp", "tfen", "tfben", "tfsen", "tfentp", "tfbentp"};

		
		for (String scheme : lists) {

			evaluation.readTrueLabel(filePath + "/svm/data/test" + scheme
					+ ".txt");

			evaluation.readPrediction(filePath + "/svm/prediction/" + scheme
					+ ".txt");
			evaluation.evaluation(scheme);
		}
		// for (File file : files) {
		// String testFile = file.getName();
		// if (testFile.startsWith("test") && testFile.endsWith(".txt")) {
		// String scheme = testFile.replace("test", "");
		// // scheme = scheme.replace(".scale", ".txt");
		// // System.out.println(scheme);
		//
		// evaluation.readTrueLabel(file.getAbsolutePath());
		// System.out.println(file.getAbsolutePath());
		// evaluation.readPrediction(filePath
		// + "/svm/prediction/" + scheme);
		// evaluation.evaluation(scheme);
		// }
		// }
		evaluation.outPut(filePath);

	}
}
