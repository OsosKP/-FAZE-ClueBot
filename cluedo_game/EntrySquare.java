package cluedo_game;

import java.util.Map;

public class EntrySquare implements BoardSquare{
	private final int[] position = new int[2];
	private final Room roomAssigned;
	private final int referenceNumber;
	private boolean playerOn;

	//
	//Constructors
	//
	public EntrySquare(int x, int y, Room roomAssigned, int refNum){
		position[0] = x;
		position[1] = y;
		this.roomAssigned = roomAssigned;
		this.referenceNumber = refNum;
		this.playerOn = false;
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
	public boolean isPlayerOn() {return playerOn;}
	@Override
	public int[] getLocation() {return position;}
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

	//
	//Mutators
	//
	public void changePlayerOn(boolean x) {playerOn = x;}
	
	

}
