package cluedo_game;

import java.util.ArrayList;

public class Room implements BoardSquare {
	private String name;
	private Weapon weaponInRoom = null;
	private Room secretPassage;
	private boolean isMurderRoom = false; //determines if this is the room which the murder took place

	ArrayList<Token> playersInRoom = new ArrayList<>();
	ArrayList<EntrySquare> doorway;

	//Consructors
	/**
	 * Other than the cellar, rooms with no secretPassage all have multiple entries.
	 * As a result, this constructor handles those rooms by merely taking an entire ArrayList
	 * as an argument for its doorway variable. The exception is cellar, and we will just send
	 * 'null' for the doorway argument in that case.
	 * @param name String representation of the name of the room
	 * @param doorway The entryways to the room
	 */
	public Room(String name, ArrayList<EntrySquare> doorway) {
		this.name = name;
		this.doorway = doorway;
		this.secretPassage = null;
	}

	/**
	 * Since this method handles rooms with only one entry (besides the secret passage), we
	 * just construct an ArrayList for doorway within the constructor then add the single
	 * EntrySquare argument for the doorway.
	 * @param name String representation of the name of the room
	 * @param secretPassage A pointer to the room to which this room's passage leads
	 */
	public Room(String name, Room secretPassage) {
		this.name = name;
		this.doorway = new ArrayList<>();
		this.secretPassage = secretPassage;
	}

	//
	//Accessors
	//
	public String getName() {return name;}
	public Weapon getWeaponInRoom() {return weaponInRoom;}
	public Room getsecretPassage() {return secretPassage;}
	public ArrayList<Token> getPlayersInRoom() {return playersInRoom;}
	public ArrayList<EntrySquare> getDoorway() {return doorway;}
	@Override
	public int[] getLocation() {return position;}

	//
	//Mutators
	//
	public void setName(String name) {this.name = name;}
	public void setWeaponInRoom(Weapon weaponInRoom) {this.weaponInRoom = weaponInRoom;}
	public void setsecretPassage(Room secretPassage) {this.secretPassage = secretPassage;}
	public void setPlayersInRoom(ArrayList<Token> playersInRoom) {this.playersInRoom = playersInRoom;}
	public void setDoorway(ArrayList<EntrySquare> doorway) {this.doorway = doorway;}
}
