package cluedo_game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * Basic set up is we are going to have one MEGA JPanel which is going to hold all the other JPanels in the correct position
 */


import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class HelpPage {
	private ListOfCommandsTitle buttons;
	
	private JFrame displayTemp = new JFrame(); //currently just here for testing, will be removed later
	private JPanel userInputtedCommandsPanel = null;
	private JPanel howToPlayInfoPanel = null;
	private JPanel currentPlayerInfoPanel = null;
	private JPanel containerJPanel = null;
	
	//TODO when I am actually implementing this with the main JFrame, I can pass that object into this constructor, then I can mess with it all I want
	public HelpPage() {
	
		/* Test Code that will be removed when functionality is done */
		displayTemp.setSize(800,700);
		displayTemp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    displayTemp.setResizable(true);
	    
	    /* Going to handle the overall 'list of commands' section of the help page */
	    userInputtedCommandsPanel = new JPanel();
	    userInputtedCommandsPanel.setLayout(new GridLayout(5,1));
	    
	    /* Creating Individual Objects -- which will be inserted into the help page */
	    ListOfMovementCommands movementCommands = new ListOfMovementCommands();
	    AfterEnteringRoomCommands roomCommands = new AfterEnteringRoomCommands();
	    HowToGuessCommands guessCommands = new HowToGuessCommands();
	    WhenTryingToSolveCommands solveCommands = new WhenTryingToSolveCommands();
	    MiscCommands miscCommands = new MiscCommands();
	    
	    /* Adding components to overall movement commands */
	    userInputtedCommandsPanel.add(movementCommands);
	    userInputtedCommandsPanel.add(roomCommands);
	    userInputtedCommandsPanel.add(guessCommands);
	    userInputtedCommandsPanel.add(solveCommands);
	    userInputtedCommandsPanel.add(miscCommands);
	   
	    
	    buttons = new ListOfCommandsTitle();
	    buttons.toggleUser();
	    
	    containerJPanel = new JPanel();
	    containerJPanel.setLayout(new BorderLayout());
	    
	    containerJPanel.add(buttons, BorderLayout.NORTH);
	    containerJPanel.add(userInputtedCommandsPanel, BorderLayout.CENTER);
	    	    
	    displayTemp.add(containerJPanel);
	    displayTemp.setVisible(true);
	}
	
	/* Using the title as an inner class because we need to change the created objects */
	class ListOfCommandsTitle extends JPanel{
		private JButton userCommands;
		private JButton howToPlay;
		private JButton playerList;
		
		@Override
		public void setLayout(LayoutManager mgr) {
			// TODO Auto-generated method stub
			super.setLayout(mgr);
		}
	
		public void setTitle() {
			userCommands = new JButton("Accepted Commands");
			howToPlay = new JButton("How to Play Cleudo");
			playerList = new JButton("Player List");
		}
	
		public ListOfCommandsTitle() {
			this.setLayout(new GridLayout(1,3));
			this.setTitle();
	
			/* Action Listeners used to change the info displayed on the help page */
			AcceptedListener acceptedAction = new AcceptedListener();
			HowToPlayListener howToAction = new HowToPlayListener();
			PlayerListListener playAction = new PlayerListListener();
			
			/* Adding actionListeners to the buttons */
			userCommands.addActionListener(acceptedAction);
			howToPlay.addActionListener(howToAction);
			playerList.addActionListener(playAction);
			
			this.add(userCommands);
			this.add(howToPlay);
			this.add(playerList);
		}
		
		public void toggleList() {
			playerList.setEnabled(false);
			howToPlay.setEnabled(true);
			userCommands.setEnabled(true);
		}
	
		public void toggleUser() {
			userCommands.setEnabled(false);
			howToPlay.setEnabled(true);
			playerList.setEnabled(true);
		}
	
		public void togglePlay() {
			howToPlay.setEnabled(false);
			userCommands.setEnabled(true);
			playerList.setEnabled(true);
		}
	}
	/* Going to be toggled when the user wants to learn more about the valid commands for the game */
	class AcceptedListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			/* Disabling the option for the user to toggle the 'accepted commands' button -- since that is what they are supposed to be looking at anyway */
			buttons.toggleUser();
			
			/* Checking which component is actually attached to the panel, and removing it */
			if (howToPlayInfoPanel != null) {
				containerJPanel.remove(howToPlayInfoPanel);
				howToPlayInfoPanel = null;
			}
			else if (currentPlayerInfoPanel != null) {
				containerJPanel.remove(currentPlayerInfoPanel);
				currentPlayerInfoPanel = null;
			}
			else {
				System.err.println("Someathing broke HelpPage.java");
				System.exit(0);			
			}
			
			/* Going to handle the overall 'list of commands' section of the help page */
			userInputtedCommandsPanel = new JPanel();
			userInputtedCommandsPanel.setLayout(new GridLayout(5,1));
	    
			/* Creating Individual Objects -- which will be inserted into the help page */
			ListOfMovementCommands movementCommands = new ListOfMovementCommands();
			AfterEnteringRoomCommands roomCommands = new AfterEnteringRoomCommands();
			HowToGuessCommands guessCommands = new HowToGuessCommands();
			WhenTryingToSolveCommands solveCommands = new WhenTryingToSolveCommands();
			MiscCommands miscCommands = new MiscCommands();
	    
			/* Adding components to overall movement commands */
			userInputtedCommandsPanel.add(movementCommands);
			userInputtedCommandsPanel.add(roomCommands);
			userInputtedCommandsPanel.add(guessCommands);
			userInputtedCommandsPanel.add(solveCommands);
			userInputtedCommandsPanel.add(miscCommands);
			
			containerJPanel.add(userInputtedCommandsPanel, BorderLayout.CENTER);
			displayTemp.repaint();
			displayTemp.revalidate();
		}
		
	}
	/* Going to be toggled when the user wants to learn more about how to play cluedo */
	
	class HowToPlayListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			/* Disabling the option for the user to toggle the 'how to play' button -- since that is the page they will be looking at already */
			buttons.togglePlay();
			
			if (userInputtedCommandsPanel != null) {
				containerJPanel.remove(userInputtedCommandsPanel);
				userInputtedCommandsPanel = null;
			}
			else if (currentPlayerInfoPanel != null){
				containerJPanel.remove(currentPlayerInfoPanel);
				currentPlayerInfoPanel = null;
			}
			else {
				System.err.println("Someathing broke HelpPage.java");
				System.exit(0);
			}
			
			/* Setting up the JPanel which will hold onto the second half of the help info */
			howToPlayInfoPanel = new JPanel();
			howToPlayInfoPanel.setLayout(new GridLayout(1,1));
	    
			HowToPlayInfo playInfo = new HowToPlayInfo();

			/* Creating the different inputs that we can work */
			howToPlayInfoPanel.add(playInfo);
			
			containerJPanel.add(howToPlayInfoPanel, BorderLayout.CENTER);
			displayTemp.repaint();
			displayTemp.revalidate();
		}
		
	}
	/* Going to be toggled when the user wants to learn more about the current players playing the game */
	class PlayerListListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			buttons.toggleList();
			
			if (userInputtedCommandsPanel != null) {
				containerJPanel.remove(userInputtedCommandsPanel);
				userInputtedCommandsPanel = null;
			}
			else if (howToPlayInfoPanel != null) {
				containerJPanel.remove(howToPlayInfoPanel);
				howToPlayInfoPanel = null;
			}
			else {
				System.err.println("Someathing broke HelpPage.java");
				System.exit(0);			
			}
			currentPlayerInfoPanel = new JPanel();
			currentPlayerInfoPanel.setLayout(new GridLayout(2,1));
			
			PlayerListCharacterNameInfo characters = new PlayerListCharacterNameInfo();
			PlayerListUsernameInfo users = new PlayerListUsernameInfo();
			
			currentPlayerInfoPanel.add(characters);
			currentPlayerInfoPanel.add(users);
			
			containerJPanel.add(currentPlayerInfoPanel, BorderLayout.CENTER);
			displayTemp.repaint();
			displayTemp.revalidate();
		}
		
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
	private JLabel movementInstructions;
	private JLabel guessingInstructions;
	private JLabel solveInstructions;
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
		movementInstructions = new JLabel("<html><div style= 'text-align: center;'>How To Move</div><br>You can move only horizontally or vertically, never diagonally, and can't enter a space or doorway you have already entered this turn.<br><br>You can move through a doorway to enter a room, but this ends your movement.<br><br>You can't move through a yellow space occupied by another player, but multiple players can be in the same room.<br><br>If you start your turn in a room with a secret passage, you can use the secret passage instead of rolling the die.<br><br>This will put your character in another room across the board, ending your movement.<br></html>");
		guessingInstructions = new JLabel("<html><div style= 'text-align: center;'>How To Guess</div><br>If you end your movement in a room, you get to make a guess. To do this, reference the guess commands to the left. For example, if you just entered the lounge, you might say, \"XX \" The named suspect and murder weapon are both moved into your current room.<br><br>The player to your left must disprove your suggestion by showing you one card from her hand that matches your suggestion. If that player can't do so, the player to her left must disprove your suggestion by showing you one card from his hand. This responsibility passes clockwise until someone shows you a card, or until all players have passed.<br><br>If someone shows you a card, you should cross it off on your detective notebook as a possibility. Any cards you hold should also be crossed off as possibilities. Don't let other players see your notebook.<br></html>");
		solveInstructions = new JLabel("<html><div style= 'text-align: center;'>How To Accuse</div><br>If you think you have solved the case by eliminating all the false possibilities and have not just had your suggestion disproved this turn, you can end your turn by making an accusation, provided that you are in the celler. Announce that you are making an accusation, and state your final guess of the murderer, the murder weapon, and the murder location.<br><br>Once this is done, secretly look at the three cards in the murder envelope. If you are correct, lay the cards face-up on the table, proving to all players that you have won the game.<br><br>If you are wrong, you lose the game! Secretly replace the three cards back in the murder envelope without revealing them. Your turn is over, and you are now eliminated from the game.<br></html>");
	}
	
	public HowToPlayInfo() {
		this.setLayout(new GridLayout(3,1));
		
		this.setTitles();
		this.add(movementInstructions);
		this.add(guessingInstructions);
		this.add(solveInstructions);
	}
}
/* Will return the list of characters who are currently playing */
class PlayerListCharacterNameInfo extends JPanel{
	private JLabel playerTitle;
	private ArrayList<JLabel> playerListArray = new ArrayList<JLabel>();
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
			ArrayList<String> characterNames = new ArrayList<String>();
			ArrayList<String> usernames = new ArrayList<String>();
			
