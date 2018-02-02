package cluedo_game;

public class BoardBuilder {
    private BoardSquare[][] board = new BoardSquare[24][25];

    /**
     * addEntrySquare
     * This method assigns the appropriate board squares as entries
     * to the corresponding rooms.
     */
    public void addEntrySquares(){
        // Add Kitchen Entry
        board[4][6] = new EntrySquare(4, 6, Kitchen, 1);
        // Add 3 Ballroom Entries
        board[8][5] = new EntrySquare(8, 5, Ballroom, 1);
        board[9][7] = new EntrySquare(9, 7, Ballroom, 2);
        board[14][7] = new EntrySquare(14, 7, Ballroom, 3);
        // Add Conservatory Entry
        board[18][4] = new EntrySquare(18, 4, Conservatory, 1);
        // Add Dining Room Entries
        board[7][12] = new EntrySquare(7, 12, DiningRoom, 1);
        board[6][15] = new EntrySquare(6, 15, DiningRoom, 2);
        // Add Billiard Room Entries
        board[18][9] = new EntrySquare(18, 9, BilliardRoom, 1);
        board[22][12] = new EntrySquare(22, 12, BilliardRoom, 2);
        // Add Library Entries
        board[17][16] = new EntrySquare(17, 16, Library, 1);
        board[20][14] = new EntrySquare(20, 14, Library, 2);
        // Add Lounge Entry
        board[2][21] = new EntrySquare(2, 21, Lounge, 1);
        // Add Hall Entries
        board[11][18] = new EntrySquare(11, 18, Hall, 1);
        board[12][18] = new EntrySquare(12, 18, Hall, 2);
        board[14][20] = new EntrySquare(14, 20, Hall, 3);
        // Add Study Entry
        board[17][21] = new EntrySquare(17, 21, Study, 1);
        // Add Cellar Entry. The Token enters the Cellar to attempt a guess
        board[12][16] = new EntrySquare(12, 16, Cellar, 1);
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
    public void addBarriersAndSpawnPoints(){
        // Loop to assign top edge barrier squares
        for(int i = 0; i < 24; i++){
            // board[9][0] and board[14][0] are both spawn points
            if(i == 9)
                board[i][0] = new FloorSquare(i, 0, White);
            else if(i == 14)
                board[i][0] = new FloorSquare(i, 0, Green);
            else
                board[i][0] = new WallSquare(i, 0);
        }

        // Loop to assign left edge barrier squares
        for(int i = 0; i < 25 ; i++){
            // board[0][17] is a spawn point
            if(i == 17)
                board[0][i] = new FloorSquare(0, i, Mustard);
            else
                board[0][i] = new WallSquare(0, i);
        }

        // Loop to assign right edge barrier squares
        for(int i = 0; i < 25 ; i++){
            // board[23][6] and board[23][19] are both spawn points
            if(i == 6)
                board[23][i] = new FloorSquare(23, i, Peacock);
            else if(i == 19)
                board[23][i] = new FloorSquare(23, i, Plum);
            else
                board[23][i] = new WallSquare(23, i);
        }

        // Loop to assign bottom edge barrier squares
        for(int i = 0; i < 24; i++){
            // board[7][24] is a spawn point
            if(i == 7)
                board[i][24] = new FloorSquare(i, 24, Scarlet);
            else
                board[i][24] = new WallSquare(i, 24);
        }

    }

}
