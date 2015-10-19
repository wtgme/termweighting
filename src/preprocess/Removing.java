package preprocess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * removing the punctuation, numbers, and email address.
 */
public class Removing {

	public String punctuation(String document) {
		// Pattern p = Pattern.compile("[.,\"\\*^?!:'<>()-=+$#%&~{}_`/;|]");//
		// 增加对应的标点
		// Matcher m = p.matcher(document);
		// String first = m.replaceAll(""); // 把英文标点符号替换成空，即去掉英文标点符号

		String first = document.replaceAll("[[\\p{P}\\p{S}]&&[^@]]", "");
		Pattern p = Pattern.compile(" {2,}");// 去除多余空格
		Matcher m = p.matcher(first);
		String second = m.replaceAll(" ");
		return second;
	}

	public String numEmHt(String document) {
		String[] tokens = document.split(" ");
		StringBuffer newDocument = new StringBuffer();
		for (String token : tokens) {
			if (hasNum(token)) {
				continue;
			} else if (isEmail(token)) {
				continue;
			} else if (isHttp(token)) {
				continue;
			} else {
				newDocument.append(token + " ");
			}
		}
		return newDocument.toString().trim();
	}

	public boolean hasNum(String s) {
		String regex = "[0-9]+?";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.find();
	}

	public boolean isEmail(String s) {
		return s.contains("@");
	}

	public boolean isHttp(String s) {
		String regex = "http[\\w].*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.find();
	}

	public String removeProcess(String document) {
		String processed = punctuation(document);
		System.out.println(processed);
		return numEmHt(processed);
	}

	public static void main(String[] args) {
		Removing pu = new Removing();
		System.out
				.println(pu
						.removeProcess("http,.least in the,  ?  dfa@physics community, has been decided. (tzs@stein.u.washington.edu)?"));
	}
}
