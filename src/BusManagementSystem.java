import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class BusManagementSystem {
		
		public static void main(String[] args){
		   Scanner input = new Scanner(System.in);
		   boolean validInput = false;
		   
		   while(!validInput) {
			   System.out.println("\nChoose which feature you want (0, 1, 2 or 3):\n"
			   		+ "0: Quit\n"
			   		+ "1: Shortest path between two bus stops\n"
			   		+ "2: Search for a bus stop\n"
			   		+ "3: Search for trips within a given time\n");

			   if(input.hasNextInt()) {
				   int userChoice = input.nextInt();
	
				   switch(userChoice) {
				   		case 0:
				   			validInput = true;
				   			System.out.println("Goodbye :)");
				   			break;
				   		case 1:
				   			validInput = true;
				   			callGetShortestPath();
				   			break;
				   		case 2:
				   			validInput = true;
				   			callBusStopSearch();
				   			break;
				   		case 3:
				   			validInput = true;
				   			callSearchArrivalTime();
				   			break;
				   		default:
				   			System.out.println("Choice not valid.");	
				   
				   }
			   }
			   
			   else {
				   System.out.println("Please only enter numbers.");
				   input.nextLine();
			  }
			   
		   }
		  input.close();
		   		        
	   }
	   
	   public static void callSearchArrivalTime() {
		   Scanner userInput = new Scanner(System.in);
		   boolean validInput = false;
		   DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		   
		   timeFormat.setLenient(false);
		   
		   while(!validInput) {
			   System.out.println("Enter the arrival time (hh:mm:ss):\n");
			   String inputTime = userInput.next();
			   
			   try {
				   timeFormat.parse(inputTime);
				   validInput = true;
				   SearchArrivalTime.getResults(inputTime);
			   } catch(ParseException e) {
				   System.out.println("\"" + inputTime + "\" is not in a valid time format. \n");
			   }
			   
		   }
		   userInput.close();
		   
	   }
	   
	   public static void callBusStopSearch() {
		   Scanner userInput = new Scanner(System.in);
		   BusStopSearch bss = new BusStopSearch("stops.txt");
		   boolean validSearch = false;
		   
		   while(!validSearch) {
			   System.out.println("Enter the stop you are looking for:\n");
			   String inputStop = userInput.nextLine();
			   if(bss.displayStopDetails(inputStop.toUpperCase()))
				   validSearch = true;
			   
		   }
		   
		   userInput.close();
	   }
	   
	   public static void callGetShortestPath() {
		   Shortest_Path_Between_2_Stops.getShortestPath();
		   
	   }

}
