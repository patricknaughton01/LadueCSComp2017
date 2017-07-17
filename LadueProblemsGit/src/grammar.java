import java.io.*;
import java.util.*;

public class grammar {

	public static void main(String[] args) throws IOException{
		ArrayList<String> essays = new ArrayList<String>();
		ArrayList<String> dictionary = new ArrayList<String>();
		BufferedReader r = new BufferedReader(new FileReader("grammar.in"));
		for(int i = 0; i<370101; i++){
			dictionary.add(r.readLine().trim());
		}
		int numEssays = Integer.valueOf(r.readLine().trim());
		for(int i = 0; i<numEssays; i++){
			essays.add(r.readLine());
		}
		r.close();
		String[] puncLiterals = {"Dr.", "Mr.", "Mrs.", "Ms.", "i.e.", "e.g.", "etc."};
		for(int i = 0, n = essays.size(); i<n; i++){
			ArrayList<int[]> flags = new ArrayList<int[]>();
			for(int j = 0, m = puncLiterals.length; j<m; j++){
				int start = 0;
				int ind;
				while((ind=essays.get(i).indexOf(puncLiterals[j], start))!=-1){
					flags.add(new int[] {ind, ind+puncLiterals[j].length()});
					start = ind+1;
				}
			}
			ArrayList<String> sentences = new ArrayList<String>();
			for(int j = 0; j<essays.get(i).length(); j++){
				
			}
		}
	}
	

}
