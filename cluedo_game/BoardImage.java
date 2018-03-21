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
	private final int WIDTH  = 552; //Width of the board
	private final int HEIGHT = 575; //Height of the board
	private final int step = 23;//Incrementer for for loop to correctly move to next square,size of each square
	private final Color PURPLE = new Color(75, 0, 130); // Poor Professor Plum should get his own color, too

	BufferedImage bi;//Object for the board itself

	private JButton[] weaponArray;
	private ArrayList<Integer[]> weaponRoomLocations;

	// DELETE
	// private JPanel imagePanel;
	private JButton[][] defaultBoard = new JButton[25][24];
	private JButton[][] editedBoard = new JButton[25][24];

	//This is super janky and will fix later
	static int[] whiteindex = {0,9};
	static int[] greenindex = {0,14};
	static int[] peacockindex = {6,23};
	static int[] mustardindex = {17,0};
	static int[] plumindex = {19,23};
	static int[] scarletindex = {24,7};

<<<<<<< HEAD
	//DELETE
	// int myVar = 24;
=======
	int myVar = 2;
>>>>>>> 3af94e69fc9af44bd048934478c2ff0c7540895e


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


	public JPanel returnPanel() {//Handles image fetch as well
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
		return returnPanel(bi);//calls overloaded version of function
	}

	// DELETE
	// public JPanel getImagePanel() {
	// 	return imagePanel;
	// }

	/**
	 * @return JPanel with an empty grid layout
	 */
	private JPanel returnEmptyGridLayout() {
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

                button.setActionCommand(""+xIndex+","+yIndex);
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

	/**
	 * "moves" the players on the screen by swapping the JButtons on the screen
	 * @param initX = the initial X starting position
	 * @param initY = the initial Y starting position
	 * @param finX = the final X starting position
	 * @param finY = the final Y starting position
	 * @return the JPanel that will represent the new board
	 */

	//DELETE?
	public JPanel move(int initY, int initX, int finY, int finX) {
		/* Creating new JPanel -- set = to an empty layout */

		System.out.println("Init: ["+initY+","+initX+"] Fin: ["+finY + "," + finX + "]");
		JPanel newPanel = returnEmptyGridLayout();
		JPanel returnMe = returnFinalJPanel();
		/* Assigning the colour of the new JButton */
		this.editedBoard[finY][finX] = this.editedBoard[initY][initX];

		/* Returning the old JButton to its original colour */
		this.editedBoard[initY][initX] = this.defaultBoard[initY][initX];

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

	public JPanel movetoExit(String room, int exitnumber, String player){
		switch(room){
			case "Kitchen":
				move("kexit1", player);
				break;
			case "BallRoom":
				if (exitnumber==1) this.move("br1", player);
				if (exitnumber==2) this.move("br2", player);
				break;
			case "Conservatory":
				move("cons1", player);
				break;
			case "DiningRoom":
				move("dinexit1",player);
				break;
			case "BilliardRoom":
				move("bilexit1", player);
				break;
			case "Library":
				move("libexit1", player);
				break;
			case "Lounge":
				move("louexit1", player);
				break;
			case "Hall":
				move("hallexit1", player);
				break;
			case "Study":
				move("study1", player);
				break;
		}
		return null;
	}

	//move by passing token instead of player name
	//public JPanel move()

	public JPanel move(String direction, String player){

		System.out.println("Moving " + player + " " + direction);

		JPanel newPanel = returnEmptyGridLayout();
		JPanel returnMe = returnFinalJPanel();
		int startx=0, starty=0, endx=0, endy=0;
		int[] modifier = {0,0};
		Boolean simpledirection = true;//This tells the next switch to not overwrite the endx endy;

		switch (direction) {
			case "up":
				modifier[0]=-1;
				modifier[1]=0;
				break;
			case "down":
				modifier[0]=1;
				modifier[1]=0;
				break;
			case "left":
				modifier[0]=0;
				modifier[1]=-1;
				break;
			case "right":
				modifier[0]=0;
				modifier[1]=1;
				break;
			case "Kitchen":
			case "Ballroom":
			case "Conservatory":
			case "DiningRoom":
			case "BilliardRoom":
			case "Library":
			case "Lounge":
			case "Hall":
			case "Study":
				int[] endsquares = calculateEndSquareRoom(direction);
				endx=endsquares[0];
				endy=endsquares[1];
				simpledirection=false;
				break;
			//Exits
			case "kexit1":
				endx=7;
				endy=4;
				simpledirection=false;
				break;
			case "br1":
				endx=5;
				endy=7;
				simpledirection=false;
				break;
			case "br2":
				endx=8;
				endy=9;
				simpledirection=false;
				break;
			case "cons1":
				endx=5;
				endy=18;
				simpledirection=false;
				break;
			case "dinexit1":
				endx=12;
				endy=8;
				simpledirection=false;
				break;
			case "libexit1":
				endx=16;
				endy=16;
				simpledirection=false;
				break;
			case "louexit1":
				endx=18;
				endy=6;
				simpledirection=false;
				break;
			case "hallexit1":
				endx=17;
				endy=12;
				simpledirection=false;
				break;

			default:
				System.out.println("ERROR INPUT "+direction+"NOT FOUND");
				break;
		}


		switch (player) {
			case "White":
				startx=whiteindex[0];
				starty=whiteindex[1];
				if (simpledirection){
					endx=whiteindex[0]+modifier[0];
					endy=whiteindex[1]+modifier[1];
					whiteindex[0]+=modifier[0];
					whiteindex[1]+=modifier[1];
				}
				break;
			case "Green":
				startx=greenindex[0];
				starty=greenindex[1];
				if (simpledirection){
					endx=greenindex[0]+modifier[0];
					endy=greenindex[1]+modifier[1];
					greenindex[0]+=modifier[0];
					greenindex[1]+=modifier[1];
				}
				break;
			case "Peacock":
				startx=peacockindex[0];
				starty=peacockindex[1];
				if (simpledirection){
					endx=peacockindex[0]+modifier[0];
					endy=peacockindex[1]+modifier[1];
					peacockindex[0]+=modifier[0];
					peacockindex[1]+=modifier[1];
				}
				break;
			case "Mustard":
				startx=mustardindex[0];
				starty=mustardindex[1];
				if (simpledirection){
					endx=mustardindex[0]+modifier[0];
					endy=mustardindex[1]+modifier[1];
					mustardindex[0]+=modifier[0];
					mustardindex[1]+=modifier[1];
				}
				break;
			case "Plum":
				startx=plumindex[0];
				starty=plumindex[1];
				if (simpledirection){
					endx=plumindex[0]+modifier[0];
					endy=plumindex[1]+modifier[1];
					plumindex[0]+=modifier[0];
					plumindex[1]+=modifier[1];
				}
				break;
			case "Scarlet":
				startx=scarletindex[0];
				starty=scarletindex[1];
				if (simpledirection){
					endx=scarletindex[0]+modifier[0];
					endy=scarletindex[1]+modifier[1];
					scarletindex[0]+=modifier[0];
					scarletindex[1]+=modifier[1];
				}
				break;
			default:
				System.out.println("ERROR CANNT FIND PLAYER");
				break;
		}



		System.out.println("Calculation is [" +startx + "," + starty + "] + [" + modifier[0] + "," + modifier[1] + "] = [" + endx + "," + endy + "]");

		/* Assigning the colour of the new JButton */

		this.editedBoard[endx][endy] = this.editedBoard[startx][starty];

		/* Returning the old JButton to its original colour */
		this.editedBoard[startx][starty] = this.defaultBoard[startx][starty];

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

	//Probably  will delete
	public int[] calculateModifier(int[] destinationSquare, int[] playerindex){
		BoardBuilder roomsReference = new BoardBuilder(new Tokens());//I know this sucks but it works
		int[] modifier = {playerindex[0]-destinationSquare[0], playerindex[1]-playerindex[1]};
		return modifier;
	}

	public int[] returnExitSquares(String exit){
		return null;
	}


	//Represents the number of players per room, in order
	//Kitchen, ballroom, conservator, diningroom, billiardroom, library, lounge, hall, and study
	static int[] playersinRooms = {0,0,0,0,0,0,0,0,0};
	public int[] calculateEndSquareRoom(String room){
		int[] destination = {0,0};
		 if (room.equals("Kitchen")){
			switch (playersinRooms[0]){
				case 0:
					destination[0] = 3;
					destination[1] = 2;
					break;
				case 1:
					destination[0] = 3;
					destination[1] = 1;
					break;
				case 2:
					destination[0] = 3;
					destination[1] = 3;
					break;
				case 3:
					destination[0] = 2;
					destination[1] = 2;
					break;
				case 4:
					destination[0] = 2;
					destination[1] = 1;
					break;
				case 5:
					destination[0] = 2;
					destination[1] = 3;
					break;
				default:
					System.out.println("ERROR");
					break;
			}
			playersinRooms[0]++;
			}
			if (room.equals("Ballroom")){
			   switch (playersinRooms[1]){
				   case 0:
					   destination[0] = 4;
					   destination[1] = 10;
					   break;
				   case 1:
					   destination[0] = 4;
					   destination[1] = 11;
					   break;
				   case 2:
					   destination[0] = 4;
					   destination[1] = 12;
					   break;
				   case 3:
					   destination[0] = 3;
					   destination[1] = 10;
					   break;
				   case 4:
					   destination[0] = 3;
					   destination[1] = 11;
					   break;
				   case 5:
					   destination[0] = 3;
					   destination[1] = 12;
					   break;
				   default:
					   System.out.println("ERROR");
					   break;
			   }
			   playersinRooms[1]++;
		   }
	   if (room.equals("Conservatory")){
		  switch (playersinRooms[2]){
			  case 0:
				  destination[0] = 2;
				  destination[1] = 21;
				  break;
			  case 1:
				  destination[0] = 2;
				  destination[1] = 20;
				  break;
			  case 2:
				  destination[0] = 2;
				  destination[1] = 22;
				  break;
			  case 3:
				  destination[0] = 1;
				  destination[1] = 21;
				  break;
			  case 4:
				  destination[0] = 1;
				  destination[1] = 20;
				  break;
			  case 5:
				  destination[0] = 1;
				  destination[1] = 22;
				  break;
			  default:
				  System.out.println("ERROR");
				  break;
		  }
		  playersinRooms[2]++;
	  }
	  if (room.equals("DiningRoom")){
		 switch (playersinRooms[3]){
			 case 0:
				 destination[0] = 11;
				 destination[1] = 3;
				 break;
			 case 1:
				 destination[0] = 11;
				 destination[1] = 2;
				 break;
			 case 2:
				 destination[0] = 11;
				 destination[1] = 4;
				 break;
			 case 3:
				 destination[0] = 11;
				 destination[1] = 1;
				 break;
			 case 4:
				 destination[0] = 11;
				 destination[1] = 5;
				 break;
			 case 5:
				 destination[0] = 11;
				 destination[1] = 0;
				 break;
			 default:
				 System.out.println("ERROR");
				 break;
		 }
		 playersinRooms[3]++;
	 }

	 if (room.equals("BilliardRoom")){
		switch (playersinRooms[4]){
			case 0:
				destination[0] = 4;
				destination[1] = 10;
				break;
			case 1:
				destination[0] = 4;
				destination[1] = 11;
				break;
			case 2:
				destination[0] = 4;
				destination[1] = 12;
				break;
			case 3:
				destination[0] = 3;
				destination[1] = 10;
				break;
			case 4:
				destination[0] = 3;
				destination[1] = 11;
				break;
			case 5:
				destination[0] = 3;
				destination[1] = 12;
				break;
			default:
				System.out.println("ERROR");
				break;
		}
		playersinRooms[4]++;
	}
			if (room.equals("Library")){
				switch (playersinRooms[5]){
					case 0:
						destination[0] = 15;
						destination[1] = 20;
						break;
					case 1:
						destination[0] = 15;
						destination[1] = 19;
						break;
					case 2:
						destination[0] = 15;
						destination[1] = 21;
						break;
					case 3:
						destination[0] = 16;
						destination[1] = 19;
						break;
					case 4:
						destination[0] = 16;
						destination[1] = 21;
						break;
					case 5:
						destination[0] = 17;
						destination[1] = 20;
						break;
					default:
						System.out.println("ERROR");
						break;
				}
				playersinRooms[5]++;
			}
			if (room.equals("Lounge")){
				switch (playersinRooms[6]){
					case 0:
						destination[0] = 21;
						destination[1] = 3;
						break;
					case 1:
						destination[0] = 21;
						destination[1] = 2;
						break;
					case 2:
						destination[0] = 21;
						destination[1] = 4;
						break;
					case 3:
						destination[0] = 22;
						destination[1] = 3;
						break;
					case 4:
						destination[0] = 22;
						destination[1] = 2;
						break;
					case 5:
						destination[0] = 22;
						destination[1] = 4;
						break;
					default:
						System.out.println("ERROR");
						break;
				}
				playersinRooms[6]++;
			}
			if (room.equals("Hall")){
				switch (playersinRooms[4]){
					case 0:
						destination[0] = 21;
						destination[1] = 11;
						break;
					case 1:
						destination[0] = 21;
						destination[1] = 12;
						break;
					case 2:
						destination[0] = 22;
						destination[1] = 11;
						break;
					case 3:
						destination[0] = 22;
						destination[1] = 12;
						break;
					case 4:
						destination[0] = 20;
						destination[1] = 11;
						break;
					case 5:
						destination[0] = 20;
						destination[1] = 12;
						break;
					default:
						System.out.println("ERROR");
						break;
				}
				playersinRooms[7]++;
			}
			if (room.equals("Study")){
				switch (playersinRooms[4]){
					case 0:
						destination[0] = 22;
						destination[1] = 20;
						break;
					case 1:
						destination[0] = 22;
						destination[1] = 19;
						break;
					case 2:
						destination[0] = 22;
						destination[1] = 21;
						break;
					case 3:
						destination[0] = 23;
						destination[1] = 20;
						break;
					case 4:
						destination[0] = 23;
						destination[1] = 19;
						break;
					case 5:
						destination[0] = 23;
						destination[1] = 21;
						break;
					default:
						System.out.println("ERROR");
						break;
				}
				playersinRooms[8]++;
			}

	 	return destination;
	}

	public JPanel move(int init[], int fin[]) {
		/* Creating new JPanel -- set = to an empty layout */
		JPanel newPanel = returnEmptyGridLayout();
		JPanel returnMe = returnFinalJPanel();
		/* Assigning the colour of the new JButton */
		this.editedBoard[fin[1]][fin[0]] = this.editedBoard[init[1]][init[0]];

		/* Returning the old JButton to its original colour */
		this.editedBoard[init[1]][init[0]] = this.defaultBoard[init[1]][init[0]];

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
