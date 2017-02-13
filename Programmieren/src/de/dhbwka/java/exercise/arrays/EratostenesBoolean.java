package de.dhbwka.java.exercise.arrays;

import java.util.Scanner;

public class EratostenesBoolean {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// Limit einlesen
		System.out.append("Bitte Grenze angeben:");
		int limit = new Scanner(System.in).nextInt() + 1;
		
		// Arrays deklarieren
		boolean[] sieve = new boolean[limit];
		
		primes(sieve);
		
//		// Über "sieve" iterieren
//		for (int i = 2; i < sieve.length; i++)
//			// Primzahl ?
//			if (!sieve[i]) {
//				// Markiere alle nicht Primzahlen
//				for (int j = 2*i; j < sieve.length; j += i)
//					sieve[j] = true;
//				// Ausgabe
//				System.out.println(i);
//			}
	}
	
	private static void primes(boolean[] sieve){
		for (int i = 2; i < sieve.length; i++)
			if (!sieve[i]) {
				for (int j = 2*i; j < sieve.length; j += i)
					sieve[j] = true;
				System.out.println(i);
			}
	}
}