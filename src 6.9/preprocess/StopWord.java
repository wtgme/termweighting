package preprocess;

import java.util.HashSet;
import java.util.Set;

import filesIOUtil.FileReaderInterface;

public class StopWord {
	private Set<String> stopwords = new HashSet<String>();

	public StopWord() {
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile("stopWord.txt");
		String line;
		while ((line = fin.readLine()) != null) {
			stopwords.add(line.trim());
		}
	}

	public boolean isStopWord(String token) {
		return stopwords.contains(token);
	}
}
