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
	private JPanel userInputtedCommandsPanel;
	
	public HelpPage() {
		/* Test Code that will be removed when functionality is done */
		displayTemp.setSize(500,500);
		displayTemp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    displayTemp.setResizable(false);
	    
	    /* Going to handle the overall 'list of commands' section of the help page */
	    userInputtedCommandsPanel = new JPanel();
	    userInputtedCommandsPanel.setLayout(new GridLayout(5,1));
	    
	    /* Creating Individual Objects -- which will be inserted into the help page */
	    ListOfCommandsTitle commandTitle = new ListOfCommandsTitle();
	    ListOfMovementCommands movementCommands = new ListOfMovementCommands();
	    
	    /* Adding components to overall movement commands */
	    userInputtedCommandsPanel.add(commandTitle);
	    userInputtedCommandsPanel.add(movementCommands);
	   
	    displayTemp.add(userInputtedCommandsPanel);
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
	JLabel movementTitle;
	JLabel upTitle;
	JLabel downTitle;
	JLabel leftTitle;
	JLabel rightTitle;
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
		movementTitle = new JLabel("Movement");
		upTitle = new JLabel("Up - Moves the current player up one space");
		downTitle = new JLabel("Down - Moves current player down one space");
		leftTitle = new JLabel("Left - Moves the current player left one space");
		rightTitle = new JLabel("Right - Moves the Current Player right one space");
	}
	
	public ListOfMovementCommands() {
		this.setLayout(new GridLayout(5,1));
		/* sets the titles for the labels */
		setTitles();
		
		/* Adding the titles to the JPanel */
		movementTitle.setHorizontalAlignment(JLabel.CENTER);
		this.add(movementTitle);
		this.add(upTitle);
		this.add(downTitle);
		this.add(leftTitle);
		this.add(rightTitle);
	}
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
