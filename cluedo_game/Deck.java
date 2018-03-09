package cluedo_game;

import java.util.ArrayList;

public class Deck {
    private ArrayList<ArrayList<Card>> deck = new ArrayList<>();
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
            c.setName(c.toString());
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
                c.setName(c.toString());
            }
        }
        return simplified;
    }

    public ArrayList<ArrayList<Card>> getDeck() {
        return deck;
    }
}