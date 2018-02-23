package cluedo_game;

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
    public void setPlayerList() {
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

    /**
     * Checks if player is active
     * @param name lower case name of requested player
     * @return true or false
     */
    public boolean isPlayerInPlayerList(String name){
        for (int i=0; i<numberOfPlayers; i++){
            if (name.equals(this.getPlayerByIndex(i).getName().toLowerCase()))
                return true;
        }
        return false;
    }



    // Accessors
    public Token getFirst() {
        return first;
    }
    public Token getLast() {
        return last;
    }
    public Token getPlayerByIndex(int index) {
        if (first.getPlayerNumber() == index)
            return first;

        Token curr = first.next();
        while(curr.getPlayerNumber() != index) {
            curr = curr.next();
            if (curr == first)
                throw new PlayerNotFoundException();
        }
        return curr;
    }
    public int getNumberOfPlayers() {return numberOfPlayers; }
    public boolean isEmpty(){ return numberOfPlayers == 0; }

    // Mutators
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
            prev.setNext(curr.next());
        }
        this.numberOfPlayers--;
        return name;
    }
    // This might be a good quick method to call to move to the next player?
    public void advanceTurn(Token t){
        t = t.next();
    }
}
