package cluedo_game;

import java.util.ArrayList;

public class AcceptedUserInputs {
    /**
     * These input lists are checked depending on what kind of square the user is on
     */
    private final ArrayList<String> floor = new ArrayList<>();  // The player is moving on a general floor square
    private final ArrayList<String> entry = new ArrayList<>();  // The player is in the entry to a room and may enter
    private final ArrayList<String> room = new ArrayList<>();   // The player is in a room and making a guess or exiting
    private final ArrayList<String> makingGuess = new ArrayList<>();    // The player has decided to make a guess
    private final ArrayList<String> cellar = new ArrayList<>(); // The player is attempting to solve


    public AcceptedUserInputs(){
        floor.add("up");
        floor.add("down");
        floor.add("left");
        floor.add("right");
        /*
        When at an EntrySquare, user will be asked if they want to enter the room
        They can say 'yes' or 'no' only
        If no, or if user came to that square from a room (i.e. just exited), they will be able to enter movement
        If yes, user is taken to the room
         */
        entry.add("yes");
        entry.add("no");
        /*
        If in a room, the player is allowed to make a guess - but the room guess has to be the one they're in
         */
        room.add("");
        room.add("");
        room.add("");
        room.add("");


    }
}
