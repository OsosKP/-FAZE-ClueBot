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
	ImagePanel test = new ImagePanel();
	GridPanel test1 = new GridPanel();

	/**
	 * Creates and returns a JPanel based on the board image
	 * @return
	 */
	public void returnBoardPanel() {
		/* Creating the JFrame, setting default operations */
		JFrame frame = new JFrame("JPanel Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel testing = new JLabel();
		testing = test.createImage();

		testing.setLayout(new BorderLayout());
		frame.setContentPane(testing);
			
		frame.add(test1.paintMe());


		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public static void main (String[] agrs) {
		BoardImage test = new BoardImage();
		test.returnBoardPanel();
	}
}

class ImagePanel extends JPanel{

	public JLabel createImage() {
		JLabel temp = new JLabel();
		ImageIcon icon = new ImageIcon(this.getImage("/boardEdit.jpeg"));

		temp.setIcon(icon);
		temp.setBounds(0, 0, 552, 575); //needed in order for 24x25 to work

		return temp;
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

class GridPanel extends JPanel{
	private JLabel[][] grid;

	public GridPanel paintMe(){
		this.setBounds(0, 0, 552, 575); //needed in order for 24x25 to work
		this.fillGrid();
		this.setOpaque(false);
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
