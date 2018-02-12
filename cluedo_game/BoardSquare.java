// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

public interface BoardSquare{
	/* X and Y position of the Square */
	int[] position = new int[2];

	/**
     * Returns coordinates (indices) of this square on the board
     * @return An integer array of the x and y coordinates
     */
    int[] getLocation();
    String getSquareType();//We can change this later
    boolean isPlayerOn();
    void setPlayerOn(Token playerOn);
    void removePlayerOn();
    Token getPlayerOn();
    String toString();
    /**
     * setGeography
     * Places pointers to the square above, below, to the left and to the right of this one
     * @param board the game board
     */
    void setGeography(BoardBuilder board);
    BoardSquare getAbove();
    BoardSquare getBelow();
    BoardSquare getLeft();
    BoardSquare getRight();
}
