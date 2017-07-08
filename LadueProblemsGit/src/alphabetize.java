import java.io.*;
import java.util.*;
public class alphabetize {

	public static void main(String[] args) throws IOException{
		ArrayList<String> words = new ArrayList<String>();
		BufferedReader r = new BufferedReader(new FileReader("words_alpha.txt"));
		String line;
		while((line = r.readLine())!=null){
			words.add(line);
		}
		r.close();
		words = sortStr(words);
		BufferedWriter w = new BufferedWriter(new FileWriter("words.out"));
		for(int i = 0; i<words.size(); i++){
			w.write(words.get(i) + "\n");
		}
		w.close();

	}
	public static ArrayList<String> sortStr(ArrayList<String> a){
		if(a.size()>1){
			ArrayList<String> a1 = new ArrayList<String>(a.subList(0, a.size()/2));
			ArrayList<String> a2 = new ArrayList<String>(a.subList(a.size()/2, a.size()));
			return(mergeStr(sortStr(a1), sortStr(a2)));
		}else{
			return(a);
		}
	}
	public static ArrayList<String> mergeStr(ArrayList<String> a1, ArrayList<String> a2){
		ArrayList<String> r = new ArrayList<String>();
		while(a1.size()>0 && a2.size()>0){
			if((a1.get(0)).compareTo(
					(a2.get(0)))<0){
				r.add(a1.get(0));
				a1.remove(0);
			}else{
				r.add(a2.get(0));
				a2.remove(0);
			}
		}
		if(a1.size()>0){
			r.addAll(a1);
		}else if(a2.size()>0){
			r.addAll(a2);
		}
		return(r);
	}

}
