package schemes.category.baseline;

import schemes.category.TermBasedWeighting;
import util.Util;

public class IDF extends TermBasedWeighting {

	public IDF(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		int a = matrix.getA(key, label);
		int c = matrix.getC(a, key);

		double idf = Util.log2((double) N / (a + c));

		return idf;
	}

}
