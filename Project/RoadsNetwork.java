//montesantos spiros 2300
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class RoadsNetwork{
	private String nodesPath;
	private String edgesPath;
	private ArrayList <Nodes> nodesList = new ArrayList<Nodes>(); ;
	private ArrayList <Boolean> visited;
	private ArrayList <Double> spd ;
	private ArrayList <Double> Aspd;
	private ArrayList<Integer> path;
	private ArrayList<Integer> previewsNode;
	private ArrayList<Integer> finalPath;
	private ArrayList<Nodes> priorityQueue;

	private Nodes node;
	

	public RoadsNetwork(String nodesPathname, String edgesPathname){
		this.nodesPath = nodesPathname;
		this.edgesPath = edgesPathname;
	}
	

	public void readNodes() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(nodesPath));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        String[] str = line.split(" ");
		        node = new Nodes(Integer.parseInt(str[0]), Double.parseDouble(str[1]), Double.parseDouble(str[2]));
		        nodesList.add(node);
		        
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}

	}
	
	public void readEdges() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(edgesPath));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        String[] str = line.split(" ");
		        nodesList.get(Integer.parseInt(str[1])).makeNeighbours(Integer.parseInt(str[2]),Double.parseDouble(str[3]));
		        nodesList.get(Integer.parseInt(str[2])).makeNeighbours(Integer.parseInt(str[1]),Double.parseDouble(str[3]));		        
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}

	}
	
	public ArrayList<Integer> dijkstra(int sourceNode, int targetNode) {
		spd = new ArrayList<Double>();
		int counter = 0;
		visited = new ArrayList<Boolean>();
		previewsNode = new ArrayList<Integer>();
		finalPath = new ArrayList<Integer>();
		Nodes v;
		for(int i=0; i<nodesList.size();i++) {
			spd.add(Double.MAX_VALUE);
			visited.add(false);
			previewsNode.add(-1);
		}
		path = new ArrayList<Integer>(); 
		priorityQueue = new ArrayList<Nodes>();
		priorityQueue.add(nodesList.get(sourceNode));
		spd.set(sourceNode, 0.0);
		while(!priorityQueue.isEmpty()) {
			v = priorityQueue.get(0);
			priorityQueue.remove(0);
			path.add(v.getId());
			counter++;			
			visited.set(v.getId(), true);
			if(v.getId()== targetNode) {
				int node = targetNode;
				finalPath.add(node);
				node = previewsNode.get(targetNode);
				while(node != sourceNode) {
					finalPath.add(node);
					node = previewsNode.get(node);
				}
				finalPath.add(sourceNode);
				Collections.reverse(finalPath);
				System.out.println("Shortest path: " + finalPath.toString());
				System.out.println("Cost : " + spd.get(targetNode));
				System.out.println("visited: " + counter);
				System.out.println();
				return path;
			}
			for (Integer y: v.getNeighbours().keySet()){
				if(visited.get(y) == false) {
					if(spd.get(y) > spd.get(v.getId()) + v.getNeighbours().get(y)) {
						spd.set(y,spd.get(v.getId()) + v.getNeighbours().get(y));
						previewsNode.set(y, v.getId());
						priorityQueue.add(nodesList.get(y));
						Collections.sort(priorityQueue, new Comparator<Nodes>() {
							public int compare(Nodes n1, Nodes n2) {
								return Double.valueOf(spd.get(n1.getId())).compareTo(spd.get(n2.getId()));
							}
						});
					}
				}
				
			}
			
		}
		return null;
	}
	
	public ArrayList<Integer> AstarSearch(int sourceNode, int targetNode) {
		int counter = 0;
		Aspd = new ArrayList<Double>();
		visited = new ArrayList<Boolean>();
		previewsNode = new ArrayList<Integer>();
		finalPath = new ArrayList<Integer>();
		Nodes v;
		for(int i=0; i<nodesList.size();i++) {
			Aspd.add(Double.MAX_VALUE);
			visited.add(false);
			previewsNode.add(-1);
		}
		path = new ArrayList<Integer>(); 
		priorityQueue = new ArrayList<Nodes>();
		priorityQueue.add(nodesList.get(sourceNode));
		Aspd.set(sourceNode, +this.euclidian(nodesList.get(sourceNode), nodesList.get(targetNode)));
		while(!priorityQueue.isEmpty()) {
			v = priorityQueue.get(0);
			priorityQueue.remove(0);
			path.add(v.getId());
			counter++;
			visited.set(v.getId(), true);
			if(v.getId()== targetNode) {
				int node = targetNode;
				finalPath.add(node);
				node = previewsNode.get(targetNode);
				while(node != sourceNode) {
					finalPath.add(node);
					node = previewsNode.get(node);
				}
				finalPath.add(sourceNode);
				Collections.reverse(finalPath);
				System.out.println("Shortest path: " + finalPath.toString());
				System.out.println("visited: " + counter);
				System.out.println("Cost with euclidian distances: " + Aspd.get(targetNode));
				System.out.println("Cost: " + spd.get(targetNode));
				System.out.println();
				return path;
			}
			for (Integer y: v.getNeighbours().keySet()){
				if(visited.get(y) == false) {
					if(Aspd.get(y) > Aspd.get(v.getId()) + v.getNeighbours().get(y) + this.euclidian(nodesList.get(y), nodesList.get(targetNode))) {
						Aspd.set(y,Aspd.get(v.getId()) + v.getNeighbours().get(y) + this.euclidian(nodesList.get(y), nodesList.get(targetNode)));
						previewsNode.set(y, v.getId());
						priorityQueue.add(nodesList.get(y));
						Collections.sort(priorityQueue, new Comparator<Nodes>() {
							public int compare(Nodes n1, Nodes n2) {
								return Double.valueOf(Aspd.get(n1.getId())).compareTo(Aspd.get(n2.getId()));
							}
						});
					}
				}
			}
			
		}
		return null;
	}
	
	public double euclidian(Nodes node1, Nodes node2) {
		return Math.sqrt(Math.pow(node1.getLatitude() - node2.getLatitude(), 2) + Math.pow(node1.getLongitude() - node2.getLongitude() , 2));
	}
	
	
}