package data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import knn.Weights;
import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class WeightComparsion {
	public Weights bdcweights;
	public Weights rfweights;
	public Weights iqfweights;
	public Weights idfweights;
	public Map<Integer, String> voc = new HashMap<Integer, String>();

	public void setWeight(String idfweight, String bdcweightFilePath,
			String rfweightFilePath, String iqfWeightFilePath) {
		idfweights = new Weights(idfweight);
		bdcweights = new Weights(bdcweightFilePath);
		rfweights = new Weights(rfweightFilePath);
		iqfweights = new Weights(iqfWeightFilePath);

	}
	public void setWeight(String bdcweightFilePath, String rfweightFilePath) {
		bdcweights = new Weights(bdcweightFilePath);
		rfweights = new Weights(rfweightFilePath);

	}

	public void readVoc(String filePath) {
		FileReaderInterface fr = new FileReaderInterface();
		fr.readFile(filePath);
		String line;
		while ((line = fr.readLine()) != null) {
			String[] token = line.split("\t");
			this.voc.put(Integer.valueOf(token[1]), token[0]);
		}
		fr.close();
	}

	// public void comparsion(String filePath) {
	// Set<Integer> set = bdcweights.max_weight.keySet();
	// int index = 0;
	// FileWriterInterface fw = new FileWriterInterface();
	// fw.writeFile(filePath);
	// for (Integer key : set) {
	// Double bdc = bdcweights.getMaxWeight(key);
	// Double idf = idfweights.getMaxWeight(key);
	// System.out.println(idf);
	// Double rf = rfweights.getMaxWeight(key);
	// Double iqf = iqfweights.getMaxWeight(key);
	// // if ((rf / bdc) > 10)
	// {
	// // System.out.println(index++ + this.voc.get(key) + "\t" + bdc
	// // + "\t" + rf + "\t" + iqf);
	//
	// fw.append(index++ + this.voc.get(key) + "\t" + idf + "\t" + bdc
	// + "\t" + rf + "\t" + iqf + "\n");
	// }
	// }
	// fw.flush();
	// fw.close();
	// }

	public void comparsion(String filePath) {
		Set<Integer> set = bdcweights.avg_weight.keySet();
		int index = 0;
		FileWriterInterface fw = new FileWriterInterface();
		fw.writeFile(filePath);
		for (Integer key : set) {
			Double idf = idfweights.getMaxWeight(key);
			Double bdc = bdcweights.getMaxWeight(key);
			Double rf = rfweights.getMaxWeight(key);
			Double iqf = iqfweights.getMaxWeight(key);
			// if ((rf / bdc) > 10)

			{
				// System.out.println(index++ + this.voc.get(key) + "\t" + bdc
				// + "\t" + rf + "\t" + iqf);

				fw.append(index++ + "\t" + this.voc.get(key) + "\t" + idf
						+ "\t" + bdc + "\t" + rf + "\t" + iqf + "\n");
			}
		}
		fw.flush();
		fw.close();
	}

	public static void main(String[] args) {
		WeightComparsion wc = new WeightComparsion();
		String data = "snippets";
		wc.readVoc("dataSets/" + data + "/vocabulary.txt");
		wc.setWeight("dataSets/" + data + "/weights/tfidf.txt", "dataSets/"
				+ data + "/weights/bdc.txt", "dataSets/" + data
				+ "/weights/tfrf.txt", "dataSets/" + data
				+ "/weights/iqfqficf.txt");
		wc.comparsion("dataSets/" + data + "/idfbdcrfiqf.txt");
	}
}
