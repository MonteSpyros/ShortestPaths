//montesantos spiros 2300

import java.util.ArrayList;
import java.util.HashMap;

public class Nodes implements Comparable<Nodes>{
	
	private int id;
	private double longitude;
	private double latitude;
	private HashMap<Integer,Double> neighbours = new HashMap<Integer,Double>();
//	private ArrayList<Integer> neighbours = new ArrayList<Integer>();
//	private ArrayList<Double> distance = new ArrayList<Double>();
	//private double distance;
	
	public Nodes(int id, double longitude, double latitude) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	
	public void makeNeighbours(int id, double value) {
		this.neighbours.put(id, value);
	}
	
	public void printNeighbours() {
		for (Integer i: neighbours.keySet()){
            System.out.println(i + " exei distance" + neighbours.get(i));  
		}
	}
	

	public HashMap<Integer, Double> getNeighbours() {
        return neighbours;
   }

	public int getId() {
		return id;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}


	public int compareTo(Nodes other) {
		return Double.compare(neighbours.get(id), other.getNeighbours().get(other.id));
	}



}
