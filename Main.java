// Import necessary classes
import javax.swing.JFrame;
import javax.swing.JPanel; 

public class Main {

	public static void main(String[] args) {
		
		// Create a JFrame object
		JFrame obj = new JFrame();
		
		// Set the bounds of the JFrame
		// x-coordinate, y-coordinate, width, height
		obj.setBounds(10, 10, 700, 600);
		
		// Set the title of the JFrame
		obj.setTitle("Breakout Ball");
		
		// Disable resizing of the JFrame
		obj.setResizable(false);
		
		// Set the default close operation
		// Closes the application on exit
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Display the JFrame at the center of the screen
		obj.setLocationRelativeTo(null);
		
		// Make the JFrame visible
		obj.setVisible(true);
		
		// Create an object of the GamePlay class
		GamePlay gamePlay = new GamePlay();
		
		// Add the GamePlay object to the JFrame
		// Since GamePlay extends JPanel, it can be added to a JFrame
		obj.add(gamePlay);
	}

}