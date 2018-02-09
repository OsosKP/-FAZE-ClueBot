package cluedo_game;

public class WallSquare implements BoardSquare {
    /*
        Instance variables
     */
    private int[] location;         // Coordinates of this square on the board
    // Navigational pointers
    private BoardSquare above;
    private BoardSquare below;
    private BoardSquare toLeft;
    private BoardSquare toRight;
    /*
    Constructor
     */
    public WallSquare(int x, int y){
    	this.location[0] = x;
    	this.location[1] = y;
    }

    public WallSquare(){
        // This is a dummy WallSquare for type check
    }

    /**
     * setGeography
     * Places pointers to the square above, below, to the left and to the right of this one
     * @param board the game board
     */
    public void setGeography(Board board){
        // Set a pointer to the square above this one
        if(this.location[0] > 0)
            this.above = board.getSquare(this.location[0]-1, this.location[1]);
        else
            this.above = null;
        // Set a pointer to the square below this one
        if(this.location[0] < 23)
            this.below = board.getSquare(this.location[0]+1, this.location[1]);
        else
            this.below = null;
        // Set a pointer to the square to the left of this one
        if(this.location[1] > 0)
            this.toLeft = board.getSquare(this.location[0], this.location[1]-1);
        else
            this.toLeft = null;
        // Set a pointer to the square to the right of this one
        if(this.location[1] < 24)
            this.toRight = board.getSquare(this.location[0], this.location[1]+1);
        else
            this.toRight = null;
    }
    
    @Override
    public int[] getLocation() {
        return location;
    }

    /**
     * getSquareType
     * This method returns an object of type WallSquare
     * This is used to check that the object containing this method is a WallSquare
     * @return temporary object of type WallSquare
     */
    @Override
    public BoardSquare getSquareType() {
        return new WallSquare();
    }

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
    public void removePlayerOn() { }
    @Override
    public Token getPlayerOn() { return null; }
    @Override
    public boolean isPlayerOn(){ return false; }

    public String toString() { return "wall"; }
}
