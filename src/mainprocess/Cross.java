package mainprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import knn.KNNFormat;
import knn.KNNmult;
import preprocess.Preprocessing;
import preprocess.String2Number;
import schemes.SchemesMain;
import svm.KNN2SVM;
import evaluation.KNNEvaluation;
import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class Cross {
	public ArrayList<String> train = new ArrayList<String>();
	public ArrayList<String> test = new ArrayList<String>();
	public ArrayList<String> all = new ArrayList<String>();
	public String dataset;
	public int C;

	public Cross(String filePath, int category) {
		this.dataset = "dataSets/" + filePath;
		this.C = category;
	}
	public void readFile() {
		System.out.println("Processing text: Removing stopword, Stemming");
		Preprocessing process = new Preprocessing();
		process.run(dataset);

		System.out.println("Transform Text into Number");
		String2Number sn = new String2Number();
		sn.run(dataset);

		FileReaderInterface fr = new FileReaderInterface();
		fr.readFile(dataset + "/train.txt");
		String line;
		while ((line = fr.readLine()) != null) {
			this.all.add(line);
		}
		fr.readFile(dataset + "/test.txt");
		while ((line = fr.readLine()) != null) {
			this.all.add(line);
		}
		fr.close();
	}

	public void fiveCross(int c) throws IOException {
		int size = this.all.size();
		int length = size / c;
		Random rand = new Random();
		Set<Integer> flag = new HashSet<Integer>();

		for (int i = 0; i < c; i++) {
			for (int j = 0, k = 0; k < length; j = (j + 1) % size) {
				int ran = rand.nextInt(c) + 1;
				if ((ran == 1) && (!flag.contains(j))) {
					this.test.add(this.all.get(j));
					flag.add(j);
					k++;
				}
				// else if (this.train.size() <= (size - length)) {
				// this.train.add(this.all.get(j));
				// }
			}
			this.train.addAll(this.all);
			this.train.removeAll(this.test);

			FileWriterInterface fw = new FileWriterInterface();
			fw.writeFile(dataset + "/" + i + "train.txt");
			for (String tr : this.train) {
				fw.append(tr + "\n");
			}
			fw.flush();
			fw.writeFile(dataset + "/" + i + "test.txt");
			for (String te : this.test) {
				fw.append(te + "\n");
			}
			fw.flush();
			fw.close();

			this.train.clear();
			this.test.clear();

			System.out.println("Get Term Weights");
			SchemesMain sm = new SchemesMain();
			sm.runWeighting(dataset, i + "");
			sm = null;

			System.out.println("Document Vectors for KNN");
			KNNFormat kf = new KNNFormat();
			kf.run(dataset, i + "");
			kf = null;

			System.out.println("Document Vectors for SVM");
			KNN2SVM k2s = new KNN2SVM();
			k2s.run(dataset, i + "");
			k2s = null;

			System.out.println("KNN Classification");

			// String[] fileNames = { "tfbdctp.txt" };
			KNNmult knn = new KNNmult(100);

			knn.runClassifier(dataset, i + "","4");
			// knn.runClassifier(dataset, fileNames);
			knn = null;

			System.out.println("Classification Evaluation");
			KNNEvaluation knne = new KNNEvaluation(100, C);
			knne.run(dataset, i + "");
			knne = null;
		}
	}

	public static void main(String[] args) throws IOException {
		// cade 12
		// r8 8
		// snippets 8
		// 20ng 20
		// r52 52
		// sci 4
		Cross cross = new Cross("cade", 12);
		cross.readFile();
		cross.fiveCross(3);
	}
}
