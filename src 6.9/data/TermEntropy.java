package data;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import matrix.Matrix;
import util.Util;
import filesIOUtil.FileReaderInterface;

public class TermEntropy {
	private Matrix matrix;
	private String filePath;
	private String fileName;
	private int allterms;
	private Set<Double> entValues;
	private int C;

	private Map<Integer, Integer> cateNum;
	private Map<Double, Integer> entropy;

	public TermEntropy(String filePath, String fileName, int c) {
		matrix = new Matrix();
		this.C = c;
		entropy = new TreeMap<Double, Integer>();
		cateNum = new HashMap<Integer, Integer>();
		entValues = new HashSet<Double>();
		this.filePath = filePath;
		this.fileName = fileName;
	}

	public void initMatrix() {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath + File.separator + fileName);
		String line = "";
		while ((line = fin.readLine()) != null) {
			// System.out.println(line);
			String[] partsString = line.split("\t");
			String[] tokensStrings = partsString[1].split(" ");
			int label = Integer.valueOf(partsString[2]);
			matrix.setValue(tokensStrings, label);
		}
		fin.close();
	}

	public void getEntropy() {
		Set<Integer> keys = this.matrix.matrix_tf_t_c.keySet();
		this.allterms = keys.size();
		System.out.println(keys.size());
		for (Integer key : keys) {
			Double value = this.matrix.getEntropy(key);
			double dc = 1 - value / Util.log2(this.C);
			entValues.add(dc);

			int d = (int) (dc / 0.1);
			double k = d * 0.1;

			Integer count = this.entropy.get(k);
			if (count == null) {
				count = 0;
			}
			this.entropy.put(k, count + 1);

			Integer cateN = this.matrix.matrix_tf_t_c.get(key).keySet().size();
			count = this.cateNum.get(cateN);
			if (count == null) {
				count = 0;
			}
			this.cateNum.put(cateN, count + 1);
		}
	}

	public void display() {

		Set<Double> ent = this.entropy.keySet();

		for (Double en : ent) {
			System.out.println(en + "\t" + (double) this.entropy.get(en)
					/ this.allterms);
		}
		System.out.println(this.entValues.size());

		Set<Integer> cats = this.cateNum.keySet();

		for (Integer cat : cats) {
			System.out.println(cat + "\t" + (double) this.cateNum.get(cat)
					/ this.allterms);
		}
		System.out.println(this.entValues.size());
	}

	public static void main(String[] args) {
		TermEntropy te = new TermEntropy("dataSets/20ng", "train.txt", 20);
		te.initMatrix();
		te.getEntropy();
		te.display();
	}
}
