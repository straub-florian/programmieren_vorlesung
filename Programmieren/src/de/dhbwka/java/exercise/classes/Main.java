package de.dhbwka.java.exercise.classes;

import java.util.Random;

import de.dhbwka.java.exercise.classes.wechselspiel.Spielfeld;

@SuppressWarnings("unused")
public class Main {

	public static void main(String[] args) {
//		execRadio();
//		execPoint();
//		execBankAccount();
//		execPolynominal();
//		execComplex();
//		execHorner();
//		execNimmspiel();
//		execLotto();
//		execMasterMind();
		execWechselspiel();
	}

	private static void execRadio() {
		Radio radio = new Radio(true, 5, 100.8);
		System.out.println(radio.toString());
	}

	private static void execBankAccount() {
		Account acc = new Account(15453, "Dirk", 5000, 1000);
		System.out.println(acc.toString());
		acc.processPayment(6000);
		acc.processDeposit(5000);
		acc.processPayment(6000);
	}

	private static void execPoint() {
		Point point = new Point(10, 10);
		System.out.println(point);
		System.out.println("\tSpiegel: " + point.mirror());
		System.out.println("\tSpiegel X: " + point.mirrorX());
		System.out.println("\tSpiegel Y: " + point.mirrorY());
		System.out.println("\tAbstand von [10;10] zu [10;20]: " + point.getDistance(new Point(10, 20)).getLength());
	}

	private static void execPolynominal() {
		Polynomial p1 = new Polynomial(2, 0, 0);
		Polynomial p2 = new Polynomial(0, -4, 1);
		System.out.println("P1: " + p1.toString());
		System.out.println("P2: " + p2.toString());
		Polynomial p3 = p1.add(p2);
		System.out.println("P3 = P1 + P2: " + p3.toString());
		p3 = p3.multiply(2.0);
		System.out.println("P3 = 2.0 * P3: " + p3.toString());
		double[] z = p3.zeros();
		System.out.printf("Nullstellen von P3: (%s)" + System.lineSeparator(), p3.toString());
		for (int i = 0; i < z.length; i++) {
			System.out.append(z[i] + ", ");
		}
	}

	private static void execComplex() {
		// Test
		Complex c1 = new Complex(1, 2);
		Complex c2 = new Complex(2, 1);
		System.out.println("C1:    " + c1);
		System.out.println("C2:    " + c2);
		System.out.println("C1+C2: " + c1.add(c2));
		System.out.println("C1-C2: " + c1.sub(c2));
		System.out.println("C1*C2: " + c1.mult(c2));
		System.out.println("C1/C2: " + c1.div(c2));
		System.out.println("C1<C2: " + c1.isLess(c2));

		// 10 Zufallszahlen generieren
		Random random = new Random();
		Complex[] numbers = new Complex[10];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = new Complex(random.nextDouble() * 10, random.nextDouble() * 10);
		}

		// Ausgabe unsortiert
		System.out.println("Unsortiert:");
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i]);
		}

		// Sortieren mit Bubblesort
		boolean move = true;
		while (move) {
			move = false;
			for (int i = 0; i < numbers.length - 1; i++) {
				Complex a = numbers[i];
				Complex b = numbers[i + 1];

				if (b.isLess(a)) {
					numbers[i] = b;
					numbers[i + 1] = a;
					move = true;
				}
			}
		}

		// Ausgabe unsortiert
		System.out.println("Sortiert:");
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i]);
		}
	}

	private static void execHorner() {
		Horner horner = new Horner(0.5,-3,2,4,3,-10,8,4.5,3,-2,1);
		System.out.println(horner);
		System.out.println(horner.getValue(1.5f));
	}
	
	private static void execNimmspiel() {
		Nimmspiel spiel = new Nimmspiel("Mark", "Ralf");
		spiel.startGame();
	}
	
	private static void execLotto() {
		Lotto lotto = new Lotto(6,49);
		lotto.guess();
		lotto.pick();
		System.out.println(lotto);
	}
		
	private static void execMasterMind() {
		MasterMind m = new MasterMind();
		m.startGame();
	}
	

	private static void execWechselspiel() {
		Spielfeld w = new Spielfeld(9,48);
		w.startGame();
		
	}
}
