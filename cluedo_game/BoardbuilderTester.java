package cluedo_game;

public class BoardbuilderTester {
   
    public static void main (String[] agrs) {
    	
    	/* Does the board create correctly */
    	BoardBuilder builderTest = new BoardBuilder();
    	
    	/* Does the board re-create properly */
    	builderTest.RecreateBoard();

    	/* Can we get all the rooms without breaking everything */
    	Room Ballroom = builderTest.getBallroom();
        Room Conservatory = builderTest.getConservatory();
        Room DiningRoom = builderTest.getDiningRoom();
        Room BilliardRoom = builderTest.getBilliardRoom();
        Room Library = builderTest.getLibrary();
        Room Lounge = builderTest.getLounge();
        Room Hall = builderTest.getHall();
        Room Kitchen = builderTest.getKitchen();
        Room Study = builderTest.getStudy();
        Room Cellar = builderTest.getCellar();
        
        BoardSquare[][] BoardSquareTest = builderTest.returnBoard();
        
        /* Does the board correctly print? */
        for (int rows = 0; rows < 24; rows++) {
        	for (int cols = 0; cols < 25; cols++) {
        		if (BoardSquareTest[rows][cols].getSquareType().equals("Room")) {
        			System.out.println("We goot someathing");
        		}
        	}
        }
    }

}
