package cluedo_game;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class QuestionMenu {
	private JPanel initialState;
	private JFrame currentDisplay;
	
	/* Representing which player is currently getting 'accused'  */
	boolean characterSelected = false, isPlum = false, isGreen = false, isMustard = false, isPeacock = false, isScarlet = false, isWhite = false;
	/* Representing which weapon is getting 'guessed' */
	boolean isCandlestick = false, isDagger = false, isLeadPipe = false, isPistol = false, isRope = false;
	
	private ChoiceContainter currentContainer;
	private JPanel finalPanel;
	
	private JLabel dynamicGuess;
	
	public QuestionMenu(JFrame currentDisplay) {
		
		this.currentDisplay = new JFrame();
		this.currentDisplay.setSize(800,700);
		this.currentDisplay.setTitle("Temp Question Frame");
	
		finalPanel = new JPanel();
		finalPanel.setLayout(new BorderLayout());
		
		
		currentContainer = new ChoiceContainter();	
		dynamicGuess = new JLabel();
		
		JButton confirmButton = new JButton("Confirm");
		finalPanel.add(currentContainer, BorderLayout.CENTER);
		finalPanel.add(confirmButton, BorderLayout.SOUTH);		
		
		this.currentDisplay.add(finalPanel);
		this.currentDisplay.setVisible(true);		
	}
	
	/**
	 * updates the name section of the guessing JLabel 
	 * @param name = the name of the character we are accusing
	 */
	private void updateDynamicName(String name) {
		/* Need to make sure that the user is not allowed to select more than one character -- so we re-set the prior character thay chose */
		isPlum = false;
		isGreen = false;
		isMustard = false;
		isPeacock = false;
		isScarlet = false;
		isWhite = false;
		
		characterSelected = true;
		
		if (name.equals("plum")) {
			dynamicGuess.setText("I think Plum is the killer, and he used ?");
			isPlum = true;
		}
		else if (name.equals("green")) {
			dynamicGuess.setText("I think Green is the killer, and he used ?");
			isGreen = true;
		}
		else if (name.equals("mustard")) {
			dynamicGuess.setText("I think Mustard is the killer, and he used ?");
			isMustard = true;
		}
		else if (name.equals("peacock")) {
			dynamicGuess.setText("I think Peacock is the killer, and she used ?");
			isPeacock = true;
		}
		else if (name.equals("scarlet")) {
			dynamicGuess.setText("I think Scarlet is the killer, and she used ?");
			isScarlet = true;
		}
		else if (name.equals("white")) {
			dynamicGuess.setText("I think White is the killer, and she used ?");
			isWhite = true;
		}
	}
	
	/**
	 * 
	 */
	private void updateDynamicWeapon(String name) {
		/* Need to make sure we re-set any of booleans that MAY have been triggered by the user before -- we cant let them select more than one weapon */
		isCandlestick = false;
		isDagger = false;
		isLeadPipe = false;
		isPistol = false;
		isRope = false;
		
		/* If the use tries to click the weapon cards before the character cards -- everything wont break */
		if (!characterSelected) {
			if (name.equals("candlestick")) {
				dynamicGuess.setText("I think ? is the killer, and s/he used the candlestick");
				isCandlestick = true;
			}
			else if (name.equals("dagger")) {
				dynamicGuess.setText("I think ? is the killer, and s/he used the dagger");
				isDagger = true;
			}
			else if (name.equals("leadpipe")) {
				dynamicGuess.setText("I think ? is the killer, and s/he used the lead pipe");
				isRope = true;
			}
			else if (name.equals("pistol")) {
				dynamicGuess.setText("I think ? is the killer, and s/he used the pistol");
				isRope = true;
			}
			else if (name.equals("rope")) {
				dynamicGuess.setText("I think ? is the killer, and s/he used the rope");
				isRope = true;
			}
		}
		else {
			/* Checking to see which player is getting 'accused' */
			if (isPlum) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("I think Plum is the killer, and he used the candlestick");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("I think Plum is the killer, and he used the dagger");
					isDagger = true;
				}
				else if (name.equals("leadpipe")) {
					dynamicGuess.setText("I think Plum is the killer, and he used the lead pipe");
					isRope = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("I think Plum is the killer, and he used the pistol");
					isRope = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("I think Plum is the killer, and he used the rope");
					isRope = true;
				}
			}
			else if (isGreen) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("I think Green is the killer, and he used the candlestick");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("I think Green is the killer, and he used the dagger");
					isDagger = true;
				}
				else if (name.equals("leadpipe")) {
					dynamicGuess.setText("I think Green is the killer, and he used the lead pipe");
					isRope = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("I think Green is the killer, and he used the pistol");
					isRope = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("I think Green is the killer, and he used the rope");
					isRope = true;
				}
			}
			else if (isMustard) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("I think Mustard is the killer, and he used the candlestick");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("I think Mustard is the killer, and he used the dagger");
					isDagger = true;
				}
				else if (name.equals("leadpipe")) {
					dynamicGuess.setText("I think Mustard is the killer, and he used the lead pipe");
					isRope = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("I think Mustard is the killer, and he used the pistol");
					isRope = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("I think Mustard is the killer, and he used the rope");
					isRope = true;
				}
			}
			else if (isPeacock) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("I think Peacock is the killer, and she used the candlestick");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("I think Peacock is the killer, and she used the dagger");
					isDagger = true;
				}
				else if (name.equals("leadpipe")) {
					dynamicGuess.setText("I think Peacock is the killer, and she used the lead pipe");
					isRope = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("I think Peacock is the killer, and she used the pistol");
					isRope = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("I think Peacock is the killer, and she used the rope");
					isRope = true;
				}
			}
			else if (isScarlet) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("I think Scarlet is the killer, and she used the candlestick");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("I think Scarlet is the killer, and she used the dagger");
					isDagger = true;
				}
				else if (name.equals("leadpipe")) {
					dynamicGuess.setText("I think Scarlet is the killer, and she used the lead pipe");
					isLeadPipe = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("I think Scarlet is the killer, and she used the pistol");
					isPistol = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("I think Scarlet is the killer, and she used the rope");
					isRope = true;
				}
			}
			else if (isWhite) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("I think White is the killer, and she used the candlestick");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("I think White is the killer, and she used the dagger");
					isDagger = true;
				}
				else if (name.equals("leadpipe")) {
					dynamicGuess.setText("I think White is the killer, and she used the lead pipe");
					isLeadPipe = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("I think White is the killer, and she used the pistol");
					isPistol = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("I think White is the killer, and she used the rope");
					isRope = true;
				}
			}		
		}
	}
	
	class ChoiceContainter extends JPanel{
		private CharacterPane character = new CharacterPane();
		private WeaponPane weapon = new WeaponPane();
	
		@Override
		public void setLayout(LayoutManager mgr) {
			// TODO Auto-generated method stub
			super.setLayout(mgr);
		}
		
		public ChoiceContainter() {
			this.setLayout(new GridLayout(2,1));
			this.add(character);
			this.add(weapon);		
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
			private IndividualPicture[] characterPictures = new IndividualPicture[6];
			
			@Override
			public void setLayout(LayoutManager mgr) {
				// TODO Auto-generated method stub
				super.setLayout(mgr);
			}
	
			public CharacterPictures() {
				this.setLayout(new GridLayout(1,6));
				
				/* Creating all the pictures we need: TODO: when this is implemented,  */
				String[] characterNames = {"green", "mustard", "peacock", "plum", "scarlet", "white"};
				for (int i = 0; i < 6; i++) {
					characterPictures[i] = new IndividualPicture(characterNames[i], i);
					this.add(characterPictures[i]);
				}
			}
			
			/* Going to represent the individual characterPicture */
			class IndividualPicture extends JPanel{
				JLabel  currentImage = new JLabel();
				int objNum;
				
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
							currentImage.setIcon(new ImageIcon(image));
						}
						else if (name.equals("mustard")) {
							image = ImageIO.read(new File("src/characterCards/Mustard.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
						else if (name.equals("peacock")) {
							image = ImageIO.read(new File("src/characterCards/Peacock.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
						else if (name.equals("plum")) {
							image = ImageIO.read(new File("src/characterCards/Plum.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
						else if (name.equals("scarlet")) {
							image = ImageIO.read(new File("src/characterCards/Scarlet.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
						else if (name.equals("white")) {
							image = ImageIO.read(new File("src/characterCards/White.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
					} catch (IOException e) {
						System.err.println(e);
					}
				}
			
				public IndividualPicture(String name, int indexinArray) {
					this.objNum = indexinArray;
					this.setLayout(new BorderLayout());
					this.loadImage(name);
					this.add(currentImage);
				}
			}
		
		}
	}

	/* Class to deal with the weapon selection */
	class WeaponPane extends JPanel{
		private WeaponTitle title = new WeaponTitle();
		private WeaponPictures pitures = new WeaponPictures();
		
		@Override
		public void setLayout(LayoutManager mgr) {
			// TODO Auto-generated method stub
			super.setLayout(mgr);
		}
		
		public WeaponPane() {
			this.setLayout(new BorderLayout());
			this.add(title, BorderLayout.NORTH);
			this.add(pitures, BorderLayout.CENTER);
		}
		
		/* Class is going to deal with the weapon title */
		class WeaponTitle extends JPanel {
			private JLabel title;
			private GridBagLayout layout;
			private GridBagConstraints gbc;

	
			@Override
			public void setLayout(LayoutManager mgr) {
				// TODO Auto-generated method stub
				super.setLayout(mgr);
			}
	
			public WeaponTitle() {
				layout = new GridBagLayout();
				gbc = new GridBagConstraints();	
		
				this.setLayout(layout);
		
				title= new JLabel("Select a Weapon: ");
				gbc.gridx=0;
				gbc.gridy=0;
				this.add(title, gbc);
			}
		}
		
		/* Class is going to deal with the weapon pictures */
		class WeaponPictures extends JPanel {
			IndividualPicture[] weaponPictures = new IndividualPicture[5];
			
			@Override
			public void setLayout(LayoutManager mgr) {
				// TODO Auto-generated method stub
				super.setLayout(mgr);
			}
		
			public WeaponPictures() {
				this.setLayout(new GridLayout(1,5));
				String[] weaponNames = {"candlestick", "dagger", "pipe", "pistol", "rope"};
				
				for (int i = 0; i < 5; i++) {
					weaponPictures[i] = new IndividualPicture(weaponNames[i], i);
					this.add(weaponPictures[i]);
				}
			}
			
			/* Going to handle the individual pictures for weapons */
			class IndividualPicture extends JPanel{
				private JLabel currentImage = new JLabel();
				private int objNum;
			
				@Override
				public void setLayout(LayoutManager mgr) {
					// TODO Auto-generated method stub
					super.setLayout(mgr);
				}
			
				/* Loads the specified image into our current JLabel */
				public void loadImage(String name) {
					BufferedImage image;
			
					/* java program is loaded in root dir of the system, meaning we need to navigate into the /src/ part of the code */
					try {
						if (name.equals("candlestick")) {
							image = ImageIO.read(new File("src/weaponCards/Candlestick.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
						else if (name.equals("dagger")) {
							image = ImageIO.read(new File("src/weaponCards/Dagger.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
						else if (name.equals("pipe")) {
							image = ImageIO.read(new File("src/weaponCards/LeadPipe.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
						else if (name.equals("pistol")) {
							image = ImageIO.read(new File("src/weaponCards/Pistol.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
						else if (name.equals("rope")) {
							image = ImageIO.read(new File("src/weaponCards/Rope.png"));
							currentImage.setIcon(new ImageIcon(image));
						}
					} catch (IOException e) {
						System.err.println(e);
					}
				}	
			
				public IndividualPicture(String weaponName, int indexInAarray) {
					this.setLayout(new BorderLayout());
					this.objNum = indexInAarray;
				
					this.loadImage(weaponName);
					this.add(currentImage);
				}
			}	
		}
	}
}



	





