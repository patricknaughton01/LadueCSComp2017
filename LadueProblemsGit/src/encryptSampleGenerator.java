import java.io.*;
import java.util.*;
public class encryptSampleGenerator {
	public static final int MAX_SENTENCES = 100;
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		ArrayList<String> possibleMsgs = new ArrayList<String>();
		BufferedReader r = new BufferedReader(new FileReader("encrypt.sentences"));
		int numMsgs = 1 + (int) (Math.random()*MAX_SENTENCES);
		int numSents = Integer.valueOf(r.readLine().trim());
		for(int i = 0;i <numSents; i++){
			possibleMsgs.add(r.readLine().trim());
		}
		r.close();
		BufferedWriter w = new BufferedWriter(new FileWriter("encrypt.in"));
		w.write(Integer.toString(numMsgs)+ "\n");
		for(int i = 0; i<numMsgs; i++){
			int choice = (int)(Math.random()*numSents);
			w.write(Integer.toString(-26+(int)(53*Math.random()))+"\n");
			w.write(possibleMsgs.get(choice)+"\n");
		}
		w.close();
	}

}
