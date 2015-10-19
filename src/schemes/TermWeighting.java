package schemes;

import schemes.category.baseline.CHI;
import schemes.category.baseline.ECCD;
import schemes.category.baseline.IDF;
import schemes.category.baseline.IG;
import schemes.category.baseline.IQFQFICF;
import schemes.category.baseline.NIDF;
import schemes.category.baseline.NRF;
import schemes.category.baseline.RF;
import schemes.category.baseline.TF;
import schemes.category.dcs.BDC;
import schemes.category.dcs.BDCTP;
import schemes.category.dcs.BEN;
import schemes.category.dcs.BENTP;
import schemes.category.dcs.DC;
import schemes.category.dcs.DCTP;
import schemes.category.dcs.EN;
import schemes.category.dcs.ENTP;
import schemes.category.smooth.LapSEN;

/*
 * all schemes for term weighting 
 * output the weighting files 
 */
public class TermWeighting {

	public static void main(String[] args) {
		// public void runWeighting(String filePath, String trainFile) {
		Long begin = System.currentTimeMillis();
		// ExecutorService executorSer vice = Executors.newFixedThreadPool(100);
		// String filePath = "reuters";

		// String trainFile = "train.txt";
		String filePath = "";
		String trainFile = args[0];
				
		// String filePath = new String("dataSets/cade");
		// String filePath = new String("dataSets/r8");
		// String filePath = new String("dataSets/20ng");
		// String filePath = new String("dataSets/twitter");

		// Binary binary = new Binary(filePath + File.separator + "data",
		// "class_trainNew.txt", filePath + File.separator + "weights",
		// "binary.txt");
		// binary.run();
		// TF tf = new TF(filePath + File.separator + "data",
		// "class_trainNew.txt",
		// filePath + File.separator + "weights", "tf.txt");
		// tf.run();

		// System.out.println("---ig----");
		IG tfig = new IG(filePath, trainFile, filePath + "/weights", "tfig.txt");
		tfig.run();
		tfig = null;

		// System.out.println("------------binary-----------");
		// Binary binary = new Binary(filePath, trainFile, filePath +
		// "/weights",
		// "binary.txt");
		// binary.run();
		// binary = null;

		// System.out.println("-------------tf------------");
		TF tf = new TF(filePath, trainFile, filePath + "/weights", "tf.txt");
		tf.run();
		tf = null;

		// System.out.println("---chi-----");
		CHI tfchi = new CHI(filePath, trainFile, filePath + "/weights",
				"tfchi.txt");
		tfchi.run();
		tfchi = null;

		// System.out.println("----ECCD----");
		ECCD eccd = new ECCD(filePath, trainFile, filePath + "/weights",
				"eccd.txt");
		eccd.run();
		eccd = null;

		// System.out.println("-----rf-----");
		RF tfrf = new RF(filePath, trainFile, filePath + "/weights", "tfrf.txt");
		tfrf.run();
		tfrf = null;

		// System.out.println("-----nrf-----");
		NRF tfnrf = new NRF(filePath, trainFile, filePath + "/weights",
				"tfnrf.txt");
		tfnrf.run();
		tfnrf = null;

		//
		// System.out.println("-----iqfqficf----");
		IQFQFICF iqfqficf = new IQFQFICF(filePath, trainFile, filePath
				+ "/weights", "iqfqficf.txt");
		iqfqficf.run();
		iqfqficf = null;

		// System.out.println("-----idf-----");
		IDF tfidf = new IDF(filePath, trainFile, filePath + "/weights",
				"tfidf.txt");
		tfidf.run();
		tfidf = null;

		// System.out.println("-----nidf-----");
		NIDF tfnidf = new NIDF(filePath, trainFile, filePath + "/weights",
				"tfnidf.txt");
		tfnidf.run();
		tfnidf = null;

		// System.out.println("----dctp----");
		DCTP tfbdctp = new DCTP(filePath, trainFile, filePath + "/weights",
				"dctp.txt");
		tfbdctp.run();
		tfbdctp = null;

		// System.out.println("----bdctp----");
		BDCTP bdctp = new BDCTP(filePath, trainFile, filePath + "/weights",
				"bdctp.txt");
		bdctp.run();
		bdctp = null;

		// System.out.println("----dc----");
		DC tfdc = new DC(filePath, trainFile, filePath + "/weights", "dc.txt");
		tfdc.run();
		tfdc = null;

		// System.out.println("-----bdc-----");
		BDC tfbdc = new BDC(filePath, trainFile, filePath + "/weights",
				"bdc.txt");
		tfbdc.run();
		tfbdc = null;

		// System.out.println("----sdc----");
		LapSEN sdc = new LapSEN(filePath, trainFile, filePath + "/weights", "sdc.txt");
		sdc.run();
		sdc = null;

		// System.out.println("-----tfdc----");
		EN tfsdc = new EN(filePath, trainFile, filePath + "/weights",
				"tfen.txt");
		tfsdc.run();
		tfsdc = null;
		//
		// System.out.println("----tfbdc----");
		BEN tfbsdc = new BEN(filePath, trainFile, filePath + "/weights",
				"tfben.txt");
		tfbsdc.run();
		tfbsdc = null;


		// System.out.println("----tfbdctp----");
		ENTP entropy = new ENTP(filePath, trainFile, filePath + "/weights",
				"tfentp.txt");
		entropy.run();
		entropy = null;

		// System.out.println("----tfbdctp----");
		BENTP bentp = new BENTP(filePath, trainFile, filePath + "/weights",
				"tfbentp.txt");
		bentp.run();
		bentp = null;

		// System.out.println((System.currentTimeMillis() - begin) / 1000);
	}
}
