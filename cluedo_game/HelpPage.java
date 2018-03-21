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
	private JPanel listOfCommandsOverallPanel;
	private JPanel listOfMovementCommands;
	
	public HelpPage() {
		/* Test Code that will be removed when functionality is done */
		displayTemp.setSize(500,500);
		displayTemp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    displayTemp.setResizable(false);
	    
	    ListOfCommandsTitle title = new ListOfCommandsTitle();
	    displayTemp.add(title);
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
