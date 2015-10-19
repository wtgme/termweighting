package knn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BinaryAndTf {

	public static void main(String[] args) throws FileNotFoundException {
		
		String filepath = new String("natural");
//		String filepath = new String("500");
//		String filepath = new String("1000");
//		String filepath = new String("reuters");
//		String filepath = "newsgroup";
		
		Scanner trainIn = new Scanner(new File("C:/Users/Qixuan/Desktop/"+filepath+"/train.txt"));
		Scanner testIn = new Scanner(new File("C:/Users/Qixuan/Desktop/"+filepath+"/test.txt"));
		
		PrintWriter btrainOut = new PrintWriter(new File("C:/Users/Qixuan/Desktop/"+filepath+"/knndata/trainbinary.txt"));
		PrintWriter btestOut = new PrintWriter(new File("C:/Users/Qixuan/Desktop/"+filepath+"/knndata/testbinary.txt"));
		PrintWriter tftrainOut = new PrintWriter(new File("C:/Users/Qixuan/Desktop/"+filepath+"/knndata/traintf.txt"));
		PrintWriter tftestOut = new PrintWriter(new File("C:/Users/Qixuan/Desktop/"+filepath+"/knndata/testtf.txt"));
		
		String line = null;
		while(trainIn.hasNext()){
			
			line = trainIn.nextLine();
			if(line.length()>3){
				String[] lines = line.split("\t");
				if(lines.length>2){
					
					btrainOut.print(lines[0]+"\t");
					tftrainOut.print(lines[0]+"\t");
					
					String[] termpairs = lines[1].split(" ");
					
					double sum = 0;
					double count = 0;
					
					for(String pair: termpairs){
						String[] pairs = pair.split(":");
						int tf = Integer.valueOf(pairs[1]);
						sum += tf*tf;
						if(tf>0)
							count++;
					}
					
					sum = Math.sqrt(sum);
					count = Math.sqrt(count);
					
					for(String pair: termpairs){
						String[] pairs = pair.split(":");
						double tf = Integer.valueOf(pairs[1])/sum;
						double bi = 0;
						if(tf>0)
							bi=1/count;
						tftrainOut.print(pairs[0]+":"+tf+" ");
						btrainOut.print(pairs[0]+":"+bi+" ");
					}
					
					btrainOut.print("\t"+lines[2]+"\n");
					tftrainOut.print("\t"+lines[2]+"\n");
				}
			}
		}
		
		while(testIn.hasNext()){
			
			line = testIn.nextLine();
			if(line.length()>3){
				String[] lines = line.split("\t");
				if(lines.length>2){
					
					btestOut.print(lines[0]+"\t");
					tftestOut.print(lines[0]+"\t");
					
					String[] termpairs = lines[1].split(" ");
					
					double sum = 0;
					double count = 0;
					
					for(String pair: termpairs){
						String[] pairs = pair.split(":");
						int tf = Integer.valueOf(pairs[1]);
						sum+=tf*tf;
						if(tf>0)
							count++;
					}
					
					sum = Math.sqrt(sum);
					count = Math.sqrt(count);
					
					for(String pair: termpairs){
						String[] pairs = pair.split(":");
						double tf = Integer.valueOf(pairs[1])/sum;
						double bi = 0;
						if(tf>0)
							bi=1/count;
						tftestOut.print(pairs[0]+":"+tf+" ");
						btestOut.print(pairs[0]+":"+bi+" ");
					}
					
					btestOut.print("\t"+lines[2]+"\n");
					tftestOut.print("\t"+lines[2]+"\n");
				}
			}
		}
		
		btrainOut.close();
		btestOut.close();
		tftrainOut.close();
		tftestOut.close();
		
	}
}
