package cluedo_game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Data structure which holds Card objects as a modified ArrayList
 * See Card usage notes
 */

public class Deck {
    // Once all cards are dealt, any leftovers remain in the publicDeck
    private ArrayList<ArrayList<Card>> publicDeck = new ArrayList<>();
    // Full deck of all cards for referencing
    private ArrayList<ArrayList<Card>> fullDeck = new ArrayList<>();
    private ArrayList<Card> murderEnvelope = new ArrayList<>();
    private Card first;

    /*
    Generic constructor for the primary publicDeck of all cards
     */
    public Deck() {
        publicDeck.add(new ArrayList<>());
        try {
            publicDeck.get(0).add(new Card("White", 0, 0, "token", CardImages.getBufferedWhite()));
            publicDeck.get(0).add(new Card("Green", 0, 1, "token", CardImages.getBufferedGreen()));
            publicDeck.get(0).add(new Card("Mustard", 0, 2, "token", CardImages.getBufferedMustard()));
            publicDeck.get(0).add(new Card("Scarlet", 0, 3, "token", CardImages.getBufferedScarlet()));
            publicDeck.get(0).add(new Card("Peacock", 0, 4, "token", CardImages.getBufferedPeacock()));
            publicDeck.get(0).add(new Card("Plum", 0, 5, "token", CardImages.getBufferedPlum()));

            publicDeck.add(new ArrayList<>());
            publicDeck.get(1).add(new Card("Kitchen", 1, 0, "room", CardImages.getBufferedKitchen()));
            publicDeck.get(1).add(new Card("Ball Room", 1, 1, "room", CardImages.getBufferedBallroom()));
            publicDeck.get(1).add(new Card("Conservatory", 1, 2, "room", CardImages.getBufferedConservatory()));
            publicDeck.get(1).add(new Card("Dining Room", 1, 3, "room", CardImages.getBufferedDiningroom()));
            publicDeck.get(1).add(new Card("Billiard Room", 1, 4, "room", CardImages.getBufferedBilliardroom()));
            publicDeck.get(1).add(new Card("Library", 1, 5, "room", CardImages.getBufferedLibrary()));
            publicDeck.get(1).add(new Card("Lounge", 1, 6, "room", CardImages.getBufferedLounge()));
            publicDeck.get(1).add(new Card("Hall", 1, 7, "room", CardImages.getBufferedHall()));
            publicDeck.get(1).add(new Card("Study", 1, 8, "room", CardImages.getBufferedStudy()));
            
            publicDeck.add(new ArrayList<>());
            publicDeck.get(2).add(new Card("Candlestick", 2, 0, "weapon", CardImages.getBufferedCandlestick()));
            publicDeck.get(2).add(new Card("Dagger", 2, 1, "weapon", CardImages.getBufferedDagger()));
            publicDeck.get(2).add(new Card("Pistol", 2, 2, "weapon", CardImages.getBufferedPistol()));
            publicDeck.get(2).add(new Card("Pipe", 2, 3, "weapon", CardImages.getBufferedPipe()));
            publicDeck.get(2).add(new Card("Rope", 2, 4, "weapon", CardImages.getBufferedRope()));
            publicDeck.get(2).add(new Card("Wrench", 2, 5, "weapon", CardImages.getBufferedWrench()));

            first = publicDeck.get(0).get(0);
            // Populate full deck
            for (int i = 0; i < 3; i++) {
                fullDeck.add(new ArrayList<>());
                fullDeck.get(i).addAll(publicDeck.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Constructor for debugging
    public Deck(int i) {
        murderEnvelope = new ArrayList<>();
        try {
            murderEnvelope.add(new Card("White", 0, 0, "token", ImageIO.read(new File("src/characterCards/White.png"))));
            murderEnvelope.add(new Card("Study", 1, 8, "room", ImageIO.read(new File("src/roomCards/study.jpeg"))));
            murderEnvelope.add(new Card("Pipe", 2, 3, "weapon", ImageIO.read(new File("src/weaponCards/Pipe.png"))));
        } catch (Exception e) {
            System.out.println("Card images weren't loaded properly");
        }
    }

    //
    //  Accessors
    //
    public Card getCardByReference(int[] reference) {
        return publicDeck.get(reference[0]).get(reference[1]);
    }

    public Card getCardByReference(int ref1, int ref2) {
        return publicDeck.get(ref1).get(ref2);
    }

    /**
     * @param index index of which card type to get string from
     *              0 = Players
     *              1 = Rooms
     *              2 = Weapons
     * @return simpleString version of card type strings
     */
    public ArrayList<Card> getSubDeckSimplified(int index) {
        ArrayList<Card> simplifiedSubDeck = publicDeck.get(index);
        for (Card c : simplifiedSubDeck) {
            c.setName(c.getName());
        }
        return simplifiedSubDeck;
    }

    public ArrayList<Card> getSubDeck(int index) {
        return publicDeck.get(index);
    }

    public ArrayList<ArrayList<Card>> getDeckSimplified() {
        ArrayList<ArrayList<Card>> simplified = publicDeck;
        for (int i = 0; i < 3; i++) {
            for (Card c : simplified.get(i)) {
                c.setName(c.getName());
            }
        }
        return simplified;
    }

    public ArrayList<ArrayList<Card>> getPublicDeck() {
        return publicDeck;
    }

    public ArrayList<Card> getFullPublicDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            deck.addAll(publicDeck.get(i));
        }
        return deck;
    }

    /*
    Methods to retrieve a specific card when given a name
     */
    public Card getPlayerCardByName(String name) {
        name = AcceptedUserInputs.simpleString(name);
        for (Card c : fullDeck.get(0)) {
            if (AcceptedUserInputs.simpleString(c.name).equals
                    (AcceptedUserInputs.simpleString(name)))
                return c;
        }
        return null;
    }

    public Card getRoomCardByName(String name) {
        for (Card c : fullDeck.get(1)) {
            if (AcceptedUserInputs.simpleString(c.name).equals
                    (AcceptedUserInputs.simpleString(name)))
                return c;
        }
        return null;
    }

    public Card getWeaponCardByName(String name) {
        name = AcceptedUserInputs.simpleString(name);
        for (Card c : fullDeck.get(2)) {
            if (AcceptedUserInputs.simpleString(c.name).equals
                    (AcceptedUserInputs.simpleString(name)))
                return c;
        }
        return null;
    }

    public ArrayList<Card> getMurderEnvelope() {
        return murderEnvelope;
    }

    /*
            Returns the total size of the publicDeck, including all three sublists
         */
    public int totalSize() {
        return publicDeck.get(0).size() + publicDeck.get(1).size() + publicDeck.get(2).size();
    }

    /*
        Returns size of a given sublist by index
     */
    public int size(int index) {
        return publicDeck.get(index).size();
    }

    //
    //  Mutators and Game methods
    //
    /*
        This is an inefficient remove method, because it has to search through
            a sublist for a given reference. This seems unavoidable to me, since
            the publicDeck is changing variably to suit the number of players.
        At most, in the current implementation, it will have to loop 8 times.
            I figure that's not too terrible of a situation to be in.
     */
    public Card remove(int[] reference) {
        Card card = null;
        int index = 0;
        for (Card c : publicDeck.get(reference[0])) {
            if (c.reference == reference) {
                card = c;
                break;
            }
            index++;
        }
        if (card == null)
            throw new CardNotFoundException();
        publicDeck.get(reference[0]).remove(index);

        return card;
    }

    public void fillMurderEnvelope() {
        Random rand = new Random();
        int randIndex;

        for (int i = 0; i < 3; i++) {
            randIndex = rand.nextInt(6 + (3 * i % 2));
            murderEnvelope.add(publicDeck.get(i).remove(randIndex));
        }
    }

    public void dealHands(Tokens list) {
        // Put all cards in one ArrayList for easier traversal
        ArrayList<Card> fullDeck = new ArrayList<>();
        fullDeck.addAll(publicDeck.get(0));
        fullDeck.addAll(publicDeck.get(1));
        fullDeck.addAll(publicDeck.get(2));

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
                this.totalSize() % list.getNumberOfPlayers()) / list.getNumberOfPlayers());

        // Generate a random seed
        Random rand = new Random();
        int randValue;

        Card card;
        /*
            Iterate through each player as many times as needed to distribute
                cards up to handSize
         */
        for (int i = 0; i < handSize; i++) {
            for (int j = 0; j < list.getNumberOfPlayers(); j++) {
                // Random value bounded by number of cards left
                randValue = rand.nextInt(fullDeck.size());

                card = fullDeck.get(randValue);
                // Add that randomized card to given player's hand
                list.getPlayerByIndex(j).addCardToHand(card);
                // Remove card from publicDecks
                this.remove(card.reference);
                fullDeck.remove(randValue);
            }
        }
    }
}