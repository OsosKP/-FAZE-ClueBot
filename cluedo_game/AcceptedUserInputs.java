// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

import javax.swing.*;
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
        When having just entered a room, the user may leave, take the secret passage or exit.
        If guessing, that's a different list of commands and is handled differently.
         */
        roomNavigation.add("passage");
        roomNavigation.add("exit");
        roomNavigation.add("guess");
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
        Rooms are inputted when the player is attempting to solve, NOT guess
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

    /**
     * This method handles user input checking for FloorSquares, EntrySquares and in Rooms -
     *  It does NOT handle cases where user is guessing or solving. That will be a different panel and logic.
     * @param p Player who entered command
     * @param in Command entered by player
     * @return Boolean designating whether the command was found in the associated list of valid commands
     */
    public String checkForValidEntry(Token p, String in){
        boolean matchFound = false;
        switch(p.getLocationAsString()){
            case "floor":
                for (String s : floorNavigation) {
                    if (s.equals(in))
                        // If command was valid, move the player
                        return GameLogic.PlayerEntry.PlayerMovementHandler(p, in);
                }
                break;
            case "entry":
                for (String s : entryChoices){
                    if (s.equals(in))
                        matchFound = true;
                }
                break;
            case "room":
                for (String s : roomNavigation){
                    if (s.equals(in))
                        matchFound = true;
                }
            default:
                JOptionPane.showConfirmDialog(null, "Something Went Wrong...");
                break;

        }
        return "Please Enter a Valid Command!";
    }

}
