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
    private Boolean firstTurn = true;
    private Random rand;
    private boolean rollOrDone = false;

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

	private Room roomIn;
	private Room lastRoomIn;

	private boolean inRoom = false;
	private boolean guessedInRoom = false;
	private boolean movedThisTurn = false;
	private boolean timeToAccuse = false;
    
    public FazeClueBot (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
        this.rand = new Random();

        guessing = new GuessingLogic();
    }
    
//    
//    Note: All inputs are sanitized with .trim() and .toLowerCase()
//
    
    public String getName() {
        return "FazeClueBot"; // must match the class name
    }

    @Override
	public String getVersion() {
		return "0.2";
	}

    public String getCommand() { // Possible inputs: quit|done|roll|passage|notes|cheat|question|log|accuse|help
    	// We will only be using: roll, question, accuse, done
        if (firstTurn) {
            setNoteCards(); /* Creating the game cards */
            firstTurn = false;
        }

        guessing.startTurnLogic();

        timeToAccuse = guessing.getAccuseState();

        if (timeToAccuse && roomIn.toString().equals("Cellar"))
            return "accuse";

        System.out.println("Time to Accuse: " + timeToAccuse);

        diceRoll = dice.getTotal();

        // rollOrDone is just a simple switch
        rollOrDone = !rollOrDone;

		System.out.println("Roll or Done: " + rollOrDone +
							"\nIn room: " + inRoom +
							"\nGuess in room: " + guessedInRoom +
							"\nMoved this turn: " + movedThisTurn);

        if (inRoom && guessedInRoom && !movedThisTurn) {
            inRoom = false;
            guessedInRoom = false;
            System.out.println(player.getToken().getName() + " has left the room.");
            movedThisTurn = true;
            return "roll";
        }
        else if (inRoom && !guessedInRoom) {
            guessedInRoom = true;
            System.out.println(player.getToken().getName() +
                    " is asking a question.\n In the " + roomIn.toString());
            return "question";
        }

        if (rollOrDone && !movedThisTurn) {
            guessedInRoom = false;
            inRoom = false;
            movedThisTurn = true;
            return "roll";
        }
        else {
        	movedThisTurn = false;
			return "done";
		}
    }
 
    public String getMove() {
    	movedThisTurn = true;
        String move = "Error in move logic";
        do {
            switch (rand.nextInt(4)) { //TODO: Currently random, need to change that back
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

        } while (shouldLoopAgain(move)); 

        lastPosition = player.getToken().getPosition();

        if (map.isDoor(lastPosition, map.getNewPosition(lastPosition, move))) {
            lastRoomIn = roomIn;
            roomIn = map.getRoom(map.getNewPosition(lastPosition, move));

            System.out.println("Last room: " + lastRoomIn);
            System.out.println("Current room: " + roomIn);

            inRoom = true;
        }

        return move;
    }
  
    
    private Boolean shouldLoopAgain(String move) {
    	if (lastPosition == null)
    		return false;
        // Keep running this while one of these conditions is met:
            // 1. If we are trying to move back to the last position 
            // 2. If it is a not valid move
            // 3. If it's not time to accuse, and the door leads to the cellar 
            // 4. If we are trying to go back into a room we were just in
			// 5. The room we're trying to enter is one we don't need to ask about

		// TODO:
		/*
			More checks we can add:
			- Don't go in room if the probability for it is 0
			- Find a way to go to cellar when we have an accusation
		 */
    	
    	Boolean backtolastposition = map.getNewPosition(player.getToken().getPosition(), move) == lastPosition;

    	Boolean validmove = !map.isValidMove(player.getToken().getPosition(), move);

    	Boolean cellarcase = (!timeToAccuse && ((map.getNewPosition(player.getToken().getPosition(), move).getCol() == 12)
				&& map.getNewPosition(player.getToken().getPosition(), move).getRow() == 16));

    	Boolean backtracking = (lastRoomIn != null && ((map.isDoor(lastPosition, map.getNewPosition(lastPosition, move)))
				&& map.getRoom(map.getNewPosition(lastPosition, move)) == lastRoomIn));

    	Boolean needRoom = ((map.isDoor(lastPosition, map.getNewPosition(lastPosition, move))) &&
				!guessing.canEnterRoom(map.getRoom(map.getNewPosition(lastPosition, move)).toString()));

		return (backtolastposition || validmove || cellarcase || backtracking || needRoom);
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

    public String getDoor() { // Gets the door you want to exit from
        return "1";
    }
    
    /* When someone is asking a question */
    public String getCard(Cards matchingCards) {
        return matchingCards.get().toString();
    }

    public String getPlayerName() {
        return "FazeClueBot";
    }
    
    public void notifyResponse(Log response) {
    	guessing.questionAnswered(response);
    }
    
	@Override
	public void notifyPlayerName(String playerName) {
    }

	@Override
	public void notifyTurnOver(String playerName, String position) {

	}

	@Override
	public void notifyQuery(String playerName, String query) {

	}

	@Override
	public void notifyReply(String playerName, boolean cardShown) {

	}

    /*
        Our Code
     */

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
    		printOut(500);
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
    		if (playerCards.size() == 1) {
    			System.out.println("we got one!");
    			printOut(1000);
    			lockedInCharacter = true;
    			lockedInNoteCharacter = playerCards.get(0);
    		}
    		
    		if (weaponCards.size() == 1) {
    			    			System.out.println("we got one!");
    			printOut(1000);
    			lockedInWeapon = true;
    			lockedInNoteWeapon = playerCards.get(0);
    		}
    		
    		if (roomCards.size() == 1) {
    			    			System.out.println("we got one!");
    			printOut(1000);
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
    			
    			String delim = "[ ]+";
    			String[] tempArray = tempLookUp.split(delim);

    			for (String s : tempArray)
    			    s = s.replaceFirst("\\.", "");

    			System.out.println(tempArray[0]);
    			
    			if (!tempArray[0].equals(getName())) {
    				System.out.println("we got though there?");
    				if (reviewMe.hasNext()) {
    					tempIndex++;
    					String tempNext = reviewMe.next();
    				
    				if (tempNext.contains("showed one card")) {
    					System.out.println(tempNext);
    					System.out.println(tempLookUp);
    					
    					System.out.println("\n\n\n\n\n");
    					System.out.println("The player: ");
    					System.out.println(tempArray[6]);
    					System.out.println("The weapon: ");
    					System.out.println(tempArray[9]);
    					System.out.println("The room: ");
    					System.out.println(tempArray[12]);
    					System.out.println("\n\n\n\n\n\n\n");
    					
    					/* Need to loop though the string and see if we get any matches for the  */
    					
    					if (!lockedInCharacter) {
    						for (int i = 0; i < playerCards.size(); i++) {
    							String tempName = playerCards.get(i).name;
    				
    							/* If we find the player's name in the log string  */
    							if (tempArray[6].equals(tempName)) {
    								System.out.println("**We got a match with player!**");
    								System.out.println("\n\n\n");
    								System.out.println("Original String: ");
    								System.out.println(tempLookUp);
    								System.out.println("Our character: ");
    								System.out.println(playerCards.get(i).name);
    								System.out.println("\n\n\n\n");   							
    								playerCards.get(i).probability = playerCards.get(i).probability - 11;
    								break;
    							}
    						}
    					}
    			
    					if (!lockedInRoom) {
    						/* Looping though the weaponCards */
    						for (int i = 0; i < weaponCards.size(); i++) {
    							String tempName = weaponCards.get(i).name;
    				
    							/* If we find the weapon name in the log string */
    							if (tempArray[9].equals(tempName)) {
    								System.out.println("**We got a match with weapon!**");
    								System.out.println("\n\n\n");
    								System.out.println("Original String: ");
    								System.out.println(tempLookUp);
    								System.out.println("Our weapon: ");
    								System.out.println(weaponCards.get(i).name);
    								System.out.println("\n\n\n\n");
    								weaponCards.get(i).probability = weaponCards.get(i).probability - 11;
    								break;
    							}
    							else {
    								String[] tempSplit = tempName.split(delim);
    								if (tempArray[9].equals(tempSplit[0])) {
    									System.out.println("**We got a match with weapon!**");
    									System.out.println("\n\n\n");
    									System.out.println("Original String: ");
    									System.out.println(tempLookUp);
    									System.out.println("Our Weapon: ");
    									System.out.println(weaponCards.get(i).name);
    									System.out.println("\n\n\n\n");
    									weaponCards.get(i).probability = weaponCards.get(i).probability - 11;
    									break;   								
    								}
    							}
    						}
    					}
    			
    					/* Looping though the characterCards */
    					for (int i = 0; i < roomCards.size(); i++) {
    						String tempName = roomCards.get(i).name;
    				
    						if (tempArray[12].contains(tempName)) {
    							System.out.println("**We got a match with room!**");
     							System.out.println("\n\n\n");
    							System.out.println("Original String: ");
    							System.out.println(tempLookUp);
    							System.out.println("Our Room: ");
    							System.out.println(roomCards.get(i).name);
    							System.out.println("\n\n\n\n");
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
    			printOut(500);
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
    	
    	public boolean canEnterRoom(String roomName) {
    		String nameSubstring = roomName.replaceAll("\\s+","");
    		
    		/* Checking to see if we can enter a given room */
    		for (int i = 0; i < roomCards.size(); i++) {
				System.out.println("Comparing " + nameSubstring +
						" to " + roomCards.get(i).name.replaceAll("\\s+",""));
    			String arrayString = roomCards.get(i).name.replaceAll("\\s+","");
    			if (arrayString.equals(nameSubstring)) {
    				return true;
    			}
    		}
    		return false;
    	}
    	
    	private void sleepMe(int mili) {    		
     		try {
				Thread.sleep(mili);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	private void printOut(int mili) {    		
    		System.out.println("\n\n\n\n\n\n\n\n");
    		System.out.println("---Print out of possible cards---");
    		
    		System.out.println("Weapon Cards: ");
    		for (int i = 0; i < weaponCards.size(); i++) {
    			System.out.print("| " + weaponCards.get(i).name + ":" + weaponCards.get(i).probability + " |");
    		}
    		
    		System.out.println("\nCharacter Cards: ");
     		for (int i = 0; i < playerCards.size(); i++) {
    			System.out.print("| " + playerCards.get(i).name + ":" +  playerCards.get(i).probability + " |");
    		}   
     		
     		System.out.println("\nRoom Cards: ");
     		for (int i = 0; i < roomCards.size(); i++) {
    			System.out.print("| " + roomCards.get(i).name + ":" + roomCards.get(i).probability + " |");
    		}    		
     		
     		System.out.println("\n\n");
     		sleepMe(mili);
    	}
    		
    	
    	/**
    	 * Updating guessing based on question feedback
    	 * @param questionLog
    	 */ 
    	public void questionAnswered(Log questionLog) {
    		
    		questionLog.iterator();
    		
    		Boolean roomNotFound = true;
    		int roomFoundIndex = -1;
    		String nameSubstring = null;
    		
    		Boolean playerNotFound = true;
    		int playerFoundIndex = -1;
    		
    		Boolean weaponNotFound = true;
    		int weaponFoundIndex = -1;
    		
    		Boolean illegalParsing = false;
    		
    		/* Marking the current cards based on what we guessed */
    		playerCards.get(0).guessed = true;
    		weaponCards.get(0).guessed = true;
    		roomCards.get(0).guessed = true;
    		
    		/* Representing the current position in the log */
    		int tempIndex = 0;
    		
    		/* Looping though the log */
    		while (questionLog.hasNext()) {
    			
    			System.out.println("tempindex: " + tempIndex + " logsize: " + logSize);
    			
    			/* If we are looking at the 'new' info in the log */
    			if (tempIndex >= logSize) {
    				/* Grabbing the log line as a string */
    				String temp = questionLog.next();
    
    				/* If we have the ':' then we know that we are dealing with answers to our question */
    				int startIndex = temp.indexOf(':');
    				
    				/* Case is we do not find the ':' -> meaning we didnt get an answer to our question */
    				if (startIndex < 0 && temp.contains(getName())) {
    					//Nothing 
    				}
    				/* Otherwise we are looking at an answer to the question */
    				else {
    					/* We know that the end of the card name we have the '.' char */
    					int endIndex = temp.indexOf('.');
    					
    					    					
    					/* Grabbing the card name that was returned */
    					nameSubstring = temp.substring(startIndex+2, endIndex);
    					nameSubstring = nameSubstring.replaceAll("\\s+","");
    					
    					System.out.println("This is the stirng: " + temp);
    					System.out.println("This is the sub: " + nameSubstring);
    					
    					//String compareWeapom = weaponCards.get(0).name.replaceAll("\\s+","");
    					

    					if (nameSubstring.length() > 11) {
     						System.out.println("am I getting called?");
    						illegalParsing = true;
     					}     					
    				
    					
    					/* Checking to see if we got a match for the room */
    					for (int i = 0; i < roomCards.size(); i++) {
    						
    						String arrayString = roomCards.get(i).name.replaceAll("\\s+","");
    						System.out.println(roomCards.get(i).name + " =? " + nameSubstring);
    						
    						if (arrayString.equals(nameSubstring)) {
    							System.out.println("We got a match!");
    							System.out.println("This is our guess?: " + roomCards.get(i).name);
    							System.out.println("\n\n\n");
    							System.out.println(roomCards.get(i).name + " =? " + nameSubstring);

    							roomCards.get(i).probability = 0;   						
    							printOut(8000);
    							roomNotFound = false;
    							roomFoundIndex = i;
    							break;
    						}
    					}	 
    					
    					for (int i = 0; i < playerCards.size(); i++) {
     						String arrayString = playerCards.get(i).name.replaceAll("\\s+","");
    						System.out.println(playerCards.get(i).name + " =? " + nameSubstring);
    						
    						if (arrayString.equals(nameSubstring)) {
    							System.out.println("We got a match!");
    							System.out.println("\n\n\n");
    							System.out.println(playerCards.get(i).name + " =? " + nameSubstring);
    							System.out.println("This is our guess?: " + playerCards.get(i).name);
    							playerCards.get(i).probability = 0;   						
    							printOut(8000);
    							playerNotFound = false;
    							playerFoundIndex = i;
    							break;
    						}
    					   						
    					}
    					
    					for (int i = 0; i < weaponCards.size(); i++) {
      						String arrayString = weaponCards.get(i).name.replaceAll("\\s+","");
    						System.out.println(weaponCards.get(i).name + " =? " + nameSubstring);
    						
    						if (arrayString.equals(nameSubstring)) {
    							System.out.println("We got a match!");
    							System.out.println("This is our guess?: " + weaponCards.get(i).name);
    							System.out.println("\n\n\n");
    							System.out.println(weaponCards.get(i).name + " =? " + nameSubstring);
    							weaponCards.get(i).probability = 0;   						
    							printOut(8000);
    							weaponNotFound = false;
    							weaponFoundIndex = i;
    							break;
    						}   							
    					}
    				}
    			}
    			tempIndex++;
    		}
//    		/* If none of the probabilities are changed -- then we have gotten a winning hand! */
    		if (playerNotFound && weaponNotFound && roomNotFound && (illegalParsing != true) && !firstCharacterGuess && !firstWeaponGuess && !firstRoomGuess) {
    			
     			System.out.println("\n\n\n\n\n");   				    			
    		    System.out.println("We got a winning hand!");
    		    System.out.println("This is the sub:");
    		    System.out.println(nameSubstring);
     			System.out.println("\n\n\n\n\n");
     			 	
     			if (lockedInCharacter) {
     				if (!lockedInWeapon && lockedInRoom) {
     					lockInWeapon();
     				}
     				else if (lockedInWeapon && !lockedInRoom) {
     					lockInRoom();
     				}
     				else if (!lockedInWeapon && !lockedInRoom) {
    					lockInWeapon();
    					lockInRoom();
     				}
     			}
     			else if (lockedInWeapon) {
     				if (!lockedInCharacter && lockedInRoom) {
     					lockInPlayer();
     				}
     				else if (lockedInCharacter && !lockedInRoom) {
     					lockInRoom();
     				}
     				else if (!lockedInCharacter && !lockedInRoom) {
     					lockInPlayer();
     					lockInRoom();
     				}
     			}
     			else if (lockedInRoom) {
     				if (!lockedInWeapon && lockedInCharacter) {
     					lockInWeapon();
     				}
     				else if (lockedInWeapon && !lockedInCharacter) {
     					lockInPlayer();
     				}
     				else if (!lockedInWeapon && !lockedInCharacter) {
     					lockInWeapon();
     					lockInPlayer();
     				}
     			}
     			else {
     				lockInPlayer();
     				lockInWeapon();
     				lockInRoom();
     			}

	
     			printOut(80000);
     			
    		}	
    		accuseCheck();	
    	}
    	
    	private void lockInWeapon() {
    		lockedInWeapon = true;
     		lockedInNoteWeapon = weaponCards.get(0);
     		weaponCards.get(0).probability = 200;   		
    	}
    	
    	private void lockInRoom() {
    		int roomFoundIndex = -1;
    		lockedInRoom = true;
     					
     		String name = roomIn.toString();	
     		for (int i = 0; i < roomCards.size(); i++) {
     			if (roomCards.get(i).name.equals(name)) {
     				roomFoundIndex = i;
     				break;
     			}
     		}
     		lockedInNoteRoom = roomCards.get(roomFoundIndex);
     		roomCards.get(roomFoundIndex).probability = 200;    		
    	}
    	
    	private void lockInPlayer() {
    		lockedInCharacter = true;
    		lockedInNoteCharacter = playerCards.get(0);
    		playerCards.get(0).probability = 200;
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
        System.out.println("\nRoom Hand Array: \n");
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

	static int R = 100;
    static int D = 101;
	public static int GRID[][] = {
			{R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R} , 
			{R , R , R , R , R , R , R , 2 , 2 , 2 , R , R , R , R , 5 , 5 , 5 , R , R , R , R , R , R , R} , 
			{R , R , R , R , R , R , 2 , 2 , R , R , R , R , R , R , R , R , 5 , 5 , R , R , R , R , R , R} , 
			{R , R , R , R , R , R , 2 , 2 , R , R , R , R , R , R , R , R , 5 , 5 , R , R , R , R , R , R} , 
			{R , R , R , R , R , R , 2 , 2 , R , R , R , R , R , R , R , R , 5 , 5 , D , R , R , R , R , R} , 
			{R , R , R , R , R , R , 2 , 2 , D , R , R , R , R , R , R , D , 5 , 5 , 6 , R , R , R , R , R} , 
			{R , R , R , R , D , R , 2 , 2 , R , R , R , R , R , R , R , R , 5 , 5 , 6 , 6 , 6 , 6 , 6 , R} , 
			{R , 1 , 1 , 1 , 1 , 1 , 2 , 2 , R , D , R , R , R , R , D , R , 4 , 7 , 7 , 6 , 6 , 6 , 6 , R} , 
			{R , 1 , 1 , 1 , 1 , 1 , 1 , 3 , 3 , 3 , 3 , 3 , 3 , 4 , 4 , 4 , 4 , 7 , R , R , R , R , R , R} , 
			{R , R , R , R , R , 1 , 1 , 3 , 3 , 3 , 3 , 3 , 3 , 4 , 4 , 4 , 7 , 7 , D , R , R , R , R , R} , 
			{R , R , R , R , R , R , R , R , 17, 3 , R , R , R , R , R , 7 , 7 , 7 , R , R , R , R , R , R} ,
			{R , R , R , R , R , R , R , R , 17, 17, R , R , R , R , R , 7 , 7 , 7 , R , R , R , R , R , R} ,
			{R , R , R , R , R , R , R , D , 17, 17, R , R , R , R , R , 7 , 7 , 7 , R , R , R , R , D , R} ,
			{R , R , R , R , R , R , R , R , 17, 17, R , R , R , R , R , 7 , 8 , 8 , 8 , 8 , 8 , 10, 10, R} ,
			{R , R , R , R , R , R , R , R , 17, 17, R , R , R , R , R , 9 , 9 , 8 , R , R , D , R , R , R} , // 14
			{R , R , R , R , R , R , D , R , 17, 17, R , R , R , R , R , 9 , 9 , R , R , R , R , R , R , R} ,
			{R , 16, 16, 16, 16, 16, 16, 16, 14, 14, R , R , D , R , R , 9 , 9 , D , R , R , R , R , R , R} ,
			{R , 15, 16, 16, 16, 16, 16, 16, 14, 14, 14, 14, 13, 13, 13, 9 , 9 , R , R , R , R , R , R , R} ,
			{R , 15, 15, 15, 15, 15, 15, 15, 15, R , R , D , D , R , R , 9 , 9 , 9 , R , R , R , R , R , R} ,
			{R , R , R , R , R , R , D , 15, 15, R , R , R , R , R , R , 11, 11, 12, 12, 12, 12, 12, 12, R} ,
			{R , R , R , R , R , R , R , 15, 15, R , R , R , R , R , D , 11, 12, 12, 12, 12, 12, 12, 12, R} ,
			{R , R , R , R , R , R , R , 15, 15, R , R , R , R , R , R , 11, 12, D , R , R , R , R , R , R} ,
			{R , R , R , R , R , R , R , 15, 15, R , R , R , R , R , R , 11, 12, R , R , R , R , R , R , R} ,
			{R , R , R , R , R , R , R , 15, 15, R , R , R , R , R , R , 11, 12, R , R , R , R , R , R , R} ,
			{R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R , R} , 
	};

//	static Coordinates[] doorCoordinates = {
//			// Cellar
//			new Coordinates(12, 16),
//			new Coordinates(4, 6),
//			new Coordinates(8, 5),
//			new Coordinates(9, 7),
//			new Coordinates(14, 7),
//			new Coordinates(15, 5),
//			// Conservatory
//			new Coordinates(18, 4),
//			new Coordinates(18, 9),
//			new Coordinates(),
//			new Coordinates(),
//			new Coordinates(),
//			new Coordinates(),
//			new Coordinates(),
//			new Coordinates(),
//			new Coordinates(),
//			new Coordinates(),
//			new Coordinates(),
//			new Coordinates(),
//
//	}
        
}
