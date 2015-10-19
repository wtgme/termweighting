package schemes.category;

import util.Util;

public class EN extends TermBasedWeighting {

	public EN(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		// TODO Auto-generated method stub
		// SDC值
		double smoothEntropy = matrix.getEntropy(key);

		// double sdc = 10.0 / (1 + Math.exp(smoothEntropy - 1.0));
		// double sdc = Math.exp(-smoothEntropy);
		double sdc = 1 - (smoothEntropy / Util.log2(this.C));
		// double sdc = 1 + smoothEntropy / Util.log2(matrix.getN());
		// double sdc = 1.0 - (smoothEntropy / Util.log2(C));
		// sdc *= Util.log2(matrix.getDF(key));
		return sdc;
	}

}
