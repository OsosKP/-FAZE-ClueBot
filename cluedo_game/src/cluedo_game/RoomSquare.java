package cluedo_game;

import java.util.ArrayList;

public class RoomSquare implements BoardSquare {
	public String name;
	public Weapon weaponInRoom = null;
	public RoomSquare seacretPassage = null;
	
	ArrayList<Player> playersInRoom = new ArrayList();
	ArrayList<EntrySquare> doorway = new ArrayList();
	
	

	public RoomSquare(String name, ArrayList<EntrySquare> doorway) {
		this.name = name;
		this.doorway = doorway;
	}
	
	public RoomSquare(String name, RoomSquare seacretPassage, ArrayList<EntrySquare> doorway) {
		super();
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


	public ArrayList<Player> getPlayersInRoom() {
		return playersInRoom;
	}


	public void setPlayersInRoom(ArrayList<Player> playersInRoom) {
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
		// TODO Auto-generated method stub
		return null;
	}
}
