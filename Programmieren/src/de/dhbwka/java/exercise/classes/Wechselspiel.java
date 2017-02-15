package de.dhbwka.java.exercise.classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

public class Wechselspiel extends JFrame implements Runnable, MouseListener {

	/* ******************* Attributes ******************* */
	private static final long serialVersionUID = 203636144384178165L;

	private final int SIZE;
	private final int TILE_SIZE;
	private final int BUFFERS = 2;
	private final int TARGET_FPS = 60;
	private final int extraWidth;
	private final int extraHeight;
	private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

	private final String COLUMNS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final Font font = new Font("Arial", Font.PLAIN, 28);

	private int[][] colors;
	private boolean isRunning;

	private BufferStrategy buffer;
	private Graphics2D graphics;
	private Thread gameThread;
	/* ******************* /Attributes ******************* */

	public Wechselspiel(int size, int tilesize) {
		this.SIZE = size+1;
		this.TILE_SIZE = tilesize;
		setTitle("Wechselspiel");
		setSize((SIZE)*(TILE_SIZE), (SIZE)*(TILE_SIZE));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);	
		
		extraWidth  =  (int) (((SIZE)*(TILE_SIZE)) - getContentPane().getSize().getWidth());
		extraHeight =  (int) (((SIZE)*(TILE_SIZE)) - getContentPane().getSize().getHeight());
		
		setSize(extraWidth+(SIZE)*(TILE_SIZE), extraHeight+(SIZE)*(TILE_SIZE));
		setVisible(true);
		createBufferStrategy(BUFFERS);
		
		colors = new int[size][size];
		Random rand = new Random();
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				colors[i][j] = rand.nextInt(7);
			}
		}
		
		buffer = getBufferStrategy();
		gameThread = new Thread(this, "GameThread-Wechselspiel");
	}

	public void startGame() {
		isRunning = true;
		gameThread.start();
	}

	public void run() {
		long lastLoopTime = System.nanoTime();

		while (isRunning) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / 100_000_000.0d;

			update(delta);

			graphics = (Graphics2D) buffer.getDrawGraphics();
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.fillRect(0, 0, getWidth(), getHeight());
			graphics.translate(extraWidth/2, extraHeight-extraHeight/4+1);
			render(graphics);
			buffer.show();
			graphics.dispose();
			Toolkit.getDefaultToolkit().sync();

			try {
				long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
				Thread.sleep(timeout < 0 ? 1000/TARGET_FPS : timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void update(double delta) {

	}

	private void render(Graphics2D g) {
		
		
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				if(x == 0){
					String text = String.valueOf(y);
					Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
					g.drawString(text, (int) (x*TILE_SIZE+TILE_SIZE/2-r.getWidth()/2), (int) ((y)*TILE_SIZE+TILE_SIZE/2+r.getHeight()/3));	
				}else if(y == 0){
					String text = String.valueOf(COLUMNS.charAt(x-1));
					Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
					g.drawString(text, (int) (x*TILE_SIZE+TILE_SIZE/2-r.getWidth()/2), (int) (y*TILE_SIZE+TILE_SIZE/2+r.getHeight()/3));					
				}else{
					g.setColor(getColor(x,y));
					g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
				g.setColor(Color.BLACK);
				g.drawRect(x*TILE_SIZE,y*TILE_SIZE, TILE_SIZE-1, TILE_SIZE-1);				
			}
		}
		
		if(getMousePosition()!=null){
			java.awt.Point p = getMousePosition();
			int x = toX(p.getX());
			int y = toY(p.getY());
			
			int indexX = (x+1)/(TILE_SIZE-1);
			int indexY = (y+1)/(TILE_SIZE-1);

			if(indexX > 0 && indexX < SIZE && indexY > 0 && indexY < SIZE){			
				int radius = TILE_SIZE/4;
				g.setColor((getColor(indexX,indexY)).darker());
				g.fillRect(x,y, TILE_SIZE, TILE_SIZE);
				g.setColor(invert(getColor(indexX,indexY)).brighter());
				g.fillOval(x+TILE_SIZE/2-radius/2, y+TILE_SIZE/2-radius/2, radius,radius);
			}
		}
	}

	private Color invert(Color color) {
		return new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue());
	}

	private int toX(double pos){
		return (((int)pos-extraWidth/2)/TILE_SIZE)*TILE_SIZE;
	}
	
	private int toY(double pos){
		return (((int)pos-(extraHeight-extraHeight/4+1))/TILE_SIZE)*TILE_SIZE;
	}
	
	private Color getColor(int x, int y) {
		if (x <= 0 || y <= 0)
			return Color.BLACK;

		switch (colors[x-1][y-1]) {
			case 0:		return Color.RED;
			case 1:		return Color.GREEN;
			case 2:		return Color.BLUE;
			case 3:		return Color.ORANGE;
			case 4:		return Color.YELLOW;
			case 5:		return Color.PINK;
			case 6:		return Color.WHITE;
			default:	return Color.BLACK;
		}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {
		
	}
}
