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
	private JPanel howToPlayInfoPanel;
	private JPanel containerJPanel;
	
	public HelpPage() {
		/* Test Code that will be removed when functionality is done */
		displayTemp.setSize(500,500);
		displayTemp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    displayTemp.setResizable(true);
	    
	    /* Going to handle the overall 'list of commands' section of the help page */
	    userInputtedCommandsPanel = new JPanel();
	    userInputtedCommandsPanel.setLayout(new GridLayout(6,1));
	    
	    /* Creating Individual Objects -- which will be inserted into the help page */
	    ListOfCommandsTitle commandTitle = new ListOfCommandsTitle();
	    ListOfMovementCommands movementCommands = new ListOfMovementCommands();
	    AfterEnteringRoomCommands roomCommands = new AfterEnteringRoomCommands();
	    HowToGuessCommands guessCommands = new HowToGuessCommands();
	    WhenTryingToSolveCommands solveCommands = new WhenTryingToSolveCommands();
	    MiscCommands miscCommands = new MiscCommands();
	    
	    /* Adding components to overall movement commands */
	    userInputtedCommandsPanel.add(commandTitle);
	    userInputtedCommandsPanel.add(movementCommands);
	    userInputtedCommandsPanel.add(roomCommands);
	    userInputtedCommandsPanel.add(guessCommands);
	    userInputtedCommandsPanel.add(solveCommands);
	    userInputtedCommandsPanel.add(miscCommands);
	    
	    
	    /* Setting up the JPanel which will hold onto the second half of the help info */
	    howToPlayInfoPanel = new JPanel();
	    howToPlayInfoPanel.setLayout(new GridLayout(2,1));
	    
	    HowToPlayInfo playInfo = new HowToPlayInfo();
	    PlayerListInfo playerList = new PlayerListInfo();

	    /* Creating the different inputs that we can work */
	    howToPlayInfoPanel.add(playInfo);
	    //howToPlayInfoPanel.add(playerList);
	    
	    containerJPanel = new JPanel();
	    containerJPanel.setLayout(new GridLayout(1,1));
	    
	    containerJPanel.add(userInputtedCommandsPanel);
	    containerJPanel.add(howToPlayInfoPanel);
	    
	    displayTemp.add(containerJPanel);
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
		weaponOptions = new JLabel("pistol, dagger, candlestick, wrench, rope, pipe - possible murder weapons to guess");
		playerOptions = new JLabel("mustard, scarlet, peacock, plum, white, green - possible murderers to guess");
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
	private JLabel solvingTitle;
	private JLabel weaponOptions;
	private JLabel playerOptions;
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
		solvingTitle = new JLabel("Solve");
		weaponOptions = new JLabel("pistol, dagger, candlestick, wrench, rope, pipe - possible murder weapons to accuse");
		playerOptions = new JLabel("mustard, scarlet, peacock, plum, white, green - possible murderers to accuse");
	}
	
	public WhenTryingToSolveCommands() {
		this.setLayout(new GridLayout(3,1));
		
		this.setTitles();
		
		solvingTitle.setHorizontalAlignment(JLabel.CENTER);
		this.add(solvingTitle);
		this.add(weaponOptions);
		this.add(playerOptions);
	}
}
/* Class that is going to deal with displaying the Misc Commands help */
class MiscCommands extends JPanel{
	private JLabel miscTitle;
	private JLabel helpTitle;
	private JLabel notesTitle;
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
		miscTitle = new JLabel("Miscellaneous");
		helpTitle = new JLabel("help - opens the current page, this does not count as your action for the turn");
		notesTitle = new JLabel("notes - shows all the infomation that you have about the murder");
	}
	
	public MiscCommands() {
		this.setLayout(new GridLayout(3,1));
		
		this.setTitles();
		miscTitle.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(miscTitle);
		this.add(helpTitle);
		this.add(notesTitle);
	}
}
/* Contains info on how the player can actually play the game */
class HowToPlayInfo extends JPanel{
	private JLabel howToPlayTitle;
	private JLabel movementInstructions;
	private JLabel guessingInstructions;
	private JLabel solveInstructions;
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
		howToPlayTitle = new JLabel("How to Play Clue");
		movementInstructions = new JLabel("You can move only horizontally or vertically, never diagonally, and can't enter a space or doorway you have already entered this turn.\n" + 
				"\n" + 
				"You can move through a doorway to enter a room, but this ends your movement.\n" + 
				"\n" + 
				"You can't move through a yellow space occupied by another player, but multiple players can be in the same room.\n" + 
				"\n" + 
				"If you start your turn in a room with a secret passage, you can use the secret passage instead of rolling the die.\n" + 
				"\n" + 
				"This will put your character in another room across the board, ending your movement.");
		guessingInstructions = new JLabel("If you end your movement in a room, you get to make a guess. To do this, reference the guess commands to the left. For example, if you just entered the lounge, you might say, \"XX \" The named suspect and murder weapon are both moved into your current room.\n" + 
				"\n" + 
				"The player to your left must disprove your suggestion by showing you one card from her hand that matches your suggestion. If that player can't do so, the player to her left must disprove your suggestion by showing you one card from his hand. This responsibility passes clockwise until someone shows you a card, or until all players have passed.\n" + 
				"\n" + 
				"If someone shows you a card, you should cross it off on your detective notebook as a possibility. Any cards you hold should also be crossed off as possibilities. Don't let other players see your notebook.");
		solveInstructions = new JLabel("If you think you have solved the case by eliminating all the false possibilities and have not just had your suggestion disproved this turn, you can end your turn by making an accusation, provided that you are in the celler. Announce that you are making an accusation, and state your final guess of the murderer, the murder weapon, and the murder location.\n" + 
				"\n" + 
				"Once this is done, secretly look at the three cards in the murder envelope. If you are correct, lay the cards face-up on the table, proving to all players that you have won the game.\n" + 
				"\n" + 
				"If you are wrong, you lose the game! Secretly replace the three cards back in the murder envelope without revealing them. Your turn is over, and you are now eliminated from the game.\n" + 
				"\n" + 
				"You no longer take any turns, but must stay at the table to disprove the suggestions of others. If your piece is blocking a doorway, it is moved into the room.");
	}
	
	public HowToPlayInfo() {
		this.setLayout(new GridLayout(4,1));
		
		this.setTitles();
		howToPlayTitle.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(howToPlayTitle);
		this.add(movementInstructions);
		this.add(guessingInstructions);
		this.add(solveInstructions);
	}
}
/* Will return the list of players currently playing */
class PlayerListInfo extends JPanel{
	private JLabel playerListInfoTitle;
	private JLabel playerList;
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
		playerListInfoTitle = new JLabel("List of Players");
		playerList = new JLabel("This is going to be a placeHolder");
	}
	
	public PlayerListInfo() {
		this.setLayout(new GridLayout(2,1));
		
		this.setTitles();
		playerListInfoTitle.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(playerListInfoTitle);
		this.add(playerList);
	}
}
