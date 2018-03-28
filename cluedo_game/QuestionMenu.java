package cluedo_game;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuestionMenu {
	private JPanel initialState;
	private JFrame currentDisplay;

	
	private JPanel currentContainer;
	
	private CharacterPane character;
	
	public QuestionMenu(JFrame currentDisplay) {
		
		this.currentDisplay = new JFrame();
		this.currentDisplay.setSize(800,700);
		this.currentDisplay.setTitle("Temp Question Frame");
	
		currentContainer = new JPanel();
		character = new CharacterPane();
		
		currentContainer.setLayout(new BorderLayout());
		
		/* Just for testing */
		currentContainer.add(character, BorderLayout.NORTH);
		
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
}

/* Class that is going to manage all the character input stuff */
class CharacterPane extends JPanel{
	private CharacterTitle title =  new CharacterTitle();
	//TODO add a characterImages class
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public CharacterPane() {
		this.setLayout(new BorderLayout());
		this.add(title, BorderLayout.NORTH);
	}

	/* Class that handles the characterTitle  */
	class CharacterTitle extends JPanel{
		private JLabel title;
		private GridBagLayout layout;
		private GridBagConstraints gbc;

	
		@Override
		public void setLayout(LayoutManager mgr) {
			// TODO Auto-generated method stub
			super.setLayout(mgr);
		}
	
		public CharacterTitle() {
			layout = new GridBagLayout();
			gbc = new GridBagConstraints();	
		
			this.setLayout(layout);
		
			title= new JLabel("Select a Character: ");
			gbc.gridx=0;
			gbc.gridy=0;
			this.add(title, gbc);
		}
}





}







