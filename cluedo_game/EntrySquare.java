package cluedo_game;

public class EntrySquare implements BoardSquare{
	private int[] position = new int[2];
	private Room roomAssigned;
	private int referenceNumber;
	private boolean playerOn;

	//
	//Constructors
	//
	EntrySquare(int x, int y, Room roomAssigned, int refNum){
		position[0] = x;
		position[1] = y;
		this.roomAssigned = roomAssigned;
		this.referenceNumber = refNum;
	}

	//
	//Accessors
	//
	public Room getRoomName() {return roomAssigned;}
	public int getRefNum() {return this.referenceNumber;}
	public boolean isPlayerOn() {return playerOn;}
	@Override
	public int[] getLocation() {return position;}

	//
	//Mutators
	//
	public void changePlayerOn(boolean x) {playerOn = x;}

}
