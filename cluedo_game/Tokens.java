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
