package de.dhbwka.java.exercise.arrays;

public class Fibonacci {
	public static void main(String[] args) {
		 int[] fibonacci = new  int[20];
//		long[] fibonacci = new long[50];
		
		// Ausgabe Titel und erste 2 Zahlen
		System.out.println(Fibonacci.class.getSimpleName() + System.lineSeparator() + "------------------"
				+ System.lineSeparator() + String.format("%3d. %3d", 1, fibonacci[0] = 1) 
				+ System.lineSeparator() + String.format("%3d. %3d", 2, fibonacci[1] = 1));
		
		// Berechnung und Ausgabe der Zahlen
		for (int i = 2; i < fibonacci.length; i++) {
			fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
			System.out.printf("%3d. %3d" + System.lineSeparator(), i + 1, fibonacci[i]);
		}
	}
}