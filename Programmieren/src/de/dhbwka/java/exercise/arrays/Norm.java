package de.dhbwka.java.exercise.arrays;

import java.util.Scanner;

import de.dhbwka.java.exercise.arrays.StandardDeviation;

@SuppressWarnings("unused")
public class Norm {
	private static Scanner scanner;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		System.out.println(Norm.class.getSimpleName());
		System.out.println("------------------"+System.lineSeparator());
		
		// Anzahl ELemente eingeben
		System.out.append("Bitte Anzahl der Elemente n eingeben:");
		int element = scanner.nextInt();
		double[] elements = new double[element];
		
		// Elemente einlesen
		for(int i = 0; i < element; i++){
			System.out.printf("Bitte x_%2d eingeben: ", i);
			elements[i] = scanner.nextDouble();
		}
		
		// Länge ermitteln
		double length = 0;
		for(double d : elements){
			length += d*d;
		}
		
		// Ausgabe Länge
		System.out.printf("Der Betrag von x ist %1.3f",Math.sqrt(length));
	}

}
