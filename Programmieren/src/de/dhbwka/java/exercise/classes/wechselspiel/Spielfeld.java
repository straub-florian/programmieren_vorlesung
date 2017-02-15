package de.dhbwka.java.exercise.classes.wechselspiel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

/**
 * Es gibt ein Spielfeld mit {@link #SIZE}x{@link #SIZE} Feldern. Jedes Feld hat
 * eine aus insgesamt {@link #COLOR_AMOUNT} Farben. Am Anfang wird das Spielfeld
 * zufällig so mit Farben initialisiert, dass an keiner Stelle 3 neben - oder
 * übereinander liegende Felder dieselbe Farbe haben (Diagonalen spielen keine
 * Rolle)...
 * 
 * @author Florian Straub
 * @see <a href=
 *      "https://www.iai.kit.edu/~javavorlesung/dhbw/2016-17/09_Klassen_Aufgaben-Sternchen.pdf">
 *      Javavorlesung, Klassen - Sternchenaufgabe</a>
 */
public class Spielfeld extends JFrame implements Runnable, MouseListener {

	/* ******************* Attributes ******************* */
		private static final long serialVersionUID = 203636144384178165L;
	
		private final int MIN_SIZE 	    = 6;
		private final int MAX_SIZE 	    = 16;
		private final int MIN_TILE_SIZE = 20;
		private final int SIZE;
		private final int TILE_SIZE;
		private final int BUFFERS = 2;
		private final int TARGET_FPS = 60;
		private final int EXTRA_WIDTH;
		private final int EXTRA_HEIGHT;
		private final int COLOR_AMOUNT = 7;
		private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
		private final String COLUMNS = "ABCDEFGHIJKLMNOPQ";
		private final Font font = new Font("Arial", Font.PLAIN, 28);
	
		private int[][] colors;
		private int score;
		private boolean isRunning;
	
		private BufferStrategy buffer;
		private Graphics2D graphics;
		private Thread gameThread;
		private Random random;
		private Point currentTile;
	/* ******************* /Attributes ******************* */

	/**
	 * Constructor of Wechselspiel. It initializes all attributes and sets up
	 * the JFrame.
	 * 
	 * @param size
	 *            how much tiles the game should vertically/horizontally have
	 *            (capped between {@link #MIN_SIZE} and {@link #MAX_SIZE})
	 * @param tilesize
	 *            how big each tile should be in pixels (minimum is capped at
	 *            {@link #MIN_TILE_SIZE})
	 */
	public Spielfeld(int size, int tilesize) {
		// cap size and tilesize value
		if(tilesize < MIN_TILE_SIZE)
			tilesize = MIN_TILE_SIZE;
		
		if(size < MIN_SIZE)
			size = MIN_SIZE;
		
		if(size > MAX_SIZE)
			size = MAX_SIZE;
		
		// set the final values
		this.SIZE = size+1;
		this.TILE_SIZE = tilesize;
		
		// setup the JFrame
		setTitle("Wechselspiel");
		setSize((SIZE)*(TILE_SIZE), (SIZE)*(TILE_SIZE));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
			
			// calculate the window borders to make the field fit in perfectly 
			EXTRA_WIDTH  =  (int) (((SIZE)*(TILE_SIZE)) - getContentPane().getSize().getWidth());
			EXTRA_HEIGHT =  (int) (((SIZE)*(TILE_SIZE)) - getContentPane().getSize().getHeight());
				
			// apply the calculated values
			setSize(EXTRA_WIDTH+(SIZE)*(TILE_SIZE), EXTRA_HEIGHT+(SIZE)*(TILE_SIZE));
			setVisible(true);
			addMouseListener(this);
			createBufferStrategy(BUFFERS);
		
		// initialize attributes
		random = new Random();
		buffer = getBufferStrategy();
		gameThread = new Thread(this, "GameThread-Wechselspiel");
		
		// generate a random map
		generateRandomMap(size);
	}

	/**
	 * Starts the game-thread.
	 */
	public void startGame() {
		isRunning = true;
		gameThread.start();
	}
	
	/**
	 * Stops the game-thread.
	 */
	public void stopGame() {
		// only stop the game if it is running
		if(isRunning){			
			isRunning = false;
		}
	}

