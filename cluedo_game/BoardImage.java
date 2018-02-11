package cluedo_game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class BoardImage {
	private int width  = 0;
	private int height = 0;
	private int step = 0;
	BufferedImage bi;

	private JPanel imagePanel;
	private JButton[][] defaultBoard = new JButton[25][24];
	private JButton[][] editedBoard = new JButton[25][24];

	/**
	 * default constructor
	 */
	public BoardImage() {
		//this.createPanel();
	}
	/**
	 * returns a panel that can be added to a JFrame
	 * @param bi bufferedImage that will be loaded into the JPanel
	 * @return a JPanel that holds the buffered image
	 */
	public JPanel returnPanel(BufferedImage bi) {
		this.bi = bi;

		JPanel p = this.returnEmptyGridLayout();
		p = this.populateGrid(p);

		JPanel holder = this.returnFinalJPanel();
		holder.add(p);
		return holder;
	}


	/**
     * Sticks the image into a JPanel and makes that the instance variable for this class
	 */
	public void createPanel() {
        BufferedImage test = null;

        try {
            test = ImageIO.read(new File("board1.jpg"));
        }
        catch (IOException e) {
        	System.err.println("Unable to find default map file in file system...trying to fetch it from imgur...");
        	try {
            	//URL url = new URL("https://i.imgur.com/DVoCYy1.png");
            	//bi = ImageIO.read(url);
				System.out.println("Uh oh");
        	}
        	catch (Exception q) {
        		System.err.println("Unable to find image file in local system AND has no connection to imgur");
        	}

        }

		this.bi = test;

		JPanel p = this.returnEmptyGridLayout();
		p = this.populateGrid(p);

		JPanel holder = this.returnFinalJPanel();
		holder.add(p);
		imagePanel = holder;
	}
	/**
	 * test method that tests with the default map
	 * @param bi a buffered image to load into the class variable
	 */
	public void testMe(BufferedImage bi) {
        JFrame frame = new JFrame("Test Bufferedimage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.bi = bi;

		JPanel p = this.returnEmptyGridLayout();
		p = this.populateGrid(p);

        /* Setting frame size -- Will be removed */
        frame.setSize(this.width,this.height);
        frame.setVisible(true);

		JPanel holder = this.returnFinalJPanel();
		holder.add(p);
		frame.add(holder);

	}
	/**
	 * Constructs an empty grid, places it in a JPanel and returns it
	 * @return a JPanel with an empty grid layout -- will be filled with JButtons later
	 */
	private JPanel returnEmptyGridLayout() {
        /* Getting the width and height of the given image */
        width = bi.getWidth();
        height = bi.getHeight();

        /* ?? */
        step = 23;

        /* Creating JPanel -- will represent the grid that will overly the image */
        JPanel p = new JPanel(new GridLayout(25,24));
        p.setOpaque(false);
        return p;
	}
	/**
	 * method that populates a JPanel with an array of buttons, which are colored with the image of the BufferedImage defined earlier
	 * @param p the JPanel to be populated
	 * @return The JPanel after populating with buttons
	 */
	private JPanel populateGrid(JPanel p) {
        /* Var to print the number of times we have created the grid */
        int count = 0;

        /* vars that deal e */
        int xIndex = 0;
        int yIndex = 0;

        /* Loop that goes through the given image, splitting it up, the  */
        for (int ii=0; ii<height; ii+=step) {
        	/* Need to lay it out like this, otherwise we set some spaces between the JButtons */
            yIndex = 0;
        	for (int jj=0; jj<width; jj+=step) {
				System.out.println("JButton[" + xIndex + "]["+ yIndex + "]");

            	/* Getting the sub-image of the given BufferedImage */
            	//System.out.println("Getting subimage coords: " + jj + ", " + ", " + ii + ", " +step + ", "+ step);
                Image icon = bi.getSubimage(jj, ii, step, step);

                /* Creating the button that will will have the image of this current section of the map*/
                JButton button = new JButton(new ImageIcon(icon));

                /* remove the border - indicate action using a different icon */
                button.setBorder(null);//THIS IS IMPORTANT BECAUSE IT COMBINES THE SUB

                /* Making a pressed icon, otherwise the user would get no 'feedback' from the program */
                BufferedImage iconPressed = new BufferedImage(step,step,BufferedImage.TYPE_INT_ARGB);
                /* Making sure that the pressed button looks the same as the old one -- besides the green outline  */
                Graphics g = iconPressed.getGraphics();
                g.drawImage(icon, 0, 0, p);
                g.setColor(Color.RED);
                g.drawRoundRect(0, 0, iconPressed.getWidth(p)-1, iconPressed.getHeight(p)-1, 12, 12);
                g.dispose();
                button.setPressedIcon(new ImageIcon(iconPressed));

                button.setActionCommand(""+count);
                button.addActionListener(new ActionListener(){

                /* What happens when we press the button? */
                @Override
                  public void actionPerformed(ActionEvent ae) {
                         System.out.println(ae.getActionCommand());
                  }
                });

                /* Adding the button to the p JPanel */
                this.defaultBoard[xIndex][yIndex] = button;
                this.editedBoard[xIndex][yIndex] = button;

                count++;
                yIndex++;
                /* Adding button to panel -- doesnt really matter that we add this now because this is an un-edited board */
                p.add(button);
             }
            /* Incrementing array counters */
            xIndex++;
        }
        return p;
	}

	public JPanel move(int initX, int initY, int finX, int finY) {
		/* Creating new JPanel -- set = to an empty layout */
		JPanel newPanel = this.returnEmptyGridLayout();

		/* Assigning the colour of the new JButton */
		this.editedBoard[finX][finY] = this.editedBoard[initX][initY]; //this should give us the colour of

		/* Returning the old JButton to its original colour */
		this.editedBoard[initX][initY] = this.defaultBoard[initX][initY];

		/* Need to recreate the JPanel based on the new */
		for (int rows = 0; rows < 25; rows++) {
			for (int cols = 0; cols < 24; cols++) {
				/* This *should* correctly re-add the JButtons to the JPanel */
				newPanel.add(this.editedBoard[rows][cols]);
			}
		}
		return newPanel;
	}
	
	public void initPlayers() {
		
	}

	/**
	 * returns JPanel that will be used to hold the JPanel of buttons
	 * @return
	 */
	private JPanel returnFinalJPanel() {
        /* Put the first JPanel in this one -- GridBagLayout messes with the spacing to make it look nicer */
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.BLACK);
        return center;
	}
	/**
	 * returns the default array of JButtons
	 * @return
	 */
	public JButton[][] returnDefultBoard(){
		return this.defaultBoard;
	}
	/**
	 * returns the edited array of JButtons
	 * @return
	 */
	public JButton[][] returnEditedBoard(){
		return this.editedBoard;
	}

    public static void main(String[] args) throws IOException {

        BufferedImage test = null;
        BoardImage testMe = new BoardImage();

        try {
            test = ImageIO.read(new File("board1.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        testMe.testMe(test);
    }

    public JPanel getImagePanel() {
        return imagePanel;
    }
}
