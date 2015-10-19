package data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import filesIOUtil.FileReaderInterface;

public class SelectResults {
	public Map<String, String> bdcTest = new HashMap<String, String>();
	public Map<String, String> rfTest = new HashMap<String, String>();
	public Map<String, Double> bdcCorrect = new HashMap<String, Double>();
	public Map<String, Double> rfCorrect = new HashMap<String, Double>();
	public WeightComparsion wc = new WeightComparsion();
	public Map<String, Integer> voc = new HashMap<String, Integer>();

	public SelectResults() {
		wc.readVoc("dataSets/r8/vocabulary.txt");
		wc.setWeight("dataSets/r8/weights/tfdc.txt",
				"dataSets/r8/weights/tfrf.txt");
	}

	public void readVoc(String filePath) {
		FileReaderInterface fr = new FileReaderInterface();
		fr.readFile(filePath);
		String line;
		while ((line = fr.readLine()) != null) {
			String[] token = line.split("\t");
			this.voc.put(token[0], Integer.valueOf(token[1]));
		}
		fr.close();
	}

	public void readResult(String filePath, Map<String, String> map,
			Map<String, Double> correctMap) {
		FileReaderInterface fr = new FileReaderInterface();
		fr.readFile(filePath);
		String line;
		while ((line = fr.readLine()) != null) {
			String[] documents = line.split("\t");
			String test = documents[0];
			int startPos = test.indexOf(":");
			String testLable = test.substring(startPos + 1, startPos + 2);
			String testID = test.substring(0, startPos);
			int total = 0;

			int correct = 0;
			for (int i = 1; i < documents.length; i++) {
				String train = documents[i];
				int lastPos = train.lastIndexOf(":");
				String trainLable = train.substring(lastPos - 1, lastPos);
				total++;
				// System.out.println(testLable+"\t"+trainLable);
				if (testLable.equals(trainLable)) {
					correct++;
				}
			}
			double rate = (double) correct / total;
			map.put(testID, test);
			correctMap.put(testID, rate);
		}
		fr.close();
	}

	public void outPutDifference() {
		Set<String> testSet = this.bdcTest.keySet();
		for (String test : testSet) {
			Double bdcRate = this.bdcCorrect.get(test);
			Double rfRate = this.rfCorrect.get(test);
			if (bdcRate > 0.3 && rfRate < 0.3) {
				String rfDoc = this.rfTest.get(test);
				String bdcDoc = this.bdcTest.get(test);
				String[] rftokens = rfDoc.substring(7).split(" ");
				String[] bdctokens = bdcDoc.substring(7).split(" ");

				System.out.println();
				System.out.println(rfDoc.replaceAll(" ", "\t"));
				System.out.println(bdcDoc.replaceAll(" ", "\t"));
				System.out.println();

				for (String token : rftokens) {
					token = token.split(":")[0];
					// System.out.println(token);
					System.out.print(token + ":"
							+ wc.rfweights.getMaxWeight(this.voc.get(token))
							+ "\t");
				}
				System.out.println();

				for (String token : bdctokens) {
					token = token.split(":")[0];

					System.out.print(token + ":"
							+ wc.bdcweights.getMaxWeight(this.voc.get(token))
							+ "\t");
				}
				System.out.println();
			}
			// System.out.println();
			// System.out.println();
		}
	}

	public static void main(String[] args) {
		SelectResults sr = new SelectResults();
		sr.readVoc("dataSets/r8/vocabulary.txt");
		sr.readResult("dataSets/r8/prediction/tfrf.txt", sr.rfTest,
				sr.rfCorrect);
		sr.readResult("dataSets/r8/prediction/tfdc.txt", sr.bdcTest,
				sr.bdcCorrect);
		sr.outPutDifference();

	}
}
