// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

/**
 * WallSquare
 * The BoardSquares which cannot be traversed
 * See BoardSquare for detailed information on the methods
 */
public class WallSquare implements BoardSquare {
    /*
        Instance variables
     */
    private int[] position = null;         // Coordinates of this square on the board
    // Navigational pointers
    private BoardSquare above;
    private BoardSquare below;
    private BoardSquare toLeft;
    private BoardSquare toRight;
    /*
    Constructor
     */
    
    public WallSquare(int x, int y){
    	/* Initializing the position array */
    	this.position = new int[2];
    	this.position[0] = x;
    	this.position[1] = y;
    }

    public WallSquare(){
        // This is a dummy WallSquare for type check
    }
    
    public int[] getPosition() {
        return position;
    }
    @Override
    public String toString() { return "wall"; }
    /**
     * getSquareType
     * This method returns an object of type WallSquare
     * This is used to check that the object containing this method is a WallSquare
     * @return temporary object of type WallSquare
     */
    @Override
    public WallSquare getSquareType() {
        return this;
    }
    /*
    Geographical Pointers
     */
    public BoardSquare getAbove() { return this.above; }
    public BoardSquare getBelow() { return this.below; }
    public BoardSquare getLeft() { return this.toLeft; }
    public BoardSquare getRight() { return this.toRight; }

    /*
    These overridden methods don't matter for WallSquare
     */
    @Override
    public void setPlayerOn(Token playerOn) { }
    @Override
    public Token removePlayerOn() { return null; }
    @Override
    public Token getPlayerOn() { return null; }
    @Override
    public boolean isPlayerOn(){ return false; }

    /**
     * setGeography
     * Places pointers to the square above, below, to the left and to the right of this one
     * @param board the game board
     */
    public void setGeography(BoardBuilder board){
        // Set a pointer to the square above this one
        if(this.position[0] > 0)
            this.above = board.getSquare(this.position[0]-1, this.position[1]);
        else
            this.above = null;
        // Set a pointer to the square below this one
        if(this.position[0] < 24)
            this.below = board.getSquare(this.position[0]+1, this.position[1]);
        else
            this.below = null;
        // Set a pointer to the square to the left of this one
        if(this.position[1] > 0)
            this.toLeft = board.getSquare(this.position[0], this.position[1]-1);
        else
            this.toLeft = null;
        // Set a pointer to the square to the right of this one
        if(this.position[1] < 23)
            this.toRight = board.getSquare(this.position[0], this.position[1]+1);
        else
            this.toRight = null;
    }
}
