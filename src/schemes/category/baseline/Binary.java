package schemes.category.baseline;

import schemes.category.TermBasedWeighting;
import util.Util;

public class Binary extends TermBasedWeighting {
	public Binary(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {

		return 1;
	}
}
