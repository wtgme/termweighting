package svm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class KNN2SVM {
	public List<Integer> testId = new ArrayList<Integer>();

	public void formatTransform(String filePath, String outputFile,
			boolean store) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(outputFile);
		String line;
		while ((line = fin.readLine()) != null) {
			String[] tokens = line.split("\t");
			Integer label = Integer.valueOf(tokens[2]);
			fout.append(label + " " + tokens[1] + "\n");
			if (store) {
				this.testId.add(Integer.valueOf(tokens[0]));
			}
		}
		fin.close();
		fout.flush();
		fout.close();
	}

	public void outId(String filePath) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath);
		for (int id : testId) {
			fout.append(id + "\n");
		}
		fout.flush();
		fout.close();
		testId.clear();
	}
	public void run(String dataset, String i)
	// public static void main(String[] args)
	{
		// String filePath = "reuters";
		// String filePath = new String("dataSets/cade");
		// String filePath = new String("dataSets/snippets");
		// String filePath = new String("dataSets/r8");
		// String filePath = new String("dataSets/20ng");

		System.out.println(dataset);

		File fileDir = new File(dataset + "/weights");
		File[] files = fileDir.listFiles();

		// String[] weightFilePaths = {"tfidf.txt", "tfrf.txt", "iqfqficf.txt",
		// "tfsdc.txt", "tfdc.txt", "tfbdc.txt", "tfbsdc.txt", "tfig.txt",
		// "tfchi.txt", "tfbdctp.txt"};

		String trainFile = i + "train";
		String testFile = i + "test";
		String idFile = i + "ID";

		for (File file : files) {
			String weightFilePath = file.getName();
			formatTransform(dataset + "/knndata/" + trainFile + weightFilePath,
					dataset + "/svm/data/" + trainFile + weightFilePath, false);
			formatTransform(dataset + "/knndata/" + testFile + weightFilePath,
					dataset + "/svm/data/" + testFile + weightFilePath, true);
			outId(dataset + "/svm/data/" + idFile + weightFilePath);
		}
	}
}
