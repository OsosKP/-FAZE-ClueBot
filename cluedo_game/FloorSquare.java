package cluedo_game;

public class FloorSquare implements BoardSquare {
    //Instance Variables
    private Token playerOn;         // The player token currently on the square
    private final Token spawnPoint; // The player who spawns on this square
    private int[] location;         // Coordinates of this square on the board

    //
    //Constructor
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

  	public FloorSquare(){
        this.spawnPoint = null;
        // This is just a dummy square to check type
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
    public BoardSquare getSquareType() {
        return new FloorSquare();
    }

    @Override
    public boolean isPlayerOn() {
        return (this.playerOn != null);
    }

    //
    //Mutators
    //
  	public void setPlayerOn(Token playerOn) {this.playerOn = playerOn;}
    public void removePlayerOn() {this.playerOn = null;}

}
