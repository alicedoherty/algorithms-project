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


import javax.swing.*;

public class BusManagementSystem {

	public static void main(String[] args) {
	    JFrame frame = new JFrame("My First GUI");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(300,300);
	    JButton button1 = new JButton("Button 1");
	    JButton button2 = new JButton("Button 2");
	    frame.getContentPane().add(button1);
	    frame.getContentPane().add(button2);
	    frame.setVisible(true);

	}

}
