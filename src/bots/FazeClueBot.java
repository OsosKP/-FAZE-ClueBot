// [FAZE]ClueBot

package bots;

import gameengine.*;
import java.util.ArrayList;
import java.util.List;

public class FazeClueBot implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the board or the player objects
    // It may only inspect the state of the board and the player objects

    private Player player;
    private PlayersInfo playersInfo;
    private Map map;
    private Dice dice;
    private Log log;
    private Deck deck;

    public FazeClueBot (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
        // Store locations to all doors on the board
        storeAllDoors();
    }
    
//    
//    Note: All inputs are sanitized with .trim() and .toLowerCase()
//
    
    public String getName() {
        return "FazeClueBot"; // must match the class name
    }

    public String getCommand() {
        // Possible inputs: quit|done|roll|passage|notes|cheat|question|log|accuse|help
        return "done";
    }

    public String getMove() {
        // Add your code here
        String move;
        if (currentRoute == null)
            beginPing();
        move = findNextCommandMovingToRoom();

        return move;
    }

    public String getSuspect() {
        // Add your code here
        // TODO: George - just put 'yourNoteCardForSuspects.get(0). Do for all 3
        // Input must return true for Names.isSuspect(String input)
        // Possible inputs: u|d|l|r
        return getSuspect();
    }

    public String getWeapon() {
    	// Input must return true for Names.isWeapon(String input)
        return Names.WEAPON_NAMES[0];
    }

    public String getRoom() {
    	// Input must return true for Names.isRoomCard(String input)
        return Names.ROOM_NAMES[0];
    }

    public String getDoor() {
        // Add your code here
        return "0";
    }

    public String getCard(Cards matchingCards) {
        // Add your code here
        // TODO: This might be placeholder, but the format is correct - Kelsey
        Query ourQuery = new Query(getSuspect(), getWeapon(), getRoom());
        // Possible input: 1|2|3|4
        // Input needs to return true: (at least one card).hasName(String input) in matchingCards
        return matchingCards.get(ourQuery).toString();
    }

    public void notifyResponse(Log response) {
    	//See notes for this method
        // Add your code here
    }

    /*
        Our Code
     */

    /*
        New variables and methods
     */

    // Map coords: 24 x 23
    private List<Integer[]> currentRoute = new ArrayList<>();
    private Room currentRoomDestination;
    private Integer[] currentPosition = new Integer[2];
    private List<Integer[]> doors = new ArrayList<>();
    private String[] doorsReference = new String[18];
    private List<NoteCard> notes = new ArrayList<>();
    private boolean movingToRoom = false;
    private boolean inRoom = false;
    private boolean guessedInRoom = false;
    private boolean timeToAccuse = false;
    private int currentRouteProgressIndex = 0;

    /*
        Movement
     */
    private Integer[] getCurrentPosition() {
        return currentPosition;
    }
    private int getCurrentRow() {
        return currentPosition[0];
    }
    private int getCurrenColumn() {
        return currentPosition[1];
    }
    private void setCurrentPosition(Coordinates coords) {
        this.currentPosition[0] = coords.getRow();
        this.currentPosition[1] = coords.getCol();
    }

    private String takeTurn() {
        // Update Log
        //TODO: Josh

        /* Check if we have an accusation
                If so, we will call shouldIVisitRoom.
                This method checks timeToAccuse and returns the boolean:
                    (we need to go to the cellar) AND (we are going to the cellar)
                If timeToAccuse is false, shouldIVisitRoom just looks for the room
                    it is given and decides whether or not we know it's in the
                    murder envelope. If we don't know, we'll visit it.
         */
        timeToAccuse = checkIfIShouldGoToCellar();

        // If not, see if we're en route to a room
        if (movingToRoom) {
            // Make sure we still want to go to this room
            if(shouldIVisitRoom(currentRoomDestination))
                // If so, continue
                return findNextCommandMovingToRoom();
            else {
                movingToRoom = false;
                // If not, re-ping
                beginPing();
                // And start moving
                return findNextCommandMovingToRoom();
            }
        }
        // If we begin our turn in a room
        if (inRoom) {
            // Check if we've already guessed in this room. If we have...
            if (guessedInRoom)
                // TODO: Exit - Is this right?
                    // We need to choose a door (or just pick 0), exit, then ping
                getDoor();
            // Otherwise, make a guess
            // TODO: George: This is where we do our guessing
            else
                return "guess";
        }
        /*
            If we have gotten to this point without returning:
                We're not in a room (if we were, we just exited) and not on our way
                to one. So we need to ping to find the closest room we want and redo all this.
         */
        beginPing();
        return takeTurn();
    }

    /*
        When we have a path to follow to a room, this method gets the next step to
            do and sends the appropriate command
     */
    private String findNextCommandMovingToRoom() {
        currentRouteProgressIndex++;
        /*
            The next square in our route will be different in either the row
                or column index. Find which one it is (it should only be one) and
                set our next move command accordingly.
         */
        int rowDiff = currentRoute.get(currentRouteProgressIndex-1)[0] -
                currentRoute.get(currentRouteProgressIndex)[0];
        int colDiff = currentRoute.get(currentRouteProgressIndex-1)[1] -
                currentRoute.get(currentRouteProgressIndex)[1];

        switch (rowDiff) {
            case -1:
                return "u";
            case 1:
                return "d";
            default:
                break;
        }
        switch (colDiff) {
            case -1:
                return "l";
            case 1:
                return "r";
            default:
                break;
        }
        return "error";
    }

    public boolean isDoor(int row, int col, int counter) {
        boolean found = false;
        for (Integer[] index : doors) {
            if (index[0].equals(row) && index[1].equals(col)) {
                found = true;
            }
            counter++;
        }
        return found;
    }

    /*
        Begin a ping in all directions looking for a room to move to
     */
    private void beginPing() {
        if (map.isValidMove(new Coordinates(currentPosition[0], currentPosition[1]), "l"))
            ping(currentPosition[0], currentPosition[1]-1,
                    new ArrayList<>(), new boolean[][] {{false}, {false}});
        if (map.isValidMove(new Coordinates(currentPosition[0], currentPosition[1]), "u"))
            ping(currentPosition[0]-1, currentPosition[1],
                    new ArrayList<>(), new boolean[][] {{false}, {false}});
        if (map.isValidMove(new Coordinates(currentPosition[0], currentPosition[1]), "r"))
            ping(currentPosition[0], currentPosition[1]+1,
                    new ArrayList<>(), new boolean[][] {{false}, {false}});
        if (map.isValidMove(new Coordinates(currentPosition[0], currentPosition[1]), "d"))
            ping(currentPosition[0]+1, currentPosition[1],
                    new ArrayList<>(), new boolean[][] {{false}, {false}});
    }

    /*
        This is a recursive method that finds the quickest path to a room we want to visit
            Each call updates its own route, and returns it when a suitable room is found
               explored is sent as an empty array of booleans, one for each board square
     */
    private void ping(int row, int col, ArrayList<Integer[]> route, boolean[][] explored) {
        // Counter is supposed to help the bot choose which exit to take from a room
        // Not using this yet because I'm just having the bot always pick exit 0
        int counter = 0;

        // If we've come to a square that we haven't been to yet...
        if (!explored[row][col]) {
            // Mark this square as explored
            explored[row][col] = true;
            // Add this square to the current route
            route.add(new Integer[]{row, col});
            // If we have found a door to a room
            if (isDoor(row, col, counter)) {
                // If we haven't eliminated the possibility of this room
                    // being in the murder envelope
                if (shouldIVisitRoom(map.getRoom(doorsReference[counter]))) {
                    // Set the current route as the route to this room
                    currentRoute = route;
                    // Denote that we are moving to a room currently
                    movingToRoom = true;
                    // Set the room we're moving towards
                    // We will use this variable to check at the beginning of
                        // each turn if we should still be moving toward that room
                    currentRoomDestination = map.getRoom(doorsReference[counter]);
                    // End recursive calls
                    return;
                }
            }
        }
        // Check each direction to ensure we can move that way
        // If so, recursive call to continue in that direction
        if (map.isValidMove(new Coordinates(row, col-1), "l"))
            ping(row, col-1, route, explored);
        if (map.isValidMove(new Coordinates(row-1, col), "u"))
            ping(row-1, col, route, explored);
        if (map.isValidMove(new Coordinates(row, col+1), "r"))
            ping(row, col+1, route, explored);
        if (map.isValidMove(new Coordinates(row+1, col), "d"))
            ping(row+1, col, route, explored);
    }

    // TODO: George: Change this probability of 25 to whatever you want
    /*
        shouldIVisitRoom returns a boolean when passed a room as a parameter.
            If it decides we want to visit that room, it returns true.
            It is called from ping() and determines where we go next.
     */
    private boolean shouldIVisitRoom(Room room) {
        // If we need to accuse, just check if we're going to the cellar
        if (timeToAccuse)
            return room.hasName("Cellar");
        // Otherwise, decide if the given room is worth visiting
        for (NoteCard nc : notes) {
            if (nc.name.equals(room.toString())) {
                if (nc.probability > 25)
                    return true;
                break;
            }
        }
        return false;
    }

    // TODO: George: The skeleton of this is right, but update based on your probability needs
    public boolean checkIfIShouldGoToCellar() {
        int counter = 0;
        // Check status of characters guessed
        for (int i=0; i<6; i++) {
            if (notes.get(i).probability > 0)
                // Count how many characters we're unsure about
                counter++;
        }
        // If we're unsure about more than 1, no guessing
        if (counter > 1)
            return false;

        counter = 0;
        // Do the same method for weapons
        for (int i=0; i<6; i++) {
            if (notes.get(i+6).probability > 0)
                // Count how many weapons we're unsure about
                counter++;
        }
        if (counter > 1)
            return false;

        // Do the same method for rooms
        for (int i=0; i<9; i++) {
            if (notes.get(i+12).probability > 0)
                // Count how many rooms we're unsure about
                counter++;
        }
        // At this point if we only have 1 room to guess, we're ready.
        // Otherwise, this evaluates to false and we don't guess
        return (counter == 1);
    }

    /*
        Guessing
     */
    // TODO: An idea I have for storing guess cards. We could change this. - Kelsey
    public static class NoteCard {
        private String name;
        private int probability;
        private boolean guessed;
        private String whoHas;

        public NoteCard(String name) {
            this.name = name;
            probability = 100;
            guessed = false;
            whoHas = "";
        }

        private boolean isGuessed() {
            return guessed;
        }

        private int getProbability() {
            return probability;
        }

        private void setShown() {
            probability = 0;
        }

        private void setMaybeShown() {
            if (probability > 25)
                probability -= 25;
            else
                probability = 0;
        }
    }

    private void setNoteCards() {
        for (int i=0; i<6; i++)
            notes.add(new NoteCard(Names.SUSPECT_NAMES[i]));
        for (int i=0; i<6; i++)
            notes.add(new NoteCard(Names.WEAPON_NAMES[i]));
        for (int i=0; i<9; i++)
            notes.add(new NoteCard(Names.ROOM_NAMES[i]));
    }

    private List<NoteCard> getNoteCards() {
        return notes;
    }

    private NoteCard getNoteCardByName(String name) {
        for (NoteCard nc : notes) {
            if (nc.name.equals(name))
                return nc;
        }
        return null;
    }

    /*
        Auxiliary and storage methods
     */

    private void storeAllDoors() {
        doors.add(new Integer[]{4,6});
        doors.add(new Integer[]{8,5});
        doors.add(new Integer[]{9,7});
        doors.add(new Integer[]{14,7});
        doors.add(new Integer[]{15,5});
        doors.add(new Integer[]{18,4});
        doors.add(new Integer[]{18,9});
        doors.add(new Integer[]{22,12});
        doors.add(new Integer[]{17,16});
        doors.add(new Integer[]{20,14});
        doors.add(new Integer[]{17,21});
        doors.add(new Integer[]{11,18});
        doors.add(new Integer[]{12,18});
        doors.add(new Integer[]{14,20});
        doors.add(new Integer[]{6,19});
        doors.add(new Integer[]{6,15});
        doors.add(new Integer[]{7,12});
        doors.add(new Integer[]{12,17});
    }
}
