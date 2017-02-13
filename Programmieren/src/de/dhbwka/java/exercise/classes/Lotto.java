package de.dhbwka.java.exercise.classes;

import java.util.Random;
import java.util.Scanner;

import de.dhbwka.java.exercise.arrays.BubbleSort;

@SuppressWarnings("resource")
public class Lotto {

	private int amountToChoose, totalAmount;
	private int[] guess, values;

	public Lotto(int amountToChoose, int totalAmount) {
		this.amountToChoose = amountToChoose;
		this.totalAmount = totalAmount;
		
		this.guess  = new int[amountToChoose];
		this.values = new int[amountToChoose];
	}

	public void guess(){
		Scanner scanner = new Scanner(System.in);
		for(int i = 0; i < amountToChoose; i++){
			System.out.printf("Geben Sie ihren Tipp für die %1d. Zahl ein: ",i+1);
			int guessedValue = scanner.nextInt();
			while(!isInRange(guessedValue)){
				System.out.printf("Außerhalb der gültigen Zahlen! Bitte Tipp für die %1d. Zahl eingeben: ",i+1);
				guessedValue = scanner.nextInt();
			}
			guess[i] = guessedValue;
		}
	}

	public void guess(int[] guessed){
		if(guessed.length != amountToChoose){			
			System.out.printf("Sie haben zu wenig Zahlen angeben!");
		}else{			
			for(int i = 0; i < amountToChoose; i++){
				if(!isInRange(guessed[i])){
					System.out.printf("Eine ihrer angegebnen Zahlen ist außerhalb der Reichweite!");
					break;
				}
			}
			for(int i = 0; i < amountToChoose; i++){
				guess[i] = guessed[i];
			}
		}
	}
	
	public void pick(){
		Random rand = new Random();
		for(int i = 0; i < amountToChoose; i++){
			values[i] = 1+rand.nextInt(49);
		}
	}
	
	private int correct(){
		int correct = 0;
		for(int i = 0; i < values.length; i++){
			if(values[i] == guess[i])
				correct++;
		}
		return correct;
	}
	
	private boolean isInRange(int number){
		return number > 0 && number <= totalAmount;
	}
	
	public int getAmountToChoose() {
		return amountToChoose;
	}

	public int getTotalAmount() {
		return totalAmount;
	}
	
	public String toString(){
		String ret  = "";
		boolean guessed = guess != null  && guess[0] > 0;
		boolean picked  = values != null && values[0] > 0;
		
		if(guessed){
			// Sortiere Zahlen
			BubbleSort.sort(guess);
			ret+=System.lineSeparator();
			ret += ("Deine geratenen Zahlen:"+System.lineSeparator());
			for(int i = 0; i < guess.length; i++){
				ret += String.format("\t%1d. %1d"+System.lineSeparator(),i+1,guess[i]);
			}
		}
		if(picked){
			// Sortiere Zahlen
			BubbleSort.sort(values);
			ret+=System.lineSeparator();
			ret += ("Die gezogenen Zahlen:"+System.lineSeparator());
			for(int i = 0; i < values.length; i++){
				ret += String.format("\t%1d. %1d"+System.lineSeparator(),i+1,values[i]);
			}
			
		}
		if(guessed && picked){
			ret+=System.lineSeparator();			
			ret += ("Richtige: " + correct());
		}
		
		return ret;
	}
}
