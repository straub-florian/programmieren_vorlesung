package de.dhbwka.java.exercise.control;

public class Deers {

	public static void main(String[] args) {
		int hirsche = 200;
		int jahr = 0;
		while (hirsche < 300) {
			hirsche -= 15;
			hirsche *= 1.15;
			if(hirsche > 300){	
				System.out.printf("Ende des %2d. Jahres würde die Hirschpopulation mehr als 300 Hirsche(%3d) betragen.", jahr, hirsche);
				break;
			}
			System.out.println(String.format("Ende des %2d Jahres: %3d Hirsche", jahr, hirsche));
			jahr++;
		}
	}
}
