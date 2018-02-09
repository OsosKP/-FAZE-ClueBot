package cluedo_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * UserInterface
 * This class is the JFrame that will hold all of the individual JPanels:
 *  Game Board
 *  Input Box
 *  Text Display Box
 */
public class UserInterface extends JPanel {
//    private JPanel boardImage = BoardImage.buildBoardDisplay();
    private JFrame display = new JFrame();
    private UserInputBox in = new UserInputBox();
    private JPanel input = in.createInputPanel();
    private OutputTextDisplay out = new OutputTextDisplay();
    private JPanel output = out.createOutputPanel();
    // This will be replaced by the graphical board
    private JPanel placeHolder = this.placeHolderPanel();

    private JPanel userDisplay = new JPanel();

    public UserInterface(){
        this.buildGUI();
    }

    public void updateOutput(String newText){
        out.update(output, newText);
    }


    public void buildGUI(){
        display.setSize(1600, 900);
        display.setTitle("Cluedo");
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userDisplay.setLayout(new BorderLayout());
        userDisplay.add(input, BorderLayout.SOUTH);
        userDisplay.add(output, BorderLayout.EAST);
        userDisplay.add(placeHolder, BorderLayout.CENTER);

        display.add(userDisplay);
        display.setVisible(true);
    }

    /**
     * The user input portion of the GUI
     */
    private class UserInputBox {
        final int FIELD_WIDTH = 10;
        private JTextField inputField = new JTextField(FIELD_WIDTH);
        private JLabel whoseTurnLabel = new JLabel("     It is now NAME's turn.");
        private JLabel promptLabel = new JLabel("     What would you like to do?");
        private JPanel inputPanel;

        public UserInputBox(){
            inputPanel = createInputPanel();
        }

        class UserInputListener_FloorSquare implements ActionListener {
            public void actionPerformed(ActionEvent event){
                out.update(output, inputField.getText());
                inputField.setText("");
                inputField.requestFocus();
                System.out.println("You have performed the action: " + inputField.getText());
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

            return input;
        }
    }

    /**
     * The user output portion of the GUI
     */
    private class OutputTextDisplay{
        JTextArea textOutput;
        JScrollPane scroller;
        JPanel allowedCommandsDisplay;

        public OutputTextDisplay(){
            textOutput = new JTextArea("", 10, 15);
            textOutput.setEnabled(false);
            textOutput.setLineWrap(true);

            this.createAllowedCommandsDisplay();

            scroller = new JScrollPane(textOutput);
        }

        public void createAllowedCommandsDisplay(){
            allowedCommandsDisplay = new JPanel();
            allowedCommandsDisplay.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            allowedCommandsDisplay.setLayout(new GridLayout(10, 1));
            allowedCommandsDisplay.setOpaque(true);
            allowedCommandsDisplay.setBackground(Color.GRAY);
            allowedCommandsDisplay.setForeground(Color.white);

            this.updateAllowedCommandsBasedOnSquare(null);
        }

        public void updateAllowedCommandsBasedOnSquare(Token p) {
            // This is for a readout to tell the player where they are
            JLabel locationReadout;

            // The text in the readout depends on what square/room the player is on
            // p == null is for testing, won't be in the game
            if(p == null)
                locationReadout = new JLabel("Not on the board. Testing?");
            else if(p.getSquareOn() instanceof FloorSquare)
                locationReadout = new JLabel("You are on a Floor square.");
            // This will only be accessed after a player exits the room
            // Because when a player enters this square from a floor square, they are immediately taken to the room
            else if(p.getSquareOn() instanceof EntrySquare)
                locationReadout = new JLabel("You are on an Entry square to: "
                        + ((EntrySquare) p.getSquareOn()).getRoomName());
            else if(p.getSquareOn() instanceof WallSquare)
                locationReadout = new JLabel("Wall Square? Something went wrong...");
            else
                locationReadout = new JLabel("You are in the " + p.getInRoom().getName());

            locationReadout.setForeground(Color.white);
            locationReadout.setHorizontalAlignment(JLabel.CENTER);
            allowedCommandsDisplay.add(locationReadout);

            JLabel title = new JLabel("You May:");
            title.setForeground(Color.white);
            title.setHorizontalAlignment(JLabel.CENTER);
            allowedCommandsDisplay.add(title);

            try {
                if (p != null && p.getInRoom() == null) {
                    switch (p.getSquareOn().getSquareType().toString()) {
                        case "floor":
                            for (String s : AcceptedUserInputs.getFloorNavigation()) {
                                JLabel d = new JLabel(s);
                                d.setForeground(Color.white);
                                d.setHorizontalAlignment(JLabel.CENTER);
                                allowedCommandsDisplay.add(d);
                            }
                            break;
                        case "entry":
                            for (String s : AcceptedUserInputs.getEntryChoices()) {
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
                            for (String s : AcceptedUserInputs.getFloorNavigation()) {
                                JLabel d = new JLabel(s);
                                d.setForeground(Color.yellow);
                                d.setHorizontalAlignment(JLabel.CENTER);
                                allowedCommandsDisplay.add(d);
                            }
                            break;

                    }
                }
                else {
                    for (String s : AcceptedUserInputs.getRoomNavigation()) {
                        JLabel d = new JLabel(s);
                        d.setForeground(Color.white);
                        d.setHorizontalAlignment(JLabel.CENTER);
                        allowedCommandsDisplay.add(d);
                    }
                }

            } catch (Exception e) {
                e.getMessage();
            }
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
    // Just making this to stick it into the overall panel to help determine blocking
    public JPanel placeHolderPanel(){
        JPanel placeHolder = new JPanel();
        JLabel ph = new JLabel("Placeholder Panel");
        placeHolder.add(ph);
        return placeHolder;
    }


}
