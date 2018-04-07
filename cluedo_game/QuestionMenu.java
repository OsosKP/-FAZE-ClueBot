package cluedo_game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import cluedo_game.GameLogic.Guessing;

public class QuestionMenu {
    /* Keeps track of the frame and JPanel it is held in, important because we need to remove this panel from it when the user is done guessing */
    private JFrame initialUserDisplay;
    private JPanel revertToMe;

    /* Representing which player is currently getting 'accused'  */
    boolean characterSelected = false, isPlum = false, isGreen = false, isMustard = false, isPeacock = false, isScarlet = false, isWhite = false;
    /* Representing which weapon is getting 'guessed' */
    boolean weaponSelected = false, isCandlestick = false, isDagger = false, isLeadPipe = false, isPistol = false, isRope = false, isWrench = false;

    private ChoiceContainer currentContainer;
    private JPanel finalPanel;

    private TitleBar dynamicGuess;
    private ConfirmButton confirm;

    private String returnString[];
    private String currentPlayerGuessing;
    private Token currentPlayerGuessingToken;

    public QuestionMenu(JFrame initialPanel, JPanel revert, Token playerGuessing) {
        this.currentPlayerGuessing = playerGuessing.getName();
        this.currentPlayerGuessingToken = playerGuessing;
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

        if (isCandlestick || isDagger || isLeadPipe || isPistol || isRope || isWrench) {
            weaponSelected = true;
        }

        /* Checking the case where the user tries to select the weapon first -- then the character */
        if (!weaponSelected) {
            switch (name) {
                case "plum":
                    dynamicGuess.setText("\"I think Plum is the killer, and he used ?\"");
                    isPlum = true;
                    break;
                case "green":
                    dynamicGuess.setText("\"I think Green is the killer, and he used ?\"");
                    isGreen = true;
                    break;
                case "mustard":
                    dynamicGuess.setText("\"I think Mustard is the killer, and he used ?\"");
                    isMustard = true;
                    break;
                case "peacock":
                    dynamicGuess.setText("\"I think Peacock is the killer, and she used ?\"");
                    isPeacock = true;
                    break;
                case "scarlet":
                    dynamicGuess.setText("\"I think Scarlet is the killer, and she used ?\"");
                    isScarlet = true;
                    break;
                case "white":
                    dynamicGuess.setText("\"I think White is the killer, and she used ?\"");
                    isWhite = true;
                    break;
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
                else if (isWrench) {
                	dynamicGuess.setText("\"I think Plum is the killer, and he used the wrench\"");
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
                else if (isWrench) {
                	dynamicGuess.setText("\"I think Green is the killer, and he used the wrench\"");
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
                else if (isWrench) {
                	dynamicGuess.setText("\"I think Mustard is the killer, and he used the wrench\"");
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
                else if (isWrench) {
                	dynamicGuess.setText("\"I think Peacock is the killer, and she used the wrench\"");
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
                else if (isWrench) {
                    dynamicGuess.setText("\"I think Scarlet is the killer, and she used the wrench\"");
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
                else if (isWrench) {
                    dynamicGuess.setText("\"I think White is the killer, and she used the wrench\"");
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
        isWrench = false;

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
            else if (name.equals("wrench")) {
                dynamicGuess.setText("\"I think ? is the killer, and s/he used the wrench\"");
                isWrench = true;           	
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
                else if (name.equals("wrench")) {
                    dynamicGuess.setText("\"I think Plum is the killer, and he used the wrench\"");
                    isWrench = true;               	
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
                else if (name.equals("wrench")) {
                    dynamicGuess.setText("\"I think Green is the killer, and he used the wrench\"");
                    isWrench = true;               	
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
                else if (name.equals("wrench")) {
                    dynamicGuess.setText("\"I think Mustard is the killer, and he used the wrench\"");
                    isWrench = true;               	
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
                 else if (name.equals("wrench")) {
                    dynamicGuess.setText("\"I think Peacock is the killer, and she used the wrench\"");
                    isWrench = true;
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
                else if (name.equals("wrench")) {
                	System.out.println("I am gettting called----");
                    dynamicGuess.setText("\"I think Scarlet is the killer, and she used the wrench\"");
                    isWrench = true;               	
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
                else if (name.equals("wrench")) {
                    dynamicGuess.setText("\"I think White is the killer, and she used the wrench\"");
                    isWrench = true;
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
        private String[] returnArray;

        @Override
        public void setLayout(LayoutManager mgr) {
            super.setLayout(mgr);
        }

        public void enableButton() {
            if ((isGreen || isMustard || isPeacock || isPlum || isScarlet || isWhite) && (isCandlestick || isDagger || isLeadPipe || isPistol || isRope || isWrench)) {
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
                    if ((isGreen || isMustard || isPeacock || isPlum || isScarlet || isWhite) && (isCandlestick || isDagger || isLeadPipe || isPistol || isRope || isWrench)) {
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
                        else if (isWrench) {
                        	returnString[1] = "wrench";
                        }
                        System.err.println("Returning from Menu: " +  returnString[0] + " " + returnString[1]);

                        // TODO: GameLogic interplay goes here
						/*
							InitiateRoundOfQuestion populates the guessed
							character and weapon, and the first player to answer
						 */                        
                        GameLogic.Guessing.initiateRoundOfQuestioning(returnString[0], returnString[1], "library");


                        /* Adding the character's name to the return string */
                        returnString[2] = currentPlayerGuessing;

						/* Removing the current JPanel from the screen, and
							replacing it with the regular game board */
                        initialUserDisplay.getContentPane().removeAll();
                        // TODO: Will use this eventually, but first we go to the other question panel in UI
                        
                        initialUserDisplay.add(QuestionRound.beginQuestionRound(returnString[0], returnString[1], "library"));

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
            private IndividualPicture[] characterPictures = new IndividualPicture[6];

            @Override
            public void setLayout(LayoutManager mgr) {
                super.setLayout(mgr);
            }

            public CharacterPictures() {
                this.setLayout(new GridLayout(1,6));

                /* Need to not show the character who is currently playing, otherwise the player would be able to guess himself as the killer :O */
                String[] characterNames = {"green", "mustard", "peacock", "plum", "scarlet", "white"};

                for (int i = 0; i < 6; i++) {
                    characterPictures[i] = new IndividualPicture(characterNames[i], i);
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
                    this.add(currentImage, BorderLayout.CENTER);
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
            private IndividualPicture[] weaponPictures = new IndividualPicture[6];

            @Override
            public void setLayout(LayoutManager mgr) {
                super.setLayout(mgr);
            }

            public WeaponPictures() {
                this.setLayout(new GridLayout(1,6));
                String[] weaponNames = {"candlestick", "dagger", "pipe", "pistol", "rope", "wrench"};

                for (int i = 0; i < 6; i++) {
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
                                image = ImageIO.read(new File("src/weaponCards/Pipe.png"));
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
                            else if (name.equals("wrench")) {
                            	System.out.println("Am I loading here?");
                            	image = ImageIO.read(new File("src/weaponCards/Wrench.png"));
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
                                image = ImageIO.read(new File("src/weaponCards/PipeB&W.png"));
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
                            else if (name.equals("wrench")) {
                            	System.out.println("I am getting blacked out here? ");
                                 image = ImageIO.read(new File("src/weaponCards/WrenchB&W.png"));
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
                            for (int i = 0; i < 6; i++) {
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
    	    	
    	private static ChoicePane pane;
    	private static LowerPane lowerPane;
    	private static String returnString;
    	
    	/* Representing what the player presses */
    	private static Boolean chooseGreen = false, choosePlum = false, chooseWhite = false, chooseScarlet = false, chooseMustard = false, choosePeacock = false;
    	private static Boolean choosePistol = false, chooseDagger = false, choosePipe = false, chooseCandlestick = false, chooseRope = false, chooseWrench = false;  
    	private static Boolean chooseBallroom = false, chooseBilliardroom = false, chooseConservatory  = false, chooseDiningroom = false, chooseHall = false, chooseKitchen = false, chooseLibrary = false, chooseLounge = false, chooseStudy = false;
    	
    	private static Boolean canShowCharacter = false, canShowRoom = false, canShowWeapon = false;
    	
    	public QuestionRound() {
            // TODO Auto-generated constructor stub
        }
        
        //TODO get room working
        public static JPanel beginQuestionRound(String character, String weapon, String room) {
       	
        	JPanel returnMe = new JPanel();
            returnMe.setLayout(new GridLayout(2,1));

            isAbleToAnswer();
            
            pane = new ChoicePane(character, weapon, room);
            lowerPane = new LowerPane();
            
            returnMe.add(pane);
            returnMe.add(lowerPane);
            return returnMe;
        }
        
        /**
         * Will determine if the user has the ability to answer the guess
         * @param character
         * @param weapon
         */
        private static void isAbleToAnswer() {
        	/* Grabbing the hand of the player who is guessing */
        	ArrayList<Card> hand = Guessing.getAnsweringPlayer().getHand();
        	
        	for (int i = 0; i < hand.size(); i++) {
        		
        		/* Checking to see if the player if able to guess the player */
        		if (hand.get(i).name.equals(Guessing.getAccusedPlayer().name)) {
        			canShowCharacter = true;
        			System.out.println("We are able to show the character");
        		}
        		else if (hand.get(i).name.equals(Guessing.getAccusedWeapon().name)) {
        			canShowWeapon = true;
        			System.out.println("We are able to show the weapon");
        		}
        		else if (hand.get(i).name.equals(Guessing.getAccusedRoom().name)) {
        			canShowRoom = true;
        			System.out.println("We are able to show the room!");
        		}
        	}
        	
        }
        
        static class ChoicePane extends JPanel {
        	private Title info;
        	private GuessedCards cards;
        	
        	@Override
        	public void setLayout(LayoutManager mgr) {
        		super.setLayout(mgr);
        	}
        	
        	public ChoicePane(String characterName, String weaponName, String roomName) {

        		this.setLayout(new GridLayout(2,1));
				info = new Title("---One of the Players has gussed the cards below, do you wish to aid him?---");
				cards = new GuessedCards(characterName, weaponName, roomName);
				
				this.add(info);
				this.add(cards);
        	}
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
            
            public void changeText(String text) {
            	title.setText(text);
            }

            public Title(String text) {
                layout = new GridBagLayout();
                gbc = new GridBagConstraints();

                this.setLayout(layout);
                title= new JLabel(text);

                gbc.gridx=0;
                gbc.gridy=0;
                this.add(title, gbc);
            }
        }
	
        /* classes to represent the images the player guessed earlier  */
        static class GuessedCards extends JPanel {
            private WeaponPictures weaponImage;
            private CharacterPictures characterImage;
            private RoomPictures roomImage;
            

            @Override
            public void setLayout(LayoutManager mgr) {
                // TODO Auto-generated method stub
                super.setLayout(mgr);
            }

            public GuessedCards(String characterName, String weaponName, String roomName) {
            	System.out.println("GuessedCards are getting created");
               this.setLayout(new GridLayout(1,3));

                /* Creating the cards */
                characterImage = new CharacterPictures(characterName); 
                weaponImage = new WeaponPictures(weaponName);
                roomImage = new RoomPictures(roomName);

                this.add(characterImage);
                this.add(weaponImage);
                this.add(roomImage);
            }
            
            /* Class that is going to deal with displaying the weapons */
            class WeaponPictures extends JPanel {
            	private JLabel imageLabel = new JLabel();
            	private String weaponName;
            	private Boolean isGreyed = false;
            	
            	public WeaponPictures(String weaponName) {
            		
					this.weaponName = weaponName;
					
					/* Setting the default image */
					setImage();
					/* Setting the actionListener */
					setListener();
					this.add(imageLabel);
            	}
            	
            	public void setGrey(Boolean set) {
            		isGreyed = set;
            	}
            	
            	/* Function that will set the weapon image  */
            	private void setImage() {
            		if (canShowWeapon) {
            			setColor(false);
                	}
                	else {
                		setNAColor();
                	}           		            	
            	}
            	
            	/**
            	 * Sets the current character card in color 
            	 */
            	public void setColor(Boolean userClick) {
            		try {
            			BufferedImage image;
            			
            			if (weaponName.equals("candlestick")) {
            				image = ImageIO.read(new File("src/weaponCards/Candlestick.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClick) {
            					chooseCandlestick = true;
            					lowerPane.changeTitle("I want to share the CandleStick card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            				
            			}
            			else if (weaponName.equals("dagger")) {
            				image = ImageIO.read(new File("src/weaponCards/Dagger.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClick) {            					
            					chooseDagger = true;
            					lowerPane.changeTitle("I want to share the Dagger card with the player who made the guesss?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (weaponName.equals("pipe")) {
            				image = ImageIO.read(new File("src/weaponCards/Pipe.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClick) {
            					choosePipe = true;
            					lowerPane.changeTitle("I want to share the Pipe card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (weaponName.equals("pistol")) {
            				image = ImageIO.read(new File("src/weaponCards/Pistol.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				            				
            				if (userClick) {
            					choosePistol = true;
            					lowerPane.changeTitle("I want to share the Pistol card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (weaponName.equals("rope")) {
            				image = ImageIO.read(new File("src/weaponCards/Rope.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClick) {
            					chooseRope = true;
            					lowerPane.changeTitle("I want to share the Rope card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (weaponName.equals("wrench")) {
                 			image = ImageIO.read(new File("src/weaponCards/Wrench.png"));
                			imageLabel.setIcon(new ImageIcon(image));               			
                			chooseWrench = false;               			

            			    if (userClick) {
            					chooseWrench = true;
            					lowerPane.changeTitle("I want to share the Wrench card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            		} catch (Exception e) {
            			System.err.println(e);
					}
            	}
            	
            	/**
            	 * Sets the current character card in black and white
            	 */
            	public void setNoColor() {
                  	BufferedImage image;
                  	try {
                  		if (weaponName.equals("candlestick")) {
                			image = ImageIO.read(new File("src/weaponCards/CandlestickB&W.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseCandlestick = false;
                 		}
                		else if (weaponName.equals("dagger")) {
                			image = ImageIO.read(new File("src/weaponCards/DaggerB&W.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseDagger = false;
                		}
                		else if (weaponName.equals("pipe")) {
                			image = ImageIO.read(new File("src/weaponCards/PipeB&W.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			choosePipe = false;
                		}
                		else if (weaponName.equals("pistol")) {
                			image = ImageIO.read(new File("src/weaponCards/PistolB&W.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			choosePistol = false;
                		}
                		else if (weaponName.equals("rope")) {
                			image = ImageIO.read(new File("src/weaponCards/RopeB&W.png"));
                			imageLabel.setIcon(new ImageIcon(image));               			
                			chooseRope = false;
                		}
                		else if (weaponName.equals("wrench")) {
                 			image = ImageIO.read(new File("src/weaponCards/WrenchB&W.png"));
                			imageLabel.setIcon(new ImageIcon(image));               			
                			chooseWrench = false;               			
                		}
                  	} catch (Exception e) {
                  		System.err.println(e);
					}
            	}

             	public void setNAColor() {
                  	BufferedImage image;
                  	try {
                  		if (weaponName.equals("candlestick")) {
                			image = ImageIO.read(new File("src/weaponCards/CandlestickNA.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseCandlestick = false;
                 		}
                		else if (weaponName.equals("dagger")) {
                			image = ImageIO.read(new File("src/weaponCards/DaggerNA.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseDagger = false;
                		}
                		else if (weaponName.equals("pipe")) {
                			image = ImageIO.read(new File("src/weaponCards/PipeNA.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			choosePipe = false;
                		}
                		else if (weaponName.equals("pistol")) {
                			image = ImageIO.read(new File("src/weaponCards/PistolNA.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			choosePistol = false;
                		}
                		else if (weaponName.equals("rope")) {
                			image = ImageIO.read(new File("src/weaponCards/RopeNA.png"));
                			imageLabel.setIcon(new ImageIcon(image));               			
                			chooseRope = false;
                		}   
                 		else if (weaponName.equals("wrench")) {
                 			image = ImageIO.read(new File("src/weaponCards/WrenchNA.png"));
                			imageLabel.setIcon(new ImageIcon(image));               			
                			chooseWrench = false;               			
                		}                 		
                  	} catch (Exception e) {
                  		System.err.println(e);
					}
            	}           	
            	
            	private void setListener() {
            		this.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseExited(MouseEvent e) {
							/* If we can show the weapon */
							if (canShowWeapon) {
								if (isGreyed) {
									setNoColor();
								}
							}
						}
						
						@Override
						public void mouseEntered(MouseEvent e) {
							/* If we can show the weapon */
							if (canShowWeapon) {
								setColor(false);
							}
						}
						
						@Override
                		public void mouseClicked(MouseEvent e) {
							/* We only want to allow the user to click on the button if they hvae  */
                			if (canShowWeapon) {
                				/* Re-setting any options that the user may have pressed earlier */
                				
                				
                				isGreyed  = false;
                				setColor(true);
                				
                				if (canShowCharacter) {
                					characterImage.setNoColor();
                					characterImage.setGrey(true);               					
                				}
                				else if (canShowRoom) {
                					roomImage.setNoColor();
                					roomImage.setGrey(true);               					
                				}
                			}
                		}					
					});
            	}
            }
            
            
            /* Class that is going to deal with displaying the character pictures */
            class CharacterPictures extends JPanel {
            	private JLabel imageLabel = new JLabel();
            	private Boolean isGreyed = false;
            	private String characterName;
            	
            	public CharacterPictures(String name) {
            		this.characterName = name;
            		setImage();
            		setListener();
            		this.add(imageLabel);
				}
            	
            	private void setImage() {
            		if (canShowCharacter) {
            			setColor(false);
            		}
            		else {
            			setNAColor();
            		}
            	}
            	
            	public void setGrey(Boolean set) {
            		isGreyed = set;
            	}
            	
            	public void setColor(Boolean userClicked) {
            		try {
            			BufferedImage image;
            			if (characterName.equals("green")) {
            				image = ImageIO.read(new File("src/characterCards/Green.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClicked) {
            					chooseGreen = true;
            					lowerPane.changeTitle("I want to share the Green card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("mustard")) {
            				image = ImageIO.read(new File("src/characterCards/Mustard.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				
            			
            				if (userClicked) {
            					chooseMustard = true;
            					lowerPane.changeTitle("I want to share the Mustard card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("peacock")) {
            				image = ImageIO.read(new File("src/characterCards/Peacock.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				
            			
            				if (userClicked) {
            					choosePeacock = true;
            					lowerPane.changeTitle("I want to share the Peacock card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("plum")) {
            				image = ImageIO.read(new File("src/characterCards/Plum.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				
            				if (userClicked) {
            					choosePlum = true;
            					lowerPane.changeTitle("I want to share the Plum card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("scarlet")) {
            				image = ImageIO.read(new File("src/characterCards/Scarlet.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClicked) {
            					chooseScarlet = true;
            					lowerPane.changeTitle("I want to share the Scarlet card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("white")) {
            				image = ImageIO.read(new File("src/characterCards/White.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            			
            				if (userClicked) {
            					chooseWhite = true;
            					lowerPane.changeTitle("I want to share the White card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            		} catch (Exception e) {
            			System.err.println(e);
					}
            	}
            	
            	public void setNoColor() {
                 	try {
                		BufferedImage image;
                 		if (characterName.equals("green")) {
                			image = ImageIO.read(new File("src/characterCards/GreenB&W.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseGreen = false;
                 		}
                        else if (characterName.equals("mustard")) {
                            image = ImageIO.read(new File("src/characterCards/MustardB&W.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseMustard = false;
                        }
                        else if (characterName.equals("peacock")) {
                            image = ImageIO.read(new File("src/characterCards/PeacockB&W.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            choosePeacock = false;
                        }
                        else if (characterName.equals("plum")) {
                            image = ImageIO.read(new File("src/characterCards/PlumB&W.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            choosePlum = false;
                        }
                        else if (characterName.equals("scarlet")) {
                            image = ImageIO.read(new File("src/characterCards/ScarletB&W.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseScarlet = false;
                        }
                        else if (characterName.equals("white")) {
                            image = ImageIO.read(new File("src/characterCards/WhiteB&W.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseWhite = false;
                        }               			
                	} catch (Exception e) {
                		System.err.print(e);
					}           		
            	}

            	public void setNAColor() {
                 	try {
                		BufferedImage image;
                 		if (characterName.equals("green")) {
                			image = ImageIO.read(new File("src/characterCards/GreenNA.png"));
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseGreen = false;
                 		}
                        else if (characterName.equals("mustard")) {
                            image = ImageIO.read(new File("src/characterCards/MustardNA.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseMustard = false;
                        }
                        else if (characterName.equals("peacock")) {
                            image = ImageIO.read(new File("src/characterCards/PeacockNA.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            choosePeacock = false;
                        }
                        else if (characterName.equals("plum")) {
                            image = ImageIO.read(new File("src/characterCards/PlumNA.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            choosePlum = false;
                        }
                        else if (characterName.equals("scarlet")) {
                            image = ImageIO.read(new File("src/characterCards/ScarletNA.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseScarlet = false;
                        }
                        else if (characterName.equals("white")) {
                            image = ImageIO.read(new File("src/characterCards/WhiteNA.png"));
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseWhite = false;
                        }               			
                	} catch (Exception e) {
                		System.err.print(e);
					}           		
            	}
            	
            	
               	private void setListener() {
            		this.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseExited(MouseEvent e) {
							/* If we can show the weapon */
							if (canShowCharacter) {
								System.out.println("I am being exited");
								if (isGreyed) {
									setNoColor();
								}
							}
						}
						
						@Override
						public void mouseEntered(MouseEvent e) {
							/* If we can show the weapon */
							if (canShowCharacter) {
								System.out.println("I am being entered!");
								setColor(false);
							}
						}
						
						@Override
                		public void mouseClicked(MouseEvent e) {
							/* We only want to allow the user to click on the button if they have  */
							if (canShowCharacter) {
								System.out.println("The user actually pressed me!");
                				
                				isGreyed  = false;
                				setColor(true);
                				
                				if (canShowWeapon) {
                					weaponImage.setNoColor();
                					weaponImage.setGrey(true);
                				}
                				else if (canShowRoom) {
                					roomImage.setNoColor();
                					roomImage.setGrey(true);               					
                				}
                			}
                		}					
					});
            	}
            }
            
            /* Class that is going to deal with displaying the room pictures */
            class RoomPictures extends JPanel {
            	private JLabel imageLabel = new JLabel();
            	private Boolean isGreyed = false;
            	private String roomName;
            	
            	
            	public RoomPictures(String name) {
            		this.roomName = name;
            		this.setImage();
            		this.setListener();
            		this.add(imageLabel);
				}
            	
            	private void setImage() {
            		if (canShowRoom) {
            			setColor(false);
            		}
            		else {
            			setNAColor();
            		}
            	}
            	
            	public void setGrey(Boolean set) {
            		isGreyed = set;
            	}
            	
            	public void setColor(Boolean userClick) {
            		try {
            			BufferedImage image;
        			
            			if (roomName.equals("ballroom")) {
            				image = ImageIO.read(new File("src/roomCards/ballroom.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseBallroom = true;
            					lowerPane.changeTitle("I want to share the Ballroom card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (roomName.equals("billiardroom")) {
            				image = ImageIO.read(new File("src/roomCards/billiardroom.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {            					
            					chooseBilliardroom = true;
            					lowerPane.changeTitle("I want to share the BilliardRoom card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (roomName.equals("diningroom")) {
            				image = ImageIO.read(new File("src/roomCards/diningroom.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseConservatory = true;
            					lowerPane.changeTitle("I want to share the DiningRoom card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (roomName.equals("kitchen")) {
            				image = ImageIO.read(new File("src/roomCards/kitchen.jpeg"));
        					imageLabel.setIcon(new ImageIcon(image));
        				            				
        					if (userClick) {
        						chooseDiningroom = true;
        						lowerPane.changeTitle("I want to share the Kitchen card with the player who guessed?");
        						lowerPane.toggleConfirm(true);
        					}
            			}
            			else if (roomName.equals("lounge")) {
            				image = ImageIO.read(new File("src/roomCards/lounge.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseHall = true;
            					lowerPane.changeTitle("I want to share the Lounge card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (roomName.equals("conservatory")) {
            				image = ImageIO.read(new File("src/roomCards/conservatory.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseKitchen = true;
            					lowerPane.changeTitle("I want to share the Conservatory card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}       				
            			}
            			else if (roomName.equals("hall")) {
            				image = ImageIO.read(new File("src/roomCards/hall.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseLibrary = true;
            					lowerPane.changeTitle("I want to share the Hall card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}       				
            			}
            			else if (roomName.equals("library")) {
            				image = ImageIO.read(new File("src/roomCards/library.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseLounge = true;
            					lowerPane.changeTitle("I want to share the Library card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}       				
            			}
            			else if (roomName.equals("study")) {
            				image = ImageIO.read(new File("src/roomCards/study.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseStudy = true;
            					lowerPane.changeTitle("I want to share the Study card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}   
            			}
            		} catch (Exception e) {
            			System.err.println(e);
            		}
            	}
            	
            	public void setNoColor() {
            		try {
            			BufferedImage image;
            			if (roomName.equals("ballroom")) {
            				image = ImageIO.read(new File("src/roomCards/ballroomb&w.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseBallroom = false;
            			}
            			else if (roomName.equals("billiardroom")) {
            				image = ImageIO.read(new File("src/roomCards/billiardroomb&w.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseBilliardroom = false;
            			}
            			else if (roomName.equals("diningroom")) {
            				image = ImageIO.read(new File("src/roomCards/diningroomb&w.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseDiningroom = false;
            			}
            			else if (roomName.equals("kitchen")) {
            				image = ImageIO.read(new File("src/roomCards/kitchenb&w.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
        				    chooseKitchen = false;        				
            			}
            			else if (roomName.equals("lounge")) {
            				image = ImageIO.read(new File("src/roomCards/loungeb&w.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseLounge = false;
            			}
            			else if (roomName.equals("conservatory")) {
            				image = ImageIO.read(new File("src/roomCards/conservatoryb&w.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseConservatory = false;
            			}
            			else if (roomName.equals("hall")) {
            				image = ImageIO.read(new File("src/roomCards/hallb&w.jpeg"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseHall = false;
            			}
            			else if (roomName.equals("library")) {
          					image = ImageIO.read(new File("src/roomCards/libraryb&w.jpeg"));
          					imageLabel.setIcon(new ImageIcon(image));
          					chooseLibrary = false;
            			}
            			else if (roomName.equals("study")) {
          					image = ImageIO.read(new File("src/roomCards/studyb&w.jpeg"));
          					imageLabel.setIcon(new ImageIcon(image));
          					chooseStudy = false;
            			}
            		} catch (Exception e) {
            			System.err.print(e);
            		}          		
            	}

            	public void setNAColor() {
            		try {
            			BufferedImage image;
            			if (roomName.equals("ballroom")) {
            				image = ImageIO.read(new File("src/roomCards/ballroomNA.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseBallroom = false;
            			}
            			else if (roomName.equals("billiardroom")) {
            				image = ImageIO.read(new File("src/roomCards/billiardroomNA.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseBilliardroom = false;
            			}
            			else if (roomName.equals("diningroom")) {
            				image = ImageIO.read(new File("src/roomCards/diningroomNA.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseDiningroom = false;
            			}
            			else if (roomName.equals("kitchen")) {
            				image = ImageIO.read(new File("src/roomCards/kitchenNA.png"));
            				imageLabel.setIcon(new ImageIcon(image));
        				    chooseKitchen = false;        				
            			}
            			else if (roomName.equals("lounge")) {
            				image = ImageIO.read(new File("src/roomCards/loungeNA.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseLounge = false;
            			}
            			else if (roomName.equals("conservatory")) {
            				image = ImageIO.read(new File("src/roomCards/conservatoryNA.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseConservatory = false;
            			}
            			else if (roomName.equals("hall")) {
            				image = ImageIO.read(new File("src/roomCards/hallNA.png"));
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseHall = false;
            			}
            			else if (roomName.equals("library")) {
          					image = ImageIO.read(new File("src/roomCards/libraryNA.png"));
          					imageLabel.setIcon(new ImageIcon(image));
          					chooseLibrary = false;
            			}
            			else if (roomName.equals("study")) {
          					image = ImageIO.read(new File("src/roomCards/studyNA.png"));
          					imageLabel.setIcon(new ImageIcon(image));
          					chooseStudy = false;
            			}
            		} catch (Exception e) {
            			System.err.print(e);
            		}          		
            	}
            	
            	
            	private void setListener() {
            		this.addMouseListener(new MouseAdapter() {	
						@Override
						public void mouseExited(MouseEvent e) {
							/* If we can show the weapon */
							if (canShowRoom) {
								if (isGreyed) {
									setNoColor();
								}
							}
						}
						
						@Override
						public void mouseEntered(MouseEvent e) {
							/* If we can show the weapon */
							if (canShowRoom) {
								setColor(false);
							}
						}
						
						@Override
                		public void mouseClicked(MouseEvent e) {
							/* We only want to allow the user to click on the button if they hvae  */
                			if (canShowRoom) {
                				/* Re-setting any options that the user may have pressed earlier */

                				isGreyed  = false;
                				setColor(true);
                				
                				if (canShowCharacter) {
                					characterImage.setNoColor();
                					characterImage.setGrey(true);               					
                				}
                				else if (canShowWeapon) {
                					weaponImage.setNoColor();
                 					weaponImage.setGrey(true);               					
                				}
                			}
                		}					
					});
            	}
            }    
        }       
        
        static class LowerPane extends JPanel{
        	private Title lowerPaneTitle;
        	private ButtonPane confirmButton;
        	/* Function that checks the of the user can actually answer the guess -- if they cant, we enable the JButon and change the text */
        	private void checkInitialLoad() {
        		
        		/* Checking to see if the user is not able to choose any options */
        		if (!canShowCharacter && !canShowRoom && !canShowWeapon) {
        			lowerPaneTitle.changeText("---You do not have any of the cards the prior player has guessed---");
        			confirmButton.setButtonEnable(true);
        		} else {
        			lowerPaneTitle.changeText("Select one of the available cards above to share with the guessing player: ");
        			confirmButton.setButtonEnable(false);
        		}
        		
        	}
        	
        	/* Need to change the title text based on what the user has selected */
        	public void changeTitle(String changeMe) {
        		lowerPaneTitle.changeText(changeMe);
        		confirmButton.setEnabled(true);
        	}
        	
        	public void toggleConfirm(Boolean set) {
        		confirmButton.setButtonEnable(set);
        	}
        	
        	public LowerPane() {
				// TODO Auto-generated constructor stub
        		lowerPaneTitle = new Title("");
        		confirmButton = new ButtonPane();
        		checkInitialLoad();

        		setLayout(new GridLayout(2,1));
        		this.add(lowerPaneTitle);
        		this.add(confirmButton);
        	}
        }
        
        
        /* players can only select one of the card options before they hit confirm -- then they */
        static class ButtonPane extends JPanel {
            JButton confirmButton;
 
            @Override
            public void setLayout(LayoutManager mgr) {
            	super.setLayout(mgr);
            }
            
            /* Create the confirmButton and set its actionListener */
            private void setConfirmListener() {
            	confirmButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						/* Checking to see if the user pressed one of the room cards */
						if(chooseBallroom) {
							
						}
						else if (chooseBilliardroom) {
							
						}
						else if (chooseConservatory) {
							
						}
						else if (chooseDiningroom) {
							
						}
						else if (chooseHall) {
							
						}
						else if (chooseKitchen) {
							
						}
						else if (chooseLibrary) {
							
						}
						else if (chooseLounge) {
							
						}
						else if (chooseStudy) {
							
						}
						/* Checking to see if the user pressed any of the weapon cards */
						else if (chooseCandlestick) {
							
						}
						else if (chooseDagger) {
							
						}
						else if (choosePipe) {
							
						}
						else if (choosePistol) {
							
						}
						else if (chooseRope) {
							
						}
						//else if (chooseWr)
					}
					
				});
            	
            }
            
            public void setButtonEnable(Boolean set) {
            	confirmButton.setEnabled(set);
            }
            
            public ButtonPane() {
				confirmButton = new JButton("Confirm");
            	setConfirmListener();
				this.add(confirmButton);
			}
        }
    }
}
