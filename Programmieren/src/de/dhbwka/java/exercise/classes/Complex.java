package de.dhbwka.java.exercise.classes;

public class Complex {

	private double real, imag;

	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	public Complex add(Complex c) {
		double real = this.real + c.getReal();
		double imag = this.imag + c.getImag();
		return new Complex(real, imag);
	}

	public Complex sub(Complex c) {
		double real = this.real - c.getReal();
		double imag = this.imag - c.getImag();
		return new Complex(real, imag);
	}

	public Complex mult(Complex c) {
		double real = (this.real * c.getReal()) - (this.imag * c.getImag());
		double imag = (this.real * c.getImag()) + (this.imag * c.getReal());
		return new Complex(real, imag);
	}

	public Complex div(Complex c) {
		double div = ((c.getReal() * c.getReal()) + (c.getImag() * c.getImag()));
		double real = (this.real * c.getReal() + this.imag * c.getImag()) / div;
		double imag = (this.imag * c.getReal() - this.real * c.getImag()) / div;
		return new Complex(real, imag);
	}

	public double getMagnitude(){
		return Math.sqrt((real * real) + (imag * imag));
	}
	
	public boolean isLess(Complex c){
		return getMagnitude() < c.getMagnitude();
	}
	
	public double getReal() {
		return real;
	}

	public double getImag() {
		return imag;
	}
	
	public String toString(){
		return String.format("Complex %3.2f + %3.2f * i   Betrag: %3.2f", real,imag, getMagnitude());
	}
}
