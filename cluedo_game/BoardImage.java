package cluedo_game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class BoardImage extends JPanel {
    private BufferedImage boardImage;

    public JPanel buildBoardDisplay() {
        JPanel gameBoardImage = new JPanel();

        try {
            boardImage = ImageIO.read(new File("board.jpg"));
        } catch (IOException ioex) {
            System.out.println("Could not find board image file!\n" + ioex.toString());
        }




    }


}
