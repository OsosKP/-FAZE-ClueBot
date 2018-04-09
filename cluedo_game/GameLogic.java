// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * class that is going to handle all of the game logic
 * @author george
 *
 */
public class GameLogic {
	/* This is going to handle the board */
	static BoardBuilder currentBoard;
	static Tokens playerList;
	static Deck deck;
	static UserInterface ui;
	static LoopSound startMusic;
	static GameMusic gameMusic;
	static boolean music;
	
	static ArrayList<String> guesses = new ArrayList<>();

	public GameLogic() {
	    new StartMenu();
	}

	public static void startGame(boolean debugOption) {
		AcceptedUserInputs.setAcceptedUserInputs();
		if (debugOption) {
			playerList = new Tokens();
			playerList.setDebugPlayerList();
			createGame();
		}
		else {
			PlayerListCreator playersCreator = new PlayerListCreator();
			playerList = playersCreator.getPlayerList();
		}
        if(music) {
		    // Sending false to tell it we aren't in the start menu
		    playMusic(false);
        }
	}
	
	public static void addToGuessArray(String addMe) {
		guesses.add(addMe);
	}
	
	public static ArrayList<String> returnGuessArray(){
		return guesses;
	}

	public static BoardBuilder getCurrentBoard() {
		return currentBoard;
	}
	public static Tokens getPlayerList() {
		return playerList;
	}
	public static UserInterface getUi() {
		return ui;
	}
	public static Deck getDeck() { return deck; }

	/**
	 * This method waits for PlayerListCreator to tell it to run
	 */
	public static void createGame(){
		currentBoard = new BoardBuilder(playerList);
		deck = new Deck();
		deck.fillMurderEnvelope();
		deck.dealHands(playerList);
		populatePlayerNoteCards();
		ui = new UserInterface(playerList);
		new CardImages();
	}

	public static class PlayerEntry {
		// Designates that the given command was valid
		private static boolean commandSuccessful = false;

		public static boolean getCommandSuccessful() {
			return commandSuccessful;
		}

		// Designates that the player's move was successful
		private static boolean movementSuccessful = false;

		public static boolean isMovementSuccessful() {
			return movementSuccessful;
		}

		// Designates whether player's choice in exiting a room was valid
		private static boolean roomExitCheck;

		public static boolean getRoomExitCheck() {
			return roomExitCheck;
		}

		public static boolean wasTurnSuccessful() {
			return (commandSuccessful && movementSuccessful);
		}

		public static void resetSwitches() {
			commandSuccessful = false;
			movementSuccessful = false;
		}

		/**
		 * This method handles the logical aspect of a player's move once they press the 'perform action' button.
		 * Pressing that button calls this method, which checks that the entered command was valid.
		 * If so, it calls a "handler" to check whether the command can be carried out.
		 *
		 * @param player player
		 * @param entry  entered command
		 * @return string with result of command or error message
		 */
		public static String ActionPerformer(Token player, String entry) {
			// Check that user input was a valid command (no game logic check yet, just that the command was allowed)
			boolean validEntryCheck = AcceptedUserInputs.checkForValidEntry(player, entry);
			// Setting value to result will switch the commandSuccessful boolean to true if it is valid
			if (!validEntryCheck)
				return "Please Enter a Valid Command!";

			commandSuccessful = true;
			String result = "";

			if (entry.replaceAll("\\s+","").toLowerCase().equals("help") || entry.replaceAll("\\s+","").toLowerCase().equals("?")){
					return "help";
			}

			// If we pass the above check, call the appropriate game logic handler

			if (entry.replaceAll("\\s+","").toLowerCase().equals("done")){
				Dice.setMovesLeft(0);
				result = "done";
				movementSuccessful = true;
				return result;
			}
			if (entry.replaceAll("\\s+","").toLowerCase().equals("quit")) {
				return quitGameHandler();
			}
			// Don't set movement successful if player is just viewing notes
			if(entry.replaceAll("\\s+","").toLowerCase().equals("notes")) {
				return "notes";
			}
			if(entry.replaceAll("\\s+","").toLowerCase().equals("log")) {
				return "log";
			}
			if(entry.replaceAll("\\s+","").toLowerCase().equals("cheat"))
				return "cheat";

			switch (player.getLocationAsString()) {
				case "floor":
					result = FloorMovementHandler(player, entry);
					break;
				case "room":
					result = RoomActionHandler(player, entry);
					if (result.equals("exitChoice")){
						System.out.println("Command: " + commandSuccessful +
								"\nMovement: " + movementSuccessful);
					}
					break;
				// This is a placeholder for when the player is solving
				case "cellar":
					result = "solving";
					break;
			}
			// If move was successful, subtract one more from dice roll
			if (PlayerEntry.wasTurnSuccessful())
				Dice.decrementMovesLeft();
			return result;
		}

