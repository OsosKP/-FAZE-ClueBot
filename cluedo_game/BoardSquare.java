package cluedo_game;

public interface BoardSquare{
	/* X and Y position of the Square */
	int[] position = new int[2];
	
	/**
     * Returns coordinates (indices) of this square on the board
     * @return An integer array of the x and y coordinates
     */
    int[] getLocation();
    BoardSquare getSquareType();
    boolean isPlayerOn();
    void setPlayerOn(Token playerOn);
    void removePlayerOn();
    Token getPlayerOn();
    /**
     * setGeography
     * Places pointers to the square above, below, to the left and to the right of this one
     * @param board the game board
     */
    void setGeography(Board board);
    BoardSquare getAbove();
    BoardSquare getBelow();
    BoardSquare getLeft();
    BoardSquare getRight();
}