			characterNames = GameLogic.playerList.returnCharacterNames();
			usernames = GameLogic.playerList.returnUsernames();
			
			String characterNameString;
			String userNameString = "<html>";
			String titleTest = "This is a test";
			
			for (int i = 0; i < characterNames.size(); i++) {
				characterNameString = characterNames.get(i);
				userNameString = userNameString + usernames.get(i) + " is playing: " + characterNames.get(i) +"<br>";
				playerListArray.add(new JLabel(characterNameString));
			}
			userNameString = userNameString + "</html>";
			playerTitle = new JLabel("All the Characters who were Created", SwingConstants.CENTER);
	}
	
	public PlayerListCharacterNameInfo() {
		this.setLayout(new GridLayout(playerListArray.size(),1));
		this.setTitles(); 
		this.add(playerTitle);
		for (int i = 0; i < playerListArray.size(); i++) {
			this.add(playerListArray.get(i));
		}
	}
}

/* Will return the list of players currently playing their character */
//TODO add alive/dead stats here at some point
class PlayerListUsernameInfo extends JPanel{
	private JLabel playerTitle;
	private ArrayList<JLabel> playerListArray = new ArrayList<JLabel>();
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public void setTitles() {
			ArrayList<String> usernames = new ArrayList<String>();
			ArrayList<String> characterNames = new ArrayList<String>();
			
			usernames = GameLogic.playerList.returnUsernames();
			characterNames = GameLogic.playerList.returnCharacterNames();		
			
			String userNameString;
			
			for (int i = 0; i < usernames.size(); i++) {
				userNameString = usernames.get(i) + " is playing: " + characterNames.get(i);
				playerListArray.add(new JLabel(userNameString));
			}
			playerTitle = new JLabel("Players' Character Selection", SwingConstants.CENTER);
	}
	
	public PlayerListUsernameInfo() {
		this.setLayout(new GridLayout(playerListArray.size(),1));
		this.setTitles(); 
		this.add(playerTitle);
		for (int i = 0; i < playerListArray.size(); i++) {
			this.add(playerListArray.get(i));
		}
	}
}

