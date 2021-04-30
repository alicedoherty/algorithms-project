import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

public class BusStopSearch {

	public static final String[] STOP_KEYWORDS = new String[] {"FLAGSTOP", "WB", "NB", "SB", "EB"};
	public static final String[] BUS_STOP_VARIABLE_NAMES = new String[] {"Stop ID", "Stop code", "Stop name", "Stop description", "Stop latitude", "Stop longitude", "Zone ID", "Stop URL", "Location type", "Parent station"};
	public static final int STOP_NAME_IDX = 2;
	
	TernarySearchTree<String[]> TST;
	List<String> inputKeywords;
	
	public BusStopSearch(String fname) {
		
		TST = new TernarySearchTree<String[]>();
		
		try {
			Scanner scanner = new Scanner(new File(fname));
			scanner.nextLine(); // Ignore first line with headings
			
			while (scanner.hasNextLine()) {
				String stop = scanner.nextLine();
				String[] stopDetails = stop.split(",");
				
				String stopName = stopDetails[STOP_NAME_IDX];
				
				String adjustedStopName = getAdjustedStopName(stopName, false);
				TST.put(adjustedStopName, stopDetails);
				
			}
		}
		
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Inputted filename doesn't exist.");
		}
	}

	public String getAdjustedStopName (String stopName, boolean isSearch) {
		String[] stopWords = stopName.split(" ");
		inputKeywords = new ArrayList<String>();

		while (Arrays.asList(STOP_KEYWORDS).contains(stopWords[0])) {
			String firstWord = stopWords[0];
			stopName = stopName.replace(firstWord, "").trim();
			
			if (isSearch) inputKeywords.add(firstWord);
			else stopName = stopName + " " + firstWord;
			
			stopWords = stopName.split(" ");
		}
		
		return stopName;
	}
	
	public List<String[]> getStopDetails (String stopName) {
		String adjustedStopName = getAdjustedStopName(stopName, true);
		List<String[]> stopDetails = TST.get(adjustedStopName);
		
		if (stopDetails == null || stopDetails.size() == 0) return null;
		
		String curStopName;
		int i = 0;
		while (i < stopDetails.size()) {
			curStopName = stopDetails.get(i)[STOP_NAME_IDX];
			if (!endingsMatch(curStopName)) stopDetails.remove(i);
			else i++;
		}
		
		return stopDetails;
	}

	public boolean displayStopDetails (String stopName) {
		List<String[]> stopDetails = getStopDetails(stopName);
		
		if (stopDetails == null || stopDetails.size() == 0) {
			System.out.println("No stops match this search criteria");
			return false;
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
		return true;
	}
	
	public boolean endingsMatch (String stopName) {
		String[] stopWords = stopName.split(" ");
		for (int i = 0; i < inputKeywords.size(); i++) {
			if (!stopWords[i].equals(inputKeywords.get(i))) return false;
		}
		
		return true;
	}

}
