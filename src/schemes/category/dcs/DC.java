package schemes.category.dcs;

import schemes.category.TermBasedWeighting;
import util.Util;

public class DC extends TermBasedWeighting {

	public DC(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		// DC 值
		double entropy = matrix.getEntropy(key);
		// int a = matrix.getA(key, label);
		// int c = matrix.getC(a, key);
		// int max = matrix.getMaxDF(key);
		// double avg = matrix.getAvgDF(key);

		// double dc = Math.exp(-entropy);
		// double dc = 1 - entropy / Util.log2(this.C);
		// double dc = 10.0 / (1 + Math.exp(entropy - 1.0));
		double dc = 1.0 - (entropy / Util.log2(C));
		// dc = Util.log2(matrix.getDF(key)) * dc;

		// TFDC值
		return dc;
	}

}
