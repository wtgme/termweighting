package mainprocess;

import java.io.IOException;

import data.CrossValidate;

import knn.KNNFormat;
import knn.KNNmult;
import preprocess.FeatureSelection;
import preprocess.Preprocessing;
import preprocess.String2Number;
import schemes.SchemesMain;
import svm.KNN2SVM;
import evaluation.KNNEvaluation;
import evaluation.SVMEvaluation;

public class MainProcess {
	/*
	 * DataPath, ClassNO, CrossNO. ThreadNO, KNNorSVM
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException {
		// cade 12
		// r8 8
		// snippets 8
		// 20ng 20
		// r52 52
		// sci 4
		// twitter 7
		// String dataset = "dataSets/" + "20ng";
		// int C = 20;
		// String t = "4";
		System.out.println(args[4]);
		String dataset = args[0];
		int C = Integer.valueOf(args[1]);
		int cross = Integer.valueOf(args[2]);
		String t = args[3];

		if (args[4].equals("KNN")) {
			System.out.println("Processing text: Removing stopword, Stemming");
			Preprocessing process = new Preprocessing();
			process.run(dataset);

			System.out.println("Transform Text into Number");
			String2Number sn = new String2Number();
			C = sn.run(dataset);

//			CrossValidate cv = new CrossValidate();
//			cv.run(dataset, cross);

			for (int i = 1; i < cross + 1; i++) {

				System.out.println("Get Term Weights");
				SchemesMain sm = new SchemesMain();
				sm.runWeighting(dataset, i + "");

				System.out.println("Document Vectors for KNN");
				KNNFormat kf = new KNNFormat();
				kf.run(dataset, i + "");

				System.out.println("Document Vectors for SVM");
				KNN2SVM k2s = new KNN2SVM();
				k2s.run(dataset, i + "");

				System.out.println("KNN Classification");

				String[] fileNames = {"bsdc", "tfbsen", "bsdctp", "tfbsentp"};
				KNNmult knn = new KNNmult(100);
				knn.runClassifier(dataset, i + "", t);
				// knn.runClassifier(dataset, fileNames);

				System.out.println("Classification Evaluation");
				KNNEvaluation knne = new KNNEvaluation(100, C);
				knne.run(dataset, i + "");
			}

			// String command = "python " + dataset + "/svm/runadmin.py";
			// System.out.println(command);
			// Process pro = Runtime.getRuntime().exec(command);
			// pro.waitFor();
			System.out.print("class number:" + C);

		} else if (args[4].equals("SVM")) {
			for (int i = 1; i < cross + 1; i++) {

				SVMEvaluation evaluation = new SVMEvaluation(C);
				evaluation.runEvaluation(dataset, i + "");

			}
		}
	}
}
