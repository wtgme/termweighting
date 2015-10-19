package filesIOUtil;

public class Test
{
	public static void main(String[] args)
	{
		FileReaderInterface fin = new FileReaderInterface();
		fin.readFile("F:\\baidu\\keyword_titles_sample.txt");
		String line = null;
		while ((line = fin.readLine()) != null)
		{
			System.out.println(line);
		}
	}
}
