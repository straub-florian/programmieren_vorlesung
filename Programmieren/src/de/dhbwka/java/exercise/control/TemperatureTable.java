package de.dhbwka.java.exercise.control;

public class TemperatureTable {

	public static void main(String[] args) {
		System.out.println("Fahrenheit | Celsius");
		System.out.println("-----------+--------");
		for (int i = 0; i <= 300; i++) {
			System.out.println(String.format("%03d        | %3.2f", i, (5f / 9f) * (i - 32f)));
		}
	}
}
