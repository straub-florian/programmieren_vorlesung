package de.dhbwka.java.exercise.control;

import java.util.Scanner;

public class AddUp {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int zahl = 0;
		int summe = 0;
		System.out.println("Summenberechnung"						+System.lineSeparator()
						 + "----------------"						+System.lineSeparator()
						 + "(zum Abbrechen negative Zahl eingeben)"	+System.lineSeparator());
		
		while (zahl >= 0) {
			System.out.append("Bitte Zahl eingeben: ");
			zahl = scanner.nextInt();
			if (zahl > 0)
				summe += zahl;
		}
		System.out.println("Ergenis der Summe: " + summe);
	}
}
