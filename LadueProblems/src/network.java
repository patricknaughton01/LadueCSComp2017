import java.io.*;
import java.util.*;

public class network {

	public static void main(String[] args) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader("network.in"));
		int sets = Integer.valueOf(r.readLine().trim());
		for(int i = 0; i<sets; i++){
			int numEdges = Integer.valueOf(r.readLine().trim());
			ArrayList<String[]> edges = new ArrayList<String[]>();
			for(int j = 0; j<numEdges; j++){
				edges.add(r.readLine().trim().split(" "));
			}
			Graph g = new Graph(edges);
			g.findCutNodes();
			//System.err.println(g.cutNodes);
			if(g.cutNodes.size()==0){
				System.out.println("0");
			}else{
				int newEdges = 0;
				for(int j = 0; j<g.cutNodes.size(); j++){
					Graph c = g.getCutGraph(g.cutNodes.get(j));
					ArrayList<String> unreach = c.getUnreachableNodes(c.nodes.get(0));
					while(unreach.size()>0){
						//System.err.println("unreach: " + unreach);
						for(int k = 0; k<unreach.size(); k++){
							if(!g.cutNodes.contains(unreach.get(k))){
								c.addEdge(c.nodes.get(0), unreach.get(k));
								g.addEdge(c.nodes.get(0), unreach.get(k));
								//System.err.println(g + "\n");
								newEdges++;
								k = unreach.size();
							}
						}
						unreach = c.getUnreachableNodes(c.nodes.get(0));
					}
				}
				System.out.println(newEdges);
			}
		}
		r.close();
	}

}

class Graph{
	public ArrayList<String> nodes = new ArrayList<String>();
	public ArrayList<String[]> edges = new ArrayList<String[]>();
	public HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
	public ArrayList<String> cutNodes = new ArrayList<String>();
	public ArrayList<String> unCutNodes = new ArrayList<String>();
	@SuppressWarnings("unchecked")
	public Graph(ArrayList<String[]> edges){
		for(int i = 0; i<edges.size(); i++){
			if(!nodes.contains(edges.get(i)[0])){
				nodes.add(edges.get(i)[0]);
			}if(!nodes.contains(edges.get(i)[1])){
				nodes.add(edges.get(i)[1]);
			}
		}
		this.edges = (ArrayList<String[]>) edges.clone();
		this.clearVisitation();
		this.cutNodes = new ArrayList<String>();
		this.unCutNodes = new ArrayList<String>();
	}
	
	public ArrayList<String> getUnreachableNodes(String node){
		ArrayList<String> r = new ArrayList<String>();
		int[] dist = this.edgeDistance(node);
		for(int i = 0; i<dist.length; i++){
			if(dist[i]==-1){
				r.add(this.nodes.get(i));
			}
		}return(r);
	}
	
	public void findCutNodes(){
		for(int i = 0; i<nodes.size(); i++){
			if(isCutNode(nodes.get(i))){
				this.cutNodes.add(nodes.get(i));
			}else{
				this.unCutNodes.add(nodes.get(i));
			}
		}
	}
	
	public void addEdge(String node1, String node2){
		if(!nodes.contains(node1)){
			nodes.add(node1);
		}if(!nodes.contains(node2)){
			nodes.add(node2);
		}
		String[] t = {node1, node2};
		edges.add(t);
	}
	
	public Graph getCutGraph(String cutNode){
		Graph copy = new Graph(this.edges);
		copy.removeConnectedEdges(cutNode);
		copy.removeNode(cutNode);
		return(copy);
	}
	
	public boolean isCutNode(String node){
		Graph copy = new Graph(this.edges);
		copy.removeConnectedEdges(node);
		copy.removeNode(node);
		if(copy.nodes.size()==0){
			return(false);
		}
		int[] d = copy.edgeDistance(copy.nodes.get(0));
		for(int i = 0; i<d.length; i++){
			if(d[i]==-1){
				return(true);
			}
		}return(false);
	}
	
	public int[] edgeDistance(String startNode){
		this.clearVisitation();
		int[] dist = new int[this.nodes.size()];
		for(int i = 0; i<dist.length; i++){
			dist[i]=-1;
		}
		int ind = this.nodes.indexOf(startNode);
		dist[ind]=0;
		visited.replace(startNode, true);
		ArrayList<String> hold = new ArrayList<String>();
		ArrayList<Integer> dis = new ArrayList<Integer>();
		ArrayList<String> neighbors = getNeighbors(startNode);
		hold.addAll(neighbors);
		for(int i = 0; i<hold.size(); i++){
			dis.add(1);
			dist[nodes.indexOf(hold.get(i))] = 1;
			visited.replace(hold.get(i), true);
		}
		while(hold.size()>0){
			//System.err.println("hold " + hold);
			//System.err.println("dis  " + dis);
			dist[nodes.indexOf(hold.get(0))] = dis.get(0);
			visited.replace(hold.get(0), true);
			ArrayList<String> newNeighbors = getNeighbors(hold.get(0));
			for(int i = 0; i<newNeighbors.size(); i++){
				if(!visited.get(newNeighbors.get(i))){
					hold.add(newNeighbors.get(i));
					dis.add(dis.get(0)+1);
					dist[nodes.indexOf(newNeighbors.get(i))] = dis.get(0)+1;
				}
			}
			hold.remove(0);
			dis.remove(0);
		}
		//System.err.println(Arrays.toString(dist));
		return(dist);
	}
	
	public ArrayList<String> getNeighbors(String node){
		ArrayList<String> neighbors = new ArrayList<String>();
		for(int i = 0; i<edges.size(); i++){
			if(edges.get(i)[0].equals(node)){
				neighbors.add(edges.get(i)[1]);
			}else if(edges.get(i)[1].equals(node)){
				neighbors.add(edges.get(i)[0]);
			}
		}
		return(neighbors);
	}
	
	public void removeNode(String node){
		for(int i = nodes.size()-1; i>=0; i--){
			if(nodes.get(i).equals(node)){
				nodes.remove(i);
			}
		}
	}
	
	public void removeConnectedEdges(String node){
		for(int i = edges.size()-1; i>-1; i--){
			if(edges.get(i)[0].equals(node)||edges.get(i)[1].equals(node)){
				edges.remove(i);
			}
		}
	}
	
	public void removeEdge(String node1, String node2){
		for(int i = edges.size()-1; i>-1; i--){
			if(edges.get(i)[0].equals(node1) && edges.get(i)[1].equals(node2)||
					edges.get(i)[0].equals(node2) && edges.get(i)[1].equals(node1)){
				edges.remove(i);
			}
		}
	}
	
	public void clearVisitation(){
		this.visited = new HashMap<String, Boolean>();
		for(int i = 0; i<nodes.size(); i++){
			visited.put(nodes.get(i), false);
		}
	}
	
	public String toString(){
		String out = "";
		for(int i = 0; i<edges.size(); i++){
			out += Arrays.toString(edges.get(i)) + "\n";
		}
		return(out);
	}
}