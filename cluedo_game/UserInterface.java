package cluedo_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private static JButton startGameButton;

    // Text output portion of the display, generated in the same way as user input
    private OutputTextDisplay out = new OutputTextDisplay();
    private JPanel output = out.createOutputPanel();

    private QuestionMenu initialQuestion = null;

    // The board image portion of the UI
    JPanel boardImagePanel;
    BoardImage myImg;
    JLabel PlayerCards[] = new JLabel[3];

    // The questioning panel, which replaces the input panel
//    private JPanel question = in.createQuestionPanel();

    // The overall display panel that will control layout of the 3 panels
    private JPanel userDisplay = new JPanel();

    // Pointer to player whose turn it is
    private Token currentPlayer;
    private Tokens playerList;

    private JButton questionDoneButton;


    /**
     * The constructor for the UI which will set off a chain of events drawing all of the components
     * Everything so far is done in buildGUI, but when we add game logic it will also(?) be contained here
     */
    public UserInterface(Tokens playerList) {
        /* This is going to happen AFTER the start game button is pressed */
        this.playerList = playerList;
        this.currentPlayer = playerList.getFirst();
        this.buildGUI();
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

        // Center display
        display.setLocationRelativeTo(null);

        // Make the UI visible
        display.setVisible(true);
    }

    public JFrame getDisplay() {
        return display;
    }

    public JPanel getUserDisplay() {
        return userDisplay;
    }

    public void refreshGuiFromUnsuccessfulGuess() {
        initialQuestion.revertToRegularDisplay();
        in.inputField.setText("");
        in.inputField.requestFocus();
    }

    public void refreshDisplayForNextTurn(Token p) {
        // Tell players whose turn it is
        in.whoseTurnLabel.setText("     It is now " + p.getName() + "'s turn. Moves Left: " + GameLogic.getMovesLeft());
        in.inputField.requestFocus();
        // Update what player is allowed to input
        out.updateAllowedCommandsBasedOnSquare(p);
    }

    public void setCurrentPlayer(Token t) {
        currentPlayer = t;
    }

    public Token getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the QuestionMenu Object -- Note that the return type for the array: characterName who we guessed | weapon that was guessed | character who made the guess
     * @return the most recent questionMenu object that was created, will return null if the user has not asked a question yet
     */
    public QuestionMenu getQuestionGUI() {
    	return this.initialQuestion;
    }

    /**
     * The user input portion of the GUI
     */
    private class UserInputBox {
        final int FIELD_WIDTH = 10;
        private JTextField inputField = new JTextField(FIELD_WIDTH);
        private JLabel whoseTurnLabel = new JLabel("     Welcome to Cluedo");
        private JLabel promptLabel = new JLabel("     Please press start when ready to play.");
        private JButton performActionButton;
        private JButton exitChoiceButton;
        private JButton viewNotesButton;
        private StartGameListener returnStartGameListener;
        private UserInputListener returnPressListener;
        private ExitChoiceListener returnPressExitListener;
        private ViewNotesListener returnPressViewNotesListener;
        private ArrowListener arrows;

        public JPanel createInputPanel() {
            JPanel input = new JPanel();
            input.setLayout(new BorderLayout());
            JButton startGameButton = createStartGameButton();
            arrows = new ArrowListener();

            input.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));

            // Making it so user can press 'return' to 'Perform Action'
            returnPressListener = new UserInputListener();
            // But at the beginning, pressing 'return' should start the game
            returnStartGameListener = new StartGameListener();
            inputField.addActionListener(returnStartGameListener);

            returnPressExitListener = new ExitChoiceListener();
            returnPressViewNotesListener = new ViewNotesListener();

            input.add(whoseTurnLabel, BorderLayout.NORTH);
            input.add(promptLabel, BorderLayout.CENTER);
            input.add(inputField, BorderLayout.SOUTH);
            input.add(startGameButton, BorderLayout.EAST);

            inputField.requestFocus();

            return input;
        }

        public void refreshBoard(JPanel update){
        	//Testing shit around n stuff
        	JButton[] currentPlayerCards = new JButton[3];
            currentPlayerCards[0] = new JButton();
        	currentPlayerCards[1] = new JButton();
        	currentPlayerCards[2] = new JButton();

			currentPlayerCards[0].setBorder(null);
        	currentPlayerCards[1].setBorder(null);
        	currentPlayerCards[2].setBorder(null);

        	currentPlayerCards[0].setIcon(new ImageIcon(currentPlayer.getHand().get(0).getImage()));
			currentPlayerCards[1].setIcon(new ImageIcon(currentPlayer.getHand().get(1).getImage()));
			currentPlayerCards[2].setIcon(new ImageIcon(currentPlayer.getHand().get(2).getImage()));

			JPanel cardsPanel = new JPanel(new GridLayout(3,1));
        	cardsPanel.add(currentPlayerCards[0]);
        	cardsPanel.add(currentPlayerCards[1]);
        	cardsPanel.add(currentPlayerCards[2]);

        	JPanel bigPanel = new JPanel();
        	bigPanel.add(cardsPanel);
        	bigPanel.add(boardImagePanel);

            userDisplay.remove(boardImagePanel);
            boardImagePanel = update;
            userDisplay.add(boardImagePanel);
//            userDisplay.add(bigPanel); //TODO: UNCOMMENT TO DISPLAY CARDS
            display.invalidate();
            display.validate();
            display.repaint();
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
                GameLogic.Dice.rollForTurn();
                input.remove(startGameButton);
                performActionButton = createPerformActionButton();
                inputField.removeActionListener(returnStartGameListener);
                inputField.addActionListener(returnPressListener);
                inputField.addKeyListener(arrows);
                input.add(performActionButton, BorderLayout.EAST);
                whoseTurnLabel.setText("     It is now " + currentPlayer.getName() + "'s turn. Moves Left: "
                        + GameLogic.getMovesLeft());
                in.promptLabel.setText("     What would you like to do?");
                out.updateAllowedCommandsBasedOnSquare(currentPlayer);
                inputField.setText("");
                input.revalidate();
                output.revalidate();
            }
        }
        private JButton createViewNotesButton(){
            viewNotesButton = new JButton("Done");
            ActionListener listener = new ViewNotesListener();
            viewNotesButton.addActionListener(listener);

            return viewNotesButton;
        }
        class ViewNotesListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.endViewNotes();
                switchToInput(returnPressViewNotesListener, viewNotesButton);
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
        public class UserInputListener implements ActionListener {
            String input;
            String result;

            // Constructor for use with JButtons
            public UserInputListener(String input) {
                this.input = input;
            }
            // Generic constructor for use with text entry
            public UserInputListener() {
                this.input = "";
            }

            public void actionPerformed(ActionEvent event) {
                if (inputField.getText().equals("") && input.equals("")){
                    inputField.setText("");
                    inputField.requestFocus();
                    return;
                }

                // String becomes either text entered or result of button
                String text;

                // If this method was called from a button
                if (input.equals("")) {
                    text = AcceptedUserInputs.simpleString(inputField.getText());
                    result = GameLogic.PlayerEntry.ActionPerformer(currentPlayer, text);
                }
                // If this method was called from user entry
                else {
                    text = input.toLowerCase();
                    result = GameLogic.PlayerEntry.ActionPerformer(currentPlayer, text);
                }

                // If user did not enter an appropriate command, show a JOptionPane telling
                // them to reenter the command then clear the input box.
                if (!GameLogic.PlayerEntry.getCommandSuccessful()) {
                    JOptionPane.showMessageDialog(null, result);
                }

                /* If the user wants to get helpful hints */
                if (result.equals("help")) {
                	Thread helpThread = new Thread() {
                		@Override
                		public void run() {
                			this.setName("Help Thread");
                			new HelpPage(true);
                		}
                	};
                	helpThread.start();

                	/* Closing the thread once it has created the HelpPage object -- the main thread handles the actionListeners anyway */
                	try {
						helpThread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
                if (result.equals("notes") || result.equals("cheat")){
                    switchToViewNotes(result);
                }
                // TODO: Get rid of || result.equals("question") when done debugging!
                else {
                    if (currentPlayer.getLocationAsString().equals("room") || result.equals("question")) {
                        switch (result) {
                            // If player has chosen to exit a room, bring up the appropriate prompt if necessary
                            case "exitChoice":
                                /*
                                Change input and output to handle user input of an exit choice
                                 */
                                switchToExitChoiceButton();
                                return;
                            case "exit":
                                result = (currentPlayer.getName() + " has exited the room.");
                                break;
                            // If player is making a question, enter the appropriate menu
                            case "question":

                                /* Creating a question menu  */
                                initialQuestion = new QuestionMenu(display, userDisplay, currentPlayer);

                                display.remove(userDisplay);
                                display.add(initialQuestion.returnPanel());
                                display.revalidate();
                                display.repaint();

                                break;
                            case "passage":
                                //Moves players in ArrayList<Token> Room.playersInRoom, and moves player in boardpanel
                                currentPlayer.getPreviousRoom().removePlayerFromRoom(currentPlayer);//Removes player from room they were in
                                currentPlayer.getInRoom().addPlayerToRoom(currentPlayer);
                                JPanel newBoard = myImg.passageMove(currentPlayer, currentPlayer.getPreviousRoom(), currentPlayer.getInRoom());
                                currentPlayer.setPreviousRoom(currentPlayer.getInRoom());
                                refreshBoard(newBoard);
                                break;
                        }
                    }

                    // If the turn was successful, cycle to next turn
                    if (GameLogic.PlayerEntry.wasTurnSuccessful()) {
                        if(result.equals("done")){
                            out.updateMoveHistory(currentPlayer.getName() + " has finished the turn early.");
                        }
                        else {
                            out.updateMoveHistory(result);
                            if (currentPlayer.getInRoom() == null) {
                                System.out.println("Player:\t" + currentPlayer.getName() + "\tAction: " + text
                                        + "\t\tNew Location: " + currentPlayer.getSquareOn().getPositionAsString());

                               int[] currentCoordinates;
                               int[] destinationCoordinates = currentPlayer.getSquareOn().getPosition();//Since the player has already moved, destination is the "current" square
                               JPanel movementPanel = null;
                               switch (text) {
                                   case "up"://Since the player has already moved, current is the "previous" position
                                   case "u":
                                       currentCoordinates = currentPlayer.getSquareOn().getBelow().getPosition();
                                       movementPanel = myImg.move(currentCoordinates, destinationCoordinates);
                                       break;
                                   case "down":
                                   case "d":
                                        currentCoordinates = currentPlayer.getSquareOn().getAbove().getPosition();
                                        movementPanel = myImg.move(currentCoordinates, destinationCoordinates);
                                       break;
                                   case "left":
                                   case "l":
                                        currentCoordinates = currentPlayer.getSquareOn().getRight().getPosition();
                                        movementPanel = myImg.move(currentCoordinates, destinationCoordinates);
                                        break;
                                   case "right":
                                   case "r":
                                        currentCoordinates = currentPlayer.getSquareOn().getLeft().getPosition();
                                        movementPanel = myImg.move(currentCoordinates, destinationCoordinates);
                                        break;
                                   case "exit":
                                   case "e":
                                        currentPlayer.getPreviousRoom().removePlayerFromRoom(currentPlayer);//Removes player from room they were in
                                        movementPanel = myImg.movetoExit(currentPlayer, currentPlayer.getSquareOn().getPosition(), currentPlayer.getPreviousRoom());
                                        currentPlayer.setPreviousRoom(null);
                                        break;
                                   default:
                                       System.out.println("No direction detected ERROR");
                                       break;
                               }
                               refreshBoard(movementPanel);
                            }
                            else {//If the player (In game logic) has already moved into a room
                                switch (text) {
                                    case "up"://Since the player has already moved, current is the "previous" position
                                    case "u":
                                    case "down":
                                    case "d":
                                    case "left":
                                    case "l":
                                    case "right":
                                    case "r":
                                    	currentPlayer.getInRoom().addPlayerToRoom(currentPlayer);//I don't know if this will work
                                        JPanel entrancePanel = myImg.moveToRoom(currentPlayer, currentPlayer.getPrevious().getPosition(), currentPlayer.getInRoom());
                                        currentPlayer.setPreviousRoom(currentPlayer.getInRoom());
                                        refreshBoard(entrancePanel);
                                        break;
                                }

                                // Print action and location to system out
                                System.out.println("Player:\t" + currentPlayer.getName() + "\tAction: " + text
                                        + "\t\tNew Location: " + currentPlayer.getInRoom().getName());
                                // Only update move history with player's room if they aren't making a question
                                    // Otherwise it'll say they're in the room multiple times.
                                if (!result.equals(currentPlayer.getName() + " is making a guess."))
                                    out.updateMoveHistory(currentPlayer.getName()
                                        + " has entered the " + currentPlayer.getInRoom().getName());
                            }
                        }
                        // Use GameLogic to decrement dice and check turn status
                        GameLogic.checkEndOfTurn();
                    }
                    // If not successful, show error and do not cycle to next turn
                        // Error doesn't show if player viewed notes
                    else if (!(result.equals("notes") || result.equals("cheat") || result.equals("help") || result.equals("question"))){
                        // This will be an error message if move was unsuccessful
                    	JOptionPane.showMessageDialog(null, result);
                    }
                }
                GameLogic.PlayerEntry.resetSwitches();
                inputField.setText("");
                inputField.requestFocus();
            }
        }

        //TODO: Arrows
        public class ArrowListener implements KeyListener {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                String dir = null;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        dir = "u";
                        break;
                    case KeyEvent.VK_DOWN:
                        dir = "d";
                        break;
                    case KeyEvent.VK_LEFT:
                        dir = "l";
                        break;
                    case KeyEvent.VK_RIGHT:
                        dir = "r";
                        break;
                }
                System.out.println("CHECK : " + dir);
                if (dir != null) {
                    in.inputField.setText(dir);
                    performActionButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        }

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

        public ExitChoiceListener getNewExitChoiceListener(int i) {
            return new ExitChoiceListener(i);
        }

        class ExitChoiceListener implements ActionListener {
            int exitNumber;

            // If they press an exit button
            public ExitChoiceListener(int num) {
                this.exitNumber = num;
            }
            // If they enter their choice instead
            public ExitChoiceListener() {
                this.exitNumber = -1;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = exitNumber;
                // Check to ensure the entry was an integer
                if (choice == -1) {
                    try {
                        choice = Integer.valueOf(inputField.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Please enter only an integer value");
                    }
                }
                // Call method in GameLogic to see if entry was valid for the number of exits
                GameLogic.PlayerEntry.checkRoomExit(currentPlayer, choice);

                // The checkRoomExit method switches 'roomExitCheck' to true if successful
                if (GameLogic.PlayerEntry.getRoomExitCheck()) {
                    int[] coords = currentPlayer.getSquareOn().getPosition();
                    System.out.println("Move to " + coords[0] +","+coords[1] + " to " + currentPlayer.getPreviousRoom().getName());
                    currentPlayer.getPreviousRoom().removePlayerFromRoom(currentPlayer);
                    JPanel complexExitPanel = myImg.movetoExit(currentPlayer, currentPlayer.getSquareOn().getPosition(), currentPlayer.getPreviousRoom());
                    refreshBoard(complexExitPanel);
                    currentPlayer.setPreviousRoom(null);//frees up a little memory

                    out.updateMoveHistory(currentPlayer.getName() + " has exited the room.");
                    switchToInput(returnPressExitListener, exitChoiceButton);
                    if(currentPlayer.getInRoom() == null) {
                        System.out.println("Player:\t" + currentPlayer.getName() + "\tAction: Exit " + choice
                                + "\t\tNew Location: " + currentPlayer.getSquareOn().getPositionAsString());
                    }
                    GameLogic.checkEndOfTurn();
                }
            }
        }

        /**
         * This method changes the user input panel to reflect choice for exiting a room
         */
        public void switchInputToExitPicker() {
            input.remove(promptLabel);
            inputField.removeActionListener(returnPressListener);
            inputField.removeKeyListener(arrows);
            inputField.addActionListener(returnPressExitListener);
            promptLabel.setText("     Which exit would you like to take?");
            input.add(promptLabel, BorderLayout.CENTER);
        }

        public void switchToViewNotes(String in){
            // Clear input box no matter what
            //  but only show notes if game has started
            if(performActionButton != null &&
                    performActionButton.getParent() == input) {
                switchInputToViewNotes();
                out.viewNotes(in);
                input.remove(performActionButton);
                input.add(createViewNotesButton(), BorderLayout.EAST);
            }
            inputField.setText("");
            inputField.requestFocus();

            input.revalidate();
            output.revalidate();
        }
        private void switchInputToViewNotes(){
            input.remove(promptLabel);
            inputField.removeActionListener(returnPressListener);
            inputField.removeKeyListener(arrows);
            inputField.addActionListener(returnPressViewNotesListener);
            promptLabel.setText("     You are viewing your notes.");
            input.add(promptLabel, BorderLayout.CENTER);
            input.revalidate();
        }

        /**
         * This method switches back to general user input
         *  It can be called to remove panels for either 'exit' or 'notes'/'cheat'
         */
        public void switchToInput(ActionListener al, JButton button) {
            inputField.removeActionListener(al);
            inputField.addActionListener(returnPressListener);
            inputField.addKeyListener(arrows);
            in.inputField.setText("");
            in.inputField.requestFocus();
            promptLabel.setText("     What would you like to do?");
            input.remove(button);
            input.add(performActionButton, BorderLayout.EAST);
            input.revalidate();
            refreshDisplayForNextTurn(currentPlayer);
        }

        public UserInputListener getNewUserInputListener(String s) {
            return new UserInputListener(s);
        }
    }

    // TODO: Question
    /**
     * Panels and methods for asking a question
     */
    private JButton createQuestionDoneButton(){
        JButton button = new JButton();
        button.addActionListener(new questionDoneListener());
        return button;
    }

    class questionDoneListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            display.add(userDisplay);
            display.revalidate();
        }
    }

    // So GameLogic can clear this field for questioning and room exit
    public void clearInputField() {
        in.inputField.setText("");
        in.inputField.requestFocus();
    }

    /**
     * The user output portion of the GUI
     */
    public class OutputTextDisplay {
        JTextArea moveHistory;
        JScrollPane scroller;
        JScrollPane notesScroller;
        JPanel allowedCommandsDisplay;
        JLabel locationReadout;
        JPanel possibleCommandsList;

        public OutputTextDisplay() {
            moveHistory = new JTextArea("", 10, 15);
            moveHistory.setEnabled(false);
            moveHistory.setLineWrap(true);
            moveHistory.setForeground(Color.BLACK);

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
            possibleCommandsList.setLayout(new BoxLayout(possibleCommandsList, BoxLayout.Y_AXIS));
            possibleCommandsList.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
            allowedCommandsDisplay.add(possibleCommandsList, BorderLayout.CENTER);

            allowedCommandsDisplay.revalidate();

            /*
            The move history will be in a scrollable window
             */
            scroller = new JScrollPane(moveHistory);
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
//            System.out.println("InRoom: " + p.getInRoom());
            // The text in the readout depends on what square/room the player is on
            // p == null is for testing (hopefully), won't be in the game
            allowedCommandsDisplay.remove(possibleCommandsList);
            possibleCommandsList.removeAll();
            possibleCommandsList.revalidate();
            possibleCommandsList.repaint();
            possibleCommandsList.setLayout(new BoxLayout(possibleCommandsList, BoxLayout.Y_AXIS));

            if (p == null) {
                locationReadout.setText("Not on the board. Testing?");
                return;
            }
            else if (p.getSquareOn() instanceof FloorSquare)
                locationReadout.setText("<html>You are on a Floor square.<br/>Possible Commands:</html>");
                // This will only be accessed after a player exits the room
            else if (p.getSquareOn() instanceof WallSquare)
                locationReadout.setText("Wall Square? Something went wrong...");
            else
                locationReadout.setText("<html>You are in the " + p.getInRoom().getName()
                        + "<br/>Possible Commands:</html>");

            ArrayList<String> options;

            try {
                 options = (p.getInRoom() == null) ? AcceptedUserInputs.getFloorNavigation()
                        : AcceptedUserInputs.getRoomNavigation();
                for (String s : options) {
                    s = s.substring(0, 1).toUpperCase().concat(s.substring(1));
                    JButton btn = new JButton(s);
                    btn.addActionListener(in.getNewUserInputListener(s));
                    possibleCommandsList.add(btn);
                    btn.setAlignmentX(JButton.CENTER_ALIGNMENT);
                }
            } catch (Exception e) { e.printStackTrace(); }

            // TODO: Debugging for AccuseMenu
//            JButton accuse = new JButton("Accuse");
//            AccuseMenu menu = new AccuseMenu(display, userDisplay, currentPlayer);
//            accuse.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    menu.switchToAccuseMenu();
//                }
//            });
//            possibleCommandsList.add(accuse);


            possibleCommandsList.repaint();
            allowedCommandsDisplay.add(possibleCommandsList);
            allowedCommandsDisplay.revalidate();
            allowedCommandsDisplay.repaint();
        }

        public void roomExitChoicesUpdater(){
            allowedCommandsDisplay.remove(possibleCommandsList);
            possibleCommandsList.removeAll();

            ArrayList<Integer> choices = AcceptedUserInputs.getRoomExits(currentPlayer.getInRoom());

            possibleCommandsList.setLayout(new BoxLayout(possibleCommandsList, BoxLayout.Y_AXIS));

            for (Integer i : choices) {
                String s = "Exit " + i;
                JButton btn = new JButton(s);
                btn.addActionListener(in.getNewExitChoiceListener(i));
                possibleCommandsList.add(btn);
                btn.setAlignmentX(JButton.CENTER_ALIGNMENT);
            }


            possibleCommandsList.revalidate();

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
            moveHistory.append(in);
            moveHistory.append("\n\n");

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

        public void viewNotes(String entry){
            JTextArea notes = new JTextArea("", 10, 15);
            notes.setBackground(Color.BLACK);
            notes.setForeground(Color.WHITE);
            if(entry.equals("notes")) {
                for (String s : currentPlayer.getNotes())
                    notes.append(s + "\n");
            }
            else if(entry.equals("cheat")){
                notes.append("CHEATER!!!\n");
                for (Card c : GameLogic.deck.getMurderEnvelope())
                    notes.append(c.toString() + "\n");
            }

            notesScroller = new JScrollPane(notes);
            output.removeAll();
            output.setLayout(new GridLayout(1,1));
            output.add(notesScroller);
            output.revalidate();
            userDisplay.repaint();
        }
        public void endViewNotes(){
            output.remove(notesScroller);
            output.setLayout(new GridLayout(2, 1));
            output.add(scroller);
            output.add(allowedCommandsDisplay);
            output.repaint();
        }
    }

    public OutputTextDisplay getOut() {
        return out;
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

    public QuestionMenu getInitialQuestion() {
        return initialQuestion;
    }
}
