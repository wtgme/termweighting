package schemes.category.dcs;

import java.util.Map;

import schemes.category.TermBasedWeighting;
import util.Util;

public class BEN extends TermBasedWeighting {

	public BEN(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		double balanceEntropy = matrix.getBalanceEntropy(key);

		double bdc = 1 - balanceEntropy / Util.log2(this.C);

		// double bdc = 1 + balanceEntropy / Util.log2(matrix.getN());
		// double bdc = 10.0 / (1 + Math.exp(balanceEntropy - 1.0));

		// double bdc = 1.0 - (balanceEntropy / Util.log2(C));
		// BDC值
		// bdc *= Util.log2(matrix.getDF(key));
		return bdc;
		
		
	}
}









