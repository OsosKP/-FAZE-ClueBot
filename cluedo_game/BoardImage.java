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
    	/* Creating the JFrame -- Will be removed */
        JFrame frame = new JFrame("Test Bufferedimage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        /* Getting the width and height of the given image */ 
        int w = bi.getWidth();
        int h = bi.getHeight();
        
        /* Setting frame size -- Will be removed */
        frame.setSize(w,h);
        frame.setVisible(true);
        
        
        /* ?? */
        int step = h/23;
        
        /* Creating JPanel -- will represent the grid that will overly the image */
        JPanel p = new JPanel(new GridLayout(23,23));
        p.setOpaque(false);

        /* Var to print the number of times we have created the grid */
        int count = 0;
        
        /* Loop that goes through the given image, splitting it up, the  */
        for (int ii=0; ii<w; ii+=step) {
        	/* Need to lay it out like this, otherwise we set some spaces between the JButtons */
            for (int jj=0; jj<h; jj+=step) {
            	
            	/* Getting the sub-image of the given BufferedImage */
            	System.out.println("Getting subimage coords: " + jj + ", " + ", " + ii + ", " +step + ", "+ step);
                Image icon = bi.getSubimage(jj, ii, 23, 23);
           
                /* Creating the button that will will have the image of this current section of the map*/
                JButton button = new JButton(new ImageIcon(icon));

                /* remove the border - indicate action using a different icon */
                button.setBorder(null);//THIS IS IMPORTANT BECAUSE IT COMBINES THE SUB

                /* Making a pressed icon, otherwise the user would get no 'feedback' from the program */
                BufferedImage iconPressed = new BufferedImage(step,step,BufferedImage.TYPE_INT_ARGB);
                /* Making sure that the pressed button lookes the same as the old one -- besides the green outline  */
                Graphics g = iconPressed.getGraphics();
                g.drawImage(icon, 0, 0, p);
                g.setColor(Color.GREEN);
                g.drawRoundRect(0, 0, iconPressed.getWidth(p)-1, iconPressed.getHeight(p)-1, 12, 12);
                g.dispose();
                button.setPressedIcon(new ImageIcon(iconPressed));

                
                button.setActionCommand(""+count);
                button.addActionListener(new ActionListener(){
                
                /* What happens when we press the button? */
                @Override
                  public void actionPerformed(ActionEvent ae) {
                         System.out.println(ae.getActionCommand());
                  }
                });
                /* Adding the button to the p JPanel */
                p.add(button);
             }
            count++;
        }
        /* Put the first JPanel in this one -- GridBagLaout messes with the spacing to make it look nicer */
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.BLACK);
        center.add(p);
        /* Will be removed */
        frame.add(center);//FOR SOME REASON IT WORKS IN A JOPTIONPANE BUT NOT IN A JFRAME
    }

    public static void main(String[] args) throws IOException {
    	/* Curent test -- will be removed */
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
