package cluedo_game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
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
    /* Returning the Dynamic Title */
    private String returnTitle;

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

    public String returnGuess() {
    	return this.returnTitle;
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
//                	System.out.println("I am getting called----");
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
        
        public String returnString() {
        	return title.getText();
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
                        GameLogic.Guessing.initiateRoundOfQuestioning
                                (returnString[0],
                                        returnString[1],
                                        currentPlayerGuessingToken.getInRoom().getName());


                        /* Adding the character's name to the return string */
                        returnString[2] = currentPlayerGuessing;

						/* Removing the current JPanel from the screen, and
							replacing it with the regular game board */
                        initialUserDisplay.getContentPane().removeAll();
                       
                        returnTitle = currentPlayerGuessing + " " + " guesses that: " + dynamicGuess.returnString();
                        GameLogic.addToGuessArray(returnTitle);
                        
                        // TODO: Will use this eventually, but first we go to the other question panel in UI
                        QuestionRound firstRound = new QuestionRound();
                        initialUserDisplay.add(firstRound.beginQuestionRound(returnString[0], returnString[1],
                                AcceptedUserInputs.simpleString(currentPlayerGuessingToken.getInRoom().getName()),
                                currentPlayerGuessingToken, revertToMe, initialUserDisplay));

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
                                image = CardImages.getBufferedGreen();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("mustard")) {
                                image = CardImages.getBufferedMustard();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("peacock")) {
                                image = CardImages.getBufferedPeacock();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("plum")) {
                                image = CardImages.getBufferedPlum();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("scarlet")) {
                                image = CardImages.getBufferedScarlet();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("white")) {
                                image = CardImages.getBufferedWhite();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                    else {
                        try {
                            if (name.equals("green")) {
                                image = CardImages.getBufferedGreenbw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if(name.equals("mustard")) {
                                image = CardImages.getBufferedMustardbw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("peacock")) {
                                image = CardImages.getBufferedPeacockbw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("plum")) {
                                image = CardImages.getBufferedPlumbw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("scarlet")) {
                                image = CardImages.getBufferedScarletbw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("white")) {
                                image = CardImages.getBufferedWhitebw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                        } catch (Exception a) {
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
                            for (int i = 0; i < 6; i++) {
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

                public IndividualPicture(String name, int indexInArray) {
                    this.setLayout(new BorderLayout());
                    this.objNum = indexInArray;

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
                                image = CardImages.getBufferedCandlestick();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("dagger")) {
                                image = CardImages.getBufferedDagger();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("pipe")) {
                                image = CardImages.getBufferedPipe();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("pistol")) {
                                image = CardImages.getBufferedPistol();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("rope")) {
                                image = CardImages.getBufferedRope();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("wrench")) {
//                            	System.out.println("Am I loading here?");
                            	image = CardImages.getBufferedWrench();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    else {
                        try {
                            if (name.equals("candlestick")) {
                                image = CardImages.getBufferedCandlestickbw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("dagger")) {
                                image = CardImages.getBufferedDaggerbw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("pipe")) {
                                image = CardImages.getBufferedPipebw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("pistol")) {
                                image = CardImages.getBufferedPistolbw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("rope")) {
                                image = CardImages.getBufferedRopebw();
                                currentImage.setIcon(new ImageIcon(image));
                            }
                            else if (name.equals("wrench")) {
                                 image = CardImages.getBufferedWrenchbw();
                                currentImage.setIcon(new ImageIcon(image));                           	
                            }
                        } catch (Exception d) {
                            d.printStackTrace();
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
    public class QuestionRound {
    	    	
    	private ChoicePane pane;
    	private LowerPane lowerPane;
    	private String returnString;
    	private Token playerAskingQuestion;
    	
    	/* Representing what the player presses */
    	private Boolean chooseGreen = false, choosePlum = false, chooseWhite = false, chooseScarlet = false, chooseMustard = false, choosePeacock = false;
    	private Boolean choosePistol = false, chooseDagger = false, choosePipe = false, chooseCandlestick = false, chooseRope = false, chooseWrench = false;  
    	private Boolean chooseBallroom = false, chooseBilliardroom = false, chooseConservatory  = false, chooseDiningroom = false, chooseHall = false, chooseKitchen = false, chooseLibrary = false, chooseLounge = false, chooseStudy = false;
    	
    	private Boolean canShowCharacter = false, canShowRoom = false, canShowWeapon = false;
    	
    	private JPanel revertPane;
    	private JFrame currentDisplay;
    	
    	public QuestionRound() {
        }
        
        public JPanel beginQuestionRound(String character, String weapon, String room, Token playerAsking, JPanel revertToMe, JFrame display) {
        	playerAskingQuestion = playerAsking;
        	revertPane = revertToMe;
        	currentDisplay = display;

        	JPanel returnMe = new JPanel();
            returnMe.setLayout(new GridLayout(2,1));

            isAbleToAnswer();
            
            pane = new ChoicePane(character, weapon, room);
            lowerPane = new LowerPane(character, weapon, room);
            
            returnMe.add(pane);
            returnMe.add(lowerPane);
            return returnMe;
        }
        
        /*
         * Will determine if the user has the ability to answer the guess
         */
        private void isAbleToAnswer() {
        	/* Grabbing the hand of the player who is guessing */
        	ArrayList<Card> hand = Guessing.getAnsweringPlayer().getHand();
        	
        	for (int i = 0; i < hand.size(); i++) {
        		
        		/* Checking to see if the player if able to guess the player */
        		if (hand.get(i).name.equals(Guessing.getAccusedPlayer().name)) {
        			canShowCharacter = true;
//        			System.out.println("We are able to show the character");
        		}
        		else if (hand.get(i).name.equals(Guessing.getAccusedWeapon().name)) {
        			canShowWeapon = true;
//        			System.out.println("We are able to show the weapon");
        		}
        		else if (hand.get(i).
                        name.equals
                        (Guessing.
                                getAccusedRoom().
                                name)) {
        			canShowRoom = true;
//        			System.out.println("We are able to show the room!");
        		}
        	}
        	
        }
        
        class ChoicePane extends JPanel {
        	private Title info;
        	private GuessedCards cards;
        	
        	@Override
        	public void setLayout(LayoutManager mgr) {
        		super.setLayout(mgr);
        	}
        	
        	public ChoicePane(String characterName, String weaponName, String roomName) {

        		this.setLayout(new GridLayout(2,1));
				info = new Title("---One of the Players has guessed the cards below, do you wish to aid them?---");
				cards = new GuessedCards(characterName, weaponName, roomName);
				
				this.add(info);
				this.add(cards);
        	}
        }

        /* Class that handles the title */
        class Title extends JPanel{
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
        class GuessedCards extends JPanel {
            private WeaponPictures weaponImage;
            private CharacterPictures characterImage;
            private RoomPictures roomImage;
            

            @Override
            public void setLayout(LayoutManager mgr) {
                super.setLayout(mgr);
            }

            public GuessedCards(String characterName, String weaponName, String roomName) {
//            	System.out.println("GuessedCards are getting created");
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
            				image = CardImages.getBufferedCandlestick();
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClick) {
            					chooseCandlestick = true;
            					lowerPane.changeTitle("I want to share the CandleStick card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            				
            			}
            			else if (weaponName.equals("dagger")) {
            				image = CardImages.getBufferedDagger();
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClick) {            					
            					chooseDagger = true;
            					lowerPane.changeTitle("I want to share the Dagger card with the player who made the guesss?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (weaponName.equals("pipe")) {
            				image = CardImages.getBufferedPipe();
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClick) {
            					choosePipe = true;
            					lowerPane.changeTitle("I want to share the Pipe card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (weaponName.equals("pistol")) {
            				image = CardImages.getBufferedPistol();
            				imageLabel.setIcon(new ImageIcon(image));
            				            				
            				if (userClick) {
            					choosePistol = true;
            					lowerPane.changeTitle("I want to share the Pistol card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (weaponName.equals("rope")) {
            				image = CardImages.getBufferedRope();
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClick) {
            					chooseRope = true;
            					lowerPane.changeTitle("I want to share the Rope card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (weaponName.equals("wrench")) {
                 			image = CardImages.getBufferedWrench();
                			imageLabel.setIcon(new ImageIcon(image));               			
                			chooseWrench = false;               			

            			    if (userClick) {
            					chooseWrench = true;
            					lowerPane.changeTitle("I want to share the Wrench card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            		} catch (Exception e) {
            			e.printStackTrace();
					}
            	}
            	
            	/**
            	 * Sets the current character card in black and white
            	 */
            	public void setNoColor() {
                  	BufferedImage image;
                  	try {
                  		if (weaponName.equals("candlestick")) {
                			image = CardImages.getBufferedCandlestickbw();
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseCandlestick = false;
                 		}
                		else if (weaponName.equals("dagger")) {
                			image = CardImages.getBufferedDaggerbw();
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseDagger = false;
                		}
                		else if (weaponName.equals("pipe")) {
                			image = CardImages.getBufferedPipebw();
                			imageLabel.setIcon(new ImageIcon(image));
                			choosePipe = false;
                		}
                		else if (weaponName.equals("pistol")) {
                			image = CardImages.getBufferedPistolbw();
                			imageLabel.setIcon(new ImageIcon(image));
                			choosePistol = false;
                		}
                		else if (weaponName.equals("rope")) {
                			image = CardImages.getBufferedRopebw();
                			imageLabel.setIcon(new ImageIcon(image));               			
                			chooseRope = false;
                		}
                		else if (weaponName.equals("wrench")) {
                 			image = CardImages.getBufferedWrenchbw();
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
                			image = CardImages.getNACandlestick();
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseCandlestick = false;
                 		}
                		else if (weaponName.equals("dagger")) {
                			image = CardImages.getNADagger();
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseDagger = false;
                		}
                		else if (weaponName.equals("pipe")) {
                			image = CardImages.getNAPipe();
                			imageLabel.setIcon(new ImageIcon(image));
                			choosePipe = false;
                		}
                		else if (weaponName.equals("pistol")) {
                			image = CardImages.getNAPistol();
                			imageLabel.setIcon(new ImageIcon(image));
                			choosePistol = false;
                		}
                		else if (weaponName.equals("rope")) {
                			image = CardImages.getNARope();
                			imageLabel.setIcon(new ImageIcon(image));               			
                			chooseRope = false;
                		}   
                 		else if (weaponName.equals("wrench")) {
                 			image = CardImages.getNAWrench();
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
//								System.out.println("I am not working!");
								if (isGreyed) {
//									System.out.println("I should be turning grey!");
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
            				image = CardImages.getBufferedGreen();
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClicked) {
            					chooseGreen = true;
            					lowerPane.changeTitle("I want to share the Green card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("mustard")) {
            				image = CardImages.getBufferedMustard();
            				imageLabel.setIcon(new ImageIcon(image));
            				
            			
            				if (userClicked) {
            					chooseMustard = true;
            					lowerPane.changeTitle("I want to share the Mustard card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("peacock")) {
            				image = CardImages.getBufferedPeacock();
            				imageLabel.setIcon(new ImageIcon(image));
            				
            			
            				if (userClicked) {
            					choosePeacock = true;
            					lowerPane.changeTitle("I want to share the Peacock card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("plum")) {
            				image = CardImages.getBufferedPlum();
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				
            				if (userClicked) {
            					choosePlum = true;
            					lowerPane.changeTitle("I want to share the Plum card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("scarlet")) {
            				image = CardImages.getBufferedScarlet();
            				imageLabel.setIcon(new ImageIcon(image));
            				
            				if (userClicked) {
            					chooseScarlet = true;
            					lowerPane.changeTitle("I want to share the Scarlet card with the player who made the guess?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (characterName.equals("white")) {
            				image = CardImages.getBufferedWhite();
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
                			image = CardImages.getBufferedGreenbw();
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseGreen = false;
                 		}
                        else if (characterName.equals("mustard")) {
                            image = CardImages.getBufferedMustardbw();
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseMustard = false;
                        }
                        else if (characterName.equals("peacock")) {
                            image = CardImages.getBufferedMustard();
                            imageLabel.setIcon(new ImageIcon(image));
                            choosePeacock = false;
                        }
                        else if (characterName.equals("plum")) {
                            image = CardImages.getBufferedPlumbw();
                            imageLabel.setIcon(new ImageIcon(image));
                            choosePlum = false;
                        }
                        else if (characterName.equals("scarlet")) {
                            image = CardImages.getBufferedScarletbw();
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseScarlet = false;
                        }
                        else if (characterName.equals("white")) {
                            image = CardImages.getBufferedWhitebw();
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseWhite = false;
                        }               			
                	} catch (Exception e) {
                		e.printStackTrace();
					}           		
            	}

            	public void setNAColor() {
                 	try {
                		BufferedImage image;
                 		if (characterName.equals("green")) {
                			image = CardImages.getBufferedNAGreen();
                			imageLabel.setIcon(new ImageIcon(image));
                			chooseGreen = false;
                 		}
                        else if (characterName.equals("mustard")) {
                            image = CardImages.getBufferedNAMustard();
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseMustard = false;
                        }
                        else if (characterName.equals("peacock")) {
                            image= CardImages.getBufferedNAPeacock();
                            imageLabel.setIcon(new ImageIcon(image));
                            choosePeacock = false;
                        }
                        else if (characterName.equals("plum")) {
                            image = CardImages.getBufferedNAPlum();
                            imageLabel.setIcon(new ImageIcon(image));
                            choosePlum = false;
                        }
                        else if (characterName.equals("scarlet")) {
                            image = CardImages.getBufferedNAScarlet();
                            imageLabel.setIcon(new ImageIcon(image));
                            chooseScarlet = false;
                        }
                        else if (characterName.equals("white")) {
                            image = CardImages.getBufferedNAWhite();
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
//								System.out.println("I am being exited");
								if (isGreyed) {
									setNoColor();
								}
							}
						}
						
						@Override
						public void mouseEntered(MouseEvent e) {
							/* If we can show the weapon */
							if (canShowCharacter) {
//								System.out.println("I am being entered!");
								setColor(false);
							}
						}
						
						@Override
                		public void mouseClicked(MouseEvent e) {
							/* We only want to allow the user to click on the button if they have  */
							if (canShowCharacter) {
//								System.out.println("The user actually pressed me!");
                				
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
            				image = CardImages.getBufferedBallroom();
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseBallroom = true;
            					lowerPane.changeTitle("I want to share the Ballroom card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (roomName.equals("billiardroom")) {
            				image = CardImages.getBufferedBilliardroom();
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {            					
            					chooseBilliardroom = true;
            					lowerPane.changeTitle("I want to share the BilliardRoom card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (roomName.equals("diningroom")) {
            				image = CardImages.getBufferedDiningroom();
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseConservatory = true;
            					lowerPane.changeTitle("I want to share the DiningRoom card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (roomName.equals("kitchen")) {
            				image = CardImages.getBufferedKitchen();
        					imageLabel.setIcon(new ImageIcon(image));
        				            				
        					if (userClick) {
        						chooseDiningroom = true;
        						lowerPane.changeTitle("I want to share the Kitchen card with the player who guessed?");
        						lowerPane.toggleConfirm(true);
        					}
            			}
            			else if (roomName.equals("lounge")) {
            				image = CardImages.getBufferedLounge();
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseHall = true;
            					lowerPane.changeTitle("I want to share the Lounge card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}
            			}
            			else if (roomName.equals("conservatory")) {
            				image = CardImages.getBufferedConservatory();
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseKitchen = true;
            					lowerPane.changeTitle("I want to share the Conservatory card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}       				
            			}
            			else if (roomName.equals("hall")) {
            				image = CardImages.getBufferedHall();
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseLibrary = true;
            					lowerPane.changeTitle("I want to share the Hall card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}       				
            			}
            			else if (roomName.equals("library")) {
            				image = CardImages.getBufferedLibrary();
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseLounge = true;
            					lowerPane.changeTitle("I want to share the Library card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}       				
            			}
            			else if (roomName.equals("study")) {
            				image = CardImages.getBufferedStudy();
            				imageLabel.setIcon(new ImageIcon(image));
        				
            				if (userClick) {
            					chooseStudy = true;
            					lowerPane.changeTitle("I want to share the Study card with the player who guessed?");
            					lowerPane.toggleConfirm(true);
            				}   
            			}
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
            	}
            	
            	public void setNoColor() {
            		try {
            			BufferedImage image;
            			if (roomName.equals("ballroom")) {
            				image = CardImages.getBufferedBallroombw();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseBallroom = false;
            			}
            			else if (roomName.equals("billiardroom")) {
            				image = CardImages.getBufferedBiilliardRoombw();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseBilliardroom = false;
            			}
            			else if (roomName.equals("diningroom")) {
            				image = CardImages.getBufferedDiningroombw();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseDiningroom = false;
            			}
            			else if (roomName.equals("kitchen")) {
            				image = CardImages.getBufferedKitchenbw();
            				imageLabel.setIcon(new ImageIcon(image));
        				    chooseKitchen = false;        				
            			}
            			else if (roomName.equals("lounge")) {
            				image = CardImages.getBufferedLoungebw();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseLounge = false;
            			}
            			else if (roomName.equals("conservatory")) {
            				image = CardImages.getBufferedConservatorybw();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseConservatory = false;
            			}
            			else if (roomName.equals("hall")) {
            				image = CardImages.getBufferedHallbw();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseHall = false;
            			}
            			else if (roomName.equals("library")) {
          					image = CardImages.getBufferedLibrarybw();
          					imageLabel.setIcon(new ImageIcon(image));
          					chooseLibrary = false;
            			}
            			else if (roomName.equals("study")) {
          					image = CardImages.getBufferedStudybw();
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
            				image = CardImages.getBufferedNABallroom();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseBallroom = false;
            			}
            			else if (roomName.equals("billiardroom")) {
            				image = CardImages.getBufferedNABilliardroom();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseBilliardroom = false;
            			}
            			else if (roomName.equals("diningroom")) {
            				image = CardImages.getBufferedNADiningroom();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseDiningroom = false;
            			}
            			else if (roomName.equals("kitchen")) {
            				image = CardImages.getBufferedNAKitchen();
            				imageLabel.setIcon(new ImageIcon(image));
        				    chooseKitchen = false;        				
            			}
            			else if (roomName.equals("lounge")) {
            				image = CardImages.getBufferedNALounge();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseLounge = false;
            			}
            			else if (roomName.equals("conservatory")) {
            				image = CardImages.getBufferedNAConservatory();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseConservatory = false;
            			}
            			else if (roomName.equals("hall")) {
            				image = CardImages.getBufferedNAHall();
            				imageLabel.setIcon(new ImageIcon(image));
            				chooseHall = false;
            			}
            			else if (roomName.equals("library")) {
          					image = CardImages.getBufferedNALibrary();
          					imageLabel.setIcon(new ImageIcon(image));
          					chooseLibrary = false;
            			}
            			else if (roomName.equals("study")) {
          					image = CardImages.getBufferedNAStudy();
          					imageLabel.setIcon(new ImageIcon(image));
          					chooseStudy = false;
            			}
            		} catch (Exception e) {
            			e.printStackTrace();
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
							/* We only want to allow the user to click on the button if they have  */
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
        
        class LowerPane extends JPanel{
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
        	
        	public LowerPane(String characterName, String weaponName, String roomName) {
        		lowerPaneTitle = new Title("");
        		confirmButton = new ButtonPane(characterName, weaponName, roomName);
        		checkInitialLoad();

        		setLayout(new GridLayout(2,1));
        		this.add(lowerPaneTitle);
        		this.add(confirmButton);
        	}
        }
        
        /* players can only select one of the card options before they hit confirm -- then they */
        class ButtonPane extends JPanel {
        	private  JButton confirmButton;
        	
        	String characterName;
        	String roomName;
        	String weaponName;
   
        	
            @Override
            public void setLayout(LayoutManager mgr) {
            	super.setLayout(mgr);
            }
            
            /* Create the confirmButton and set its actionListener */
            private void setConfirmListener() {
            	confirmButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int[] referenceNumbers = new int[2];
                        /* Used to see if we actually return something  */
                        Boolean match = false;

                        /* Checking to see if the user pressed one of the room cards */
                        if (chooseBallroom) {
                            referenceNumbers[0] = 1;
                            referenceNumbers[1] = 1;
                            match = true;
                        } else if (chooseBilliardroom) {
                            referenceNumbers[0] = 1;
                            referenceNumbers[1] = 4;
                            match = true;
                        } else if (chooseConservatory) {
                            referenceNumbers[0] = 1;
                            referenceNumbers[1] = 2;
                            match = true;
                        } else if (chooseDiningroom) {
                            referenceNumbers[0] = 1;
                            referenceNumbers[1] = 3;
                            match = true;
                        } else if (chooseHall) {
                            referenceNumbers[0] = 1;
                            referenceNumbers[1] = 7;
                            match = true;
                        } else if (chooseKitchen) {
                            referenceNumbers[0] = 1;
                            referenceNumbers[1] = 0;
                            match = true;
                        } else if (chooseLibrary) {
                            referenceNumbers[0] = 1;
                            referenceNumbers[1] = 5;
                            match = true;
                        } else if (chooseLounge) {
                            referenceNumbers[0] = 1;
                            referenceNumbers[1] = 6;
                            match = true;
                        } else if (chooseStudy) {
                            referenceNumbers[0] = 1;
                            referenceNumbers[1] = 8;
                            match = true;
                        }
                        /* Checking to see if the user pressed any of the weapon cards */
                        else if (chooseCandlestick) {
                            referenceNumbers[0] = 2;
                            referenceNumbers[1] = 0;
                            match = true;
                        } else if (chooseDagger) {
                            referenceNumbers[0] = 2;
                            referenceNumbers[1] = 1;
                            match = true;
                        } else if (choosePipe) {
                            referenceNumbers[0] = 2;
                            referenceNumbers[1] = 3;
                            match = true;
                        } else if (choosePistol) {
                            referenceNumbers[0] = 2;
                            referenceNumbers[1] = 2;
                            match = true;
                        } else if (chooseRope) {
                            referenceNumbers[0] = 2;
                            referenceNumbers[1] = 4;
                            match = true;
                        } else if (chooseWrench) {
                            referenceNumbers[0] = 2;
                            referenceNumbers[1] = 5;
                            match = true;
                        }
                        /* Checking to see if the user pressed any of the character cards */
                        else if (chooseWhite) {
                            referenceNumbers[0] = 0;
                            referenceNumbers[1] = 0;
                            match = true;
                        } else if (chooseGreen) {
                            referenceNumbers[0] = 0;
                            referenceNumbers[1] = 1;
                            match = true;
                        } else if (chooseMustard) {
                            referenceNumbers[0] = 0;
                            referenceNumbers[1] = 2;
                            match = true;
                        } else if (chooseScarlet) {
                            referenceNumbers[0] = 0;
                            referenceNumbers[1] = 3;
                            match = true;
                        } else if (choosePeacock) {
                            referenceNumbers[0] = 0;
                            referenceNumbers[1] = 4;
                            match = true;
                        } else if (choosePlum) {
                            referenceNumbers[0] = 0;
                            referenceNumbers[1] = 5;
                            match = true;
                        }

                        /* If the user actually has one of  */
                        if (match) {
//							System.err.println("Updating the notes correctly");
                            playerAskingQuestion.getPlayerDeckNotes().changeGuessStatus(referenceNumbers, '✔');
                            currentDisplay.getContentPane().removeAll();
                            currentDisplay.add(revertPane);
                            GameLogic.Dice.setMovesLeft(0);
                            GameLogic.checkEndOfTurn();
                            finalPanel.invalidate();
                            finalPanel.setVisible(false);
                            currentDisplay.revalidate();
                            currentDisplay.repaint();
                        }
                        else {
//							System.out.println("Am I getting called here?");
                            /* If none of the players are able to give the cards -- the  */

//								System.out.println("Am i getting called here?");
//								System.out.println("This is the current player who is confirming: " + Guessing.getAnsweringPlayer().getName());


                                currentDisplay.getContentPane().removeAll();

                                /* Getting the next player */
                                GameLogic.Guessing.answeringPlayer = GameLogic.Guessing.answeringPlayer.next();

                                if (GameLogic.Guessing.answeringPlayer == GameLogic.Guessing.getAccusingPlayer())
                                    GameLogic.Guessing.unsuccessfulGuess();
                                else
                                    JOptionPane.showMessageDialog
                                            (null, "It is now " + Guessing.answeringPlayer.getName() + "'s Turn to Answer.");

                                QuestionRound nextRound = new QuestionRound();

                                currentDisplay.getContentPane().add(nextRound.beginQuestionRound(characterName, weaponName, roomName, GameLogic.Guessing.getAnsweringPlayer(), revertPane, currentDisplay));
                                currentDisplay.revalidate();
                                currentDisplay.repaint();
                            }
                    }
				});
            	
            }
            
            public void setButtonEnable(Boolean set) {
            	confirmButton.setEnabled(set);
            }
            
            public ButtonPane(String character, String weapon, String room) {
            	characterName = character;
            	weaponName = weapon;
            	roomName = room;
            	
            	confirmButton = new JButton("Confirm");
            	setConfirmListener();
				this.add(confirmButton);
			}
        }
    }
}
