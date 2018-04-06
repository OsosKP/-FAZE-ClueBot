package cluedo_game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 * Steps:
 *  1. Show all cards possible and player's notes to the right
 *  2. Let them pick one of each, then confirm
 *  3. Go to interim screen warning other players not to look
 *  4. Go to third screen - show cards guessed and murder envelope
 *  5. Player either wins or is eliminated - put this functionality in GameLogic
 *  6. If player is eliminated and subsequent player count is 1, remaining player wins
 */

public class AccuseMenu {
    // Original state of board before entering this menu
    private final JFrame boardDisplay;
    private final JPanel board;
    // New display and its holder
    private JFrame display;
    // Pictures for final accusation
    private JLabel characterImage;
    private JLabel weaponImage;
    private JLabel roomImage;
    // Player who is accusing
    private final Token accusingPlayer;
    // Confirm button for first screen, okay button for next
    private JButton accuse;
    private JButton okay;

    // Panels for the first menu
    // gui holds the panels and fits into the JFrame
    // leftPanel holds everything but the notes and accuse button
    private JPanel gui = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel titlePanel = new JPanel();       // Width of these = 1675
    private JPanel charsPanel = new JPanel();       // Height = 197
    private JPanel wpnsPanel = new JPanel();
    private JPanel rmsPanel = new JPanel();
    private JPanel accuseButtonPanel = new JPanel();
    private JPanel notePanel = new JPanel();        // Width = 400

    // First half is color, second is B&W. Index with [i + len/2]?
    private JButton[] characters = new JButton[6];
    private JButton[] weapons = new JButton[6];
    private JButton[] rooms = new JButton[9];
    // Images for the JButtons
    private ImageIcon[] charPics = new ImageIcon[12];
    private ImageIcon[] wpnPics = new ImageIcon[12];
    private ImageIcon[] rmPics = new ImageIcon[18];
    // Strings to store the accused so we can call those cards when user confirms
        //  0 : Character
        //  1 : Weapon
        //  2 : Room
    private String[] accusedStrings = new String[3];
    private boolean[] guessed = {false, false, false};
    private boolean winner = false;

    int accuseButtonPressResult;

