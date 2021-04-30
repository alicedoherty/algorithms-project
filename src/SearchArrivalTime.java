import java.io.*;
import java.util.*;
import java.text.*;

public class SearchArrivalTime {
	
	
	public static ArrayList<String> getTrips(String item, ArrayList<String> list)    {
		ArrayList<String> trips = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		//linear search
		for (int i = 0; i<list.size(); i++) {
			String current = list.get(i);
			String[] t = current.split(",");
			try {
				Date currentTime = sdf.parse(t[1]);
				Date itemTime = sdf.parse(item);
				if (currentTime.getTime() == itemTime.getTime()) {
					trips.add(current);
				}
			} catch (Exception e){
	            System.out.print(e);
	        }
		}
		return trips;
	}
	
	public static ArrayList<String> allTrips(){
		ArrayList<String> allTrips = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String maxTime = "24:00:00";
		try{
			Date max = sdf.parse(maxTime);
			BufferedReader br = new BufferedReader(new FileReader("stop_times.txt"));
			br.readLine();
			String line;
	        while ((line = br.readLine()) != null) {
	        	if (!line.isEmpty()) {
	            	String[] temp = line.split(",");
	            	Date time = sdf.parse(temp[1]);
	            	if (time.getTime() < max.getTime()) {
	            		allTrips.add(line);
	            	}
	        	}
            }
            br.close();
        } catch (Exception e){
            System.out.print(e);
        }
	
		return allTrips;
	}
	
	public static ArrayList<String> quickSort(ArrayList<String> list)
	{
	    if (list.size() <= 1) 
	        return list;   

	    ArrayList<String> sorted = new ArrayList<String>();
	    ArrayList<String> lesser = new ArrayList<String>();
	    ArrayList<String> greater = new ArrayList<String>();
	    String pivot = list.get(list.size()-1); // Use last element as pivot
	    for (int i = 0; i < list.size()-1; i++)
	    {
	        if (list.get(i).compareTo(pivot) < 0)
	            lesser.add(list.get(i));    
	        else
	            greater.add(list.get(i));   
	    }

	    lesser = quickSort(lesser);
	    greater = quickSort(greater);

	    lesser.add(pivot);
	    lesser.addAll(greater);
	    sorted = lesser;

	    return sorted;
	}
	
	public static boolean getResults(String searchItem){
		ArrayList<String> list = allTrips();
		ArrayList<String> results = getTrips(searchItem, list);
		results = quickSort(results);
		if (!results.isEmpty()) {
			System.out.println(results.size() + " trips match your search:");
			for(int i=0; i < results.size(); i++){
	            System.out.println( results.get(i) );
	        }
		}
		else {
			System.out.println("There are no trips that match your search.");
			return false;
		}
		return true;
	}
}


