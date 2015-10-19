package filesIOUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/*
 *  a file writer interface
 *  write a file to filePath
 *  include: append(),flush(),close();
 */
public class FileWriterInterface {
	public BufferedWriter bw;

	public void writeFile(String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filePath), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int linenum = 0;
	static long startTime = System.currentTimeMillis();

	public void append(String s) {
		try {
			// if (++linenum % 1000 == 0)
			// {
			// System.out.println(" " + linenum + "   "
			// + (System.currentTimeMillis() - startTime));
			// }
			bw.append(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void flush() {
		try {
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}