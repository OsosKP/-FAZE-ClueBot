package cluedo_game;

public class WallSquare implements BoardSquare {
    /*
        Instance variables
     */
    private int[] location;         // Coordinates of this square on the board
    
    WallSquare(int x, int y){
    	this.location[0] = x;
    	this.location[1] = y;
    }
    
    @Override
    public int[] getLocation() {
        return location;
    }
}
