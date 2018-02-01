package cluedo_game;

import java.util.ArrayList;

public class RoomSquare implements BoardSquare {
	private String name;
	private Weapon weaponInRoom = null;
	private RoomSquare seacretPassage = null;
	private boolean isMurderRoom = false; //determines if this is the room which the murder took place
	private int[] position = new int[2];

	ArrayList<Token> playersInRoom = new ArrayList();
	ArrayList<EntrySquare> doorway = new ArrayList();
	
	public RoomSquare(String name, ArrayList<EntrySquare> doorway) {
		this.name = name;
		this.doorway = doorway;
	}
	
	public RoomSquare(String name,int x, int y, RoomSquare seacretPassage, ArrayList<EntrySquare> doorway) {
		this.position[0] = x;
		this.position[1] = y;
		this.name = name;
		this.seacretPassage = seacretPassage;
		this.doorway = doorway;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Weapon getWeaponInRoom() {
		return weaponInRoom;
	}


	public void setWeaponInRoom(Weapon weaponInRoom) {
		this.weaponInRoom = weaponInRoom;
	}


	public RoomSquare getSeacretPassage() {
		return seacretPassage;
	}


	public void setSeacretPassage(RoomSquare seacretPassage) {
		this.seacretPassage = seacretPassage;
	}


	public ArrayList<Token> getPlayersInRoom() {
		return playersInRoom;
	}


	public void setPlayersInRoom(ArrayList<Token> playersInRoom) {
		this.playersInRoom = playersInRoom;
	}


	public ArrayList<EntrySquare> getDoorway() {
		return doorway;
	}


	public void setDoorway(ArrayList<EntrySquare> doorway) {
		this.doorway = doorway;
	}


	@Override
	public int[] getLocation() {
		return position;
	}
}
