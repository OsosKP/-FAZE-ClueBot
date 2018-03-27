package cluedo_game;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class QuestionMenu {
	private JPanel initialState;
	private JPanel currentContainer;
	private JFrame currentDisplay;

	public QuestionMenu(JFrame currentDisplay) {
		
		this.currentDisplay = new JFrame();
		this.currentDisplay.setSize(800,700);
		this.currentDisplay.setTitle("Temp Question Frame");
		
		
		this.currentDisplay.setVisible(true);
	}
}
