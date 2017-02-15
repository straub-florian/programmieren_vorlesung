package de.dhbwka.java.exercise.classes.wechselspiel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

/**
 * Responsible for visualizing the matchfield.
 * 
 * @author Florian Straub
 */
public class GUI extends JFrame implements MouseListener{

	/* *******************  Attributes  ******************* */
		private static final long serialVersionUID = 203636144384178165L;
	
		private final int SIZE;
		private final int TILE_SIZE;
		private final int BUFFERS = 2;
		
		private final String COLUMNS = "ABCDEFGHIJKLMNOPQ";
		private final Font font = new Font("Arial", Font.PLAIN, 28);
		
		private int EXTRA_WIDTH;
		private int EXTRA_HEIGHT;
		
		private ChangeGame changeGame;
		private BufferStrategy buffer;
		private Graphics2D graphics;
	/* ******************* /Attributes  ******************* */
	
	/**
	 * Sets up a JFrame and initializes all rendering related
	 * attributes/actions.
	 * 
	 * @param changeGame The controller-class
	 */
	public GUI(ChangeGame changeGame){
		this.changeGame = changeGame;
			
		SIZE 		= changeGame.getMatchfield().getSize();
		TILE_SIZE = changeGame.getMatchfield().getTileSize();
		
		// setup the JFrame
		setTitle("Wechselspiel");
		setSize((SIZE)*(TILE_SIZE), (SIZE)*(TILE_SIZE));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addMouseListener(this);
		setVisible(true);
			
			// calculate the window borders to make the field fit in perfectly 
			EXTRA_WIDTH  =  (int) (((SIZE)*(TILE_SIZE)) - getContentPane().getSize().getWidth());
			EXTRA_HEIGHT =  (int) (((SIZE)*(TILE_SIZE)) - getContentPane().getSize().getHeight());
				
			// apply the calculated values
			setSize(EXTRA_WIDTH+(SIZE)*(TILE_SIZE), EXTRA_HEIGHT+(SIZE)*(TILE_SIZE));
			setVisible(true);
			createBufferStrategy(BUFFERS);
		
		// initialize attributes
		buffer = getBufferStrategy();
	}
	
	/**
	 * Handles the raw rendering process
	 */
	public void render() {
		// Get the graphics and draw a white background
		graphics = (Graphics2D) buffer.getDrawGraphics();
		graphics.setColor(Color.WHITE);
		graphics.setFont(font);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		// Make the game draw only at the visible part of the JFrame
		graphics.translate(EXTRA_WIDTH/2, EXTRA_HEIGHT-EXTRA_WIDTH/2);
		
		// Render calls
		renderMatchfield(graphics, changeGame.getMatchfield());
		
		// Show the buffers input
		buffer.show();
		
		// Dispose the graphics-object
		graphics.dispose();
	}
	
	/**
	 * Renders all Tiles and other visual effects
	 * 
	 * @param g
	 *            the graphics object which we use to draw
	 */
	private void renderMatchfield(Graphics2D g, Matchfield matchfield) {
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
					if(matchfield.isValidNeigbour(x,y)){
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
		highlightTile(graphics,changeGame.getMatchfield().getCurrentTile());
		
		// Draw mouse selection
		highlightTile(graphics,getMouseIndex());
		
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
	 * Returns the corresponding color value from an given integer value
	 * 
	 * @param x
	 *            x-tile-index
	 * @param y
	 *            y-tile-index
	 * @return a color
	 */
	private Color getColor(int x, int y) {
		 switch (changeGame.getMatchfield().getColorID(x-1,y-1)) {
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
	 * Invoked when a mouse button has been released on a component.
	 * Here we call the corresponding method in our controller-class.
	 */
	public void mouseReleased(MouseEvent e) {
		changeGame.mouseReleased(e);
	}

	/**
	 * Gets the mouse position as index values and does some error prevention.
	 * 
	 * @return the indices as a point. If it is not a valid mouse position it
	 *         will return null.
	 */
	Point getMouseIndex(){
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
	
	/* ********  Unused implementations ******** */
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
	/* ******** /Unused implementations ******** */
}
