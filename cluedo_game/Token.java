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
	private BoardSquare previous;
	private Room previousRoom = null;
	private Room inRoom;
	private ArrayList<Card> hand;
	private NoteCards playerDeckNotes;
	private int diceRoll = -1;
	private int initialObjNum;
	private int positionInRoom;
	private boolean inGame;

	// Variable to help with circularly linked list traversal
	private Token next;

	//Constructor
	public Token(int x, int y, String name, int playerNumber) {
		this.position[0] = x;
		this.position[1] = y;

		this.name = name;
		this.playerNumber = playerNumber;
		this.inRoom = null;
		this.inGame = true;
		// This is set to the spawn point when the board is created

		hand = new ArrayList<>();

		// TODO: Set up a note card for the player, right now with no information
		playerDeckNotes = new NoteCards();
	}

	public Token(int x, int y, String characterName, String userName, int playerNumber) {
		this.position[0] = x;
		this.position[1] = y;

		this.name = characterName;
		this.playerName = userName;
		this.playerNumber = playerNumber;
		this.inRoom = null;
		// This is set to the spawn point when the board is created

		hand = new ArrayList<>();

		// TODO: Set up a note card for the player, right now with no information
		playerDeckNotes = new NoteCards();
	}

	public Token(int x, int y, String characterName, String userName, int playerNumber, int diceRoll) {
		this.position[0] = x;
		this.position[1] = y;

		this.name = characterName;
		this.playerName = userName;
		this.playerNumber = playerNumber;
		System.out.println(playerNumber);
		this.inRoom = null;
		this.diceRoll = diceRoll;
		// This is set to the spawn point when the board is created

		hand = new ArrayList<>();

		// TODO: Set up a note card for the player, right now with no information
		playerDeckNotes = new NoteCards();
	}

	public Token(int x, int y, String characterName, String userName, int playerNumber, int diceRoll, int objNum) {
		this.position[0] = x;
		this.position[1] = y;

		this.name = characterName;
		this.playerName = userName;
		this.playerNumber = playerNumber;
		this.inRoom = null;
		this.diceRoll = diceRoll;
		this.initialObjNum = objNum;
		// This is set to the spawn point when the board is created

		hand = new ArrayList<>();

		// TODO: Set up a note card for the player, right now with no information
		playerDeckNotes = new NoteCards();
	}

	public void enterRoom(Room room){
		this.previous = squareOn;
		this.squareOn = null;

		this.inRoom = room;
		this.previousRoom = room;

		this.setLocationAsString("room");
	}
	public void exitRoom(int exitIndex){
		this.previous = null;
		this.setSquareOn(inRoom.getExits().get(exitIndex));
		this.inRoom.removeCapacity();
		this.inRoom = null;
	}
	public void exitRoomThroughPassage(){
		this.inRoom.removeCapacity();
		this.inRoom = inRoom.getSecretPassage();
		this.inRoom.addCapacity();
	}

	//
	// Accessors
	//
	public String getName() { return name;}
	public Room getPreviousRoom(){ return previousRoom; }
	public void setPreviousRoom(Room room){ previousRoom = room;}
	public String getPlayerName() {return playerName; }
	public int getPlayerNumber() {return playerNumber;}
	public int[] getPosition() {return position;}
	public int returnDiceNumber() { return this.diceRoll;}
	public int returnObjNum() {return this.initialObjNum; }
	public String getLocationAsString() {
		if(inRoom != null)
			return "room";
		return locationAsString;
	}
	public BoardSquare getSquareOn() {
		return squareOn;
	}

	public BoardSquare getPrevious(){
		return previous;
	}

	public Room getInRoom() { return inRoom; }
	public Token next() {
		return next;
	}
	public boolean getInGame(){
		return inGame;
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
		this.previous = this.squareOn;
		this.squareOn = squareOn;
		this.setPosition(squareOn.getPosition());
		this.setLocationAsString(squareOn.toString());
		squareOn.setPlayerOn(this);
	}
	public void setNext(Token next) {
		this.next = next;
	}
	public void setInGame(boolean b){
		this.inGame = b;
	}

	//
	// Player hand methods
	//
	public void addCardToHand(Card card){
		this.hand.add(card);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public ArrayList<String> getNotes(){
		ArrayList<String> notes = new ArrayList<>();
		for(int i=0; i<3; i++) {
			for (NoteCards.NoteCard n : playerDeckNotes.getNoteSheet().get(i))
				notes.add(String.valueOf(n.guessed) + "\t" + n.name);
		}
		return notes;
	}

	public NoteCards getPlayerDeckNotes() {
		return playerDeckNotes;
	}

	/**
	 * Update player's note card with public deck and cards in hand
	 * @param deck Game deck of cards
	 */
	public void populateNoteCards(Deck deck){
		// Add each card in player's hand to note card
		for(Card c : hand){
			playerDeckNotes.changeGuessStatus(c.reference, 'X');
		}
		// If there is a publicly viewable deck, add those cards to the notes
		if(deck.getDeck() != null){
			for(Card c : deck.getFullPublicDeck())
				getPlayerDeckNotes().changeGuessStatus(c.reference, 'A');
		}
	}

	/**
	 * NoteCards is just the note list for a player, with each Token/Room/Weapon
	 * 	represented along with a 'mark' for whether that card has been guessed
	 * 		Marks:
	 * 			(blank)	-	Unknown
	 * 			'A'		-	Card is in public deck
	 * 			'X'		-	Card is in this player's hand
	 * 			'✔'		-	Card was found in another player's hand
	 * 			'?'		-	Card was guessed but not discovered in another hand
	 */
	static class NoteCards extends Deck {
		ArrayList<ArrayList<NoteCard>> noteSheet = new ArrayList<>();
		/*
			NoteCard is the same as card with one extra variable -
				The char that denotes whether the card has been guessed and, if
					so, what the result was
		 */
		private class NoteCard extends Card {
			char guessed;
			public NoteCard(String name, int ref0, int ref1, String type){
				super(name, ref0, ref1, type);
				this.guessed = ' ';
			}
			public void changeStatus(char mark){
				this.guessed = mark;
			}
		}
		/*
			Constructor of NoteCards is the same as for Deck
				This isn't efficient but makes for simple referencing
				Since a card's 'reference' is set at declaration and never
					changed, we can use it to move seamlessly between public deck,
					hands, murder envelope and noteCards.
		 */
		public NoteCards(){
			noteSheet.add(new ArrayList<>());
			noteSheet.get(0).add(new NoteCard("White", 0, 0, "token"));
			noteSheet.get(0).add(new NoteCard("Green", 0, 1, "token"));
			noteSheet.get(0).add(new NoteCard("Mustard", 0, 2, "token"));
			noteSheet.get(0).add(new NoteCard("Scarlet", 0, 3, "token"));
			noteSheet.get(0).add(new NoteCard("Peacock", 0, 4, "token"));
			noteSheet.get(0).add(new NoteCard("Plum", 0, 5, "token"));

			noteSheet.add(new ArrayList<>());
			noteSheet.get(1).add(new NoteCard("Kitchen", 1, 0, "room"));
			noteSheet.get(1).add(new NoteCard("Ball Room", 1, 1, "room"));
			noteSheet.get(1).add(new NoteCard("Conservatory", 1, 2, "room"));
			noteSheet.get(1).add(new NoteCard("Dining Room", 1, 3, "room"));
			noteSheet.get(1).add(new NoteCard("Billiard Room", 1, 4, "room"));
			noteSheet.get(1).add(new NoteCard("Library", 1, 5, "room"));
			noteSheet.get(1).add(new NoteCard("Lounge", 1, 6, "room"));
			noteSheet.get(1).add(new NoteCard("Hall", 1, 7, "room"));
			noteSheet.get(1).add(new NoteCard("Study", 1, 8, "room"));

			noteSheet.add(new ArrayList<>());
			noteSheet.get(2).add(new NoteCard("Candlestick", 2, 0, "weapon"));
			noteSheet.get(2).add(new NoteCard("Dagger", 2, 1, "weapon"));
			noteSheet.get(2).add(new NoteCard("Gun", 2, 2, "weapon"));
			noteSheet.get(2).add(new NoteCard("Pipe", 2, 3, "weapon"));
			noteSheet.get(2).add(new NoteCard("Rope", 2, 4, "weapon"));
			noteSheet.get(2).add(new NoteCard("Wrench", 2, 5, "weapon"));
		}

		/*
			When guess, call this method to update the appropriate noteCard
		 */
		protected void changeGuessStatus(int[] reference, char mark){
			noteSheet.get(reference[0]).get(reference[1]).changeStatus(mark);
		}

		protected ArrayList<ArrayList<NoteCard>> getNoteSheet() {
			return noteSheet;
		}
	}
}
