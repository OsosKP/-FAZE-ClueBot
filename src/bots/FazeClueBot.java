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
    private Boolean firstTurn = true;
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
			return findNextCommandMovingToRoom();
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
        return guessing.getCharacterGuess().name;
    }

    public String getWeapon() {
        return guessing.getWeaponGuess().name;
    }

    public String getRoom() {
        return guessing.getRoomGuess().name;
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
    private List<Coordinates> currentRoute = new ArrayList<>();
    private Room currentRoomDestination;
    private Coordinates currentPosition;
    private List<Integer[]> doors = new ArrayList<>();

    /* ArrayLists that will represent the different cards in the game that the bot will guess */ 
    private List<NoteCard> playerCards = new ArrayList<>();
    private List<NoteCard> weaponCards = new ArrayList<>();
    private List<NoteCard> roomCards = new ArrayList<>();
    
    /* ArrayList that will represent the cards that are currently in our hand */
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
    private Coordinates getCurrentPosition() {
        return currentPosition;
    }
    private int getCurrentRow() {
        return currentPosition.getRow();
    }
    private int getCurrenColumn() {
        return currentPosition.getCol();
    }
    private void setCurrentPosition(Coordinates coords) {
        currentPosition = coords;
    }

    private String takeTurn() {
        System.out.println("Location: " + player.getToken().getPosition().toString());

        if (!(movingToRoom || inRoom || guessedInRoom)) {
            return "roll";
        }

        /* Updating probability */
    	if (firstTurn) {
    		setNoteCards();
    		firstTurn = false;
    	}
    	
//    	guessing.startTurnLogic();

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
                // TODO: Exit - Is this right?
                // Rolling dice should have us exit the room
                // We should ensure the correct exit is picked prior to rolling,
                    // because the rollDice() method checks getDoor()
                // I've just got it returning 0 for now so we don't crash
                return "roll";
            }
            // Otherwise, make a guess
            else {
            	commandnumber=3;//This tells the game that we want to guess
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
            System.out.println("There's your problem");
            beginPing();
            currentRouteProgressIndex = 0;
            return "l";
        }

        System.out.println("Check: from " + currentRoute.get(currentRouteProgressIndex-1)
                + " to " + currentRoute.get(currentRouteProgressIndex));

       	commandnumber=1;//Command is move
        /*
            The next square in our route will be different in either the row
                or column index. Find which one it is (it should only be one) and
                set our next move command accordingly.
         */
        int rowDiff =
                currentRoute.get(currentRouteProgressIndex-1).getRow() -
                currentRoute.get(currentRouteProgressIndex).getRow();
        int colDiff = currentRoute.get(currentRouteProgressIndex-1).getCol() -
                currentRoute.get(currentRouteProgressIndex).getCol();

        currentPosition = player.getToken().getPosition();

        switch (rowDiff) {
            case -1:
                return "d";
            case 1:
                return "u";
            default:
                break;
        }
        switch (colDiff) {
            case -1:
                return "r";
            case 1:
                return "l";
            default:
                break;
        }
        return "error";
    }

    public boolean isDoor(int col, int row, int counter) {
        boolean found = false;
        for (Integer[] index : doors) {
            if (index[0].equals(col) && index[1].equals(row)) {
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
        boolean[][] explored = new boolean[24][25];
        for (boolean[] b : explored)
            Arrays.fill(b, false);

        System.out.println("Begin ping");

        System.out.println("Current Position: " + player.getToken().getPosition());


        if (map.isValidMove(currentPosition, "l") && currentPosition.getCol() > 0)
            ping(currentPosition.getCol()-1, currentPosition.getRow(),
                    new ArrayList<>(), explored);
        if (map.isValidMove(currentPosition, "u") && currentPosition.getRow() > 0)
            ping(currentPosition.getCol(), currentPosition.getRow()-1,
                    new ArrayList<>(), explored);
        if (map.isValidMove(currentPosition, "r") && currentPosition.getCol() < 23)
            ping(currentPosition.getCol()+1, currentPosition.getRow(),
                    new ArrayList<>(), explored);
        if (map.isValidMove(currentPosition, "d") && currentPosition.getRow() < 24)
            ping(currentPosition.getCol(), currentPosition.getRow()+1,
                    new ArrayList<>(), explored);


    }

    /*
        This is a recursive method that finds the quickest path to a room we want to visit
            Each call updates its own route, and returns it when a suitable room is found
               explored is sent as an empty array of booleans, one for each board square
     */
    private void ping(int col, int row, ArrayList<Coordinates> route, boolean[][] explored) {
        // Counter is supposed to help the bot choose which exit to take from a room
        // Not using this yet because I'm just having the bot always pick exit 0
        int counter = 0;

        if (col < 0 || row < 0 || col > 22 || row > 23 || explored[col][row])
            return;

        Coordinates coordinates = new Coordinates(col, row);

        System.out.println("Now: " + coordinates.toString());

        // If we've come to a square that we haven't been to yet...
        if (!explored[col][row]) {
            // Mark this square as explored
            explored[col][row] = true;
            // Add this square to the current route
            route.add(coordinates);
            // If we have found a door to a room
            if (isDoor(col, row, counter)) {
                // If we haven't eliminated the possibility of this room
                    // being in the murder envelope
                System.out.println("Coords: (" + col + ", " + row + ")");
                if (shouldIVisitRoom(map.getRoom(new Coordinates(row, col)))) {
                    // Set the current route as the route to this room
                    currentRoute = route;
                    // Denote that we are moving to a room currently
                    movingToRoom = true;
                    // Set the room we're moving towards
                    // We will use this variable to check at the beginning of
                        // each turn if we should still be moving toward that room
                    currentRoomDestination = map.getRoom(new Coordinates(row, col));
                    // End recursive calls
                    return;
                }
                else
                    return;
            }
        }
        // Check each direction to ensure we can move that way
        // If so, recursive call to continue in that direction
        if (map.isValidMove(currentPosition, "l"))
            ping(col - 1, row, route, explored);
        if (map.isValidMove(currentPosition, "u"))
            ping(col, row-1, route, explored);
        if (map.isValidMove(currentPosition, "r"))
            ping(col+1, row, route, explored);
        if (map.isValidMove(currentPosition, "d"))
            ping(col, row+1, route, explored);
    }

    // TODO: George: Change this probability of 25 to whatever you want
    /*
        shouldIVisitRoom returns a boolean when passed a room as a parameter.
            If it decides we want to visit that room, it returns true.
            It is called from ping() and determines where we go next.
     */
    private boolean shouldIVisitRoom(Room room) {
        System.out.println("Should I visit the " + room.toString() + "?");
        return true;
//        // If we need to accuse, just check if we're going to the cellar
//        if (timeToAccuse)
//            return room.hasName("Cellar");
//        // Otherwise, decide if the given room is worth visiting
//        for (NoteCard nc : roomHandCards) {
//            if (nc.name.equals(room.toString())) {
//                if (nc.probability > 0)
//                    return true;
//                break;
//            }
//        }
//        return false;
    }

    // TODO: George: The skeleton of this is right, but update based on your probability needs
    public boolean checkIfIShouldGoToCellar() {
        int counter = 0;
        // Check status of characters guessed
        for (int i=0; i<6; i++) {
            if (playerHandCards.get(i).probability > 0)
                // Count how many characters we're unsure about
                counter++;
        }
        // If we're unsure about more than 1, no guessing
        if (counter > 1)
            return false;

        counter = 0;
        // Do the same method for weapons
        for (int i=0; i<6; i++) {
            if (weaponHandCards.get(i+6).probability > 0)
                // Count how many weapons we're unsure about
                counter++;
        }
        if (counter > 1)
            return false;

        // Do the same method for rooms
        for (int i=0; i<9; i++) {
            if (roomHandCards.get(i+12).probability > 0)
                // Count how many rooms we're unsure about
                counter++;
        }
        // At this point if we only have 1 room to guess, we're ready.
        // Otherwise, this evaluates to false and we don't guess
        return (counter == 1);
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
    
    public class GuessingLogic {
    	
    	private Boolean readyToAccuse = false;
    	
    	private Boolean lockedInCharacter = false;
    	private NoteCard lockedInNoteCharacter;
    	
    	private Boolean lockedInWeapon = false;
    	private NoteCard lockedInNoteWeapon;
    	
    	private Boolean lockedInRoom = false;
    	private NoteCard lockedInNoteRoom;
    	
    	private int logSize;
    	public GuessingLogic() {
			/* Filling the note cards */
    		//setNoteCards();
    	}
    	
    	//TODO: add the probability case from the log 
    	
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
    			lockedInRefresh();
    			accuseCheck();
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
    		System.err.println("I am working!");
    		int tempIndex = 0;
    		reviewMe.iterator();
    		
    		while (reviewMe.hasNext()) {
    			tempIndex++;
    			
    			String tempLookUp = reviewMe.next();
    			int ourGuessCheck = tempLookUp.indexOf(':');
    			
    			if (ourGuessCheck < 0) {
    				if (tempLookUp.contains("showed one card")) {
    					/* Need to loop though the string and see if we get any matches for the  */
    					for (int i = 0; i < playerCards.size(); i++) {
    						String tempName = playerCards.get(i).name;
    				
    						/* If we find the player's name in the log string  */
    						if (tempLookUp.contains(tempName)) {
    							System.out.println("We got a match with player!");
    							playerCards.get(i).probability = playerCards.get(i).probability - 11;
    						}
    					}
    			
    					/* Looping though the weaponCards */
    					for (int i = 0; i < weaponCards.size(); i++) {
    						String tempName = weaponCards.get(i).name;
    				
    						/* If we find the weapon name in the log string */
    						if (tempLookUp.contains(tempName)) {
    							System.out.println("We got a match woth wepaon!");
    							weaponCards.get(i).probability = weaponCards.get(i).probability - 11;
    						}
    					}
    			
    					/* Looping though the characterCards */
    					for (int i = 0; i < roomCards.size(); i++) {
    						String tempName = roomCards.get(i).name;
    				
    						if (tempLookUp.contains(tempName)) {
    							System.out.println("We got a match with room!");
    							roomCards.get(i).probability = roomCards.get(i).probability - 11;
    						}
    					}
    				
    				}
    			}
    		}
    		logSize = tempIndex;
    	}
    	
    	/**
    	 * Checking to see if we are ready to make an accusation 
    	 */
    	private void accuseCheck() {
    		if (lockedInCharacter && lockedInWeapon && lockedInRoom) {
    			readyToAccuse = true;
    		}
    	}
    	
    	/**
    	 * Simple Bubblesort implementation to sort a given arrayList from high -> low
    	 * @param myList = a given arrayList to sort
    	 */
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
    	
    	/**
    	 * Removing all the cards that were marked for removal
    	 * @param myList = a given list that we are removing elements from
    	 * @param type = the list type (character, weapon, room)
    	 */
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
    		
    		questionLog.iterator();
    		
    		/* Marking the current cards based on what we guessed */
    		playerCards.get(0).guessed = true;
    		weaponCards.get(0).guessed = true;
    		roomCards.get(0).guessed = true;
    		
    		/* Representing the current position in the log */
    		int tempIndex = 0;
    		
    		/* Looping though the log */
    		while (questionLog.hasNext()) {
    			/* If we are looking at the 'new' info in the log */
    			if (tempIndex >= logSize) {
    				/* Grabbing the log line as a string */
    				String temp = questionLog.next();
    
    				/* If we have the ':' then we know that we are dealing with answers to our question */
    				int startIndex = temp.indexOf(':');
    				
    				/* Case is we do not find the ':' -> meaning we didnt get an answer to our question */
    				if (startIndex < 0) {
    					//Nothing 
    				}
    				/* Otherwise we are looking at an answer to the question */
    				else {
    					/* We know that the end of the card name we have the '.' char */
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
    					else { //if stuff broke
    						System.err.println("Someathing fucked up");
    					}
    				}
    			}
    			tempIndex++;
    		}
    	
    		/* If none of the probabilities are changed -- then we have gotten a winning hand! */
    		if (playerCards.get(0).probability != 0 && weaponCards.get(0).probability != 0 && roomCards.get(0).probability != 0) {
    		    System.err.println("We got a winning hand!");
    			lockedInCharacter = true;
    			lockedInRoom = true;
    			lockedInWeapon = true;
    			
    			lockedInNoteCharacter = playerCards.get(0);
    			lockedInNoteRoom = roomCards.get(0);
    			lockedInNoteWeapon = weaponCards.get(0);
    			
    			playerCards.get(0).probability = 200;
    			weaponCards.get(0).probability = 200;
    			roomCards.get(0).probability = 200;	
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
    	
    	System.out.println("This is the cards we are stating with: " + player.getCards().toString());
    	System.out.println("\n");
    	
        for (int i=0; i<6; i++) {
            if (player.hasCard(Names.SUSPECT_NAMES[i]) == false) {
            	playerCards.add(new NoteCard(Names.SUSPECT_NAMES[i]));
            }
            else {
            	playerHandCards.add(new NoteCard(Names.SUSPECT_NAMES[i]));
            }
        }
        for (int i=0; i<6; i++) {       	
            if (player.hasCard(Names.WEAPON_NAMES[i]) == false) {
            	weaponCards.add(new NoteCard(Names.WEAPON_NAMES[i]));
            }
            else {
            	weaponHandCards.add(new NoteCard(Names.WEAPON_NAMES[i]));
            }
        }
        for (int i=0; i<9; i++) {
        	if (player.hasCard(Names.ROOM_CARD_NAMES[i]) == false) {
        		roomCards.add(new NoteCard(Names.ROOM_NAMES[i]));
        	}
        	else {
        		roomHandCards.add(new NoteCard(Names.ROOM_CARD_NAMES[i]));
        	}
        }
        
        System.out.println("Player Card Array:\n ");
        for (int i = 0; i < playerCards.size(); i++) {
        	System.out.println(playerCards.get(i).name);
        }
        System.out.println("\nWeapon Card Array:\n ");;
        for (int i = 0; i < weaponCards.size(); i++) {
        	System.out.println(weaponCards.get(i).name);
        }
        System.out.println("\nRoom Card Arrray:\n ");
        for (int i = 0; i < roomCards.size(); i++) {
        	System.out.println(roomCards.get(i).name);
        }
        System.out.println("-----------------------\n");
        
        System.out.println("Player Hand Array: \n");
        for (int i = 0; i < playerHandCards.size(); i++) {
        	System.out.println(playerHandCards.get(i).name);
        }
        System.out.println("\nWeapon Hand Array: \n");;
        for (int i = 0; i < weaponHandCards.size(); i++) {
        	System.out.println(weaponHandCards.get(i).name);
        }
        System.out.println("\nRoom Hand Arrray: \n");
        for (int i = 0; i < roomHandCards.size(); i++) {
        	System.out.println(roomHandCards.get(i).name);
        }
        System.out.println("-----------------------\n\n");
               
}


    private NoteCard getNoteCardByName(String name) {
        for (NoteCard nc : playerCards) {
            if (nc.name.equals(name))
                return nc;
        }
        for (NoteCard nc : weaponCards) {
            if (nc.name.equals(name))
                return nc;
        }
        for (NoteCard nc : roomCards) {
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
        doors.add(new Integer[]{17,21});
        doors.add(new Integer[]{11,18});
        doors.add(new Integer[]{12,18});
        doors.add(new Integer[]{14,20});
        doors.add(new Integer[]{6,19});
        doors.add(new Integer[]{6,15});
        doors.add(new Integer[]{7,12});
        doors.add(new Integer[]{12,17});
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
