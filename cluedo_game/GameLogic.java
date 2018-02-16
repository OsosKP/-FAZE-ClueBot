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
	
	public GameLogic(BoardBuilder currentBoard) {

		this.currentBoard = currentBoard;
	}
	
	/* this is where we have to initialize our boardBuilder */
	public void startGame(int numPlayers) {
		
	}

	/*	1. createRooms()
	 * 	2. addEntrySquares
	 * 	3. addWalls()
	 * 	4. addFloorSquares
	 */

	public static class PlayerEntry {
		private static boolean moveSuccessful = false;
		public static boolean getMoveSuccessful(){
			return moveSuccessful;
		}
		public static void resetMoveSuccessfulSwitchToFalse(){
			moveSuccessful = false;
		}
		public static String PlayerMovementHandler(Token player, String move) {
			BoardSquare square = player.getSquareOn();
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
						square.getAbove().setPlayerOn(player);
						player.setSquareOn(square.getAbove());
						square.removePlayerOn();
						PlayerEntry.moveSuccessful = true;
						return player.getName() + " has moved up.";
					}
					return player.getName() + " is unable to move up";

				case "down":
					if ((square.getBelow() instanceof FloorSquare || square.getBelow() instanceof EntrySquare)
							&& !(square.getBelow().isPlayerOn())) {
						square.getBelow().setPlayerOn(player);
						player.setSquareOn(square.getBelow());
						square.removePlayerOn();
						PlayerEntry.moveSuccessful = true;
						return player.getName() + " has moved down.";
					}
					break;
				case "left":
					if ((square.getLeft() instanceof FloorSquare || square.getLeft() instanceof EntrySquare)
							&& !(square.getLeft().isPlayerOn())) {
						square.getLeft().setPlayerOn(player);
						player.setSquareOn(square.getLeft());
						square.removePlayerOn();
						PlayerEntry.moveSuccessful = true;
						return player.getName() + " has moved left.";
					}
					break;
				case "right":
					if ((square.getRight() instanceof FloorSquare || square.getRight() instanceof EntrySquare)
							&& !(square.getRight().isPlayerOn())) {
						square.getRight().setPlayerOn(player);
						player.setSquareOn(square.getRight());
						square.removePlayerOn();
						PlayerEntry.moveSuccessful = true;
						return player.getName() + " has moved right.";
					}
					break;
				/*
				Player Movement from Room
				 */
				case "passage":
					player.exitRoomThroughPassage();
					PlayerEntry.moveSuccessful = true;
					return player.getName() + " has taken a secret passage to the "
							+ player.getInRoom().getName();
				case "exit":

				default:
					System.out.println("Error");
			}
			return "Unable to Complete Move";
		}
	}
}
