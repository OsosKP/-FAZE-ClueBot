// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

public class Token {
	private final String name;
	private final int playerNumber;
	private int[] position = new int[2]; //holding the position of the player
	private String locationAsString;
	private BoardSquare squareOn;
	private Room inRoom;

	// Variable to help with circularly linked list traversal
	private Token next;

	//Constructor
	public Token(int x, int y, String name, int playerNumber) {
		this.position[0] = x;
		this.position[1] = y;

		this.name = name;
		this.playerNumber = playerNumber;
		this.inRoom = null;
		// This is set to the spawn point when the board is created
	}

	public void enterRoom(Room room){
		this.squareOn = null;

		/*
		TODO: WHY does this set the room's exit square's position to ( -1, -1 )!?!???!?!?!?!
		 */
//		this.position[0] = -1;
//		this.position[1] = -1;

		this.inRoom = room;

		this.setLocationAsString("room");
	}
	public void exitRoom(int exitIndex){
		this.setSquareOn(inRoom.getExits().get(exitIndex));
		this.inRoom = null;
	}
	public void exitRoomThroughPassage(){
		this.inRoom = inRoom.getSecretPassage();
	}

	//
	//Accessors
	//
	public String getName() { return name;}
	public int getPlayerNumber() {return playerNumber;}
	public int[] getPosition() {return position;}
	public String getLocationAsString() {
		if(inRoom != null)
			return "room";
		return locationAsString;
	}
	public BoardSquare getSquareOn() {
		return squareOn;
	}
	public Room getInRoom() { return inRoom; }
	public Token next() {
		return next;
	}

	/**
	 * If player's location isn't known, this will either return the room name or the square coordinates.
	 * @return either room name or square
	 */
	public String safeGetLocation(){
		if (this.inRoom != null)
			return this.inRoom.getName();
		else
			return this.getSquareOn().getPositionAsString();
	}

  	//
	//Mutators
	//
	public void setPosition(int[] position) {this.position = position;}
	public void setLocationAsString(String location) {this.locationAsString = location; }
	public void setSquareOn(BoardSquare squareOn) {
		this.squareOn = squareOn;
		this.setPosition(squareOn.getPosition());
		this.setLocationAsString(squareOn.toString());
		squareOn.setPlayerOn(this);
	}
	public void setNext(Token next) {
		this.next = next;
	}
}
