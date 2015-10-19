package mainprocess;

import java.io.IOException;

import data.CrossValidate;

import knn.KNNFormat;
import knn.KNNmult;
import preprocess.Preprocessing;
import preprocess.String2Number;
import schemes.SchemesMain;
import svm.KNN2SVM;
import evaluation.KNNEvaluation;
import evaluation.SVMEvaluation;

public class Main {
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
		String dataset = "dataSets/" + "snippets";
		int C = 8;
		int cross = 5;
		String t = "4";

//		System.out.println("Processing text: Removing stopword, Stemming");
//		Preprocessing process = new Preprocessing();
//		process.run(dataset);
//
//		System.out.println("Transform Text into Number");
//		String2Number sn = new String2Number();
//		C = sn.run(dataset);
//
//		CrossValidate cv = new CrossValidate();
//		cv.run(dataset, cross);

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

			String[] fileNames = {"dctp.txt", "bdctp.txt", "tfentp.txt",
					"tfbentp.txt"};
			KNNmult knn = new KNNmult(100);
			knn.runClassifier(dataset, i + "", t, fileNames);
			// knn.runClassifier(dataset, fileNames);

			System.out.println("Classification Evaluation");
			KNNEvaluation knne = new KNNEvaluation(100, C);
			knne.run(dataset, i + "");
		}
	}
}
