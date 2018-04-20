// [FAZE]ClueBot

package bots;

import gameengine.*;
import gameengine.Map;

import java.util.*;
import java.util.jar.Attributes.Name;

public class FazeClueBot2 extends FazeClueBot implements BotAPI {
 
	
	public FazeClueBot2 (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
		super(player, playersInfo, map, dice, log, deck);
    }
    
    public String getName() {
        return "FazeClueBot2"; // must match the class name
    }

}