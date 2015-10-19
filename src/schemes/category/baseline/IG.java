package schemes.category.baseline;

import schemes.category.TermBasedWeighting;
import util.Util;

public class IG extends TermBasedWeighting
{

	public IG(String filePath, String fileName, String outFilePath,
			String outFileName)
	{
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	private double alog(double a, double b, double c, double N)
	{
		if (a == 0)
			return 0;
		return (a / N )* Util.log2((a * N) / ((a + c) * (a + b)));
	}

	@Override
	protected double weightCalculate(Integer key, int label)
	{
		// IG 值
		int a = matrix.getA(key, label);
		int b = matrix.getB(a, label);
		int c = matrix.getC(a, key);
		int d = matrix.getD(a, b, c);

		double ig = alog(a, b, c, N) + alog(b, a, d, N) + alog(c, a, d, N)
				+ alog(d, c, b, N);
		return ig;
	}

}
