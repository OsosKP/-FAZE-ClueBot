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
	
	public QuestionMenu(JFrame currentDisplay) {
		
		this.currentDisplay = new JFrame();
		this.currentDisplay.setSize(800,700);
		this.currentDisplay.setTitle("Temp Question Frame");
	
		currentContainer = new JPanel();
		layout = new GridBagLayout();
		gbc = new GridBagConstraints();
		currentContainer.setLayout(layout);
		
		JLabel characterLabel = new JLabel("Select a Character");
		
		
		
		JLabel greenPic = new JLabel("hello");

		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.LINE_START;
		
		convertImg("green");
		greenPic.setText("what is going wrong here?");
			
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		layout.setConstraints(characterLabel, gbc);
		currentContainer.add(characterLabel);
		
		//this.addItems(characterLabel, 0, 0, 1, 1);
		//this.addItems(greenPic, 1, 3, 1, 1);

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
	
	
	/* Method that adds a component to the container jPanel, based on given params */
	public void addItems(Component addMe, int gridx, int gridy, int gridwidth, int gridhight) {
		/*	gridx = basically the row variable in our layout
		 *  gridy = works as the column variable
		 * 
		 */
		this.gbc.gridx = gridx;
		this.gbc.gridy = gridy;
		
		this.gbc.gridwidth = gridwidth;
		this.gbc.gridheight = gridhight;
		
		layout.setConstraints(addMe, gbc);
		currentContainer.add(addMe);
	}
	
}


