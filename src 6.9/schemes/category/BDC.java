package schemes.category;

import util.Util;

public class BDC extends TermBasedWeighting {

	public BDC(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		double balanceEntropy = matrix.getBalanceEntropy(key);

		// int a = matrix.getA(key, label);
		// int c = matrix.getC(a, key);
		// int max = matrix.getMaxDF(key);
		// double avg = matrix.getAvgDF(key);
		// // double bdc = Math.exp(-balanceEntropy);
		double bdc = 1 - balanceEntropy / Util.log2(this.C);
		// bdc = Util.log2(1 + avg) * bdc;

		// double bdc = 1 + balanceEntropy / Util.log2(matrix.getN());
		// double bdc = 1.0 / (1 + Math.exp(balanceEntropy - 1.0));
		// double bdc = 1.0 - (balanceEntropy / Util.log2(C));
		// TFBDC值
		return bdc;
	}

}
