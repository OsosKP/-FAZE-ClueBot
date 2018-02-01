package cluedo_game;

public class Weapon {
	private String name;
	private RoomSquare room; //because the weapon is bound to a room, we can just store a pointer
	private boolean isMurderWeapon = false;

	//Constructor
	public Weapon(String name, boolean isMurderWeapon, RoomSquare roomPlacement) {
		this.name = name;
		this.isMurderWeapon = isMurderWeapon;
		this.room = roomPlacement;
	}

	//
	//Accessors
	//
	public String getName(){return name;}
	public RoomSquare getRoom(){return room;}
	public boolean isMurderWeapon(){return isMurderWeapon;}

	//
	//Mutators
	//
	public void setName(String name){this.name = name;}
	public void setMurderWeaopn(boolean isMurderWeapon){ this.isMurderWeapon = isMurderWeapon;}

}
