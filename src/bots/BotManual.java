package bots;

import java.util.Scanner;

import gameengine.*;

public class BotManual implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the board or the player objects
    // It may only inspect the state of the board and the player objects

    private Player player;
    private PlayersInfo playersInfo;
    private Map map;
    private Dice dice;
    private Log log;
    private Deck deck;

    public BotManual (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
    }

    public String getName() {
        return "BotManual"; // must match the class name
    }

    public String getVersion () {
        return "0.1";   // change on a new release
    }

    public String getCommand() {
    	System.out.println("Enter your command");
    	Scanner input = new Scanner(System.in);
    	return input.nextLine(); 
    }

    public String getMove() {
        System.out.println("Enter your command");
    	Scanner input = new Scanner(System.in);
    	return "r";
    }

    public String getSuspect() {
        System.out.println("Enter your command");
    	Scanner input = new Scanner(System.in);
    	return input.nextLine(); 
    }

    public String getWeapon() {
    	System.out.println("Enter your command");
    	Scanner input = new Scanner(System.in);
    	return input.nextLine(); 
    }

    public String getRoom() {
    	System.out.println("Enter your command");
    	Scanner input = new Scanner(System.in);
    	return input.nextLine(); 
    }

    public String getDoor() {
    	System.out.println("Enter your command");
    	Scanner input = new Scanner(System.in);
    	return input.nextLine(); 
    }

    public String getCard(Cards matchingCards) {
    	System.out.println("Enter your command");
    	Scanner input = new Scanner(System.in);
    	return input.nextLine(); 
    }

    public void notifyResponse(Log response) {
    	System.out.println("Notify response: \n\t" + response);
        // Add your code here
    }

    public void notifyPlayerName(String playerName) {
     	System.out.println("NotifyPlayerName: \n\t PlayerName: " + playerName);
       // Add your code here
    }

    public void notifyTurnOver(String playerName, String position) {
        // Add your code here
    	    	System.out.println("NotifyTurnOver: \n\t PlayerName: " + playerName + " Position: " + position);

    }

    public void notifyQuery(String playerName, String query) {
        // Add your code here
    	System.out.println("Notify Query: \n\t PlayerName: " + playerName + " Query: " + query);

    }

    public void notifyReply(String playerName, boolean cardShown) {
        // Add your code here
		System.out.println("Notify reply: \n\t PlayerName:" + playerName + " has shown card?: " + cardShown);

    }
}
