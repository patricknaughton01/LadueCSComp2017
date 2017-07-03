import java.io.*;
//import java.util.*;
public class factorsSampleGenerator {

	public static void main(String[] args) throws IOException{
		BufferedWriter w = new BufferedWriter(new FileWriter("factors.in"));
		int numNums = 1 + (int) (Math.random()*999);
		w.write(Integer.toString(numNums) + "\n");
		for(int i = 0; i<numNums; i++){
			int num = 2 + (int) (Math.random()*(1000000-1));
			w.write(Integer.toString(num)+ "\n");
		}
		w.close();
	}

}
