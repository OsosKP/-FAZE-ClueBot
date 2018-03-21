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
	    AfterEnteringRoomCommands roomCommands = new AfterEnteringRoomCommands();
	    HowToGuessCommands guessCommands = new HowToGuessCommands();
	    
	    /* Adding components to overall movement commands */
	    userInputtedCommandsPanel.add(commandTitle);
	    userInputtedCommandsPanel.add(movementCommands);
	    userInputtedCommandsPanel.add(roomCommands);
	    userInputtedCommandsPanel.add(guessCommands);
	    
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
	private JLabel movementTitle;
	private JLabel upTitle;
	private JLabel downTitle;
	private JLabel leftTitle;
	private JLabel rightTitle;
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
		movementTitle = new JLabel("Movement");
		upTitle = new JLabel("up - Moves the current player up one space");
		downTitle = new JLabel("down - Moves current player down one space");
		leftTitle = new JLabel("left - Moves the current player left one space");
		rightTitle = new JLabel("right - Moves the Current Player right one space");
	}
	
	public ListOfMovementCommands() {
		this.setLayout(new GridLayout(5,1));
		/* sets the titles for the labels */
		this.setTitles();
		
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
	private JLabel enteringTitle;
	private JLabel passageTitle;
	private JLabel exitTitle;
	private JLabel guessingTitle;
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
		enteringTitle = new JLabel("After Entering a Room");
		passageTitle = new JLabel("passage - Moves the current player through the room's seacret passage");
		exitTitle = new JLabel("exit - Moves the current player out of the current room");
		guessingTitle = new JLabel("guess - The Current player makes a guess about the murder");
	}
	
	public AfterEnteringRoomCommands() {
		this.setLayout(new GridLayout(4,1));
		
		this.setTitles();
		
		enteringTitle.setHorizontalAlignment(JLabel.CENTER);
		this.add(enteringTitle);
		this.add(passageTitle);
		this.add(exitTitle);
		this.add(guessingTitle);
	}
}
/* Class that is going to deal with displaying the guessing commands help */
class HowToGuessCommands extends JPanel{
	private JLabel guessingTitle;
	private JLabel weaponOptions;
	private JLabel playerOptions;
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
		guessingTitle = new JLabel("Guess");
		weaponOptions = new JLabel("pistol, dagger, candlestick, wrench, rope, pipe");
		playerOptions = new JLabel("mustard, scarlet, peacock, plum, white, green");
	}
	
	public HowToGuessCommands() {
		this.setLayout(new GridLayout(3,1));
		
		this.setTitles();
		
		guessingTitle.setHorizontalAlignment(JLabel.CENTER);
		this.add(guessingTitle);
		this.add(weaponOptions);
		this.add(playerOptions);
	}
}
/* Class that is going to deal with displaying the solving commands help */
class WhenTryingToSolveCommands extends JPanel{
	
}
/* Class that is going to deal with displaying the Misc Commands help */
class MiscCommands extends JPanel{
	
	
}
