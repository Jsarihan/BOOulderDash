/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Boulder Dash");
        frame.setLocation(300, 100);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        final JLabel time = new JLabel("0");
        final JLabel score = new JLabel("0");
        status_panel.add(time);
        status_panel.add(status);
        status_panel.add(score);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double screenWidth = screenSize.getWidth();
        final double screenHeight = screenSize.getHeight();
        
        final boolean isHD = (screenWidth > 3000 && screenHeight > 3000);
        
        // Main playing area
        final GameCourt court = new GameCourt(status, time, score, isHD);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        
        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
	            String filename = userPrompt("Please enter a save name:");
	            try {
	                court.save(filename);
	            } catch (IOException i) {
	            	JFrame errorFrame = new JFrame("Error Saving File");
	              	// prompt the user to enter their name
	              	JOptionPane.showMessageDialog(errorFrame, "please try again", "there was an error", JOptionPane.ERROR_MESSAGE);
	            }
            }
        });
        
        final JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	File fileChosen = filePrompt();
        			try {
    	                court.load(fileChosen);
    	            } catch (IOException i) {
    	            	defaultError();
    	            } catch (NullPointerException i) {
    	            	defaultError();
    	            }
        		}
        });
        
        final JButton inButton = new JButton("Instructions");
        inButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              	// prompt the user to enter their name
            	ImageIcon img= new ImageIcon("instructions.PNG");
            	JOptionPane.showMessageDialog(
                        null, null, "How to Play", JOptionPane.INFORMATION_MESSAGE, img);
            }
        });
        
        
        
        
        
        
        control_panel.add(reset);
        control_panel.add(saveButton);
        control_panel.add(loadButton);
        control_panel.add(inButton);
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    
    
    private File filePrompt() {
    	JFileChooser fileChooser = new JFileChooser("C:\\Users\\John\\Documents\\Workspace\\bOOulderDash\\levels");
		int val = fileChooser.showOpenDialog(null);
		
		if (val == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
    }
    
    private String userPrompt(String question) {
   	 JFrame promptFrame = new JFrame("InputDialog");
   	 String input = "default";
   	 // prompt the user to enter their name
   	 input = JOptionPane.showInputDialog(promptFrame, question);
   	 return(input);
    }
    
    private void defaultError() {
    	JFrame errorFrame = new JFrame("Error Loading File");
      	// prompt the user to enter their name
      	JOptionPane.showMessageDialog(errorFrame, "Please try again", 
      			"There was an error loading your file", JOptionPane.ERROR_MESSAGE);
    }
   
    
    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}