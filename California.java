//montesantos spiros 2300
import java.io.IOException;
import java.util.Scanner;

public class California {

	public static void main(String[] args) throws IOException {
		
		
		RoadsNetwork roads = new RoadsNetwork("/Users/spirosmontesantos/Desktop/Nodes.txt" , "/Users/spirosmontesantos/Desktop/Edges.txt");
		
		roads.readNodes();
		roads.readEdges();
		
		roads.dijkstra(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		roads.AstarSearch(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		//roads.dijkstra(0,8);
		//roads.AstarSearch(0, 8);

	}

}
