package cluedo_game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
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
		
		this.currentDisplay.setVisible(true);
	}
	
	/* Method that adds a component to the container jPanel, based on given params */
	public void addItems(Component addMe, int gtidx, int gridy, int gridwidth, int gridhight) {
		
	}
}


