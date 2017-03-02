package de.dhbwka.java.exercise.classes.wechselspiel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Contains the matchfield and maintains it.
 * 
 * @author Florian Straub
 */
public class Matchfield {

	/* ******************* Attributes ******************* */
		private final int MIN_SIZE = 5;
		private final int MAX_SIZE = 16;
	
		private final int MIN_COLOR_AMOUNT = 4;
		private final int MAX_COLOR_AMOUNT = 10;
		
		private final int COLOR_AMOUNT;
		private final int SEQUENCE_BONUS = 10;
	
		private int[][] colors;
		private int size;
		private int score;
		private int foundSequences;
	
		private boolean hasBonus;
		
		private Random random;
		private Point currentTile;
		private Stack<ArrayList<Point>> sequences;
	/* ******************* /Attributes ******************* */

	/**
	 * Constructor of ChangeGame. It initializes all attributes.
	 * 
	 * @param size
	 *            how much tiles the game should vertically/horizontally have
	 *            (capped between {@link #MIN_SIZE} and {@link #MAX_SIZE})
	 * @param colorAmount 
	 * 			  how much colors the matchfield should have
	 * @param tilesize
	 *            how big each tile should be in pixels (minimum is capped at
	 *            {@link #MIN_TILE_SIZE})
	 */
	public Matchfield(int size, int colorAmount) {
		// cap size value
		if (size < MIN_SIZE)
			size = MIN_SIZE;

		if (size > MAX_SIZE)
			size = MAX_SIZE;
		
		if(colorAmount  < MIN_COLOR_AMOUNT)
			colorAmount = MIN_COLOR_AMOUNT;
		
		if(colorAmount  > MAX_COLOR_AMOUNT)
			colorAmount = MAX_COLOR_AMOUNT;

		
		// set the final values
		this.size = size + 1;
		this.COLOR_AMOUNT = colorAmount;

		this.random = new Random();
		this.sequences = new Stack<ArrayList<Point>>();

		// generate a random map
		generateRandomMap(size);
	}


	/**
	 * Resets everything and generates a new matchfield
	 */
	public void reset() {
		score = 0;
		hasBonus = false;
		foundSequences = 0;
		generateRandomMap(size-1);
	}
	
	/**
	 * Clears all sequences and "refills" the matchfield
	 */
	private void updateMatchfield() {
		// clear every sequence
		while(!sequences.isEmpty()){
			ArrayList<Point> points = sequences.peek();
			
			Point start = points.get(0);
			Point stop  = points.get(points.size()-1);
			
			start.translate(-1, -1);
			 stop.translate(-1, -1);
			
			boolean horizontal = start.y == stop.y;
			
			// horizointal sequence
			if(horizontal){
				for(int x = start.x; x <= stop.x; x++){
					//flag black
					colors[x][start.y] = -1;
				}				
			// vertical sequence
			}else{
				for(int y = start.y; y <= stop.y; y++){
					//flag black
					colors[start.x][y] = -1;
				}
			}

			// let all affected tiles "fall" down
			if (horizontal) {
				for (int y = start.y; y > 0; y--) {
					for (int x = start.x; x < start.x + 3; x++) {
						colors[x][y] = getColorID(x + 1, y);
					}
				}
				for (int x = start.x; x < start.x + 3; x++) {
					colors[x][0] = random.nextInt(COLOR_AMOUNT);
				}

			} else {
				for (int y = start.y; y > 0; y--) {
					colors[start.x][y + 2] = getColorID(start.x + 1, y);
				}

				for (int y = 0; y < 3; y++) {
					colors[start.x][y] = random.nextInt(COLOR_AMOUNT);
				}

			}
			
			
			// clear first element of the stack
			sequences.pop();
		}
		
		// look for possible new sequences
		scanMatchfield();
	}

	private void scanMatchfield() {
		do{
			
		}while(sequences.size() > 0);
	}


