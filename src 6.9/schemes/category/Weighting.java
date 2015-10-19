package schemes.category;

import java.io.File;

import matrix.Matrix;
import filesIOUtil.FileReaderInterface;

public abstract class Weighting {
	protected Matrix matrix;
	// protected List<String> documents;
	protected int N;
	protected int C;
	protected String filePath;
	protected String fileName;
	protected String outFilePath;
	protected String outFileName;

	public Weighting(String filePath, String fileName, String outFilePath,
			String outFileName) {
		this.filePath = filePath;
		this.fileName = fileName;
		this.outFileName = outFileName;
		this.outFilePath = outFilePath;

		matrix = new Matrix();
		// documents = new ArrayList<String>();
		N = 0;
		C = 0;
	}

	/**
	 * @param filePath
	 * @param fileName
	 * 
	 *            初始化confuse matrix
	 */
	public void initMatrix() {
		FileReaderInterface fin = new FileReaderInterface();
//		System.out.println("Term Weighting reader File :" + filePath
//				+ File.separator + fileName);
		if (filePath.equals("")) {
			fin.readFile(fileName);
		} else {
			fin.readFile(filePath + File.separator + fileName);
		}
		String line = "";
		while ((line = fin.readLine()) != null) {
			String[] partsString = line.split("\t");
			String[] tokensStrings = partsString[1].split(" ");
			matrix.setValue(tokensStrings, Integer.valueOf(partsString[2]));
		}
		N = matrix.getN();
		C = matrix.getCat();
		fin.close();
	}

	public abstract void calculation();

	abstract protected double weightCalculate(Integer key, int label);

	public void run() {
		initMatrix();
		calculation();
	}
}
