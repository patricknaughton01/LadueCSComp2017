import java.io.*;
import java.util.*;
public class encrypt {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<Character> alphabet = new ArrayList(Arrays.asList(new Character[]
		{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'}));
	public static void main(String[] args) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader("encrypt.in"));
		int msgs = Integer.valueOf(r.readLine());
		for(int i = 0; i<msgs; i++){
			int shift = Integer.valueOf(r.readLine());
			String m = r.readLine().trim();
			System.out.println(shift(shift+26, m));
		}
		r.close();
	}
	
	public static String shift(int shift, String msg){
		String r = "";
		for(int i = 0; i<msg.length(); i++){
			if(!Character.isAlphabetic(msg.charAt(i))){
				r+=msg.charAt(i);
			}else{
				boolean isLower = Character.isLowerCase(msg.charAt(i));
				char c = Character.toUpperCase(msg.charAt(i));
				char x = alphabet.get((alphabet.indexOf(c)+shift)%alphabet.size());
				if(isLower){
					x = Character.toLowerCase(x);
				}
				r+=x;
			}
		}
		return(r);
	}

}
