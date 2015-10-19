package data;

import java.io.File;

public class TrainTest
{
	public static void main(String[] args)
	{

		int[] indexes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		for (int k = 5; k < 10; k++)
		{
			String filePath = "corpus" + File.separator + k;
			// FileWriterInterface.writeFile(filePath + File.separator + "data"
			// + File.separator + "class_train.txt");
			// for (int i = 0; i < 10; i++)
			// {
			// if (i != k)
			// {
			// FileReaderInterface.readFile("ten_fold" + File.separator
			// + "test" + i);
			// String line = "";
			// while ((line = FileReaderInterface.readLine()) != null)
			// {
			// FileWriterInterface.append(line + "\n");
			// }
			// FileWriterInterface.flush();
			// }
			// }
			//
			// FileWriterInterface.writeFile(filePath + File.separator + "data"
			// + File.separator + "class_test.txt");
			// FileReaderInterface.readFile("ten_fold" + File.separator + "test"
			// + k);
			// String line = "";
			// while ((line = FileReaderInterface.readLine()) != null)
			// {
			// FileWriterInterface.append(line + "\n");
			// }
			// FileWriterInterface.flush();
			// FileReaderInterface.close();
			// FileWriterInterface.close();
			// SchemesMain schemes = new SchemesMain();
			// schemes.runWeighting(filePath);

			// Evaluation evaluation = new Evaluation(100);
			// evaluation.runEvaluation(filePath);
		}
	}
}
