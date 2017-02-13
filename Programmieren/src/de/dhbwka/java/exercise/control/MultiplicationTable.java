package de.dhbwka.java.exercise.control;

public class MultiplicationTable {
	
	public static void main(String[] args) {
		for(int y = 1; y <= 10; y++){
			for(int x = 1; x <= 10; x++){
				System.out.append(String.format("%3d ", x*y));
			}
			System.out.append("\n");
		}
	}
}
