package de.dhbwka.java.exercise.classes;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Beim bekannten Spiel MasterMind ist das Ziel, eine vom Gegenspieler gewählte
 * Farbkombination herauszufinden. Hier handelt es sich um eine Version die mit
 * textueller Ein-/Ausgabe arbeitet.
 * 
 * @author Florian Straub
 * @see <a href=
 *      "https://www.iai.kit.edu/~javavorlesung/dhbw/2016-17/09_Klassen_Aufgaben-3.pdf">
 *      Javavorlesung, Klassen Nr.3</a>
 */
public class MasterMind {

	/* ******************* Attributes ******************* */
	private int tries;
	private boolean isRunning;

	private String toGuess;
	private String[] guessed;
	private Scanner scanner;

	private final int LENGTH = 5;
	private final int ALLOWED_TRIES = 7;

	private final String CHARS = "ABCDEFGH";
	private final String TEXT_PLAYER_GUESS = "Geben Sie ihren Tipp ab: ";
	private final String TEXT_INPUT_ERROR = "Eingegebener Wert ist fehlerhaft, bitte erneut eingeben:";
	private final String TEXT_PLAYER_TRIES = "%1d bisherige Versuche:";
	private final String TEXT_PLAYER_WIN = "Mit %1d Versuch(en) gewonnen!  Neues Spiel? (J/N)";
	private final String TEXT_PLAYER_LOSE = "Leider verloren... Neues Spiel? (J/N)";

	/* ******************* /Attributes ******************* */

	/**
	 * The constructor that makes the game initialize.
	 */
	public MasterMind() {
		this.scanner = new Scanner(System.in);
		init();
	}

	/**
	 * Initializes the game.
	 */
	private void init() {
		this.isRunning = false;
		this.tries = 0;
		this.guessed = new String[ALLOWED_TRIES];

		generateRandomString();
	}

	/**
	 * Creates a random string with length {@link #LENGTH}, containing only
	 * characters out of {@link #CHARS}
	 */
	private void generateRandomString() {
		this.toGuess = "";
		Random random = new Random();
		for (int i = 0; i < LENGTH; i++) {
			this.toGuess += CHARS.charAt(random.nextInt(CHARS.length()));
		}
	}

	/**
	 * Starts the main game loop
	 */
	public void startGame() {
		isRunning = true;
		gameLoop();
	}

	/**
	 * The main game loop.
	 */
	private void gameLoop() {
		// while the game is running
		while (isRunning) {
			// let the player make a guess and only accept it if it is valid
			out(false, TEXT_PLAYER_GUESS);
			String input = scanner.nextLine().toUpperCase();
			while (!isValidInput(input)) {
				out(false, TEXT_INPUT_ERROR);
				input = scanner.nextLine().toUpperCase();
			}

			// check if the player won the game and exit if he did
			guessed[tries] = input;
			if (input.equals(toGuess)) {
				out(TEXT_PLAYER_WIN, tries + 1);
				isRunning = false;
				if (!newGame(scanner))
					break;
			}

			// output his results
			out(TEXT_PLAYER_TRIES, tries + 1);
			for (int i = 0; i < guessed.length; i++) {
				if (guessed[i] == null)
					break;
				outputResult(i);
			}
			out("");

			// increment the number of tries
			tries++;

			// check if the player is out of tries, if he is, display lose-text
			// and ask for a new game
			if (tries >= ALLOWED_TRIES) {
				out(false, TEXT_PLAYER_LOSE);
				if (!newGame(scanner))
					break;
			}
		}
	}

	/**
	 * Checks if the player wants to exit the game or play another one.
	 * 
	 * @param scanner
	 *            a scanner to get the players input
	 * @return if the player wants to play another game
	 */
	private boolean newGame(Scanner scanner) {
		if (scanner.nextLine().equals("J")) {
			init();
			isRunning = true;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Compares the guess with the one to guess and outputs a formatted value
	 * 
	 * @param index
	 *            The number of the guess to check
	 */
	private void outputResult(int index) {
		HashMap<Character, Integer> amounts = new HashMap<Character, Integer>();

		int correct = 0, wrongIndex = 0;
		boolean[] isCorrect = new boolean[LENGTH];

		// iterate over all chars of the input-string
		for (int i = 0; i < LENGTH; i++) {
			char to_guess = toGuess.charAt(i);

			// get number of correct placed chars
			if (guessed[index].charAt(i) == to_guess) {
				correct++;
				isCorrect[i] = true;
			}

			// get total amount of every char which is not correct
			if (!isCorrect[i])
				if (amounts.containsKey(to_guess)) {
					amounts.replace(to_guess, amounts.get(to_guess) + 1);
				} else {
					amounts.put(to_guess, 1);
				}
		}

		// get number of correct placed chars which are at the wrong spot
		for (int i = 0; i < LENGTH; i++) {
			if (isCorrect[i])
				continue;

			char guessedChar = guessed[index].charAt(i);
			for (int j = 0; j < LENGTH; j++) {
				char toGuessChar = toGuess.charAt(j);
				if (guessedChar == toGuessChar && !isCorrect[j] && amounts.get(guessedChar) > 0) {
					amounts.replace(guessedChar, amounts.get(guessedChar) - 1);
					wrongIndex++;
					break;
				}
			}
		}

		out("%s %1d  %1d", guessed[index], correct, wrongIndex);
	}

	/**
	 * Checks if the entered String is valid.
	 * 
	 * @param input
	 *            The string to check
	 * @return If input is valid
	 */
	private boolean isValidInput(String input) {
		boolean length = input.length() == LENGTH;
		boolean elements = true;

		// does the length of the input match the length of the string to guess?
		if (length)
			// Check if every chararcter of the input-string is also in the
			// CHAR-string
			for (int i = 0; i < input.length(); i++) {
			boolean found = false;
			for (int j = 0; j < CHARS.length(); j++) {
			if (input.charAt(i) == CHARS.charAt(j)) {
			found = true;
			}
			}
			if (!found) {
			elements = false;
			break;
			}
			}

		return length && elements;
	}

	/**
	 * Prints a formatted message.
	 * 
	 * @param msg
	 *            The message to print
	 * @param o
	 *            The values to format
	 */
	private void out(String msg, Object... o) {
		out(true, msg, o);
	}

	/**
	 * Prints a formatted message.
	 * 
	 * @param linewrap
	 *            Go to next line and print message or print to current one
	 * @param msg
	 *            The message to print
	 * @param o
	 *            The values to format
	 */
	private void out(boolean linewrap, String msg, Object... o) {
		System.out.printf(msg + (linewrap ? System.lineSeparator() : ""), o);
	}
}
