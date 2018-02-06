package cluedo_game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
/*
THIS DOESN'T WORK
 */
public class BoardImage extends JPanel {
    private static BufferedImage boardImage;

    public static JPanel buildBoardDisplay() {
        JPanel gameBoardImage = new JPanel();

        try {
            boardImage = ImageIO.read(new File("board.jpg"));
        } catch (IOException ioex) {
            System.out.println("Could not find board image file!\n" + ioex.toString());
        }

        JLabel image = new JLabel(new ImageIcon(boardImage));
        gameBoardImage.add(image);

        return gameBoardImage;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(boardImage, 0, 0, Frame.WIDTH, Frame.HEIGHT, this);
    }



}
