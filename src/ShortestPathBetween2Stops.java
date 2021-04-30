//		@author Stephen Davis

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShortestPathBetween2Stops {

	public EdgeWeightedDigraph graph;
	public HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	public int mapIndex = 0;

	/**
	 * @param filename: A filename containing the details of the city road network
	 */
	ShortestPathBetween2Stops (String filename){
		graph = initialiseVertexSizedGraph(filename);
		graph = addEdgesFromTransfers_txt();
		graph = addEdgesFromStop_Times_Txt();
	}

	public EdgeWeightedDigraph initialiseVertexSizedGraph(String filename) {
		if(filename==null) {
			return null;
		}
		File file = new File(filename);
		int numberOfVertices = 0;
		try {	
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				numberOfVertices++;
				scanner.nextLine();
			}
			numberOfVertices-=1;									// 1st line in input is not a bus stop
			graph = new EdgeWeightedDigraph(numberOfVertices);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		return graph;
	}

	public EdgeWeightedDigraph addEdgesFromTransfers_txt() {
		// create edges from transfers.txt
		try {
			File file = new File("transfers.txt");
			Scanner scanner = new Scanner(file);
			String test = scanner.nextLine();
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] lineContents = line.split(",");
				int v = Integer.parseInt(lineContents[0]);
				if(map.get(v)==null) {	// if bus stop not already encountered
					map.put(v, mapIndex);
					mapIndex++;
				}
				int w = Integer.parseInt(lineContents[1]);
				if(map.get(w)==null) {	// if bus stop not already encountered
					map.put(w, mapIndex);
					mapIndex++;
				}
				int transferType = Integer.parseInt(lineContents[2]);
				double weight = 0;
				if(transferType==0) {
					weight = 2;	
				}
				else if(transferType==2) {
					double minimumTransferTime = Double.parseDouble(lineContents[3]);
					weight = minimumTransferTime/100;
				}
				DirectedEdge edge = new DirectedEdge(map.get(v), map.get(w), weight);
				graph.addEdge(edge);
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		return graph;
	}

	public EdgeWeightedDigraph addEdgesFromStop_Times_Txt() {
		// create edges from stop_times.txt
		File file = new File("stop_times.txt");
		try {	
			Scanner scanner = new Scanner(file);
			String firstLine = scanner.nextLine();
			String currentLine = scanner.nextLine();
			while(scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				String[] currentLineContents = currentLine.split(",");
				String[] nextLineContents = nextLine.split(",");
				if(currentLineContents[0].equalsIgnoreCase(nextLineContents[0])) {
					String currentStopId = currentLineContents[3];
					int v = Integer.parseInt(currentStopId);	// convert from String to int
					if(map.get(v)==null) {	// if bus stop not already encountered
						map.put(v, mapIndex);
						mapIndex++;
					}
					String nextStopId = nextLineContents[3];
					int w = Integer.parseInt(nextStopId);		// convert from String to int
					if(map.get(w)==null) {	// if bus stop not already encountered
						map.put(w, mapIndex);
						mapIndex++;
					}
					double weight = 1;
					DirectedEdge edge = new DirectedEdge(map.get(v), map.get(w), weight);
					graph.addEdge(edge);
				}
				currentLine = nextLine;
			}	
			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		return graph;
	}

	public static void getShortestPath() {
		String filename = "stops.txt";
		ShortestPathBetween2Stops sp = new ShortestPathBetween2Stops(filename); 
		
		
		String getSourceString = "Please enter the stop_id of the source stop: ";
		String getDestinationString = "Please enter the stop_id of the destination stop: ";
		
		int sourceStop = getUserInput(sp, getSourceString);
		int destinationStop = getUserInput(sp, getDestinationString);
		
		int sourceStopIndex = sp.map.get(sourceStop);
		int destinationStopIndex = sp.map.get(destinationStop);
		
		DijkstraSP dijkstraGraph = new DijkstraSP(sp.graph, sourceStopIndex);	
		
		if(dijkstraGraph.hasPathTo(destinationStopIndex)) {
			double pathLength = dijkstraGraph.distTo(destinationStopIndex);
			System.out.println("Cost: " + pathLength);
			
			Iterable<DirectedEdge> stopList = dijkstraGraph.pathTo(destinationStopIndex);
			System.out.println("List of stops en route (and associated costs):");
			for(DirectedEdge stop: stopList) {
				System.out.println("Stop ID: " + stop.to() + "\t Cost (from prev): " + stop.weight());
			}
			
		}
		else {
			System.out.println("There's no path from stop \"" + sourceStop + "\" to stop \"" + destinationStop + "\"");
		}
	}
	
	
	public static int getUserInput(ShortestPathBetween2Stops sp, String getInputString) {
		Scanner scanner = new Scanner(System.in);
		boolean valid = false;
		int stop = -1;
		while(!valid) {
			System.out.println(getInputString);
			
			if(scanner.hasNextInt()) {
				stop = scanner.nextInt();
				
				if(sp.map.containsKey(stop)) {
					valid = true;
				}
				
				else {
					System.out.println("There is no stop with the id: \"" + stop + "\" on our system.");
				}
			}
			else {
				System.out.println("Invalid input. Please only enter integers.");
				scanner.nextLine();				
			}
		}
		return stop;
	}
}