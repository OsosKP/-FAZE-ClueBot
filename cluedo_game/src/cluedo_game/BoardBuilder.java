package cluedo_game;

public class BoardBuilder {
    private BoardSquare[][] board = new BoardSquare[23][24];

    /**
     * addEntrySquare
     * This method assigns the appropriate board squares as entries
     * to the corresponding rooms.
     */
    public void addEntrySquares(){
        // Add Kitchen Entry
        board[4][6] = EntrySquare(4, 6, Kitchen, 1);
        // Add 3 Ballroom Entries
        board[8][5] = EntrySquare(8, 5, Ballroom, 1);
        board[9][7] = EntrySquare(9, 7, Ballroom, 2);
        board[14][7] = EntrySquare(14, 7, Ballroom, 3);
        // Add Conservatory Entry
        board[18][4] = EntrySquare(18, 4, Conservatory, 1);
        // Add Dining Room Entries
        board[7][12] = EntrySquare(7, 12, DiningRoom, 1);
        board[6][15] = EntrySquare(6, 15, DiningRoom, 2);
        // Add Billiard Room Entries
        board[18][9] = EntrySquare(18, 9, BilliardRoom, 1);
        board[22][12] = EntrySquare(22, 12, BilliardRoom, 2);
        // Add Library Entries
        board[17][16] = EntrySquare(17, 16, Library, 1);
        board[20][14] = EntrySquare(20, 14, Library, 2);
        // Add Lounge Entry
        board[2][21] = EntrySquare(2, 21, Lounge, 1);
        // Add Hall Entries
        board[11][18] = EntrySquare(11, 18, Hall, 1);
        board[12][18] = EntrySquare(12, 18, Hall, 2);
        board[14][20] = EntrySquare(14, 20, Hall, 3);
        // Add Study Entry
        board[17][21] = EntrySquare(17, 21, Study, 1);
        // Add Cellar Entry. The Token enters the Cellar to attempt a guess
        board[12][16] = EntrySquare(12, 16, Cellar, 1);
    }

}
