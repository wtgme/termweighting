package knn;

import java.util.HashMap;
import java.util.Map;

import filesIOUtil.FileReaderInterface;

public class Weights {
	public Map<Integer, Map<Integer, Double>> token_weight;
	public Map<Integer, Double> max_weight;
	private double avage_weight;
	private int classNumber;
	public Map<Integer, Double> avg_weight;

	public Weights(String weightFile) {
		token_weight = new HashMap<Integer, Map<Integer, Double>>();
		max_weight = new HashMap<Integer, Double>();
		avg_weight = new HashMap<Integer, Double>();
		avage_weight = 0.0;
		classNumber = 0;
		readWeightFile(weightFile);
	}

	private void readWeightFile(String weightFilePath) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(weightFilePath);
		String line = null;
		// int index = 0;
		while ((line = fin.readLine()) != null) {
			// System.out.println(index++);
			Map<Integer, Double> map = new HashMap<Integer, Double>();

			String[] doc_token = line.split("\t");
			String[] token_weights = doc_token[1].split(" ");
			classNumber = token_weights.length;
			Double max = 0.0;
			double wordAvg = 0.0;
			for (String token_weight : token_weights) {

				String[] key_value = token_weight.split(":");
				Double value = Double.valueOf(key_value[1]);
				if (value > max) {
					max = value;
				}
				wordAvg += value;
				map.put(Integer.valueOf(key_value[0]), value);
				avage_weight += value;
			}
			max_weight.put(Integer.valueOf(doc_token[0]), max);
			avg_weight
					.put(Integer.valueOf(doc_token[0]), wordAvg / classNumber);

			token_weight.put(Integer.valueOf(doc_token[0]), map);
		}
		avage_weight /= (max_weight.size() * classNumber);
		fin.close();
	}

	public Double getWeight(Integer string, int label) {
		Map<Integer, Double> map = token_weight.get(string);
		if (map == null) {
			return null;
		} else {
			return map.get(label);
		}

	}

	public Map<Integer, Double> getWeightsIClass(Integer string) {
		Map<Integer, Double> map = this.token_weight.get(string);
		if (map == null) {
			return null;
		} else {
			return map;
		}
	}

	public Double getWordAvgWeight(Integer string) {
		return this.avg_weight.get(string);
	}

	public Double getMaxWeight(Integer string) {
		return max_weight.get(string);
	}

	public Double getAgaveWeight() {
		return avage_weight;
	}
}