	/**
	 * Runs once when the game is started. Contains the game loop which tries to
	 * keep the game at a constant {@link #TARGET_FPS}.
	 */
	public void run() {
		// Get initial time value
		long lastLoopTime = System.nanoTime();

		// As long as the game should run
		while (isRunning) {
			// Get current time value
			long now = System.nanoTime();
			
			// Calculate the time that passed between the last and this iteration.
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / 100_000_000.0d;

			// Update with the given timestep
			update(delta);

			// Process all rendering
				// Get the graphics and draw a white background
				graphics = (Graphics2D) buffer.getDrawGraphics();
				graphics.setColor(Color.WHITE);
				graphics.setFont(font);
				graphics.fillRect(0, 0, getWidth(), getHeight());
				
				// Make the game draw only at the visible part of the JFrame
				graphics.translate(EXTRA_WIDTH/2, EXTRA_HEIGHT-EXTRA_WIDTH/2);
				
				// Render calls
				render(graphics);
				
				// Show the buffers input
				buffer.show();
				
				// Dispose the graphics-object
				graphics.dispose();
				
				// Sync with monitors Hz-rate
				Toolkit.getDefaultToolkit().sync();

			// Make the thread sleep the calculated value, so that the TARGET_FPS value can be approached
			try {
				long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
				Thread.sleep(timeout < 0 ? 1000/TARGET_FPS : timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// If the game is no longer running --> EXIT
		System.exit(0);
	}

	/**
	 * Update all logic with the given timestep
	 * 
	 * @param delta
	 *            the timestap of the game
	 */
	private void update(double delta) {
		
	}

	/**
	 * Renders all Tiles and other visual effects
	 * 
	 * @param g
	 *            the graphics object which we use to draw
	 */
	private void render(Graphics2D g) {
		// Draw the tilemap
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				// Draw the numbers of the rows
				if(x == 0){
					String text = String.valueOf(y);
					Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
					g.drawString(text, (int) (x*TILE_SIZE+TILE_SIZE/2-r.getWidth()/2), (int) ((y)*TILE_SIZE+TILE_SIZE/2+r.getHeight()/3));
					
				// Draw the letters of the columns
				}else if(y == 0){
					String text = String.valueOf(COLUMNS.charAt(x-1));
					Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
					g.drawString(text, (int) (x*TILE_SIZE+TILE_SIZE/2-r.getWidth()/2), (int) (y*TILE_SIZE+TILE_SIZE/2+r.getHeight()/3));
				// Draw all the colored tiles
				}else{
					// If the selection is not null, highlight only the neighbors
					if(isValidNeigbour(x,y)){
						g.setColor(getColor(x,y));
					}else{
						g.setColor((getColor(x,y)).darker().darker().darker());
					}
					g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
				
				// Draw the black frame of every tile 
				g.setColor(Color.BLACK);
				g.drawRect(x*TILE_SIZE,y*TILE_SIZE, TILE_SIZE, TILE_SIZE);				
			}
		}
		
		// Draw current selection
		highlightTile(graphics,currentTile);
		
		// Draw mouse selection
		highlightTile(graphics,getMouseIndex());
		
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
		
		// Iterate over array set a color and check if it is valid before applying it to the array
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				// A random color
				int col = random.nextInt(COLOR_AMOUNT);
				// As long as it is not valid find another color
				while(!isValid(x, y, col)){
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
		left  = x > 0      && colors[x-1][y  ] == color;
		right = x < SIZE-2 && colors[x+1][y  ] == color;
		up    = y > 0      && colors[x  ][y-1] == color;
		down  = y < SIZE-2 && colors[x  ][y+1] == color;
		
		return (!right && !left) && (!up && !down);
	}
	
	/**
	 * Highlight the tile at the given point
	 * 
	 * @param g
	 * @param p
	 */
	private void highlightTile(Graphics2D g, Point p){
		// if the tile is not null
		if(p != null){			
			// draw a darker tile and a brighter dot in the middle at the given tile position
			int radius = TILE_SIZE/4;
			g.setColor((getColor(p.x,p.y)).darker());
			g.fillRect(p.x*TILE_SIZE,p.y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
			g.setColor((getColor(p.x,p.y)).brighter());
			g.fillOval(p.x*TILE_SIZE+TILE_SIZE/2-radius/2, p.y*TILE_SIZE+TILE_SIZE/2-radius/2, radius,radius);
		}
	}

	/**
	 * Convert a x position into a x-tile-index
	 * 
	 * @param pos
	 *            the position of the tile
	 * 
	 * @return the x-tile-index
	 */
	private int toX(double pos){
		return (((int)pos-EXTRA_WIDTH/2)/TILE_SIZE)*TILE_SIZE;
	}
	
	/**
	 * Convert a y position into a y-tile-index
	 * 
	 * @param pos
	 *            the position of the tile
	 * 
	 * @return the y-tile-index
	 */
	private int toY(double pos){
		return (((int)pos-(EXTRA_HEIGHT-EXTRA_HEIGHT/4+1))/TILE_SIZE)*TILE_SIZE;
	}
	
	/**
	 * Returns the corresponding color value from an given integer value
	 * 
	 * @param x
	 *            x-tile-index
	 * @param y
	 *            y-tile-index
	 * @return a color
	 */
	private Color getColor(int x, int y) {
		 switch (colors[x-1][y-1]) {
			case 0:		return Color.RED;
			case 1:		return Color.GREEN;
			case 2:		return Color.BLUE;
			case 3:		return Color.ORANGE;
			case 4:		return Color.YELLOW;
			case 5:		return new Color(153,51,102); // strange red...
			case 6:		return Color.WHITE;
			default:	return Color.BLACK;
		}
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
	private boolean isValidNeigbour(int x, int y){
		boolean neighbour = false;
		// If it is null, no neighbor needs to be found
		if(currentTile != null){
			int iX = currentTile.x;
			int iY = currentTile.y;
			
			// Check if it is either under, above, to the left or to the right of the given tile
			     if(x > 0        && x == iX -1 && y == iY)  neighbour = true;
			else if(x < SIZE - 2 && x == iX +1 && y == iY)  neighbour = true;
			else if(y > 0		 && y == iY -1 && x == iX)  neighbour = true;
			else if(y < SIZE - 2 && y == iY +1 && x == iX)  neighbour = true;
			
		}else{
			neighbour = true;
		}
		
		return neighbour;
	}
	
	/**
	 * Gets the mouse position as index values and does some error prevention.
	 * 
	 * @return the indices as a point. If it is not a valid mouse position it
	 *         will return null.
	 */
	private Point getMouseIndex(){
		// Check if the mouse is in the JFrame
		if(getMousePosition()!=null){
			// Some conversion and error prevention
			java.awt.Point p = getMousePosition();
			int x = toX(p.getX());
			int y = toY(p.getY());
			
			int indexX = (x+1)/(TILE_SIZE-1);
			int indexY = (y+1)/(TILE_SIZE-1);

			// Check if it is a valid mouse position
			if(indexX > 0 && indexX < SIZE && indexY > 0 && indexY < SIZE){						
				return new Point(indexX, indexY);
			}else{
				return null;
			}	
		}else{
			return null;
		}
	}

	/**
	 * Checks if the swap of the two given tiles would be a valid swap.
	 * 
	 * @param tileA
	 * @param tileB
	 * @return if it is swapable
	 */
	private boolean isSwapable(Point tileA, Point tileB) {
//		int colorCount = 0;
//		int colorA = colors[tileA.x][tileA.y];
//		int colorB = colors[tileB.x][tileB.y];
		
		
		
		return true;
	}
	
	/**
	 * Invoked when a mouse button has been released on a component.
	 * Here we set the current tile and do the swapping.
	 */
	public void mouseReleased(MouseEvent e) {
		// If a current tile is set, do the swapping
		if (currentTile != null) {
			// Get the mouse-selected tile
			Point p = getMouseIndex();
			if (p != null) {
				// Check if the tiles are valid neighbors
				if (isValidNeigbour(p.x, p.y)) {
					int iX = p.x - 1;
					int iY = p.y - 1;
					int x = currentTile.x - 1;
					int y = currentTile.y - 1;
					
					// Check if they can be swapped
					if (isSwapable(currentTile,p)) {
						int temp = colors[iX][iY];
						colors[iX][iY] = colors[x][y];
						colors[x][y] = temp;
					}
				}
				// Highlight no tile anymore (deselect the current tile)
				currentTile = null;
			}
		// Otherwise set the current tile
		} else {
			currentTile = getMouseIndex();
		}
	}
	
	/**
	 * @return the score of the current player
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * @return if the game is running
	 */
	public boolean isRunning(){
		return isRunning;
	}
	
	/* ********  Unused implementations ******** */
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
	/* ******** /Unused implementations ******** */
}