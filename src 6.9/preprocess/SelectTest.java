package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SelectTest {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner fiveIn = new Scanner(new File("C:\\Users\\Qixuan\\Desktop\\origin\\class_trainNew_500.txt"));
		Scanner tenIn = new Scanner(new File("C:\\Users\\Qixuan\\Desktop\\origin\\class_trainNew_1000.txt"));
		
		PrintWriter fiveTrain = new PrintWriter("C:\\Users\\Qixuan\\Desktop\\origin\\train_500.txt");
		PrintWriter tenTrain = new PrintWriter("C:\\Users\\Qixuan\\Desktop\\origin\\train_1000.txt");
		PrintWriter fiveTest = new PrintWriter("C:\\Users\\Qixuan\\Desktop\\origin\\test_500.txt");
		PrintWriter tenTest = new PrintWriter("C:\\Users\\Qixuan\\Desktop\\origin\\test_1000.txt");
		
		
		select(500, 33, fiveIn, fiveTrain, fiveTest);
		select(1000, 33, tenIn, tenTrain, tenTest);
		
		fiveTrain.close();
		tenTrain.close();
		fiveTest.close();
		tenTest.close();
	}
	
	public static void select(int times, int C, Scanner in, PrintWriter trainOut, PrintWriter testOut){
		
		int outTime = (int)(times/10.0);
		
		ArrayList<Integer> timeCounter = new ArrayList<Integer>();
		
		for(int i=0;i<C;i++){
			timeCounter.add(0);
		}
		
		while(in.hasNext()){
			String line = in.nextLine();
			if(line.length()>3){
				int label = Integer.valueOf(line.split("\t")[2]);
				if(timeCounter.get(label-1)<outTime){
					testOut.println(line);
					timeCounter.set(label-1, timeCounter.get(label-1)+1);
				}
				else{
					trainOut.println(line);
				}
			}
		}
	}
}
