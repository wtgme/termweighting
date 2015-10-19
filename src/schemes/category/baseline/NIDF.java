package schemes.category.baseline;

import schemes.category.TermBasedWeighting;
import util.Util;

public class NIDF extends TermBasedWeighting {

	public NIDF(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		int a = matrix.getA(key, label);
		int c = matrix.getC(a, key);

		int df = a + c;

		double idf = Util.log2((double) N / df);
		double aef = (double) matrix.getTFSum(key) / df;
		idf = idf * aef / (1 + aef);

		return idf;
	}

}
