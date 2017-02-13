package de.dhbwka.java.exercise.classes;

public class Horner {

	private double[] values;

	/**
	 * Polynom x-ten Grades Beispiel: ax^4+bx^3+cx^2+dx+e
	 * 
	 * @param values
	 */
	public Horner(double... values) {
		this.values = values;
	}

	public double getValue(double x) {
		double result = 0;
		for(int i = 0; i < values.length; i++){
			result = values[i] + (x * result);
		}
		return result;
	}

	public String toString() {
		String ret = "";
		for (int i = values.length - 1; i > 0; i--) {
			ret += String.format("%1.2f + x * " + (i > 1 ? "(" : String.format("%1.2f", values[i - 1])), values[i]);
		}
		for (int i = 0; i < values.length - 1; i++) {
			ret += ")";
		}
		return ret;
	}

}
