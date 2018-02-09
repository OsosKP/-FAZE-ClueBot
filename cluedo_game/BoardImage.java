package cluedo_game;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class BoardImage extends Component { 
	//TODO get a grid layout ontop of the image
	
	private int width, height;
	private JLabel[][] grid;
	
	/**
	 * will attempt to load an image from a given filePath
	 * @param filePath = the file path to the image we want 
	 * @return an imageIcon object that we can use to create the JLabel
	 */
	public ImageIcon getImage(String filePath) {
		Image tempImage = null;
		ImageIcon returnMe = null;
		/* Now we need to try to load the image into the file system */
		try {
			/* Loads the image, and assigns it to the tempImage var */
			URL imagePath = BoardImage.class.getResource(filePath);
			tempImage = Toolkit.getDefaultToolkit().getImage(imagePath);
			returnMe = new ImageIcon(tempImage);
			
			/* Sets the height and width, if we wanted to know the dimensions of the img */
			this.height = returnMe.getIconHeight();
			this.width = returnMe.getIconWidth();
		}
		catch(Exception e){ //if the filePath does not exist, or something else messed up
			System.err.println("We were not able to load the requested image form the given filePath: " + "\n" + filePath);
		}
		return returnMe;
	}
	/**
	 * Creates and returns a JPanel based on the board image
	 * @return
	 */
	public void returnBoardPanel() {
		JPanel panel = new JPanel();

		panel.setBounds(0, 0, 552, 575); //needed in order for 24x25 to work
		panel.setLayout(new GridLayout(24, 25));
		
		grid = new JLabel[24][25];
		
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 25; j++) {
				grid[i][j] = new JLabel();
				grid[i][j].setBorder(new LineBorder(Color.BLACK));
				grid[i][j].setOpaque(true);
				panel.add(grid[i][j]);
			}
		}
		JLabel test = new JLabel();
		test.setIcon(getImage("boardEdit.jpeg"));
		panel.add(test);
		
		JFrame frame = new JFrame("JPanel Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	    // add the Jpanel to the main window
	    frame.add(panel); 
	
	    frame.pack();
	    frame.setVisible(true);		
	}
	/**
	 * Creates and returns a JPanel based on any given image
	 * @param filePath
	 * @return
	 */
	public JPanel returnImagePanel(String filePath) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(getImage(filePath));
		panel.add(label);
		
	
	/**
	 * returns the height of the image that is loaded into this jPanel
	 * @return
	 */
	public int returnHeight() {
		return this.height; 
	}
	/**
	 * returns the width of the image that is loaded into this jPanel
	 * @return
	 */
	public int returnWidth() {
		return this.width;
	}

	public static void main (String[] agrs) {
		BoardImage test = new BoardImage();
		test.returnBoardPanel();
	}
}
