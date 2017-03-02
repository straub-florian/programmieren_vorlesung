package de.dhbwka.java.exercise.vehicles;

public class Ambulance extends Car {

	private boolean blueLight = false;

	public Ambulance(double velocity, boolean blueLight) {
		super(velocity);
		this.blueLight = blueLight;
	}

	public boolean isBlueLight() {
		return blueLight;
	}

	public void setBlueLight(boolean blueLight) {
		this.blueLight = blueLight;
	}

	public String toString() {
		return super.toString() + System.lineSeparator() + String.format("\t Signal %s", blueLight ? "on" : "off");
	}

	
	
}
