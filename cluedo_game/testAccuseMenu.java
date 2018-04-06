package cluedo_game;

import javax.swing.*;

public class testAccuseMenu {
    public static void main(String[] args) {
        Token white = new Token(0, 9, "White", 0);
        new AccuseMenu(new JFrame(), new JPanel(), white);
    }
}
