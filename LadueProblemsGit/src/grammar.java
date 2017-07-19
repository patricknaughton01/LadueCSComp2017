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
				} //flags are the start of the literal followed by one index after the literal
				//"I am a Dr." gets the flag [7, 10]
			}
			ArrayList<String> sentences = new ArrayList<String>();
			//Each entry will not contain the punctuation that caused the split
			int start = 0;
			for(int j = 0; j<essays.get(i).length(); j++){
				if(!flagged(flags, j)){
					if(essays.get(i).substring(j, j+1).equals(".")||
							essays.get(i).substring(j, j+1).equals("?")||
							essays.get(i).substring(j, j+1).equals("!")){
						sentences.add(essays.get(i).substring(start, j));
						start = j+1;
					}
				}
			}
			//System.err.println(sentences);
		}
	}
	
	public static boolean flagged(ArrayList<int[]> flags, int ind){
		for(int i = 0; i<flags.size(); i++){
			if(ind>=flags.get(i)[0] && ind<flags.get(i)[1]){
				return(true);
			}
		}
		return(false);
	}
	

}
