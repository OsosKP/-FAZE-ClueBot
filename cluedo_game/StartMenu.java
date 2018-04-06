package cluedo_game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

public class StartMenu extends JPanel {
    JFrame holder = new JFrame();
    JButton startGameButton;
    JButton debugStartGameButton;
    JButton audioButton;
    JLabel on;
    JLabel off;
    BufferedImage bgImage = null;
    BufferedImage titleImage = null;
    BufferedImage startButtonImage = null;
    BufferedImage debugButtonImage = null;
    BufferedImage helpButtonImage = null;
    BufferedImage soundOnImage = null;
    BufferedImage soundOffImage = null;
    boolean audioOn;

    public StartMenu() {
        audioOn = true;
        // Load button icon images
        // Start
        URL startButtonImageURL = this.getClass().getResource("startButton.jpg");
        try {
            startButtonImage = ImageIO.read(startButtonImageURL);
        } catch (Exception e) { e.printStackTrace(); }
        // Debug
        URL debugButtonImageURL = this.getClass().getResource("debugButton.jpg");
        try {
            debugButtonImage = ImageIO.read(debugButtonImageURL);
        } catch (Exception e) { e.printStackTrace(); }
        // Help
        URL helpButtonImageURL = this.getClass().getResource("helpButton.jpg");
        try {
            helpButtonImage = ImageIO.read(helpButtonImageURL);
        } catch (Exception e) { e.printStackTrace(); }

        createStartGameButtons();
        JButton helpButton = createHelpButton();

        holder.setSize(1200, 675);
        holder.setTitle("Cluedo");
        holder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load background image
        URL bgImageUrl = this.getClass().getResource("startMenuBG.jpg");
        try {
            bgImage = ImageIO.read(bgImageUrl);
        } catch (Exception e) { e.printStackTrace(); }
        JLabel background = new JLabel(new ImageIcon(bgImage));

        // Load title image
        URL titleImageURL = this.getClass().getResource("cluedotext.png");
        try {
            titleImage = ImageIO.read(titleImageURL);
        } catch (Exception e) { e.printStackTrace(); }
        JLabel title = new JLabel(new ImageIcon(titleImage));
        // Center the title image
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        createAudioButton();

        holder.setContentPane(background);
        Container contentPane = holder.getContentPane();
        contentPane.setLayout(new BoxLayout(holder.getContentPane(), BoxLayout.Y_AXIS));

        holder.add(title);

        holder.add(startGameButton);
        holder.add(debugStartGameButton);
        holder.add(helpButton);
        holder.add(audioButton);

        this.setOpaque(false);

        holder.pack();
        holder.setLocationRelativeTo(null);
        holder.setResizable(false);
        holder.setVisible(true);
        GameLogic.playMusic();
    }

    /**
     * A button that must be pressed to start the game
     */
    private void createStartGameButtons() {
        startGameButton = new JButton("", new ImageIcon(startButtonImage));
        startGameButton.setHorizontalTextPosition(JButton.CENTER);
        startGameButton.setVerticalTextPosition(JButton.CENTER);
        startGameButton.setBorderPainted(false);
        startGameButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        StartGameListener regular = new StartGameListener(false);
        startGameButton.addActionListener(regular);

        debugStartGameButton = new JButton("", new ImageIcon(debugButtonImage));
        debugStartGameButton.setHorizontalTextPosition(JButton.CENTER);
        debugStartGameButton.setVerticalTextPosition(JButton.CENTER);
        debugStartGameButton.setBorderPainted(false);
        debugStartGameButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        StartGameListener debug = new StartGameListener(true);
        debugStartGameButton.addActionListener(debug);
    }
    class StartGameListener implements ActionListener {
        private boolean debug;

        public StartGameListener(boolean debug) { this.debug = debug; }

        public void actionPerformed(ActionEvent event) {
            holder.dispose();
            LoopSound.turnMusicOff();
            GameLogic.startGame(debug);
        }
    }

    private JButton createHelpButton() {
        JButton helpButton = new JButton("", new ImageIcon(helpButtonImage));
        helpButton.setHorizontalTextPosition(JButton.CENTER);
        helpButton.setVerticalTextPosition(JButton.CENTER);
        helpButton.setBorderPainted(false);
        helpButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        helpButton.addActionListener(new HelpListener());

        return helpButton;
    }

    class HelpListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Thread helpThread = new Thread() {
                @Override
                public void run() {
                    this.setName("Help Thread");
                    HelpPage userAid = new HelpPage(false);
                }
            };
            helpThread.start();

            /* Closing the thread once it has created the HelpPage object -- the main thread handles the actionListeners anyway */
            try {
                helpThread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createAudioButton() {
        // Load title image
        URL onURL = this.getClass().getResource("soundOnButton.jpg");
        URL offURL = this.getClass().getResource("soundOffButton.jpg");

        try {
            soundOnImage = ImageIO.read(onURL);
            soundOffImage = ImageIO.read(offURL);
        } catch (Exception e) { e.printStackTrace(); }
        on = new JLabel(new ImageIcon(soundOnImage));
        on.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        off = new JLabel(new ImageIcon(soundOffImage));
        off.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        audioButton = new JButton("", new ImageIcon(soundOnImage));
        audioButton.setBorderPainted(false);
        audioButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        audioButton.addActionListener(new AudioActionListener());
    }

    public class AudioActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (audioOn) {
                audioOn = false;
                audioButton.remove(on);
                audioButton.add(off);
                audioButton.revalidate();
                LoopSound.turnMusicOff();
            }
            else {
                audioOn = true;
                audioButton.remove(off);
                audioButton.add(on);
                audioButton.revalidate();
                LoopSound.turnMusicOn();
            }

        }
    }
}