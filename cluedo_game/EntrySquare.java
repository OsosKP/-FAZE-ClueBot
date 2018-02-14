// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

public class EntrySquare implements BoardSquare{
	private final int[] position = new int[2];
	private final Room roomAssigned;
	private final int referenceNumber;
	private boolean playerOnCheck;
	private Token playerOn;
	// Navigational pointers
	private BoardSquare above;
	private BoardSquare below;
	private BoardSquare toLeft;
	private BoardSquare toRight;

	public Token removePlayerOn() {
		Token removedPlayer = this.playerOn;
		this.playerOn = null;
		return removedPlayer;
	}

	//
	//Constructors
	//
	public EntrySquare(int x, int y, Room roomAssigned, int refNum){
		position[0] = x;
		position[1] = y;
		this.roomAssigned = roomAssigned;
		this.referenceNumber = refNum;
		this.playerOnCheck = false;
	}

	//
	//Accessors
	//
	public Room getRoomName() {return roomAssigned;}
	public int getRefNum() {return this.referenceNumber;}
	public boolean isPlayerOn() {return playerOnCheck;}
	public Token getPlayerOn(){ return this.playerOn; }
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
	public void changePlayerOnStatus(boolean x) {playerOnCheck = x;}
	public void setPlayerOn(Token player) { this.playerOn = player; }
	/**
	 * getSquareType
	 * This method returns an object of type EntrySquare
	 * This is used to check that the object containing this method is a EntrySquare
	 * @return temporary object of type EntrySquare
	 */
	@Override
	public EntrySquare getSquareType() {
		return this;
	}
	@Override
	public String toString(){ return "entry"; }

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
