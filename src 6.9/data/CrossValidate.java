package data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class CrossValidate {
	public List<String> data = new ArrayList<String>();

	public void readFile(String fileString) {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(fileString);
		String line;
		while ((line = fin.readLine()) != null) {
			data.add(line);
		}
		fin.close();
	}

	public void readData(String train, String test) {
		this.readFile(train);
		this.readFile(test);
	}

	public void outFile(ArrayList<String> data, String filePath) {
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(filePath);
		for (String line : data) {
			fout.append(line + "\n");
		}
		fout.flush();
		fout.close();
	}

	public void cross() {
		Random rand = new Random();
		Map<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		for (String line : data) {
			int key = rand.nextInt(5) + 1;
			ArrayList<String> subData = map.get(key);
			if (subData == null) {
				subData = new ArrayList<String>();
			}

			subData.add(line);
			map.put(key, subData);
		}

		Set<Integer> keys = map.keySet();
		for (Integer key : keys) {
			outFile(map.get(key), key + "test.txt");

			ArrayList<String> train = new ArrayList<String>();
			for (Integer keyj : keys) {
				if (keyj != key) {
					train.addAll(map.get(keyj));
				}
			}
			outFile(train, key + "train.txt");
		}
	}

	public void run(String filePath) {
		readData(filePath+File.separator+"","");
		cross();
	}
}
