package de.dhbwka.java.exercise.arrays;

import java.util.Scanner;

public class Pascal {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// Einlesen der Größe und Initialisieren
		Scanner scan = new Scanner(System.in);
		System.out.append("Bitte Anzahl Ebenen eingeben:");

		int size = scan.nextInt();
		int maxLength = 0;
		int[][] triangle = new int[size + 1][];

		triangle[0] = new int[1 + 2];
		triangle[0][1] = 1;

		// Dreieck erzeugen
		for (int i = 1; i <= size; i++) {
			triangle[i] = new int[i + 2];

			for (int j = 1; j < triangle[i].length - 1; j++) {
				triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];

				// "Längste" Zahl ermitteln
				int length = Integer.toString(triangle[i][j]).length();
				if (length > maxLength) {
					maxLength = length;
				}
			}
		}

		// Ausgabe der Ebenen
		for (int i = 1; i <= size; i++) {
			for (int k = size; k > i; k--)
				System.out.printf("%-" + (maxLength) + "s"," ");

			for (int j = 1; j < triangle[i].length - 1; j++)
				System.out.printf("%-" + (maxLength + maxLength) + "s", triangle[i][j]);

			System.out.println();
		}
	}
}