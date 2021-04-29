import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

public class BusStopSearch {

	public static final String[] STOP_KEYWORDS = new String[] {"FLAGSTOP", "WB", "NB", "SB", "EB"};
	public static final String[] BUS_STOP_VARIABLE_NAMES = new String[] {"Stop code: ", "Stop name: ", "Stop description: ", "Stop latitude: ", "Stop longitude: ", "Zone ID: ", "Stop URL: ", "Location type: ", "Parent Station: "};
	public static final int NUM_STOP_VARIABLES = 9;
	public static final int STOP_NAME_IDX = 1;
	
	HashMap<Integer, String[]> busStops;
	TernarySearchTree<Integer> TST;
	
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
				String[] stopDetails = Arrays.copyOfRange(stopEntries, 1, stopEntries.length-1);
				
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
	
	public String[][] getStopDetails (String stopName) {
		String adjustedStopName = getAdjustedStopName(stopName);
		List<Integer> stopIds = TST.get(adjustedStopName);
		String[][] stopDetails = new String[stopIds.size()][NUM_STOP_VARIABLES];
		int stopId;
		
		for (int i = 0; i < stopIds.size(); i++) {
			stopId = stopIds.get(i);
			busStops.get(stopId);
			stopDetails[i] = busStops.get(stopId);
		}
		
		return stopDetails;
	}

	
	public void displayStopDetails (String stopName) {
		String[][] stopDetails = getStopDetails(stopName);
		
		for (int i = 0; i < stopDetails.length; i++) {
			System.out.println("Stop details for stop : " + stopName);
			for (int j = 0; j < NUM_STOP_VARIABLES; j++) {
				System.out.print(BUS_STOP_VARIABLE_NAMES[j] + ": " + stopDetails[0][j] + ", ");	
			}
		}
	}
	
//	public static void main(String[] args) {
//		BusStopSearch bss = new BusStopSearch("stops.txt");
//		bss.displayStopDetails ("NB SHAUGHNESSY ST FS MCALLISTER AVE");
//	}

}
