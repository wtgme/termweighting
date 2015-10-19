package preprocess;

//import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import filesIOUtil.FileReaderInterface;
import filesIOUtil.FileWriterInterface;

public class POS {
//	private String modelPath;
//	private MaxentTagger tagger;
//
//	POS(String mPath) {
//		this.modelPath = mPath;
//		tagger = new MaxentTagger(modelPath);
//	}
//
//	public void pos(String filePath, String outPutPath) {
//		FileReaderInterface.readFile(filePath);
//		String line = "";
//		FileWriterInterface.writeFile(outPutPath);
//		while ((line = FileReaderInterface.readLine()) != null) {
//			String[] content_cate = line.split("\t");
//			String tagged = tagger.tagString(content_cate[0]);
//			FileWriterInterface.append(tagged + "\t" + content_cate[1] + "\n");
//		}
//		FileReaderInterface.close();
//		FileWriterInterface.flush();
//		FileWriterInterface.close();
//	}
//
//	public static void main(String[] args) {
//		POS pf = new POS("models/english-left3words-distsim.tagger");
//		pf.pos("newsgroup/newsgroup_test.txt",
//				"newsgroup/newsgroup_testPOS.txt");
//		pf.pos("newsgroup/newsgroup_train.txt",
//				"newsgroup/newsgroup_trainPOS.txt");
//		pf.pos("reuters/reuters_test.txt", "reuters/reuters_testPOS.txt");
//		pf.pos("reuters/reuters_train.txt", "reuters/reuters_trainPOS.txt");
//	}
}
