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
    // Text output portion of the display, generated in the same way as user input
    private OutputTextDisplay out = new OutputTextDisplay();
    private JPanel output = out.createOutputPanel();

    // The overall display panel that will control layout of the 3 panels
    private JPanel userDisplay = new JPanel();

    // Pointer to player whose turn it is. When we add 'turns', the turn object will send info to this
    private Token currentPlayer;
    private Tokens playerList;

    BoardBuilder gameBoard;

    /**
     * The constructor for the UI which will set off a chain of events drawing all of the components
     * Everything so far is done in buildGUI, but when we add game logic it will also(?) be contained here
     */
    public UserInterface() {
        // Placeholder board and players for testing
        gameBoard = new BoardBuilder();



        /* This is going to happen AFTER the start game button is pressed */
        this.playerList = BoardBuilder.getPlayerList();
        this.currentPlayer = playerList.getFirst();
        //this.buildGUI();
        this.createPlayersGUI();
    }

    public void createPlayersGUI() {
        showCharacterInput testMe = new showCharacterInput();
    }

    /**
     * buildGui creates the graphical aspect of the UI
     */
    public void buildGUI() {
        // Set list of user input possibilities
        // TODO: This might be moved to game logic?
        AcceptedUserInputs.setAcceptedUserInputs();
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
        BoardImage myImg = new BoardImage();
        JPanel boardImagePanel = myImg.returnPanel();
        boardImagePanel = myImg.refreshMe();

        userDisplay.add(boardImagePanel);

        JPanel movementPanel = movementUpdate();

        // Add formatted JPanel to the frame
        display.add(userDisplay);
        // Make the UI visible
        display.setVisible(true);

        /*  */
        JOptionPane.showConfirmDialog(this, "Demonstrating Movement....");

        userDisplay.remove(boardImagePanel);
        userDisplay.add(movementPanel);
        display.invalidate();
        display.validate();
        display.repaint();
    }

    public void refreshDisplayForNextTurn(Token p) {
        in.whoseTurnLabel.setText("     It is now " + p.getName() + "'s turn.");
        out.updateAllowedCommandsBasedOnSquare(p);
    }
    /*
    TODO: It would be cool to show the user which square they could exit to on the board image
     */
    public void refreshDisplayForRoomExitChoice(Token p) {
        in.promptLabel.setText("     Which exit would you like to take?");

        out.possibleCommandsList.removeAll();
        for (int i=0; p.getInRoom().getExits().get(i)!=null; i++) {
            JLabel d = new JLabel(String.valueOf(i+1));
            d.setForeground(Color.yellow);
            d.setHorizontalAlignment(JLabel.CENTER);
            out.possibleCommandsList.add(d);
        }
        out.allowedCommandsDisplay.revalidate();
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

            input.add(whoseTurnLabel, BorderLayout.NORTH);
            input.add(promptLabel, BorderLayout.CENTER);
            input.add(inputField, BorderLayout.SOUTH);
            input.add(startGameButton, BorderLayout.EAST);

            inputField.requestFocus();

            return input;
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
                if (!GameLogic.PlayerEntry.getCommandSuccessful()){
                    JOptionPane.showConfirmDialog(null, result);
                }

                if (currentPlayer.getLocationAsString().equals("room")) {
                    switch (result) {
                        // If player has chosen to exit a room, bring up the appropriate prompt if necessary
                        case "exitChoice":
                            refreshDisplayForRoomExitChoice(currentPlayer);
                            // TODO: Call a method in GameLogic for user to choose an exit
                            
                            result = (currentPlayer.getName() + " has exited the room.");

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
                    System.out.println("Action: " + inputField.getText());
                    currentPlayer = currentPlayer.next();
                    // Update input display with that player
                    refreshDisplayForNextTurn(currentPlayer);
                }
                // If not, show error and do not cycle to next turn
                else {
                    // This will be an error message if move was unsuccessful
                    JOptionPane.showConfirmDialog(null, result);
                }
                GameLogic.PlayerEntry.resetSwitches();
                inputField.setText("");
                inputField.requestFocus();
            }
        }

        public void switchInputToExitPicker(Room room){
            JPanel exits = new JPanel();
            exits.setLayout(new GridLayout(3,1));

        }

        class ExitChoiceListener implements ActionListener {
            private int choice;

            public ExitChoiceListener(int choice) {
                
            }

            public int getExitChoice(int choice){
                return choice;
            }
            @Override
            public void actionPerformed(ActionEvent e) {

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
                if (p == null)
                    locationReadout.setText("Not on the board. Testing?");
                else if (p.getSquareOn() instanceof FloorSquare)
                    locationReadout.setText("<html>You are on a Floor square.<br/>Possible Commands:</html>");
                    // This will only be accessed after a player exits the room
                else if (p.getSquareOn() instanceof WallSquare)
                    locationReadout.setText("Wall Square? Something went wrong...");
                else
                    locationReadout.setText("You are in the " + p.getInRoom().getName()
                            + "<html><br/>Possible Commands:</html>");

                try {
                    if (p == null)
                        throw new Exception("Player not found error");
                    if (p.getInRoom() == null) {
                        // If player is on a square, get the type of square and show their available
                        // commands based on what is available from that square.
                        switch (p.getLocationAsString()) {
                            case "floor":
                                possibleCommandsList.removeAll();
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
                                possibleCommandsList.removeAll();
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
            public void updateMoveHistory( String in) {
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

        /* Inner classes that will be useful later */
        class CharacterList extends JPanel {

            private JTextField value;
            String willThisWork;

            CharacterList(String[] items) {
                super(new BorderLayout(5, 5));

                JList list = new JList(items);
                list.addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent lse) {
                        willThisWork = ((String) list.getSelectedValue());
                    }
                });
                add(list, BorderLayout.CENTER);

                value = new JTextField("", 20);
                add(value, BorderLayout.NORTH);
            }

            public String[] getValue() {
                String[] valueArray = new String[2];
                valueArray[0] = this.value.getText();
                valueArray[1] = this.willThisWork;
                return valueArray;
            }
        }

        class showCharacterInput {

            public showCharacterInput() {
                Runnable r = new Runnable() {

                    public void run() {
                        String[] items = {
                                "Miss Scarlett",
                                "Colonel Mustard",
                                "Mrs White",
                                "Mr Green",
                                "Mrs Peacock",
                                "Professor Plum",
                        };

                        // what was requested
                        CharacterList elp = new CharacterList(items);
                        JOptionPane.showMessageDialog(null, elp);
                        String[] valueArray = elp.getValue();

                        System.out.println("EditableListPanel value: " + valueArray[0]);
                        System.out.println(" Value: " + valueArray[1]);
                    }
                };
                SwingUtilities.invokeLater(r);
            }
        }

    }
