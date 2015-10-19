package preprocess;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import matrix.Matrix;
import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class FeatureSelection {
	public Matrix matrix;
	public int size;
	public String filePath;
	public int C;
	public Map<Integer, Map<Integer, Double>> cate_feat;
	// public Map<Integer, Double> featureMap;
	public Set<Integer> features;

	public FeatureSelection(String filePath, int C, int size) {
		this.size = size;
		this.matrix = new Matrix();
		this.filePath = filePath;
		this.C = C;
		this.features = new HashSet<Integer>();
		this.cate_feat = new HashMap<Integer, Map<Integer, Double>>();
		// this.featureMap = new HashMap<Integer, Double>();
	}

	public void initMatrix(String trainName, String testName) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath + File.separator + trainName);
		String line = "";

		while ((line = fin.readLine()) != null) {
			// System.out.println(line);
			String[] partsString = line.split("\t");
			String[] tokensStrings = partsString[1].split(" ");
			int label = Integer.valueOf(partsString[2]);
			matrix.setValue(tokensStrings, label);
		}

		fin.readFile(filePath + File.separator + testName);
		while ((line = fin.readLine()) != null) {
			// System.out.println(line);
			String[] partsString = line.split("\t");
			String[] tokensStrings = partsString[1].split(" ");
			int label = Integer.valueOf(partsString[2]);
			matrix.setValue(tokensStrings, label);

		}
		fin.close();
	}

	public void calculate() {
		Set<Integer> terms = this.matrix.token_tf_size.keySet();
		for (Integer term : terms) {
			for (int label = 1; label <= C; label++) {
				int a = matrix.getA(term, label);
				int b = matrix.getB(a, label);
				int c = matrix.getC(a, term);
				int d = matrix.getD(a, b, c);

				double up = (double) a * d - b * c;
				double down = (double) (a + c) * (b + d) * (a + b) * (c + d);
				double chi = 0.0;
				if (down != 0.0) {
					chi = (double) this.matrix.N * ((Math.pow(up, 2)) / down);
				}

				// System.out.println(a + "\t" + b + "\t" + c + "\t" + d + "\t"
				// + chi);
				Map<Integer, Double> tv = this.cate_feat.get(label);
				if (tv == null) {
					tv = new HashMap<Integer, Double>();
				}
				tv.put(term, chi);
				this.cate_feat.put(label, tv);
			}
		}
	}

	public void selection() {
		for (int label = 1; label <= C; label++) {
			Map<Integer, Double> tv = this.cate_feat.get(label);
			List arrayList = new ArrayList(tv.entrySet());
			Collections.sort(arrayList, new Comparator() {
				public int compare(Object o1, Object o2) {
					Map.Entry obj1 = (Map.Entry) o1;
					Map.Entry obj2 = (Map.Entry) o2;
					if ((Double) obj1.getValue() >= (Double) obj2.getValue())
						return 0;
					else
						return 1;
				}
			});

			for (int i = 0; i < size; i++) {
				this.features.add((Integer) ((Map.Entry) arrayList.get(i))
						.getKey());
				System.out.println(((Map.Entry) arrayList.get(i)).getKey()
						+ ":" + ((Map.Entry) arrayList.get(i)).getValue());
			}
		}
	}

	// public void calculate() {
	// Set<Integer> terms = this.matrix.token_tf_size.keySet();
	// for (Integer term : terms) {
	// Double max = 0.0;
	// for (int label = 1; label <= C; label++) {
	// int a = matrix.getA(term, label);
	// int b = matrix.getB(a, label);
	// int c = matrix.getC(a, term);
	// int d = matrix.getD(a, b, c);
	//
	// double up = (double) a * d - b * c;
	// double down = (double) (a + c) * (b + d) * (a + b) * (c + d);
	// double chi = 0.0;
	// if (down != 0.0) {
	// chi = (double) this.matrix.N * ((Math.pow(up, 2)) / down);
	// }
	//
	// // System.out.println(a + "\t" + b + "\t" + c + "\t" + d + "\t"
	// // + chi);
	// if (max <= chi) {
	// max = chi;
	// }
	// }
	// this.featureMap.put(term, max);
	// }
	// }

	// public void selection() {
	//
	// List arrayList = new ArrayList(this.featureMap.entrySet());
	// Collections.sort(arrayList, new Comparator() {
	// public int compare(Object o1, Object o2) {
	// Map.Entry obj1 = (Map.Entry) o1;
	// Map.Entry obj2 = (Map.Entry) o2;
	// if ((Double) obj1.getValue() >= (Double) obj2.getValue())
	// return 0;
	// else
	// return 1;
	// }
	// });
	//
	// for (int i = 0; i < size; i++) {
	// this.features
	// .add((Integer) ((Map.Entry) arrayList.get(i)).getKey());
	// System.out.println(((Map.Entry) arrayList.get(i)).getKey() + ":"
	// + ((Map.Entry) arrayList.get(i)).getValue());
	// }
	// System.out.println(this.features.size());
	// }

	public void reconstruct(String infile, String outfile) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath + File.separator + infile);
		String line = "";
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(this.filePath + File.separator + outfile);

		while ((line = fin.readLine()) != null) {
			// System.out.println(line);
			String[] partsString = line.split("\t");
			String[] tokensStrings = partsString[1].split(" ");

			StringBuffer sb = new StringBuffer();
			int s = 0;
			for (String tokensString : tokensStrings) {
				String[] t_v = tokensString.split(":");
				if (this.features.contains(Integer.valueOf(t_v[0]))) {
					s++;
					sb.append(tokensString + " ");
				}
			}
			if (s >= 3)
				fout.append(partsString[0] + "\t" + sb.toString() + "\t"
						+ partsString[2] + "\n");
		}
		fout.flush();
		fout.close();
		fin.close();
	}
	public void run(String trainfile, String newtrainfile, String testfile,
			String newtestfile) {
		initMatrix(trainfile, testfile);
		calculate();
		selection();
		reconstruct(trainfile, newtrainfile);
		reconstruct(testfile, newtestfile);
		// System.out.println(fs.features);
		// System.out.println(fs.features.size());
	}

	public static void main(String args[]) {
		FeatureSelection fs = new FeatureSelection("dataSets/r8/", 8, 500);
		fs.initMatrix("train.txt", "test.txt");
		fs.calculate();
		fs.selection();
		System.out.println(fs.features);
		System.out.println(fs.features.size());
	}
}
