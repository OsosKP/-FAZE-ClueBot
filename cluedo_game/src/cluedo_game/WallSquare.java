package cluedo_game;

public class WallSquare implements BoardSquare {
    /*
        Instance variables
     */
    private int[] location;         // Coordinates of this square on the board
    /*
        These variables point to the surrounding board squares
        This might be ultimately unnecessary since we're doing this as a 2D array,
            but I figured it may come in handy at some point - Kelsey
     */
    private final BoardSquare northPointer;
    private final BoardSquare southPointer;
    private final BoardSquare eastPointer;
    private final BoardSquare westPointer;

    public WallSquare(int[] location, BoardSquare north, BoardSquare south,
                      BoardSquare east, BoardSquare west){
        this.location = location;
        this.northPointer = north;
        this.southPointer = south;
        this.eastPointer = east;
        this.westPointer = west;
    }

    @Override
    public int[] getLocation() {
        return location;
    }
}
