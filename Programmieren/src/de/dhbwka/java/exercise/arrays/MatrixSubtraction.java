package de.dhbwka.java.exercise.arrays;

import java.util.Random;
import java.util.Scanner;

public class MatrixSubtraction {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		
		System.out.append("Bitte Anzahl der Zeilen für Matrix eingeben:");
		int a1=scan.nextInt();
		System.out.append("Bitte Anzahl der Spalten für Matrix eingeben:");
		int a2=scan.nextInt();

		int[][] matrixA = new int[a1][a2];
		int[][] matrixB = new int[a1][a2];
		int[][] result  = new int[a1][a2];

		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixA[i][j] = rand.nextInt(100);
				matrixB[i][j] = rand.nextInt(100);
			}
		}
		
		System.out.printf(System.lineSeparator()+"%2d x %2d Matrix mit Zufallszahlen von 0 - 99:"+System.lineSeparator(),a1,a2);
		
		System.out.println("Matrix A");
		printMatrix(matrixA);	
		System.out.println("Matrix B");
		printMatrix(matrixB);	
		
		for(int i = 0; i < a1; i++){
			for(int j = 0; j < a2; j++){
				result[i][j] = matrixA[i][j] - matrixB[i][j];
			}
		}
		
		System.out.println();
		System.out.println("Ergebnis");
		printMatrix(result);	
	}
	
	private static void printMatrix(int[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				System.out.printf("%4d ",matrix[i][j]);
			}
			System.out.append(System.lineSeparator());
		}
	}

}

/*
 * 
 * Aufgabenstellung: Schreiben Sie ein Programm MatrixSubtraction, das zwei n x
 * m Matrizen X und Y mit ganzen Zahlen elementweise subtrahiert. Hinweis: Sie
 * können die beiden Matrizen X und Y entweder mit Zufallszahlen füllen oder per
 * Hand eingeben lassen. Im Falle von Zufallszahlen geben Sie auch die
 * ursprünglichen Matrizen X und Y aus! Beispielausgabe (4 x 4-Matrizen mit
 * Zufallszahlen von 0 bis 99): Bitte Anzahl der Zeilen n eingeben: 4 Bitte
 * Anzahl der Spalten m eingeben: 4 X: 47 29 73 2 67 42 93 9 6 4 36 86 70 57 18
 * 43 Y: 90 58 97 96 6 60 0 46 46 11 55 96 33 95 71 7 X-Y: -43 -29 -24 -94 61
 * -18 93 -37 -40 -7 -19 -10 37 -38 -53 36
 * 
 */