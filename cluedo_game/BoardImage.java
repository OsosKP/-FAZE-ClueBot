package cluedo_game;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
	TODO: Import the JPEG + get a grid layer over it
	CURRENTLY working!! when you run this class, the image displays
	Should be its own Jpanel, then set to the north side of the userInterface 
*/

public class BoardImage extends Component { 
	private int width, height;
	
	/**
	 * will attempt to load an image from a given filePath
	 * @param filePath = the file path to the image we want 
	 * @return an image object
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
			this.height = returnMe.getIconHeight();
			this.width = returnMe.getIconWidth();
		}
		catch(Exception e){ //if the filePath does not exist, or something else messed up
			System.err.println("We were not able to load the requested image form the given filePath: " + "\n" + filePath);
		}
		return returnMe;
	}
	
	public int returnHeight() {
		return this.height; 
	}
	
	public int returnWidth() {
		return this.width;
	}
	
	public static void main (String args[]) {
		
	}
	
}
