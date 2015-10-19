package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class RandomSelect {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner trainIn = new Scanner(new File("C:\\Users\\Qixuan\\Desktop\\3\\data\\class_trainNew.txt"));
		Scanner testIn = new Scanner(new File("C:\\Users\\Qixuan\\Desktop\\3\\data\\class_testNew.txt"));
//		PrintWriter trainOut = new PrintWriter(new File("C:\\Users\\Qixuan\\Desktop\\origin\\class_trainNew.txt"));
		PrintWriter testOut = new PrintWriter(new File("C:\\Users\\Qixuan\\Desktop\\origin\\class_testNew.txt"));
		
//		ArrayList<Integer> trainDistribution = new ArrayList<Integer>();
		ArrayList<Integer> testDistribution = new ArrayList<Integer>();
		
		
//		for(int i=0;i<33;i++)
//			trainDistribution.add(0);
		for(int i=0;i<33;i++)
			testDistribution.add(0);
		
		
//		ArrayList<Integer> trainRandom = new ArrayList<Integer>();
		ArrayList<Integer> testRandom = new ArrayList<Integer>();
		
//		for(int i=0;i<183920;i++){
//			int idx = (int)(Math.random()*1000000);
//			if(!trainRandom.contains(idx))
//				trainRandom.add(idx);
//		}
		for(int i=0;i<22103;i++){
			int idx = (int)(Math.random()*120000);
			if(!testRandom.contains(idx))
				testRandom.add(idx);
		}

//		int idx = 0;
//		int len = trainRandom.size();
//		System.out.println(len);
//		while (trainIn.hasNext()) {
//			String line = trainIn.nextLine();
//			if (trainRandom.contains(idx)) {
//				int lable = Integer.valueOf(line.split("\t")[2]) - 1;
//				trainDistribution.set(lable, trainDistribution.get(lable) + 1);
//				trainOut.println(line);
//			}
//			idx++;
//		}
		 
		
//		while (trainIn.hasNext()) {
//			String line = trainIn.nextLine();
//			int lable = Integer.valueOf(line.split("\t")[2]) - 1;
//			int size = trainDistribution.get(lable);
//			trainDistribution.set(lable, size+1);
//			if(size<1000)
//				trainOut.println(line);
//		}
		
//		for(int i=0;i<33;i++)
//			System.out.println(i+1+"\t:\t"+trainDistribution.get(i));
		
		int idx2 = 0;
		int len2 = testRandom.size();
		System.out.println(len2);
		while(testIn.hasNext()){
			String line = testIn.nextLine();
			if(testRandom.contains(idx2)){
				int lable = Integer.valueOf(line.split("\t")[2])-1;
				testDistribution.set(lable, testDistribution.get(lable)+1);
				testOut.println(line);
			}
			idx2++;
		}
//		while(testIn.hasNext()){
//			String line = testIn.nextLine();
//			int lable = Integer.valueOf(line.split("\t")[2])-1;
//			int size = testDistribution.get(lable)+1;
//			testDistribution.set(lable, size);
//			if(size<=500)
//				testOut.println(line);
//		}

		for(int i=0;i<33;i++)
			System.out.println(i+1+"\t:\t"+testDistribution.get(i));
		
//		trainOut.close();
		testOut.close();
	}
}
