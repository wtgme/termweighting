package schemes.category;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import filesIOUtil.FileWriterInterface;

public abstract class TermBasedWeighting extends Weighting {
	public TermBasedWeighting(String filePath, String fileName,
			String outFilePath, String outFileName) {
		super(filePath, fileName, outFilePath, outFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void calculation() {
		FileWriterInterface fout = new FileWriterInterface();
		if (outFilePath.startsWith("/")) {
			outFilePath = outFilePath.replaceFirst("/", "");
		}
		fout.writeFile(outFilePath + File.separator + outFileName);
		Set<Integer> doc = matrix.getTokenSet();

		for (Iterator<Integer> iterator = doc.iterator(); iterator.hasNext();) {
			Integer key = iterator.next();

			double[] weights = new double[C];
			for (int label = 1; label <= C; label++) {
				double value = weightCalculate(key, label);
				weights[label - 1] = value;
			}
			fout.append(key + "\t");
			for (int i = 0; i < C; i++) {
				fout.append((i + 1) + ":" + (weights[i]) + " ");
			}
			fout.append("\n");
		}
		fout.flush();
		fout.close();
	}

	abstract protected double weightCalculate(Integer key, int label);
}
