package cluedo_game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class BoardImage {
	private int width  = 0;
	private int height = 0;
	private int step = 0;
	BufferedImage bi;
	
	public BoardImage() {
		//nothing
	}
	
	public JPanel returnPanel(BufferedImage bi) {
		this.bi = bi;
		
		JPanel p = this.returnEmptyGridLayout(bi);
		p = this.populateGrid(p);
		
		JPanel holder = this.returnFinalJPanel();
		holder.add(p);
		return holder;
	}
	
	public void testMe(BufferedImage bi) {
        JFrame frame = new JFrame("Test Bufferedimage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		this.bi = bi;	
		
		JPanel p = this.returnEmptyGridLayout(bi);
		p = this.populateGrid(p);
		
        /* Setting frame size -- Will be removed */
        frame.setSize(this.width,this.width);
        frame.setVisible(true);	
		
		JPanel holder = this.returnFinalJPanel();
		holder.add(p);
		frame.add(holder);
		
	}
	
	private JPanel returnEmptyGridLayout(BufferedImage bi) {
        /* Getting the width and height of the given image */ 
        width = bi.getWidth();
        height = bi.getHeight();
        
        /* ?? */
        step = height/23;
        
        /* Creating JPanel -- will represent the grid that will overly the image */
        JPanel p = new JPanel(new GridLayout(23,23));
        p.setOpaque(false);
        return p;
	}
	
	private JPanel populateGrid(JPanel p) {
        /* Var to print the number of times we have created the grid */
        int count = 0;
        
        /* Loop that goes through the given image, splitting it up, the  */
        for (int ii=0; ii<width; ii+=step) {
        	/* Need to lay it out like this, otherwise we set some spaces between the JButtons */
            for (int jj=0; jj<height; jj+=step) {
            	
            	/* Getting the sub-image of the given BufferedImage */
            	System.out.println("Getting subimage coords: " + jj + ", " + ", " + ii + ", " +step + ", "+ step);
                Image icon = bi.getSubimage(jj, ii, 23, 23);
           
                /* Creating the button that will will have the image of this current section of the map*/
                JButton button = new JButton(new ImageIcon(icon));

                /* remove the border - indicate action using a different icon */
                button.setBorder(null);//THIS IS IMPORTANT BECAUSE IT COMBINES THE SUB

                /* Making a pressed icon, otherwise the user would get no 'feedback' from the program */
                BufferedImage iconPressed = new BufferedImage(step,step,BufferedImage.TYPE_INT_ARGB);
                /* Making sure that the pressed button looks the same as the old one -- besides the green outline  */
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
        return p;
	}
	
	private JPanel returnFinalJPanel() {
        /* Put the first JPanel in this one -- GridBagLaout messes with the spacing to make it look nicer */
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.BLACK);
        return center;
	}

    public static void main(String[] args) throws IOException {

    	URL url = new URL("https://i.imgur.com/A01Gmjw.jpg");//http://i.stack.imgur.com/SNN04.png
        final BufferedImage bi = ImageIO.read(url);
        BoardImage testMe = new BoardImage();
        
        testMe.testMe(bi);
    }
}
