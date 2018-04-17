// [FAZE]ClueBot

package bots;

import gameengine.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Bot1 implements BotAPI {

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

    public Bot1 (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
        setMainTrack();
    }

    public String getName() {
        return "Bot1"; // must match the class name
    }

    public String getCommand() {
        // Add your code here
        return "done";
    }

    public String getMove() {
        // Add your code here
        return "r";
    }

    public String getSuspect() {
        // Add your code here
        return Names.SUSPECT_NAMES[0];
    }

    public String getWeapon() {
        // Add your code here
        return Names.WEAPON_NAMES[0];
    }

    public String getRoom() {
        // Add your code here
        return Names.ROOM_NAMES[0];
    }

    public String getDoor() {
        // Add your code here
        return "1";
    }

    public String getCard(Cards matchingCards) {
        // Add your code here
        return matchingCards.get().toString();
    }

    public void notifyResponse(Log response) {
        // Add your code here
    }



    /*
        New variables and methods
     */

    /*
        mainTrack
            This will hold the main course our bot should take around the cellar
            It will follow the course, search for rooms to check, and stray from
                the course to get to a room if needed.
            Once done, it will either look for a close room or return to the course
     */

    // Map coords: 24 x 23
    private List<Integer[]> mainTrack = new ArrayList<>();
    private List<Integer[]> currentRoute = new ArrayList<>();
    private Integer[] currentPosition = new Integer[2];
    private List<Integer[]> doors = new ArrayList<>();
    private String[] doorsReference = new String[18];
    private List<NoteCard> notes = new ArrayList<>();
    private boolean movingToRoom = false;
    private boolean inRoom = false;
    private boolean guessedInRoom = false;
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
        if (movingToRoom) {
            return findNextCommandMovingToRoom();
        }

        if (inRoom && !guessedInRoom) {
            return "guess";
        }

        if (inRoom && guessedInRoom) {
            return "exit 0";
        }

        if (checkIfIShouldGoToCellar())
            //TODO: Direct ping to cellar only
            return "placeholder";

        // If we're not in a room and we need to find one to go to, ping and start over
        beginPing();
        takeTurn();

        // I don't think it should every come to this
        return "placeholder";

    }

    private String findNextCommandMovingToRoom() {
        currentRouteProgressIndex++;
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


    private void setMainTrack() {
        mainTrack.add(new Integer[]{8,8});
        mainTrack.add(new Integer[]{8,9});
        mainTrack.add(new Integer[]{8,10});
        mainTrack.add(new Integer[]{8,11});
        mainTrack.add(new Integer[]{8,12});
        mainTrack.add(new Integer[]{8,13});
        mainTrack.add(new Integer[]{8,14});
        mainTrack.add(new Integer[]{8,15});
        mainTrack.add(new Integer[]{8,16});

        mainTrack.add(new Integer[]{9,16});
        mainTrack.add(new Integer[]{10,16});
        mainTrack.add(new Integer[]{11,16});
        mainTrack.add(new Integer[]{12,16});
        mainTrack.add(new Integer[]{13,16});
        mainTrack.add(new Integer[]{14,16});
        mainTrack.add(new Integer[]{15,16});
        mainTrack.add(new Integer[]{16,16});

        mainTrack.add(new Integer[]{17,16});
        mainTrack.add(new Integer[]{17,15});
        mainTrack.add(new Integer[]{17,14});
        mainTrack.add(new Integer[]{17,13});
        mainTrack.add(new Integer[]{17,12});
        mainTrack.add(new Integer[]{17,11});
        mainTrack.add(new Integer[]{17,10});
        mainTrack.add(new Integer[]{17,9});
        mainTrack.add(new Integer[]{17,8});

        mainTrack.add(new Integer[]{17,8});
        mainTrack.add(new Integer[]{16,8});
        mainTrack.add(new Integer[]{15,8});
        mainTrack.add(new Integer[]{14,8});
        mainTrack.add(new Integer[]{13,8});
        mainTrack.add(new Integer[]{12,8});
        mainTrack.add(new Integer[]{11,8});
        mainTrack.add(new Integer[]{10,8});
        mainTrack.add(new Integer[]{9,8});
    }

    private List<Integer[]> getPathToRoom(String roomName) {
        ArrayList<Integer[]> path = new ArrayList<>();
        switch (roomName) {
            case "Kitchen":
                path.add(new Integer[]{8, 8});
                path.add(new Integer[]{8, 7});
                path.add(new Integer[]{8, 6});
                path.add(new Integer[]{8, 5});
                path.add(new Integer[]{8, 4});
                path.add(new Integer[]{7, 4});
                path.add(new Integer[]{6, 4});
                break;
            case "Ballroom":
                path.add(new Integer[]{8, 9});
                path.add(new Integer[]{7, 9});
                path.add(new Integer[]{8, 14});
                path.add(new Integer[]{7, 14});
                break;
            case "Conservatory":
                path.add(new Integer[]{8, 16});
                path.add(new Integer[]{7, 16});
                path.add(new Integer[]{6, 16});
                path.add(new Integer[]{5, 16});
                path.add(new Integer[]{5, 17});
                path.add(new Integer[]{5, 18});
                path.add(new Integer[]{4, 18});
                break;
            case "Billiard Room":
                path.add(new Integer[]{9, 16});
                path.add(new Integer[]{9, 17});
                path.add(new Integer[]{9, 18});
                break;
            case "Library":
                path.add(new Integer[]{16, 16});
                path.add(new Integer[]{16, 17});
                break;
            case "Study":
                path.add(new Integer[]{17, 16});
                path.add(new Integer[]{18, 16});
                path.add(new Integer[]{18, 17});
                path.add(new Integer[]{19, 17});
                path.add(new Integer[]{20, 17});
                path.add(new Integer[]{21, 17});
                break;
            case "Hall":
                path.add(new Integer[]{17, 12});
                path.add(new Integer[]{18, 12});
                break;
            case "Lounge":
                path.add(new Integer[]{17, 8});
                path.add(new Integer[]{17, 7});
                path.add(new Integer[]{17, 6});
                path.add(new Integer[]{18, 6});
                path.add(new Integer[]{19, 6});
                break;
            case "Dining Room":
                path.add(new Integer[]{12, 8});
                path.add(new Integer[]{12, 7});
                break;
            case "Cellar":
                path.add(new Integer[]{17, 12});
                path.add(new Integer[]{16, 12});
                break;
        }
        return path;
    }

    private void beginPing() {
        if (map.isValidMove(new Coordinates(currentPosition[0], currentPosition[1]), "l"))
            ping(currentPosition[0], currentPosition[1]-1, null);
        if (map.isValidMove(new Coordinates(currentPosition[0], currentPosition[1]), "u"))
            ping(currentPosition[0]-1, currentPosition[1], null);
        if (map.isValidMove(new Coordinates(currentPosition[0], currentPosition[1]), "r"))
            ping(currentPosition[0], currentPosition[1]+1, null);
        if (map.isValidMove(new Coordinates(currentPosition[0], currentPosition[1]), "d"))
            ping(currentPosition[0]+1, currentPosition[1], null);
    }

    private void ping(int row, int col, @Nullable ArrayList<Integer[]> route) {
        int counter = 0;

        route.add(new Integer[]{row,col});

        if (isDoor(row, col, counter)) {
            if(shouldIVisitRoom(map.getRoom(doorsReference[counter]))) {
                currentRoute = route;
                movingToRoom = true;
                return;
            }
        }

        if (map.isValidMove(new Coordinates(row, col), "l"))
            ping(row, col-1, route);
        if (map.isValidMove(new Coordinates(row, col), "u"))
            ping(row-1, col, route);
        if (map.isValidMove(new Coordinates(row, col), "r"))
            ping(row, col+1, route);
        if (map.isValidMove(new Coordinates(row, col), "d"))
            ping(row+1, col, route);
    }

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
    // TODO: An idea I have for storing guess cards. We could change this.
    public static class NoteCard {
        private String name;
        private int probability;
        private boolean guessed;

        public NoteCard(String name) {
            this.name = name;
            probability = 100;
            guessed = false;
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
            probability -= 25;
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

    private boolean shouldIVisitRoom(Room room) {
        for (NoteCard nc : notes) {
            if (nc.name.equals(room.toString())) {
                if (nc.probability > 25)
                    return true;
                break;
            }
        }
        return false;
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
