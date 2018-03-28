package cluedo_game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuestionMenu {
	private JPanel initialState;
	private JFrame currentDisplay;

	
	private JPanel currentContainer;
	private GridBagLayout layout;
	private GridBagConstraints gbc;
	
	private JPanel characterPane;
	private JPanel chracterTitle;
	private JPanel characterPictures;
	
	public QuestionMenu(JFrame currentDisplay) {
		
		this.currentDisplay = new JFrame();
		this.currentDisplay.setSize(800,700);
		this.currentDisplay.setTitle("Temp Question Frame");
	
		currentContainer = new JPanel();
		characterPane = new JPanel();
		
		layout = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		currentContainer.setLayout(new BorderLayout());
		characterPane.setLayout(new BorderLayout());
		
		createCharacterTitle();
		characterPane.add(chracterTitle, BorderLayout.NORTH);
		
		/* Just for testing */
		currentContainer.add(characterPane, BorderLayout.NORTH);
		
		//convertImg("green");	
		this.currentDisplay.add(currentContainer);
		this.currentDisplay.setVisible(true);
	}
	
	private void convertImg(String name) {
		BufferedImage readImage = null;
		try {
			if (name.equals("green")) {
				readImage = ImageIO.read(new File("characterCards/Green.png"));
				System.out.println("I am actually printing stuff rn!");
			}		
		} catch (IOException e) {
			System.err.println("Bad stuff just happened");
		}
		
	}
	
	private void createCharacterTitle() {
		chracterTitle = new JPanel();
		
		chracterTitle.setLayout(layout);
		
	    JLabel title= new JLabel("Select a Character: ");
	    gbc.gridx=0;
	    gbc.gridy=0;
	    chracterTitle.add(title, gbc);
	}
}


