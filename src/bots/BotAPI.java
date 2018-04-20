package bots;

import gameengine.Cards;
import gameengine.Log;

public interface BotAPI {

    String getName();
    String getVersion();  // returns the version number of the Bot for use during the competition 
    String getCommand();
    String getMove();
    String getSuspect();
    String getWeapon();
    String getRoom();
    String getDoor();
    String getCard(Cards matchingCards);
    void notifyResponse(Log response);
    void notifyPlayerName(String playerName);// gives the player name when the start
    void notifyTurnOver(String playerName, String position); // gives the name and position of the players whose turn just finished
    void notifyQuery(String playerName, String query);  // gives the querying player name and the question
    void notifyReply(String playerName, boolean cardShown); // gives the queried player's reply - card or no card

}
