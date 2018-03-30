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

	public GameLogic() {
		AcceptedUserInputs.setAcceptedUserInputs();
		// TODO: Implementation for actual product - switch to this before turning in
//		PlayerListCreator playersCreator = new PlayerListCreator();
//		playerList = playersCreator.getPlayerList();

		// TODO: Debugging setup
		playerList = new Tokens();
		playerList.setDebugPlayerList();
		createGame();
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

		public static void setRoomExitCheck(boolean roomExitCheck) {
			roomExitCheck = roomExitCheck; //wtf is this
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

		    //TODO: For debugging only
            if(AcceptedUserInputs.simpleString(move).equals("question"))
                return "question";


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
	    private static Token accusedPlayer;
	    private static Weapon accusedWeapon;

	    public static void getAccusedPlayerAndWeapon(int player, int weapon){
	        accusedPlayer = playerList.getPlayerByIndex(player);

        }
    }



}
