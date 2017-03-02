package de.dhbwka.java.exercise.vehicles;

public class Car extends Vehicle{

	private final static int TIRES = 4;
	private final static int V_MAX = 140;
	private final static int V_MAX_RACING = 220;
	
	public Car(double velocity) {
		super(TIRES, V_MAX, velocity);
	}
	
	protected Car(boolean isRacingCar, double velocity) {
		super(TIRES, isRacingCar ? V_MAX_RACING : V_MAX, velocity);
	}

}
