package data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import filesIOUtil.FileReaderInterface;

public class CategoryUtilityTF {
	public Map<Integer, Map<Integer, Integer>> cate_word_count;
	public Map<Integer, Integer> cate_count;
	public Map<Integer, Integer> word_count;
	public int v;
	public String filePath;
	public String fileName;

	public CategoryUtilityTF(String filePath, String fileName) {
		cate_word_count = new HashMap<Integer, Map<Integer, Integer>>();
		cate_count = new HashMap<Integer, Integer>();
		word_count = new HashMap<Integer, Integer>();
		this.filePath = filePath;
		this.fileName = fileName;
	}

	public void readFile() {
		FileReaderInterface fr = new FileReaderInterface();
		fr.readFile(filePath + File.separator + fileName);

		String line;
		while ((line = fr.readLine()) != null) {
			String[] parts = line.split("\t");
			int label = Integer.valueOf(parts[2]);
			String[] tokens = parts[1].split(" ");

			Map<Integer, Integer> w_count = this.cate_word_count.get(label);
			if (w_count == null) {
				w_count = new HashMap<Integer, Integer>();
			}

			int countD = 0;

			for (String token : tokens) {
				String[] t_v = token.split(":");
				int word = Integer.valueOf(t_v[0]);
				int count = Integer.valueOf(t_v[1]);
				count = 1;
				countD += count;
				v += count;

				Integer wc = w_count.get(word);
				if (wc == null) {
					wc = 0;
				}
				w_count.put(word, count + wc);

				Integer wcount = this.word_count.get(word);
				if (wcount == null) {
					wcount = 0;
				}
				this.word_count.put(word, wcount + count);
			}

			Integer c_count = this.cate_count.get(label);
			if (c_count == null) {
				c_count = 0;
			}
			this.cate_count.put(label, countD + c_count);
			this.cate_word_count.put(label, w_count);
		}
		fr.close();
	}

	public void calculateCU() {
		int C = this.cate_count.size();
		long V = this.v;
		System.out.println(C + "\t" + V + "\t");
		Map<Integer, Double> pf = new HashMap<Integer, Double>();

		Set<Integer> cates = this.cate_count.keySet();
		Set<Integer> words = this.word_count.keySet();

		double pfa = 0.0;
		for (Integer w : words) {
			Integer count = this.word_count.get(w);
			Double pro = (double) count / V;
			pfa += pro;
			pf.put(w, pro);
		}
		System.out.println("===========" + pfa);

		double cu = 0.0;
		double c_all = 0.0;
		for (Integer c : cates) {
			Integer c_count = this.cate_count.get(c);
			Double c_pro = (double) c_count / V;
			c_all += c_pro;

			Map<Integer, Integer> c_t_c = this.cate_word_count.get(c);
			Set<Integer> t_s = c_t_c.keySet();

			double cfv = 0.0;
			double cv = 0.0;
			double pa = 0.0;
			for (Integer t : t_s) {
				Integer t_c = c_t_c.get(t);
				Double t_cPro = (double) t_c / c_count;
				Double fPro = pf.get(t);

				cfv += Math.pow(t_cPro, 2);
				cv += Math.pow(fPro, 2);
				pa += t_cPro;
			}
			System.out.println(pa);
			cu = cu + c_pro * (cfv - cv);
		}
		cu /= C;
		System.out.println(cu);
		System.out.println(c_all);
	}

	public static void main(String[] args) {
		CategoryUtilityTF cu = new CategoryUtilityTF("dataSets/r8",
				"train.txt");
		cu.readFile();
		cu.calculateCU();
	}
}
