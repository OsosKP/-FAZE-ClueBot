package cluedo_game;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardImage extends Component { 
	private int width, height;
	
	/**
	 * will attempt to load an image from a given filePath
	 * @param filePath = the file path to the image we want 
	 * @return an imageIcon object that we can use to create the JLabel
	 */
	public ImageIcon getImage(String filePath) {
		Image tempImage = null;
		ImageIcon image = null;
		/* Now we need to try to load the image into the file system */
		try {
			/* Loads the image, and assigns it to the tempImage var */
			URL imagePath = BoardImage.class.getResource(filePath);
			tempImage = Toolkit.getDefaultToolkit().getImage(imagePath);
			image = new ImageIcon(tempImage);
			
			/* Sets the height and width, if we wanted to know the dimensions of the img */
			this.height = image.getIconHeight();
			this.width = image.getIconWidth();
		}
		catch(Exception e){ //if the filePath does not exist, or something else messed up
			System.err.println("We were not able to load the requested image from the given filePath: " + "\n" + filePath);
		}
		return image;
	}
	/**
	 * Creates and returns a JPanel based on the board image
	 * @return JPanel with image
	 */
	public JPanel returnBoardPanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(getImage("boardEdit.jpeg"));
		panel.add(label);
		
		return panel;
	}
	/**
	 * Creates and returns a JPanel based on any given image
	 * @param filePath directory location of the image
	 * @return a JPanel containing the given image
	 */
	public JPanel getImagePanel(String filePath) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(getImage(filePath));
		panel.add(label);
		
		return panel;
	}
	/**
	 * returns the height of the image that is loaded into this jPanel
	 * @return image height
	 */
	public int getHeight() {
		return this.height; 
	}
	/**
	 * returns the width of the image that is loaded into this jPanel
	 * @return image width
	 */
	public int getWidth() {
		return this.width;
	}
}
