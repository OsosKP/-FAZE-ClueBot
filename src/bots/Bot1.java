package bots;

import gameengine.*;

public class Bot1 extends FazeClueBot implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the board or the player objects
    // It may only inspect the state of the board and the player objects

    public Bot1 (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        super(player, playersInfo, map, dice, log, deck);
    }

    public String getName() {
        return "Bot1"; // must match the class name
    }

    public String getVersion () {
        return "0.1";   // change on a new release
    }

}
