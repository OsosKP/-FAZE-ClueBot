package cluedo_game;

import java.util.ArrayList;

public class RoomSquare implements BoardSquare {
	private String name;
	private Weapon weaponInRoom = null;
	private RoomSquare secretPassage = null;
	private boolean isMurderRoom = false; //determines if this is the room which the murder took place
	private int[] position = new int[2];

	ArrayList<Token> playersInRoom = new ArrayList();
	ArrayList<EntrySquare> doorway = new ArrayList();

	//Consructors
	public RoomSquare(String name, ArrayList<EntrySquare> doorway) {
		this.name = name;
		this.doorway = doorway;
	}

	public RoomSquare(String name,int x, int y, RoomSquare secretPassage, ArrayList<EntrySquare> doorway) {
		this.position[0] = x;
		this.position[1] = y;
		this.name = name;
		this.secretPassage = secretPassage;
		this.doorway = doorway;
	}

	//
	//Accessors
	//
	public String getName() {return name;}
	public Weapon getWeaponInRoom() {return weaponInRoom;}
	public RoomSquare getsecretPassage() {return secretPassage;}
	public ArrayList<Token> getPlayersInRoom() {return playersInRoom;}
	public ArrayList<EntrySquare> getDoorway() {return doorway;}
	@Override
	public int[] getLocation() {return position;}

	//
	//Mutators
	//
	public void setName(String name) {this.name = name;}
	public void setWeaponInRoom(Weapon weaponInRoom) {this.weaponInRoom = weaponInRoom;}
	public void setsecretPassage(RoomSquare secretPassage) {this.secretPassage = secretPassage;}
	public void setPlayersInRoom(ArrayList<Token> playersInRoom) {this.playersInRoom = playersInRoom;}
	public void setDoorway(ArrayList<EntrySquare> doorway) {this.doorway = doorway;}
}
