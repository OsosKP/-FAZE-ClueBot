package cluedo_game;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuestionLog {
	
	/* TODO: actually flush this out, because rn it is going to look real bad */
	public QuestionLog() {
		
		if (GameLogic.returnGuessArray() == null) {
			return;
		}
		
		JPanel panel = new JPanel();
		ArrayList<String> tempArray = GameLogic.returnGuessArray();
		int arraySize = tempArray.size();
		
		panel.setLayout(new GridLayout(arraySize, 1));
		
		for (int i = 0; i < arraySize; i++) {
			JLabel tempLabel = new JLabel(tempArray.get(i));
			panel.add(tempLabel);
		}
		
		JFrame tempFrame = new JFrame();
		
		tempFrame.setTitle("---Log of all the questions currently asked ---");
		tempFrame.setSize(400, 400);
		tempFrame.add(panel);
		tempFrame.setVisible(true);	
	}
}
