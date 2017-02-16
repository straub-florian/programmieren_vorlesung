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
		private final int MIN_TILE_SIZE = 28;
		private final int MAX_TILE_SIZE = 55;
		
		private final String COLUMNS = "ABCDEFGHIJKLMNOPQ";
		private final Font font 	 = new Font("Arial", Font.PLAIN, 28);
		private final Font clockFont = new Font("Arial", Font.ITALIC, 16);
		
		private int extraWidth;
		private int extraHeight;
		private int infoPanelWidth;
		
		private Button[] buttons;
		private Graphics2D graphics;
		private BufferStrategy buffer;
		private ColorChangerGame colorChangerGame;
	/* ******************* /Attributes  ******************* */
	
	/**
	 * Sets up a JFrame and initializes all rendering related
	 * attributes/actions.
	 * 
	 * @param colorChangerGame The controller-class
	 */
	public GUI(ColorChangerGame colorChangerGame, int tilesize){
		this.colorChangerGame = colorChangerGame;
			
		// Cap tilesize value
		if (tilesize < MIN_TILE_SIZE)
			tilesize = MIN_TILE_SIZE;
		
		if (tilesize > MAX_TILE_SIZE)
			tilesize = MAX_TILE_SIZE;
		
		SIZE 		= colorChangerGame.getMatchfield().getSize();
		TILE_SIZE   = tilesize;
		
		// setup the JFrame
		setTitle(ColorChangerGame.TITLE);
		setSize((SIZE)*(TILE_SIZE), (SIZE)*(TILE_SIZE));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addMouseListener(this);
		setResizable(false);
		setVisible(true);
		createBufferStrategy(BUFFERS);
			
		// initialize buffer
		buffer = getBufferStrategy();
		graphics = (Graphics2D) buffer.getDrawGraphics();
		graphics.setFont(font);
		
		
		infoPanelWidth = graphics.getFontMetrics().stringWidth(ColorChangerGame.TITLE)*2;
		
			// calculate the window borders to make the field fit in perfectly 
			extraWidth  =  (int) (((SIZE)*(TILE_SIZE)) - getContentPane().getSize().getWidth());
			extraHeight =  (int) (((SIZE)*(TILE_SIZE)) - getContentPane().getSize().getHeight());
				
			// apply the calculated values
			setSize((extraWidth+(SIZE)*(TILE_SIZE))+ infoPanelWidth, extraHeight+(SIZE)*(TILE_SIZE));
			setVisible(true);
		
		this.buttons = new Button[3];
		this.buttons[0] = new Button("Reset", (extraWidth+(SIZE)*(TILE_SIZE)), (SIZE)*(TILE_SIZE)-graphics.getFontMetrics().getHeight()*3, graphics);
		this.buttons[1] = new Button("Exit" , (extraWidth+(SIZE)*(TILE_SIZE)) + infoPanelWidth - (extraWidth*2 + graphics.getFontMetrics().stringWidth("Exit")*2), (SIZE)*(TILE_SIZE)-graphics.getFontMetrics().getHeight()*3, graphics);
		this.buttons[2] = new Button("Start Game" , (extraWidth+(SIZE)*(TILE_SIZE)) + (infoPanelWidth - (extraWidth*2 + graphics.getFontMetrics().stringWidth("Start Game")*2))/2, (SIZE)*(TILE_SIZE)-graphics.getFontMetrics().getHeight()*5, graphics);
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
		graphics.translate(extraWidth/2, extraHeight-extraWidth/2);
		
		// Render calls
		renderMatchfield(graphics, colorChangerGame.getMatchfield());
		renderInfoPanel(graphics);
		
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
					if(matchfield.isValidNeigbour(x,y) && colorChangerGame.isInputAllowed()){
						g.setColor(getColor(x,y));
					}else{
						g.setColor(!colorChangerGame.isInputAllowed() ? Color.DARK_GRAY : (getColor(x,y)).darker().darker().darker());
					}
					g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
				
				// Draw the black frame of every tile 
				g.setColor(Color.BLACK);
				g.drawRect(x*TILE_SIZE,y*TILE_SIZE, TILE_SIZE, TILE_SIZE);				
			}
		}
		
		// Draw current selection
		highlightTile(graphics,colorChangerGame.getMatchfield().getCurrentTile());
		
		// Draw mouse selection
		highlightTile(graphics,getMouseIndex());
	}
	
	/**
	 * Renders game information to free space on the left
	 * 
	 * @param g
	 *            the graphics object which we use to draw
	 */
	private void renderInfoPanel(Graphics2D g) {
		int x = (extraWidth+(SIZE)*(TILE_SIZE));
		int y = g.getFontMetrics().getHeight();
		
		// set color
		g.setColor(Color.BLACK);
		
		// draw headline
		g.drawString(ColorChangerGame.TITLE, x+infoPanelWidth/4, y);
		g.drawLine(x+infoPanelWidth/4, y, x+infoPanelWidth*3/4, y);
		
		// draw score
		g.drawString(String.format("Score: %4d", colorChangerGame.getMatchfield().getScore()), x, y*3);
		
		// draw buttons
		for(Button b : buttons){
			b.render(g);
		}
		
		// draw passed time
		double countdown = colorChangerGame.getCountdown().getTimerValue();
		int seconds = (int) countdown;
		int minutes = seconds / 60;
		int hours = minutes / 60;
		g.setColor(seconds > 20 ? Color.BLACK : Color.RED);
		g.setFont(clockFont);
		g.drawString(String.format("Duration: %02d:%02d:%02d.%03.3f (hh:mm:ss.ms)", hours,minutes%60,seconds%60, countdown-seconds), x, x-y/2);
	}

	/**
	 * Highlight the tile at the given point
	 * 
	 * @param g
	 *            the graphics object which we use to draw
	 * @param p
	 *            the tile position
	 */
	private void highlightTile(Graphics2D g, Point p){
		// if the tile is not null
		if(p != null && colorChangerGame.isInputAllowed()){			
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
		 switch (colorChangerGame.getMatchfield().getColorID(x,y)) {
			case 0:		return Color.RED;
			case 1:		return Color.GREEN;
			case 2:		return Color.BLUE;
			case 3:		return new Color(0xFF7300); // orange
			case 4:		return Color.YELLOW;
			case 5:		return new Color(0x993366); // purple
			case 6:		return Color.WHITE;
			case 7:     return Color.CYAN;
			case 8:     return new Color(0x00FF9E); // turquoise
			case 9:     return Color.MAGENTA;
			default:	return Color.BLACK;
		}
	}
	
	/**
	 * Invoked when a mouse button has been released on a component.
	 * Here we call the corresponding method in our controller-class.
	 */
	public void mouseReleased(MouseEvent e) {
		colorChangerGame.mouseReleased(e);
	}

	/**
	 * Gets the mouse position as index values and does some error prevention.
	 * 
	 * @return the indices as a point. If it is not a valid mouse position it
	 *         will return null.
	 */
	public Point getMouseIndex(){
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
	 * @return All the buttons of the gui
	 */
	public Button[] getButtons(){
		return buttons;
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
		return (((int)pos-extraWidth/2)/TILE_SIZE)*TILE_SIZE;
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
		return (((int)pos-(extraHeight-extraHeight/4+1))/TILE_SIZE)*TILE_SIZE;
	}
		
	/**
	 * Represtents an interactive GUI-button
	 * 
	 * @author Florian Straub
	 */
	class Button{
		
		/* ******  Attributes  ****** */
			private int x, y;
			private int height, width;
			private String text;
		/* ****** /Attributes  ****** */
			
		/**
		 * Creates a new button for the GUI
		 * 
		 * @param text
		 *            the text it should display
		 * @param x
		 *            the x-position
		 * @param y
		 *            the y-position
		 * @param g
		 *            the graphics that calculates the string width and height
		 */
		public Button(String text, int x, int y, Graphics2D g){
			this.x = x;
			this.y = y;
			this.text = text;
			this.height = (int) (g.getFontMetrics().getHeight()*1.5);
			this.width  = g.getFontMetrics().stringWidth(text)*2;
		}
		
		/**
		 * Renders the button at the given x/y coordinates
		 * 
		 * @param g
		 *            the graphics object used to render on the screen
		 */
		public void render(Graphics2D g) {
			boolean highlighted = isMouseInside();
			g.setColor(!highlighted ? Color.LIGHT_GRAY : Color.LIGHT_GRAY.brighter());
			g.fillRect(x, y, width, height);
			g.setColor(!highlighted ? Color.BLACK : Color.RED);
			g.drawRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawString(text, x+width/4, y+height-height/3);
		}
		
		/**
		 * @return if the mouse is inside of the button
		 */
		public boolean isMouseInside() {
			Point p = getMousePosition();
			if (p != null)
				p.translate(-extraWidth / 2, -extraHeight);
			return p != null && p.getX() >= x && p.getX() <= x + width && p.getY() >= y && p.getY() <= y + height;
		}

		/**
		 * @return the text of the button
		 */
		public String getText() {
			return text;
		}
	}
	
	/* ********  Unused implementations ******** */
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
	/* ******** /Unused implementations ******** */
}
