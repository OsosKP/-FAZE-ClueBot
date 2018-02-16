// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.File;

public class BoardImageTest {

    public BoardImageTest(BufferedImage bi) {
        int w = bi.getWidth();//Gets the width of the image
        int h = bi.getHeight();//Gets the height of the image
        int step = w/3;//Increments by thirds
        JPanel p = new JPanel(new GridLayout(24,25));
        p.setOpaque(false);

        int count = 0;
        for (int ii=0; ii<w; ii+=step) {
            for (int jj=0; jj<h; jj+=step) {
                // This is it - GET THE SUB IMAGE
                Image icon = bi.getSubimage(jj, ii, step, step);
                if (count%2==1) {
                    JButton button = new JButton(new ImageIcon(icon));

                    // remove the border - indicate action using a different icon
                    button.setBorder(null);

                    // // make a 'pressed' icon..
                    // BufferedImage iconPressed = new BufferedImage(step,step,BufferedImage.TYPE_INT_ARGB);
                    // Graphics g = iconPressed.getGraphics();
                    // g.drawImage(icon, 0, 0, p);
                    // g.setColor(Color.GREEN);
                    // g.drawRoundRect(
                    //         0, 0,
                    //         iconPressed.getWidth(p)-1, iconPressed.getHeight(p)-1,
                    //         12, 12);
                    // g.dispose();
                    // button.setPressedIcon(new ImageIcon(iconPressed));
					//
                    // // make it transparent
                    // //button.setContentAreaFilled(false);
					//
                    // button.setActionCommand(""+count);
                    // button.addActionListener(new ActionListener(){
                    //     @Override
                    //     public void actionPerformed(ActionEvent ae) {
                    //         System.out.println(ae.getActionCommand());
                    //     }
                    // });

                    p.add(button);
                } else {
                    JLabel label = new JLabel(new ImageIcon(icon));
                    p.add(label);
                }
                count++;
            }
        }
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.BLACK);
        center.add(p);
        JOptionPane.showMessageDialog(null, center);
    }

    public static void main(String[] args) throws IOException {
        final BufferedImage bi = ImageIO.read(new File("boardEdit.jpeg"));
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new BoardImageTest(bi);
            }
        });
    }
}
