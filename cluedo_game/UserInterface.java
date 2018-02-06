package cluedo_game;

import javax.swing.*;

/**
 * UserInterface
 * This class is the JFrame that will hold all of the individual JPanels:
 *  Game Board
 *  Input Box
 *  Text Display Box
 */
public class UserInterface extends JPanel {
    private JPanel boardImage = BoardImage.buildBoardDisplay();
    private JFrame display = new JFrame();

    public UserInterface(){
        this.buildGUI();
    }


    public void buildGUI(){
        display.setSize(960, 855);
        display.setTitle("Cluedo");
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display.add(boardImage);
    }




}
