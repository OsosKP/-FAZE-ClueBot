package cluedo_game;

import java.util.ArrayList;

/**
 * This is a modified circularly linked list.
 * We will need to add players to the end and pull from the front (as with a queue)
 * We don't need addFirst, insertAfter, insertBefore, or other things like that
 */
public class Tokens {
    private Token first;
    private Token last;
    private int numberOfPlayers;
    private int playerNumberIndex;

    // Constructor
    public Tokens(){
        this.first = null;
        this.last = null;
        numberOfPlayers = 0;
    }

    /*
    Temporary methods to create and access all 6 characters to test spawn points and printouts
    Some form of these methods will eventually move to the GameEngine class
    The UI class will be accessing these methods, so update that when we make the switch
     */
    public void setDefaultPlayerList() {
        Token white = new Token(0, 9, "White", 0);
        Token green = new Token(0, 14, "Green", 1);
        Token mustard = new Token(17, 0, "Mustard", 2);
        Token peacock = new Token(6, 23, "Peacock", 3);
        Token plum = new Token(19, 23, "Plum", 4);
        Token scarlet = new Token(24, 7, "Scarlet", 5);

        white.setNext(green);
        green.setNext(mustard);
        mustard.setNext(peacock);
        peacock.setNext(plum);
        plum.setNext(scarlet);
        scarlet.setNext(white);

        this.first = white;
        this.last = scarlet;

        numberOfPlayers = 6;
    }
    // TODO: Change who spawns in this if you want to debug
    public void setDebugPlayerList() {
        Token mustard = new Token(17, 0, "Mustard", 0);
        Token peacock = new Token(6, 23, "Peacock", 1);
        mustard.setNext(peacock);
        peacock.setNext(mustard);
        first = mustard;
        last = peacock;
        numberOfPlayers = 2;
    }

    /**
     * Checks if player is active
     * @param name lower case name of requested player
     * @return true or false
     */
    public boolean isPlayerInPlayerList(String name){
        Token curr = first;
        do {
            if (name.equals(curr.getName().toLowerCase()))
                return true;
            curr = curr.next();
        } while (curr != first);
        return false;
    }

    public int getIndexOfPlayerByName(String name){
        Token curr = first;
        do {
            if (name.equals(curr.getName().toLowerCase()))
                return curr.getPlayerNumber();
            curr = curr.next();
        } while (curr != first);
        return -1;
    }

    // Accessors
    public Token getFirst() {
        return first;
    }
    public Token getLast() {
        return last;
    }
    public Token getPlayerByIndex(int index) {
        Token curr = first;
        do {
            if (curr.getPlayerNumber() == index)
                return curr;
            curr = curr.next();
        } while (curr != first);
        return null;
    }
    public int getNumberOfPlayers() {return numberOfPlayers; }
    public boolean isEmpty(){ return numberOfPlayers == 0; }

    // Mutators
    public void setFirst(Token t){
        first = t;
    }
    public void addPlayer(Token t){
        if(this.isEmpty()) {
            this.first = t;
            this.last = this.first;
        }
        // Set last to point to new token
        this.last.setNext(t);
        // Now set last AS the new token
        this.last = this.last.next();
        // Reset new token's next pointer to the first (making this circularly linked)
        this.last.setNext(this.first);
        this.numberOfPlayers++;
    }
    public String removePlayer(Token t){
        String name = t.getName();
        // If the token we're looking for is the first player, remove first
        if(t == first){
            first = t.next();
        }
        else
        {
            Token prev;
            Token curr = t.next();
            do{
                prev = curr;
                curr = curr.next();
                // If curr == t, we get rid of it
                // If prev == t, we have completed a loop and haven't found the Token
            } while(curr != t && prev != t);
            if(prev == t)
                throw new PlayerNotFoundException();
            curr.setInGame(false);
        }
        this.numberOfPlayers--;
        return name;
    }
    // advanceTurn lets us skip players who are "out" without removing them
        // (We want to keep them for questioning)
    public Token advanceTurn(Token t){
        t = t.next();
        while (true) {
            if (!t.getInGame())
                t = t.next();
            else
                break;
        }
        return t;
    }


    /* prints only one iteration of the list -- used for testing  */
    public void printList(){
    	System.out.println("\n\n----Printing ONE iteration of the Linked List of Players----");
    	Token printMe = this.first;
    	boolean run = true;
    	
    	while (run) {
    		System.out.print("| " + printMe.getName() + " |");
    		printMe = printMe.next();
    		if (printMe == last.next()) {
    			run = false;
    		}
    	}
    	System.out.print("\n\n");
    }
    
    /* Need to pulling the characterNames from the list -- needed for the help menu */
    public ArrayList<String> returnCharacterNames(){
    	ArrayList<String> characterNames = new ArrayList<>();
    	
    	Token currentToken = this.first;
    	boolean run = true;
    	
    	while (run) {
    		characterNames.add(currentToken.getName());
    		currentToken = currentToken.next();
    		if (currentToken == last.next()) {
    			run = false;
    		}
    	}
    	
    	return characterNames;
    }
    
    /* Pulling the usernames from the list -- needed for the help menu */
    public ArrayList<String> returnUsernames(){
    	ArrayList<String> userNames = new ArrayList<>();
    	
    	Token currentToken = this.first;
    	boolean run = true;
    	
    	while (run) {
    		userNames.add(currentToken.getPlayerName());
    		currentToken = currentToken.next();
    		if (currentToken == last.next()) {
    			run = false;
    		}
    	}
    	
    	return userNames;
    }
}
