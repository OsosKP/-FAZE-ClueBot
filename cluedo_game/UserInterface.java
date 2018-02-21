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
    private int playerListIndex;

    private  CharacterList[] GUIPlayerList = null;
    
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
       
//        this.createPlayersGUI();
        this.buildGUI();
    }

    public void createPlayersGUI() {
    	 
            String[] items = {
            		"Miss Scarlett",
                    "Colonel Mustard",
                    "Mrs White",
                    "Mr Green",
                    "Mrs Peacock",
                    "Professor Plum",
                    "Not Playing"
            };
                     
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
            	GUIPlayerList[i] = new CharacterList (items);
            	panel.add(GUIPlayerList[i]);
            }
            
            CharacterListUITitle titleBar = new CharacterListUITitle();
            //TODO --Priority-- need to generate the players when we hit the submit button
            
            //TODO need to elim the choice from the other arrays if it has already been selected
            
            CharacterListUIButton sumbitButton = new CharacterListUIButton();
            
            display.add(panel, BorderLayout.CENTER);
            display.add(titleBar, BorderLayout.NORTH);
            display.add(sumbitButton, BorderLayout.SOUTH);
            display.setVisible(true);
   
    
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

        public JPanel createInputPanel() {
            JPanel input = new JPanel();
            input.setLayout(new BorderLayout());
            JButton startGameButton = createStartGameButton();

            input.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));

            // Making it so user can press 'return' to 'Perform Action'
            UserInputListener returnPressListener = new UserInputListener();
            inputField.addActionListener(returnPressListener);

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
                input.add(createPerformActionButton(), BorderLayout.EAST);
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
                                switchInputToExitPicker(currentPlayer.getInRoom());
                                result = (currentPlayer.getName() + " has exited the room.");
                                // Switch panel back to input
                                userDisplay.remove(exits);
                                userDisplay.add(input);
                                userDisplay.updateUI();
                                break;
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

                    // If the turn was successful, cycle to next turn
                    // TODO: Move this to a GameLogic method so all this work isn't done here
                    if (GameLogic.PlayerEntry.wasTurnSuccessful()) {
                        out.updateMoveHistory(result);
                        if(currentPlayer.getInRoom() == null) {
                            System.out.println("Player:\t" + currentPlayer.getName() + "\tAction: " + inputField.getText()
                                    + "\t\tNew Location: " + currentPlayer.getSquareOn().getPositionAsString());

//                            /*
//                        TODO: This was my idea for movement on the board image, and it doesn't work
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
                        else
                            System.out.println("Player:\t" + currentPlayer.getName() + "\tAction: " + inputField.getText()
                                    + "\t\tNew Location: " + currentPlayer.getInRoom().getName());

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

        public void switchInputToExitPicker(Room room) {
            exits = new JPanel();
            exits.setLayout(new GridLayout(3, 1));
            int i = 1;

            for (FloorSquare square : room.getExits()) {
                JButton exitChoice = new JButton("Exit " + i);
                ExitChoiceListener listener = new ExitChoiceListener(i);
                exitChoice.addActionListener(listener);
                exits.add(exitChoice);
            }
            out.allowedCommandsDisplay.removeAll();
            out.allowedCommandsDisplay.revalidate();
            userDisplay.remove(input);
            userDisplay.add(exits, BorderLayout.SOUTH);
            userDisplay.updateUI();
        }

        public void switchExitPickerToInput() {
            userDisplay.remove(exits);
            userDisplay.add(input);
            userDisplay.updateUI();
        }

        class ExitChoiceListener implements ActionListener {
            private int choice;

            public ExitChoiceListener(int choice) {
                this.choice = choice;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlayer.exitRoom(choice);
            }
        }


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
}


   /* Inner classes that will be useful later */
    class CharacterList extends JPanel {

        private JTextField value;
        String willThisWork;
        
        public CharacterList() {
			// TODO Auto-generated constructor stub
		}

        CharacterList(String[] items) {
            super(new BorderLayout(5, 5));

            JList list = new JList(items);
            /* TODO need to figure out how to remove the other values in the list  */
            list.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent lse) {
                    willThisWork = ((String) list.getSelectedValue());
                }
            });

            add(list, BorderLayout.CENTER);

            value = new JTextField("", 20);
            add(value, BorderLayout.EAST);
        }

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
    	/* Where the magic happens  */
    	public void actionPerformed(ActionEvent arg0) {
    		System.out.println("Stuff didnt break!");
    	}
    }


    
    
    
  
    
 

    

