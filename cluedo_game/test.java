// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

public class test {
	public test() {
		//nothing	
	}
	/* Forces the game to reset by creating a new obj -- only happens when the user does not correctly input the players */
	public void  resetgame() {	
		GameLogic resetGame = new GameLogic();
	}
		
	public static void main(String[] args) {
//		BoardBuilder bb = new BoardBuilder();
//		BoardImageTest board = new BoardImageTest(bb);

		GameLogic game = new GameLogic();
	}

	/**
	 * Testing methods
	 */
	public void RoomExitAndMovementTester(UserInterface ui){
		ui.buildGUI();
	}

}
