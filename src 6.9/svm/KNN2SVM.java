package svm;

import java.io.File;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class KNN2SVM {

	public void formatTransform(String filePath, String outputFile) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(outputFile);
		String line;
		while ((line = fin.readLine()) != null) {
			String[] tokens = line.split("\t");
			Integer label = Integer.valueOf(tokens[2]);
			fout.append(label + " " + tokens[1] + "\n");
		}
		fin.close();
		fout.flush();
		fout.close();
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

		for (File file : files) {
			String weightFilePath = file.getName();
			formatTransform(dataset + "/knndata/" + trainFile + weightFilePath,
					dataset + "/svm/data/" + trainFile + weightFilePath);
			formatTransform(dataset + "/knndata/" + testFile + weightFilePath,
					dataset + "/svm/data/" + testFile + weightFilePath);
		}
	}
}
