package cluedo_game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class BoardImage { 
    private JPanel lPane = new JPanel(new CardLayout());
	imagePanel test = new imagePanel();
	gridPanel test1 = new gridPanel();
	
	/**
	 * Creates and returns a JPanel based on the board image
	 * @return
	 */
	public void returnBoardPanel() {
		/* Creating the JFrame, setting default operations */
		JFrame frame = new JFrame("JPanel Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		
		/* Adding the JLayeredPame */
		frame.setLayout(new BorderLayout());
	    
	    /* Trying to set the compmax bounds on the lpane -- should make sure that the grid falls exactly over the image */
	   // lpane.setBounds(0, 0, 552, 575);

		lPane.add(test, 0);
		lPane.add(test1.paintMe(), 1);

		

	    frame.add(lPane); //we want to center all of our jPanels

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
	protected void paintComponent(Graphics g) {		
		super.paintComponent(g);
		g.drawImage(this.getImage("/boardEdit.jpeg"), 0, 0, this);
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

class gridPanel extends JPanel{
	private JLabel[][] grid;

	public gridPanel paintMe(){		
	//	this.setBounds(0, 0, 552, 575); //needed in order for 24x25 to work
		this.fillGrid();	
		return this;
	}
	
	public void fillGrid() {
		this.setLayout(new GridLayout(24, 25));
		
		grid = new JLabel[24][25];
		
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 25; j++) {
				grid[i][j] = new JLabel();
				grid[i][j].setBorder(new LineBorder(Color.BLACK));
				grid[i][j].setOpaque(false);
				this.add(grid[i][j]);
			}
		}
	}
}