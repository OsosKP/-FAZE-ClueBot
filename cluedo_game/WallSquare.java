package cluedo_game;

public class WallSquare implements BoardSquare {
    /*
        Instance variables
     */
    private int[] location;         // Coordinates of this square on the board
    /*
    Constructor
     */
    public WallSquare(int x, int y){
    	this.location[0] = x;
    	this.location[1] = y;
    }

    public WallSquare(){
        // This is a dummy WallSquare for type check
    }
    
    @Override
    public int[] getLocation() {
        return location;
    }

    /**
     * getSquareType
     * This method returns an object of type WallSquare
     * This is used to check that the object containing this method is a WallSquare
     * @return temporary object of type WallSquare
     */
    @Override
    public BoardSquare getSquareType() {
        return new WallSquare();
    }
    @Override
    public boolean isPlayerOn(){
        return false;
    }
}
