package schemes.category.baseline;

import schemes.category.TermBasedWeighting;

public class CHI extends TermBasedWeighting
{

	public CHI(String filePath, String fileName, String outFilePath,
			String outFileName)
	{
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label)
	{
		int a = matrix.getA(key, label);
		int b = matrix.getB(a, label);
		int c = matrix.getC(a, key);
		int d = matrix.getD(a, b, c);

		double up = (double) a * d - b * c;
		double chi = (double) N * ((Math.pow(up, 2))
				/ ((double) (a + c) * (b + d) * (a + b) * (c + d)));

		// CHI值
		return chi;
	}
}
