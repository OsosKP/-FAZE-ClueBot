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
    private JButton[] characters = new JButton[12];
    private JButton[] weapons = new JButton[10];
    private JButton[] rooms = new JButton[18];

    public AccuseMenu(JFrame orig, JPanel board, Token player) {
        boardDisplay = orig;
        this.board = board;
        accusingPlayer = player;

        display = new JFrame("Cluedo");
        display.setPreferredSize(new Dimension(1450, 850));

        try {
            titlePanel = createTitleImage();
            loadAllCardImages();
            createAccuseButtonPanel();
            loadBackgroundImage();
        } catch (Exception e) { e.printStackTrace(); }

        gui.setSize(1450, 850);
        gui.setOpaque(false);
        gui.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;

        leftPanel.setSize(1300, 850);
        rightPanel.setSize(100, 850);
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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 25, 0, 25);
        gui.add(rightPanel, gbc);
        display.add(gui);
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display.pack();
        display.setLocationRelativeTo(null);
        display.setVisible(true);
    }

    public void loadAllCardImages() throws Exception {
        String[] chars =    // Size 6
                {"Green", "Mustard", "Peacock", "Plum", "Scarlet", "White"};
        String[] wpns =     // Size 5
                {"Candlestick", "Dagger", "Pipe", "Pistol", "Rope"};
        String[] rms =      // Size 9
                {"ballroom", "billiardroom", "conservatory", "diningroom",
                "hall", "kitchen", "library", "lounge", "study"};

        BufferedImage charTemp;
        BufferedImage wpnTemp;
        BufferedImage rmTemp;

        for (int i=0; i<9; i++) {
            rmTemp = ImageIO.read
                    (new File("src/roomCards/" + rms[i] + ".jpeg"));
            rooms[i] = new JButton(new ImageIcon(rmTemp));
            rooms[i].setBorderPainted(false);
            rmsPanel.add(rooms[i]);

            // Get B&W image
            rmTemp = ImageIO.read
                    (new File("src/roomCards/" + rms[i] + "b&w.jpeg"));
            rooms[i + 9] = new JButton(new ImageIcon(rmTemp));

            if (i<6) {
                charTemp = ImageIO.read
                        (new File("src/characterCards/" + chars[i] + ".png"));
                characters[i] = new JButton(new ImageIcon(charTemp));
                characters[i].setBorderPainted(false);
                charsPanel.add(characters[i]);

                // Get B&W image
                charTemp = ImageIO.read
                        (new File("src/characterCards/" + chars[i] + "B&W.png"));
                characters[i + 6] = new JButton(new ImageIcon(charTemp));
            }
            if (i<5) {
                wpnTemp = ImageIO.read
                        (new File("src/weaponCards/" + wpns[i] + ".png"));
                weapons[i] = new JButton(new ImageIcon(wpnTemp));
                weapons[i].setBorderPainted(false);
                wpnsPanel.add(weapons[i]);

                // Get B&W image
                wpnTemp = ImageIO.read
                        (new File("src/weaponCards/" + wpns[i] + "B&W.png"));
                weapons[i + 5] = new JButton(new ImageIcon(wpnTemp));
            }
        }
        rmsPanel.setOpaque(false);
        rmsPanel.setSize(1200, 850);
        charsPanel.setOpaque(false);
        charsPanel.setSize(1200, 850);
        wpnsPanel.setOpaque(false);
        wpnsPanel.setSize(1200, 850);
    }

    public JPanel createTitleImage() {
        JPanel panel = new JPanel();
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
        accuse.setAlignmentX(Component.CENTER_ALIGNMENT);
        accuse.addActionListener(new AccuseListener());
        accuse.setOpaque(false);
        accuse.setBorderPainted(false);

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
            JOptionPane.showMessageDialog(null, "Check!");
        }
    }

    public void createNotesPanel() {
        notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));
        notePanel.setSize(150, 100);
        JTextArea notes = new JTextArea("", 10, 17);
        notes.setBackground(Color.BLACK);
        notes.setForeground(Color.WHITE);

        notes.append("    " + accusingPlayer.getName() + "'s notes");
        notes.append("\n-------------------------\n");
        for (String s : accusingPlayer.getNotes())
            notes.append(s + "\n");

//        JScrollPane scroller = new JScrollPane(notes);
        notePanel.add(notes);

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

    class CharacterAL implements ActionListener {
        int index;

        public CharacterAL(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i=0; i<6; i++) {
                if (i != index)
                    characters[i].setIcon(new ImageIcon());
            }
        }
    }


}
