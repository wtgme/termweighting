package filesIOUtil;

public class StaticsClass
{
	public static void main(String[] args)
	{
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile("keyword_class.txt");
		// FileReaderInterface.readFile("keyword_class_part.txt");
		for (int i = 0; i < 33; i++)
		{
			FileWriterInterface fout = new FileWriterInterface();
			fout.writeFile((i + 1) + "_keyword_class.txt");
			String line = "";
			while ((line = fin.readLine()) != null)
			{
				if (line.endsWith("-"))
				{
					String[] tokens = line.split("\t");
					fout.append(tokens[0] + "\t" + (i + 1)
							+ "\n");
					fout.flush();
				}
				else
				{
					fout.append(line + "\n");
					fout.flush();
				}
			}
			fout.close();
		}
		fin.close();
	}
}
