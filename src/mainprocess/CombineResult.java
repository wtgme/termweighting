package mainprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class CombineResult {
	public Map<String, List<Double>> macro = new HashMap<String, List<Double>>();
	public Map<String, List<Double>> micro = new HashMap<String, List<Double>>();
	public Map<String, List<Double>> pre = new HashMap<String, List<Double>>();
	public Map<String, List<Double>> rec = new HashMap<String, List<Double>>();

	// public int c = 3;

	public void readEvaluation(String filePath) {
		FileReaderInterface fr = new FileReaderInterface();
		fr.readFile(filePath);

		String line;
		line = fr.readLine();
		while (!((line = fr.readLine()).startsWith("Micro"))) {
			System.out.println(line);
			setValue(line, this.macro);
		}
		while (!((line = fr.readLine()).startsWith("Prec"))) {
			System.out.println(line);
			setValue(line, this.micro);
		}
		while (!((line = fr.readLine()).startsWith("Reca"))) {
			System.out.println(line);
			setValue(line, this.pre);
		}
		while ((line = fr.readLine()) != null) {
			System.out.println(line);
			setValue(line, this.rec);
		}
		fr.close();
	}

	public void setValue(String line, Map<String, List<Double>> map) {
		String[] tokens = line.split("\t");
		String label = tokens[0];
		List<Double> list = map.get(label);
		if (list == null) {
			list = new ArrayList<Double>();
			for (int i = 1; i < tokens.length; i++) {
				list.add(Double.valueOf(tokens[i]));
			}
		} else {
			for (int i = 1; i < tokens.length; i++) {
				list.set(i - 1, list.get(i - 1) + Double.valueOf(tokens[i]));
			}
		}
		map.put(label, list);
	}

	public void outPut(String filePath, int c) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath + "/evaluation/" + "allevaluation.txt");
		fout.append("MacroF1\n");

		fout.append(outPutMap(this.macro, c));

		fout.append("MicroF1\n");
		fout.append(outPutMap(this.micro, c));
		fout.append("Precision\n");
		fout.append(outPutMap(this.pre, c));
		fout.append("Recall\n");
		fout.append(outPutMap(this.rec, c));
		fout.flush();
		fout.close();
	}

	public String outPutMap(Map<String, List<Double>> map, int c) {
		String[] labels = {"tf.txt", "tfidf.txt", "tfchi.txt", "tfig.txt",
				"tfrf.txt", "iqfqficf.txt", "eccd.txt", "tfdc.txt",
				"tfbdc.txt", "tfbdctp.txt", "tfsdc.txt", "tfbsdc.txt",
				"entropy.txt"};
		StringBuffer sb = new StringBuffer();
		for (String label : labels) {
			sb.append(label + "\t");
			List<Double> list = map.get(label);
			for (Double value : list) {
				sb.append(value / c + "\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		CombineResult cr = new CombineResult();
		String filePath = "dataSets/" + "cade";
		int c = 3;
		for (int i = 0; i < c; i++) {
			System.out.println("result");
			cr.readEvaluation(filePath + "/evaluation/" + i + "evaluation.txt");
		}
		cr.outPut(filePath, c);
	}
}
