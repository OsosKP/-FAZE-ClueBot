package cluedo_game;

public class CellarEntrySquare extends EntrySquare {
    public CellarEntrySquare(int x, int y, int refNum) {
        super(x, y, refNum);
    }

    public void setPlayerOn(Token player) {
        player.enterRoom(this.getRoomAssigned());
//        GameLogic.Accusing.startAccusing(player);
    }


}
