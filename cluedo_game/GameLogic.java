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
		public static boolean PlayerMovementHandler(Token player, String move) {
			BoardSquare square = player.getSquareOn();
			switch (move) {
				/*
                Checks:
                Player isn't at the top/bottom/far left/far right of the board
                The square above the player is traversable (a FloorSquare or EntrySquare)
                There is no player on the above/below/left/right square
                 */
				case "up":
					if ((square.getAbove() instanceof FloorSquare || square.getAbove() instanceof EntrySquare)
							&& !(square.getAbove().isPlayerOn())) {
						square.getAbove().setPlayerOn(player);
						square.removePlayerOn();
						return true;
					}
				case "down":
					if ((square.getBelow() instanceof FloorSquare || square.getBelow() instanceof EntrySquare)
							&& !(square.getBelow().isPlayerOn())) {
						square.getBelow().setPlayerOn(player);
						square.removePlayerOn();
						return true;
					}
					break;
				case "left":
					if ((square.getLeft() instanceof FloorSquare || square.getLeft() instanceof EntrySquare)
							&& !(square.getLeft().isPlayerOn())) {
						square.getLeft().setPlayerOn(player);
						square.removePlayerOn();
						return true;
					}
					break;
				case "right":
					if ((square.getRight() instanceof FloorSquare || square.getRight() instanceof EntrySquare)
							&& !(square.getRight().isPlayerOn())) {
						square.getRight().setPlayerOn(player);
						square.removePlayerOn();
						return true;
					}
					break;
				default:
					System.out.println("Error");
			}
			return false;
		}
	}
	
}