		/**
		 * This method handles the logic necessary to move a player from one square to another.
		 *
		 * @param player player
		 * @param move   command
		 * @param to     ending square
		 * @param from   starting square
		 * @return String with result or error
		 */
		public static String movePlayerToSquare(Token player, String move, BoardSquare to, BoardSquare from) {
			to.setPlayerOn(player);
			if (to instanceof FloorSquare)
				player.setSquareOn(to);
			from.removePlayerOn();

			PlayerEntry.movementSuccessful = true;

			return player.getName() + " has moved " + move;
		}

		/**
		 * If player is moving along the floor, this method checks geography and ensures
		 * that their movement is valid.
		 *
		 * @param player token who is moving
		 * @param move   entered command
		 * @return result of movement or an error if invalid
		 */
		public static String FloorMovementHandler(Token player, String move) {
			BoardSquare square = player.getSquareOn();
			String moveResult = "That move is not allowed.";
			// Check user input in lower case and without whitespaces
			switch (move.replaceAll("\\s+", "").toLowerCase()) {
				/*
				Player Movement from Floor Square
                Checks:
                Player isn't at the top/bottom/far left/far right of the board
                The square above the player is traversable (a FloorSquare or EntrySquare)
                There is no player on the above/below/left/right square
                 */
				case "up":
				case "u":
					if ((square.getAbove() instanceof FloorSquare || square.getAbove() instanceof EntrySquare)
							&& !(square.getAbove().isPlayerOn())) {
						moveResult = movePlayerToSquare(player, move, square.getAbove(), square);
					}
					break;
				case "down":
				case "d":
					if ((square.getBelow() instanceof FloorSquare || square.getBelow() instanceof EntrySquare)
							&& !(square.getBelow().isPlayerOn())) {
						moveResult = movePlayerToSquare(player, move, square.getBelow(), square);
					}
					break;
				case "left":
				case "l":
					if ((square.getLeft() instanceof FloorSquare || square.getLeft() instanceof EntrySquare)
							&& !(square.getLeft().isPlayerOn())) {
						moveResult = movePlayerToSquare(player, move, square.getLeft(), square);
					}
					break;
				case "right":
				case "r":
					if ((square.getRight() instanceof FloorSquare || square.getRight() instanceof EntrySquare)
							&& !(square.getRight().isPlayerOn())) {
						moveResult = movePlayerToSquare(player, move, square.getRight(), square);
					}
					break;
				default:
					moveResult = "Default Switch in GameLogic";
					break;
			}
			return moveResult;
		}

		/**
		 * If a player is in a room and entering a command, this method carries out that command.
		 * No command can be logically impossible from a room unless a player tries to take a nonexistent passage
		 *
		 * @param player  player
		 * @param command command
		 * @return Can be:
		 * ... passage... : 	player is taking the passage to another room
		 * exit:				player is exiting from a room with only one exit
		 * exitChoice: 		player is exiting and must choose one of multiple exits
		 * question:				this will be the result of what happens from questionPrompt
		 */
		public static String RoomActionHandler(Token player, String command) {
			String result = "";
			// Any move is possible from a room
			movementSuccessful = true;
			// Check command without spaces and in lower case
			switch (command.replaceAll("\\s+", "").toLowerCase()) {
				/*
				Player Movement from Room
				 */
				case "passage":
				case "p":
					if (player.getInRoom().getSecretPassage() == null) {
						result = "The " + player.getInRoom().getName() + " does not have a secret passage!";
						movementSuccessful = false;
						break;
					}
					if (!isThisTheFirstMove()) {
						result = "You have already moved this turn!";
						movementSuccessful = false;
					}
					else {
						player.exitRoomThroughPassage();
						result = "passage";
						// Player cannot move after taking a secret passage
						Dice.setMovesLeft(1);
					}
					break;
				case "exit":
				case "e":
					// If room has multiple exits, call the dialog to see which one the user wants
					if (player.getInRoom().getExits().size() > 1)
						result = "exitChoice";
					else {
						player.exitRoom(0);
						result = "exit";
					}
					break;
				/*
				Player is in the room and wants to make a question
				 */
				case "question":
				case "q":
					result = questionPrompt();
					Dice.setMovesLeft(2);
					break;
			}
			return result;
		}

