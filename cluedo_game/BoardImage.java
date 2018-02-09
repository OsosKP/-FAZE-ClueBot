package cluedo_game;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

/*
	TODO: Import the JPEG + get a grid layer over it
	CURRENTLY working!! when you run this class, the image displays
	Should be its own Jpanel, then set to the north side of the userInterface 
*/

public class BoardImage extends Component { 
	private int width, height;

	/* Will hold the image that we will draw, initially set = null so we could potentially load different images if we wanted to */
	private Image board = null;
	
	/**
	 * this will actually draw the image onto the panel	 
	*/
	public void paint(Graphics g) {
		/* If we havent already loaded an image, we will load the default one */
		if (board == null) {
			board = getImage("boardEdit.jpeg");
		}
		Graphics2D g2 = (Graphics2D)g; //an alien face
		
		/* Calling the image at its full size */
		width = board.getWidth(this);
		height = board.getHeight(this);
		
		g2.drawImage(board, 0, 0, width, height, this); //image observer notifies everything else when the image actually is placed 
	}
	/**
	 * will attempt to load an image from a given filePath
	 * @param filePath = the file path to the image we want 
	 * @return an image object
	 */
	public Image getImage(String filePath) {
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
	
	public int returnHeight() {
		return this.height; 
	}
	public int returnWidth() {
		return this.width;
	}
	
	public static void main(String[] agrs) { //simple test to see if works
        JFrame f = new JFrame("Load Image Sample");
        BoardImage showMe = new BoardImage();
        f.add(new BoardImage());
        f.pack();
        f.setPreferredSize(new Dimension(showMe.returnWidth(), showMe.returnHeight()));
        f.setVisible(true);
	}
	
}
