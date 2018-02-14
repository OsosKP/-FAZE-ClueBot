// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

public class FloorSquare implements BoardSquare {
    //Instance Variables
    private Token playerOn;         // The player token currently on the square
    private final Token spawnPoint; // The player who spawns on this square
    private int[] position;         // Coordinates of this square on the board
    // Navigational pointers
    private BoardSquare above;
    private BoardSquare below;
    private BoardSquare toLeft;
    private BoardSquare toRight;

    //
    //Constructors
    //
    public FloorSquare(int x, int y, Token spawnPoint) {
	    /* Initializing the array */
    	this.position = new int[2];
    	this.position[0] = x;
	    this.position[1] = y;
  		this.spawnPoint = spawnPoint;
  		this.playerOn = spawnPoint;
  	}

  	public FloorSquare(int x, int y) {
	    /* Initializing the array */
    	this.position = new int[2];
    	
  		this.position[0] = x;
  		this.position[1] = y;
  		this.spawnPoint = null;
  		this.playerOn = null;
  	}

    //
    //Accessors
    //
    public String toString() { return "floor"; }
    public FloorSquare getSquareType() { return this; }
    public boolean isPlayerOn() {
        return (this.playerOn != null);
    }
    public Token getPlayerOn() {return playerOn;}
    public Token getSpawnPoint() {return spawnPoint;}
    public int[] getPosition() {return position;}
    /*
    Geographical Pointers
     */
    public BoardSquare getAbove() { return this.above; }
    public BoardSquare getBelow() { return this.below; }
    public BoardSquare getLeft() { return this.toLeft; }
    public BoardSquare getRight() { return this.toRight; }

    //
    //Mutators
    //
  	public void setPlayerOn(Token playerOn) {this.playerOn = playerOn;}
    public Token removePlayerOn() {
        Token removedPlayer = this.playerOn;
        this.playerOn = null;
        return removedPlayer;
    }

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
        if(this.position[0] < 23)
            this.below = board.getSquare(this.position[0]+1, this.position[1]);
        else
            this.below = null;
        // Set a pointer to the square to the left of this one
        if(this.position[1] > 0)
            this.toLeft = board.getSquare(this.position[0], this.position[1]-1);
        else
            this.toLeft = null;
        // Set a pointer to the square to the right of this one
        if(this.position[1] < 24)
            this.toRight = board.getSquare(this.position[0], this.position[1]+1);
        else
            this.toRight = null;
    }
}
