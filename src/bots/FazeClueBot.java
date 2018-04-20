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

        /* Creating the game cards */
        setNoteCards();
        guessing = new GuessingLogic();
    }
    
//    
//    Note: All inputs are sanitized with .trim() and .toLowerCase()
//
    
    public String getName() {
        return "FazeClueBot"; // must match the class name
    }

    boolean rollOrDone = false;

    public String getCommand() {
        diceRoll = dice.getTotal();
        guessing.startTurnLogic();

        rollOrDone = !rollOrDone;
        if (inRoom && guessedInRoom) {
            inRoom = false;
            guessedInRoom = false;
            return "roll";
        }
        else if (inRoom) {
            guessedInRoom = true;
            return "question";
        }

        if (rollOrDone) {
            guessedInRoom = false;
            inRoom = false;
            return "roll";
        }
        else
            return "done";

//        switch (commandnumber) {
//            case 0:
//                commandnumber++;
//                return "roll";
//            case 1:
//                System.out.println("Case 1");
//                return "done";
//            case 2:
//                System.out.println("Case 2");
//                return "passage";
//            case 3:
//                System.out.println("Case 3");
//                return "question";
//            case 4:
//                System.out.println("Case 4");
//                return "accuse";
//            case 5:
//                System.out.println("Case 5");
//                return "done";
//            default:
//                break;
//		}
//        // Possible inputs: quit|done|roll|passage|notes|cheat|question|log|accuse|help
//        return "roll";
    }

    Random rand;

    public String getMove() {
        diceRoll--;

        if (diceRoll == 0)
            return "done";

        String move = "done";

        rand = new Random();

        do {

            int commandInt = rand.nextInt(4);

            switch (commandInt) {
                case 0:
                    move = "u";
                    break;
                case 1:
                    move = "d";
                    break;
                case 2:
                    move = "l";
                    break;
                case 3:
                    move = "r";
                    break;
            }
        } while (map.getNewPosition(player.getToken().getPosition(), move) == lastPosition
                || !map.isValidMove(player.getToken().getPosition(), move)
                || (map.getNewPosition(player.getToken().getPosition(), move).getCol() == 12)
                    && map.getNewPosition(player.getToken().getPosition(), move).getRow() == 16);

        lastPosition = player.getToken().getPosition();

        if (map.isDoor(lastPosition, map.getNewPosition(lastPosition, move)))
            inRoom = true;

        return move;
    }

    public String getSuspect() {
    	System.err.println("I am guessing this character: " + guessing.getCharacterGuess().name);
        return guessing.getCharacterGuess().name;
    }

    public String getWeapon() {
    	System.err.println("I am guessing this weapon: " + guessing.getWeaponGuess().name);
    	return guessing.getWeaponGuess().name;
    }

    public String getRoom() {
        System.err.println("I am guessing this room: " + guessing.getRoomGuess().name);
    	return guessing.getRoomGuess().name;
    }

    public String getDoor() {
        // Add your code here
        return "1";
    }
    
    /* When someone is asking a question */
    public String getCard(Cards matchingCards) {
        // Add your code here
        Query ourQuery = new Query(getSuspect(), getWeapon(), getRoom());
        // Possible input: 1|2|3|4
        // Input needs to return true: (at least one card).hasName(String input) in matchingCards
        return matchingCards.get(ourQuery).toString();
    }

    public String getPlayerName() {
        return "FazeClueBot";
    }
    
    /*
     * Function call that happens when we ask a question and get an answer
     * TODO: Josh look at this and see if I am going in the right direction
     */
    public void notifyResponse(Log response) {
    	guessing.questionAnswered(response);
    }
    
    @Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public void notifyPlayerName(String playerName) {
		// TODO Auto-generated method stub
    }

	@Override
	public void notifyTurnOver(String playerName, String position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyQuery(String playerName, String query) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyReply(String playerName, boolean cardShown) {
		// TODO Auto-generated method stub
		
	}

    /*
        Our Code
     */

    /*
        New variables and methods
     */

    // Map coords: 24 x 23
    private Coordinates lastPosition;
    private int diceRoll;

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
    private boolean firstMove = true;

    private void takeTurn() {
        System.out.println("Location: " + player.getToken().getPosition().toString());

        /* Updating probability */
        if (firstTurn) {
            setNoteCards();
            firstTurn = false;
        }

        
        timeToAccuse = guessing.getAccuseState();
    }

    /*
        shouldIVisitRoom returns a boolean when passed a room as a parameter.
            If it decides we want to visit that room, it returns true.
            It is called from ping() and determines where we go next.
     */
        private boolean shouldIVisitRoom (Room room){
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
    	
    	private Boolean firstCharacterGuess;
    	private Boolean firstWeaponGuess;
    	private Boolean firstRoomGuess;
    	
    	public GuessingLogic() {
			/* Filling the note cards */
    		firstCharacterGuess = true;
    		firstWeaponGuess = true;
    		firstRoomGuess = true;
    	}
    	
    	//TODO: add the probability case from the log 
    	
    	/**
    	 * To be called at the beginning of out bots turn
    	 */
    	public void startTurnLogic() {
    	//	if (!readyToAccuse) {
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
    	//	}
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
    		
    		while (reviewMe.hasNext() ) {
    			tempIndex++;
    			String tempLookUp = reviewMe.next();
    			
    			if (reviewMe.hasNext()) {
    			
    			int ourGuessCheck = tempLookUp.indexOf(':');
    			
    			if (tempLookUp.contains(getName()) == false) {

    				if (tempLookUp.contains("showed one card")) {
    					/* Need to loop though the string and see if we get any matches for the  */
    					for (int i = 0; i < playerCards.size(); i++) {
    						String tempName = playerCards.get(i).name;
    				
    						/* If we find the player's name in the log string  */
    						if (tempLookUp.contains(tempName)) {
    							System.out.println("We got a match with player!"+ tempLookUp + " = " + tempName);
    							playerCards.get(i).probability = playerCards.get(i).probability - 11;
    							break;
    						}
    					}
    			
    					/* Looping though the weaponCards */
    					for (int i = 0; i < weaponCards.size(); i++) {
    						String tempName = weaponCards.get(i).name;
    				
    						/* If we find the weapon name in the log string */
    						if (tempLookUp.contains(tempName)) {
    							System.out.println("We got a match with weapon!"+ tempLookUp + " = " + tempName);
    							weaponCards.get(i).probability = weaponCards.get(i).probability - 11;
    							break;
    						}
    					}
    			
    					/* Looping though the characterCards */
    					for (int i = 0; i < roomCards.size(); i++) {
    						String tempName = roomCards.get(i).name;
    				
    						if (tempLookUp.contains(tempName)) {
    							System.out.println("We got a match with room!: " + tempLookUp + " = " + tempName);
    							roomCards.get(i).probability = roomCards.get(i).probability - 11;
    							break;
    						}
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
    		/* Will hold the values that we are going to delete for this turn  */
    		List<NoteCard> deleteCandidates = new ArrayList<>(); 
		
    		/* First pass -- grab the values we want to delete */
    		for (NoteCard currValue : myList) {
    			/* If we get a value that is marked for deletion */
    			if (currValue.probability == 0) {
    				deleteCandidates.add(currValue);
    			}
    		}	
		 
    		/* Last Pass -- actually removing the values */
    		for (NoteCard deleteCandidate : deleteCandidates) {
    			myList.remove(deleteCandidate);
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
    				System.out.println("\n\n\n\n\n\n");
    				System.out.println(temp);
    				/* If we have the ':' then we know that we are dealing with answers to our question */
    				int startIndex = temp.indexOf(':');
    				
    				/* Case is we do not find the ':' -> meaning we didnt get an answer to our question */
    				if (startIndex < 0 || temp.contains(getName())) {
    					//Nothing 
    				}
    				/* Otherwise we are looking at an answer to the question */
    				else {

    					/* We know that the end of the card name we have the '.' char */
    					int endIndex = temp.indexOf('.');
    					
    					/* Grabbing the card name that was returned */
    					String nameSubstring = temp.substring(startIndex+2, endIndex);
  
     					
    					/*  Checking to see if the card name given is the (weapon, character, or room) we guessed */
    					
    					System.out.println("\n\n\n\n\n---------------------");	
     					System.err.println("We made a guess and got an answer!");
     					System.out.println("This is the stirng: "+ temp);
     					
    					System.out.println(playerCards.get(0).name);
    					System.out.println(weaponCards.get(0).name);
    					System.out.println(roomCards.get(0).name);
    					
    					System.out.println(nameSubstring);
    					
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
    						System.err.println("Error");
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
    		/* checking to see if we cane make a winning 'guess' to try and get the other bots out */
    		if (firstCharacterGuess) {
     			firstCharacterGuess = false;
    			if (playerHandCards.size() > 0) {
    				Random randomGenerator = new Random();
    				return playerHandCards.get(randomGenerator.nextInt(playerHandCards.size()));
    			}
    			else {
    				return playerCards.get(0);
    			}   			
    		}
    		
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
    		/* checking to see if we can make a winning 'guess' to try and get the other bots out */
    		if (firstWeaponGuess) {
    			firstWeaponGuess = false;
    			if (weaponHandCards.size() > 0) {
    				Random randomGenerator = new Random();
    				return weaponHandCards.get(randomGenerator.nextInt(weaponHandCards.size()));
    			}
    			else {
    				return weaponCards.get(0);
    			}
    		}
    		
    		
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
    		/* Checking to see if we can make a winning 'guess' to get the other bots out */
     		if (firstRoomGuess) {
     			firstRoomGuess = false;
    			if (roomHandCards.size() > 0) {
    				Random randomGenerator = new Random();
    				return roomHandCards.get(randomGenerator.nextInt(roomHandCards.size()));
    			}
    			else {
    				return roomCards.get(0);
    			}
    		}   		
    		
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
    	
    	System.out.println("These are the cards we are starting with: " + player.getCards().toString());
    	System.out.println("\n");
    	
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

//    private void storeAllDoors() {
//        doors.add(new Coordinates(4,6));
//        doors.add(new Coordinates(8,5));
//        doors.add(new Coordinates(9,7));
//        doors.add(new Coordinates(14,7));
//        doors.add(new Coordinates(15,5));
//        doors.add(new Coordinates(18,4));
//        doors.add(new Coordinates(18,9));
//        doors.add(new Coordinates(22,12));
//        doors.add(new Coordinates(17,16));
//        doors.add(new Coordinates(20,14));
//        doors.add(new Coordinates(17,21));
//        doors.add(new Coordinates(11,18));
//        doors.add(new Coordinates(12,18));
//        doors.add(new Coordinates(14,20));
//        doors.add(new Coordinates(6,19));
//        doors.add(new Coordinates(6,15));
//        doors.add(new Coordinates(7,12));
//        doors.add(new Coordinates(12,16));
//    }
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
