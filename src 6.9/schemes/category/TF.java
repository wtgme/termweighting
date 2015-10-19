package schemes.category;

public class TF extends TermBasedWeighting {

	public TF(String filePath, String fileName, String outFilePath,
			String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double weightCalculate(Integer key, int label) {

		return 1;
	}
}
