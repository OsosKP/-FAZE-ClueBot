// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardBuilder {
    /*
    Since BoardSquare is an interface, we can declare an entire array
    of BoardSquare 'objects' and then turn them into our square types as needed.
     */
    private BoardSquare[][] board = new BoardSquare[25][24];

    /* Will store all the players in the game */
//    playerList players = new playerList();
    private Tokens players;

    /* This will allow is to access the rooms on the fly */
    private Room Ballroom;
    private Room Conservatory;
    private Room DiningRoom;
    private Room BilliardRoom;
    private Room Library;
    private Room Lounge;
    private Room Hall;
    private Room Kitchen;
    private Room Study;
    private Room Cellar;

    public BoardBuilder(Tokens players) {
        /* Calling classes to create board */
        this.players = players;
        this.addBarriersAndSpawnPoints();
        this.addWalls();
        this.addEntrySquares();
        this.addFloorSquares();
        this.createRooms();
        // Split this up and took the assignments out of the EntrySquare constructor
        this.connectEntrySquaresToRooms();

        for(int i=0; i<25; i++){
            for(int j=0; j<24; j++) {
                board[i][j].setGeography(this);
            }
        }
    }

    public Tokens getPlayerList() {
        return players;
    }

    //
    // Accessors
    //
    public Room getBallroom() { return Ballroom;} //Returns Ballroom
    public Room getConservatory() {return Conservatory;}//Returns conservatory
    public Room getDiningRoom() {return DiningRoom;}//Returns Dining room
    public Room getBilliardRoom() {return BilliardRoom;}//Return billiard room
    public Room getLibrary() {return Library;}//Returns library
    public Room getLounge() {return Lounge;}//Returns lounge
    public Room getHall() {return Hall;}//Returns hall
    public Room getKitchen() {return Kitchen;}//Return kitchen
    public Room getStudy() {return Study;}//Return study
    public Room getCellar() {return Cellar;}//Return Cellar

    // Get square with x and y as separate values
    public BoardSquare getSquare(int yLoc, int xLoc) {
        return board[yLoc][xLoc];
    }
    // Get square with x and y as an array
    public BoardSquare getSquare(int[] coordinates){
        return board[coordinates[0]][coordinates[1]];
    }


    /**
     * addEntrySquare
     * This method assigns the appropriate board squares as entries
     * to the corresponding rooms.
     */
    public void addEntrySquares(){
        // Add Kitchen Entry
        board[6][4] = new EntrySquare(6, 4, 1);
        // Add 4 Ballroom Entries
        board[5][8] = new EntrySquare(5, 8, 1);
        board[7][9] = new EntrySquare(7, 9, 2);
        board[7][14] = new EntrySquare(7, 14, 3);
        board[5][15] = new EntrySquare(5, 15, 4);
        // Add Conservatory Entry
        board[4][18] = new EntrySquare(4, 18, 1);
        // Add Dining Room Entries
        board[12][7] = new EntrySquare(12, 7, 1);
        board[15][6] = new EntrySquare(15, 6, 2);
        // Add Billiard Room Entries
        board[9][18] = new EntrySquare(9, 18, 1);
        board[12][22] = new EntrySquare(12, 22, 2);
        // Add Library Entries
        board[16][17] = new EntrySquare(16, 17, 1);
        board[14][20] = new EntrySquare(14, 20, 2);
        // Add Lounge Entry
        board[19][6] = new EntrySquare(19, 6, 1);
        // Add Hall Entries
        board[18][11] = new EntrySquare(18, 11, 1);
        board[18][12] = new EntrySquare(18, 12, 2);
        board[20][14] = new EntrySquare(20, 14, 3);
        // Add Study Entry
        board[21][17] = new EntrySquare(17, 21, 1);
        // Add Cellar Entry. The Token enters the Cellar to attempt a guess
        board[16][12] = new EntrySquare(16, 12, 1);
    }

    /**
     * addBarriers
     * The board is a 25x24 matrix, but there are extra squares at the top
     * to spawn White and Green. To resolve this I made the board 24x25 and
     * I'm making everything WallSquares except those spawn points.
     *
     * There are also some barriers along the sides and bottom where there
     * are no room squares and no spawn points (see Documentation). This
     * implementation will look the same since we're making our Room Squares
     * walls so they're impassable.
     */
    public void addBarriersAndSpawnPoints(){
        int i;  // Indexing loops
        /*
        playerNumberIndex is set to result of Tokens' method getIndexOfPlayerByName()
        This method iterates through the list looking for the index number of the given name
        This index is return and used for assigning spawn points correctly
         */
        int playerNumberIndex = -1;


        // Loop to assign top edge barrier squares
        for(i = 0; i < 24; i++){
            // board[0][9] and board[0][14] are both spawn points
            if(i == 9 && players.isPlayerInPlayerList("white")) {
                playerNumberIndex = players.getIndexOfPlayerByName("white");
                board[0][i] = new FloorSquare(0, i, players.getPlayerByIndex(playerNumberIndex));
                players.getPlayerByIndex(playerNumberIndex).setSquareOn(board[0][9]);
                System.out.println("Player: " + players.getPlayerByIndex(playerNumberIndex).getName() + "\tLocation: " +
                        players.getPlayerByIndex(playerNumberIndex).getSquareOn().getPositionAsString());
            }
            else if(i == 14 && players.isPlayerInPlayerList("green")) {
                playerNumberIndex = players.getIndexOfPlayerByName("green");
                board[0][i] = new FloorSquare(0, i, players.getPlayerByIndex(playerNumberIndex));
                players.getPlayerByIndex(playerNumberIndex).setSquareOn(board[0][14]);
                System.out.println("Player: " + players.getPlayerByIndex(playerNumberIndex).getName() + "\tLocation: " +
                        players.getPlayerByIndex(playerNumberIndex).getSquareOn().getPositionAsString());
            }
            else
                board[0][i] = new WallSquare(0, i);
        }

        // Loop to assign left edge barrier squares
        for(i = 0; i < 25 ; i++){
            // board[17][0] is a spawn point
            if(i == 17 && players.isPlayerInPlayerList("mustard")) {
                playerNumberIndex = players.getIndexOfPlayerByName("mustard");
                board[17][0] = new FloorSquare(i, 0, players.getPlayerByIndex(playerNumberIndex));
                players.getPlayerByIndex(playerNumberIndex).setSquareOn(board[17][0]);
                System.out.println("Player: " + players.getPlayerByIndex(playerNumberIndex).getName() + "\tLocation: " +
                        players.getPlayerByIndex(playerNumberIndex).getSquareOn().getPositionAsString());
            }
            else
                board[i][0] = new WallSquare(i, 0);
        }

        // Loop to assign right edge barrier squares
        for(i = 0; i < 25 ; i++){
            // board[6][23] and board[19][23] are both spawn points
            if(i == 6 && players.isPlayerInPlayerList("peacock")) {
                playerNumberIndex = players.getIndexOfPlayerByName("peacock");
                board[i][23] = new FloorSquare(i, 23, players.getPlayerByIndex(playerNumberIndex));
                players.getPlayerByIndex(playerNumberIndex).setSquareOn(board[i][23]);
                System.out.println("Player: " + players.getPlayerByIndex(playerNumberIndex).getName() + "\tLocation: " +
                        players.getPlayerByIndex(playerNumberIndex).getSquareOn().getPositionAsString());
            }
            else if(i == 19 && players.isPlayerInPlayerList("plum")) {
                playerNumberIndex = players.getIndexOfPlayerByName("plum");
                board[i][23] = new FloorSquare(i, 23, players.getPlayerByIndex(playerNumberIndex));
                players.getPlayerByIndex(playerNumberIndex).setSquareOn(board[i][23]);
                System.out.println("Player: " + players.getPlayerByIndex(playerNumberIndex).getName() + "\tLocation: " +
                        players.getPlayerByIndex(playerNumberIndex).getSquareOn().getPositionAsString());
            }
            else
                board[i][23] = new WallSquare(i, 23);
        }

        // Loop to assign bottom edge barrier squares
        for(i = 0; i < 24; i++){
            // board[24][7] is a spawn point
            if(i == 7 && players.isPlayerInPlayerList("scarlet")) {
                playerNumberIndex = players.getIndexOfPlayerByName("scarlet");
                board[24][i] = new FloorSquare(24, i, players.getPlayerByIndex(playerNumberIndex));
                players.getPlayerByIndex(playerNumberIndex).setSquareOn(board[24][i]);
                System.out.println("Player: " + players.getPlayerByIndex(playerNumberIndex).getName() + "\tLocation: " +
                        players.getPlayerByIndex(playerNumberIndex).getSquareOn().getPositionAsString());
            }
            else
                board[24][i] = new WallSquare(24, i);
        }

        // Two impassable squares at the top - see Documentation
        board[1][6] = new WallSquare(1, 6);
        board[1][17] = new WallSquare(1, 17);
    }

    /**
     * addWalls
     * This method uses loops to create all of the rooms on the board.
     * We are declaring room tiles as WallSquares and making the impassable,
     * and the room itself will be a separate object to which a token is transported.
     */
    public void addWalls(){
        int i, j;   // Indexing loops

        // Kitchen
        for(i = 1; i < 7; i++) {
            for (j = 1; j < 6; j++) {
                // Ensure this isn't an entry square
                if(!(i == 6 && j == 4))
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Ballroom
        for(i = 2; i < 8; i++){
            for(j = 8; j < 16 ; j++) {
                // Ensure this isn't an entry square
                if (!((i == 5 && (j == 8 || j == 15)) ||
                        (i == 7 && (j == 9 || j == 14))))
                board[i][j] = new WallSquare(i, j);
            }
        }
        for(j = 10; j < 14; j++)
            board[1][j] = new WallSquare(1, j);

        // Conservatory
        for(i = 1; i < 6; i++){
            for(j = 18; j < 23 ; j++){
                // Ensure this isn't an entry square
                // This time we also account for Conservatory's weird shape
                if(!(j == 18 && (i == 4 || i == 5)))
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Dining Room
        for(j = 1; j<5; j++){
            board[9][j] = new WallSquare(9, j);
        }
        for(i = 10; i < 16; i++){
            for(j = 1; j < 8; j++){
                if(!((i == 15 && j == 6) || (i == 12 && j == 7)))
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Cellar
        for(i = 10; i < 17; i++){
            for(j = 10; j < 15; j++){
                if(!(i == 16 && j == 12))
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Billiard Room
        for(i = 8; i < 13; i++){
            for(j = 18; j < 23; j++){
                if(!((i == 9 && j == 18) || (i == 12 && j == 22)))
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Library
        for(i = 15; i < 18; i++){
            board[i][17] = new WallSquare(i, 17);
        }
        for(i = 14; i < 19; i++){
            for(j = 18; j < 23; j++){
                if(!((i == 16 && j == 17) || (i == 14 && j == 20)))
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Lounge
        for(i = 19; i < 24; i++){
            for(j = 1; j < 7; j++){
                if(!(i == 19 && j == 6))
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Hall
        for(i = 18; i < 24; i++){
            for(j = 9; j < 15; j++){
                if(!((i == 18 && (j == 11 || j == 12)) || (i == 20 && j == 14)))
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Study
        for(i = 21; i < 24; i++){
            for(j = 17; j < 23; j++){
                if(!(i == 21 && j == 17))
                    board[i][j] = new WallSquare(i, j);
            }
        }
    }

    /**
     * addFloorSquares
     * Loops through the entire board. Every square that isn't a WallSquare
     * or an EntrySquare is made into a passable FloorSquare.
     */
    public void addFloorSquares(){
        for(int i = 1; i < 25; i++){
            for(int j = 1; j < 24; j++){
                if(!(board[i][j] instanceof WallSquare || board[i][j] instanceof EntrySquare
                        || board[i][j] instanceof FloorSquare)) {
                    board[i][j] = new FloorSquare(i, j);
                }
            }
        }
    }

    public void createRooms() {
        /* Creating the Rooms with Multiple Entrances*/
        /* Will be used to store the entrances for the individual rooms */
        ArrayList<EntrySquare> entrances = new ArrayList<>();
        /* Will be used to store the FloorSquares to which the rooms exit*/
        ArrayList<FloorSquare> exits = new ArrayList<>();

        ArrayList<int[]> ballroomCoords = new ArrayList<>(Arrays.asList(new int[]{4,10}, new int[]{4,11}, new int[]{4,12}, new int[]{3,10}, new int[]{3,11}, new int[]{3,12}));
        ArrayList<int[]> kitchenCoords = new ArrayList<>(Arrays.asList(new int[]{3,2}, new int[]{3,1}, new int[]{3,3}, new int[]{2,2}, new int[]{2,1}, new int[]{2,3}));
        ArrayList<int[]> conservatoryCoords = new ArrayList<>(Arrays.asList(new int[]{2,21}, new int[]{2,20}, new int[]{2,22}, new int[]{1,21}, new int[]{1,20}, new int[]{1,22}));
        ArrayList<int[]> diningCoords = new ArrayList<>(Arrays.asList(new int[]{11,3}, new int[]{11,2}, new int[]{11,4}, new int[]{11,1}, new int[]{11,5}, new int[]{11,6}));
        ArrayList<int[]> billiardCoords = new ArrayList<>(Arrays.asList(new int[]{9,20}, new int[]{9,21}, new int[]{9,22}, new int[]{11,20}, new int[]{11,21}, new int[]{11,22}));
        ArrayList<int[]> libraryCoords = new ArrayList<>(Arrays.asList(new int[]{15,20}, new int[]{15,19}, new int[]{15,21}, new int[]{16,19}, new int[]{16,21}, new int[]{17,20}));
        ArrayList<int[]> loungeCoords = new ArrayList<>(Arrays.asList(new int[]{21,3}, new int[]{21,2}, new int[]{21,4}, new int[]{22,3}, new int[]{22,2}, new int[]{22,4}));
        ArrayList<int[]> hallCoords = new ArrayList<>(Arrays.asList(new int[]{21,11}, new int[]{21,12}, new int[]{22,11}, new int[]{22,12}, new int[]{20,11}, new int[]{20,12}));
        ArrayList<int[]> studyCoords = new ArrayList<>(Arrays.asList(new int[]{22,18}, new int[]{22,19}, new int[]{22,20}, new int[]{23,19}, new int[]{23,20}, new int[]{23,21}));


        /* Creating Ballroom Object*/
        entrances.add((EntrySquare)board[5][8]);
        entrances.add((EntrySquare)board[7][9]);
        entrances.add((EntrySquare)board[7][14]);
        entrances.add((EntrySquare)board[5][15]);
        exits.add((FloorSquare)board[5][7]);
        exits.add((FloorSquare)board[8][9]);
        exits.add((FloorSquare)board[8][14]);
        exits.add((FloorSquare)board[5][16]);
        Ballroom = new Room("Ballroom", entrances, exits, ballroomCoords);

        entrances.clear(); //clearing the arrayLists, since we need it to hold the Squares for the next object
        exits.clear();

        /* Creating Dining Room Object */
        entrances.add((EntrySquare)board[15][6]);
        entrances.add((EntrySquare)board[12][7]);
        exits.add((FloorSquare)board[16][6]);
        exits.add((FloorSquare)board[12][8]);
        DiningRoom = new Room("DiningRoom", entrances, exits, diningCoords);

        entrances.clear();
        exits.clear();

        /* Creating BilliardRoom Object */
        entrances.add((EntrySquare)board[9][18]);
        entrances.add((EntrySquare)board[12][22]);
        exits.add((FloorSquare)board[9][17]);
        exits.add((FloorSquare)board[13][22]);
        BilliardRoom = new Room("BilliardRoom", entrances, exits, billiardCoords);

        entrances.clear();
        exits.clear();

        /* Creating Library Object */
        entrances.add((EntrySquare)board[16][17]);
        entrances.add((EntrySquare)board[14][20]);
        exits.add((FloorSquare)board[16][16]);
        exits.add((FloorSquare)board[13][20]);
        Library = new Room("Library", entrances, exits, libraryCoords);

        entrances.clear();
        exits.clear();

        /* Creating Hall Object */
        entrances.add((EntrySquare)board[18][11]);
        entrances.add((EntrySquare)board[18][12]);
        entrances.add((EntrySquare)board[20][14]);
        exits.add((FloorSquare)board[17][11]);
        exits.add((FloorSquare)board[17][12]);
        exits.add((FloorSquare)board[20][15]);
        Hall = new Room("Hall", entrances, exits, hallCoords);

        /* Create rooms with one entrance and a secret passage */
        Kitchen = new Room("Kitchen", (EntrySquare)board[6][4], (FloorSquare)board[7][4], kitchenCoords);
        Study = new Room("Study", (EntrySquare)board[21][17], (FloorSquare)board[20][17], studyCoords);
        Conservatory = new Room("Conservatory", (EntrySquare)board[4][18], (FloorSquare)board[5][18], conservatoryCoords);
        Lounge = new Room("Lounge", (EntrySquare)board[19][6], (FloorSquare)board[18][6], loungeCoords);

    // Create Cellar
        Cellar = new Room("Cellar", (EntrySquare)board[16][12], (FloorSquare)board[17][12]);

        Kitchen.setSecretPassage(Study);
        Study.setSecretPassage(Kitchen);
        Conservatory.setSecretPassage(Lounge);
        Lounge.setSecretPassage(Conservatory);
    }

    public void connectEntrySquaresToRooms(){
        // Add Kitchen Entry
        ((EntrySquare)board[6][4]).addRoomConnection(Kitchen);
        // Add 3 Ballroom Entries
        ((EntrySquare)board[5][8]).addRoomConnection(Ballroom);
        ((EntrySquare)board[7][9]).addRoomConnection(Ballroom);
        ((EntrySquare)board[7][14]).addRoomConnection(Ballroom);
        ((EntrySquare)board[5][15]).addRoomConnection(Ballroom);
        // Add Conservatory Entry
        ((EntrySquare)board[4][18]).addRoomConnection(Conservatory);
        // Add Dining Room Entries
        ((EntrySquare)board[12][7]).addRoomConnection(DiningRoom);
        ((EntrySquare)board[15][6]).addRoomConnection(DiningRoom);
        // Add Billiard Room Entries
        ((EntrySquare)board[9][18]).addRoomConnection(BilliardRoom);
        ((EntrySquare)board[12][22]).addRoomConnection(BilliardRoom);
        // Add Library Entries
        ((EntrySquare)board[16][17]).addRoomConnection(Library);
        ((EntrySquare)board[14][20]).addRoomConnection(Library);
        // Add Lounge Entry
        ((EntrySquare)board[19][6]).addRoomConnection(Lounge);
        // Add Hall Entries
        ((EntrySquare)board[18][11]).addRoomConnection(Hall);
        ((EntrySquare)board[18][12]).addRoomConnection(Hall);
        ((EntrySquare)board[20][14]).addRoomConnection(Hall);
        // Add Study Entry
        ((EntrySquare)board[21][17]).addRoomConnection(Study);
        // Add Cellar Entry. The Token enters the Cellar to attempt a guess
        ((EntrySquare)board[16][12]).addRoomConnection(Cellar);
    }

    public BoardSquare[][] returnBoard(){
    	return this.board;
    }
    /**
     * will over-write the current board
     */
    public void RecreateBoard() {
    	System.err.println("BOARD GETTING ERASED");

    	board = new BoardSquare[24][25];

        Ballroom = null;
        Conservatory = null;
        DiningRoom = null;
        BilliardRoom = null;
        Library = null;
        Lounge = null;
        Hall = null;
        Kitchen = null;
        Study = null;
        Cellar = null;

       	/* Calling classes to create board */
       	this.createRooms();
       	this.addEntrySquares();
       	this.addWalls();
       	this.addFloorSquares();
    }

}
