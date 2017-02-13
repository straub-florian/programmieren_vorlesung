package de.dhbwka.java.exercise.classes;

import java.util.Random;
import java.util.Scanner;

public class MasterMind {

	private int tries;
	private boolean isRunning;

	private String toGuess;
	private String[] guessed;

	private final int LENGTH = 5;
	private final int ALLOWED_TRIES = 7;

	private final String CHARS = "ABCDEFGH";
	private final String TEXT_PLAYER_GUESS = "Geben Sie ihren Tipp ab: ";
	private final String TEXT_INPUT_ERROR = "Eingegebener Wert ist fehlerhaft, bitte erneut eingeben:";
	private final String TEXT_PLAYER_TRIES = "%1d bisherige Versuche:";
	private final String TEXT_PLAYER_WIN = "Mit %1d Versuch(en) gewonnen!";
	private final String TEXT_PLAYER_LOSE = "Leider verloren... Neues Spiel? (J/N)";

	public MasterMind() {
		init();
	}

	private void init() {
		isRunning = false;
		tries = 0;
		this.guessed = new String[ALLOWED_TRIES];
		generateRandomString();
	}

	private void generateRandomString() {
		this.toGuess = "";
		Random random = new Random();
		for (int i = 0; i < LENGTH; i++) {
			this.toGuess += CHARS.charAt(random.nextInt(CHARS.length()));
		}
		toGuess = "HDGDF";
		// FDGDH
	}

	public void startGame() {
		isRunning = true;
		gameLoop();
	}

	private void gameLoop() {
		Scanner scanner = new Scanner(System.in);
		while (isRunning) {
			out("");
			out(false, TEXT_PLAYER_GUESS);
			String input = scanner.nextLine().toUpperCase();
			while (!isValidInput(input)) {
				out(false, TEXT_INPUT_ERROR);
				input = scanner.nextLine().toUpperCase();
			}

			guessed[tries] = input;
			if (input.equals(toGuess)) {
				out(TEXT_PLAYER_WIN, tries + 1);
				isRunning = false;
				break;
			}

			out(TEXT_PLAYER_TRIES, tries + 1);
			for (int i = 0; i < guessed.length; i++) {
				if (guessed[i] == null)
					break;
				outputResult(i);
			}

			tries++;

			if (tries >= ALLOWED_TRIES) {
				out(false, TEXT_PLAYER_LOSE);
				if (scanner.nextLine().equals("J")) {
					init();
					isRunning = true;
				} else {
					break;
				}
			}
		}
	}

	private void outputResult(int index) {
		int correct = 0, wrongIndex = 0;

		boolean[] isCorrect = new boolean[LENGTH];

		for (int i = 0; i < LENGTH; i++) {
			if (guessed[index].charAt(i) == toGuess.charAt(i)) {
				correct++;
				isCorrect[i] = true;
			}
		}

		for (int i = 0; i < LENGTH; i++) {
			if (isCorrect[i])
				continue;
			
			char guessedChar = guessed[index].charAt(i);			
			for (int j = 0; j < LENGTH; j++) {
				char toGuessChar = toGuess.charAt(j);
				if (guessedChar == toGuessChar && !isCorrect[j]) {
					wrongIndex++;
					break;
				}
			}

		}

		out("%s %1d  %1d", guessed[index], correct, wrongIndex);
	}

	private boolean isValidInput(String input) {
		boolean length = input.length() == 5;
		boolean elements = true;

		if (length)
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

	private void out(String msg, Object... o) {
		out(true, msg, o);
	}

	private void out(boolean linewrap, String msg, Object... o) {
		System.out.printf(msg + (linewrap ? System.lineSeparator() : ""), o);
	}
}
