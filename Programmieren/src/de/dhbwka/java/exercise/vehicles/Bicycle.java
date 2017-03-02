package de.dhbwka.java.exercise.vehicles;

public class Bicycle extends Vehicle{

	private final static int TIRES = 2;
	private final static int V_MAX = 30;
	
	public Bicycle(double velocity) {
		super(TIRES, V_MAX, velocity);
	}

}
