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
import schemes.category.dcs.BSDC;
import schemes.category.dcs.BSDCTP;
import schemes.category.dcs.BSEN;
import schemes.category.dcs.BSENTP;
import schemes.category.dcs.DC;
import schemes.category.dcs.DCTP;
import schemes.category.dcs.EN;
import schemes.category.dcs.ENTP;
import schemes.category.smooth.ADSSEN;
import schemes.category.smooth.DirSEN;
import schemes.category.smooth.JMSEN;
import schemes.category.smooth.LapSEN;
import schemes.category.smooth.TSSEN;

/*
 * all schemes for term weighting 
 * output the weighting files 
 */
public class SchemesMain {

	// public static void main(String[] args) throws InterruptedException
	public void runWeighting(String filePath, String i) {
		Long begin = System.currentTimeMillis();
		// ExecutorService executorSer vice = Executors.newFixedThreadPool(100);
		// String filePath = "reuters";

		String trainFile = i + "train.txt";

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

		System.out.println("---ig----");
		IG tfig = new IG(filePath, trainFile, filePath + "/weights", "tfig.txt");
		tfig.run();
		tfig = null;

		// System.out.println("------------binary-----------");
		// Binary binary = new Binary(filePath, trainFile, filePath +
		// "/weights",
		// "binary.txt");
		// binary.run();
		// binary = null;

		System.out.println("-------------tf------------");
		TF tf = new TF(filePath, trainFile, filePath + "/weights", "tf.txt");
		tf.run();
		tf = null;

		System.out.println("-----idf-----");
		IDF tfidf = new IDF(filePath, trainFile, filePath + "/weights",
				"tfidf.txt");
		tfidf.run();
		tfidf = null;

		System.out.println("-----nidf-----");
		NIDF tfnidf = new NIDF(filePath, trainFile, filePath + "/weights",
				"tfnidf.txt");
		tfnidf.run();
		tfnidf = null;
		
		System.out.println("---chi-----");
		CHI tfchi = new CHI(filePath, trainFile, filePath + "/weights",
				"tfchi.txt");
		tfchi.run();
		tfchi = null;

		System.out.println("----ECCD----");
		ECCD eccd = new ECCD(filePath, trainFile, filePath + "/weights",
				"eccd.txt");
		eccd.run();
		eccd = null;

		System.out.println("-----rf-----");
		RF tfrf = new RF(filePath, trainFile, filePath + "/weights", "tfrf.txt");
		tfrf.run();
		tfrf = null;

		System.out.println("-----nrf-----");
		NRF tfnrf = new NRF(filePath, trainFile, filePath + "/weights",
				"tfnrf.txt");
		tfnrf.run();
		tfnrf = null;

		//
		System.out.println("-----iqfqficf----");
		IQFQFICF iqfqficf = new IQFQFICF(filePath, trainFile, filePath
				+ "/weights", "iqfqficf.txt");
		iqfqficf.run();
		iqfqficf = null;

		System.out.println("----dc----");
		DC tfdc = new DC(filePath, trainFile, filePath + "/weights", "dc.txt");
		tfdc.run();
		tfdc = null;

		System.out.println("-----bdc-----");
		BDC tfbdc = new BDC(filePath, trainFile, filePath + "/weights",
				"bdc.txt");
		tfbdc.run();
		tfbdc = null;

		System.out.println("-----tfdc----");
		EN tfsdc = new EN(filePath, trainFile, filePath + "/weights",
				"tfen.txt");
		tfsdc.run();
		tfsdc = null;
		//
		System.out.println("----tfbdc----");
		BEN tfbsdc = new BEN(filePath, trainFile, filePath + "/weights",
				"tfben.txt");
		tfbsdc.run();
		tfbsdc = null;

		System.out.println("----dctp----");
		DCTP tfbdctp = new DCTP(filePath, trainFile, filePath + "/weights",
				"dctp.txt");
		tfbdctp.run();
		tfbdctp = null;

		System.out.println("----bdctp----");
		BDCTP bdctp = new BDCTP(filePath, trainFile, filePath + "/weights",
				"bdctp.txt");
		bdctp.run();
		bdctp = null;

		System.out.println("----tfbdctp----");
		ENTP entropy = new ENTP(filePath, trainFile, filePath + "/weights",
				"tfentp.txt");
		entropy.run();
		entropy = null;

		System.out.println("----tfbdctp----");
		BENTP bentp = new BENTP(filePath, trainFile, filePath + "/weights",
				"tfbentp.txt");
		bentp.run();
		bentp = null;

		System.out.println("----tfadsen----");
		ADSSEN adssen = new ADSSEN(filePath, trainFile, filePath + "/weights",
				"tfadsen.txt");
		adssen.run();
		adssen = null;

		System.out.println("----tfdirsen----");
		DirSEN dirsen = new DirSEN(filePath, trainFile, filePath + "/weights",
				"tfdirsen.txt");
		dirsen.run();
		dirsen = null;

		System.out.println("----tfjmsen----");
		JMSEN jmsen = new JMSEN(filePath, trainFile, filePath + "/weights",
				"tfjmsen.txt");
		jmsen.run();
		jmsen = null;

		System.out.println("----tflapsen----");
		LapSEN lapsen = new LapSEN(filePath, trainFile, filePath + "/weights",
				"tflapsen.txt");
		lapsen.run();
		lapsen = null;

		System.out.println("----tftssen----");
		TSSEN tssen = new TSSEN(filePath, trainFile, filePath + "/weights",
				"tftssen.txt");
		tssen.run();
		tssen = null;
		
		System.out.println("----bsdc----");
		BSDC bsdc = new BSDC(filePath, trainFile, filePath + "/weights",
				"bsdc.txt");
		bsdc.run();
		bsdc = null;
		
		System.out.println("----tfbsen----");
		BSEN bsen = new BSEN(filePath, trainFile, filePath + "/weights",
				"tfbsen.txt");
		bsen.run();
		bsen = null;
		
		System.out.println("----bsdctp----");
		BSDCTP bsdctp = new BSDCTP(filePath, trainFile, filePath + "/weights",
				"bsdctp.txt");
		bsdctp.run();
		bsdctp = null;
		
		
		System.out.println("----tfbsentp----");
		BSENTP bsentp = new BSENTP(filePath, trainFile, filePath + "/weights",
				"tfbsentp.txt");
		bsentp.run();
		bsentp = null;

		System.out.println((System.currentTimeMillis() - begin) / 1000);
	}
}
