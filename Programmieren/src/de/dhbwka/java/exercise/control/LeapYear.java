package de.dhbwka.java.exercise.control;

import java.util.Scanner;

public class LeapYear {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Schaltjahrtester\n----------------\n");
		System.out.append("Bitte Jahr zum prüfen eingeben:");
		int jahr = scanner.nextInt();

		if (jahr % 4 == 0) {
			if (jahr % 400 != 0 && jahr % 100 == 0) {
				System.err.println(jahr + " ist kein Schaltjahr");
			} else {
				System.out.println(jahr + " ist ein Schaltjahr");
			}
		}
	}
}