    public AccuseMenu(JFrame orig, JPanel board, Token player) {
        boardDisplay = orig;
        this.board = board;
        accusingPlayer = player;

        display = new JFrame("Cluedo");
        display.setPreferredSize(new Dimension(1415, 850));

        try {
            titlePanel = createTitleImage();
            loadAllCardImages();
            createAccuseButtonPanel();
            loadBackgroundImage();
        } catch (Exception e) { e.printStackTrace(); }

        gui.setSize(1415, 850);
        gui.setOpaque(false);
        gui.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;

        leftPanel.setSize(1000, 850);
        rightPanel.setSize(415, 850);
        leftPanel.setLayout(new GridLayout(5, 1));
        leftPanel.add(titlePanel);
        leftPanel.add(charsPanel);
        leftPanel.add(wpnsPanel);
        leftPanel.add(rmsPanel);
        leftPanel.setOpaque(false);

        rightPanel.setLayout(new GridLayout(2, 1));
        rightPanel.add(notePanel);

        rightPanel.add(accuseButtonPanel);
        rightPanel.setOpaque(false);

        createNotesPanel();

        gui.add(leftPanel, gbc);
        gbc.gridx = 6;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 100);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gui.add(rightPanel, gbc);
        display.add(gui);
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display.setResizable(false);
        display.pack();
        display.setLocationRelativeTo(null);
        display.setVisible(true);
    }

    public boolean getGuessed() {
        return guessed[0] && guessed[1] && guessed[2];
    }

    public void loadAllCardImages() throws Exception {
        String[] chars =    // Size 6
                {"White", "Green", "Mustard", "Scarlet", "Peacock", "Plum"};
        String[] wpns =     // Size 6
                {"Candlestick", "Dagger", "Pistol", "Pipe", "Rope", "Wrench"};
        String[] rms =      // Size 9
                {"kitchen", "ballroom", "conservatory", "diningroom",
                "billiardroom", "library", "lounge", "hall", "study"};

        BufferedImage charTemp;
        BufferedImage wpnTemp;
        BufferedImage rmTemp;

        for (int i=0; i<9; i++) {
            rmTemp = ImageIO.read
                    (new File("src/roomCards/" + rms[i] + ".jpeg"));
            rmPics[i] = new ImageIcon(rmTemp);
            rooms[i] = new JButton(rmPics[i]);
            rooms[i].setBorderPainted(false);
            rooms[i].addActionListener(new ButtonAL(i, rooms, rmPics, rms[i], 2));
            rmsPanel.add(rooms[i]);

            // Get B&W image
            rmTemp = ImageIO.read
                    (new File("src/roomCards/" + rms[i] + "b&w.jpeg"));
            rmPics[i + 9] = new ImageIcon(rmTemp);

            if (i<6) {
                charTemp = ImageIO.read
                        (new File("src/characterCards/" + chars[i] + ".png"));
                charPics[i] = new ImageIcon(charTemp);
                characters[i] = new JButton(charPics[i]);
                characters[i].setBorderPainted(false);
                characters[i].addActionListener(new ButtonAL(i, characters, charPics, chars[i].toLowerCase(), 0));
                charsPanel.add(characters[i]);

                // Get B&W image
                charTemp = ImageIO.read
                        (new File("src/characterCards/" + chars[i] + "B&W.png"));
                charPics[i + 6] = new ImageIcon(charTemp);

                wpnTemp = ImageIO.read
                        (new File("src/weaponCards/" + wpns[i] + ".png"));
                wpnPics[i] = new ImageIcon(wpnTemp);
                weapons[i] = new JButton(wpnPics[i]);
                weapons[i].setBorderPainted(false);
                weapons[i].addActionListener(new ButtonAL(i, weapons, wpnPics, wpns[i].toLowerCase(), 1));
                wpnsPanel.add(weapons[i]);

                // Get B&W image
                wpnTemp = ImageIO.read
                        (new File("src/weaponCards/" + wpns[i] + "B&W.png"));
                wpnPics[i + 6] = new ImageIcon(wpnTemp);
            }
        }
        rmsPanel.setOpaque(false);
        rmsPanel.setSize(1000, 850);
        charsPanel.setOpaque(false);
        charsPanel.setSize(1000, 850);
        wpnsPanel.setOpaque(false);
        wpnsPanel.setSize(1000, 850);
    }

    public JPanel createTitleImage() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000, 145));
        JLabel title;
        URL titleImageURL = this.getClass().getResource("accuse.png");
        BufferedImage titleImage = null;
        try {
            titleImage = ImageIO.read(titleImageURL);
        } catch (Exception e) {
            e.printStackTrace();
            // #NuclearOption
            System.exit(1);
        }

        title = new JLabel(new ImageIcon(titleImage));

        panel.add(title);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.BLACK);

        return panel;
    }

    public void createAccuseButtonPanel() throws Exception {
        URL accuseButtonURL =
                this.getClass().getResource("accusebutton.png");
        BufferedImage accuseButtonImage = ImageIO.read(accuseButtonURL);

        accuse = new JButton(new ImageIcon(accuseButtonImage));
        accuse.setAlignmentX(Component.LEFT_ALIGNMENT);
        accuse.addActionListener(new AccuseListener());
        accuse.setOpaque(false);
        accuse.setBorderPainted(false);

        // Starts invisible, becomes visible when player has guess all three
        accuse.setVisible(false);

        accuseButtonPanel.setLayout(new GridLayout(3, 1));

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        accuseButtonPanel.add(spacer);

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(accuse);
        accuseButtonPanel.add(btnPanel);
        accuseButtonPanel.setOpaque(false);
    }

    class AccuseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(getGuessed()) {
                accuseButtonPressResult = JOptionPane.showConfirmDialog(null, "Do you think it was: \n" +
                    accusedStrings[0].substring(0, 1).toUpperCase().concat(accusedStrings[0].substring(1))
                    + " in the " +
                    accusedStrings[2].substring(0, 1).toUpperCase().concat(accusedStrings[2].substring(1)) +
                    " with the " +
                    accusedStrings[1].substring(0, 1).toUpperCase().concat(accusedStrings[1].substring(1)) + "?",
                    null, JOptionPane.YES_NO_OPTION);
                if (accuseButtonPressResult == JOptionPane.YES_OPTION)
                    displayAccusationResult(GameLogic.Accusing.checkAccusation(accusedStrings, accusingPlayer));
            }
        }
    }

    public void createNotesPanel() {
        notePanel.setLayout(new BorderLayout());
        notePanel.setPreferredSize(new Dimension(125, 100));
        notePanel.setOpaque(false);

        JTextArea notes = new JTextArea("", 10, 8);
        notes.setBackground(Color.BLACK);
        notes.setForeground(Color.WHITE);
        notes.setAlignmentX(Component.LEFT_ALIGNMENT);
        notes.setEnabled(false);

        notes.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.BLACK));

        notes.append("    " + accusingPlayer.getName() + "'s notes");
        notes.append("\n-------------------------\n");
        for (String s : accusingPlayer.getNotes())
            notes.append(s + "\n");

        notePanel.add(notes, BorderLayout.WEST);

        JPanel spacer = new JPanel();
        spacer.setSize(75, 115);
        spacer.setOpaque(false);
        notePanel.add(spacer, BorderLayout.EAST);

        notePanel.validate();
    }

    public void loadBackgroundImage() throws Exception {
        BufferedImage bgImage;
        // Load background image
        URL bgImageUrl = this.getClass().getResource("accusebg.jpg");
        bgImage = ImageIO.read(bgImageUrl);
        JLabel background = new JLabel(new ImageIcon(bgImage));

        display.setContentPane(background);
    }

    class ButtonAL implements ActionListener {
        int index;
        JButton[] type;
        ImageIcon[] pics;
        String label;
        int typeIndex;

        public ButtonAL(int index, JButton[] type, ImageIcon[] pics, String label, int typeIndex) {
            this.index = index;
            this.type = type;
            this.pics = pics;
            this.label = label;
            this.typeIndex = typeIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // If this button is clicked, every other button should become black and white
            for (int i = 0; i < type.length; i++) {
                if (i != index) {
                    type[i].setIcon(pics[i + type.length]);
                    type[i].setBorderPainted(false);
                }
                else {
                    type[i].setIcon(pics[i]);
                    type[i].setBorderPainted(true);
                }
            }
            // Assign this button's string label to the appropriate accused string
            accusedStrings[typeIndex] = label;
            // Check this type as 'guessed'
            if (!guessed[typeIndex])
                guessed[typeIndex] = true;
            // Make accuse button visible if it wasn't already
                // and all three types have been guessed
            if (getGuessed() && !accuse.isVisible())
                accuse.setVisible(true);
        }
    }

    public void displayAccusationResult(boolean result) {
        if (result)
            JOptionPane.showMessageDialog(null, "Win");
        else
            JOptionPane.showMessageDialog(null, "Lose");
    }


}
