package de.dhbwka.java.exercise.classes;

public class Radio {

	private boolean on;
	/** 0-10 */
	private int volume;
	/** 85.0 - 110.0 */
	private double frequency;

	public Radio() {
		this.on = false;
		this.volume = 3;
		this.frequency = 101.8f;
	}

	public Radio(boolean on, int volume, double frequency) {
		this.on = on;
		this.volume = volume;
		this.frequency = frequency;
	}

	public void incVolume() {
		if(volume<10)
			volume++;
	}

	public void decVolume() {
		if(volume>0)
			volume--;
	}

	public void turnOn() {
		on = true;
	}

	public void turnOff() {
		on = false;
	}

	public void setFrequency(double frequency) {
		if(frequency >= 85 && frequency <= 110)
			this.frequency = frequency;
		else
			this.frequency = 99.9;
	}

	public String toString() {
		return String.format("Radio %s: \n\tFreq = %2.1f\n\tLautstärke = %2d", on ? "an" : "aus", frequency, volume);
	}
}
