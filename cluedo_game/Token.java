package cluedo_game;

public class Token {
	private String name;
	private int playerNumber;
	private int[] position = new int[2]; //holding the position of the player
	private boolean isMurderer = false;

	//Constructor
	public Token(int x, int y,String name, int playerNumber, boolean isMurderer) {
		this.position[0] = x;
		this.position[1] = y;
		this.name = name;
		this.playerNumber = playerNumber;
		this.isMurderer = isMurderer;
	}

	//
	//Accessors
	//
	public String getName() { return name;}
	public int getPlayerNumber() {return playerNumber;}
	public int[] getPosition() {return position;}
	public boolean isMurderer() {return isMurderer;}
	public int[] getLocation() { return position;}

	public BoardSquare getSquare(Board board) {
		return board.getSquare(this.position[0], this.position[1]);
	}

	//
	//Mutators
	//
	public void setName(String name) {this.name = name;}
	public void setPlayerNumber(int playerNumber) {this.playerNumber = playerNumber;}
	public void setPosition(int[] position) {this.position = position;}
	public void setMurderer(boolean isMurderer) {this.isMurderer = isMurderer;}

}
