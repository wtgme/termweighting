package preprocess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

/*
 * transform the text into number version
 */
public class String2Number {
	private Map<String, Integer> labelMap = new HashMap<String, Integer>();// label
																			// -
																			// label
																			// ID
	private Map<String, Integer> vocabulary = new HashMap<String, Integer>();// word
																				// -
																				// word
																				// ID
	private Map<String, Integer> voc_time = new HashMap<String, Integer>();// word
																			// -
																			// times

	private int index = 1;

	public void readVocTime(String trainFilePath) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(trainFilePath);
		String line;
		while ((line = fin.readLine()) != null) {
			String tokens[] = line.split("\t");
			// System.out.println(tokens.length);
			if (tokens.length < 2) {
				continue;
			}
			String[] words = tokens[1].split(" ");
			for (String word : words) {
				Integer times = voc_time.get(word);
				if (times == null) {
					times = 0;
				}
				voc_time.put(word, times + 1);
			}
		}
		fin.close();
	}

	public void transform(String filePath, String outputPath) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(filePath);
		String line;
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(outputPath);

		while ((line = fin.readLine()) != null) {
			// System.out.println(line);
			String tokens[] = line.split("\t");
			// System.out.println(tokens.length);
			if (tokens.length < 2) {
				continue;
			}
			String label = tokens[0];
			String[] words = tokens[1].split(" ");
			Integer labelId = labelMap.get(label);
			if (labelId == null) {
				labelId = labelMap.size() + 1;
				labelMap.put(label, labelId);
			}

			Map<Integer, Integer> word_count = new TreeMap<Integer, Integer>();
			for (String word : words) 
			{
				Integer times = voc_time.get(word);
				if (times > 5)
				{
					Integer wordId = vocabulary.get(word);
					if (wordId == null) {
						wordId = vocabulary.size() + 1;
						vocabulary.put(word, wordId);
					}

					Integer count = word_count.get(wordId);
					if (count == null) {
						count = 0;
					}
					word_count.put(wordId, count + 1);
				}
			}

			if (word_count.size() > 1) {
				fout.append(index++ + "\t");
				Set<Integer> keySet = word_count.keySet();
				for (Iterator<Integer> ite = keySet.iterator(); ite.hasNext();) {
					Integer wordId = ite.next();
					fout.append(wordId + ":" + word_count.get(wordId) + " ");
				}
				fout.append("\t" + labelMap.get(label) + "\n");
			}
		}

		fin.close();
		fout.flush();
		fout.close();
	}

	public void outputVoca(String filePath) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath);
		Set<String> keySet = this.vocabulary.keySet();
		for (Iterator<String> ite = keySet.iterator(); ite.hasNext();) {
			String key = ite.next();
			Integer value = this.vocabulary.get(key);
			fout.append(key + "\t" + value + "\n");
		}
		fout.flush();
		fout.close();
	}

	public void outputWordTime(String filePath) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath);
		Set<String> keySet = this.voc_time.keySet();
		for (Iterator<String> ite = keySet.iterator(); ite.hasNext();) {
			String key = ite.next();
			Integer value = this.voc_time.get(key);
			fout.append(key + "\t" + value + "\n");
		}
		fout.flush();
		fout.close();
	}

	public void outputLabel(String filePath) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath);
		Set<String> keySet = this.labelMap.keySet();
		for (Iterator<String> ite = keySet.iterator(); ite.hasNext();) {
			String key = ite.next();
			Integer value = this.labelMap.get(key);
			fout.append(key + "\t" + value + "\n");
		}
		fout.flush();
		fout.close();
	}

	public void run(String dataset)
	// public static void main(String[] args)
	{

		// String[] datasets = {"twitter", "newtitle", "newabstract",
		// "snippets",
		// "new"};

		// String filepath = new String("dataSets/cade");
		// String filepath = new String("dataSets/r8");
		// String filepath = new String("dataSets/20ng");

		String filepath = new String(dataset);
		readVocTime(filepath + "/trainPro");
		readVocTime(filepath + "/testPro");
		transform(filepath + "/trainPro", filepath + "/train.txt");
		transform(filepath + "/testPro", filepath + "/test.txt");
		outputLabel(filepath + "/label.txt");
		outputVoca(filepath + "/vocabulary.txt");
		outputWordTime(filepath + "/wordFrequency.txt");
	}
}
