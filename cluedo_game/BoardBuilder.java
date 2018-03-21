package cluedo_game;

import java.util.ArrayList;

public class BoardBuilder {
    /*
    Since BoardSquare is an interface, we can declare an entire array
    of BoardSquare 'objects' and then turn them into our square types as needed.
     */
    private BoardSquare[][] board = new BoardSquare[24][25];
    /* Will store all the players in the game */
//    playerList players = new playerList();
    private int numPlayers = 0;

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
    
    /* Will prevent us from breaking everything */
    private boolean entrySquaresCreated = false;

    //
    // Accessors
    //
    public Room getBallroom() { return Ballroom;} //Returns Ballroom
    public Room getConservatory() {return Conservatory;}//Returns conservatory
    public Room getDiningRoom() {return DiningRoom;}//Returns Dining room
    public Room getBilliardRoom() {return BilliardRoom;}//Return billiar room
    public Room getLibrary() {return Library;}//Returns library
    public Room getLounge() {return Lounge;}//Returns lounge
    public Room getHall() {return Hall;}//Returns hall
    public Room getKitchen() {return Kitchen;}//Return kitchen
    public Room getStudy() {return Study;}//Return study
    public Room getCellar() {return Cellar;}//Return Cellar
    public BoardSquare getSquare(int xLoc, int yLoc) {
        return board[xLoc][yLoc];
    }
    
    /**
     * addEntrySquare
     * This method assigns the appropriate board squares as entries
     * to the corresponding rooms.
     */
    public void addEntrySquares(){
        // Add Kitchen Entry
        board[6][4] = new EntrySquare(6, 4, Kitchen, 1);
        // Add 3 Ballroom Entries
        board[5][8] = new EntrySquare(5, 8, Ballroom, 1);
        board[7][9] = new EntrySquare(7, 9, Ballroom, 2);
        board[7][14] = new EntrySquare(7, 14, Ballroom, 3);
        // Add Conservatory Entry
        board[4][18] = new EntrySquare(4, 18, Conservatory, 1);
        // Add Dining Room Entries
        board[12][7] = new EntrySquare(12, 7, DiningRoom, 1);
        board[15][6] = new EntrySquare(15, 6, DiningRoom, 2);
        // Add Billiard Room Entries
        board[9][18] = new EntrySquare(9, 18, BilliardRoom, 1);
        board[12][22] = new EntrySquare(12, 22, BilliardRoom, 2);
        // Add Library Entries
        board[16][17] = new EntrySquare(16, 17, Library, 1);
        board[14][20] = new EntrySquare(14, 20, Library, 2);
        // Add Lounge Entry
        board[19][6] = new EntrySquare(19, 6, Lounge, 1);
        // Add Hall Entries
        board[18][11] = new EntrySquare(18, 11, Hall, 1);
        board[18][12] = new EntrySquare(18, 12, Hall, 2);
        board[20][14] = new EntrySquare(20, 14, Hall, 3);
        // Add Study Entry
        board[21][17] = new EntrySquare(17, 21, Study, 1);
        // Add Cellar Entry. The Token enters the Cellar to attempt a guess
        board[16][12] = new EntrySquare(16, 12, Cellar, 1);
        //useful so we dont screw up later
        entrySquaresCreated = true;
    }

    /**
     * addBarriers
     * The board is a 24x24 matrix, but there are extra squares at the top
     * to spawn White and Green. To resolve this I made the board 24x25 and
     * I'm making everything WallSquares except those spawn points.
     *
     * There are also some barriers along the sides and bottom where there
     * are no room squares and no spawn points (see Documentation). This
     * implementation will look the same since we're making our Room Squares
     * walls so they're impassable.
     */
//    public void addBarriersAndSpawnPoints(){
//        int i;  // Indexing loops
//        // Loop to assign top edge barrier squares
//        for(i = 0; i < 24; i++){
//            // board[9][0] and board[14][0] are both spawn points
//            if(i == 9)
//                board[i][0] = new FloorSquare(i, 0, this.generatePlayer("White", 9, 0));
//            else if(i == 14)
//                board[i][0] = new FloorSquare(i, 0, this.generatePlayer("Green", 14, 0));
//            else
//                board[i][0] = new WallSquare(i, 0);
//        }
//
//        // Loop to assign left edge barrier squares
//        for(i = 0; i < 25 ; i++){
//            // board[0][17] is a spawn point
//            if(i == 17)
//                board[0][i] = new FloorSquare(0, i, this.generatePlayer("Mustard", 0, 17));
//            else
//                board[0][i] = new WallSquare(0, i);
//        }
//
//        // Loop to assign right edge barrier squares
//        for(i = 0; i < 25 ; i++){
//            // board[23][6] and board[23][19] are both spawn points
//            if(i == 6)
//                board[23][i] = new FloorSquare(23, i, this.generatePlayer("Peacock", 23, 6));
//            else if(i == 19)
//                board[23][i] = new FloorSquare(23, i, this.generatePlayer("Plum", 23, 19));
//            else
//                board[23][i] = new WallSquare(23, i);
//        }
//
//        // Loop to assign bottom edge barrier squares
//        for(i = 0; i < 24; i++){
//            // board[7][24] is a spawn point
//            if(i == 7)
//                board[i][24] = new FloorSquare(i, 24, this.generatePlayer("Scarlet", 7, 24));
//            else
//                board[i][24] = new WallSquare(i, 24);
//        }
//
//        // Two impassable squares at the top - see Documentation
//        board[6][1] = new WallSquare(6, 1);
//        board[17][1] = new WallSquare(17, 1);
//
//    }

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
                if(i != 6 && j != 4)
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Ballroom
        for(i = 2; i < 8; i++){
            for(j = 8; j < 16 ; j++) {
                // Ensure this isn't an entry square
                if(!(i == 5 && j == 8) && !(i == 7 && (j == 9 || j == 14)))
                board[i][j] = new WallSquare(i, j);
            }
        }
        for(j = 10; j < 14; j++)
            board[1][j] = new WallSquare(1, j);