		public static void checkRoomExit(Token p, int selection) {
			/*
			If the choice was valid, the player exits the room
			 */
			if (AcceptedUserInputs.roomExitCheck(p.getInRoom(), selection - 1)) {
				p.setPosition(p.getInRoom().getExits().get(selection - 1).getPosition());
				p.exitRoom(selection - 1);
				roomExitCheck = true;
			}
			/*
			Otherwise, clear input field and keep asking
			 */
			else {
				ui.clearInputField();
				roomExitCheck = false;
			}
		}

		public static String questionPrompt() {
			return "question";
		}

		public static String quitGameHandler() {
			int result = JOptionPane.showConfirmDialog(null,
					"Are you sure you would like to quit?", "Cluedo",
					JOptionPane.YES_NO_OPTION);
			if (result == 0)
				System.exit(1);

			return "Game Will Continue";
		}
	}

	public static void checkEndOfTurn() {
		// Switch player if the turn is over (or if they entered 'done')
		if (GameLogic.getMovesLeft() == 0) {
			// This calls UI to advance turn and change the output display
				// Done from here so it can be called from multiple places in UI
			getUi().setCurrentPlayer(playerList.advanceTurn(getUi().getCurrentPlayer()));

			Dice.rollForTurn();

			// Update move history to show new turn and where the player is.
			//      This will be less useful when GUI works
			getUi().getOut().updateMoveHistory("It is now " +
					getUi().getCurrentPlayer().getName() + "'s turn. Location: "
					+ getUi().getCurrentPlayer().safeGetLocation());
		}
//		if (getUi().getCurrentPlayer().getInRoom() != null &&
//				getUi().getCurrentPlayer().getInRoom().getName().equals("Cellar"))
//			GameLogic.Accusing.startAccusing(getUi().getCurrentPlayer());
//		else
			// Same as above - called from several places in UI so it's here
			getUi().refreshDisplayForNextTurn(getUi().getCurrentPlayer());
	}

	/*
	 * Update player note cards with public deck and individual hands
	 */
	public static void populatePlayerNoteCards() {
		for (int i = 0; i < playerList.getNumberOfPlayers(); i++) {
			playerList.getPlayerByIndex(i).populateNoteCards(deck);
		}
	}

	/*
	 * These methods are for player movement and dice rolls
	 */
	public static class Dice {
		private static Random rand = new Random();
		private static int movesLeft;
		private static int initialNumberOfMoves;

		public static void rollForTurn(){
			movesLeft = rollDice();
			// Use this to check if player has moved
			initialNumberOfMoves = movesLeft;
		}

		public static int rollDice(){
			return rand.nextInt(6)+1 + rand.nextInt(6)+1;
		}

		public static void decrementMovesLeft(){
			movesLeft--;
		}

		public static void setMovesLeft(int num){
			Dice.movesLeft = num;
		}
	}

	/*
    These methods are just accessors for GameLogic and UI
     */
	public static int getMovesLeft(){
		return Dice.movesLeft;
	}

	/*
    Returns true if player has not yet moved
     */
	public static boolean isThisTheFirstMove(){
		return Dice.movesLeft == Dice.initialNumberOfMoves;
	}

	public static class Guessing {
		protected static Token accusingPlayer;
		protected static Token answeringPlayer;
	    protected static Card accusedPlayer;
	    protected static Card accusedWeapon;
		protected static Card accusedRoom;

