import java.io.*;
import java.util.*;

public class grammar {
	public static ArrayList<String> dictionary = new ArrayList<String>();
	public static void main(String[] args) throws IOException{
		ArrayList<String> essays = new ArrayList<String>();
		
		BufferedReader r = new BufferedReader(new FileReader("grammar.in"));
		for(int i = 0; i<370101; i++){
			dictionary.add(r.readLine().trim());
		}
		//System.out.println(dictionary);
		int numEssays = Integer.valueOf(r.readLine().trim());
		for(int i = 0; i<numEssays; i++){
			essays.add(r.readLine());
		}
		r.close();
		/*ArrayList<String> test = new ArrayList<String>( //binary function tester
				Arrays.asList(new String[] {"a", "aa", "aaa", "aaaa", "aaaaa"}));
		System.err.println(binSearch(test, "aaaaa", 0, test.size()));*/
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
						sentences.add(essays.get(i).substring(start, j).trim());
						start = j+1;
					}
				}
			}
			//System.err.println(sentences);
			for(int j = 0; j<sentences.size(); j++){
				Pair1<Integer, String> caps = checkCaps(sentences.get(j));
				sentences.set(j, caps.getS());
				//System.err.println(caps.getI());
				//System.err.println(caps.getS());
				int spelling = checkSpelling(sentences.get(j));
				System.err.println(sentences.get(j));
				System.err.println(spelling);
			}
		}
	}
	
	public static int checkSpelling(String sentence){
		int start = 0;
		int mistakes = 0;
		for(int i = 0; i<sentence.length(); i++){
			if(sentence.charAt(i)==' '){
				String word = sentence.substring(start, i);
				if(Character.isLowerCase(word.charAt(0)) &&
						!hasChar(word, '\'')){
					if(binSearch(dictionary, word, 
							0, dictionary.size())==-1){
						mistakes++;
						//System.err.println(word);
					}
				}
				start = i+1;
			}
		}
		return(mistakes);
	}
	
	public static Pair1<Integer,String> checkCaps(String sentence){
		String workingSentence = sentence;
		int mistakes = 0;
		if(sentence.length()>0){
			if(Character.isLowerCase(sentence.charAt(0))){
				mistakes++;
			}else{
				workingSentence = Character.toLowerCase(sentence.charAt(0)) + 
						sentence.substring(1);
			}
			for(int i = 1; i<sentence.length(); i++){
				if(Character.isUpperCase(sentence.charAt(i))){
					if(!(sentence.charAt(i-1)==' ')){
						mistakes++;
						if(i<sentence.length()-1){
							workingSentence = workingSentence.substring(0,i) +
									Character.toLowerCase(sentence.charAt(i))+
									workingSentence.substring(i);
						}else{
							workingSentence = workingSentence.substring(0,i) +
									Character.toLowerCase(sentence.charAt(i));
						}	
					}
				}
			}
		}
		return(new Pair1<Integer, String>(mistakes, workingSentence));
	}
	
	public static int binSearch(ArrayList<String> arr, String targ, int start, int end){
		if(start>end){
			return(-1);
		}else{
			int mid = (start+end)/2;
			if(arr.get(mid).equals(targ)){
				return(mid);
			}else{
				if(targ.compareTo(arr.get(mid))<0){
					return(binSearch(arr, targ, start, mid-1));
				}else{
					return(binSearch(arr, targ, mid+1, end));
				}
			}
		}
	}
	
	public static boolean hasChar(String search, char targ){
		for(int i = 0; i<search.length(); i++){
			if(search.charAt(i)==targ){
				return(true);
			}
		}
		return(false);
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

class Pair1<I,S> {

	  private final I integer;
	  private final S str;

	  public Pair1(I integer, S str) {
	    this.integer = integer;
	    this.str = str;
	  }

	  public I getI() { return integer; }
	  public S getS() { return str; }

	  @Override
	  public int hashCode() { return integer.hashCode() ^ str.hashCode(); }

	  @SuppressWarnings("rawtypes")
	@Override
	  public boolean equals(Object o) {
	    if (!(o instanceof Pair1)) return false;
	    Pair1 pairo = (Pair1) o;
	    return this.integer.equals(pairo.getI()) &&
	           this.str.equals(pairo.getS());
	  }
	  public String toString(){
		  return (this.integer.toString() + " " + this.str);
	  }
}
