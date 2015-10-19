package schemes.category.dcs;

import schemes.category.TermBasedWeighting;
import util.Util;

public class ENTP extends TermBasedWeighting {

	public ENTP(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		double balanceEntropy = matrix.getEntropy(key);

		// double entropy = 1 - balanceEntropy / Util.log2(this.C);
		int a = matrix.getTFA(key, label);
		int c = matrix.getTFSum(key);
		double bdc = 1 - (balanceEntropy / Util.log2(this.C));

		// double tc = matrix.getClassTFSize(label);
		// double mi = ((double) a * matrix.getV()) / ((double) c * tc);
		// mi = Util.log2(2 + mi);
		// double norm = -Util.log2((double) a / matrix.getV());
		// if (norm == 0) {
		// mi = 0;
		// } else {
		// mi = mi / norm;
		// }

		// bdc = Util.log2((double) matrix.getV() / c) * Util.log2((double) a +
		// 2)
		// * bdc;
		// bdc = mi * bdc;
		bdc *= Util.log2((double) a / c + 2);
		// weight = Util.log2(2 + (double)a / c) * weight;
		return bdc;
	}
}
