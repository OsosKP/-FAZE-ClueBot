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
    BufferedImage bgImage = null;
    BufferedImage titleImage = null;
    BufferedImage buttonImage = null;
    int buttonWidth;
    int buttonHeight;

    public StartMenu() {
        // Load button icon image
        URL buttonImageURL = this.getClass().getResource("button.jpg");
        try {
            buttonImage = ImageIO.read(buttonImageURL);
        } catch (Exception e) { e.printStackTrace(); }
        buttonWidth = buttonImage.getWidth();
        buttonHeight = buttonImage.getHeight();

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

        holder.setContentPane(background);
        Container contentPane = holder.getContentPane();
        contentPane.setLayout(new BoxLayout(holder.getContentPane(), BoxLayout.Y_AXIS));

        holder.add(title);

        holder.add(startGameButton);
        holder.add(debugStartGameButton);
        holder.add(helpButton);

        this.setOpaque(false);

        holder.pack();
        holder.setLocationRelativeTo(null);

        holder.setVisible(true);
    }

    /**
     * A button that must be pressed to start the game
     */
    private void createStartGameButtons() {
        startGameButton = new JButton("Start Game", new ImageIcon(buttonImage));
        startGameButton.setHorizontalTextPosition(JButton.CENTER);
        startGameButton.setVerticalTextPosition(JButton.CENTER);
        startGameButton.setBorderPainted(false);
        startGameButton.setFont(new Font("Arial", Font.ITALIC, 32));
        startGameButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        StartGameListener regular = new StartGameListener(false);
        startGameButton.addActionListener(regular);

        debugStartGameButton = new JButton("Debug Mode", new ImageIcon(buttonImage));
        debugStartGameButton.setHorizontalTextPosition(JButton.CENTER);
        debugStartGameButton.setVerticalTextPosition(JButton.CENTER);
        debugStartGameButton.setBorderPainted(false);
        debugStartGameButton.setFont(new Font("Arial", Font.ITALIC, 32));
        debugStartGameButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        StartGameListener debug = new StartGameListener(true);
        debugStartGameButton.addActionListener(debug);
    }
    class StartGameListener implements ActionListener {
        private boolean debug;

        public StartGameListener(boolean debug) { this.debug = debug; }

        public void actionPerformed(ActionEvent event) {
            holder.dispose();
            GameLogic.startGame(debug);
        }
    }

    private JButton createHelpButton() {
        JButton helpButton = new JButton("Help", new ImageIcon(buttonImage));
        helpButton.setHorizontalTextPosition(JButton.CENTER);
        helpButton.setVerticalTextPosition(JButton.CENTER);
        helpButton.setBorderPainted(false);
        helpButton.setFont(new Font("Arial", Font.ITALIC, 32));
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
                    HelpPage userAid = new HelpPage();
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
}