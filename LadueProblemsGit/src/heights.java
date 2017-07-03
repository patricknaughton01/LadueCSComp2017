import java.io.*;
import java.util.*;

public class heights {
	public static ArrayList<String> gOrder = new ArrayList<String>(
			Arrays.asList(new String[] {"SE", "J", "SO", "F"}));
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader("heights.in"));
		ArrayList<Pair> students = new ArrayList<Pair>();
		for(int i = 0; i<1000000; i++){
			String[] t = r.readLine().trim().split(" ");
			students.add(new Pair(Integer.valueOf(t[0]), t[1]));
		}
		students = sortStr(students);
		students = sortInt(students);
		System.out.println(students);
		r.close();

	}
	
	@SuppressWarnings("rawtypes")
	public static ArrayList<Pair> sortInt(ArrayList<Pair> a){
		if(a.size()>1){
			ArrayList<Pair> a1 = new ArrayList<Pair>(a.subList(0, a.size()/2));
			ArrayList<Pair> a2 = new ArrayList<Pair>(a.subList(a.size()/2, a.size()));
			return(mergeInt(sortInt(a1), sortInt(a2)));
		}else{
			return(a);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static ArrayList<Pair> mergeInt(ArrayList<Pair> a1, ArrayList<Pair> a2){
		ArrayList<Pair> r = new ArrayList<Pair>();
		while(a1.size()>0 && a2.size()>0){
			if((Integer)a1.get(0).getI()>(Integer)a2.get(0).getI()){
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
	@SuppressWarnings("rawtypes")
	public static ArrayList<Pair> sortStr(ArrayList<Pair> a){
		if(a.size()>1){
			ArrayList<Pair> a1 = new ArrayList<Pair>(a.subList(0, a.size()/2));
			ArrayList<Pair> a2 = new ArrayList<Pair>(a.subList(a.size()/2, a.size()));
			return(mergeStr(sortStr(a1), sortStr(a2)));
		}else{
			return(a);
		}
	}
	@SuppressWarnings("rawtypes")
	public static ArrayList<Pair> mergeStr(ArrayList<Pair> a1, ArrayList<Pair> a2){
		ArrayList<Pair> r = new ArrayList<Pair>();
		while(a1.size()>0 && a2.size()>0){
			if(gOrder.indexOf(((String)a1.get(0).getS()))>
					gOrder.indexOf(((String)a2.get(0).getS()))){
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

class Pair<I,S> {

	  private final I integer;
	  private final S str;

	  public Pair(I integer, S str) {
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
	    if (!(o instanceof Pair)) return false;
	    Pair pairo = (Pair) o;
	    return this.integer.equals(pairo.getI()) &&
	           this.str.equals(pairo.getS());
	  }
	  public String toString(){
		  return ("[" + this.integer.toString() + ", " + this.str + "]");
	  }
}
