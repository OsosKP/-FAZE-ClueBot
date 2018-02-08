package cluedo_game;

import com.sun.jna.platform.win32.Netapi32Util;

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

    private JPanel userDisplay = new JPanel();

    public UserInterface(){
        this.buildGUI();
    }

    public void updateOutput(String newText){
        out.update(output, newText);
    }


    public void buildGUI(){
        display.setSize(960, 855);
        display.setTitle("Cluedo");
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userDisplay.setLayout(new BorderLayout());
        userDisplay.add(input, BorderLayout.WEST);
        userDisplay.add(output, BorderLayout.EAST);

        display.add(userDisplay);
        display.setVisible(true);
    }

    /**
     * The user input portion of the GUI
     */
    private class UserInputBox {
        final int FIELD_WIDTH = 10;
        private JTextField inputField = new JTextField(FIELD_WIDTH);
        private JLabel promptLabel = new JLabel("What would you like to do?");
        private JPanel inputPanel;

        public UserInputBox(){
            inputPanel = createInputPanel();
        }

        class UserInputListener_FloorSquare implements ActionListener {
            public void actionPerformed(ActionEvent event){
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

            input.add(promptLabel, BorderLayout.NORTH);
            input.add(inputField, BorderLayout.CENTER);
            input.add(createPerformActionButton(), BorderLayout.EAST);

            return input;
        }
    }

    /**
     * The user output portion of the GUI
     */
    private class OutputTextDisplay{
        JLabel textOutput;

        StringBuilder text = new StringBuilder();

        public OutputTextDisplay(){
            textOutput = new JLabel("");
        }

        public void update(JPanel panel, String in){
            text.append(in);
            text.append("\n");

            textOutput.setText(text.toString());
            panel.revalidate();
        }

        public JPanel createOutputPanel(){
            JPanel output = new JPanel();
            output.add(textOutput);

            return output;
        }


    }


}
