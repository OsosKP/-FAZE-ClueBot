// [FAZE]ClueBot

package bots;

import gameengine.*;
import gameengine.Map;

import java.util.*;
import java.util.jar.Attributes.Name;

public class FazeClueBot implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the board or the player objects
    // It may only inspect the state of the board and the player objects

    private Player player;
    private PlayersInfo playersInfo;
    private Map map;
    private Dice dice;
    private Log log;
    private Deck deck;
    private GuessingLogic guessing;
    private int commandnumber; //int to keep track of which command. 
//		1: move
//		2: passage
//		3: question 
//		4: accuse 
//		5: done

    public FazeClueBot (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
        // Store locations to all doors on the board
        storeAllDoors();
        // Populate NoteCards
        setNoteCards();
        // Set location on board
        setCurrentPosition(player.getToken().getPosition());

        /* Creating the game cards */
        guessing = new GuessingLogic();
    }
    
//    
//    Note: All inputs are sanitized with .trim() and .toLowerCase()
//
    
    public String getName() {
        return "FazeClueBot"; // must match the class name
    }

    public String getCommand() {
    	takeTurn();
    	switch (commandnumber) {
		case 1:
			return "move";
		case 2:
			return "passage";
		case 3:
			return "question";
		case 4:
			return "accuse";
		case 5:
			return "done";
		default:
			break;
		}
        // Possible inputs: quit|done|roll|passage|notes|cheat|question|log|accuse|help
        return takeTurn();
    }

    public String getMove() {
        // Add your code here
        String move;
        if (currentRoute == null)
            beginPing();
        move = findNextCommandMovingToRoom();

        return move;
    }

    public String getSuspect() {
        // Add your code here
        // TODO: George - just put yourNoteCardForSuspects.get(0). Do for all 3
        // Input must return true for Names.isSuspect(String input)
        // Possible inputs: u|d|l|r
        return getSuspect();
    }

    public String getWeapon() {
    	// Input must return true for Names.isWeapon(String input)
        return Names.WEAPON_NAMES[0];
    }

    public String getRoom() {
    	// Input must return true for Names.isRoomCard(String input)
        return Names.ROOM_NAMES[0];
    }

    public String getDoor() {
        // Add your code here
        return "0";
    }
    
    /* When someone is asking a question */
    public String getCard(Cards matchingCards) {
        // Add your code here
        // TODO: This might be placeholder, but the format is correct - Kelsey
        Query ourQuery = new Query(getSuspect(), getWeapon(), getRoom());
        // Possible input: 1|2|3|4
        // Input needs to return true: (at least one card).hasName(String input) in matchingCards
        return matchingCards.get(ourQuery).toString();
    }
    
    /*
     * Function call that happens when we ask a question and get an answer
     * TODO: Josh look at this and see if I am going in the right direction
     */
    public void notifyResponse(Log response) {
    	guessing.questionAnswered(response);
    }

    /*
        Our Code
     */

    /*
        New variables and methods
     */

    // Map coords: 24 x 23
    private List<Integer[]> mainTrack = new ArrayList<>();
    private List<Integer[]> currentRoute = new ArrayList<>();
    private Room currentRoomDestination;
    private Integer[] currentPosition = new Integer[2];
    private List<Integer[]> doors = new ArrayList<>();
    private String[] doorsReference = new String[18];
    
    /* ArrayLists that will represent the different cards in the game that the bot will guess */ 
    private List<NoteCard> playerCards = new ArrayList<>();
    private List<NoteCard> weaponCards = new ArrayList<>();
    private List<NoteCard> roomCards = new ArrayList<>();
    
    private List<NoteCard> playerHandCards = new ArrayList<>();
    private List<NoteCard> weaponHandCards = new ArrayList<>();
    private List<NoteCard> roomHandCards = new ArrayList<>();
    
    /* ArrayList that represents the removedCards */
    private List<NoteCard> playerRemovedCards = new ArrayList<>();
    private List<NoteCard> weaponRemovedCards = new ArrayList<>();
    private List<NoteCard> roomRemovedCards = new ArrayList<>();
    
    private boolean movingToRoom = false;
    private boolean inRoom = false;
    private boolean guessedInRoom = false;
    private boolean timeToAccuse = false;
    private int currentRouteProgressIndex = 0;

    /*
        Movement
     */
    private Integer[] getCurrentPosition() {
        return currentPosition;
    }
    private int getCurrentRow() {
        return currentPosition[0];
    }
    private int getCurrenColumn() {
        return currentPosition[1];
    }
    private void setCurrentPosition(Coordinates coords) {
        this.currentPosition[0] = coords.getRow();
        this.currentPosition[1] = coords.getCol();
    }

    private String takeTurn() {
        // Update Log
        //TODO: Log

        /* Check if we have an accusation
                If so, we will call shouldIVisitRoom.
                This method checks timeToAccuse and returns the boolean:
                    (we need to go to the cellar) AND (we are going to the cellar)
                If timeToAccuse is false, shouldIVisitRoom just looks for the room
                    it is given and decides whether or not we know it's in the
                    murder envelope. If we don't know, we'll visit it.
         */
        timeToAccuse = guessing.getAccuseState();

        // If not, see if we're en route to a room
        if (movingToRoom) {
            // Make sure we still want to go to this room
            if(shouldIVisitRoom(currentRoomDestination)) {
                // If so, continue
                return findNextCommandMovingToRoom();
            }
            else {
                movingToRoom = false;
                // If not, re-ping
                beginPing();
                // And start moving
                return findNextCommandMovingToRoom();
            }
        }
        // If we begin our turn in a room
        if (inRoom) {
            // Check if the room has a passage to a room we want to visit
            if (map.getRoom(getRoom()).hasPassage() &&
                    shouldIVisitRoom(map.getRoom(getRoom()).getPassageDestination())
                    // Also make sure we haven't guess in the current room
                    // This should have the added benefit of not letting us try to
                        // take a passage after having already moved. I think?
                    && guessedInRoom)
                return "passage";
            // Check if we've already guessed in this room. If we have...
            if (guessedInRoom) {
                // Rolling dice should have us exit the room
                // We should ensure the correct exit is picked prior to rolling,
                    // because the rollDice() method checks getDoor()
                // I've just got it returning 0 for now so we don't crash
                return "roll";
            }
            // Otherwise, make a guess
            else {
            	commandnumber=3;//This tells the game that we want to guess
            	//TODO: George Insert guessing function call code
            }
        }
        /*
            If we have gotten to this point without returning:
                We're not in a room (if we were, we just exited) and not on our way
                to one. So we need to ping to find the closest room we want and redo all this.
         */
        beginPing();
        return findNextCommandMovingToRoom();
    }

    /*
        When we have a path to follow to a room, this method gets the next step to
            do and sends the appropriate command
     */
    private String findNextCommandMovingToRoom() {
        currentRouteProgressIndex++;

        if (currentRouteProgressIndex >= currentRoute.size()) {
            beginPing();
            currentRouteProgressIndex = 0;
            return "";
        }

       	commandnumber=1;//Command is move
        /*
            The next square in our route will be different in either the row
                or column index. Find which one it is (it should only be one) and
                set our next move command accordingly.
         */
        int rowDiff =
                currentRoute.get(currentRouteProgressIndex-1)[0] -
                currentRoute.get(currentRouteProgressIndex)[0];
        int colDiff = currentRoute.get(currentRouteProgressIndex-1)[1] -
                currentRoute.get(currentRouteProgressIndex)[1];

        switch (rowDiff) {
            case -1:
                return "u";
            case 1:
                return "d";
            default:
                break;
        }
        switch (colDiff) {
            case -1:
                return "l";
            case 1:
                return "r";
            default:
                break;
        }
        return "error";
    }

    public boolean isDoor(int row, int col, int counter) {
        boolean found = false;
        for (Integer[] index : doors) {
            if (index[0].equals(row) && index[1].equals(col)) {
                found = true;
            }
            counter++;
        }
        return found;
    }

    /*
        Begin a ping in all directions looking for a room to move to
     */
    private void beginPing() {
        boolean[][] explored = new boolean[23][24];
        for (boolean[] b : explored)
            Arrays.fill(b, false);

        System.out.println("Begin ping");
        if (map.isValidMove(new Coordinates(currentPosition[1], currentPosition[0]), "l")) {
            System.out.println("Move Left");
            ping(currentPosition[1] - 1, currentPosition[0],
                    new ArrayList<>(), explored);
        }
        if (map.isValidMove(new Coordinates(currentPosition[1], currentPosition[0]), "u"))
            ping(currentPosition[1], currentPosition[0]-1,
                    new ArrayList<>(), explored);
        if (map.isValidMove(new Coordinates(currentPosition[1], currentPosition[0]), "r"))
            ping(currentPosition[1]+1, currentPosition[0],
                    new ArrayList<>(), explored);
        if (map.isValidMove(new Coordinates(currentPosition[1], currentPosition[0]), "d"))
            ping(currentPosition[1], currentPosition[0]+1,
                    new ArrayList<>(), explored);
    }

    /*
        This is a recursive method that finds the quickest path to a room we want to visit
            Each call updates its own route, and returns it when a suitable room is found
               explored is sent as an empty array of booleans, one for each board square
     */
    private void ping(int row, int col, ArrayList<Integer[]> route, boolean[][] explored) {
        // Counter is supposed to help the bot choose which exit to take from a room
        // Not using this yet because I'm just having the bot always pick exit 0
        int counter = 0;

        // If we've come to a square that we haven't been to yet...
        if (!explored[col][row]) {
            // Mark this square as explored
            explored[col][row] = true;
            // Add this square to the current route
            route.add(new Integer[]{col, row});
            // If we have found a door to a room
            if (isDoor(col, row, counter)) {
                System.out.println("Check isDoor");
                // If we haven't eliminated the possibility of this room
                    // being in the murder envelope
                if (shouldIVisitRoom(map.getRoom(doorsReference[counter]))) {
                    System.out.println("Check should visit");
                    // Set the current route as the route to this room
                    currentRoute = route;
                    for (Integer[] i : route)
                        System.out.println("Route:" + i[0] + " - " + i[1]);
                    // Denote that we are moving to a room currently
                    movingToRoom = true;
                    // Set the room we're moving towards
                    // We will use this variable to check at the beginning of
                        // each turn if we should still be moving toward that room
                    currentRoomDestination = map.getRoom(doorsReference[counter]);
                    // End recursive calls
                    return;
                }
            }
        }
        // Check each direction to ensure we can move that way
        // If so, recursive call to continue in that direction
        if (map.isValidMove(new Coordinates(col-1, row), "l")) {
            Coordinates coords = new Coordinates(col-1, row);
            System.out.println(coords.toString());
            ping(col - 1, row, route, explored);
        }
        if (map.isValidMove(new Coordinates(col, row-1), "u"))
            ping(col, row-1, route, explored);
        if (map.isValidMove(new Coordinates(col+1, row), "r"))
            ping(col+1, row, route, explored);
        if (map.isValidMove(new Coordinates(col, row+1), "d"))
            ping(col, row+1, route, explored);
    }

    /*
        shouldIVisitRoom returns a boolean when passed a room as a parameter.
            If it decides we want to visit that room, it returns true.
            It is called from ping() and determines where we go next.
     */
    private boolean shouldIVisitRoom(Room room) {
        // If we need to accuse, just check if we're going to the cellar
        if (timeToAccuse)
            return room.hasName("Cellar");
        // Otherwise, decide if the given room is worth visiting
        for (NoteCard nc : roomHandCards) {
            if (nc.name.equals(room.toString())) {
                if (nc.probability > 0)
                    return true;
                break;
            }
        }
        return false;
    }

    /*
        Guessing
     */
    public static class NoteCard {
        private String name;
        public int probability;
        private boolean guessed;
        private String whoHas;

        public NoteCard(String name) {
            this.name = name;
            probability = 100;
            guessed = false;
            whoHas = "";
        }

        private boolean isGuessed() {
            return guessed;
        }

        private int getProbability() {
            return probability;
        }

        private void setShown() {
            probability = 0;
        }

        private void setMaybeShown() {
            if (probability > 25)
                probability -= 25;
            else
                probability = 0;
        }
    }
    
    /* Going to contain all of the Guessing Logic */
    public class GuessingLogic {
    	
    	private Boolean readyToAccuse = false;
    	
    	private Boolean lockedInCharacter = false;
    	private NoteCard lockedInNoteCharacter;
    	
    	private Boolean lockedInWeapon = false;
    	private NoteCard lockedInNoteWeapon;
    	
    	private Boolean lockedInRoom = false;
    	private NoteCard lockedInNoteRoom;
    	
    	public GuessingLogic() {
			/* Filling the note cards */
    		setNoteCards();
    	}
    	
    	/**
    	 * To be called at the beginning of out bots turn
    	 */
    	public void startTurnLogic() {
    		if (!readyToAccuse) {
    			/* Reviewing turn's prior log and seeing if we can update the probability of anything in the ArrayLists */
    			reviewLog(log);
    		
    			/* Removing the cards from the lists who have a probability of 0 */
    			removePlayers();
    			removeRooms();
    			removeWeapons();
    			
    			/* Sorting the list so that, the cards with the highest probability are at the beginning of the lists */
    			sortPlayers();
    			sortRooms();
    			sortWeapons();
    		
    			/* checking to see if we are ready to accuse a player */
    			accuseCheck();
    			lockedInRefresh();
    		}
    	}
    	
    	private void lockedInRefresh() {
    		if (playerCards.size() == 1 && playerCards.get(0).guessed) {
    			lockedInCharacter = true;
    			lockedInNoteCharacter = playerCards.get(0);
    		}
    		
    		if (weaponCards.size() == 1 && weaponCards.get(0).guessed) {
    			lockedInWeapon = true;
    			lockedInNoteWeapon = playerCards.get(0);
    		}
    		
    		if (roomCards.size() == 1 && roomCards.get(0).guessed) {
    			lockedInRoom = true;
    			lockedInNoteRoom = playerCards.get(0);
    		}
    	}
    	
    	/**
    	 * Going though the log and seeing if we can update any of the probabilities based on the 
    	 * @param reviewMe
    	 */
    	private void reviewLog(Log reviewMe) {
    		/*  while the log continues  */
    		
    		//TODO: josh look at this and see if I can get the first entry in the log
    		while (reviewMe.hasNext()) {
    			String tempLookUp = reviewMe.next();
    			int ourGuessCheck = tempLookUp.indexOf(':');
    			
    			if (ourGuessCheck < 0) {
    			
    				/* Need to loop though the string and see if we get any matches for the  */
    				for (int i = 0; i < playerCards.size(); i++) {
    					String tempName = playerCards.get(i).name;
    				
    					/* If we find the player's name in the log string  */
    					if (tempLookUp.contains(tempName)) {
    					
    					}
    				
    				}
    			
    				/* Looping though the weaponCards */
    				for (int i = 0; i < weaponCards.size(); i++) {
    					String tempName = weaponCards.get(i).name;
    				
    					/* */
    					if (tempLookUp.contains(tempName)) {
    					
    					}
    				}
    			
    				/* Looping though the characterCards */
    				for (int i = 0; i < roomCards.size(); i++) {
    					String tempName = roomCards.get(i).name;
    				
    					if (tempLookUp.contains(tempName)) {
    					
    					}
    				}
    			}
    		}
    		
    	}
    	
    	private void accuseCheck() {
    		if (lockedInCharacter && lockedInWeapon && lockedInRoom) {
    			readyToAccuse = true;
    		}
    	}
    	
    	private void sortList(List<NoteCard> myList) {
    		NoteCard temp;
   
    		/* Using Bubble sort to arrange the cards with the highest probability at the beginning of the array */
    		for (int x = 0; x < myList.size(); x++) {
                for (int i=0; i < myList.size()- x - 1; i++) {
                    if (myList.get(i).probability < myList.get(i+1).probability) {
                        temp = myList.get(i);
                        myList.set(i, myList.get(i+1) );
                        myList.set(i+1, temp);
                    }
                }
            }
    		
    	}
    	
    	private void removeCards(List<NoteCard> myList, char type) {
    		int currentIndex = 0;
    		
    		for (NoteCard currCard: myList) {
    			if (currCard.probability == 0) {
    				
    				if (type == 'c') {
    					playerRemovedCards.add(myList.get(currentIndex));
    				}
    				else if (type == 'w') {
    					weaponRemovedCards.add(myList.get(currentIndex));
    				}
    				else {
    					roomRemovedCards.add(myList.get(currentIndex));
    				}
    				
    				myList.remove(currentIndex);
    			}
    			currentIndex++;
    		}
    	}
    	
    	/**
    	 * Updating guessing based on question feedback
    	 * @param questionLog
    	 */ 
    	public void questionAnswered(Log questionLog) {

    		playerCards.get(0).guessed = true;
    		weaponCards.get(0).guessed = true;
    		roomCards.get(0).guessed = true;
    		
    		/* If none of the cards we guessed were shown back to us */
    		if (!questionLog.hasNext()) {
    			playerCards.get(0).probability = 200;
    			weaponCards.get(0).probability = 200;
    			roomCards.get(0).probability = 200;
    		}
    		else { //if we get some cards shown back to us:
    			while (questionLog.hasNext()) {
    				/* Going to hold the response we get from the iterator */
    				String temp = questionLog.next();
    				
    				/* Need to see if the iterator is on a response or not */
    				Boolean returnStatement  = false;
    				
    				int startIndex = temp.indexOf(':');
    				
    				/* If this is the case -- then we are looking at a question and not a reply */
    				if (startIndex < 0) {
    					//Nothing 
    				}
    				/* Otherwise we are looking at an answer to the question */
    				else {
    					/* We know that the index of  */
    					int endIndex = temp.indexOf('.');
    					
    					/* Grabbing the card name that was returned */
    					String nameSubstring = temp.substring(startIndex+1, endIndex);
    					
    					/*  Checking to see if the card name given is the (weapon, character, or room) we guessed */
    					if (playerCards.get(0).name.equals(nameSubstring)) {
    						playerCards.get(0).probability = 0;		
    					}
    					else if (weaponCards.get(0).name.equals(nameSubstring)) {
    						weaponCards.get(0).probability = 0;
    					}
    					else  if (roomCards.get(0).name.equals(nameSubstring)){
    						roomCards.get(0).probability = 0;
    					}
    					else {
    						System.err.println("Someathing fucked up");
    					}
    				}
    			}
    		}
    		accuseCheck();
    	}
    	
    	private void sortPlayers() {
    		sortList(playerCards);
    	}
    	
    	private void sortWeapons() {
      		sortList(weaponCards); 	
    	}
    	
    	private void sortRooms() {
    		sortList(roomCards);
    	}
    	
    	private void removePlayers() {
    		removeCards(playerCards, 'p');
    	}
    	
    	private void removeWeapons() {
    		removeCards(weaponCards, 'w');
    	}
    	
    	private void removeRooms() {
    		removeCards(roomCards, 'r');
    	}
    	
    	/* Getter methods */
    	public NoteCard getCharacterGuess() {
    		if (lockedInCharacter && !timeToAccuse) {
    			if (playerHandCards.size() > 0) {
    				Random randomGenerator = new Random();
    				return playerHandCards.get(randomGenerator.nextInt(playerHandCards.size()));
    			}
    			else {
    				return lockedInNoteCharacter;
    			}
    		}
    		else if (lockedInCharacter && timeToAccuse) {
    			return lockedInNoteCharacter;
    		}
    		else {
    			return playerCards.get(0);
    		}
    	}
    	
    	public NoteCard getWeaponGuess() {
    		if (lockedInWeapon && !timeToAccuse) {
    			if (weaponHandCards.size() > 0) {
    				Random randomGenerator = new Random();
    				return weaponHandCards.get(randomGenerator.nextInt(weaponHandCards.size()));
    			}
    			else {
    				return lockedInNoteWeapon;
    			}
    		}
    		else if (lockedInWeapon && timeToAccuse) {
    			return lockedInNoteWeapon;
    		}
    		else {
    			return weaponCards.get(0);
    		}
    	}
    	
    	public NoteCard getRoomGuess() {
    		if (lockedInRoom && !timeToAccuse) {
    			if (roomHandCards.size() > 0) {
    				Random randomGenerator = new Random();
    				return roomHandCards.get(randomGenerator.nextInt(roomHandCards.size()));
    			}
    			else {
    				return lockedInNoteRoom;
    			}
    		}
    		else if (lockedInRoom && timeToAccuse) {
    			return lockedInNoteRoom;
    		}
    		else {
    			return roomCards.get(0);
    		}
    	}
    	
    	public Boolean getAccuseState() {
    		return readyToAccuse;
    	}
    }

    private void setNoteCards() {
        for (int i=0; i<6; i++) {
            if (!player.hasCard(Names.SUSPECT_NAMES[i])) {
            	playerCards.add(new NoteCard(Names.SUSPECT_NAMES[i]));
            }
            else {
            	playerHandCards.add(new NoteCard(Names.SUSPECT_NAMES[i]));
            }
        }
        for (int i=0; i<6; i++) {
            if (!player.hasCard(Names.WEAPON_NAMES[i])) {
            	weaponCards.add(new NoteCard(Names.WEAPON_NAMES[i]));
            }
            else {
            	weaponHandCards.add(new NoteCard(Names.WEAPON_NAMES[i]));
            }
        }
        for (int i=0; i<9; i++) {
        	if (!player.hasCard(Names.ROOM_CARD_NAMES[i])) {
        		roomCards.add(new NoteCard(Names.ROOM_NAMES[i]));
        	}
        	else {
        		roomHandCards.add(new NoteCard(Names.ROOM_CARD_NAMES[i]));
        	}
        }
    }


    private NoteCard getNoteCardByName(String name) {
        for (NoteCard nc : playerHandCards) {
            if (nc.name.equals(name))
                return nc;
        }
        for (NoteCard nc : weaponHandCards) {
            if (nc.name.equals(name))
                return nc;
        }
        for (NoteCard nc : roomHandCards) {
            if (nc.name.equals(name))
                return nc;
        }
        return null;
    }


    /*
        Auxiliary and storage methods
     */

    private void storeAllDoors() {
        doors.add(new Integer[]{6,4});
        doors.add(new Integer[]{5,8});
        doors.add(new Integer[]{7,9});
        doors.add(new Integer[]{7,14});
        doors.add(new Integer[]{5,15});
        doors.add(new Integer[]{4,18});
        doors.add(new Integer[]{9,18});
        doors.add(new Integer[]{12,22});
        doors.add(new Integer[]{16,17});
        doors.add(new Integer[]{14,20});
        doors.add(new Integer[]{21,17});
        doors.add(new Integer[]{18,11});
        doors.add(new Integer[]{18,12});
        doors.add(new Integer[]{20,14});
        doors.add(new Integer[]{19,6});
        doors.add(new Integer[]{15,6});
        doors.add(new Integer[]{12,7});
        doors.add(new Integer[]{17,12});
    }
//    private void storeAllDoors() {
//        doors.add(new Integer[]{4,6});
//        doors.add(new Integer[]{8,5});
//        doors.add(new Integer[]{9,7});
//        doors.add(new Integer[]{14,7});
//        doors.add(new Integer[]{15,5});
//        doors.add(new Integer[]{18,4});
//        doors.add(new Integer[]{18,9});
//        doors.add(new Integer[]{22,12});
//        doors.add(new Integer[]{17,16});
//        doors.add(new Integer[]{20,14});
//        doors.add(new Integer[]{17,21});
//        doors.add(new Integer[]{11,18});
//        doors.add(new Integer[]{12,18});
//        doors.add(new Integer[]{14,20});
//        doors.add(new Integer[]{6,19});
//        doors.add(new Integer[]{6,15});
//        doors.add(new Integer[]{7,12});
//        doors.add(new Integer[]{12,17});
//    }
}
