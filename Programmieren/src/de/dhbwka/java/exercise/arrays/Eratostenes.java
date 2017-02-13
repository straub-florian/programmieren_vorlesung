package de.dhbwka.java.exercise.arrays;

import java.util.Scanner;

public class Eratostenes {
	private static Scanner scanner;
	private static final int LENGTH = 10;
	
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		System.out.println(Eratostenes.class.getSimpleName());
		System.out.println("------------------" + System.lineSeparator());

		System.out.append("Bitte Grenze angeben:");
		int limit = scanner.nextInt();

		int[] sieve = new int[limit - 1];
		int[] primes = new int[0];

		// Sieb befüllen
		for (int i = 0; i < sieve.length; i++) {
			sieve[i] = i + 2;
		}

		// Sieb sortieren
		for (int i = 0; i < sieve.length; i++) {
			int min = i;
			for (int j = i; j < sieve.length; j++) {
				if (sieve[j] < sieve[min]) {
					min = j;
				}
			}

			int tmp = sieve[i];
			sieve[i] = sieve[min];
			sieve[min] = tmp;
		}

		// Solange Sieb nicht leer ist
		while (sieve.length > 0) {
			// Kleinste Zahl
			int min = sieve[0];

			/** SIEB **/
			// Entferne Vielfache aus Sieb Array
			for (int i = 0; i < sieve.length; i++) {
				if (sieve[i] % min == 0) {
					sieve[i] = -1;
				}
			}

			// Neue Arraygröße bestimmen
			int groesse = 0;
			for (int i = 0; i < sieve.length; i++) {
				if (sieve[i] != -1) {
					groesse++;
				}
			}

			// Array zwischenspeichern
			int[] temp = new int[groesse];
			int j = 0;
			for (int i = 0; i < sieve.length; i++) {
				if (sieve[i] != -1) {
					temp[j] = sieve[i];
					j++;
				}
			}

			// Sieb Array wiederherstellen
			sieve = new int[temp.length];
			System.arraycopy(temp, 0, sieve, 0, temp.length);

			/** PRIMES **/
			// Primes zwischenspeichern
			int[] tempPrime = new int[primes.length + 1];
			System.arraycopy(primes, 0, tempPrime, 0, primes.length);

			// Neue Primzahl hinzufügen und Primes Array wiederherstellen
			tempPrime[tempPrime.length - 1] = min;
			primes = new int[tempPrime.length];
			System.arraycopy(tempPrime, 0, primes, 0, tempPrime.length);
		}

		// Formatiere Ausgaben
		System.out.println("\nAlle Primzahlen von 2 bis " + limit + ": ");
		System.out.println(System.lineSeparator()+"-------+--------+--------+--------+--------+--------+--------+--------+--------+--------+");
		for (int i = 0; i < primes.length; i++) {
			System.out.printf("%6d | ", primes[i]);
			if (i % LENGTH == LENGTH-1)
				System.out.append(System.lineSeparator()+"-------+--------+--------+--------+--------+--------+--------+--------+--------+--------+"+System.lineSeparator());
		}
		System.out.append(System.lineSeparator()+"-------+");
		for(int i = 1; i < primes.length % LENGTH; i++){
			System.out.append("--------+");
		}
	}

}
