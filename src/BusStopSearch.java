import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

public class BusStopSearch {

	public static final String[] STOP_KEYWORDS = new String[] {"FLAGSTOP", "WB", "NB", "SB", "EB"};
	public static final String[] BUS_STOP_VARIABLE_NAMES = new String[] {"Stop code", "Stop name", "Stop description", "Stop latitude", "Stop longitude", "Zone ID", "Stop URL", "Location type", "Parent station"};
	public static final int NUM_STOP_VARIABLES = 9;
	public static final int STOP_NAME_IDX = 1;
	
	HashMap<Integer, String[]> busStops;
	TernarySearchTree<Integer> TST;
	List<String> inputKeywords;
	
	public BusStopSearch(String fname) {
		
		busStops = new HashMap<Integer, String[]>(); 
		TST = new TernarySearchTree<Integer>();
		
		Scanner scanner;
		try {
			scanner = new Scanner(new File(fname));
			scanner.nextLine(); // Ignore first line with headings
			
			while (scanner.hasNextLine()) {
				String stop = scanner.nextLine();
				String[] stopEntries = stop.split(",");
				
				Integer stopId = Integer.valueOf(stopEntries[0]);
				String[] stopDetails = Arrays.copyOfRange(stopEntries, 1, stopEntries.length);
				
				busStops.put(stopId, stopDetails);
				
				String adjustedStopName = getAdjustedStopName(stopDetails[STOP_NAME_IDX]);
				TST.put(adjustedStopName, stopId);
				
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getAdjustedStopName (String stopName) {
		String[] stopWords = stopName.split(" ");
		while (Arrays.asList(STOP_KEYWORDS).contains(stopWords[0])) {
			String firstWord = stopWords[0];
			stopName = stopName.replace(firstWord, "").trim();
			stopName = stopName + " " + firstWord;
			stopWords = stopName.split(" ");
		}
		
		return stopName;
	}
	
	public String addEndingsToList (String stopName) {
		String[] stopWords = stopName.split(" ");
		inputKeywords = new ArrayList<String>();
		while (Arrays.asList(STOP_KEYWORDS).contains(stopWords[0])) {
			String firstWord = stopWords[0];
			stopName = stopName.replace(firstWord, "").trim();
			inputKeywords.add(firstWord);
			stopWords = stopName.split(" ");
		}
		
		return stopName;
	}
	
	public List<String[]> getStopDetails (String stopName) {
		String adjustedStopName = addEndingsToList(stopName);
		List<Integer> stopIds = TST.get(adjustedStopName);
		
		if (stopIds == null || stopIds.size() == 0) return null;
		
		List<String[]> stopDetails = new ArrayList<String[]>();

		int stopId;
		
		for (int i = 0; i < stopIds.size(); i++) {
			
			stopId = stopIds.get(i);
			if (endingsMatch(busStops.get(stopId)[STOP_NAME_IDX])) {
				stopDetails.add(busStops.get(stopId));
			}
		}
		
		return stopDetails;
	}

	public void displayStopDetails (String stopName) {
		List<String[]> stopDetails = getStopDetails(stopName);
		
		if (stopDetails == null || stopDetails.size() == 0) {
			System.out.println("No stops match this search criteria");
		}
		
		else {
		
			for (int i = 0; i < stopDetails.size(); i++) {
				String[] busStop = stopDetails.get(i);
				String stopOutput = "";

				System.out.println("Stop details for stop : " + busStop[STOP_NAME_IDX]);

				for (int j = 0; j < busStop.length; j++) {
					stopOutput += (BUS_STOP_VARIABLE_NAMES[j] + ": " + (!busStop[j].replaceAll(" ", "").equals("") ? busStop[j] : "unknown") + ", ");
				}
				System.out.println(stopOutput.substring(0, stopOutput.length()-2));
				System.out.println();
			}
		}
	}
	
<<<<<<< HEAD
	public boolean endingsMatch (String stopName) {
		String[] stopWords = stopName.split(" ");
		for (int i = 0; i < inputKeywords.size(); i++) {
			if (!stopWords[i].equals(inputKeywords.get(i))) return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		BusStopSearch bss = new BusStopSearch("stops.txt");
		bss.displayStopDetails ("NB TAMARACK LANE FS 1");
	}
=======
//	public static void main(String[] args) {
//		BusStopSearch bss = new BusStopSearch("stops.txt");
//		bss.displayStopDetails ("NB SHAUGHNESSY ST FS MCALLISTER AVE");
//	}
>>>>>>> 8f776be2cf24fe00b557a4a0b36cea023ae92176

}
