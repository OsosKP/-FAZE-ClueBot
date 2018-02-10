package cluedo_game;

public class FloorSquare implements BoardSquare {
    //Instance Variables
    private Token playerOn;         // The player token currently on the square
    private final Token spawnPoint; // The player who spawns on this square
    private int[] location;         // Coordinates of this square on the board
    // Navigational pointers
    private BoardSquare above;
    private BoardSquare below;
    private BoardSquare toLeft;
    private BoardSquare toRight;

    //
    //Constructors
    //
    public FloorSquare(int x, int y, Token spawnPoint) {
	    this.location[0] = x;
	    this.location[1] = y;
  		this.spawnPoint = spawnPoint;
  	}

  	public FloorSquare(int x, int y) {
  		this.location[0] = x;
  		this.location[1] = y;
  		this.spawnPoint = null;
  		this.playerOn = null;
  	}

  	public FloorSquare(){ // This is just a dummy square to check type
        this.spawnPoint = null;
    }

    //
    //Accessors
    //
    public Token getPlayerOn() {return playerOn;}
    public Token getSpawnPoint() {return spawnPoint;}
    @Override
    public int[] getLocation() {return location;}

    /**
     * getSquareType
     * This method returns an object of type FloorSquare
     * This is used to check that the object containing this method is a FloorSquare
     * @return temporary object of type FloorSquare
     */
    @Override
    public String getSquareType() {
        return "Floor";
    }

    @Override
    public boolean isPlayerOn() {
        return (this.playerOn != null);
    }

    public BoardSquare getAbove() { return this.above; }
    public BoardSquare getBelow() { return this.below; }
    public BoardSquare getLeft() { return this.toLeft; }
    public BoardSquare getRight() { return this.toRight; }

    //
    //Mutators
    //
  	public void setPlayerOn(Token playerOn) {this.playerOn = playerOn;}
    public void removePlayerOn() {this.playerOn = null;}

    /**
     * setGeography
     * Places pointers to the square above, below, to the left and to the right of this one
     * @param board the game board
     */
    public void setGeography(BoardBuilder board){
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

    public String toString() { return "floor"; }
}
