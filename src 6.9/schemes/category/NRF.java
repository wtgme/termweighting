package schemes.category;

import util.Util;

public class NRF extends TermBasedWeighting {

	public NRF(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		int a = matrix.getTFA(key, label);
		int c = matrix.getTFSum(key) - a;
		if (c == 0) {
			c = 1;
		}
		double pp = (double) a / matrix.getClassTFSize(label);
		double np = (double) c / matrix.getClassTFNCSize(label);

		double rf = Util.log2(2 + (pp / np));

		return rf;
	}

}
