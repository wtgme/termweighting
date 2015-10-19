package mainprocess;

import java.io.IOException;

import knn.KNNFormat;
import knn.KNNmult;
import preprocess.FeatureSelection;
import preprocess.Preprocessing;
import preprocess.String2Number;
import schemes.SchemesMain;
import svm.KNN2SVM;
import evaluation.KNNEvaluation;

public class OneMain {
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
		String dataset = "dataSets/" + "20ng";
		int C = 20;
		String t = "4";

		// String dataset = args[0];
		// int C = Integer.valueOf(args[1]);
		// String t = args[2];

		System.out.println("Processing text: Removing stopword, Stemming");
		Preprocessing process = new Preprocessing();
		process.run(dataset);

		System.out.println("Transform Text into Number");
		String2Number sn = new String2Number();
		C = sn.run(dataset);

		System.out.println("Get Term Weights");
		SchemesMain sm = new SchemesMain();
		sm.runWeighting(dataset, "");

		System.out.println("Document Vectors for KNN");
		KNNFormat kf = new KNNFormat();
		kf.run(dataset, "");

		System.out.println("Document Vectors for SVM");
		KNN2SVM k2s = new KNN2SVM();
		k2s.run(dataset, "");

//		System.out.println("KNN Classification");
//		// tf", "tfidf", "tfnidf", "tfchi", "tfig", "eccd",
//		// "tfrf", "tfnrf", "iqfqficf", "dc", "bdc", "tfen", "tfben",
//		// "tflapsen", "tfjmsen", "tfdirsen", "tfadsen", "tftssen",
//		// "dctp", "bdctp", "tfentp", "tfbentp", "bsdc", "tfbsen",
//		// "bsdctp", "tfbsentp"
//		String[] fileNames = {"bsdc", "tfbsen", "bsdctp", "tfbsentp"};
//		// String[] fileNames = {"bdctp.txt", "tfbentp.txt"};
//		KNNmult knn = new KNNmult(100);
//		knn.runClassifier(dataset, "", t);
//		// knn.runClassifier(dataset, "", t, fileNames);
//		// knn.runClassifier(dataset, fileNames);
//
//		System.out.println("Classification Evaluation");
//		KNNEvaluation knne = new KNNEvaluation(100, C);
//		knne.run(dataset, "");

	}
}
