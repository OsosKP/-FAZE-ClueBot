// Josh King - 16200099
// George Ridgway - 16200132
// Kelsey Osos - 16201972

package cluedo_game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class BoardImage {
	private final int WIDTH  = 552; //Width of the board, should be 552 pixels
	private final int HEIGHT = 575; //Height of the board, should be 575 pixels
	private final int step = 23;//Incrementer for for loop to correctly move to next square,size of each square
	BufferedImage bi;//Object for the board itself
	private final Color PURPLE = new Color(75, 0, 130); // Poor Professor Plum should get his own color, too

	private JButton[] weaponArray;
	private ArrayList<Integer[]> weaponRoomLocations;

	private JPanel imagePanel;
	private JButton[][] defaultBoard = new JButton[25][24];
	private JButton[][] editedBoard = new JButton[25][24];

	//This is super janky and will fix later
	static int[] whiteindex = {0,9};
	static int[] greenindex = {0,14};
	static int[] peacockindex = {6,23};
	static int[] mustardindex = {17,0};
	static int[] plumindex = {19,23};
	static int[] scarletindex = {24,7};

	//DELETE
	// int myVar = 24;


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


	public JPanel returnPanel() {
		try {
			URL imageUrl = this.getClass().getResource("board1.jpg");
			bi = ImageIO.read(imageUrl);
		} catch (Exception resourceLoadException){
			System.err.println("Unable to find default map file in file system...trying to fetch it from imgur...");
			try {
				URL url = new URL("https://i.imgur.com/7eO9OJA.jpg");
				bi = ImageIO.read(url);
				System.out.println("Uh oh");
			}
			catch (Exception q) {
				System.err.println("Unable to find image file in local system AND has no connection to imgur");
			}
		}

		JPanel p = this.returnEmptyGridLayout();
		p = this.populateGrid(p);

		JPanel holder = this.returnFinalJPanel();
		holder.add(p);
		return holder;
	}

	public JPanel getImagePanel() {
		return imagePanel;
	}

	/**
	 * Constructs an empty grid, places it in a JPanel and returns it
	 * @return a JPanel with an empty grid layout -- will be filled with JButtons later
	 */
	private JPanel returnEmptyGridLayout() {
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
        for (int ii=0; ii<HEIGHT; ii+=step) {
        	/* Need to lay it out like this, otherwise we set some spaces between the JButtons */
            yIndex = 0;
        	for (int jj=0; jj<WIDTH; jj+=step) {
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


                //button.setActionCommand(""+xIndex+","+yIndex);
				button.setActionCommand(""+xIndex+","+yIndex+" is a "+ GameLogic.currentBoard.getSquare(xIndex,yIndex).getClass());
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
                /* Adding button to panel -- doesn't really matter that we add this now because this is an un-edited board */
                //p.add(button);
             }

            /* Incrementing array counters */
            xIndex++;
        }
		drawForTheFistTime(p);
		return p;
	}


	/*
	 * "moves" the players on the screen by swapping the JButtons on the screen
	 * @return the JPanel that will represent the new board
	 */

	public JPanel move(int[] init, int[] fin) {
		//Check to see if you're about to move on an entry square
		if (GameLogic.currentBoard.getSquare(fin) instanceof EntrySquare){
			System.out.println("["+fin[0]+","+fin[1]+"] is an entry square " + GameLogic.currentBoard.getSquare(fin).getClass());
			return null;
		}
		else{
			System.out.println("["+fin[0]+","+fin[1]+"] is a " + GameLogic.currentBoard.getSquare(fin).getClass());
			return swapsquares(init, fin);
		}
	}

	public JPanel passageMove(Room roomfrom, Room roomto){

		System.out.println("\t\tFrom capacity: " + roomfrom.getCapacity());
		System.out.println("\t\tTocapacity: " + roomto.getCapacity());

		int fromcapacity = roomfrom.getCapacity();
		int tocapacity = roomto.getCapacity();

		int[] init = roomfrom.getPlayerFloors().get(fromcapacity);
		int[] fin = roomto.getPlayerFloors().get(tocapacity-1);

		System.out.println("\t New capacities From: " + roomfrom.getCapacity() + " to: " + roomto.getCapacity());

		return swapsquares(init, fin);

	}

	public JPanel swapsquares(int[] init, int[] fin){
		/* Creating new JPanel -- set = to an empty layout */
		//System.out.println("Init: ["+init[0]+","+init[1]+"] Fin: ["+fin[0] + "," + fin[1] + "]");
		JPanel newPanel = returnEmptyGridLayout();
		JPanel returnMe = returnFinalJPanel();

		if ((init!=null)&&(fin!=null)){//Safety, can be called null
			/* Assigning the colour of the new JButton */
			this.editedBoard[fin[0]][fin[1]] = this.editedBoard[init[0]][init[1]];

			/* Returning the old JButton to its original colour */
			this.editedBoard[init[0]][init[1]] = this.defaultBoard[init[0]][init[1]];

			/* Need to recreate the JPanel based on the new *, int finX */
			for (int rows = 0; rows < 25; rows++) {
				for (int cols = 0; cols < 24; cols++) {
					/* This *should* correctly re-add the JButtons to the JPanel */
					JButton temp = this.editedBoard[rows][cols];
					temp.setBorder(null);
					newPanel.add(temp);
				}
			}
			returnMe.add(newPanel);
		}
		return returnMe;
	}

	public JPanel moveToRoom(int[] init, Room room){
		System.out.println("Capacity is: " + room.getCapacity());
		int currentcapacity = room.getCapacity()-1;//capacity is incremented in the move logic in GameLogic, so it's already higher here, hence -1
		int[] fin = room.getPlayerFloors().get(currentcapacity);
		System.out.println("Moving to room from " + init[0] + "," +init[1]+" to "+ fin[0] +"," +fin[1]);
		return swapsquares(init, fin);
	}

	public JPanel movetoExit(int[] fin, Room room){
		System.out.println("Current players in room: " + room.playerListInRoom());
		int currentcapacity = room.getCapacity();
		System.out.println("Room: "+room.getName()+" Capacity: " + currentcapacity);
		int[] init = room.getPlayerFloors().get(currentcapacity);
		System.out.println("Moving from room square" + init[0] + "," +init[1]+" to "+ fin[0] +"," +fin[1]);

		return swapsquares(init, fin);
	}

	public JPanel refreshMe() {
		JPanel newPanel = returnEmptyGridLayout();
		JPanel returnMe = returnFinalJPanel();

		/* Need to recreate the JPanel based on the new */
		for (int rows = 0; rows < 25; rows++) {
			for (int cols = 0; cols < 24; cols++) {
				/* This *should* correctly re-add the JButtons to the JPanel */
				JButton temp = this.editedBoard[rows][cols];
				temp.setBorder(null);
				newPanel.add(temp);
			}
		}
		returnMe.add(newPanel);

		return returnMe;
	}

	public void initPlayers() {
		//White
		JButton white = new JButton();
		white.setBorder(null);
		white.setBackground(Color.WHITE);
		white.setOpaque(true);
		white.setBorderPainted(false);
		editedBoard[0][9]=white;
		//Green
		JButton green = new JButton();
		green.setBorder(null);
		green.setBackground(Color.GREEN);
		green.setOpaque(true);
		green.setBorderPainted(false);
		editedBoard[0][14]=green;
		//Peacock
		JButton peacock = new JButton();
		peacock.setBorder(null);
		peacock.setBackground(Color.BLUE);
		peacock.setOpaque(true);
		peacock.setBorderPainted(false);
		editedBoard[6][23]=peacock;
		//Mustard
		JButton mustard = new JButton();
		mustard.setBorder(null);
		mustard.setBackground(Color.YELLOW);
		mustard.setOpaque(true);
		mustard.setBorderPainted(false);
		editedBoard[17][0]=mustard;
		//Plum
		JButton plum = new JButton();
		plum.setBorder(null);
		plum.setBackground(PURPLE);
		plum.setOpaque(true);
		plum.setBorderPainted(false);
		editedBoard[19][23]=plum;
		//Scarlet
		JButton scarlet = new JButton();
		scarlet.setBorder(null);
		scarlet.setBackground(Color.RED);
		scarlet.setOpaque(true);
		scarlet.setBorderPainted(false);
		editedBoard[24][7]=scarlet;
	}

	/**
	 * This is a very messy method that initializes board squares where the weapons are located for visual
	 * representation. It puts those coordinates in an array, then builds an array of weapon JButtons and
	 * puts those JButtons into random rooms.
	 */
	public void initWeapons() {
		weaponArray = new JButton[6];
		/*
		Setting up the coordinates in this way will make it easier to randomize them
		 */
		int[] kitchenWeaponLocation = {4, 2};
		int[] ballroomWeaponLocation = {5, 12};
		int[] conservatoryWeaponLocation = {3, 21};
		int[] diningRoomWeaponLocation = {12, 3};
		int[] billiardRoomWeaponLocation = {10, 21};
		int[] libraryWeaponLocation = {16, 20};
		int[] loungeWeaponLocation = {21, 3};
		int[] hallWeaponLocation = {21, 12};
		int[] studyWeaponLocation = {22, 20};

		int[][] weaponRoomLocations = new int[9][];
			weaponRoomLocations[0] = kitchenWeaponLocation;
			weaponRoomLocations[1] = ballroomWeaponLocation;
			weaponRoomLocations[2] = conservatoryWeaponLocation;
			weaponRoomLocations[3] = diningRoomWeaponLocation;
			weaponRoomLocations[4] = billiardRoomWeaponLocation;
			weaponRoomLocations[5] = libraryWeaponLocation;
			weaponRoomLocations[6] = loungeWeaponLocation;
			weaponRoomLocations[7] = hallWeaponLocation;
			weaponRoomLocations[8] = studyWeaponLocation;

		/*
		Build array of weapon JButtons and place them onto the board (not randomly yet)
		This is messy but gets the job done for now
		TODO: Write a more elegant way to stick the weapons onto the board, and connect it with BoardBuilder
		 */
		//Wrench
		JButton wrench = new JButton();
		wrench.setBorder(null);
		wrench.setBackground(Color.BLACK);
		wrench.setText("W");
		wrench.setForeground(Color.WHITE);
		wrench.setOpaque(true);
		wrench.setBorderPainted(false);
		weaponArray[0] = wrench;
		editedBoard[kitchenWeaponLocation[0]][kitchenWeaponLocation[1]] = weaponArray[0];
		//Rope
		JButton rope = new JButton();
		rope.setBorder(null);
		rope.setBackground(Color.BLACK);
		rope.setText("R");
		rope.setForeground(Color.WHITE);
		rope.setOpaque(true);
		rope.setBorderPainted(false);
		weaponArray[1] = rope;
		editedBoard[ballroomWeaponLocation[0]][ballroomWeaponLocation[1]] = weaponArray[1];
		//Candlestick
		JButton candlestick = new JButton();
		candlestick.setBorder(null);
		candlestick.setBackground(Color.BLACK);
		candlestick.setText("C");
		candlestick.setForeground(Color.WHITE);
		candlestick.setOpaque(true);
		candlestick.setBorderPainted(false);
		weaponArray[2] = candlestick;
		editedBoard[conservatoryWeaponLocation[0]][conservatoryWeaponLocation[1]] = weaponArray[2];
		//Pipe
		JButton pipe = new JButton();
		pipe.setBorder(null);
		pipe.setBackground(Color.BLACK);
		pipe.setText("P");
		pipe.setForeground(Color.WHITE);
		pipe.setOpaque(true);
		pipe.setBorderPainted(false);
		weaponArray[3] = pipe;
		editedBoard[diningRoomWeaponLocation[0]][diningRoomWeaponLocation[1]] = weaponArray[3];
		//Gun - using this instead of revolver so I can represent it with 'G' since Rope is 'R'
		JButton gun = new JButton();
		gun.setBorder(null);
		gun.setBackground(Color.BLACK);
		gun.setText("G");
		gun.setForeground(Color.WHITE);
		gun.setOpaque(true);
		gun.setBorderPainted(false);
		weaponArray[4] = gun;
		editedBoard[billiardRoomWeaponLocation[0]][billiardRoomWeaponLocation[1]] = weaponArray[4];
		//Dagger
		JButton dagger = new JButton();
		dagger.setBorder(null);
		dagger.setBackground(Color.BLACK);
		dagger.setText("D");
		dagger.setForeground(Color.WHITE);
		dagger.setOpaque(true);
		dagger.setBorderPainted(false);
		weaponArray[5] = dagger;
		editedBoard[libraryWeaponLocation[0]][libraryWeaponLocation[1]] = weaponArray[5];
	}

	public void drawForTheFistTime(JPanel p){
		initPlayers();
		initWeapons();
		for (int i=0;i<25;i++){
			for (int j=0;j<24;j++){
				p.add(editedBoard[i][j]);
			}
		}
	}

	/**
	 * returns JPanel that will be used to hold the JPanel of buttons
	 * @return a formatted JPanel for the board image
	 */
	private JPanel returnFinalJPanel() {
        /* Put the first JPanel in this one -- GridBagLayout messes with the spacing to make it look nicer */
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.BLACK);
        return center;
	}
	/**
	 * returnDefaultBoard
	 * @return the default array of JButtons
	 */
	public JButton[][] returnDefultBoard(){
		return this.defaultBoard;
	}
	/**
	 * returnEditedBoard
	 * @return the edited array of JButtons
	 */
	public JButton[][] returnEditedBoard(){
		return this.editedBoard;
	}

	/**
	 * test method that tests with the default map
	 * @param bi a buffered image to load into the class variable
	 */
	public void testMe(BufferedImage bi) {
		JFrame frame = new JFrame("Test BufferedImage");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.bi = bi;

		JPanel p = this.returnEmptyGridLayout();
		p = this.populateGrid(p);

		/* Setting frame size -- Will be removed */
		frame.setSize(WIDTH,HEIGHT);
		frame.setVisible(true);

		JPanel holder = this.returnFinalJPanel();
		holder.add(p);
		frame.add(holder);

	}
	/**
	 * Tester Class
	 * @param args command-line arguments
	 * @throws IOException Exception thrown if unable to load the image
	 */
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
}
