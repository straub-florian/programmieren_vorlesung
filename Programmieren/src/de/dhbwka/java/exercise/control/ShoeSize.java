package de.dhbwka.java.exercise.control;

public class ShoeSize {
	
	public static void main(String[] args) {
		System.out.println("Zentimeter    | Gr��e");
		System.out.println("--------------+------");
		for(int i = 30; i <= 49; i++){
			System.out.printf("%2.2f - %2.2f | %2d\n",(i-1)/1.5,(i)/1.5,i);
		}
	}
}
