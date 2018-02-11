package cluedo_game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class BoardImage {

    public BoardImage(BufferedImage bi) {

        JFrame frame = new JFrame("Test Bufferedimage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int w = bi.getWidth();
        int h = bi.getHeight();
        frame.setSize(w,h);
        frame.setVisible(true);
        int step = h/23;
        JPanel p = new JPanel(new GridLayout(23,23));
        p.setOpaque(false);

        int count = 0;
        for (int ii=0; ii<w; ii+=step) {
            for (int jj=0; jj<h; jj+=step) {
                // This is it - GET THE SUB IMAGE
                System.out.println("Getting subimage coords: " + jj + ", " + ", " + ii + ", " +step + ", "+ step);
                Image icon = bi.getSubimage(jj, ii, 23, 23);
                if (1==1) {
                    JButton button = new JButton(new ImageIcon(icon));

                    // remove the border - indicate action using a different icon
                    button.setBorder(null);//THIS IS IMPORTANT BECAUSE IT COMBINES THE SUB

                    // make a 'pressed' icon..
                    BufferedImage iconPressed = new BufferedImage(step,step,BufferedImage.TYPE_INT_ARGB);
                    Graphics g = iconPressed.getGraphics();
                    g.drawImage(icon, 0, 0, p);
                    g.setColor(Color.GREEN);
                    g.drawRoundRect(
                            0, 0,
                            iconPressed.getWidth(p)-1, iconPressed.getHeight(p)-1,
                            12, 12);
                    g.dispose();
                    button.setPressedIcon(new ImageIcon(iconPressed));

                    // make it transparent
                    //button.setContentAreaFilled(false);

                    button.setActionCommand(""+count);
                    button.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            System.out.println(ae.getActionCommand());
                        }
                    });

                    p.add(button);
                }
                count++;
            }
        }
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.BLACK);
        center.add(p);
        frame.add(center);//FOR SOME REASON IT WORKS IN A JOPTIONPANE BUT NOT IN A JFRAME
    }

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://i.imgur.com/A01Gmjw.jpg");//http://i.stack.imgur.com/SNN04.png
        final BufferedImage bi = ImageIO.read(url);
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new BoardImage (bi);
            }
        });
    }
}
