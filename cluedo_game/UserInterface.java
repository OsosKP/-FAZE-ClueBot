package cluedo_game;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * UserInterface
 * This class is the JFrame that will hold all of the individual JPanels:
 *  Game Board
 *  Input Box
 *  Text Display Box
 */
public class UserInterface extends JPanel {
    // Frame which will contain the GUI
    private JFrame display = new JFrame();
    // The user input portion of the display
    // First it is built into 'in', then it is loaded into the 'input' JPanel
    private UserInputBox in = new UserInputBox();
    private JPanel input = in.createInputPanel();
    private JButton startGameButton;
    // This will replace inputs panel when user has to choose an exit
    private JPanel exits;

    // Text output portion of the display, generated in the same way as user input
    private OutputTextDisplay out = new OutputTextDisplay();
    private JPanel output = out.createOutputPanel();

    // The board image portion of the UI
    JPanel boardImagePanel;
    BoardImage myImg;

    // The overall display panel that will control layout of the 3 panels
    private JPanel userDisplay = new JPanel();

    // Pointer to player whose turn it is. When we add 'turns', the turn object will send info to this
    private Token currentPlayer;
    private Tokens playerList;

    private  CharacterList[] GUIPlayerList = null;
    private String[] deletedPlayers = new String[6];
    
    BoardBuilder gameBoard;

    /**
     * The constructor for the UI which will set off a chain of events drawing all of the components
     * Everything so far is done in buildGUI, but when we add game logic it will also(?) be contained here
     */
    public UserInterface(BoardBuilder board) {
        /* This is going to happen AFTER the start game button is pressed */
        // TODO: BoardBuilder.getPlayerList() is redundant now that we're using a LL
        this.playerList = board.getPlayerList();
        this.currentPlayer = playerList.getFirst();

        this.buildGUI();
       
//        this.createPlayersGUI();
    }

    public boolean createPlayersGUI() {
    	 

                     
            /* We can have a max of 6 characters */
            GUIPlayerList = new CharacterList[6];
            
            display.setSize(400, 900);
            display.setTitle("Cluedo");
            display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(6,2));
            
            JButton finishedWithEntry = new JButton("Sumbit");
            display.add(finishedWithEntry);
            
            for (int i = 0; i < 6; i++) { //looping 
            	GUIPlayerList[i] = new CharacterList(i);
            	panel.add(GUIPlayerList[i]);
            }
            
            CharacterListUITitle titleBar = new CharacterListUITitle();
            
            //TODO need to elim the choice from the other arrays if it has already been selected
            
            CharacterListUIButton sumbitButton = new CharacterListUIButton();
            
            display.add(panel, BorderLayout.CENTER);
            display.add(titleBar, BorderLayout.NORTH);
            display.add(sumbitButton, BorderLayout.SOUTH);
            display.setVisible(true);
   
