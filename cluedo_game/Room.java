 // Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

import java.util.ArrayList;

public class Room {
	private String name;
	private Weapon weaponInRoom = null;
	private Room secretPassage;
	private ArrayList<int[]> playerFloors = null;
	private final ArrayList<FloorSquare> exits;
	private final ArrayList<EntrySquare> entrances;
	private int capacity = 0;

	ArrayList<Token> playersInRoom = new ArrayList<>();

	/**
	 * Other than the cellar, rooms with no secretPassage all have multiple entries.
	 * As a result, this constructor handles those rooms by merely taking an entire ArrayList
	 * as an argument for its doorway variable. The exception is cellar, and we will just send
	 * 'null' for the doorway argument in that case.
	 * @param name String representation of the name of the room
	 * @param entrances The EntrySquares that lead to this room
	 * @param exits The FloorSquares to which this room exits
	 */
	public Room(String name, ArrayList<EntrySquare> entrances, ArrayList<FloorSquare> exits, ArrayList<int[]> playercoordinates) {
		this.name = name;
//		ArrayList<EntrySquare> in = new ArrayList<>(entrances);
		this.entrances = new ArrayList<>(entrances);
		this.exits = new ArrayList<>(exits);
		this.secretPassage = null;
		this.playerFloors = playercoordinates;
	}

	public Room(String name, EntrySquare entrance, FloorSquare exit) {
		this.name = name;
		this.entrances = new ArrayList<>();
		this.entrances.add(entrance);
		this.exits = new ArrayList<>();
		this.exits.add(exit);
		this.secretPassage = null;
	}

	/**
	 * Since this method handles rooms with only one entry (besides the secret passage), we
	 * just construct an ArrayList for doorway within the constructor then add the single
	 * EntrySquare argument for the doorway.
	 * @param name String representation of the name of the room
	 * @param entrance The EntrySquare that leads to this room
	 * @param exit The FloorSquare to which this room exits
	 */
	public Room(String name, EntrySquare entrance, FloorSquare exit, ArrayList<int[]> playercoordinates) {
		this.name = name;
		this.entrances = new ArrayList<>();
		this.entrances.add(entrance);
		this.exits = new ArrayList<>();
		this.exits.add(exit);
		this.secretPassage = null;
		this.playerFloors = playercoordinates;
	}

	//
	//Accessors
	//
	public String getName() {return name;}
	public int getCapacity() {return capacity;}
	public Weapon getWeaponInRoom() {return weaponInRoom;}
	public Room getSecretPassage() {return secretPassage;}
	public ArrayList<Token> getPlayersInRoom() {return playersInRoom;}
	public ArrayList<EntrySquare> getEntrances() {return entrances;}
	public ArrayList<FloorSquare> getExits() {
		return exits;
	}
	public ArrayList<int[]> getPlayerFloors() { return playerFloors; }

	//
	//Mutators
	//
	public void setName(String name) {this.name = name;}
	public void setCapacity(int x) { capacity=x; }
	public void addCapacity() { capacity++; }
	public void removeCapacity() { capacity--; }
	public void setWeaponInRoom(Weapon weaponInRoom) {this.weaponInRoom = weaponInRoom;}
	public void setSecretPassage(Room secretPassage) {this.secretPassage = secretPassage;}
	public void setPlayersInRoom(ArrayList<Token> playersInRoom) {this.playersInRoom = playersInRoom;}

	public String playerListInRoom(){//returns printable string of players in room
		StringBuilder string = new StringBuilder();
		for (int i=0;i<playersInRoom.size();i++){
			string.append(playersInRoom.get(0).getName());
			string.append(" ");
		}
		return string.toString();
	}

	public void addPlayerToRoom(Token p){
		this.playersInRoom.add(p);
	}
	public void removePlayerFromRoom(Token p){
		if (playersInRoom.contains(p)){
			this.playersInRoom.remove(p);
		}
		else
			System.err.println("Player was not located in this room. Error?");
	}


}
