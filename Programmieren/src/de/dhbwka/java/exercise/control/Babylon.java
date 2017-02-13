package de.dhbwka.java.exercise.control;

import java.util.Scanner;

public class Babylon {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.printf("Programm zur Berechnung einer Quadratwurzel\n");
		System.out.append("Bitte eine Zahl eingeben:");
		double zahl = scanner.nextDouble();
		if (zahl < 0) {
			System.out.printf("Keine Wurzel aus einer negativen Zahl!\n");
		} else {

			double x0 = 1;
			int schritte = 0;
			do {
				x0 = (x0 + zahl / x0) / 2;
				System.out.println((schritte+1)+". " + x0);
				schritte++;
			} while (x0 * x0 - zahl > 1E-6);
			System.out.printf("Ergebnis : %10.8f\n", x0);
			System.out.printf(" nach %d Schritten\n", schritte);
		}
	}

}
