package cluedo_game;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
	private CharacterPictures pictures = new CharacterPictures();
	//TODO add a characterImages class
	
	@Override
	public void setLayout(LayoutManager mgr) {
		// TODO Auto-generated method stub
		super.setLayout(mgr);
	}
	
	public CharacterPane() {
		this.setLayout(new BorderLayout());
		this.add(title, BorderLayout.NORTH);
		this.add(pictures, BorderLayout.CENTER);
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
	/* Will handle all the pictures of the players */
	class CharacterPictures extends JPanel{
		private JLabel green;
		private JLabel mustard;
		private JLabel peacock;
		private JLabel plum;
		private JLabel scarlet;
		private JLabel white;
		
		private void setLables() {
			
			green = new JLabel("");
			mustard = new  JLabel("");
			peacock = new JLabel("");
			plum = new JLabel("");
			scarlet = new JLabel("");
			white = new JLabel("");
			
			/* Testing the file IO */
			loadImage("green");
			loadImage("mustard");
			loadImage("peacock");
			loadImage("plum");
			loadImage("scarlet");
			loadImage("white");
		}
		
		@Override
		public void setLayout(LayoutManager mgr) {
			// TODO Auto-generated method stub
			super.setLayout(mgr);
		}
		
		public void loadImage(String name) {
			BufferedImage image;
			
			/* java program is loaded in root dir of the system, meaning we need to navigate into the /src/ part of the code */
			try {
				if (name.equals("green")) {
					image = ImageIO.read(new File("src/characterCards/Green.png"));
					green.setIcon(new ImageIcon(image));
				}
				else if (name.equals("mustard")) {
					image = ImageIO.read(new File("src/characterCards/Mustard.png"));
					mustard.setIcon(new ImageIcon(image));
				}
				else if (name.equals("peacock")) {
					image = ImageIO.read(new File("src/characterCards/Peacock.png"));
					peacock.setIcon(new ImageIcon(image));
				}
				else if (name.equals("plum")) {
					image = ImageIO.read(new File("src/characterCards/Plum.png"));
					plum.setIcon(new ImageIcon(image));
				}
				else if (name.equals("scarlet")) {
					image = ImageIO.read(new File("src/characterCards/Scarlet.png"));
					scarlet.setIcon(new ImageIcon(image));
				}
				else if (name.equals("white")) {
					image = ImageIO.read(new File("src/characterCards/White.png"));
					white.setIcon(new ImageIcon(image));
				}
			} catch (IOException e) {
				System.err.println(e);
			}
		}
		
		public CharacterPictures() {
			setLables();
			this.setLayout(new GridLayout(1,6));
			this.add(green);
			this.add(mustard);
			this.add(peacock);
			this.add(plum);
			this.add(scarlet);
			this.add(white);
		}
		
	}
	
	/* Class to deal with the weapon selection */
	class WeaponPane extends JPanel{
		private WeaponTitle title = new WeaponTitle();
		private WeaponPictures pitures = new WeaponPictures();
		
		/* Class is going to deal with the weapon title */
		class WeaponTitle extends JPanel {
			
		}
		
		/* Class is going to deal with the weapon pictures */
		class WeaponPictures extends JPanel {
			
		}
	}



}







