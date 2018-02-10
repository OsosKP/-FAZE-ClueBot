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

public class BoardImage { 
	/**
	 * Creates and returns a JPanel based on the board image
	 * @return
	 */
	public void returnBoardPanel() {


		/* KELSEY TRY AND USE THIS!!!! */
		imagePanel test = new imagePanel();
		gridPanel test1 = new gridPanel();
		
		JFrame frame = new JFrame("JPanel Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    // add the Jpanel to the main window
	    frame.add(test.paintMe()); 
	    //frame.add(test1);
	    frame.setPreferredSize(new Dimension(552, 575));
	    //frame.setResizable(false);

	    frame.pack();
	    frame.setVisible(true);		
	}

	public static void main (String[] agrs) {
		BoardImage test = new BoardImage();
		test.returnBoardPanel();
	}
}

class imagePanel extends JPanel{
	private int height, width;
	
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
			
			this.height = tempImage.getHeight(this);
			this.width = tempImage.getWidth(this);
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
}

class gridPanel extends JPanel{
	private JLabel[][] grid;

	public gridPanel paintMe() {
		this.drawGrid();
		return this;
	}
	
	public void drawGrid() {
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
	}
}