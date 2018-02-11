package cluedo_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    // Text output portion of the display, generated in the same way as user input
    private OutputTextDisplay out = new OutputTextDisplay();
    private JPanel output = out.createOutputPanel();
    // The graphical component of the display
//    private BoardPanel image = new BoardPanel();
//    private JPanel imagePanel = image.getBoardImagePanel();
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

        /*
        Temporary tester printing
         */
        for(Token p : playerList){
            System.out.println("Name: " + p.getName());
            System.out.println("\tLocation: " + p.getSquareOn().toString());
        }



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
        userDisplay.add(input, BorderLayout.SOUTH);
        userDisplay.add(output, BorderLayout.EAST);
        // Placeholder panel for image
        userDisplay.add(placeHolder());
//        userDisplay.add(imagePanel, BorderLayout.CENTER);

        // Add formatted JPanel to the frame
        display.add(userDisplay);
        // Make the UI visible
        display.setVisible(true);
    }

    /**
     * Update the currentPlayer variable within the UI class
     */
    public void setCurrentPlayer(Token player){
        this.currentPlayer = player;
    }

//    public static class Listeners {
//
//    }

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
        private JPanel inputPanel;

        public UserInputBox(){
            inputPanel = createInputPanel();
        }

        class UserInputListener_FloorSquare implements ActionListener {
            public void actionPerformed(ActionEvent event){
                // Only do this if the input field had something in it
                if(!(inputField.getText().equals(""))) {
                    out.update(output, inputField.getText());
                    System.out.println("You have performed the action: " + inputField.getText());
                    inputField.setText("");
                    // Increment index for player list based on number of players (in boardbuilder)
                    playerListIndex++;
                    currentPlayer = playerList[playerListIndex%(BoardBuilder.getNumPlayers())];
                    // Update input display with that player
                    refreshDisplay(currentPlayer);
                }
                inputField.requestFocus();
            }
        }

        private JButton createPerformActionButton(){
            JButton performAction = new JButton("Perform Action");
            ActionListener listener = new UserInputListener_FloorSquare();
            performAction.addActionListener(listener);

            return performAction;
        }

        public JPanel createInputPanel(){
            JPanel input = new JPanel();
            input.setLayout(new BorderLayout());

            input.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));

            input.add(whoseTurnLabel, BorderLayout.NORTH);
            input.add(promptLabel, BorderLayout.CENTER);
            input.add(inputField, BorderLayout.SOUTH);
            input.add(createPerformActionButton(), BorderLayout.EAST);

            inputField.requestFocus();


            return input;
        }

        public void updateInputDisplayPanel(Token p){
            whoseTurnLabel.setText("     It it now " + p.getName() + "'s turn.");
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
        JLabel possibleCommandsString;
        JLabel commandsList;

        public OutputTextDisplay(){
            textOutput = new JTextArea("", 10, 15);
            textOutput.setEnabled(false);
            textOutput.setLineWrap(true);

            createAllowedCommandsDisplay();

            locationReadout = new JLabel("Welcome to Cluedo!");
            locationReadout.setForeground(Color.white);
            locationReadout.setHorizontalAlignment(JLabel.CENTER);
            allowedCommandsDisplay.add(locationReadout);

            possibleCommandsString = new JLabel("Possible Commands:");
            possibleCommandsString.setForeground(Color.white);
            possibleCommandsString.setHorizontalAlignment(JLabel.CENTER);
            allowedCommandsDisplay.add(possibleCommandsString);

            allowedCommandsDisplay.revalidate();

            scroller = new JScrollPane(textOutput);
        }

        public void createAllowedCommandsDisplay(){
            allowedCommandsDisplay = new JPanel();
            allowedCommandsDisplay.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            allowedCommandsDisplay.setLayout(new GridLayout(10, 1));
            allowedCommandsDisplay.setOpaque(true);
            allowedCommandsDisplay.setBackground(Color.GRAY);
            allowedCommandsDisplay.setForeground(Color.white);
        }

        public void updateAllowedCommandsBasedOnSquare(Token p) {
            // The text in the readout depends on what square/room the player is on
            // p == null is for testing, won't be in the game
            if(p == null)
                locationReadout.setText("Not on the board. Testing?");
            else if(p.getSquareOn() instanceof FloorSquare)
                locationReadout.setText("You are on a Floor square.");
            // This will only be accessed after a player exits the room
            // Because when a player enters this square from a floor square, they are immediately taken to the room
            else if(p.getSquareOn() instanceof EntrySquare)
                locationReadout.setText("You are on an Entry square to: "
                        + ((EntrySquare) p.getSquareOn()).getRoomName());
            else if(p.getSquareOn() instanceof WallSquare)
                locationReadout.setText("Wall Square? Something went wrong...");
            else
                locationReadout.setText("You are in the " + p.getInRoom().getName());

            /*
            List of possible commands based on square
            TODO: Remove, repopulate, re-add
            This list of commands currently just keeps adding on rather than revalidating!
             */
//            allowedCommandsDisplay.remove(commandsList);

            try {
                if(p == null)
                    throw new Exception("Player found error");
                if (p.getInRoom() == null) {
                    switch (p.getSquareOn().getSquareType()) {
                        case "floor":
                            for (String s : INPUTS_LIST.getFloorNavigation()) {
                                JLabel d = new JLabel(s);
                                d.setForeground(Color.white);
                                d.setHorizontalAlignment(JLabel.CENTER);
                                allowedCommandsDisplay.add(d);
                            }
                            break;
                        case "entry":
                            for (String s : INPUTS_LIST.getEntryChoices()) {
                                JLabel d = new JLabel(s);
                                d.setForeground(Color.white);
                                d.setHorizontalAlignment(JLabel.CENTER);
                                allowedCommandsDisplay.add(d);
                            }
                            break;
                        case "wall":
                            throw new Exception("Player is on a wall square.");
                        default:
                            // Testing case for board creation
                            for (String s : INPUTS_LIST.getFloorNavigation()) {
                                JLabel d = new JLabel(s);
                                d.setForeground(Color.yellow);
                                d.setHorizontalAlignment(JLabel.CENTER);
                                allowedCommandsDisplay.add(d);
                            }
                            break;

                    }
                }
                else {
                    ArrayList<String> options = INPUTS_LIST.getFloorNavigation();
                    for (String s : options) {
                        JLabel d = new JLabel(s);
                        d.setForeground(Color.white);
                        d.setHorizontalAlignment(JLabel.CENTER);
                        allowedCommandsDisplay.add(d);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
            allowedCommandsDisplay.revalidate();
        }

        public void update(JPanel panel, String in){

            textOutput.append(in);
            textOutput.append("\n\n");

            panel.revalidate();
        }

        public JPanel createOutputPanel(){
            JPanel output = new JPanel();
            output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
            output.add(scroller);
            output.add(allowedCommandsDisplay);

            return output;
        }
    }

    /**
     * The graphical portion of the GUI
     */
//    private class BoardPanel{
//        private BoardImage boardImage;
//        private JPanel boardImagePanel;
//
//        public BoardPanel(){
//            boardImage = new BoardImage();
//            this.boardImagePanel = this.boardImage.returnBoardPanel();
//            boardImagePanel.setBackground(Color.BLACK);
//
//        }
//
//        public JPanel getBoardImagePanel() {
//            return boardImagePanel;
//        }
//    }

// This update method for the text output might not be used
//    public void updateOutput(String newText){
//        out.update(output, newText);
//    }

    /*
    A placeholder JPanel while the image is being worked on
     */
    public JPanel placeHolder(){
        JPanel tempPanel = new JPanel();
        JLabel temp = new JLabel("Placeholder");
        tempPanel.add(temp);
        return tempPanel;
    }


}
