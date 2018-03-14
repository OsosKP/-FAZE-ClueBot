package cluedo_game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Data structure which holds Card objects as a modified ArrayList
 * See Card usage notes
 */

public class Deck {
    private ArrayList<ArrayList<Card>> deck = new ArrayList<>();
    private ArrayList<Card> murderEnvelope = new ArrayList<>();
    private Card first;

    /*
    Generic constructor for the primary deck of all cards
     */
    public Deck() {
        deck.add(new ArrayList<>());
        deck.get(0).add(new Card("White", 0, 0, "token"));
        deck.get(0).add(new Card("Green", 0, 1, "token"));
        deck.get(0).add(new Card("Mustard", 0, 2, "token"));
        deck.get(0).add(new Card("Scarlet", 0, 3, "token"));
        deck.get(0).add(new Card("Peacock", 0, 4, "token"));
        deck.get(0).add(new Card("Plum", 0, 5, "token"));

        deck.add(new ArrayList<>());
        deck.get(1).add(new Card("Kitchen", 1, 0, "room"));
        deck.get(1).add(new Card("Ball Room", 1, 1, "room"));
        deck.get(1).add(new Card("Conservatory", 1, 2, "room"));
        deck.get(1).add(new Card("Dining Room", 1, 3, "room"));
        deck.get(1).add(new Card("Billiard Room", 1, 4, "room"));
        deck.get(1).add(new Card("Library", 1, 5, "room"));
        deck.get(1).add(new Card("Lounge", 1, 6, "room"));
        deck.get(1).add(new Card("Hall", 1, 7, "room"));
        deck.get(1).add(new Card("Study", 1, 8, "room"));

        deck.add(new ArrayList<>());
        deck.get(2).add(new Card("Candlestick", 2, 0, "weapon"));
        deck.get(2).add(new Card("Dagger", 2, 1, "weapon"));
        deck.get(2).add(new Card("Gun", 2, 2, "weapon"));
        deck.get(2).add(new Card("Pipe", 2, 3, "weapon"));
        deck.get(2).add(new Card("Rope", 2, 4, "weapon"));
        deck.get(2).add(new Card("Wrench", 2, 5, "weapon"));

        this.first = deck.get(0).get(0);

    }
    /*
    Specialized constructor for real decks - hands, murder envelope, public deck
     */
    public Deck(ArrayList<ArrayList<Card>> deck){
        this.deck = deck;
    }

    //
    //  Accessors
    //
    public Card getCardByReference(int[] reference){
        return deck.get(reference[0]).get(reference[1]);
    }
    public Card getCardByReference(int ref1, int ref2){
        return deck.get(ref1).get(ref2);
    }

    /**
     * @param index index of which card type to get string from
     *              0 = Players
     *              1 = Rooms
     *              2 = Weapons
     * @return simpleString version of card type strings
     */
    public ArrayList<Card> getSubDeckSimplified(int index) {
        ArrayList<Card> simplifiedSubDeck = deck.get(index);
        for (Card c : simplifiedSubDeck) {
            c.setName(c.getName());
        }
        return simplifiedSubDeck;
    }

    public ArrayList<Card> getSubDeck(int index) {
        return deck.get(index);
    }

    public ArrayList<ArrayList<Card>> getDeckSimplified() {
        ArrayList<ArrayList<Card>> simplified = deck;
        for (int i = 0; i < 3; i++) {
            for (Card c : simplified.get(i)) {
                c.setName(c.getName());
            }
        }
        return simplified;
    }

    public ArrayList<ArrayList<Card>> getDeck() {
        return deck;
    }

    public ArrayList<Card> getMurderEnvelope() {
        return murderEnvelope;
    }

    /*
            Returns the total size of the deck, including all three sublists
         */
    public int totalSize(){
        return deck.get(0).size() + deck.get(1).size() + deck.get(2).size();
    }
    /*
        Returns size of a given sublist by index
     */
    public int size(int index){
        return deck.get(index).size();
    }

    //
    //  Mutators and Game methods
    //
    /*
        This is an inefficient remove method, because it has to search through
            a sublist for a given reference. This seems unavoidable to me, since
            the deck is changing variably to suit the number of players.
        At most, in the current implementation, it will have to loop 8 times.
            I figure that's not too terrible of a situation to be in.
     */
    public Card remove(int[] reference) {
        Card card = null;
        int index = 0;
        for(Card c : deck.get(reference[0])){
            if(c.reference == reference){
                card = c;
                break;
            }
            index++;
        }
        if (card == null)
            throw new CardNotFoundException();
        deck.get(reference[0]).remove(index);

        return card;
    }

    public void fillMurderEnvelope(){
        Random rand = new Random();
        int randIndex;

        for(int i=0; i<3; i++){
            randIndex = rand.nextInt(6 + (3 * i%2));
            murderEnvelope.add(deck.get(i).remove(randIndex));
        }
    }

    public void dealHands(Tokens list){
        // Put all cards in one ArrayList for easier traversal
        ArrayList<Card> fullDeck = new ArrayList<>();
            fullDeck.addAll(deck.get(0));
            fullDeck.addAll(deck.get(1));
            fullDeck.addAll(deck.get(2));

        /*
            Find size of each player's hand
                Number of cards remaining (usually 18) minus number of cards
                    that won't divide evenly into number of players, then
                    divided by number of players.
                Example: 18 cards, 4 players.
                    18%4 = 2, 18-2=16 so 4 players get 4 cards.
                    Remaining 2 cards are public
         */
        int handSize = ((this.totalSize() -
                this.totalSize()%list.getNumberOfPlayers())/list.getNumberOfPlayers());

        // Generate a random seed
        Random rand = new Random();
        int randValue;

        Card card;
        /*
            Iterate through each player as many times as needed to distribute
                cards up to handSize
         */
        for(int i=0; i<handSize; i++){
            for(int j=0; j<list.getNumberOfPlayers(); j++){
                // Random value bounded by number of cards left
                randValue = rand.nextInt(fullDeck.size());

                card = fullDeck.get(randValue);
                // Add that randomized card to given player's hand
                list.getPlayerByIndex(j).addCardToHand(card);
                // Remove card from decks
                this.remove(card.reference);
                fullDeck.remove(randValue);
            }
        }
    }
}