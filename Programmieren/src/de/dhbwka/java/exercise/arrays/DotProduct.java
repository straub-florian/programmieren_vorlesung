package de.dhbwka.java.exercise.arrays;

import java.util.Scanner;

import de.dhbwka.java.exercise.arrays.StandardDeviation;

@SuppressWarnings("unused")
public class DotProduct {
	private static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		System.out.println(DotProduct.class.getSimpleName());
		System.out.println("------------------" + System.lineSeparator());

		// Anzahl Elemente einlesen und Arrays initialisieren
		System.out.append("Bitte Anzahl der Elemente n eingeben:");
		int element = scanner.nextInt();
		double[] elementsA = new double[element];
		double[] elementsB = new double[element];
		
		// Werte einlesen
		for (int i = 0; i < element; i++) {
			System.out.printf("Bitte x_%2d eingeben: ", i);
			elementsA[i] = scanner.nextDouble();
			System.out.printf("Bitte y_%2d eingeben: ", i);
			elementsB[i] = scanner.nextDouble();
		}

		// Skalarprodukt berechnen
		double dotProduct = 0;
		for (int i = 0; i < element; i++) {
			dotProduct += elementsA[i] * elementsB[i];
		}
		
		// Ausgabe
		System.out.printf("Das Skalarprodukt von x und y ist %1.3f", dotProduct);
	}
}
