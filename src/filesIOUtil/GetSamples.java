package filesIOUtil;

import java.util.HashSet;
import java.util.Set;


public class GetSamples
{
	private Set<String> keywords;

	public GetSamples()
	{
		keywords = new HashSet<String>();
	}

	public void getKeyword(String filePath)
	{
		FileReaderInterface fin =new FileReaderInterface();
		fin.readFile(filePath);
		String line = null;
		while ((line = fin.readLine()) != null)
		{
			String[] tokens = line.split("\t");
			keywords.add(tokens[0]);
		}
		fin.close();
	}

	public void getSmple(String inFilePath, String outFilePath)
	{
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile(inFilePath);
		FileWriterInterface fout = new FileWriterInterface();
		fout.writeFile(outFilePath);
		String line = null;
		while ((line = fin.readLine()) != null)
		{
			String[] tokens = line.split("\t");
			if (keywords.contains(tokens[0]))
			{
				fout.append(line + "\n");
			}
		}
		fin.close();
		fout.flush();
		fout.close();
	}

	public static void main(String[] args)
	{
		GetSamples sample = new GetSamples();
		sample.getKeyword("F:/baidu/keyword_titles_part.txt");
		// sample.getSmple("F:/baidu/keyword_users.txt",
		// "F:/baidu/keyword_user_part.txt");
		// sample.getSmple("F:/baidu/keyword_class.txt",
		// "F:/baidu/keyword_class_part.txt");
	}
}
