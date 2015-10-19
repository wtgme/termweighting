package schemes.category.baseline;

import schemes.category.TermBasedWeighting;
import util.Util;

public class ECCD extends TermBasedWeighting {

	public ECCD(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {
		// BDCTP 值
		double balanceEntropy = matrix.getEntropy(key);
		int a = matrix.getA(key, label);
		int b = matrix.getC(a, key);
		int c = matrix.getB(a, label);
		int d = matrix.getD(a, b, c);

		// double bdc = Math.exp(-balanceEntropy);
		double bdc = 1 - balanceEntropy / Util.log2(this.C);
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
		double tp = matrix.getDfProb(key, label);

		// TFBDC值
		// double tp1 = ((double)(a * d - b * c) / ((a + c) * (b + d)));
		// if(tp1!=tp)
		// System.out.println(tp1+"\t"+tp);
		double tfbdctp = bdc * tp;
		// System.out.println(tp + "\t" + bdc + "\t" + tfbdctp);
		return tfbdctp;
	}
}
