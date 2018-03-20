package cluedo_game;

public class CardTester {
    public static void main(String[] args) {
        Deck deck = new Deck();

        deck.fillMurderEnvelope();

        System.out.println("Murder Envelope:");
        for(Card c : deck.getMurderEnvelope())
            System.out.println(c.getName());

        Tokens list = new Tokens(1);

        deck.dealHands(list);

        Token curr = list.getFirst();
        for(int i=0; i<list.getNumberOfPlayers(); i++){
            System.out.println("");
            System.out.println(curr.getName());
            for(Card c : curr.getHand())
                System.out.println(c.getName());
            curr = curr.next();
        }

        System.out.println("\nLeftovers:");
        for(int i=0; i<3; i++) {
            for (Card c : deck.getSubDeck(i))
                System.out.println(c.getName());
        }
    }
}
