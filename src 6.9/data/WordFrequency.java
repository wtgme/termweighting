package data;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import filesIOUtil.FileReaderInterface;

public class WordFrequency {
	public Map<Integer, Integer> frequency_time = new TreeMap<Integer, Integer>();
	public double total = 0;

	public void read(String filePath) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line;
		while ((line = fin.readLine()) != null) {
			String[] parts = line.split("\t");
			Integer frequency = Integer.valueOf(parts[1]);

			if (frequency > 5) {
				Integer count = this.frequency_time.get(frequency);
				if (count == null) {
					count = 0;
				}
				this.frequency_time.put(frequency, count + 1);
				total++;
			}
		}
		fin.close();
	}

	public void output() {
		Set<Integer> keys = this.frequency_time.keySet();
		double count = 0.0;

		for (Integer key : keys) {
			Integer value = this.frequency_time.get(key);
			if (key <= 30) {
				count += value;
			}
			System.out.println(key + "\t" + (double) value / total);
		}
		System.out.println(count / total);
	}

	public static void main(String[] args) {
		WordFrequency wf = new WordFrequency();
		wf.read("dataSets/r8/wordFrequency.txt");

		wf.output();
	}

}
