import java.io.*;
public class towerSampleGenerator {
	public static final int MAX_DATASETS = 100;
	public static final int MAX_TOWNS = 100;
	public static final double MAX_D = 1000.0;
	public static void main(String[] args) throws IOException{
		int datasets = 1 + (int)(Math.random()*MAX_DATASETS);
		BufferedWriter w = new BufferedWriter(new FileWriter("tower.in"));
		w.write(Integer.toString(datasets) + "\n");
		for(int i = 0; i<datasets; i++){
			int towns = 1 + (int)(Math.random()*MAX_TOWNS);
			w.write(Integer.toString(towns) + "\n");
			for(int j = 0; j<towns; j++){
				double x = -MAX_D + (Math.random()*MAX_D);
				x += Math.random()*MAX_D;
				double y = -MAX_D + (Math.random()*MAX_D);
				y += Math.random()*MAX_D;
				String o = Double.toString(x) + " " + Double.toString(y) + "\n";
				w.write(o);
			}
		}
		w.close();
	}

}
