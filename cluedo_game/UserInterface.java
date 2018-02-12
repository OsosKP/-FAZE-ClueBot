package cluedo_game;

import clojure.lang.Obj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * UserInterface
 * This class is the JFrame that will hold all of the individual JPanels:
 *  Game Board
 *  Input Box
 *  Text Display Box
 */
public class UserInterface extends JPanel {
    // Acceptable user inputs
    AcceptedUserInputs INPUTS_LIST;
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
    private Token[] playerList;
    private int playerListIndex;

    BoardBuilder gameBoard;

    /**
     * The constructor for the UI which will set off a chain of events drawing all of the components
     * Everything so far is done in buildGUI, but when we add game logic it will also(?) be contained here
     */
    public UserInterface(){
        // Placeholder board and players for testing
        playerListIndex = 0;
        gameBoard = new BoardBuilder();
        this.playerList = BoardBuilder.getPlayerList();

        this.currentPlayer = playerList[0];
        this.buildGUI();
    }

    /**
     * buildGui creates the graphical aspect of the UI
     */
    public void buildGUI(){
        // Load list of acceptable commands
        INPUTS_LIST = new AcceptedUserInputs();
        // Set frame size to house JPanels
        display.setSize(800, 700);
        display.setTitle("Cluedo");
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // BorderLayout for overall JPanel
        userDisplay.setLayout(new BorderLayout());
        // Add the input and output panels in the appropriate positions
        userDisplay.add(input, BorderLayout.SOUTH);
        userDisplay.add(output, BorderLayout.EAST);
<<<<<<< HEAD
        // Image panel
        JPanel boardImagePanel = boardImagePanel();
=======
        // Placeholder panel for image
        BoardImage myImg = new BoardImage();
        
        JPanel boardImagePanel = myImg.returnPanel(); 
        boardImagePanel = myImg.refreshMe();

>>>>>>> 8a7f733e805b46fe86536d6af17a7900e45c5231
        userDisplay.add(boardImagePanel);

        JPanel movementPanel = movementUpdate();

        // Add formatted JPanel to the frame
        display.add(userDisplay);
        // Make the UI visible
        display.setVisible(true);

        /*  */
        JOptionPane.showConfirmDialog(this, "Demonstrating Movement....");
      
        display.setVisible(false);
        userDisplay.remove(boardImagePanel);
        userDisplay.add(movementPanel);
        display.setVisible(true);
    }

