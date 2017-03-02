package de.dhbwka.java.exercise.vehicles;

public class Vehicle {

	private int tires = 0;
	private double v_max = 0;
	private double velocity = 0;
	private double position = 0;
	
	public Vehicle(int tires, double v_max, double velocity) {
		this.tires = tires;
		this.v_max = v_max;
		this.velocity = velocity;
	}
	
	public void drive(double minutes){
		if(velocity != 0){
			position += velocity * minutes/60.;
		}
	}

	public void setSpeed(double speed) {
		if(speed > v_max)
			this.velocity = v_max;
		else 
			velocity = speed;
	}

	public String toString() {
		String format = System.lineSeparator() + "\t";
		return String.format("Vehicle: [%s] at position: %.2f km %s %1d tire(s)%s v_max=%.3f km/h %s velocity=%.3fkm/h", getClass().getSimpleName(),position,format,tires,format,v_max,format,velocity);
	}
}