        // Conservatory
        for(i = 1; i < 23; i++){
            for(j = 18; j < 24 ; j++){
                // Ensure this isn't an entry square
                // This time we also account for Conservatory's weird shape
                if(!(j == 18 && (i == 4 || i == 5)))
                    board[i][j] = new WallSquare(i, j);
            }
        }

        // Dining Room
        for(i = 9; i < 16; i++){
            for(j = 1; j < 8; j++){
                if(!(i == 9 && (j == 5 || j == 6 || j == 7)))
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
        for(i = 14; i < 19; i++){
            for(j = 17; j < 23; j++){
                if(!((i == 14 && j == 17) || (i == 18 && j == 17)))
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
                if(!((i == 8 && (j == 11 || j == 12)) || (i == 20 && j == 14)))
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
        for(int i = 1; i < 24; i++){
            for(int j = 1; j < 23; j++){
                if(!(board[i][j] instanceof WallSquare
                    || board[i][j] instanceof EntrySquare))
                        board[i][j] = new FloorSquare(i, j);
            }
        }
    }
    
    public void createRooms() {
    	if (this.entrySquaresCreated) { //this is where we actually create the rooms
    		
    		/* Creating the Rooms with Multiple Entrances*/
    		
    		/* Will be used to store the entrances for the individual rooms */
    		ArrayList<EntrySquare> entrances = new ArrayList<>();
    		
    		/* Creating Ballroom Object*/
    		entrances.add((EntrySquare)board[5][8]);
    		entrances.add((EntrySquare)board[7][9]);
    		entrances.add((EntrySquare)board[7][14]);
    		Ballroom = new Room("Ballroom",entrances);
    		
    		entrances.clear(); //clearing the arrayList, since we need it to hold the entranceSquares for the next object
    		
    		/* Creating Dining Room Object */
    		entrances.add((EntrySquare)board[12][7]);
    		entrances.add((EntrySquare)board[15][6]);
    		DiningRoom = new Room("DiningRoom", entrances);
    		
    		entrances.clear();
    		
    		/* Creating BilliardRoom Object */
    		entrances.add((EntrySquare)board[9][18]);
    		entrances.add((EntrySquare)board[12][22]);
    		BilliardRoom = new Room("BilliardRoom", entrances);
    		
    		entrances.clear();
    		
    		/* Creating Library Object */
    		entrances.add((EntrySquare)board[16][17]);
    		entrances.add((EntrySquare)board[14][20]);
    		Library = new Room("Library", entrances);
    		
    		entrances.clear();
    		
    		/* Creating Hall Object */
    		entrances.add((EntrySquare)board[18][11]);
    		entrances.add((EntrySquare)board[18][12]);
    		entrances.add((EntrySquare)board[20][14]);
    		Hall = new Room("Hall", entrances);
    		
    		entrances.clear();
    		
    		
    	}
    	else { //if we haven't created the entry squares for some reason, this will prevent it from breaking everything
    		this.addEntrySquares();
    		this.createRooms();
    	}
    }

//    public Token generatePlayer(String name, int x, int y) {
//    	//TODO turn this into some kind of circularly linked list
//    	//arrayList of all players, separate from the turnList but contains the same object
//    	Token temp = new Token(x,y,name,numPlayers++);
//    	players.addFirst(temp);
//    	return temp;
//    }
}
