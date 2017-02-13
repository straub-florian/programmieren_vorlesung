package de.dhbwka.java.exercise.control;

import java.util.Scanner;

public class Quadratics {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		while (true) {
			System.out.println("Quadratischer Gleichungsrechner\n-------------------------------\n");
			double a, b, c;
			Scanner scanner = new Scanner(System.in);
			System.out.append("Geben sie einen Wert für 'a' ein: ");
			a = scanner.nextDouble();
			System.out.append("Geben sie einen Wert für 'b' ein: ");
			b = scanner.nextDouble();
			System.out.append("Geben sie einen Wert für 'c' ein: ");
			c = scanner.nextDouble();

			if (a == 0) {
				if (b == 0) {
					System.out.println("Ihre Gleichung ist degeneriert!");
				} else {
					System.out.println("x = " + (-c / b));
				}
			} else {
				double d = (b*b) - (4 * a * c);
				if (d >= 0) {
					double x1 = ((-b) + Math.sqrt(d)) / (2 * a);
					double x2 = ((-b) - Math.sqrt(d)) / (2 * a);
					System.out.printf("x1 = %s, x2 = %s", x1, x2);
				} else {
					System.out.println("Die Lösung ist komplex konjungiert!");
				}
			}
			System.out.println("\n\nFür neue Rechnung 'new' eintippen.");
			System.out.println("Alles andere beendet die Anwendung!");
			if(!scanner.next().equals("new")){
				break;
			}else{
				System.out.print("\n\n\n\n\n\n");
			}
		}
		System.err.println("\nAnwendung beendet!");
		System.exit(0);
	}
}