	/**
	 * Generates a random map with the given size.
	 * 
	 * @param size
	 *            how big the map should be
	 */
	private void generateRandomMap(int size) {
		// Initialize array
		colors = new int[size][size];

		// Fill array with "-1" to make it easier to apply the colors.
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				colors[x][y] = -1;
			}
		}

		// Iterate over array set a color and check if it is valid before
		// applying it to the array
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				// A random color
				int col = random.nextInt(COLOR_AMOUNT);
				// As long as it is not valid find another color
				while (!isValid(x, y, col)) {
					col = random.nextInt(COLOR_AMOUNT);
				}
				// Set the color
				colors[x][y] = col;
			}
		}
	}
	
	/**
	 * Checks if the given tiles color can is valid.
	 * 
	 * @param x
	 *            x-index of the tile
	 * @param y
	 *            y-index of the tile
	 * @param color
	 *            the corresponding value for a color. See ->
	 *            {@link #getColor(int)}
	 * 
	 * @return if the color is valid
	 */
	private boolean isValid(int x, int y, int color) {
		// Every possible side
		boolean left, right, up, down;

		// Check if the neighbour is the same color
		left = x > 0 && colors[x - 1][y] == color;
		right = x < size - 2 && colors[x + 1][y] == color;
		up = y > 0 && colors[x][y - 1] == color;
		down = y < size - 2 && colors[x][y + 1] == color;

		return (!right && !left) && (!up && !down);
	}

	/**
	 * Checks if the tile at the given coordinates is a neighbor of the
	 * {@link #currentTile}
	 * 
	 * @param x
	 *            x-tile-index
	 * @param y
	 *            y-tile-index
	 * @return if it is a valid neighbor
	 */
	public boolean isValidNeigbour(int x, int y) {
		boolean neighbour = false;
		// If it is null, no neighbor needs to be found
		if (currentTile != null) {
			int iX = currentTile.x;
			int iY = currentTile.y;

			// Check if it is either under, above, to the left or to the right
			// of the given tile
			if (x > 0 && x == iX - 1 && y == iY)
				neighbour = true;
			else if (x < size  && x == iX + 1 && y == iY)
				neighbour = true;
			else if (y > 0 && y == iY - 1 && x == iX)
				neighbour = true;
			else if (y < size  && y == iY + 1 && x == iX)
				neighbour = true;

		} else {
			neighbour = true;
		}

		return neighbour;
	}

	/**
	 * Checks if the swap of the two given tiles would be a valid swap.
	 * 
	 * @param tileA
	 * @param tileB
	 * @return if it is swapable
	 */
	public int getAmountOfEquallyColoredTiles(Point tile, Point dest) {
		// count horizontal tiles
		ArrayList<Point> points =      countColor(dest, tile, 0);
					     points.addAll(countColor(dest, tile, 1));

		// if no sequence bigger than 2 was found check the vertical tiles (+1
		// because the tile itself will not be counted as it was not swapped)
		if(points.size()+1 < 3){
			points.clear();
			points =      countColor(dest, tile, 2);
			points.addAll(countColor(dest, tile, 3));
		}

		// store sequences in a stack if it was a valid sequence
		if(points.size()+1 >= 3){			
			sequences.push(points);		
			foundSequences++;
			
			// grant time bonus
			if(foundSequences >= SEQUENCE_BONUS){
				hasBonus = true;
			}
		}
		
		return points.size()+1;
	}
	
	private ArrayList<Point> countColor(Point dest, Point toSwap, int direction){
		int ctr = 1;
		ArrayList<Point> points = new ArrayList<Point>();
		
		// Check if inside bounds, if the current position is not the toSwap
		// tile and if the colorID is the one we are looking for
		switch (direction) {
			case 0:
				while(dest.x - ctr > 0 && dest.x - ctr != toSwap.x && getColorID(dest.x - ctr, dest.y) == getColorID(toSwap)){
					points.add(new Point(dest.x-ctr, dest.y));
					ctr++;
				}
				break;
			case 1:
				while(dest.x + ctr < size && dest.x + ctr != toSwap.x && getColorID(dest.x + ctr, dest.y) == getColorID(toSwap)){
					points.add(new Point(dest.x+ctr, dest.y));
					ctr++;
				}
				break;
			case 2:
				while(dest.y - ctr > 0  && dest.y - ctr != toSwap.y && getColorID(dest.x, dest.y - ctr) == getColorID(toSwap)){
					points.add(new Point(dest.x, dest.y-ctr));
					ctr++;
				}
				break;
			case 3:
				while(dest.y + ctr < size && dest.y + ctr != toSwap.y && getColorID(dest.x, dest.y + ctr) == getColorID(toSwap)){
					points.add(new Point(dest.x, dest.y+ctr));
					ctr++;
				}
				break;
		}	
		
		return points;
	}
	
	/**
	 * Swaps the tiles, if no tile could be swapped it deselects the
	 * currentTile. Also selects a tiel if no tile is currently selected.
	 * 
	 * @param selectedTile
	 */
	public void swapTiles(Point selectedTile) {
		// If a current tile is set, do the swapping
		if (currentTile != null) {
			// Get the mouse-selected tile
			if (selectedTile != null) {
				// Check if the tiles are valid neighbors
				if (isValidNeigbour(selectedTile.x, selectedTile.y)) {
					int iX = selectedTile.x - 1;
					int iY = selectedTile.y - 1;
					int x = currentTile.x - 1;
					int y = currentTile.y - 1;

					int tileA = getAmountOfEquallyColoredTiles(currentTile, selectedTile);
					int tileB = getAmountOfEquallyColoredTiles(selectedTile, currentTile);
					
					// Check if they can be swapped
					if (tileA >= 3 || tileB >= 3) {
						if(tileA >= 3){
							score(tileA);
						}else{
							score(tileB);
						}
						int temp = colors[iX][iY];
						colors[iX][iY] = colors[x][y];
						colors[x][y] = temp;
										
						
						// Update the matchfield
						updateMatchfield();
					}
				}
				// Highlight no tile anymore (deselect the current tile)
				currentTile = null;
			}
			// Otherwise set the current tile
		} else {
			currentTile = selectedTile;
		}
	}

	/**
	 * Calculates the score
	 * @param tiles the amount of equally colored tiles
	 */
	private void score(int tiles) {
		score += tiles * 10;
	}


	/**
	 * @return the score of the current player
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param x x-tile-index
	 * @param y y-tile-index
	 * @return the corresponding color value
	 */
	public int getColorID(int x, int y) {
		return colors[x-1][y-1];
	}
	
	/**
	 * @param tile the indices packed inside a point
	 * @return the corresponding color value
	 */
	public int getColorID(Point tile) {
		return colors[tile.x-1][tile.y-1];
	}
	
	/**
	 * @return the size of the matchfield
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * @return how much sequences the player found by hisself
	 */
	public int getFoundSequences() {
		return foundSequences;
	}	
	
	/**
	 * Resets the bonus if the extra time was granted
	 * 
	 * @return if the player achieved a bonus
	 */
	public boolean hasBonus(){
		if(hasBonus){
			hasBonus = false;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @return the indices of the currently selected tile as a point
	 */
	public Point getCurrentTile() {
		return currentTile;
	}	
}