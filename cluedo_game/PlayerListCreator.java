package cluedo_game;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * Creates a GUI that allows players to select their characters
 * @author george
 *
 */
public class PlayerListCreator {

    private JFrame display = new JFrame();
    private Tokens playerList = new Tokens();

    /* Holds the GUIPlayer choices */
    private  CharacterList[] GUIPlayerList = null;
    /* Holds that player choices who were deleted form the createPlayersGUI */
    private  ArrayList<String> deletedPlayers = new ArrayList<>();
    /* Holds the players who were selected form the createPlayersGUI */
    private  ArrayList<String> selectedPlayers = new ArrayList<>();
    /* JPanel that will hold the createPlayersGUI */
    JPanel panel;

    public PlayerListCreator() {
        // ref line 661 for more info on subclass GUIPLayerList

        /* We can have a max of 6 characters */
        GUIPlayerList = new CharacterList[6];

        /* Setting default params for JFrame */
        display.setSize(400, 900);
        display.setTitle("Create Players");
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* populating panel which will hold the character choices */
        panel = new JPanel();
        panel.setLayout(new GridLayout(6,2));

        /* creating GUIPlayer list objects -- they will represent each character choice and adding it to the panel */
        for (int i = 0; i < 6; i++) {
            this.GUIPlayerList[i] = new CharacterList(i);
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
        display.setVisible(true);
    }
    
    public void  forceReset() {
    	PlayerListCreator temp = new PlayerListCreator();
    }

    public Tokens getPlayerList(){ 
    	return this.playerList;
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
        String willThisWork;
        public int objNum;
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
                        value = new JTextField("You have selected: " + selectedPlayers.get(objNum), 20);
                        add(value, BorderLayout.EAST);
                    }
                    else { // if we don't, we set the string = to what we selected earlier (in the selected players array)
                        willThisWork = selectedPlayers.get(objNum);
                        value = new JTextField("You have selected: " + selectedPlayers.get(objNum), 20);
                        add(value, BorderLayout.EAST);
                        return; //we don't want the rest of the method to run, because we have our value
                    }

                    /* Looping through the GUIPlayer array -- need to do this so all the jLists get updated */
                    for (int i = 0; i < 6; i++) {
                        /* If the obj in playerList is  not the current one and is not one of the options that we can pick multiple of */
                    	if (GUIPlayerList[i].objNum != objNum && (!(willThisWork.equals("Not Playing")))) {

                        /* Grabbing the current characterList */
                    		String[] tempItems = GUIPlayerList[i].items;

                            /* We are using this to create an array of the character that we want -- ie the character names EXCEPT the one currently selected  */
                            ArrayList<String> tempArray = new ArrayList<String>();

                            /* Looping though the tempItems and copying the values over */
                            for (int tempIndex  = 0; tempIndex < tempItems.length; tempIndex++) {
                            	if (!willThisWork.equals(tempItems[tempIndex])) {
                            		tempArray.add(tempItems[tempIndex]);
                                }
                                else { //adding the deleted players to an arrayList deleted players -- will be useful in future versions
                                	deletedPlayers.add(tempItems[tempIndex]);
                                }
                             }

                             /* This array will be composed of the elements we actually want form the ArrayList */
                             String[] newList = new String[tempArray.size()];

                             /* Moving the elements from the ArrayList to the array */
                             for (int gc = 0; gc < newList.length; gc++) {
                            	 newList[gc] = tempArray.get(gc);
                             }

                             /* Updating the values in the obj's items array (the reason why we needed newList in the firstPlace) */
                             GUIPlayerList[i].items = newList;

                             /* Removing all the elements form this obj's list -- will re-fill with updates values later */
                             GUIPlayerList[i].model.removeAllElements();

                             /* Updating the obj's item values with the correct number of character names */
                             for (int index = 0; index < newList.length; index++) {
                            	 GUIPlayerList[i].model.add(index, GUIPlayerList[i].items[index]);
                             }
                             //TODO: maybe in the future just remove these elements form the panel, instead
                             /* Removing the elements form the prior JLists, otherwise they will re-apear */
                             for (int GUILoop = 0; GUILoop < objNum; GUILoop++) {
                            	 GUIPlayerList[GUILoop].model.removeAllElements();
                             }

                            }
                         else { // if this is the current JList
                        	 /* removing the JList elements from the current obj (since the user already chose that they wanted) */
                             model.removeAllElements();
                         }
                      }
                   }

                
            });
            /* setting the JList and txt box to their initial values */
            System.out.println("\n\n");
            add(list, BorderLayout.CENTER);
            value = new JTextField("", 20);
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
        JLabel myLabel = new JLabel("[Left] Select Character");

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
            
            for (int i = 0; i < 6; i++) {
                String[] returnArray = GUIPlayerList[i].getValue();

                /* If the user wants to actually play, the above token objects get populated */
                if (!(returnArray[1].equals("Not Playing"))){
                	numPlayersCreated++;
                    if (returnArray[1].equals("Colonel Mustard")) {
                        if (mustard == null) {
                            mustard = new Token(17, 0, "Mustard", numPlayers++);
                            playerList.addPlayer(mustard);
                        }
                    }
                    else if (returnArray[1].equals("Miss Scarlett")) {
                        if (scarlet == null) {
                            scarlet = new Token(24, 7, "Scarlet", numPlayers++);
                            playerList.addPlayer(scarlet);
                        }
                    }
                    else if (returnArray[1].equals("Mrs White")) {
                        if (white == null) {
                            white = new Token(0, 9, "White", numPlayers++);
                            playerList.addPlayer(white);
                        }
                    }
                    else if (returnArray[1].equals("Mr Green")) {
                        if (green == null) {
                            green = new Token(0, 14, "Green", numPlayers++);
                            playerList.addPlayer(green);
                        }
                    }
                    else if (returnArray[1].equals("Mrs Peacock")) {
                        if (peacock == null) {
                            peacock = new Token(6, 23, "Peacock", numPlayers++);
                            playerList.addPlayer(peacock);
                        }
                    }
                    else if (returnArray[1].equals("Professor Plum")) {
                        if (plum == null) {
                            plum = new Token(19, 23, "Plum", numPlayers++);
                            playerList.addPlayer(plum);
                        }
                    }

                }
            }
            /* Removing all the JPanels and closing the JFrame */
            display.setVisible(false);
            display.getContentPane().removeAll();

            /* To conserve on memory, we are going to reset all the items in the arrayLits/arrays (since we arnt going to need them anymore */
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
            
            /* If the user has entered too few players -- we cant let them continue */
            if (numPlayers < 3) {
            	JOptionPane.showMessageDialog(null, "In order to play the game, there must be at LEAST 3 players", "ERROR: Too Few Players", JOptionPane.ERROR_MESSAGE);
       
            	/* Creating a new test obj should start this whole process over again -- WARNING uses alot of mem || need to try to find another way to write this*/
            	test resetGame = new test();
            	resetGame.resetgame();
            }
            else { //if the user(s) have entered the playerInfo correctly, we can tell GameLogic to continue
            	playerList.printList();
            	GameLogic.createBoardAndUI();           	
            }

        }
    }
}
