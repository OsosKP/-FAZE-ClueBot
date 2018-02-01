package cluedo_game;

public class Token implements BoardSquare{
	private String name;
	private int playerNumber;
	private int[] position = new int[2]; //holding the position of the player
	private boolean isMurderer = false; 
	
	
	public Token(int x, int y,String name, int playerNumber, boolean isMurderer) {
		this.position[0] = x;
		this.position[1] = y;
		this.name = name;
		this.playerNumber = playerNumber;
		this.isMurderer = isMurderer;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getPlayerNumber() {
		return playerNumber;
	}


	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}


	public int[] getPosition() {
		return position;
	}


	public void setPosition(int[] position) {
		this.position = position;
	}


	public boolean isMurderer() {
		return isMurderer;
	}


	public void setMurderer(boolean isMurderer) {
		this.isMurderer = isMurderer;
	}


	@Override
	public int[] getLocation() {
		// TODO Auto-generated method stub
		return position;
	}
}
