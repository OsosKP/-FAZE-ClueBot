package cluedo_game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class BoardImage extends JPanel { 
	//TODO get 2 inner classes, one for the image, another for the grid
	
	private JLabel[][] grid;
	

		/**
		 * will attempt to load an image from a given filePath
		 * @param filePath = the file path to the image we want 
		 * @return an imageIcon object that we can use to create the JLabel
		 */

	

	/**
	 * Creates and returns a JPanel based on the board image
	 * @return
	 */
	public void returnBoardPanel() {

		this.setBounds(0, 0, 552, 575); //needed in order for 24x25 to work
		this.setLayout(new GridLayout(24, 25));
		
		grid = new JLabel[24][25];
		
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 25; j++) {
				grid[i][j] = new JLabel();
				grid[i][j].setBorder(new LineBorder(Color.BLACK));
				this.add(grid[i][j]);
			}
		}
		/* KELSEY TRY AND USE THIS!!!! */
		imagePanel test = new imagePanel();
		JFrame frame = new JFrame("JPanel Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    // add the Jpanel to the main window
	    frame.add(test.paintMe()); 
	    frame.setPreferredSize(new Dimension(552, 575));
	    //frame.setResizable(false);

	    frame.pack();
	    frame.setVisible(true);		
	}

	/**
	 * returns the height of the image that is loaded into this jPanel
	 * @return
	 */
//	public int returnHeight(imagePanel temp) {
//		return temp.height; 
//	}
	/**
	 * returns the width of the image that is loaded into this jPanel
	 * @return
	 */
//	public int returnWidth(imagePanel temp) {
//		return temp.width;
//	}

	public static void main (String[] agrs) {
		BoardImage test = new BoardImage();
		test.returnBoardPanel();
	}
}

class imagePanel extends JPanel{
	public imagePanel paintMe() {
		JLabel temp = new JLabel();
		ImageIcon icon = new ImageIcon(this.getImage("/boardEdit.jpeg")); 
		temp.setIcon(icon);

		this.add(temp);
		return this;
	}

	public Image getImage(String filePath) {
		int width, height;
		Image tempImage = null;
		/* Now we need to try to load the image into the file system */
		try {
			/* Loads the image, and assigns it to the tempImage var */
			URL imagePath = BoardImage.class.getResource(filePath);
			tempImage = Toolkit.getDefaultToolkit().getImage(imagePath);
			

		}
		catch(Exception e){ //if the filePath does not exist, or something else messed up
			System.err.println("We were not able to load the requested image form the given filePath: " + "\n" + filePath);
		}
		return tempImage;
	}

}