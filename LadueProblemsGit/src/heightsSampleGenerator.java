import java.io.*;
public class heightsSampleGenerator {

	public static void main(String[] args) throws IOException{
		String[] grades = {"SE", "J", "SO", "F"};
		BufferedWriter w = new BufferedWriter(new FileWriter("heights.in"));
		for(int i = 0; i<1000000; i++){
			int height = 1 + (int)(Math.random()*1000);
			int ind = (int)(Math.random()*4);
			w.write(Integer.toString(height) + " " + grades[ind] + "\n");
		}
		w.close();
	}

}
