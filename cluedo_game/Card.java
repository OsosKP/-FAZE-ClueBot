package cluedo_game;

public class Card {
    String name;
    final int[] reference;
    final String type;

    public Card(String name, int ref0, int ref1, String type) {
        this.name = name;
        this.reference = new int[2];
        this.reference[0] = ref0;
        this.reference[1] = ref1;
        this.type = type;
    }

    /**
     * Converts a card's name to all lower case and eliminates white spaces
     *
     * @return converted string
     */
    public String toString() {
        return this.name.replaceAll("\\s+", "").toLowerCase();
    }

    /*
    I'm just using this to set outputted utility decks as all lower-case
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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