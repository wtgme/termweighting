package data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import matrix.Matrix;
import filesIOUtil.FileReaderInterface;

public class TextCateWord {
	private Matrix matrix;
	private int N;
	private int C;
	private Double V;
	private String filePath;
	private Map<Integer, Integer> cate_docs;
	private Map<String, Integer> voc = new HashMap<String, Integer>();

	public TextCateWord(String filePath) {
		matrix = new Matrix();
		cate_docs = new HashMap<Integer, Integer>();
		this.filePath = filePath;
	}

	public void initMatrix(String trainName, String testName) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath + File.separator + trainName);
		String line = "";
		int count = 0;
		while ((line = fin.readLine()) != null) {
			// System.out.println(line);
			String[] partsString = line.split("\t");
			String[] tokensStrings = partsString[1].split(" ");
			int label = Integer.valueOf(partsString[2]);
			matrix.setValue(tokensStrings, label);
			Integer docNum = this.cate_docs.get(label);
			if (docNum == null) {
				docNum = 0;
			}
			this.cate_docs.put(label, docNum + 1);
			count++;
		}
		System.out.println("Training size:" + count);
		count = 0;
		fin.readFile(filePath + File.separator + testName);
		while ((line = fin.readLine()) != null) {
			// System.out.println(line);
			String[] partsString = line.split("\t");
			String[] tokensStrings = partsString[1].split(" ");
			int label = Integer.valueOf(partsString[2]);
			matrix.setValue(tokensStrings, label);
			Integer docNum = this.cate_docs.get(label);
			if (docNum == null) {
				docNum = 0;
			}
			this.cate_docs.put(label, docNum + 1);
			count++;
		}
		System.out.println("Test size:" + count);

		N = matrix.getN();
		C = matrix.getCat();
		V = matrix.getV();
		fin.close();
		System.out.println("Doc NO: " + N);
		System.out.println("Voc: " + matrix.getTokenSize());
		System.out.println("Cate NO: " + C);

	}

	public double getLengthPerDoc() {
		return (V / N);
	}

	public void getAvgDF_TF() {
		Map<Integer, Integer> token_df_size = this.matrix.token_df_size;
		Map<Integer, Integer> token_tf_size = this.matrix.token_tf_size;

		Set<Integer> tokenSet = token_df_size.keySet();
		System.out.println(tokenSet.size());
		Double dfSum = 0.0;
		Double tfSum = 0.0;
		for (Integer token : tokenSet) {
			Integer df = token_df_size.get(token);
			Integer tf = token_tf_size.get(token);
			dfSum += df;
			tfSum += tf;
		}
		System.out.println("The average DF:" + dfSum / tokenSet.size());
		System.out.println("The average TF:" + tfSum / tokenSet.size());
	}

	public double avgCateofWord() {
		int wordSize = matrix.matrix_tf_t_c.size();
		double categoryNumb = 0;
		Set<Integer> words = matrix.matrix_tf_t_c.keySet();
		for (Integer word : words) {
			Map<Integer, Integer> class_num = matrix.matrix_tf_t_c.get(word);
			categoryNumb += class_num.size();
		}
		return categoryNumb / wordSize;
	}

	public double avgWordofCate() {
		int cateSize = matrix.matrix_tf_c_t.size();
		double wordNumb = 0;
		Set<Integer> cates = matrix.matrix_tf_c_t.keySet();
		for (Integer cate : cates) {
			Map<Integer, Integer> class_num = matrix.matrix_tf_c_t.get(cate);
			wordNumb += class_num.size();
			System.out.print(cate + ":" + class_num.size() + "\t");
		}
		System.out.println();
		return wordNumb / cateSize;
	}

	public double avgWordTFofCate() {

		double wordNumb = 0;
		Set<Integer> cates = matrix.class_tf_size.keySet();
		for (Integer cate : cates) {
			Integer class_num = matrix.class_tf_size.get(cate);
			wordNumb += class_num;
			System.out.print(cate + ":" + class_num + "\t");
		}
		System.out.println();
		return wordNumb / matrix.class_tf_size.size();
	}

	public void docNumofCate() {
		Set<Integer> cates = this.cate_docs.keySet();
		for (Integer cate : cates) {
			System.out.print(cate + ":" + this.cate_docs.get(cate) + "\t");
		}
	}

	public void readVoc(String filePath) {
		FileReaderInterface fr = new FileReaderInterface();
		fr.readFile(filePath);
		String line;
		while ((line = fr.readLine()) != null) {
			String[] token = line.split("\t");
			this.voc.put(token[0], Integer.valueOf(token[1]));
		}
		fr.close();
	}

	public void getDistribution() {
		Scanner sc = new Scanner(System.in);
		String str = null;
		while (sc.hasNext()) {
			str = sc.next();
			Integer id = this.voc.get(str);
			Map<Integer, Integer> cateNum = this.matrix.matrix_tf_t_c.get(id);
			Set<Integer> set = cateNum.keySet();
			for (int i = 1; i < 9; i++) {
				Integer value = cateNum.get(i);
				if (value == null) {
					value = 0;
				}
				System.out.print(value + "\t");
			}
			System.out.println();
		}
	}

	public static void main(String args[]) {
		TextCateWord tcw = new TextCateWord("dataSets/20ng/");

		tcw.initMatrix("train.txt", "test.txt");
		System.out.println("Word Length of Document: " + tcw.getLengthPerDoc());
		// System.out.println("Avg Categories of Each Word: "
		// + tcw.avgCateofWord());
		// System.out
		// .println("Avg Words of Each Category: " + tcw.avgWordofCate());
		// tcw.docNumofCate();

		System.out.println("Avg Words of Each Category: "
				+ tcw.avgWordTFofCate());
		tcw.getAvgDF_TF();
		// tcw.readVoc("dataSets/snippets/vocabulary.txt");
		//
		// tcw.getDistribution();
	}
}
