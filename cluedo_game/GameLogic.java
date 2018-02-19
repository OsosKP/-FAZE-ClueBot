// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;
/**
 * class that is going to handle all of the game logic
 * @author george
 *
 */
public class GameLogic {
	/* This is going to handle the board */
	BoardBuilder currentBoard;
	Tokens playerList;
	UserInterface ui;

	public GameLogic() {
		this.playerList = new Tokens();
		playerList.setPlayerList();
		this.currentBoard = new BoardBuilder(playerList);
		this.ui = new UserInterface(currentBoard);
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

		public static boolean wasTurnSuccessful(){
			return (commandSuccessful && movementSuccessful);
		}
		public static void resetSwitches(){
			commandSuccessful = false;
			movementSuccessful = false;
		}

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

		public static String movePlayerToSquare(Token player, String move, BoardSquare to, BoardSquare from){
			to.setPlayerOn(player);
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

		public static String guessPrompt(){
			// This is just a placeholder for a later sprint
			return "Guess Prompt";
		}
	}
}
