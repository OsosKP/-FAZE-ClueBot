package cluedo_game;

public interface BoardSquare {
    /**
     * Returns coordinates (indices) of this square on the board
     * @return An integer array of the x and y coordinates
     */
    int[] getLocation();

    BoardSquare getNorthPointer();
    BoardSquare getSouthPointer();
    BoardSquare getEastPointer();
    BoardSquare getWestPointer();
}
