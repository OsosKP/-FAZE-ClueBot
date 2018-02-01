package cluedo_game;

public class EntrySquare implements BoardSquare{
	private int[] position = new int[2];
	private RoomSquare roomAssigned;
	private RoomSquare roomDestination;
	private int referenceNumber;
	private boolean playerOn;
	
	EntrySquare(int x, int y, RoomSquare roomAssigned, RoomSquare roomDestination, int refNum){
		position[0] = x;
		position[1] = y;
		this.roomAssigned = roomAssigned;
		this.roomDestination = roomDestination;
		this.referenceNumber = refNum;
	}
	
	public void changePlayerOn(boolean x) {
		playerOn = x;
	}

	public RoomSquare getRoomName() {
		return roomAssigned;
	}
	
	public int getRefNum() {
		return this.referenceNumber;
	}
	
	public RoomSquare getRoomDestination() {
		return roomDestination;
	}
	
	public boolean isPlayerOn() {
		return playerOn;
	}

	@Override
	public int[] getLocation() {
		return position;
	}
}
