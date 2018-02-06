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
    public JFrame buildGUI(){
        JFrame display = new JFrame();
        display.setSize(960, 855);
        display.setTitle("Cluedo");
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
