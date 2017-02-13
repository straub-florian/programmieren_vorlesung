package de.dhbwka.java.exercise.control;

import java.util.Scanner;

public class NumberGuess {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String spielername = "";
		int zahl = 0;
		int versuch = 0;
		int ziel = (int) (Math.random() * 100);

		System.out.println("Rate zahlen!\n\n");
		System.out.append("Wie lautet dein Name:");
		spielername = scanner.next();
		
		while (true) {
			System.out.append(spielername + ",rate eine Zahl: ");
			zahl = scanner.nextInt();			
			versuch++;
			
			if (zahl > ziel) {
				System.out.println(versuch + ". die Zahl ist zu groﬂ!");
			} else if (zahl < ziel) {
				System.out.println(versuch + ". die Zahl ist zu klein!");
			} else {
				System.out.println(zahl + " ist korrekt!");
				System.out.println("Was mˆchtest du tun?");
				System.out.println("0 - das Spiel beenden");
				System.out.println("1 - ein neues Spiel starten");
				zahl = scanner.nextInt();
				if (zahl == 0) {
					break;
				} else {
					versuch = 0;
					ziel = (int) (Math.random() * 100);
				}
			}
		}
		System.exit(0);
	}
}
