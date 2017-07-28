import java.io.*;
import java.util.*;

public class grammar {
	public static ArrayList<String> dictionary = new ArrayList<String>();
	public static String[] apoSuff = {"\'s", "s\'"};
	public static ArrayList<String> contractions = new ArrayList<String>(
			Arrays.asList(new String[] {
		"aren't",
		"can't",
		"could've",
		"couldn't",
		"didn't",
		"doesn't",
		"don't",
		"hadn't",
		"hasn't",
		"haven't",
		"he'd",
		"he'll",
		"he's",
		"how'd",
		"how'll",
		"how's",
		"i'd",
		"i'll",
		"i'm",
		"i've",
		"I'd",
		"I'll",
		"I'm",
		"I've",
		"isn't",
		"it'd",
		"it'll",
		"it's",
		"mayn't",
		"may've",
		"mightn't",
		"might've",
		"mustn't",
		"must've",
		"needn't",
		"o'clock",
		"oughtn't",
		"shan't",
		"she'd",
		"she'll",
		"she's",
		"should've",
		"shouldn't",
		"somebody's",
		"something's",
		"that'll",
		"that're",
		"that's",
		"that'd",
		"there'd",
		"there're",
		"there's",
		"these're",
		"they'd",
		"they'll",
		"they're",
		"they've",
		"this's",
		"those're",
		"'tis",
		"'twas",
		"wasn't",
		"we'd",
		"we'd've",
		"we'll",
		"we're",
		"we've",
		"weren't",
		"what'd",
		"what'll",
		"what're",
		"what's",
		"what've",
		"when's",
		"where'd",
		"where're",
		"where's",
		"where've",
		"which's",
		"who'd",
		"who'd've",
		"who'll",
		"who're",
		"who's",
		"who've",
		"why'd",
		"why're",
		"why's",
		"won't",
		"willn't",
		"would've",
		"wouldn't",
		"y'all",
		"you'd",
		"you'll",
		"you're",
		"you've",
	}));
	public static String[] puncLiterals = {"Dr.", "Mr.", 
		"Mrs.", "Ms.", "i.e.", "e.g.", "etc."};
	public static void main(String[] args) throws IOException{
		ArrayList<String> essays = new ArrayList<String>();
		
		BufferedReader r = new BufferedReader(new FileReader("grammar.in"));
		for(int i = 0; i<370101; i++){
			dictionary.add(r.readLine().trim());
		}
		dictionary.add(145454, "I");
		int numEssays = Integer.valueOf(r.readLine().trim());
		for(int i = 0; i<numEssays; i++){
			essays.add(r.readLine());
		}
		r.close();
		BufferedWriter w = new BufferedWriter(new FileWriter("grammar.out"));
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
			ArrayList<int[]> quoteFlags = new ArrayList<int[]>();
			//these flags contain the indicies of quotation marks
			int[] pair = {0,0};
			int activeInd = 0;
			for(int j = 0; j<essays.get(i).length(); j++){
				if(essays.get(i).substring(j, j+1).equals("\"")){
					if(activeInd == 0){
						pair[activeInd] = j;
						activeInd++;
					}else{
						pair[1] = j;
						activeInd = 0;
						quoteFlags.add(pair);
						pair = new int[2];
					}
				}
			}
			ArrayList<String> sentences = new ArrayList<String>();
			//Each entry will not contain the punctuation that caused the split
			int start = 0;
			for(int j = 0; j<essays.get(i).length(); j++){
				if(!flagged(flags, j) && !quoteFlagged(quoteFlags, j, essays.get(i))){
					if(essays.get(i).substring(j, j+1).equals(".")||
							essays.get(i).substring(j, j+1).equals("?")||
							essays.get(i).substring(j, j+1).equals("!")){
						sentences.add(essays.get(i).substring(start, j).trim());
						start = j+1;
					}
				}
			}
			//System.err.println(sentences);
			int count = 0;
			for(int j = 0; j<sentences.size(); j++){
				Pair1<Integer, String> caps = checkCaps(sentences.get(j));
				sentences.set(j, caps.getS());
				int spelling = checkSpelling(sentences.get(j));
				int apo = checkApo(sentences.get(j));
				count += spelling + apo + caps.getI();
			}
			w.write(Integer.toString(count) + "\n");
		}
		w.close();
	}
	
	public static int checkApo(String sentence){
		int mistakes = 0;
		String[] words = removePunc(sentence.split(" "));
		for(int i = 0; i<words.length; i++){
			int ind;
			if(words[i].contains("\'") && Character.isLowerCase(words[i].charAt(0))){
				if(!contractions.contains(words[i])){
					if((ind = apoSuffInd(words[i]))!=-1 && ind==words[i].length()){
						if(binSearch(dictionary, 
								words[i].substring(0, ind-2), 0, dictionary.size())==-1
								&& Character.isLowerCase(words[i].charAt(0))){
							System.err.println(words[i]);
							mistakes++;
						}
					}else{
						mistakes++;
						//System.err.println(words[i]);
					}
				}
			}
		}
		return(mistakes);
	}
	
	public static String[] removePunc(String[] in){
		String[] r = new String[in.length];
		for(int i = 0 ;i<in.length; i++){
			r[i] = removePunc(in[i]);
		}
		return(r);
	}
	
	public static String removePunc(String in){
		String r = "";
		for(int i = 0 ;i<in.length(); i++){
			if(Character.isLetterOrDigit(in.charAt(i)) || in.charAt(i)=='\''
					|| in.charAt(i)==' '){
				r+=in.charAt(i);
			}
		}
		return(r);
	}
	
	public static String trimPunc(String in){
		int start = 0;
		int end = in.length()-1;
		while(!Character.isLetterOrDigit(in.charAt(start))){
			start++;
		}
		while(!Character.isLetterOrDigit(in.charAt(end))){
			end--;
		}
		return(in.substring(start, end+1));
	}
	
	public static String[] trimPunc(String[] in){
		String[] r = new String[in.length];
		for(int i = 0; i<in.length; i++){
			int start = 0;
			int end = in[i].length()-1;
			while(!Character.isLetterOrDigit(in[i].charAt(start))){
				start++;
			}
			while(!Character.isLetterOrDigit(in[i].charAt(end))){
				end--;
			}
			r[i] = in[i].substring(start, end+1);
		}
		return(r);
	}
	
	public static int apoSuffInd(String word){
		for(int i = 0; i<apoSuff.length; i++){
			int ind;
			if((ind = word.indexOf(apoSuff[i]))!=-1){
				return(ind+apoSuff[i].length());
			}
		}return(-1);
	}
	
	public static int checkSpelling(String sentence){
		int start = 0;
		int mistakes = 0;
		for(int i = 0; i<sentence.length(); i++){
			if(sentence.charAt(i)==' '){
				String word = trimPunc(sentence.substring(start, i)).trim();
				if(Character.isLowerCase(word.charAt(0)) &&
						!hasChar(word, '\'') && 
						binSearch(puncLiterals, word, 0, puncLiterals.length)==-1){
					if(binSearch(dictionary, word, 
							0, dictionary.size())==-1){
						mistakes++;
						System.err.println(word);
					}
				}
				start = i+1;
			}
		}
		return(mistakes);
	}
	
	public static Pair1<Integer,String> checkCaps(String sentence){
		String workingSentence = removePunc(sentence).trim();
		//System.err.println("working: " + workingSentence);
		int mistakes = 0;
		if(workingSentence.length()>0){
			if(Character.isLowerCase(workingSentence.charAt(0))){
				mistakes++;
				System.err.println(workingSentence);
			}else{
				workingSentence = Character.toLowerCase(workingSentence.charAt(0)) + 
						workingSentence.substring(1);
			}
			for(int i = 1; i<workingSentence.length(); i++){
				if(Character.isUpperCase(workingSentence.charAt(i))){
					if(!(workingSentence.charAt(i-1)==' ')){
						System.err.println(workingSentence);
						mistakes++;
						if(i<workingSentence.length()-1){
							workingSentence = workingSentence.substring(0,i) +
									Character.toLowerCase(workingSentence.charAt(i))+
									workingSentence.substring(i+1);
						}else{
							workingSentence = workingSentence.substring(0,i) +
									Character.toLowerCase(workingSentence.charAt(i));
						}	
					}
				}
			}
		}
		return(new Pair1<Integer, String>(mistakes, workingSentence));
	}
	
	public static int binSearch(String[] arr, String targ, int start, int end){
		if(start>end){
			return(-1);
		}else{
			int mid = (start+end)/2;
			if(mid>arr.length-1 || mid<0){
				return(-1);
			}
			else if(arr[mid].equals(targ)){
				return(mid);
			}else{
				if(targ.compareTo(arr[mid])<0){
					return(binSearch(arr, targ, start, mid-1));
				}else{
					return(binSearch(arr, targ, mid+1, end));
				}
			}
		}
	}
	
	public static int binSearch(ArrayList<String> arr, String targ, int start, int end){
		if(start>end){
			return(-1);
		}else{
			int mid = (start+end)/2;
			if(mid>arr.size()-1 || mid<0){
				return(-1);
			}
			else if(arr.get(mid).equals(targ)){
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
	
	public static boolean quoteFlagged(ArrayList<int[]> qFlags, int ind, String ess){
		for(int i = 0; i<qFlags.size(); i++){
			if(ind == qFlags.get(i)[1]-1){
				if(ess.length()>qFlags.get(i)[1]+1){
					if(ess.length()>qFlags.get(i)[1]+2){
						if(Character.isUpperCase(ess.charAt(qFlags.get(i)[1]+2))){
							return(false);
						}else{
							return(true);
						}
					}
				}else{
					return(false);
				}
			}
		}return(false);
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
