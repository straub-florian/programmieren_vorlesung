package de.dhbwka.java.exercise.operators;

import java.util.Calendar;
import java.util.Scanner;

/**
 * Die Klasse "Ostertermin", die das Datum von Ostern anhand des Datums
 * 
 * @author Florian Straub
 */
public class Easter {

	/**
	 * Die Standard main-Methode die zum Programmstart ausgeführt wird
	 * 
	 * @param args
	 *            Parameter die zum Programmstart übergeben werden können
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String  eingabe = "";
		w:while (true) {
			// Jahr einlesen
			System.out.append("Termin für Ostern berechnen!\nBitte Jahr eingeben: ");
			eingabe = scanner.next();
			
			for(int i = 0; i < eingabe.length(); i++){
				if(!Character.isDigit(eingabe.charAt(i))){
					System.err.println("Fehlerhafte Eingabe, abbruch...");
					break w;
				}
			}
			
			int jahr = Integer.parseInt(eingabe);

			// Lokale Variablen deklarieren
			int a, b, c, k, p, q, m, n, d, e, ostern;

			// Osterlogik anwenden
			a = jahr % 19;
			b = jahr % 4;
			c = jahr % 7;
			k = jahr / 100;
			p = (8 * k + 13) / 25;
			q = k / 4;
			m = (15 + k - p - q) % 30;
			n = (4 + k - q) % 7;
			d = (19 * a + m) % 30;
			e = (2 * b + 4 * c + 6 * d + n) % 7;
			ostern = (22 + d + e);

			boolean overflow = ostern > 31;
			boolean vergangen = jahr < Calendar.getInstance().get(Calendar.YEAR);

			System.out.println("\t"+jahr + (vergangen ? " war" : " ist") + " Ostern am "
					+ (overflow ? (ostern - 31) + ". April" : ostern + ". Maerz") + "\n");
		}
	}

	public static void test() {
		int i = 0;
		int j = 0;
		j = ++i;

		int k = (j++ + ++i);
		System.out.println("k: " + k);
		System.out.println("*: " + (j++ + ++i));
		System.out.println(j++ + ++i);
		int m = j++ * ++i;
		System.out.println("m: " + m);
		int n = --j * --i;
		System.out.println("n: " + n);
		System.out.println("i: " + i);
		System.out.println("j: " + j);
	}

}