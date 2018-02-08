package cluedo_game;

import java.util.Map;

public class EntrySquare implements BoardSquare{
	private final int[] location = new int[2];
	private final Room roomAssigned;
	private final int referenceNumber;
	private boolean playerOnCheck;
	private Token playerOn;
	// Navigational pointers
	private BoardSquare above;

	public void removePlayerOn() {

	}

	private BoardSquare below;
	private BoardSquare toLeft;
	private BoardSquare toRight;

	//
	//Constructors
	//
	public EntrySquare(int x, int y, Room roomAssigned, int refNum){
		location[0] = x;
		location[1] = y;
		this.roomAssigned = roomAssigned;
		this.referenceNumber = refNum;
		this.playerOnCheck = false;
	}
	public EntrySquare(){
		// This is a dummy EntrySquare for type check
		this.roomAssigned = null;
		this.referenceNumber = -1;
	}

	//
	//Accessors
	//
	public Room getRoomName() {return roomAssigned;}
	public int getRefNum() {return this.referenceNumber;}
	public boolean isPlayerOn() {return playerOnCheck;}
	public Token getPlayerOn(){ return this.playerOn; }
	@Override
	public int[] getLocation() {return location;}

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
	public BoardSquare getSquareType() {
		return new EntrySquare();
	}
	
	

}
