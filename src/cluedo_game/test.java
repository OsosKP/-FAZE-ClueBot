// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

public class test {
	static GameLogic game;
	public test() {
		//nothing
	}

	public static void main(String[] args) {
//		BoardBuilder bb = new BoardBuilder();
//		BoardImageTest board = new BoardImageTest(bb);

		game = new GameLogic();
	}

	/**
	 * Testing methods
	 */
	public void RoomExitAndMovementTester(UserInterface ui){
		ui.buildGUI();
	}

}