		public static Token getAccusingPlayer() {
			return accusingPlayer;
		}

		public static Token getAnsweringPlayer() {
			return answeringPlayer;
		}

		public static Card getAccusedPlayer() {
			return accusedPlayer;
		}

		public static Card getAccusedWeapon() {
			return accusedWeapon;
		}

		public static Card getAccusedRoom() {
			return accusedRoom;
		}

		public static void startGuessing() {
	    	accusingPlayer = ui.getCurrentPlayer();
	    	answeringPlayer = null;
		}

        public static void initiateRoundOfQuestioning(String player, String weapon, String room) {
	    	answeringPlayer = ui.getCurrentPlayer().next();
			accusedPlayer = deck.getPlayerCardByName(player);
			accusedWeapon = deck.getWeaponCardByName(weapon);
			accusedRoom = deck.getRoomCardByName(room);

			JOptionPane.showMessageDialog
					(null, "It is now " + Guessing.answeringPlayer.getName() + "'s Turn to Answer.");
			// TODO: Josh uncomment me when you fix BoardImage movement code
			if (playerList.getIndexOfPlayerByName(accusedPlayer.getName()) >  0) {
				System.out.println("Testing " + ui.getCurrentPlayer().getPreviousRoom().getName());
				playerList.getPlayerByIndex
						(playerList.getIndexOfPlayerByName(accusedPlayer.getName())).sudoSetSquareOn
						(ui.getCurrentPlayer().getInRoom().getEntrances().get(0), ui.getBoardImage(), ui);
				BoardImage image=ui.getBoardImage();
				JPanel movementPanel = image.passageMove(ui.getCurrentPlayer(), ui.getCurrentPlayer().getPreviousRoom(), ui.getCurrentPlayer().getInRoom());
				ui.getIn().refreshBoard(movementPanel);
			}
		}

		public static void unsuccessfulGuess() {
			ui.getOut().updateMoveHistory(ui.getCurrentPlayer().getName() + "'s questioning was unsuccessful!");
			// The next two methods could be done better, but I figured I would just reuse code
			Dice.setMovesLeft(0);
			checkEndOfTurn();
			ui.refreshGuiFromUnsuccessfulGuess();
		}
    }

    public static class Accusing {
		static Token accusingPlayer;

		public static void startAccusing(Token t) {
			accusingPlayer = t;
			AccuseMenu menu = new AccuseMenu(ui.getDisplay(), ui.getUserDisplay(), t);
			menu.switchToAccuseMenu();
		}

		public static boolean checkAccusation(String[] guesses, Token accuser, AccuseMenu menu) {
			int correctGuessCounter = 0;
			for (Card c : deck.getMurderEnvelope()) {
				for (int i=0; i<3; i++) {
					if (guesses[i].equals(c.getName()))
						correctGuessCounter++;
				}
			}
			if (correctGuessCounter == 3) {
				menu.viewMurderEnvelope(true);
				return true;
			}
			else {
				eliminatePlayer(accuser);
				menu.viewMurderEnvelope(false);
				return false;
			}
		}

		public static void eliminatePlayer(Token p) {
			if (p==null)
				System.out.println("Player is null");
			else {
				getUi().removePlayer();
				ui.getOut().updateMoveHistory
					(p.getName() + " has made an incorrect accusation and was eliminated!");
				p.removeFromGame();

				//TODO: Josh: This breaks everything so I commented it out
//				UserInterface.myImg.removePlayer(p);
				playerList.decrementNumberOfPlayers();
				Dice.setMovesLeft(0);
				checkEndOfTurn();
			}
		}
	}

	public static void playMusic(boolean startMenu) {
		if (startMenu) {
            if (!LoopSound.playSong) {
                startMusic.restartMusic();
            }
            try {
                startMusic = new LoopSound();
                startMusic.play();
                music = true;
            } catch (Exception e) {
                System.out.println("Music failed to Load");
                e.printStackTrace();
            }
        }
        else {
            try {
                startMusic = new GameMusic();
                startMusic.play();
            } catch (Exception e) {
                System.out.println("Music failed to Load");
                e.printStackTrace();
            }
        }
	}
}
