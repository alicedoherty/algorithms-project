//4. Provide front interface enabling selection between the above features or an option to exit
//the programme, and enabling required user input. It does not matter if this is command-line
//or graphical, as long as functionality/error checking is provided.
//You are required to provide error checking and show appropriate messages in the case of erroneous
//inputs – eg bus stop doesn’t exist, wrong format for time for bus stop (eg letters instead of
//numbers), no route possible etc. 


/* Selection between:
 	1. Finding shortest path
 		- Input
 	2. Searching bus stop
 		- Input
 	3. Searching for trips in given time
 		- Input
 	4. Exit
 	
 	Error messages
*/  


import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class BusManagementSystem {

	   public static void main(String[] args){
		   Scanner input = new Scanner(System.in);
		   boolean validInput = false;
		   
		   while(!validInput) {
			   System.out.println("Choose which feature you want (0, 1, 2 or 3):\n"
			   		+ "0: Quit\n"
			   		+ "1: Shortest path between two bus stops\n"
			   		+ "2: Search for a bus stop\n"
			   		+ "3: Search for trips within a given time");
			   
			   if(input.hasNextInt()) {
				   int userChoice = input.nextInt();
				   switch(userChoice) {
				   		case 0:
				   			validInput = true;
				   			System.out.println("case 0");
				   			break;
				   		case 1:
				   			validInput = true;
				   			System.out.println("case 1");
				   			break;
				   		case 2:
				   			validInput = true;
				   			System.out.println("case 2");
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
				   input.next();
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

}
