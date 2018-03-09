package cluedo_game;

public class CardTester {
    public static void main(String[] args) {
        Deck deck = new Deck();
        Card card = deck.getCardByReference(0, 0);

        do {
            System.out.println(card.getName() + " - " +
                    card.getReferenceAsString() + " - " +
                    card.getType());
            card = card.next(deck);
        } while (card != null);
    }
}
