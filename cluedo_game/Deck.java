package cluedo_game;

import java.util.ArrayList;

public class Deck {
    private static final ArrayList<ArrayList<String>> deck= new ArrayList<>();

    public Deck(){
        deck.add(new ArrayList<>());
            deck.get(0).add("White");
            deck.get(0).add("Green");
            deck.get(0).add("Mustard");
            deck.get(0).add("Scarlet");
            deck.get(0).add("Peacock");
            deck.get(0).add("Plum");

        deck.add(new ArrayList<>());
            deck.get(1).add("Kitchen");
            deck.get(1).add("Ball Room");
            deck.get(1).add("Conservatory");
            deck.get(1).add("Dining Room");
            deck.get(1).add("Billiard Room");
            deck.get(1).add("Library");
            deck.get(1).add("Lounge");
            deck.get(1).add("Hall");
            deck.get(1).add("Study");

        deck.add(new ArrayList<>());
            deck.get(2).add("Candlestick");
            deck.get(2).add("Dagger");
            deck.get(2).add("Gun");
            deck.get(2).add("Pipe");
            deck.get(2).add("Rope");
            deck.get(2).add("Wrench");
    }

    /**
     * Converts a string to all lower case and eliminates white spaces
     * @param in string to convert
     * @return converted string
     */
    public String simpleString(String in){
        return in.replaceAll("\\s+","").toLowerCase();
    }

    /**
     * @param index index of which card type to get string from
     *              0 = Players
     *              1 = Rooms
     *              3 = Weapons
     * @return simpleString version of card type strings
     */
    public ArrayList<String> getCardTypeString(int index){
        ArrayList<String> simplifiedSubDeck = deck.get(index);
        for(String st : simplifiedSubDeck) {
            st = simpleString(st);
        }
        return simplifiedSubDeck;
    }

    public static ArrayList<ArrayList<String>> getDeck(){
        return deck;
    }
    public static ArrayList<String> getSubDeck(int index){
        return deck.get(index);
    }
}
