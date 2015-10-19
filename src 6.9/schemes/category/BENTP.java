package schemes.category;

import util.Util;

public class BENTP extends TermBasedWeighting {

	public BENTP(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		double balanceEntropy = matrix.getBalanceEntropy(key);

		// double entropy = 1 - balanceEntropy / Util.log2(this.C);
		int a = matrix.getTFA(key, label);
		int c = matrix.getTFSum(key);
		double bdc = 1 - (balanceEntropy / Util.log2(this.C));

		double tc = matrix.getClassTFSize(label);
		double mi = ((double) a * matrix.getV()) / ((double) c * tc);
		mi = Util.log2(2+mi);
//		double norm = -Util.log2((double) a / matrix.getV());
//		if (norm == 0) {
//			mi = 0;
//		} else {
//			mi = mi / norm;
//		}
		
		bdc = mi * bdc;
		
		//weight = Util.log2(2 + (double)a / c)* weight;
		return bdc;
	}
}
