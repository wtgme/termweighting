package mainprocess;

import java.io.IOException;

import knn.KNNFormat;
import knn.KNNmult;
import preprocess.Preprocessing;
import preprocess.String2Number;
import schemes.SchemesMain;
import svm.KNN2SVM;
import evaluation.KNNEvaluation;

public class MainProcess {
	public static void main(String[] args) throws IOException {
		// cade 12
		// r8 8
		// snippets 8
		// 20ng 20
		// r52 52
		// sci 4
		// twitter 7
		String dataset = "dataSets/" + "snippets";
		int C = 8;

		System.out.println("Processing text: Removing stopword, Stemming");
		Preprocessing process = new Preprocessing();
		process.run(dataset);		
		
		System.out.println("Transform Text into Number");
		String2Number sn = new String2Number();
		sn.run(dataset);

		System.out.println("Get Term Weights");
		SchemesMain sm = new SchemesMain();
		sm.runWeighting(dataset, "");

		System.out.println("Document Vectors for KNN");
		KNNFormat kf = new KNNFormat();
		kf.run(dataset, "");

		System.out.println("Document Vectors for SVM");
		KNN2SVM k2s = new KNN2SVM();
		k2s.run(dataset, "");

		System.out.println("KNN Classification");

		// String[] fileNames = { "tfbdctp.txt" };
		KNNmult knn = new KNNmult(100);
		knn.runClassifier(dataset, "");
		// knn.runClassifier(dataset, fileNames);

		System.out.println("Classification Evaluation");
		KNNEvaluation knne = new KNNEvaluation(100, C);
		knne.run(dataset, "");

	}
}
