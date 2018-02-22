// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

import javax.swing.*;
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
	static UserInterface ui;

	public GameLogic() {
		AcceptedUserInputs.setAcceptedUserInputs();
		playerList = new Tokens();
		playerList.setPlayerList();
		currentBoard = new BoardBuilder(playerList);
		ui = new UserInterface(currentBoard);
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

	public static class PlayerEntry {
		// Designates that the given command was valid
		private static boolean commandSuccessful = false;
		public static boolean getCommandSuccessful(){
			return commandSuccessful;
		}
		public static void resetCommandSuccessfulSwitchToFalse(){
			commandSuccessful = false;
		}

		// Designates that the player's move was successful
		private static boolean movementSuccessful = false;
		public static boolean isMovementSuccessful() {
			return movementSuccessful;
		}
		public static void resetMovementSuccessfulSwitchToFalse() {
			movementSuccessful = false;
		}

		// Designates whether player's choice in exiting a room was valid
		private static boolean roomExitCheck;
		public static boolean getRoomExitCheck() {
			return roomExitCheck;
		}
		public static void setRoomExitCheck(boolean roomExitCheck) {
			roomExitCheck = roomExitCheck;
		}

		public static boolean wasTurnSuccessful(){
			return (commandSuccessful && movementSuccessful);
		}
		public static void resetSwitches(){
			commandSuccessful = false;
			movementSuccessful = false;
		}

		/**
		 * This method handles the logical aspect of a player's move once they press the 'perform action' button.
		 * Pressing that button calls this method, which checks that the entered command was valid.
		 * If so, it calls a "handler" to check whether the command can be carried out.
		 * @param player player
		 * @param entry entered command
		 * @return string with result of command or error message
		 */
		public static String ActionPerformer(Token player, String entry) {

			// Check that user input was a valid command (no game logic check yet, just that the command was allowed)
			boolean validEntryCheck = AcceptedUserInputs.checkForValidEntry(player, entry);
			// Setting value to result will switch the commandSuccessful boolean to true if it is valid
			if (!validEntryCheck)
				return "Please Enter a Valid Command!";

			commandSuccessful = true;

			// If we pass the above check, call the appropriate game logic handler
			String result = "";
			switch (player.getLocationAsString()){
				case "floor":
					result = FloorMovementHandler(player, entry);
					break;
				case "room":
					result = RoomActionHandler(player, entry);
					break;
				// This is a placeholder for when the player is solving
				case "cellar":
					result = "Solving";
					break;
			}
			return result;
		}

		/**
		 * This method handles the logic necessary to move a player from one square to another.
		 * @param player player
		 * @param move command
		 * @param to ending square
		 * @param from starting square
		 * @return String with result or error
		 */
		public static String movePlayerToSquare(Token player, String move, BoardSquare to, BoardSquare from){
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
		 * @param player token who is moving
		 * @param move entered command
		 * @return result of movement or an error if invalid
		 */
		public static String FloorMovementHandler(Token player, String move) {
			BoardSquare square = player.getSquareOn();
			String moveResult = "That move is not allowed.";
			switch (move) {
				/*
				Player Movement from Floor Square
                Checks:
                Player isn't at the top/bottom/far left/far right of the board
                The square above the player is traversable (a FloorSquare or EntrySquare)
                There is no player on the above/below/left/right square
                 */
				case "up":
					if ((square.getAbove() instanceof FloorSquare || square.getAbove() instanceof EntrySquare)
							&& !(square.getAbove().isPlayerOn())) {
						moveResult =  movePlayerToSquare(player, move, square.getAbove(), square);
					}
					break;
				case "down":
					if ((square.getBelow() instanceof FloorSquare || square.getBelow() instanceof EntrySquare)
							&& !(square.getBelow().isPlayerOn())) {
						moveResult =  movePlayerToSquare(player, move, square.getBelow(), square);
					}
					break;
				case "left":
					if ((square.getLeft() instanceof FloorSquare || square.getLeft() instanceof EntrySquare)
							&& !(square.getLeft().isPlayerOn())) {
						moveResult =  movePlayerToSquare(player, move, square.getLeft(), square);
					}
					break;
				case "right":
					if ((square.getRight() instanceof FloorSquare || square.getRight() instanceof EntrySquare)
							&& !(square.getRight().isPlayerOn())) {
						moveResult =  movePlayerToSquare(player, move, square.getRight(), square);
					}
					break;
				default:
					moveResult = "Default Switch";
					break;
			}
			return moveResult;
		}

		/**
		 * If a player is in a room and entering a command, this method carries out that command.
		 * No command can be logically impossible from a room unless a player tries to take a nonexistent passage
		 * @param player player
		 * @param command command
		 * @return Can be:
		 * 					... passage... : 	player is taking the passage to another room
		 * 					exit:				player is exiting from a room with only one exit
		 * 					exitChoice: 		player is exiting and must choose one of multiple exits
		 * 					guess:				this will be the result of what happens from guessPrompt
		 */
		public static String RoomActionHandler(Token player, String command){
			String result = "";
			// Any move is possible from a room
			movementSuccessful = true;
			switch (command) {
				/*
				Player Movement from Room
				 */
				case "passage":
					player.exitRoomThroughPassage();
					result = player.getName() + " has taken a secret passage to the "
							+ player.getInRoom().getName();
					break;
				case "exit":
					// If room has multiple exits, call the dialog to see which one the user wants
					if (player.getInRoom().getExits().size() > 1)
						result = "exitChoice";
					else {
						/*
						Not working - see Token class
						 */
						player.exitRoom(0);
						result = "exit";
					}
					break;
				/*
				Player is in the room and wants to make a guess
				 */
				case "guess":
					result = guessPrompt();
			}
			return result;
		}

		public static void checkRoomExit(Token p, int selection) {
			/*
			If the choice was valid, the player exits the room
			 */
			if(AcceptedUserInputs.roomExitCheck(p.getInRoom(), selection-1)) {
				// selection-1 because indices of exits start at 0
				p.exitRoom(selection-1);
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

		public static String guessPrompt(){
			// This is just a placeholder for a later sprint
			return "Guess Prompt";
		}
		
		class Dice{
			/* We need to roll two dice to determines the movement */
			int dice1 = 0, dice2 = 0;
			
			/* Going to represent the rolling of the dice*/
			Random rand = new Random();
			
			int[] rollDice() {
				int[] myDice = new int[2];

				/* Rolling the dice [max 6 --  min 1] */
				dice1 = rand.nextInt(6) + 1;
				dice2 = rand.nextInt(6) + 1;
				
				
				myDice[0] = dice1;
				myDice[1] = dice2;
				
				return myDice;
			}
			
		}
	}
}
