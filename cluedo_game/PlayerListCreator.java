package cluedo_game;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class PlayerListCreator {

    private JFrame display = new JFrame();
    private Tokens playerList = new Tokens();

    private String currentSelection = null;

    /* Holds the GUIPlayer choices */
    private  CharacterList[] GUIPlayerList = null;
    private int[] storedValues = new int[6];
    /* Holds that player choices who were deleted form the createPlayersGUI */
    private  ArrayList<String> deletedPlayers = new ArrayList<>();
    /* Holds the players who were selected form the createPlayersGUI */
    private  ArrayList<String> selectedPlayers = new ArrayList<>();
    /* JPanel that will hold the createPlayersGUI */
    JPanel panel;
    
    boolean tooFew = false;
    
    BoardBuilder gameBoard;

    public PlayerListCreator() {
        // ref line 661 for more info on subclass GUIPLayerList

        /* We can have a max of 6 characters */
        GUIPlayerList = new CharacterList[6];

        /* Setting default params for JFrame */
        display.setSize(497, 900);
        display.setTitle("Create Players");
        
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.setResizable(true);
        
        /* populating panel which will hold the character choices */
        panel = new JPanel();
        panel.setLayout(new GridLayout(6,2));

        /* creating GUIPlayer list objects -- they will represent each character choice and adding it to the panel */
        for (int i = 0; i < 6; i++) {
            this.GUIPlayerList[i] = new CharacterList(i); 
        }
        /* 'Rolling' the dice for the users -- they are in fats's hands now */
        assignDice();
        
        /* Adding the users to the JFrame */
        for (int i = 0; i < 6; i++) {
        	panel.add(GUIPlayerList[i]);
        }
        
        /* Creating title bar class */
        CharacterListUITitle titleBar = new CharacterListUITitle();

        /* Creating the 'submit' button */
        CharacterListUIButton submitButton = new CharacterListUIButton();

        /* Adding components to the JFrame */
        display.add(panel, BorderLayout.CENTER);
        display.add(titleBar, BorderLayout.NORTH);
        display.add(submitButton, BorderLayout.SOUTH);
        
        /* Making sure that the JFrmae is displayed in the center of the users screen */
        display.setLocationRelativeTo(null); 
        
        display.setVisible(true);
    }
    
    public void  forceReset() {
    	PlayerListCreator temp = new PlayerListCreator();
    }

    public Tokens getPlayerList(){ 
    	return this.playerList;
    }
    
    public boolean runGame() {
    	System.out.println("I am getting here?");
    	return tooFew;
    }
    /**
     * simulates rolling the dice for the players
     * @param rollMe
     */
    public void assignDice() {
    	Random numberGenerator = new Random();
    	/* Using a set because it will not accpet duplicates */
    	Set<Integer> generatedNumers = new LinkedHashSet<Integer>();
    	
    	while (generatedNumers.size() < 6) {
    		Integer nextToInsert = numberGenerator.nextInt(6) + 1;
    		generatedNumers.add(nextToInsert);
    	}
    	
    	Iterator<Integer> iterateOverMe = generatedNumers.iterator();
    	int index = 0;
    	
    	while (iterateOverMe.hasNext()) {
    		GUIPlayerList[index].diceNumber = iterateOverMe.next();
    		index++;
    	}
    }

    /* Inner classes that will be useful later */
    class CharacterList extends JPanel {

        public String[] items = {
                "Miss Scarlett",
                "Colonel Mustard",
                "Mrs White",
                "Mr Green",
                "Mrs Peacock",
                "Professor Plum",
                "Not Playing"
        };


        private JTextField value;
        String willThisWork, username, characterName;
        private int objNum;
        private int diceNumber = -1;
        JList list;
        DefaultListModel model = new DefaultListModel();

        public CharacterList(int i) {
            super(new BorderLayout(5, 5));
            this.objNum = i;
            this.setListener();
        }
        
        @SuppressWarnings("unchecked")
        public void setListener() {

            /* Updating the list to be the model -- needs to be a DefaultList Model otherwise we cant update anything */
            list = new JList(model);

            /* Adding the Strung array into the jList */
            for (int i = 0; i < items.length; i++) {
                model.add(i, items[i]);
            }
            /* List Selection Listener -- needed to update the lists/get the current value */
            /* Needs to be an inner class because we need access to this CharacterList obj */
            list.addListSelectionListener(new ListSelectionListener() {
                /* If the value was selected in a list */
                public void valueChanged(ListSelectionEvent lse) {
                    /* Grabbing the Value of a String */
                    willThisWork = ((String) list.getSelectedValue());

                    /* the ListSelectionListener calls itself multiple times for each list that was selected -- this is to make sure that we dont have duplicate values in the selectedPlayers */
                    for (int i = 0; i < selectedPlayers.size()-1; i++) {
                        /* Selected Players holds the value of the player that we selected -- ListSelectionListener has a nasty habit of re-setting it to NULL */
                        if ((selectedPlayers.get(i).equals(selectedPlayers.get(i+1)) && (!(selectedPlayers.get(i).equals("Not Playing"))))  ) {
                            selectedPlayers.remove(i+1);
                        }
                    }
                    

                    /* If we have gotten a value from the list -- we add it to the selectedPlayers Array and inform the user */
                    if (willThisWork != null) {
                        selectedPlayers.add(willThisWork);

                        String[] output = getValue();
                        username = output[0];
                        
                        characterName = selectedPlayers.get(objNum);

                        value.setText("You have selected: " + selectedPlayers.get(objNum)) ;
                        
                       // value.setEditable(false);
                        
                        value.revalidate();
                    }
                    else { // if we don't, we set the string = to what we selected earlier (in the selected players array)
                        willThisWork = selectedPlayers.get(objNum);
                        value.setText("You have selected: " + selectedPlayers.get(objNum));

                        //value.setEditable(false);
                        value.revalidate();

                        return; //we don't want the rest of the method to run, because we have our value
                    }

                    /* Looping through the GUIPlayer array -- need to do this so all the jLists get updated */
                    for (int i = 0; i < 6; i++) {
                        /* If the obj in playerList is  not the current one and is not one of the options that we can pick multiple of */
                    	if (GUIPlayerList[i].objNum != objNum && (!(willThisWork.equals("Not Playing")))) {

                        /* Grabbing the current characterList */
                    		String[] tempItems = GUIPlayerList[i].items;

                            /* We are using this to create an array of the character that we want --
                                ie the character names EXCEPT the one currently selected  */
                            ArrayList<String> tempArray = new ArrayList<String>();

                            /* Looping though the tempItems and copying the values over */
                            for (int tempIndex  = 0; tempIndex < tempItems.length; tempIndex++) {
                            	if (!willThisWork.equals(tempItems[tempIndex])) {
                            		tempArray.add(tempItems[tempIndex]);
                                }
                                else { //adding the deleted players to an arrayList deleted players --
                                            // will be useful in future versions
                                	deletedPlayers.add(tempItems[tempIndex]);
                                }
                             }

                             /* This array will be composed of the elements we actually want form the ArrayList */
                             String[] newList = new String[tempArray.size()];

                             /* Moving the elements from the ArrayList to the array */
                             for (int gc = 0; gc < newList.length; gc++) {
                            	 newList[gc] = tempArray.get(gc);
                             }

                             /* Updating the values in the obj's items array
                                    (the reason why we needed newList in the firstPlace) */
                             GUIPlayerList[i].items = newList;

                             /* Removing all the elements form this obj's list --
                                    will re-fill with updates values later */
                             GUIPlayerList[i].model.removeAllElements();

                             /* Updating the obj's item values with the correct number of character names */
                             for (int index = 0; index < newList.length; index++) {
                            	 GUIPlayerList[i].model.add(index, GUIPlayerList[i].items[index]);
                             }
                             //TODO: maybe in the future just remove these elements from the panel, instead
                             /* Removing the elements from the prior JLists, otherwise they will re-appear */
                             for (int GUILoop = 0; GUILoop < objNum; GUILoop++) {
                            	 GUIPlayerList[GUILoop].model.removeAllElements();
                             }

                            }
                         else { // if this is the current JList
                        	 /* removing the JList elements from the current obj (since the user already chose that they wanted) */
                        	 model.removeAllElements();
                        	                         	                         	 	 
                        	 /* Making the username appear in a different box next to the character selection */ 
                        	 JTextField userNameHold = new JTextField("", 20);
                        	 JTextField userDice = new JTextField("", 20);
                        	 
                        	 /* need to check the case in which the user didnt enter a usname -- or chose 'not plying' */
                        	 if (username.isEmpty() || username.equals("Type username here: ")) {
                        		 username = "Player " + objNum;
                        		 userDice.setText("Dice Rolled: " + diceNumber);
                        	 }
                        	 else if (characterName.equals("Not Playing")) { //here we need to check if the user wanted not to play
                        		 username = "NA";
                        		 userDice.setText("Dice Rolled: NA");
                        	 }
                        	 else {
                        		 userDice.setText("Dice Rolled: " + diceNumber); 
                        	 }
	                        	 
        
                        	 userNameHold.setText("Username: " + username);
                        	 userNameHold.setEditable(false);
                        	 value.setEditable(false);
                        	 userDice.setEditable(false);
                        	 
                        	 
                        	 /* JPanel is going to hold the characterSelection/username so we can have space for the user's dice roll */
                        	 JPanel holderValue = new JPanel();
                        	 holderValue.setLayout(new GridLayout(2,1));
                        	
                        	 holderValue.add(value);
                        	 holderValue.add(userNameHold);
                        	 add(holderValue, BorderLayout.WEST);
                        	 add(userDice, BorderLayout.EAST);
                        }
                      }
                   }

                
            });
            /* setting the JList and txt box to their initial values */
            System.out.println("\n\n");
            add(list, BorderLayout.CENTER);
            /* Setting a default value for the text filed -- so the user knows that they have to enter their uername here */
            value = new JTextField("Type username here: ", 20);
            
            /* If the user wants to type in a username -- we want all the pre-loaded text to go away */
            value.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					// nothing	
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
					// removes the pre-loaded text
					value.setText("");
				}
			});
            

            value.setEnabled(true);

            add(value, BorderLayout.EAST);
        }
        /* Getting the value of items held in the obj */
        public String[] getValue() {
            String[] valueArray = new String[2];
            valueArray[0] = this.value.getText();
            valueArray[1] = this.willThisWork;
            return valueArray;
        }
    }

    /* Simple class to hold the title to the createPlayersGUI */
    class CharacterListUITitle extends JPanel {
        JLabel myLabel = new JLabel("Please Select a Character");

        @Override
        public void setLayout(LayoutManager mgr) {
            // TODO Auto-generated method stub
            super.setLayout(mgr);
        }

        public CharacterListUITitle() {
            this.setLayout(new BorderLayout());
            myLabel.setHorizontalAlignment(JLabel.CENTER);

            this.add(myLabel);
        }
    }

    /* Simple class to hold the submit button for the createPlayersGUI */
    class CharacterListUIButton extends JPanel{
        JButton testButton = new JButton("Submit");
        CreateUsersListener listen = new CreateUsersListener();

        public CharacterListUIButton() {
            testButton.addActionListener(listen);
            testButton.setHorizontalAlignment(JButton.CENTER);
            this.add(testButton);
        }
    }

    /**
     * Action Listener class for the createUsersButton
     * @author george
     *
     */
    class CreateUsersListener implements ActionListener {
        @Override
        /* Now that this button has been pressed, we create our players */
        public void actionPerformed(ActionEvent arg0) {
            /* Looping through the GUIPlayers list, if we get the player names to !=  */
            int numPlayers = 0;

            /* Token Objs that will make sure we only create ONE of each character */
            Token white = null;
            Token green = null;
            Token mustard = null;
            Token peacock = null;
            Token plum = null;
            Token scarlet = null;
            
            int numPlayersCreated = 0;
            
            ArrayList<Token> tempTokenArray = new ArrayList<>();
            
            
            for (int i = 0; i < 6; i++) {
                String[] returnArray = GUIPlayerList[i].getValue();
                
                /* If the user wants to actually play, the above token objects get populated */
                if (!(returnArray[1].equals("Not Playing"))){
                	numPlayersCreated++;
                	                	
                    if (returnArray[1].equals("Colonel Mustard")) {
                        if (mustard == null) {
                            mustard = new Token(17, 0, "Mustard",GUIPlayerList[i].username, numPlayers++, GUIPlayerList[i].diceNumber);
                            tempTokenArray.add(mustard);
                            
                            playerList.addPlayer(mustard);
                        }
                    }
                    else if (returnArray[1].equals("Miss Scarlett")) {
                        if (scarlet == null) {
                            scarlet = new Token(24, 7, "Scarlet",GUIPlayerList[i].username, numPlayers++, GUIPlayerList[i].diceNumber);
                            tempTokenArray.add(scarlet);
                            
                            playerList.addPlayer(scarlet);
                        }
                    }
                    else if (returnArray[1].equals("Mrs White")) {
                        if (white == null) {
                            white = new Token(0, 9, "White",GUIPlayerList[i].username, numPlayers++, GUIPlayerList[i].diceNumber);
                            tempTokenArray.add(white);
                            
                            playerList.addPlayer(white);
                        }
                    }
                    else if (returnArray[1].equals("Mr Green")) {
                        if (green == null) {
                            green = new Token(0, 14, "Green",GUIPlayerList[i].username, numPlayers++, GUIPlayerList[i].diceNumber);
                            tempTokenArray.add(green);
                            
                            playerList.addPlayer(green);
                        }
                    }
                    else if (returnArray[1].equals("Mrs Peacock")) {
                        if (peacock == null) {
                            peacock = new Token(6, 23, "Peacock",GUIPlayerList[i].username, numPlayers++, GUIPlayerList[i].diceNumber);
                            tempTokenArray.add(peacock);
                            
                            playerList.addPlayer(peacock);
                        }
                    }
                    else if (returnArray[1].equals("Professor Plum")) {
                        if (plum == null) {
                            plum = new Token(19, 23, "Plum",GUIPlayerList[i].username, numPlayers++, GUIPlayerList[i].diceNumber);
                            tempTokenArray.add(plum);
                            
                            playerList.addPlayer(plum);
                        }
                    }
                }
            }
            /* Pulling the player with the highest diceNumber */
            int largestNumberPosition = -1, largestRoll = -1;
            
            Token highRoller = null;
            ArrayList<Token> beforeHighRoller = new ArrayList<Token>();
            ArrayList<Token> afterHighRoller = new ArrayList<Token>();
            
            /* Pulling the position of the largest diceNumber from the array -- since that user will be the first to go */
            for (int i = 0; i < tempTokenArray.size(); i++) {
            	if (tempTokenArray.get(i).returnDiceNumber() > largestRoll) {
            		highRoller = tempTokenArray.get(i);
            		largestRoll = tempTokenArray.get(i).returnDiceNumber();
            		i = 0;
            	}
            }
           
            /* Populating afterHighRoller Array */
            for (int i = 0; i < tempTokenArray.size(); i++) {
            	if (tempTokenArray.get(i).getPlayerNumber() > highRoller.getPlayerNumber()) {
            		afterHighRoller.add(tempTokenArray.get(i));
            	}
            }
            
            /* Populating the beforeHighRoller Array */
            for (int i = 0; i < tempTokenArray.size(); i++) {
            	if (tempTokenArray.get(i).getPlayerNumber() < highRoller.getPlayerNumber()) {
            		
            	}
            }
            
            System.out.println("The largest Roll was: " + largestRoll + " Done by " + highRoller.getName());
            
            /* We want the 'high roller' to be the first person to play */
            playerList.setFirst(highRoller);
            /* Now we want to correctly set all the other players to come after him/her */
            
            
            /* Removing all the JPanels and closing the JFrame */
            display.setVisible(false);
            display.getContentPane().removeAll();

            /* To conserve on memory, we are going to reset all the items in the arrayLists/arrays
                    (since we aren't going to need them anymore) */
            int i;
            for (i = 0; i < GUIPlayerList.length; i++) {
            	GUIPlayerList[i] = null;
            }
            for (i = 0; i < selectedPlayers.size(); i++) {
            	selectedPlayers.remove(i);
            }
            for (i = 0; i < deletedPlayers.size(); i++) {
            	deletedPlayers.remove(i);
            }
             
            if (numPlayers < 2) {
            	JOptionPane.showMessageDialog(null, "In order to play the game, there must be at least 2 players");
            	
            	test restartGame = new test();
            	restartGame.resetgame();
            }
            else {
            	playerList.printList();
            	GameLogic.createGame();
            }

        }
    }
}
