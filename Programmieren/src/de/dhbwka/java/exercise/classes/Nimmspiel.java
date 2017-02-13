package de.dhbwka.java.exercise.classes;

import java.util.Scanner;

@SuppressWarnings("resource")
public class Nimmspiel {

	private int[] piles;
	private String playerA, playerB;
	private boolean nextPlayerA;

	private boolean isRunning;

	private final String TEXT_PLAYER_QUESTION1 = "Spieler %s: Von welchem Haufen ziehen Sie Kugeln? ";
	private final String TEXT_PLAYER_QUESTION2 = "Spieler %s: Wieviele Kugeln ziehen Sie? ";
	private final String TEXT_PLAYER_ERROR = "\t[Ungültiger Wert] Wiederholen Sie die Eingabe: ";
	private final String TEXT_PLAYER_END = "Spiel beendet";
	private final String TEXT_PLAYER_WON = "Gewonnen hat Spieler %s";
	private final String TEXT_TO_STRING = "Spieler: %s und %s | Haufen 1: %1d Kugel(n) | Haufen 2: %1d Kugel(n)";

	public Nimmspiel(String spielerA, String spielerB) {
		this.piles = new int[2];
		this.piles[0] = 1 + (int) (Math.random() * 10);
		this.piles[1] = 1 + (int) (Math.random() * 10);
		this.playerA = spielerA;
		this.playerB = spielerB;
	}

	public void startGame() {
		isRunning = true;
		gameLoop();
	}

	private void gameLoop() {
		Scanner scanner = new Scanner(System.in);
		while (isRunning) {
			out(toString());

			if (piles[0] == 0 && piles[1] == 0) {
				out(TEXT_PLAYER_END);
				out(TEXT_PLAYER_WON, nextPlayerA ? playerB : playerA);
				isRunning  = false;
				break;
			}

			out(false, TEXT_PLAYER_QUESTION1, nextPlayerA ? playerA : playerB);
			int pile = scanner.nextInt();
			while (pile < 1 || pile > 2) {
				out(false,TEXT_PLAYER_ERROR);
				pile = scanner.nextInt();
			}

			out(false, TEXT_PLAYER_QUESTION2, nextPlayerA ? playerA : playerB);
			int amount = scanner.nextInt();
			while (amount > piles[pile - 1]) {
				out(false,TEXT_PLAYER_ERROR);
				amount = scanner.nextInt();
			}

			piles[pile - 1] -= amount;
			nextPlayerA = !nextPlayerA;
		}
	}

	private void out(String msg, Object... o) {
		out(true, msg, o);
	}

	private void out(boolean linewrap, String msg, Object... o) {
		System.out.printf(msg + (linewrap ? System.lineSeparator() : ""), o);
	}

	public int getHaufen1() {
		return piles[0];
	}

	public int getHaufen2() {
		return piles[1];
	}

	public String getSpielerA() {
		return playerA;
	}

	public String getSpielerB() {
		return playerB;
	}

	public boolean isNextPlayerA() {
		return nextPlayerA;
	}

	public boolean isRunning() {
		return isRunning;
	}
	
	public String toString() {
		return String.format(TEXT_TO_STRING, playerA, playerB, piles[0], piles[1]);
	}
}
