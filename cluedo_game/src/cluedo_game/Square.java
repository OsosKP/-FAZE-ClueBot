package cluedo_game;

public interface Square {
		/* Every square needs to have an x,y position stored */
		final int xPosition = 0;
		final int yPosition = 0;
		
		/* Every Square is going to need adj pointers to the squares next to it  */
		GameSquare[][] adjacentSquares  = new GameSquare[3][3];
		/* if we think about it, the square is going to look like the following
		 *  [  ][  ][  ]
		 *  [  ][x ][  ]
		 *  [  ][  ][  ]
		 *  where x is the square we are looking at, for example (this may not be the case if we are looking at a corner piece or someathing
		 *  */
}
