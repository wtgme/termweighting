package schemes.category;

import util.Util;

public class IQFQFICF extends TermBasedWeighting
{

	public IQFQFICF(String filePath, String fileName, String outFilePath,
			String outFileName)
	{
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label)
	{
		int a = matrix.getA(key, label);
		int c = matrix.getC(a, key);
		int cf = matrix.getCF(key);

		// IQFQFICF值
		double iqfqficf = Util.log2((double) N / (a + c)) * Util.log2(a + 1)
				* Util.log2(((double) C / cf) + 1);
		return iqfqficf;
	}

}