    public void refreshDisplay(Token p){
        in.updateInputDisplayPanel(p);
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

        public JPanel createInputPanel(){
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

        public void updateInputDisplayPanel(Token p){
            whoseTurnLabel.setText("     It it now " + p.getName() + "'s turn.");
        }

        /**
         * A button that must be pressed to start the game
         * @return the button, to place into a JPanel
         */
        private JButton createStartGameButton(){
            startGameButton = new JButton("Start Game");
            ActionListener listener = new StartGameListener();
            startGameButton.addActionListener(listener);

            return startGameButton;
        }
        class StartGameListener implements ActionListener {
            public void actionPerformed (ActionEvent event){
                input.remove(startGameButton);
                input.add(createPerformActionButton(), BorderLayout.EAST);
                updateInputDisplayPanel(currentPlayer);
                out.updateAllowedCommandsBasedOnSquare(currentPlayer);
                inputField.setText("");
                input.revalidate();
                output.revalidate();
            }
        }
        /**
         * Button for the user to press when they enter a command
         * @return the button, to place into a JPanel
         */
        private JButton createPerformActionButton(){
            JButton performAction = new JButton("Perform Action");
            ActionListener listener = new UserInputListener();
            performAction.addActionListener(listener);

            return performAction;
        }
        /**
         * This ActionListener reads when somebody pressed the "Perform Action" button
         */
        class UserInputListener implements ActionListener {
            public void actionPerformed(ActionEvent event){
                // Only do this if the input field had something in it
                if(INPUTS_LIST.checkForValidUserInputNavigation(currentPlayer, inputField.getText())) {
                    // Check that the player has entered a correct navigational command
                    out.update(output, inputField.getText());
                    System.out.println("You have performed the action: " + inputField.getText());
                    // Increment index for player list based on number of players (in boardbuilder)
                    playerListIndex++;
                    currentPlayer = playerList[playerListIndex%(BoardBuilder.getNumPlayers())];
                    // Update input display with that player
                    refreshDisplay(currentPlayer);
                }
                inputField.setText("");
                inputField.requestFocus();
            }
        }
    }

    /**
     * The user output portion of the GUI
     */
    private class OutputTextDisplay{
        JTextArea textOutput;
        JScrollPane scroller;
        JPanel allowedCommandsDisplay;
        JLabel locationReadout;
        JPanel possibleCommandsList;

        public OutputTextDisplay(){
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

        public void createAllowedCommandsDisplay(){
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
         * @param p the player whose turn it is
         */
        public void updateAllowedCommandsBasedOnSquare(Token p) {
            // The text in the readout depends on what square/room the player is on
            // p == null is for testing (hopefully), won't be in the game
            if(p == null)
                locationReadout.setText("Not on the board. Testing?");
            else if(p.getSquareOn() instanceof FloorSquare)
                locationReadout.setText("<html>You are on a Floor square.<br/>Possible Commands:</html>");
            // This will only be accessed after a player exits the room
            // Because when a player enters this square from a floor square, they are immediately taken to the room
            else if(p.getSquareOn() instanceof EntrySquare)
                locationReadout.setText("<html>You are on an Entry square to: </br></html>"
                        + ((EntrySquare) p.getSquareOn()).getRoomName() + "<html><br/>Possible Commands:</html>");
            else if(p.getSquareOn() instanceof WallSquare)
                locationReadout.setText("Wall Square? Something went wrong...");
            else
                locationReadout.setText("You are in the " + p.getInRoom().getName() + "<html><br/>Possible Commands:</html>");

            try {
                if(p == null)
                    throw new Exception("Player found error");
                if (p.getInRoom() == null) {
                    // If player is on a square, get the type of square and show their available
                    // commands based on what is available from that square.
                    switch (p.getSquareOn().toString()) {
                        case "floor":
                            possibleCommandsList.removeAll();
                            for (String s : INPUTS_LIST.getFloorNavigation()) {
                                JLabel d = new JLabel(s);
                                d.setForeground(Color.yellow);
                                d.setHorizontalAlignment(JLabel.CENTER);
                                possibleCommandsList.add(d);
                            }
                            break;
                        case "entry":
                            possibleCommandsList.removeAll();
                            for (String s : INPUTS_LIST.getEntryChoices()) {
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
                    ArrayList<String> options = INPUTS_LIST.getFloorNavigation();
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
         * @param panel output panel that is being updated
         * @param in command inputted by user
         */
        public void update(JPanel panel, String in){
            textOutput.append(currentPlayer.getName());
            switch(in.toLowerCase()){
                case "up":
                case "down":
                case "left":
                case "right":
                    textOutput.append(" has moved ");
                    textOutput.append(in);
                    break;
                case "enter":
                    // This update will display once the player enters the room - it is the only one like this
                    break;
                case "exit":
                    textOutput.append(" has exited the ");
                    textOutput.append(currentPlayer.getInRoom().getName());
                    break;
                case "passage":
                    textOutput.append(" is taking a secret passage to the ");
                    textOutput.append(currentPlayer.getInRoom().getsecretPassage().getName());
                case "guess":
                    textOutput.append(" is making a guess...");
            }
            textOutput.append("\n\n");

            // Refresh the panel after updating
            panel.revalidate();
        }

        public JPanel createOutputPanel(){
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
    public JPanel boardImagePanel(){

        BufferedImage bi = null;
        BoardImage boardimage = new BoardImage();

        try {
            bi = attemptToLoadImageFromResourceFolder();
        } catch (Exception resourceLoadException){
            resourceLoadException.printStackTrace();
        }

        return boardimage.returnPanel(bi);
    }

    public JPanel movementUpdate(){
        BufferedImage bi = null;
        BoardImage boardimage = new BoardImage();

        /*
        This version is a hopefully more mobile version of the image loading method
         */
        try {
            bi = attemptToLoadImageFromResourceFolder();
        } catch (Exception resourceLoadException){
            resourceLoadException.printStackTrace();
        }

        JPanel tempPanel = boardimage.returnPanel(bi);

        tempPanel = boardimage.move(0,9,1,9);
        return tempPanel;
    }

    /**
     * attemptToLoadImageFromResourceFolder
     * This method pulls the URL/path from an image name and loads that into a buffered image
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
    public void setCurrentPlayer(Token player){
        this.currentPlayer = player;
    }

}
