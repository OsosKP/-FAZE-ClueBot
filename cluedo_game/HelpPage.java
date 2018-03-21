package cluedo_game;

import java.awt.BorderLayout;

/**
 * Basic set up is we are going to have one MEGA JPanel which is going to hold all the other JPanels in the correct position
 */


import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpPage {
	
	private JFrame displayTemp = new JFrame(); //currently just here for testing, will be removed later
	private JPanel listOfMovementCommands;
	
	public HelpPage() {
		/* Test Code that will be removed when functionality is done */
		displayTemp.setSize(500,500);
		displayTemp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    displayTemp.setResizable(false);
	    
	    /* Going to handle the overall 'list of commands' section of the help page */
	    listOfMovementCommands = new JPanel();
	    listOfMovementCommands.setLayout(new GridLayout(5,1));
	    
	    /* Creating Individual Objects -- which will be inserted into the help page */
	    ListOfCommandsTitle title = new ListOfCommandsTitle();
	    
	    /* Adding components to overall movement commands */
	    listOfMovementCommands.add(title);
	    
	    
	    displayTemp.setVisible(true);
	
	}
}

class ListOfCommandsTitle extends JPanel{
	JLabel myLabel = new JLabel("List of Commands");
	
	public void setLayout(LayoutManager mgr) {
		super.setLayout(mgr);
	}
	
	public ListOfCommandsTitle() {
		this.setLayout(new BorderLayout());
		myLabel.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(myLabel);
	}
	
}
/* Class that is going to deal with displaying the movement commands help */
class ListOfMovementCommands extends JPanel{
	
}
/* Class that is going to deal with displaying the enter commands help */
class AfterEnteringRoomCommands extends JPanel{
	
}
/* Class that is going to deal with displaying the guessing commands help */
class HowToGuessCommands extends JPanel{
	
	
}
/* Class that is going to deal with displaying the solving commands help */
class WhenTryingToSolveCommands extends JPanel{
	
}
/* Class that is going to deal with displaying the Misc Commands help */
class MiscCommands extends JPanel{
	
	
}
