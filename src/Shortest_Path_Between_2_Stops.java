//		@author Stephen Davis

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Shortest_Path_Between_2_Stops {

	public EdgeWeightedDigraph graph;
	public HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	public int mapIndex = 0;

	/**
	 * @param filename: A filename containing the details of the city road network
	 */
	Shortest_Path_Between_2_Stops (String filename){

		graph = initialiseVertexSizedGraph(filename);
		graph = addEdgesFromTransfers_txt();
		graph = addEdgesFromStop_Times_Txt();
		// count number of vertices from stops.txt
		//		if(filename==null) {
		//			return;
		//		}
		//		File file = new File(filename);
		//		int number_of_vertices = 0;
		//		int number_of_edges = 0;
		//		try {	
		//			Scanner scanner = new Scanner(file);
		//			while(scanner.hasNextLine()) {
		//				number_of_vertices++;
		//				scanner.nextLine();
		//			}
		//			number_of_vertices-=1;									// 1st line in input is not a bus stop
		//			graph = new EdgeWeightedDigraph(number_of_vertices);
		//		}
		//		catch (FileNotFoundException e) {
		//			System.out.println("File not found.");
		//			e.printStackTrace();
		//		}


		//		// create edges from transfers.txt
		//		try {
		//			File file2 = new File("transfers.txt");
		//			Scanner scanner2 = new Scanner(file2);
		//			String test = scanner2.nextLine();
		//			while(scanner2.hasNextLine()) {
		//				String line = scanner2.nextLine();
		//				String[] lineContents = line.split(",");
		//				int v = Integer.parseInt(lineContents[0]);
		//				int w = Integer.parseInt(lineContents[1]);
		//				int transferType = Integer.parseInt(lineContents[2]);
		//				double weight = 0;
		//				if(transferType==0) {
		//					weight = 2;	
		//				}
		//				else if(transferType==2) {
		//					double minimumTransferTime = Double.parseDouble(lineContents[3]);
		//					weight = minimumTransferTime/100;
		//				}
		//				DirectedEdge edge = new DirectedEdge(v, w, weight);
		//				graph.addEdge(edge);
		//			}
		//		}
		//		catch (FileNotFoundException e) {
		//			System.out.println("File not found.");
		//			e.printStackTrace();
		//		}

		//		// create edges from stop_times.txt
		//		File file3 = new File("stop_times.txt");
		//		try {	
		//			Scanner scanner3 = new Scanner(file3);
		//			String first_line = scanner3.nextLine();
		//			String currentLine = scanner3.nextLine();
		//			while(scanner3.hasNextLine()) {
		//				String nextLine = scanner3.nextLine();
		//				String[] currentLineContents = currentLine.split(",");
		//				String[] nextLineContents = nextLine.split(",");
		//				if(currentLineContents[0].equalsIgnoreCase(nextLineContents[0])) {
		//					String current_stop_id = currentLineContents[3];
		//					int v = Integer.parseInt(current_stop_id);	// convert from String to int
		//					String next_stop_id = nextLineContents[3];
		//					int w = Integer.parseInt(next_stop_id);		// convert from String to int
		//					double weight = 1;
		//					DirectedEdge edge = new DirectedEdge(v, w, weight);
		//					graph.addEdge(edge);
		//				}
		//				currentLine = nextLine;
		//			}	
		//			scanner3.close();
		//		}
		//		catch (FileNotFoundException e) {
		//			System.out.println("File not found.");
		//			e.printStackTrace();
		//		}
	}

	public EdgeWeightedDigraph initialiseVertexSizedGraph(String filename) {
		if(filename==null) {
			return null;
		}
		File file = new File(filename);
		int number_of_vertices = 0;
		try {	
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				number_of_vertices++;
				scanner.nextLine();
			}
			number_of_vertices-=1;									// 1st line in input is not a bus stop
			graph = new EdgeWeightedDigraph(number_of_vertices);
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
			File file2 = new File("transfers.txt");
			Scanner scanner2 = new Scanner(file2);
			String test = scanner2.nextLine();
			while(scanner2.hasNextLine()) {
				String line = scanner2.nextLine();
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
		File file3 = new File("stop_times.txt");
		try {	
			Scanner scanner3 = new Scanner(file3);
			String first_line = scanner3.nextLine();
			String currentLine = scanner3.nextLine();
			while(scanner3.hasNextLine()) {
				String nextLine = scanner3.nextLine();
				String[] currentLineContents = currentLine.split(",");
				String[] nextLineContents = nextLine.split(",");
				if(currentLineContents[0].equalsIgnoreCase(nextLineContents[0])) {
					String current_stop_id = currentLineContents[3];
					int v = Integer.parseInt(current_stop_id);	// convert from String to int
					if(map.get(v)==null) {	// if bus stop not already encountered
						map.put(v, mapIndex);
						mapIndex++;
					}
					String next_stop_id = nextLineContents[3];
					int w = Integer.parseInt(next_stop_id);		// convert from String to int
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
			scanner3.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		return graph;
	}

	public static void getShortestPath() {
		String filename = "stops.txt";
		Shortest_Path_Between_2_Stops sp = new Shortest_Path_Between_2_Stops(filename); 
		
		
		String getSourceString = "Please enter the stop_id of the source stop: ";
		String getDestinationString = "Please enter the stop_id of the destination stop: ";
		
		int sourceStop = getUserInput(sp, getSourceString);
		int destinationStop = getUserInput(sp, getDestinationString);
		
		int sourceStopIndex = sp.map.get(sourceStop);
		int destinationStopIndex = sp.map.get(destinationStop);
		
		DijkstraSP dijkstraGraph = new DijkstraSP(sp.graph, sourceStopIndex);	
		
		if(dijkstraGraph.hasPathTo(destinationStopIndex)) {
			double pathLength = dijkstraGraph.distTo(destinationStopIndex);
			System.out.println("Time (cost): " + pathLength);
			
			Iterable<DirectedEdge> stopList = dijkstraGraph.pathTo(destinationStopIndex);
			System.out.println("List of stops en route (and associated costs):");
			for(DirectedEdge stop: stopList) {
				System.out.println("Stop ID: " + stop.to() + "\t Time (from prev): " + stop.weight());
			}
			
		}
		else {
			System.out.println("There's no path from stop \"" + sourceStop + "\" to stop \"" + destinationStop + "\"");
		}
	}
	
	
	public static int getUserInput(Shortest_Path_Between_2_Stops sp, String getInputString) {
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