            return true;
    }

    /**
     * buildGui creates the graphical aspect of the UI
     */
    public void buildGUI() {
        // Set frame size to house JPanels
        display.setSize(800, 700);
        display.setTitle("Cluedo");
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // BorderLayout for overall JPanel
        userDisplay.setLayout(new BorderLayout());
        // Add the input and output panels in the appropriate positions
        userDisplay.add(input, BorderLayout.SOUTH);
        userDisplay.add(output, BorderLayout.EAST);
        /* Setting the board */
        myImg = new BoardImage();
        boardImagePanel = myImg.returnPanel();
        boardImagePanel = myImg.refreshMe();

        userDisplay.add(boardImagePanel);

        // Add formatted JPanel to the frame
        display.add(userDisplay);

        /*
        TODO: Not going to use this, but I'm saving it in case we need to reference it for movement
        */
//        JPanel movementPanel = movementUpdate();
//        JOptionPane.showConfirmDialog(this, "Demonstrating Movement....");
//        userDisplay.remove(boardImagePanel);
//        userDisplay.add(movementPanel);
//        display.invalidate();
//        display.validate();
//        display.repaint();

        // Make the UI visible
        display.setVisible(true);
    }

    public void refreshDisplayForNextTurn(Token p) {
        in.whoseTurnLabel.setText("     It is now " + p.getName() + "'s turn.");
        out.updateAllowedCommandsBasedOnSquare(p);
    }

    /**
     * The user input portion of the GUI
     */
    private class UserInputBox {
        final int FIELD_WIDTH = 10;
        private JTextField inputField = new JTextField(FIELD_WIDTH);
        private JLabel whoseTurnLabel = new JLabel("     Welcome to Cluedo");
        private JLabel promptLabel = new JLabel("     What would you like to do?");
        private JButton performActionButton;
        private JButton exitChoiceButton;
        private UserInputListener returnPressListener;
        private ExitChoiceListener returnPressExitListener;

        public JPanel createInputPanel() {
            JPanel input = new JPanel();
            input.setLayout(new BorderLayout());
            JButton startGameButton = createStartGameButton();

            input.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));

            // Making it so user can press 'return' to 'Perform Action'
            returnPressListener = new UserInputListener();
            inputField.addActionListener(returnPressListener);

            // Not using this listener yet, but will have the same functionality when user is exiting a room
            returnPressExitListener = new ExitChoiceListener();

            input.add(whoseTurnLabel, BorderLayout.NORTH);
            input.add(promptLabel, BorderLayout.CENTER);
            input.add(inputField, BorderLayout.SOUTH);
            input.add(startGameButton, BorderLayout.EAST);

            inputField.requestFocus();

            return input;
        }

        public void updateInputDisplayPanel(Token p) {
            whoseTurnLabel.setText("     It it now " + p.getName() + "'s turn.");
        }

        /**
         * A button that must be pressed to start the game
         *
         * @return the button, to place into a JPanel
         */
        private JButton createStartGameButton() {
            startGameButton = new JButton("Start Game");
            ActionListener listener = new StartGameListener();
            startGameButton.addActionListener(listener);

            return startGameButton;
        }

        class StartGameListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                input.remove(startGameButton);
                performActionButton = createPerformActionButton();
                input.add(performActionButton, BorderLayout.EAST);
                whoseTurnLabel.setText("     It is now " + currentPlayer.getName() + "'s turn.");
                out.updateAllowedCommandsBasedOnSquare(currentPlayer);
                inputField.setText("");
                input.revalidate();
                output.revalidate();
            }
        }

        /**
         * Button for the user to press when they enter a command
         *
         * @return the button, to place into a JPanel
         */
        private JButton createPerformActionButton() {
            JButton performAction = new JButton("Perform Action");
            ActionListener listener = new UserInputListener();
            performAction.addActionListener(listener);

            return performAction;
        }

        /*
         * This ActionListener reads when somebody presses the "Perform Action" button
         */
        class UserInputListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                String result = GameLogic.PlayerEntry.ActionPerformer(currentPlayer, inputField.getText());

                // If user did not enter an appropriate command, show a JOptionPane telling
                // them to reenter the command then clear the input box.
                if (!GameLogic.PlayerEntry.getCommandSuccessful()) {
                    JOptionPane.showMessageDialog(null, result);
                }
                else {
                    if (currentPlayer.getLocationAsString().equals("room")) {
                        switch (result) {
                            // If player has chosen to exit a room, bring up the appropriate prompt if necessary
                            case "exitChoice":
                                /*
                                Change input and output to handle user input of an exit choice
                                 */
                                switchToExitChoiceButton();
                                return;
//                                break;
                            case "exit":
                                result = (currentPlayer.getName() + " has exited the room.");
                                break;
                            // If player is making a guess, enter the appropriate menu
                            case "guess":
                                JOptionPane.showConfirmDialog(null, "This is a placeholder panel for guessing.");
                                result = currentPlayer.getName() + " is making a guess.";
                                break;
                        }
                    }

                    if (GameLogic.PlayerEntry.wasTurnSuccessful()) {
                        out.updateMoveHistory(result);
                        if(currentPlayer.getInRoom() == null) {
                            System.out.println("Player:\t" + currentPlayer.getName() + "\tAction: " + inputField.getText()
                                    + "\t\tNew Location: " + currentPlayer.getSquareOn().getPositionAsString());

//                          /*
//                          TODO: This was my idea for movement on the board image, and it doesn't work
//                         */
//                            int[] destinationCoordinates;
//                            switch (inputField.getText()) {
//                                case "up":
//                                    destinationCoordinates = currentPlayer.getSquareOn().getAbove().getPosition();
//                                    break;
//                                case "down":
//                                    destinationCoordinates = currentPlayer.getSquareOn().getBelow().getPosition();
//                                    break;
//                                case "left":
//                                    destinationCoordinates = currentPlayer.getSquareOn().getLeft().getPosition();
//                                    break;
//                                case "right":
//                                    destinationCoordinates = currentPlayer.getSquareOn().getRight().getPosition();
//                                    break;
//                                default:
//                                    destinationCoordinates = new int[2];
//                                    System.out.println("ERROR");
//                                    break;
//                            }
//                            // TODO: Josh plz fix below
//                            boardImagePanel = movePlayerAndUpdate(currentPlayer.getPosition(), destinationCoordinates);
//                            boardImagePanel.revalidate();

                        }
                        else {
                            System.out.println("Player:\t" + currentPlayer.getName() + "\tAction: " +
                                    inputField.getText() + "\t\tNew Location: " + currentPlayer.getInRoom().getName());
                            out.updateMoveHistory(currentPlayer.getName() +
                                    " has entered the " + currentPlayer.getInRoom().getName());
                        }

                        // Switch player
//                        currentPlayer = currentPlayer.next();


                        // Update input display with that player
                        refreshDisplayForNextTurn(currentPlayer);
                    }
                    // If not, show error and do not cycle to next turn
                    else {
                        // This will be an error message if move was unsuccessful
                        JOptionPane.showMessageDialog(null, result);
                    }
                }
                GameLogic.PlayerEntry.resetSwitches();
                inputField.setText("");
                inputField.requestFocus();
            }
        }

        /**
         * This method switches user input and output to reflect that the player is going to choose an exit
         */
        public void switchToExitChoiceButton(){
            switchInputToExitPicker();
            out.roomExitChoicesUpdater();
            input.remove(performActionButton);
            input.add(createExitPickerButton(), BorderLayout.EAST);
            inputField.setText("");

            input.revalidate();
            output.revalidate();
        }

        private JButton createExitPickerButton(){
            exitChoiceButton = new JButton("Choose Exit");
            ActionListener listener = new ExitChoiceListener();
            exitChoiceButton.addActionListener(listener);

            return exitChoiceButton;
        }

        class ExitChoiceListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = -1;
                // Check to ensure the entry was an integer
                try {
                    choice = Integer.valueOf(inputField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please enter an integer value!");
                }
                // Call method in GameLogic to see if entry was valid for the number of exits
                GameLogic.PlayerEntry.checkRoomExit(currentPlayer, choice);

                // The checkRoomExit method switches 'roomExitCheck' to true if successful
                if (GameLogic.PlayerEntry.getRoomExitCheck()) {
                    out.updateMoveHistory(currentPlayer.getName() + " has exited the room.");
                    switchExitPickerToInput();
                    if(currentPlayer.getInRoom() == null) {
                        System.out.println("Player:\t" + currentPlayer.getName() + "\tAction: Exit " + choice
                                + "\t\tNew Location: " + currentPlayer.getSquareOn().getPositionAsString());
                    }
                }
            }
        }

        /**
         * This method changes the user input panel to reflect choice for exiting a room
         */
        public void switchInputToExitPicker() {
            input.remove(promptLabel);
            inputField.removeActionListener(returnPressListener);
            inputField.addActionListener(returnPressExitListener);
            promptLabel.setText("     Which exit would you like to take?");
            input.add(promptLabel, BorderLayout.CENTER);
        }

        /**
         * This method switches back to general user input
         */
        public void switchExitPickerToInput() {
            inputField.removeActionListener(returnPressExitListener);
            inputField.addActionListener(returnPressListener);
            in.inputField.setText("");
            in.inputField.requestFocus();
            input.remove(promptLabel);
            promptLabel.setText("     What would you like to do?");
            input.add(promptLabel, BorderLayout.CENTER);
            input.remove(exitChoiceButton);
            input.revalidate();
            input.add(performActionButton, BorderLayout.EAST);
            input.revalidate();
        }
    }

    // So GameLogic can clear this field for guessing and room exit
    public void clearInputField() {
        in.inputField.setText("");
        in.inputField.requestFocus();
    }

    /**
     * The user output portion of the GUI
     */
    public class OutputTextDisplay {
        JTextArea textOutput;
        JScrollPane scroller;
        JPanel allowedCommandsDisplay;
        JLabel locationReadout;
        JPanel possibleCommandsList;

        public OutputTextDisplay() {
            textOutput = new JTextArea("", 10, 15);
            textOutput.setEnabled(false);
            textOutput.setLineWrap(true);
            textOutput.setForeground(Color.BLACK);

            createAllowedCommandsDisplay();

            /*
            A static part of the output text display that will update based on where the player is,
            but stays in the same place at the top of that JPanel.
             */
            locationReadout = new JLabel("<html>Welcome to Cluedo!<br/>Possible Commands:</html>");
            locationReadout.setForeground(Color.white);
            locationReadout.setHorizontalAlignment(JLabel.CENTER);
            allowedCommandsDisplay.add(locationReadout, BorderLayout.NORTH);

            /*
            This portion of the output text display is refreshed every turn to display appropriate commands
            based on whose turn it is and where they are.
             */
            possibleCommandsList = new JPanel();
            possibleCommandsList.setBackground(Color.GRAY);
            possibleCommandsList.setLayout(new GridLayout(4, 1));
            possibleCommandsList.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
            allowedCommandsDisplay.add(possibleCommandsList, BorderLayout.CENTER);

            allowedCommandsDisplay.revalidate();

            /*
            The move history will be in a scrollable window
             */
            scroller = new JScrollPane(textOutput);
        }

        public void createAllowedCommandsDisplay() {
            allowedCommandsDisplay = new JPanel();
            allowedCommandsDisplay.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
            allowedCommandsDisplay.setLayout(new BorderLayout());
            allowedCommandsDisplay.setOpaque(true);
            allowedCommandsDisplay.setBackground(Color.GRAY);
            allowedCommandsDisplay.setForeground(Color.white);
        }

        /**
         * updateAllowedCommandsBasedOnSquare
         * When a new player's turn starts, this method ensures the output text display updates correctly
         * according to what type of square they are on.
         *
         * @param p the player whose turn it is
         */
        public void updateAllowedCommandsBasedOnSquare(Token p) {
            // The text in the readout depends on what square/room the player is on
            // p == null is for testing (hopefully), won't be in the game

            allowedCommandsDisplay.remove(possibleCommandsList);
            possibleCommandsList.removeAll();

            if (p == null)
                locationReadout.setText("Not on the board. Testing?");
            else if (p.getSquareOn() instanceof FloorSquare)
                locationReadout.setText("<html>You are on a Floor square.<br/>Possible Commands:</html>");
                // This will only be accessed after a player exits the room
            else if (p.getSquareOn() instanceof WallSquare)
                locationReadout.setText("Wall Square? Something went wrong...");
            else
                locationReadout.setText("<html>You are in the " + p.getInRoom().getName()
                        + "<br/>Possible Commands:</html>");

            try {
                if (p == null)
                    throw new Exception("Player not found error");
                if (p.getInRoom() == null) {
                    // If player is on a square, get the type of square and show their available
                    // commands based on what is available from that square.
                    switch (p.getLocationAsString()) {
                        case "floor":
                            for (String s : AcceptedUserInputs.getFloorNavigation()) {
                                JLabel d = new JLabel(s);
                                d.setForeground(Color.yellow);
                                d.setHorizontalAlignment(JLabel.CENTER);
                                possibleCommandsList.add(d);
                            }
                            break;
                        case "wall":
                            throw new Exception("Player is on a wall square.");
                        default:
                            // This case should never happen
                            throw new Exception("Error Finding Square Type");

                    }
                }
                else {
                    ArrayList<String> options = AcceptedUserInputs.getRoomNavigation();
                    for (String s : options) {
                        JLabel d = new JLabel(s);
                        d.setForeground(Color.yellow);
                        d.setHorizontalAlignment(JLabel.CENTER);
                        possibleCommandsList.add(d);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
            allowedCommandsDisplay.add(possibleCommandsList);
            allowedCommandsDisplay.revalidate();
        }

        public void roomExitChoicesUpdater(){
            allowedCommandsDisplay.remove(possibleCommandsList);
            possibleCommandsList.removeAll();

            ArrayList<Integer> choices = AcceptedUserInputs.getRoomExits(currentPlayer.getInRoom());

            for (Integer i : choices) {
                JLabel d = new JLabel("Exit " + i);
                possibleCommandsList.add(new JLabel());
                d.setForeground(Color.green);
                d.setHorizontalAlignment(JLabel.CENTER);
            }

            allowedCommandsDisplay.add(possibleCommandsList);
            allowedCommandsDisplay.updateUI();
        }

        /**
         * IMPORTANT
         * update method is called BEFORE THE GAME LOGIC HAPPENS
         * So when we call the room, or the player's square, or anything else,
         * WE CALL APPROPRIATE VARIABLES BASED ON WHERE THE PLAYER WAS AT THE START OF THEIR TURN
         *
         * @param in String created by PlayerMovementHandler (in GameLogic.PlayerEntry)
         */
        public void updateMoveHistory(String in) {
            textOutput.append(in);
            textOutput.append("\n\n");

            // Refresh the panel after updating
            output.revalidate();
        }

        public JPanel createOutputPanel() {
            JPanel output = new JPanel();
            output.setLayout(new GridLayout(2, 1));
            output.add(scroller);
            output.add(allowedCommandsDisplay);

            return output;
        }
    }

    /**
     * The graphical portion of the GUI
     */
    public JPanel boardImagePanel() {

        BufferedImage bi = null;
        BoardImage boardimage = new BoardImage();

        try {
            bi = attemptToLoadImageFromResourceFolder();
        } catch (Exception resourceLoadException) {
            resourceLoadException.printStackTrace();
        }

        return boardimage.returnPanel(bi);
    }

    public void setBoardImagePanel(JPanel panel) {
        this.boardImagePanel = panel;
    }

    /*
    This is a tester class for movement
     */
    public JPanel movementUpdate() {
        BufferedImage bi = null;
        BoardImage boardimage = new BoardImage();

        /*
        This version is a hopefully more mobile version of the image loading method
         */
        try {
            bi = attemptToLoadImageFromResourceFolder();
        } catch (Exception resourceLoadException) {
            resourceLoadException.printStackTrace();
        }

        JPanel tempPanel = boardimage.returnPanel(bi);

        tempPanel = boardimage.move(0, 9, 1, 9);
        return tempPanel;
    }

    public JPanel movePlayerAndUpdate(int[] from, int[] to) {
        BufferedImage bi = null;
        BoardImage boardimage = new BoardImage();

        /*
        This version is a hopefully more mobile version of the image loading method
         */
        try {
            bi = attemptToLoadImageFromResourceFolder();
        } catch (Exception resourceLoadException) {
            resourceLoadException.printStackTrace();
        }

        JPanel tempPanel = boardimage.returnPanel(bi);

        tempPanel = boardimage.move(from, to);
        return tempPanel;
    }

    /**
     * attemptToLoadImageFromResourceFolder
     * This method pulls the URL/path from an image name and loads that into a buffered image
     *
     * @return a buffered image loaded from the hard-coded URL
     * @throws Exception Prints a stack trace in boardImagePanel if the image is not found
     */
    public BufferedImage attemptToLoadImageFromResourceFolder() throws Exception {
        URL imageUrl = this.getClass().getResource("board1.jpg");
        return ImageIO.read(imageUrl);
    }


    /**
     * Update the currentPlayer variable within the UI class
     */
    public void setCurrentPlayer(Token player) {
        this.currentPlayer = player;
    }
    
    public Tokens returnPlayerArray() {
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

        JList list = new JList(items);
        
        public CharacterList(int i) {            	
        	super(new BorderLayout(5, 5));
            this.objNum = i;
            JList list = new JList(items);
        	
            /* TODO need to figure out how to remove the other values in the list  */
            list.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent lse) {
                    willThisWork = ((String) list.getSelectedValue());
                    
                    /* If the player actually selected a character, we need to remove it from the other lists */
                    // if we select a player, it needs to be removed. BUT if we change our mind, the player needs to come back to the menu
                    if (!(willThisWork.equals("Not Playing"))) {
                    	for (int i = 0; i < 6; i++) {
                    		/* If the obj in playerList is  not the current one */
                    		if (GUIPlayerList[i].objNum != objNum) {
                    			/* Grabbing the current characterList */
                    			String[] tempItems = GUIPlayerList[i].items;
                    			int numMatches = 0;
                    			
                    			System.out.println("\n\n");
                    			for (int g = 0; g < tempItems.length; g++) {
                    				System.out.println(tempItems[g]);
                    			}
                    			System.out.println("\n\n");
                    			
                    			/* Counting the number of elements in the  */
                    			String[] newList = new String[6];
                    			
                    			for (int h  = 0; h < 6; h++) {
                    				System.out.println(h);
                    				if (!willThisWork.equals(tempItems[h])) {
                    					newList[numMatches] = tempItems[h];
                    					numMatches++;
                    				}
                    			}
                    			
                    			System.out.println("does this work/");
                    			/* Updating the lists of the obj */
                    			
                    			/* Adding the element to the removedList */
                    			
                    			/* Checking to see if any of the names need to be re-added (if so we re-add them) */
                    		}
                    	}
                    }
                }
            });

            add(list, BorderLayout.CENTER);

            value = new JTextField("", 20);
            add(value, BorderLayout.EAST);
        }
        //TODO: remove one of the objects in the list, then update it

        public String[] getValue() {
            String[] valueArray = new String[2];
            valueArray[0] = this.value.getText();
            valueArray[1] = this.willThisWork;
            return valueArray;
        }
    }
 
    
    
    
    
    
    class CharacterListUITitle extends JPanel { //going to hold the UI 
    	JLabel myLabel = new JLabel("[Left] Select Character and Type Username [Right]");
    	
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
    	/* Now that this button has been pressed, we need to loop through the GUIPlayers list */
    	public void actionPerformed(ActionEvent arg0) {
    		//1. Loop though the GUI players list
    		//2. We create some array of playerNames, and an array of booleans (they will reference one another as validation that all players have been 
  
    		/* Looping through the GUIPlayers list, if we get the player names to !=  */
    		int numPlayers = 0;
    	
    		/* Token Objs that will make sure we only create ONE of each character */
    		Token white = null;
           	Token green = null;
           	Token mustard = null;
           	Token peacock = null;
           	Token plum = null;
           	Token scarlet = null;	    
           	
    		for (int i = 0; i < 6; i++) {
    			String[] returnArray = GUIPlayerList[i].getValue();
    			
    			/* If the user wants to actually play, the above token objects get populated */
    			if (!(returnArray[1].equals("Not Playing"))){ 
        			
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
    		
    		buildGUI(); 
    	}
    }
}


   



    
    
    
  
    
 

    

