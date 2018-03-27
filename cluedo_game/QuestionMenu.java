package cluedo_game;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class QuestionMenu {
	private JPanel initialState;
	private JPanel currentContainer;
	private JFrame currentDisplay;
	
	public QuestionMenu(JFrame currentDisplay) {
		/* Saving thre state of the board -- so it doenst get erased */
		initialState = new JPanel();
		currentContainer = new JPanel();
		currentContainer.setLayout(new BorderLayout());
		
		this.currentDisplay = new JFrame();
		this.currentDisplay = currentDisplay;
		this.currentDisplay.revalidate();
	}
}
