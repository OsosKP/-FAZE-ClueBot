package cluedo_game;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateJPanel extends JPanel {
	public static void createPanel(String name) {
		BoardImage addMe = new BoardImage();
		JPanel ourPanel = new JPanel();
		JLabel myImage = new JLabel(new ImageIcon("boardEdit.jpeg"));
		ourPanel.add(myImage);
		JFrame frame = new JFrame();
		
		frame.add(myImage);
		frame.setSize(addMe.getWidth(), addMe.getHeight());
		frame.setTitle("How does this work?");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main (String[] agrs) {
		createPanel("hello");
	}
}
