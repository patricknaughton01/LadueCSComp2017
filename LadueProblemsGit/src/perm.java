import java.util.*;
import java.io.*;
public class perm {
	public static void main(String[] args) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader("perm.in"));
		ArrayList<String> list = new ArrayList<String>(
				Arrays.asList(r.readLine().trim().split(" ")));
		r.close();
		ArrayList<ArrayList<String>> ps = perms(list);
		BufferedWriter w = new BufferedWriter(new FileWriter("perm.ans"));
		for(ArrayList<String> a:ps){
			w.write(a.toString().substring(1, a.toString().length()-1) + "\n");
		}
		w.close();
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<String>> perms(ArrayList<String> a){
		ArrayList<ArrayList<String>> r = new ArrayList<ArrayList<String>>();
		ArrayList<String> usedBefore = new ArrayList<String>();
		if(a.size()>1){
			for(int i = 0; i<a.size(); i++){
				if(!usedBefore.contains(a.get(i))){
					ArrayList<String> tmp = (ArrayList<String>) a.clone();
					tmp.remove(i);
					String hold = a.get(i);
					usedBefore.add(a.get(i));
					ArrayList<ArrayList<String>> rem = perms(tmp);
					for(int j = 0; j<rem.size(); j++){
						ArrayList<String> add = rem.get(j);
						add.add(0, hold);
						r.add(add);
					}
				}
			}
		}else{
			r.add(a);
		}
		return(r);
	}

}
