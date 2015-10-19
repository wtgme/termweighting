package schemes.category;

import util.Util;

public class RF extends TermBasedWeighting {

	public RF(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		int a = matrix.getA(key, label);
		int c = matrix.getC(a, key);
		if (c == 0) {
			c = 1;
		}
		

		double rf = Util.log2(2 + ((double) a / c));

		return rf;
	}

}
