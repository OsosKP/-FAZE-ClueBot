// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

/**
 * This is the interface class for all kinds of square that will appear on the board
 */
public interface BoardSquare{
	/**
     * Returns coordinates (indices) of this square on the board
     * @return An integer array of the x and y coordinates
     */
    int[] getPosition();
    /*
    Returns a string representation of the type of square
     */
    String toString();
    /*
    Returns the square type for specifying from BoardSquare
     */
    BoardSquare getSquareType();
    // Returns true if this square is occupied by a player
    boolean isPlayerOn();
    Token getPlayerOn();

    BoardSquare getAbove();
    BoardSquare getBelow();
    BoardSquare getLeft();
    BoardSquare getRight();

    /**
     * Puts given player on this square
     * @param playerOn token that is moving here
     */
    void setPlayerOn(Token playerOn);
    // Sets playerOn to null and returns that token so that it can be moved to another square more easily
    Token removePlayerOn();
    /**
     * setGeography
     * Places pointers to the square above, below, to the left and to the right of this one
     * @param board the game board
     */
    void setGeography(BoardBuilder board);
}
