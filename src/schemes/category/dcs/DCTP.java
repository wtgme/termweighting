package schemes.category.dcs;

import schemes.category.TermBasedWeighting;
import util.Util;

public class DCTP extends TermBasedWeighting {

	public DCTP(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		// BDCTP 值
		double balanceEntropy = matrix.getEntropy(key);
		int a = matrix.getTFA(key, label);
		int c = matrix.getTFSum(key);

		// double bdc = Math.exp(-balanceEntropy);
		double bdc = 1 - (balanceEntropy / Util.log2(this.C));
		// double bdc = 10.0 / (1 + Math.exp(balanceEntropy - 1.0));
		// double bdc = 1.0 - (balanceEntropy / Util.log2(C));

		// if (c == 0) {
		// c = 1;
		// }

		// double tp = Util.log2(2 + ((double) a / c));// rf
		// double tp = Util.log2(a + 1) / Util.log2(c + 1);// vrf
		// double tp = Util.log2(a + 1);
		// double tp = Util.log2(a + 1)// nrf
		// / (Util.log2(a + 1) + Util.log2(c + 1));

		// double tp = (double) Util.log2(2 + (double) a / (a + c));

		// double tp = 1.0 + (double) a / (a + c);

		// TFBDC值
		// double tfbdctp = bdc * tp;
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
		// c = c - a;
		// if (c == 0)
		// c = 1;
		// bdc = Util.log2((double) a / c + 2) * bdc;
		// bdc = mi * bdc;
		bdc *= Util.log2((double) a / c + 2);
		// bdc = Util.log2(2 + (double)a / c) * bdc;
		// System.out.println(tp + "\t" + bdc + "\t" + tfbdctp);
		// if (a != 0)
		// System.out.println((double) a / (1.0 + a));
		return bdc;
	}
}
