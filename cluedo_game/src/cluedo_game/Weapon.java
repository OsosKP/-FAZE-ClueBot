package cluedo_game;

public class Weapon {
	private String name;
	private RoomSquare room; //because the weapon is bound to a room, we can just store a pointer
	private boolean isMurderWeaopn = false;
	
	public Weapon(String name, boolean isMurderWeaopn, RoomSquare roomPlacement) {
		this.name = name;
		this.isMurderWeaopn = isMurderWeaopn;
		this.room = roomPlacement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public RoomSquare getRoom() {
		return room;
	}
	
	public boolean isMurderWeaopn() {
		return isMurderWeaopn;
	}

	public void setMurderWeaopn(boolean isMurderWeaopn) {
		this.isMurderWeaopn = isMurderWeaopn;
	}
	
	
}
