// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

import java.util.ArrayList;

public class Token {
	private final String name;
	private String playerName;
	private final int playerNumber;
	private int[] position = new int[2]; //holding the position of the player
	private String locationAsString;
	private BoardSquare squareOn;
	private Room inRoom;
	private ArrayList<ArrayList<String>> hand;
	private ArrayList<ArrayList<String>> playerDeckNotes;

	// Variable to help with circularly linked list traversal
	private Token next;

	//Constructor
	public Token(int x, int y, String name, int playerNumber) {
		this.position[0] = x;
		this.position[1] = y;

		this.name = name;
		this.playerNumber = playerNumber;
		this.inRoom = null;
		// This is set to the spawn point when the board is created

		hand = new ArrayList<>();

		// Set up a note card for the player, right now with no information
		this.populatePlayerDeckNotes();
	}

	public void enterRoom(Room room){
		this.squareOn = null;

		this.inRoom = room;

		this.setLocationAsString("room");
	}
	public void exitRoom(int exitIndex){
		this.setSquareOn(inRoom.getExits().get(exitIndex));
		this.inRoom = null;
	}
	public void exitRoomThroughPassage(){
		this.inRoom = inRoom.getSecretPassage();
	}

	//
	// Accessors
	//
	public String getName() { return name;}
	public String getPlayerName() {return playerName; }
	public int getPlayerNumber() {return playerNumber;}
	public int[] getPosition() {return position;}
	public String getLocationAsString() {
		if(inRoom != null)
			return "room";
		return locationAsString;
	}
	public BoardSquare getSquareOn() {
		return squareOn;
	}
	public Room getInRoom() { return inRoom; }
	public Token next() {
		return next;
	}

	/**
	 * If player's location isn't known, this will either return the room name or the square coordinates.
	 * @return either room name or square
	 */
	public String safeGetLocation(){
		if (this.inRoom != null)
			return this.inRoom.getName();
		else
			return this.getSquareOn().getPositionAsString();
	}

  	//
	// Mutators
	//
	public void setPlayerName(String name) {this.playerName = name; }
	public void setPosition(int[] position) {this.position = position;}
	public void setLocationAsString(String location) {this.locationAsString = location; }
	public void setSquareOn(BoardSquare squareOn) {
		this.squareOn = squareOn;
		this.setPosition(squareOn.getPosition());
		this.setLocationAsString(squareOn.toString());
		squareOn.setPlayerOn(this);
	}
	public void setNext(Token next) {
		this.next = next;
	}

	//
	// Player hand methods
	//
	public void populateHand(ArrayList<String> cards, int[][] indices){
		int i = 0;
		for(String st : cards) {
			hand.add(new ArrayList<>());
				hand.get(i).add(st);
				hand.get(i).add(Integer.toString(indices[i][0]));
				hand.get(i).add(Integer.toString(indices[i][1]));
			i++;
		}
	}

	//
	// Player Note Card methods
	//
	// First declaration of the deck
	public void populatePlayerDeckNotes(){
		playerDeckNotes = new ArrayList<>();
		for(int i=0; i<3; i++) {
			playerDeckNotes.add(new ArrayList<>());
				for(int j=0; j<9; j++){
					// Don't do the entire loop if we're on players or weapons
					if ((i == 0 || i == 2) && j == 6)
						break;
					playerDeckNotes.get(i).add(" ");
				}
		}
	}

	public void updateNotesFromPublicDeck(Deck deck){

	}

	/**
	 * updateNotesFromGuess
	 * 	When a player finds a card in another player's deck while guessing, that card's index is sent here
	 * 		to be crossed off on the note card
	 * @param index Index of item that was found through guessing
	 */
	public void updateNotesFromGuess(int[] index){
		playerDeckNotes.get(index[0]).set(index[1], "X");
	}
}
