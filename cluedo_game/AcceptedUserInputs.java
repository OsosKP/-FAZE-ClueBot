// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

import java.util.ArrayList;

public class AcceptedUserInputs {
    /**
     * These input lists are checked depending on what kind of square the user is on
     */
    // The player is moving on a general floorNavigation square
    private final ArrayList<String> floorNavigation = new ArrayList<>();
    // The player is in the entryChoices to a room and may enter
    private final ArrayList<String> entryChoices = new ArrayList<>();
    // Making a guess or exiting
    private final ArrayList<String> weaponChoices = new ArrayList<>();
    private final ArrayList<String> characterChoices = new ArrayList<>();
    // Room guesses are only allowed if they player is solving
    private final ArrayList<String> roomChoices = new ArrayList<>();
    // For 'exit', 'guess', etc.
    private final ArrayList<String> roomNavigation = new ArrayList<>();

    public AcceptedUserInputs(){
        floorNavigation.add("up");
        floorNavigation.add("down");
        floorNavigation.add("left");
        floorNavigation.add("right");
        /*
        When at an EntrySquare, user may enter the room
        They can say 'enter' or 'move' only
        If 'move', or if user came to that square from a room (i.e. just exited), they will be able to enter movement
        If 'enter', user is taken to the room
         */
        entryChoices.add("enter");
        entryChoices.add("move");
        /*
        If in a room, the player is allowed to make a guess - but the room guess has to be the one they're in
         */
        weaponChoices.add("pistol");
        weaponChoices.add("dagger");
        weaponChoices.add("candlestick");
        weaponChoices.add("wrench");
        weaponChoices.add("rope");
        weaponChoices.add("pipe");

        characterChoices.add("mustard");
        characterChoices.add("scarlet");
        characterChoices.add("peacock");
        characterChoices.add("plum");
        characterChoices.add("white");
        characterChoices.add("green");

        /*
        Rooms are inputted when the player is attempting to solve
         */
        roomChoices.add("kitchen");
        roomChoices.add("ballroom");
        roomChoices.add("conservatory");
        roomChoices.add("study");
        roomChoices.add("hall");
        roomChoices.add("library");
        roomChoices.add("billiard room");
        roomChoices.add("dining room");
        roomChoices.add("lounge");

        roomNavigation.add("passage");
        roomNavigation.add("exit");
        roomNavigation.add("guess");
    }

    public ArrayList<String> getFloorNavigation() {
        return floorNavigation;
    }
    public ArrayList<String> getEntryChoices() {
        return entryChoices;
    }
    public ArrayList<String> getWeaponChoices() {
        return weaponChoices;
    }
    public ArrayList<String> getCharacterChoices() {
        return characterChoices;
    }
    public ArrayList<String> getRoomChoices() {
        return roomChoices;
    }
    public ArrayList<String> getRoomNavigation() {
        return roomNavigation;
    }

    public boolean checkForValidUserInputNavigation(Token p, String in){
        boolean matchFound = false;
        // Perform these checks if the player is not in a room
        if(p.getInRoom() == null) {
            switch (p.getSquareOn().toString()) {
                case "floor":
                    for (String s : floorNavigation) {
                        if (s.equals(in))
                            matchFound = true;
                    }
                    break;
                case "entry":
                    for (String s : entryChoices) {
                        if (s.equals(in))
                            matchFound = true;
                    }
                default:
                    System.out.println("ERROR in player location");
            }
        }
        // If the player is in a room and NOT guessing, we come here
        else{
            for(String s : roomNavigation){
                if(s.equals(in))
                    matchFound = true;
            }
        }
        return matchFound;
    }

}
