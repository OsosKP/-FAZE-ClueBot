package cluedo_game;

import java.awt.*;
import javax.swing.*;
import javax.swing.JButton;


// Input: BoardBuilder object
// Output: GUI that runs game


public class BoardImageTest {//
	//Initialize all needed objects here for the sake of neatness
	private JFrame gameBoard = new JFrame("Board");
	private JButton[][] buttonArray = new JButton[25][24];
	private BoardBuilder board;

	public BoardImageTest(BoardBuilder board){
		this.board=board;
		//setUpBoard();
	}

	public void setUpBoard(){
		gameBoard.setSize(1000,1000);
		gameBoard.getContentPane().setLayout(null);
		gameBoard.setVisible(true);
		gameBoard.getContentPane().setBackground(Color.DARK_GRAY);

		JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Color.LIGHT_GRAY);
		boardPanel.setBounds(0, 0, 1000, 1000);
		gameBoard.getContentPane().add(boardPanel);
		boardPanel.setLayout(new GridLayout(25, 24, 0, 0));

	}

	public void populateBoard(){
		for (int i = 0; i < 24; i++) {//This code is from the original BoardImage class
			for (int j = 0; j < 25; j++) {
				JButton tempButton = new JButton();
				tempButton.setSize(25,25);
			}
		}
	}

}
