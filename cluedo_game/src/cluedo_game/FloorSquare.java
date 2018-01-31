package cluedo_game;

public class FloorSquare implements BoardSquare {
    /*
        Instance Variables
     */
    private Token playerOn;         // The player token currently on the square
    private final Token spawnPoint; // The player who spawns on this square
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

    /**
     * Constructor for squares that will be a spawn point
     * @param spawn The player token that spawns on this square
     * @param location Coordinates of this square on the grid
     * @param north The square above this one
     * @param south The square below this one
     * @param east The square to the right of this one
     * @param west The square to the left of this one
     */
    public FloorSquare(Token spawn, int[] location, BoardSquare north,
                       BoardSquare south, BoardSquare east, BoardSquare west) {
        this.spawnPoint = spawn;

        this.location = location;
        this.northPointer = north;
        this.southPointer = south;
        this.eastPointer = east;
        this.westPointer = west;
    }

    /**
     * Constructor for squares that will not be a spawn point
     * @param location Coordinates of this square on the grid
     * @param north The square above this one
     * @param south The square below this one
     * @param east The square to the right of this one
     * @param west The square to the left of this one
     */
    public FloorSquare(int[] location, BoardSquare north, BoardSquare south,
                       BoardSquare east, BoardSquare west) {
        this.spawnPoint = null;

        this.location = location;
        this.northPointer = north;
        this.southPointer = south;
        this.eastPointer = east;
        this.westPointer = west;
    }

    public Token getPlayerOn() {
        return playerOn;
    }

    public void setPlayerOn(Token playerOn) {
        this.playerOn = playerOn;
    }

    public void removePlayerOn() {
        this.playerOn = null;
    }

    public Token getSpawnPoint() {
        return spawnPoint;
    }

    @Override
    public int[] getLocation() {
        return location;
    }
}
