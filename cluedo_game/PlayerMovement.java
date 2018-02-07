package cluedo_game;

/**
 * This method receives instructions from the user input and moves the player's token accordingly
 */
public class PlayerMovement {
    public static boolean movementCheck(BoardSquare[][] board, Token player, String move, int xLoc, int yLoc){
        switch(move){
            case "up":
                /*
                Checks:
                Player isn't at the top of the board
                The square above the player is traversable (a FloorSquare or EntrySquare)
                There is no player on the above square
                 */
                if(xLoc > 0 && !(board[xLoc-1][yLoc].getSquareType() instanceof WallSquare)
                        && !board[xLoc-1][yLoc].isPlayerOn()){

                }
        }
    }
}
