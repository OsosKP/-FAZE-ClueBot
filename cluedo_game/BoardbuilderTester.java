package cluedo_game;

public class BoardbuilderTester {

    public BoardBuilder buildTest() {
    	BoardBuilder builderTest = new BoardBuilder();
    	return builderTest;
    }
    
    public void testRooms(BoardBuilder builderTest) {
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
    }
    
    public void printMap(BoardBuilder builderTest) {
        
        BoardSquare[][] BoardSquareTest = builderTest.returnBoard();
        
        /* Does the board correctly print? */
        for (int rows = 0; rows < 24; rows++) {
        	for (int cols = 0; cols < 25; cols++) {
        		if (BoardSquareTest[rows][cols] instanceof FloorSquare) {
        			System.out.print(" | *Floor* | ");
        		}
        		else if (BoardSquareTest[rows][cols] instanceof WallSquare) {
        			System.out.print(" | Wall  | ");
        		}
        		else if (BoardSquareTest[rows][cols] instanceof EntrySquare) {
        			System.out.print(" | *Entry* | ");
        		}
        		else if (BoardSquareTest[rows][cols] instanceof Room) {
        			System.out.print(" | *Room*  | ");
        		}
        		else {
        			System.out.print(" | ??    |");
        		}
        	}
        	System.out.print("\n");
        }
    }
    
    
    
    public static void main (String[] agrs) {
    	BoardbuilderTester testMe = new BoardbuilderTester();
    	BoardBuilder testing = testMe.buildTest();
    	
    	testMe.testRooms(testing);
    	testMe.printMap(testing);
    }

}
