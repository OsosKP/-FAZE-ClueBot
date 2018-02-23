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
    private static final ArrayList<String> floorNavigation = new ArrayList<>();
    // Making a guess or exiting
    private static final ArrayList<String> weaponChoices = new ArrayList<>();
    private static final ArrayList<String> characterChoices = new ArrayList<>();
    // Room guesses are only allowed if they player is solving
    private static final ArrayList<String> roomChoices = new ArrayList<>();
    // For 'exit', 'guess', 'passage'
    private static final ArrayList<String> roomNavigation = new ArrayList<>();

    public static void setAcceptedUserInputs(){
        floorNavigation.add("up");
        floorNavigation.add("down");
        floorNavigation.add("left");
        floorNavigation.add("right");

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

    public static ArrayList<String> getFloorNavigation() {
        return floorNavigation;
    }
    public static ArrayList<String> getWeaponChoices() {
        return weaponChoices;
    }
    public static ArrayList<String> getCharacterChoices() {
        return characterChoices;
    }
    public static ArrayList<String> getRoomChoices() {
        return roomChoices;
    }
    public static ArrayList<String> getRoomNavigation() {
        return roomNavigation;
    }

    /**
     * This method handles user input checking for FloorSquares and in Rooms -
     *  It does NOT handle cases where user is guessing or solving. That will be a different panel and logic.
     * @param p Player who entered command
     * @param in Command entered by player
     * @return Boolean designating whether the command was found in the associated list of valid commands
     */
    public static boolean checkForValidEntry(Token p, String in){
        // Change input to lower case and erase whitespaces to check against allowed entries
        in = in.replaceAll("\\s+","").toLowerCase();

        boolean result = false;

        if (in.equals("done"))
            result = true;

        switch(p.getLocationAsString()){
            case "floor":
                for (String s : floorNavigation) {
                    if (s.equals(in) || s.startsWith(in))
                        // If command was valid, move the player
                        result = true;
                }
                break;
            case "room":
                for (String s : roomNavigation){
                    if (s.equals(in) || s.startsWith(in))
                        result = true;
                }
                break;
            default:
                JOptionPane.showConfirmDialog(null, "Something Went Wrong...");
                break;

        }
        return result;
    }

    public static ArrayList<Integer> getRoomExits(Room room){
        ArrayList<Integer> exits = new ArrayList<>();
        int i = 1;
        for (FloorSquare square : room.getExits())
            exits.add(i++);
        return exits;
    }

    public static boolean roomExitCheck(Room room, int choice){
        try{
            room.getExits().get(choice).getPosition();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
