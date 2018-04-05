package cluedo_game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class QuestionMenu {
	/* Keeps track of the frame and JPanel it is held in, important because we need to remove this panel from it when the user is done guessing */
	private JFrame initialUserDisplay;
	private JPanel revertToMe;
	
	/* Representing which player is currently getting 'accused'  */
	boolean characterSelected = false, isPlum = false, isGreen = false, isMustard = false, isPeacock = false, isScarlet = false, isWhite = false;
	/* Representing which weapon is getting 'guessed' */
	boolean weaponSelected = false, isCandlestick = false, isDagger = false, isLeadPipe = false, isPistol = false, isRope = false;
	
	private ChoiceContainer currentContainer;
	private JPanel finalPanel;
	
	private TitleBar dynamicGuess;
	private ConfirmButton confirm;
	
	private String returnString[];
	private String currentPlayerGuessing;
	
	public QuestionMenu(String currentPlayerName, JFrame initialPanel, JPanel revert) {
		this.currentPlayerGuessing = currentPlayerName;				
		this.initialUserDisplay = initialPanel;
		this.revertToMe = revert;

		// Initialize guessing in Game Logic with player who is guessing
		GameLogic.Guessing.startGuessing();
	}
	public JPanel returnPanel() {
		finalPanel = new JPanel();
		finalPanel.setLayout(new BorderLayout());
		
		
		currentContainer = new ChoiceContainer();
		dynamicGuess = new TitleBar();
		confirm = new ConfirmButton();
		
		finalPanel.add(dynamicGuess, BorderLayout.NORTH);
		finalPanel.add(currentContainer, BorderLayout.CENTER);
		finalPanel.add(confirm, BorderLayout.SOUTH);
		
		return this.finalPanel;
	}
	
	/**
	 * updates the name section of the guessing JLabel 
	 * @param name = the name of the character we are accusing
	 */
	private void updateDynamicName(String name) {
		/* Need to make sure that the user is not allowed to select more than one character -- so we re-set the prior character that chose */
		isPlum = false;
		isGreen = false;
		isMustard = false;
		isPeacock = false;
		isScarlet = false;
		isWhite = false;
		
		if (isCandlestick || isDagger || isLeadPipe || isPistol || isRope) {
			weaponSelected = true;
		}
		
		/* Checking the case where the user tries to select the weapon first -- then the character */
		if (!weaponSelected) {
			if (name.equals("plum")) {
				dynamicGuess.setText("\"I think Plum is the killer, and he used ?\"");
				isPlum = true;
			}
			else if (name.equals("green")) {
				dynamicGuess.setText("\"I think Green is the killer, and he used ?\"");
				isGreen = true;
			}
			else if (name.equals("mustard")) {
				dynamicGuess.setText("\"I think Mustard is the killer, and he used ?\"");
				isMustard = true;
			}
			else if (name.equals("peacock")) {
				dynamicGuess.setText("\"I think Peacock is the killer, and she used ?\"");
				isPeacock = true;
			}
			else if (name.equals("scarlet")) {
				dynamicGuess.setText("\"I think Scarlet is the killer, and she used ?\"");
				isScarlet = true;
			}
			else if (name.equals("white")) {
				dynamicGuess.setText("\"I think White is the killer, and she used ?\"");
				isWhite = true;
			}		
		}
		else {
			if (name.equals("plum")) {
				if (isCandlestick) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the candlestick\"");
				}
				else if (isDagger) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the dagger\"");
				}
				else if (isLeadPipe) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the lead pipe\"");
				}
				else if (isPistol) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the pistol\"");
				}
				else if (isRope) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the rope\"");
				}
				isPlum = true;
			}
			else if (name.equals("green")) {
				if (isCandlestick) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the candlestick\"");
				}
				else if (isDagger) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the dagger\"");
				}
				else if (isLeadPipe) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the lead pipe\"");
				}
				else if (isPistol) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the pistol\"");
				}
				else if (isRope) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the rope\"");
				}
				isGreen = true;
			}
			else if (name.equals("mustard")) {
				if (isCandlestick) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the candlestick\"");
				}
				else if (isDagger) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the dagger\"");
				}
				else if (isLeadPipe) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the lead pipe\"");
				}
				else if (isPistol) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the pistol\"");
				}
				else if (isRope) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the rope\"");
				}
				isMustard = true;
			}
			else if (name.equals("peacock")) {
				if (isCandlestick) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the candlestick\"");
				}
				else if (isDagger) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the dagger\"");
				}
				else if (isLeadPipe) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the lead pipe\"");
				}
				else if (isPistol) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the pistol\"");
				}
				else if (isRope) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the rope\"");
				}
				isPeacock = true;
			}
			else if (name.equals("scarlet")) {
				if (isCandlestick) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the candlestick\"");
				}
				else if (isDagger) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the dagger\"");
				}
				else if (isLeadPipe) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the lead pipe\"");
				}
				else if (isPistol) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the pistol\"");
				}
				else if (isRope) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the rope\"");
				}
				isScarlet = true;
			}
			else if (name.equals("white"))  {
				if (isCandlestick) {
					dynamicGuess.setText("\"I think White is the killer, and she used the candlestick\"");
				}
				else if (isDagger) {
					dynamicGuess.setText("\"I think White is the killer, and she used the dagger\"");
				}
				else if (isLeadPipe) {
					dynamicGuess.setText("\"I think White is the killer, and she used the lead pipe\"");
				}
				else if (isPistol) {
					dynamicGuess.setText("\"I think White is the killer, and she used the pistol\"");
				}
				else if (isRope) {
					dynamicGuess.setText("\"I think White is the killer, and she used the rope\"");
				}
				isWhite = true;
			}		
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
		
		if (isPlum || isGreen || isMustard || isPeacock || isScarlet || isWhite) {
			characterSelected = true;
		}
		
		/* If the use tries to click the weapon cards before the character cards -- everything wont break */
		if (!characterSelected) {
			if (name.equals("candlestick")) {
				dynamicGuess.setText("\"I think ? is the killer, and s/he used the candlestick\"");
				isCandlestick = true;
			}
			else if (name.equals("dagger")) {
				dynamicGuess.setText("\"I think ? is the killer, and s/he used the dagger\"");
				isDagger = true;
			}
			else if (name.equals("pipe")) {
				dynamicGuess.setText("\"I think ? is the killer, and s/he used the lead pipe\"");
				isLeadPipe = true;
			}
			else if (name.equals("pistol")) {
				dynamicGuess.setText("\"I think ? is the killer, and s/he used the pistol\"");
				isPistol = true;
			}
			else if (name.equals("rope")) {
				dynamicGuess.setText("\"I think ? is the killer, and s/he used the rope\"");
				isRope = true;
			}
		}
		else {
			/* Checking to see which player is getting 'accused' */
			if (isPlum) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the candlestick\"");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the dagger\"");
					isDagger = true;
				}
				else if (name.equals("pipe")) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the lead pipe\"");
					isLeadPipe = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the pistol\"");
					isPistol = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("\"I think Plum is the killer, and he used the rope\"");
					isRope = true;
				}
			}
			else if (isGreen) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the candlestick\"");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the dagger\"");
					isDagger = true;
				}
				else if (name.equals("pipe")) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the lead pipe\"");
					isLeadPipe = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the pistol\"");
					isPistol = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("\"I think Green is the killer, and he used the rope\"");
					isRope = true;
				}
			}
			else if (isMustard) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the candlestick\"");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the dagger\"");
					isDagger = true;
				}
				else if (name.equals("pipe")) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the lead pipe\"");
					isLeadPipe = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the pistol\"");
					isPistol = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("\"I think Mustard is the killer, and he used the rope\"");
					isRope = true;
				}
			}
			else if (isPeacock) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the candlestick\"");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the dagger\"");
					isDagger = true;
				}
				else if (name.equals("pipe")) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the lead pipe\"");
					isLeadPipe = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the pistol\"");
					isPistol = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("\"I think Peacock is the killer, and she used the rope\"");
					isRope = true;
				}
			}
			else if (isScarlet) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the candlestick\"");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the dagger\"");
					isDagger = true;
				}
				else if (name.equals("pipe")) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the lead pipe\"");
					isLeadPipe = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the pistol\"");
					isPistol = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("\"I think Scarlet is the killer, and she used the rope\"");
					isRope = true;
				}
			}
			else if (isWhite) {
				if (name.equals("candlestick")) {
					dynamicGuess.setText("\"I think White is the killer, and she used the candlestick\"");
					isCandlestick = true;
				}
				else if (name.equals("dagger")) {
					dynamicGuess.setText("\"I think White is the killer, and she used the dagger\"");
					isDagger = true;
				}
				else if (name.equals("pipe")) {
					dynamicGuess.setText("\"I think White is the killer, and she used the lead pipe\"");
					isLeadPipe = true;
				}
				else if (name.equals("pistol")) {
					dynamicGuess.setText("\"I think White is the killer, and she used the pistol\"");
					isPistol = true;
				}
				else if (name.equals("rope")) {
					dynamicGuess.setText("\"I think White is the killer, and she used the rope\"");
					isRope = true;
				}
			}		
		}
	}
	/**
	 * returns the values that the user selected in the menu
	 * @return String array containing the character and weapon they are guessing
	 * 		0 - Player
	 * 		1 - Weapon
	 */
	public String[] returnValues() {
		return this.returnString;
	}
	
	/* Class that handles the characterTitle  */
	class TitleBar extends JPanel{
		private JLabel title;
		private GridBagLayout layout;
		private GridBagConstraints gbc;

		@Override
		public void setLayout(LayoutManager mgr) {
			super.setLayout(mgr);
		}
			
		public void setText(String text) {
			this.title.setText(text);
		}
	
		public TitleBar() {
			layout = new GridBagLayout();
			gbc = new GridBagConstraints();	
	
			this.setLayout(layout);
	
			title= new JLabel("Select a character and weapon to make a guess ");
			gbc.gridx=0;
			gbc.gridy=0;
			this.add(title, gbc);
			this.setBorder(new EmptyBorder(10, 10, 10, 10));
		}
	}
	// TODO: ConfirmButton
	class ConfirmButton extends JPanel{
		private JButton confirm;
		private GridBagLayout layout;
		private GridBagConstraints gbc;

		@Override
		public void setLayout(LayoutManager mgr) {
			super.setLayout(mgr);
		}
		
		public void enableButton() {
			if ((isGreen || isMustard || isPeacock || isPlum || isScarlet || isWhite) && (isCandlestick || isDagger || isLeadPipe || isPistol || isRope)) {
				confirm.setEnabled(true);
			}
			else {
				confirm.setEnabled(false);
			}
		}
				
		private void addListener() {
			confirm.addActionListener(new ActionListener() {
				
				/* Pulling the info from what the user has selected so far, and will update the return variable that will be read into the back-end */
				@Override
				public void actionPerformed(ActionEvent e) {
					/* If the user has selected one of each type of card to guess */
					if ((isGreen || isMustard || isPeacock || isPlum || isScarlet || isWhite) && (isCandlestick || isDagger || isLeadPipe || isPistol || isRope)) {
						returnString = new String[3];
						
						/* Getting the character info selected */
						if (isGreen) {
							returnString[0] = "green";
						}
						else if (isMustard) {
							returnString[0] = "mustard";
						}
						else if (isPeacock) {
							returnString[0] = "peacock";
						}
						else if (isPlum) {
							returnString[0] = "plum";
						}
						else if (isScarlet) {
							returnString[0] = "scarlet";
						}
						else if (isWhite) {
							returnString[0] = "white";
						}
						
						/* Getting the weapon info selected */
						if (isCandlestick) {
							returnString[1] = "candlestick";
						}
						else if (isDagger) {
							returnString[1] = "dagger";
						}
						else if (isLeadPipe) {
							returnString[1] = "pipe";
						}
						else if (isPistol) {
							returnString[1] = "pistol";
						}
						else if (isRope) {
							returnString[1] = "rope";
						}

						// TODO: GameLogic interplay goes here
						/*
							InitiateRoundOfQuestion populates the guessed
							character and weapon, and the first player to answer
						 */
						GameLogic.Guessing.
								InitiateRoundOfQuestioning(returnString[0], returnString[1]);


						/* Adding the character's name to the return string */
						returnString[2] = currentPlayerGuessing;
						
						/* Removing the current JPanel from the screen, and
							replacing it with the regular game board */
						initialUserDisplay.getContentPane().removeAll();
						// TODO: Will use this eventually, but first we go to the other question panel in UI
//						initialUserDisplay.add(revertToMe);
						initialUserDisplay.add(
								QuestionRound.beginQuestionRound(returnString[0], returnString[1]));
												
						initialUserDisplay.revalidate();
						initialUserDisplay.repaint();						
					}
					else {
						System.err.println("Confirm button was triggered when it shouldn't have been! -- QuestionMenu");
					}
				}
				
			});
		}
		
	
		public ConfirmButton() {
			layout = new GridBagLayout();
			gbc = new GridBagConstraints();	
			
			confirm = new JButton("Confirm");
			enableButton();
			addListener();
			this.setLayout(layout);
			
			gbc.gridx=0;
			gbc.gridy=0;
			this.add(confirm, gbc);
			this.setBorder(new EmptyBorder(10, 10, 10, 10));
		}	
	}
	
	class ChoiceContainer extends JPanel{
		private CharacterPane character = new CharacterPane();
		private WeaponPane weapon = new WeaponPane();
	
		@Override
		public void setLayout(LayoutManager mgr) {
			super.setLayout(mgr);
		}
		
		public ChoiceContainer() {
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
				super.setLayout(mgr);
			}
	
			public CharacterTitle() {
				layout = new GridBagLayout();
				gbc = new GridBagConstraints();	
		
				this.setLayout(layout);
		
				title= new JLabel("---Select a Character below--- ");
				gbc.gridx=0;
				gbc.gridy=0;
				this.add(title, gbc);
			}
		}
	
		/* Will handle all the pictures of the players */
		class CharacterPictures extends JPanel{
			private IndividualPicture[] characterPictures = new IndividualPicture[5];
			
			@Override
			public void setLayout(LayoutManager mgr) {
				super.setLayout(mgr);
			}
	
			public CharacterPictures() {
				this.setLayout(new GridLayout(1,6));
				
				/* Need to not show the character who is currently playing, otherwise the player would be able to guess himself as the killer :O */
				String[] characterNames = {"green", "mustard", "peacock", "plum", "scarlet", "white"};
				ArrayList<String> validNames = new ArrayList<String>();
			
				for (int i = 0; i < 6; i++) {
					if (!characterNames[i].equals(currentPlayerGuessing.toLowerCase())) {
						validNames.add(characterNames[i]);
					}
				}
			
				for (int i = 0; i < 5; i++) {
					characterPictures[i] = new IndividualPicture(validNames.get(i), i);
					this.add(characterPictures[i]);					
				}
			}
			
			/* Going to represent the individual characterPicture */
			class IndividualPicture extends JPanel{
				private JLabel  currentImage = new JLabel();
				private int objNum;
				private String name;
				private Boolean isGrayed = false;
				
				@Override
				public void setLayout(LayoutManager mgr) {
					super.setLayout(mgr);
				}
				
				public void loadImage(String name, Boolean colour) {
					this.name = name;
					BufferedImage image;
					
					if (colour) {
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
					else {
						try {
							if (name.equals("green")) {
								image = ImageIO.read(new File("src/characterCards/GreenB&W.png"));
								currentImage.setIcon(new ImageIcon(image));
							}
							else if(name.equals("mustard")) {
								image = ImageIO.read(new File("src/characterCards/MustardB&W.png"));
								currentImage.setIcon(new ImageIcon(image));									
							}
							else if (name.equals("peacock")) {
								image = ImageIO.read(new File("src/characterCards/PeacockB&W.png"));
								currentImage.setIcon(new ImageIcon(image));
							}
							else if (name.equals("plum")) {
								image = ImageIO.read(new File("src/characterCards/PlumB&W.png"));
								currentImage.setIcon(new ImageIcon(image));
							}
							else if (name.equals("scarlet")) {
								image = ImageIO.read(new File("src/characterCards/ScarletB&W.png"));
								currentImage.setIcon(new ImageIcon(image));
							}
							else if (name.equals("white")) {
								image = ImageIO.read(new File("src/characterCards/WhiteB&W.png"));
								currentImage.setIcon(new ImageIcon(image));
							}
						} catch (IOException a) {
							System.err.println(a);
						}
					}
				}
				/* Adds a action listener -- TODO: flush this out */
				private void addListener() {
					this.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseClicked(MouseEvent e) {	
							updateDynamicName(name);
							
							/* Checking to see if we can allow the user to exit the menu  */
							confirm.enableButton();
							
							BufferedImage image;
							/* Looping though the list and loading the black and white images on all the other character images */
							for (int i = 0; i < 5; i++) {
								/* Need to make sure that we dont over-write all the cards (just all the other ones except the one the user selected */
								if (characterPictures[i].objNum != objNum) {
									/* Over-writing the specific JLables with the B&W image */
									characterPictures[i].isGrayed = true;
									characterPictures[i].loadImage(characterPictures[i].name, false);	
								}	
								else {
									characterPictures[i].isGrayed = false;
									characterPictures[i].loadImage(characterPictures[i].name, true);
								}
							}
						}
						/* Switches to the default image on hover */
						@Override
						public void mouseEntered(MouseEvent e) {
							loadImage(name, true);
						}
						/* Defaults the card to grey again if it has been grayed out already */
						@Override
						public void mouseExited(MouseEvent e) {
							/* If the card has been grayed, ie not clicked by the user, we want to revert it back */
							if (isGrayed) {
								loadImage(name, false);
							}
						}
					});				
				}
			
				public IndividualPicture(String name, int indexinArray) {
					this.setLayout(new BorderLayout());
					this.objNum = indexinArray;
					
					this.loadImage(name, true);
					this.addListener();
					this.add(currentImage);
				}
			}
		
		}
	}

	/* Class to deal with the weapon selection */
	class WeaponPane extends JPanel{
		private WeaponTitle title = new WeaponTitle();
		private WeaponPictures pictures = new WeaponPictures();
		
		@Override
		public void setLayout(LayoutManager mgr) {
			// TODO Auto-generated method stub
			super.setLayout(mgr);
		}
		
		public WeaponPane() {
			this.setLayout(new BorderLayout());
			this.add(title, BorderLayout.NORTH);
			this.add(pictures, BorderLayout.CENTER);
		}
		
		/* Class is going to deal with the weapon title */
		class WeaponTitle extends JPanel {
			private JLabel title;
			private GridBagLayout layout;
			private GridBagConstraints gbc;

	
			@Override
			public void setLayout(LayoutManager mgr) {
				super.setLayout(mgr);
			}
	
			public WeaponTitle() {
				layout = new GridBagLayout();
				gbc = new GridBagConstraints();	
		
				this.setLayout(layout);
		
				title= new JLabel("---Select a Weapon below--- ");
				gbc.gridx=0;
				gbc.gridy=0;
				this.add(title, gbc);
			}
		}
		
		/* Class is going to deal with the weapon pictures */
		class WeaponPictures extends JPanel {
			private IndividualPicture[] weaponPictures = new IndividualPicture[5];
			
			@Override
			public void setLayout(LayoutManager mgr) {
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
				private String name;
				private Boolean isGrayed = false;
			
				@Override
				public void setLayout(LayoutManager mgr) {
					super.setLayout(mgr);
				}
			
				/* Loads the specified image into our current JLabel */
				public void loadImage(String name, Boolean colour) {
					BufferedImage image;
					this.name = name;
					
					if (colour) {
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
							System.err.println(e.getMessage());
						}
					}
					else {
						try {
							if (name.equals("candlestick")) {
								image = ImageIO.read(new File("src/weaponCards/CandlestickB&W.png"));
								currentImage.setIcon(new ImageIcon(image));
							}
							else if (name.equals("dagger")) {
								image = ImageIO.read(new File("src/weaponCards/DaggerB&W.png"));
								currentImage.setIcon(new ImageIcon(image));
							}
							else if (name.equals("pipe")) {
								image = ImageIO.read(new File("src/weaponCards/LeadPipeB&W.png"));
								currentImage.setIcon(new ImageIcon(image));										
							}
							else if (name.equals("pistol")) {
								image = ImageIO.read(new File("src/weaponCards/PistolB&W.png"));
								currentImage.setIcon(new ImageIcon(image));										
							}
							else if (name.equals("rope")) {
								image = ImageIO.read(new File("src/weaponCards/RopeB&W.png"));
								currentImage.setIcon(new ImageIcon(image));										
							}
							} catch (IOException d) {
								System.err.println(d);
							}					
					}
				}	
				/* Just to segment the code out more TODO: flush this out to make the other JLabels in the array grey out + update the dynamicGuess */
				private void addListener() {
					this.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseClicked(MouseEvent e) {	
							updateDynamicWeapon(name);
							/* Checking to see if we can let the user close this menu */
							confirm.enableButton();
							
							BufferedImage image;
							for (int i = 0; i < 5; i++) {
								if (weaponPictures[i].objNum != objNum) {
										weaponPictures[i].isGrayed = true;
										weaponPictures[i].loadImage(weaponPictures[i].name, false);
								}
								else {
									weaponPictures[i].isGrayed = false;
									weaponPictures[i].loadImage(weaponPictures[i].name, true);
								}
							}
						}
						/* Switches to the default image on hover */
						@Override
						public void mouseEntered(MouseEvent e) {
							loadImage(name, true);
						}
						/* Defaults the card to grey again if it has been grayed out already */
						@Override
						public void mouseExited(MouseEvent e) {
							/* If the card has been grayed, ie not clicked by the user, we want to revert it back */
							if (isGrayed) {
								loadImage(name, false);
							}
						}					
					});
				}
			
				public IndividualPicture(String weaponName, int indexInAarray) {
					this.setLayout(new BorderLayout());
					this.objNum = indexInAarray;
				
					this.loadImage(weaponName, true);
					this.addListener();
					this.add(currentImage);	
				}
			}	
		}
	}
	public void revertToRegularDisplay() {
		initialUserDisplay.getContentPane().removeAll();
		initialUserDisplay.getContentPane().add(revertToMe);
		initialUserDisplay.revalidate();
		initialUserDisplay.repaint();
		initialUserDisplay.setVisible(true);
	}
	
	/* Class that will handle the players confirming the question that was proposed by another player */
	public static class QuestionRound {
		private static GuessedCards showCards;
		private static Title questionTitle;
		private static ButtonPane confirmButtons;

		public QuestionRound() {
			// TODO Auto-generated constructor stub
		}
		
		public static JPanel beginQuestionRound(String character, String weapon) {
			JPanel returnMe = new JPanel();
			returnMe.setLayout(new BorderLayout());
			
			questionTitle = new Title();
			showCards = new GuessedCards(character, weapon);
			
			return returnMe;
		}

		/* Class that handles the title */
		static class Title extends JPanel{
			private JLabel title;
			private GridBagLayout layout;
			private GridBagConstraints gbc;

			@Override
			public void setLayout(LayoutManager mgr) {
				super.setLayout(mgr);
			}
	
			public Title() {
				layout = new GridBagLayout();
				gbc = new GridBagConstraints();	
		
				this.setLayout(layout);
				title= new JLabel("---One of the Players has gussed the cards below, do you wish to aid him?---");
		
				gbc.gridx=0;
				gbc.gridy=0;
				this.add(title, gbc);
			}		
		}
		
		/* classes to represent the images the player guessed earlier  */
		static class GuessedCards extends JPanel{
			private IndividualPicture characterImage;
			private IndividualPicture weaponImage;

			@Override
			public void setLayout(LayoutManager mgr) {
				// TODO Auto-generated method stub
				super.setLayout(mgr);
			}
				
			public GuessedCards(String characterName, String weaponName) {
				this.setLayout(new GridLayout(1,2));
				
				/* Creating the cards */
				characterImage = new IndividualPicture(characterName, "character");
				weaponImage = new IndividualPicture(weaponName, "weapon");
				
				this.add(characterImage);
				this.add(weaponImage);
			}
				
				/* Class that is going to handle the individual pictures */
				class IndividualPicture extends JPanel{
					private JLabel imageLabel;
					private String type;
					private String name;
			
					private void setImage(String name, String type) {
						BufferedImage image = null;
						try {
							if (type.equals("weapon")) {
								image = ImageIO.read(new File("src/weaponCards/" + name.substring(0, 1).toUpperCase() + name.substring(1) + ".png"));

							}
							else if (type.equals("character")) {
								image = ImageIO.read(new File("src/characterCards/" + name.substring(0, 1).toUpperCase() + name.substring(1) + ".png"));
							}						
						} catch (Exception e) {
							System.err.println(e);
						}

						imageLabel.setIcon(new ImageIcon(image));
					}	
					
					@Override
					public void setLayout(LayoutManager mgr) {
						// TODO Auto-generated method stub
						super.setLayout(mgr);
					}
					
					public IndividualPicture(String name, String type) {
						this.setLayout(new BorderLayout());
						setImage(name, type);
						this.type = type;
						this.name = name;
						this.add(imageLabel, BorderLayout.CENTER);
					}
				}
			}
			
			/* class that is going to handle the button inputs */
			/* players can only select one of the card options beore they hit confirm -- then they */
			static class ButtonPane extends JPanel{
				JButton confirmButton;
				JButton neither;
				JButton showNotes;
					
			}
	}
}




	





