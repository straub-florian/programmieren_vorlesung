package de.dhbwka.java.exercise.arrays;

import java.util.Scanner;

import de.dhbwka.java.exercise.arrays.StandardDeviation;

@SuppressWarnings("unused")
public class BubbleSort {
	private static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		System.out.println(BubbleSort.class.getSimpleName());
		System.out.println("------------------" + System.lineSeparator());

		// Anzahl Elemente einlesen
		System.out.append("Bitte Anzahl der Elemente n eingeben:");
		int element = scanner.nextInt();
		int[] numbers = new int[element];

		// Elemente einlesen
		for (int i = 0; i < element; i++) {
			System.out.printf("Zahl %1d eingeben:", i);
			numbers[i] = scanner.nextInt();
		}

		// Sortieren
		sort(numbers);

		// Ausgabe
		System.out.append("Sortiert:");
		for (int i = 0; i < numbers.length; i++) {
			System.out.append(" " + numbers[i]);
		}
	}

	public static int[] sort(int[] numbers) {
		boolean move = true;
		while (move) {
			move = false;
			for (int i = 0; i < numbers.length - 1; i++) {
				int a = numbers[i];
				int b = numbers[i + 1];

				if (a > b) {
					numbers[i] = b;
					numbers[i + 1] = a;
					move = true;
				}
			}
		}

		return numbers;
	}

}
