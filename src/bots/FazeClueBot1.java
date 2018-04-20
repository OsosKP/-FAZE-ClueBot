// [FAZE]ClueBot

package bots;

import gameengine.*;
import gameengine.Map;

import java.util.*;
import java.util.jar.Attributes.Name;

public class FazeClueBot1 extends FazeClueBot implements BotAPI {

	
	public FazeClueBot1 (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
		super(player, playersInfo, map, dice, log, deck);
	}
    
    public String getName() {
        return "FazeClueBot1"; // must match the class name
        
    }
}