package cluedo_game;

/**
 * Card objects hold representations of all characters, rooms and weapons to
 *  form the 'deck'. These objects are SEPARATE from all game logic involving
 *      Tokens, Rooms and Weapons
 * Usage Notes:
 *  name
     *  toString returns name with capitals and white spaces
     *      Use toString for printing and output
     *  getName returns name without those - all lower case, no spaces
     *      Use getName for references within the code
 *  Traversal
    *   Because of how the Card class is set up, when traversing through the
    *       deck you should use:
    *           do { ... } while (card != null);
    *   This is the safest implementation for the Card class's functionality
 */
public class Card {
    String name;
    /*
        reference is a 2-part numerical identifier
        reference[0] refers to the card's type:
            0 - Character
            1 - Room
            2 - Weapon
        reference[1] refers to this card's place in that 'type' category

        This variable is set when Deck is constructed and cannot be changed.
            That way when we split the cards up to the envelope and player hands,
                we can reference the cards in the same way at all times.
     */
    final int[] reference;
    /*
        type is only used for indexing, not printing, so all types are declared
            in all lower case with no whitespaces.
        Because of this, we don't need a toSimpleString method for this variable
     */
    final String type;

    public Card(String name, int ref0, int ref1, String type) {
        this.name = name;
        this.reference = new int[2];
        this.reference[0] = ref0;
        this.reference[1] = ref1;
        this.type = type;
    }

    /**
     * Returns a card's name as string
     *  This does not account for case or white space! User getName for that!
     * @return string
     */
    public String toString() {
        return this.name;
    }

    /*
    I'm just using this to set outputted utility decks as all lower-case
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Converts name into lower case with no whitespaces
     *  User toString for printing, getName for referencing!
     * @return CONVERTED AND FORMATTED string 'name'
     */
    public String getName() {
        return this.name.replaceAll("\\s+", "").toLowerCase();
    }

    public int[] getReference() {
        return reference;
    }
    public String getReferenceAsString(){
        return "[" + this.reference[0] + ", " + this.reference[1] + "]";
    }

    public String getType() {
        return type;
    }

    public boolean isLast(){
        return (this.reference[0] == 2 && this.reference[1] == 5);
    }

    public Card next(Deck deck){
        int[] newIndex = new int[2];

        switch (this.reference[0]){
            case 0:
                if (this.reference[1] == 5)
                    return deck.getCardByReference(1, 0);
                else
                    newIndex[0] = 0;
                break;
            case 1:
                if (this.reference[1] == 8)
                    return deck.getCardByReference(2, 0);
                else
                    newIndex[0] = 1;
                break;
            case 2:
                if (this.reference[1] == 5)
                    return null;
                else
                    newIndex[0] = 2;
                break;
        }
        newIndex[1] = this.reference[1] + 1;

        return deck.getCardByReference(newIndex[0], newIndex[1]);
    }
}