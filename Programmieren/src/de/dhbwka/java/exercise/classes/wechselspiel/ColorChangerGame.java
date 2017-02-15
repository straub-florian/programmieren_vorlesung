package de.dhbwka.java.exercise.classes.wechselspiel;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import de.dhbwka.java.exercise.classes.wechselspiel.GUI.Button;

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
public class ColorChangerGame implements Runnable {

	/* ******************* Attributes ******************* */
		public static final String TITLE = "Wechselspiel";
		
		private final int TARGET_FPS = 60;
		private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
		private boolean isRunning;
		private long    startTimeStamp;
	
		private GUI gui;
		private Thread gameThread;
		private Matchfield matchfield;
	/* ******************* /Attributes ******************* */

	/**
	 * The main method which starts the game
	 * 
	 * @param args
	 *            possible arguments
	 */
	public static void main(String[] args) {
		ColorChangerGame cgg = new ColorChangerGame(9, 48);
		cgg.startGame();
	}

	/**
	 * Constructor of {@link #ChangeGame}. It initializes all attributes and
	 * sets up the GUI and matchfield.
	 * 
	 * @param size
	 *            how much tiles the game should vertically/horizontally have
	 * @param tilesize
	 *            how big each tile should be in pixels
	 */
	public ColorChangerGame(int size, int tilesize) {
		this.matchfield = new Matchfield(size, tilesize);
		this.gui = new GUI(this);
		this.gameThread = new Thread(this, "GameThread");
	}

	/**
	 * Starts the game-thread.
	 */
	public void startGame() {
		isRunning = true;
		startTimeStamp = System.currentTimeMillis();
		gameThread.start();
	}

	/**
	 * Resets the game.
	 */
	private void resetGame() {
		matchfield.reset();
		startTimeStamp = System.currentTimeMillis();
	}
	
	/**
	 * Stops the game-thread.
	 */
	public void stopGame() {
		// only stop the game if it is running
		if (isRunning) {
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

			// Calculate the time that passed between the last and this
			// iteration.
			lastLoopTime = now;

			// Process all rendering
			gui.render();
			// Sync with monitors Hz-rate
			Toolkit.getDefaultToolkit().sync();

			// Make the thread sleep the calculated value, so that the
			// TARGET_FPS value can be approached
			try {
				long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
				Thread.sleep(timeout < 0 ? 1000 / TARGET_FPS : timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// If the game is no longer running --> EXIT
		System.exit(0);
	}

	/**
	 * Invoked when a mouse button has been released on a component. Here we set
	 * the current tile and do the swapping.
	 */
	public void mouseReleased(MouseEvent e) {
		for(Button b : gui.getButtons()){
			if(b.isMouseInside()){
				switch(b.getText()){
					case "Exit":  stopGame();
					case "Reset": resetGame();
				}
			}
		}
		matchfield.swapTiles(gui.getMouseIndex());
	}

	public long getPassedTime(){
		return System.currentTimeMillis() - startTimeStamp;
	}
	
	/**
	 * @return if the game is running
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	/**
	 * @return the graphical user interface
	 */
	public GUI getGui() {
		return gui;
	}

	/**
	 * @return the matchfield
	 */
	public Matchfield getMatchfield() {
		return matchfield;
	}
}
