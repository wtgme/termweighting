package evaluation;

import java.io.File;

import filesIOUtil.FileReaderInterface;

public class Metrics {
	private int[][] matrix;
	private int N;
	private double[] precision;
	private double[] recall;
	private double[] f1;

	private double MicroF1;
	private double MacroF1;

	public Metrics(int n) {
		N = n;
		matrix = new int[N + 1][N + 1];
		precision = new double[N + 1];
		recall = new double[N + 1];
		f1 = new double[N + 1];
		MicroF1 = 0.0;
		MacroF1 = 0.0;
	}

	public double getMacroF1() {
		return this.MacroF1;
	}

	public double getMicroF1() {
		return this.MicroF1;
	}

	public void initMatrix(String filePath, String fileName) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath + File.separator + fileName);
		String line = "";
		while ((line = fin.readLine()) != null) {
			String[] parts = line.split("\t");
			Integer row = Integer.valueOf(parts[2]);// true
			Integer col = Integer.valueOf(parts[3]);// prediction
			setMatrix(row, col);
		}
		fin.close();
	}

	public void setMatrix(int row, int col) {
		row--;
		col--;
		this.matrix[row][col]++;
		this.matrix[row][N]++;
		this.matrix[N][col]++;
	}

	public double[] getF1() {
		return this.f1;
	}

	public void calculation() {
		double TP = 0.0, precDown = 0.0, recalDown = 0.0;

		for (int i = 0; i < N; i++) {

			TP += (double) matrix[i][i];
			precDown += (double) matrix[N][i];
			recalDown += (double) matrix[i][N];

			if (matrix[N][i] == 0) {
				precision[i] = 0;
			} else {
				precision[i] = (double) matrix[i][i] / matrix[N][i];
			}

			if (matrix[i][N] == 0) {
				recall[i] = 0;
			} else {
				recall[i] = (double) matrix[i][i] / matrix[i][N];
			}
			if (precision[i] + recall[i] == 0) {
				f1[i] = 0;
			} else {
				f1[i] = 2 * precision[i] * recall[i]
						/ (precision[i] + recall[i]);
			}
			precision[N] += precision[i];
			recall[N] += recall[i];
			f1[N] += f1[i];
		}

		// double macP = precision[N] / N;
		// double macR = recall[N] / N;
		// MacroF1 = 2 * macP * macR / (macP + macR);
		MacroF1 = f1[N] / N;

		double micP = TP / precDown;
		double micR = TP / recalDown;
		MicroF1 = 2 * micP * micR / (micP + micR);
		// MicroF1 = 2 * precision[N] * recall[N] / (precision[N] + recall[N]) /
		// N;
	}

	public double getRecall() {
		return recall[N] / N;
	}

	public double getPrecision() {
		return precision[N] / N;
	}

	public double getCatePrecision(int i) {
		return precision[i];
	}

	public double getCateRecall(int i) {
		return recall[i];
	}

	public double getCateF1(int i) {
		return f1[i];
	}
}
