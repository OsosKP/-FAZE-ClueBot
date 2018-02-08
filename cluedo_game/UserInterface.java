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
//    private JPanel boardImage = BoardImage.buildBoardDisplay();
    private JFrame display = new JFrame();

    public UserInterface(){
        this.buildGUI();
    }


    public void buildGUI(){
        display.setSize(960, 855);
        display.setTitle("Cluedo");
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UserInputBox in = new UserInputBox();
        JPanel input = in.createInputPanel();

        display.add(input);
        display.setVisible(true);
    }


    private class UserInputBox {
        final int FIELD_WIDTH = 10;
        private JTextField inputField = new JTextField(FIELD_WIDTH);
        private JLabel promptLabel = new JLabel("What would you like to do?");
        private JPanel inputPanel;

        public UserInputBox(){
            createInputPanel();
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


}
