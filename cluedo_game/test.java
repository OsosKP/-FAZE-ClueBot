package cluedo_game;

public class test {

	public static void main(String[] args) {
		BoardBuilder bb = new BoardBuilder();
		System.out.println(bb.getSquare(0,0).getSquareType().getClass());
		BoardImageTest board = new BoardImageTest(bb);
	}
}
