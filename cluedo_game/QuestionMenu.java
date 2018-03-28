package cluedo_game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		
		JLabel characterLabel = new JLabel("Select a Character");
		this.addItems(characterLabel, 0, 2, 1, 1);
		
		this.currentDisplay.add(currentContainer);
		this.currentDisplay.setVisible(true);
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


