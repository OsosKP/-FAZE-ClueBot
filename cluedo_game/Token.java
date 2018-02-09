package cluedo_game;

public class Token {
	private String name;
	private int playerNumber;
	private int[] position = new int[2]; //holding the position of the player
	private boolean isMurderer = false;
	private BoardSquare squareOn;
	private Room inRoom;
	private String locationAsString;

	//Constructor
	public Token(int x, int y,String name, int playerNumber) {
		this.position[0] = x;
		this.position[1] = y;

		this.name = name;
		this.playerNumber = playerNumber;
		this.inRoom = null;
		// This is set to the spawn point when the board is created
		this.squareOn = null;
	}

	// When entering a room, player disappears from the board
	// This might be updated to the location where we want their token to appear
	public void enterRoom(Room room){
		this.position[0] = -1;
		this.position[1] = -1;
		this.squareOn = null;
		this.inRoom = room;
	}
	public void exitRoom(BoardSquare exitToSquare){
		int pos[] = exitToSquare.getLocation();
		this.position[0] = pos[0];
		this.position[1] = pos[1];
		this.squareOn = exitToSquare;
		this.inRoom = null;
	}

	//
	//Accessors
	//
	public String getName() { return name;}
	public int getPlayerNumber() {return playerNumber;}
	public int[] getPosition() {return position;}
	public boolean isMurderer() {return isMurderer;}

	public int[] getLocation() { return position;}

	public BoardSquare getSquareOn() { return squareOn; }
	public Room getInRoom() { return inRoom; }



  	//
	//Mutators
	//
	public void setName(String name) {this.name = name;}
	public void setPlayerNumber(int playerNumber) {this.playerNumber = playerNumber;}
	public void setPosition(int[] position) {this.position = position;}
	public void setMurderer(boolean isMurderer) {this.isMurderer = isMurderer;}
	public void setSquareOn(BoardSquare squareOn) { this.squareOn = squareOn; }
	public void setInRoom(Room room) { this.inRoom = room; }
}
