package de.dhbwka.java.exercise.classes.wechselspiel;

import java.awt.Point;
import java.util.Random;

/**
 * Contains the matchfield and maintains it.
 * 
 * @author Florian Straub
 */
public class Matchfield {

	/* ******************* Attributes ******************* */
		private final int MIN_SIZE = 6;
		private final int MAX_SIZE = 16;
		private final int MIN_TILE_SIZE = 20;
	
		private final int COLOR_AMOUNT = 7;
	
		private int[][] colors;
		private int size;
		private int tileSize;
		private int score;
	
		private Random random;
		private Point currentTile;
	/* ******************* /Attributes ******************* */

	/**
	 * Constructor of ChangeGame. It initializes all attributes.
	 * 
	 * @param size
	 *            how much tiles the game should vertically/horizontally have
	 *            (capped between {@link #MIN_SIZE} and {@link #MAX_SIZE})
	 * @param tilesize
	 *            how big each tile should be in pixels (minimum is capped at
	 *            {@link #MIN_TILE_SIZE})
	 */
	public Matchfield(int size, int tilesize) {
		// cap size and tilesize value
		if (tilesize < MIN_TILE_SIZE)
			tilesize = MIN_TILE_SIZE;

		if (size < MIN_SIZE)
			size = MIN_SIZE;

		if (size > MAX_SIZE)
			size = MAX_SIZE;

		// set the final values
		this.size = size + 1;
		this.tileSize = tilesize;

		this.random = new Random();

		// generate a random map
		generateRandomMap(size);
	}


	/**
	 * Resets everything and generates a new matchfield
	 */
	public void reset() {
		score = 0;
		generateRandomMap(size-1);
	}
	
	/**
	 * Update all logic when a swap was performed
	 */
	private void updateMatchfield() {
		//Look for color sequences
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				
			}
		}
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
	public boolean isSwapable(Point tileA, Point tileB) {
		int colorCount = 0;
		int colorB = colors[tileA.x][tileA.y];
		int colorA = colors[tileB.x][tileB.y];

		colorCount = countColor(tileA, colorA);
		colorCount += countColor(tileB, colorB);
		
		return colorCount >= 3;
	}
	
	private int countColor(Point tile, int colorID){
		int colorCount = 0;
		
		int left  = tile.x - 1;
		int right = tile.x + 1;
		int up    = tile.y - 1;
		int down  = tile.y - 1;
		while(left  > 0 && colors[left   ][tile.y] == colorID){
			colorCount++;
			left--;
		}
		while(right < size && colors[right  ][tile.y] == colorID){
			colorCount++;
			right++;
		}
		while(up    > 0 && colors[tile.x][up     ] == colorID){
			colorCount++;
			up--;
		}
		while(down  < size && colors[tile.x][down   ] == colorID){
			colorCount++;
			down++;
		}
		
		return colorCount;
	}
	
	/**
	 * Swaps the tiles, if no tile could be swapped it deselects the currentTile
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

					// Check if they can be swapped
					if (isSwapable(currentTile, selectedTile)) {
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
		return colors[x][y];
	}
	
	/**
	 * @return the size of the matchfield
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the size of the tiles in pixels
	 */
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * @return the indices of the currently selected tile as a point
	 */
	public Point getCurrentTile() {
		return currentTile;
	}
}