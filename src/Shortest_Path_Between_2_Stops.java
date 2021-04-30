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

	public static void main(String[] args) {
		String filename = "stops.txt";
		// populate graph
		Shortest_Path_Between_2_Stops sp2stops = new Shortest_Path_Between_2_Stops(filename); 
		// NEED TO ACCOUNT FOR INVALID USER INPUT
		Scanner scanner = new Scanner(System.in);
		boolean valid_input = false;
		System.out.println("Please enter the stop_id of the source stop: ");
		int source_stop = -5;
		if(scanner.hasNextInt()) {
			source_stop = scanner.nextInt();
		}
		if(sp2stops.map.containsKey(source_stop)) {
			valid_input = true;
		}	
		while(valid_input==false) {
			System.out.println("Invalid input. Please try again: ");
			if(scanner.hasNextInt()) {
				source_stop = scanner.nextInt();
				if(sp2stops.map.containsKey(source_stop)) {
					valid_input = true;
				}
			}
		}
		int source_stop_index = sp2stops.map.get(source_stop);
		DijkstraSP shortest_path = new DijkstraSP(sp2stops.graph, source_stop_index);
		System.out.println("Please enter the stop_id of the destination stop: ");
		int destination_stop=-5;
		if(scanner.hasNextInt()) {
			destination_stop = scanner.nextInt();
		}
		valid_input = false;
		if(sp2stops.map.containsKey(destination_stop)) {
			valid_input = true;
		}	
		while(valid_input==false) {
			System.out.println("Invalid input. Please try again: ");
			if(scanner.hasNextInt()) {
				destination_stop = scanner.nextInt();
				if(sp2stops.map.containsKey(destination_stop)) {
					valid_input = true;
				}
			}
		}
		int destination_stop_index = sp2stops.map.get(destination_stop);
		double path_length = shortest_path.distTo(destination_stop_index); 		// find shortest path between the above 2 stops
		System.out.println("Cost: "+path_length);
		//		 return list of stops
		//		 return the associated cost


		// 1888
		// 11259
	}
}
