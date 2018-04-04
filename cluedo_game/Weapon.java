// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

public class Weapon {
	private String name;
	private Room room; //because the weapon is bound to a room, we can just store a pointer

	//Constructor
	public Weapon(String name, boolean isMurderWeapon, Room roomPlacement) {
		this.name = name;
		this.room = roomPlacement;
	}

	//
	//Accessors
	//
	public String getName(){return name;}
	public Room getRoom(){return room;}

	//
	//Mutators
	//
	public void setName(String name){this.name = name;}

}
