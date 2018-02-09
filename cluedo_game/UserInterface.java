package cluedo_game;

import com.intellij.ui.JBColor;

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
            input.setBorder(BorderFactory.createEtchedBorder(JBColor.lightGray, JBColor.black));

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
        private int AREA_SIZE_MAX = 25;
        int i = 1;

        public OutputTextDisplay(){
            textOutput = new JTextArea("", 10, 15);
            textOutput.setEnabled(false);
            textOutput.setLineWrap(true);
            scroller = new JScrollPane(textOutput);
        }

        public void update(JPanel panel, String in){

            textOutput.append(in);
            i++;
            textOutput.append("\n\n");
            i++;

            panel.revalidate();
        }

        public JPanel createOutputPanel(){
            JPanel output = new JPanel();
            output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
            output.add(scroller);

            return output;
        }


    }
    // Just making this to stick it into the overall panel to help determine blocking
    public JPanel placeHolderPanel(){
        return new JPanel();
    }


}
