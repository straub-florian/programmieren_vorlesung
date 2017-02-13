package de.dhbwka.java.exercise.arrays;

import java.lang.reflect.Array;
import java.net.DatagramSocket;
import java.util.Random;
import java.util.Scanner;

import de.dhbwka.java.exercise.Prefab;

@SuppressWarnings("unused")
public class StandardDeviation {
	private static Scanner scanner;
	private static Random random;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		random = new Random();
		System.out.println(StandardDeviation.class.getSimpleName());
		System.out.println("------------------" + System.lineSeparator());

		// Größe einlesen
		System.out.append("Bitte Arraygröße eingeben: ");
		int arraysize = scanner.nextInt();
		int[] array = new int[arraysize];

		// Zufallszahlen generieren
		for (int i = 0; i < array.length; i++) {
			array[i] = random.nextInt(11); // Von 0 bis 10
		}

		// Mittelwert
		double average = 0;
		for (int i = 0; i < array.length; i++) {
			average += array[i];
		}
		average /= array.length;

		// Standardabweichung
		double standardDeviation = 0, variance = 0;
		for (int i = 0; i < array.length; i++) {
			standardDeviation += (array[i] - average)*(array[i] - average);
		}
		variance = (1f/(array.length-1)) * standardDeviation;
		standardDeviation = Math.sqrt(variance);

		System.out.printf("Mittelwert: %2.3f\nStandardabweichung: %2.3f\nVarianz: %2.3f", average, standardDeviation, variance);
	}

}
