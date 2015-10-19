package data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import matrix.Matrix;
import filesIOUtil.FileReaderInterface;

public class CategoryUtility {
	public Matrix matrix;
	public String filePath;
	public String fileName;

	public CategoryUtility(String filePath, String fileName) {
		matrix = new Matrix();
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

	public void calculateCU() {
		int C = this.matrix.getCat();
		double V = this.matrix.getV();
		int N = this.matrix.getN();
		System.out.println(C + "\t" + V + "\t" + N);
		Map<Integer, Double> pf = new HashMap<Integer, Double>();

		Set<Integer> cates = this.matrix.class_tf_size.keySet();
		Set<Integer> words = this.matrix.token_tf_size.keySet();

		double pfa = 0.0;
		for (Integer w : words) {
			Integer count = this.matrix.token_tf_size.get(w);
			Double pro = (double) count / V;
			pf.put(w, pro);
			pfa += pro;
		}
		System.out.println(pfa);

		double cu = 0.0;
		double cpa = 0.0;
		for (Integer c : cates) {
			Integer c_count = this.matrix.class_tf_size.get(c);
			Double c_pro = (double) c_count / V;
			cpa += c_pro;

			Map<Integer, Integer> c_t_c = this.matrix.matrix_tf_c_t.get(c);
			Set<Integer> t_s = c_t_c.keySet();

			double cv = 0.0;
			double cfv = 0.0;
			double tpa = 0.0;
			for (Integer t : t_s) {
				Integer t_c = c_t_c.get(t);
				Double t_cPro = (double) t_c / c_count;
				Double fPro = pf.get(t);
				tpa += t_cPro;

				cfv += Math.pow(t_cPro, 2);
				cv += Math.pow(fPro, 2);
				// cv += Math.pow(t_cPro, 2) - Math.pow(fPro, 2);
			}
			System.out.println(tpa);
			cu += c_pro * (cfv - cv);
		}
		System.out.println("+++++++"+cpa);
		cu /= C;
		System.out.println(cu);
	}

	public static void main(String[] args) {
		CategoryUtility cu = new CategoryUtility("dataSets/r8", "train.txt");
		cu.initMatrix();
		cu.calculateCU();

	}
}
