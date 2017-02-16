package de.dhbwka.java.exercise.classes.wechselspiel;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import de.dhbwka.java.exercise.classes.wechselspiel.GUI.Button;

/**
 * Es gibt ein Spielfeld mit NxN Feldern. Jedes Feld hat
 * eine aus insgesamt {@link Matchfield#COLOR_AMOUNT} Farben. Am Anfang wird das Spielfeld
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
		
		private static final double INITIAL_COUNTDOWN_VALUE = 120;
		
		private final int TARGET_FPS = 60;
		private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	
		private boolean isRunning;
		private boolean isInputAllowed;
		
		private GUI gui;
		private Thread gameThread;
		private Countdown countdown;
		private Matchfield matchfield;
	/* ******************* /Attributes ******************* */

	/**
	 * The main method which starts the game
	 * 
	 * @param args
	 *            possible arguments
	 */
	public static void main(String[] args) {
		int size 	 = 9;
		int tilesize = 64;
		int colors 	 = 7;
		
		ColorChangerGame cgg = new ColorChangerGame(size,tilesize,colors);
		cgg.startGame();
	}

	/**
	 * Constructor of {@link ColorChangerGame}. It initializes all attributes and
	 * sets up the {@link GUI} and {@link Matchfield}.
	 * 
	 * @param size
	 *            how much tiles the game should vertically/horizontally have
	 * @param tilesize
	 *            how big each tile should be in pixels
	 */
	public ColorChangerGame(int size, int tilesize, int colorAmount) {	
		this.matchfield = new Matchfield(size, colorAmount);
		this.gui = new GUI(this, tilesize);
		this.gameThread = new Thread(this, "GameThread");
		this.countdown = new Countdown(INITIAL_COUNTDOWN_VALUE);
	}

	/**
	 * Starts the game-thread.
	 */
	public void startGame() {
		isRunning = true;
		isInputAllowed = false;
		gameThread.start();
	}

	/**
	 * Resets the game.
	 */
	private void resetGame() {
		isInputAllowed = false;
		matchfield.reset();
		countdown.reset();
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
		// get initial time value
		long lastLoopTime = System.nanoTime();

		// as long as the game should run
		while (isRunning) {
			// get current time value
			long now = System.nanoTime();

			// calculate the time that passed between the last and this
			// iteration.
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / 1000_000_000.;

			// update the countdown
			countdown.update(delta);
			
			// End the game if the countdown hits zero
			if(countdown.hasFinished()){
				resetGame();
			}
			
			if(matchfield.hasBonus()){
				countdown.add(45);
			}
			
			// process all rendering
			gui.render();
			// sync with monitors Hz-rate
			Toolkit.getDefaultToolkit().sync();

			// make the thread sleep the calculated value, so that the
			// TARGET_FPS value can be approached
			try {
				long timeout = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
				Thread.sleep(timeout < 0 ? 1000 / TARGET_FPS : timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// if the game is no longer running --> EXIT
		System.exit(0);
	}

	/**
	 * Invoked when a mouse button has been released on a component. Here we set
	 * the current tile and do the swapping.
	 * 
	 * @param mouseEvent
	 *            the mouse event
	 */
	public void mouseReleased(MouseEvent mouseEvent) {
		// handle button press
		for(Button b : gui.getButtons()){
			if(b.isMouseInside()){
				switch(b.getText()){
					case "Exit":  stopGame(); break;
					case "Reset": resetGame(); break;
					case "Start Game": if(!isInputAllowed)activateInput(); break;
				}
			}
		}
		
		// swap tiles
		if (isInputAllowed)
			matchfield.swapTiles(gui.getMouseIndex());
	}

	/**
	 * Starts the countdown and makes the player be able to interact.
	 */
	private void activateInput() {
		countdown.start();
		isInputAllowed=true;
	}

	/**
	 * @return if the game is running
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	/**
	 * @return if the game accepts the players input. False if the player did
	 *         not start the game yet.
	 */
	public boolean isInputAllowed() {
		return isInputAllowed;
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
	
	/**
	 * @return the countdown timer
	 */
	public Countdown getCountdown() {
		return countdown;
	}
}
