package filesIOUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 *  a file reader interface
 *  read a file from filePath
 *  include: readLine(),close();
 */
public class FileReaderInterface {
	public BufferedReader br;

	public void readFile(String filePath) {
		try {
			File file = new File(filePath);
			FileInputStream fin = new FileInputStream(file);
			InputStreamReader in = new InputStreamReader(fin, "UTF-8");
			br = new BufferedReader(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readFile(File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			InputStreamReader in = new InputStreamReader(fin, "UTF-8");
			br = new BufferedReader(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int linenum = 0;
	static long startTime = System.currentTimeMillis();

	public String readLine() {
		try {
			// if (++linenum % 1000 == 0)
			// {
			// System.out.println(" " + linenum + "   "
			// + (System.currentTimeMillis() - startTime));
			// }
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void close() {
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}