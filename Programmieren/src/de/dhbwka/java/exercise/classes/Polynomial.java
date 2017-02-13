package de.dhbwka.java.exercise.classes;

public class Polynomial {

	private double a, b, c;

	/**
	 * Function f(x)=ax^2+bx+c, uses default values for a,b,c (1,1,1)
	 */
	public Polynomial() {
		this.a = 1;
		this.b = 1;
		this.c = 1;
	}

	/**
	 * Function f(x)=ax^2+bx+c, uses given parameters
	 * 
	 * @param a
	 *            specific value
	 * @param b
	 *            specific value
	 * @param c
	 *            specific value
	 */
	public Polynomial(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Returns the corresponding value
	 * 
	 * @param x
	 *            the parameter of the function f(x)=ax^2+bx+c
	 */
	public double getValue(double x) {
		return a * (x * x) + b * (x) + c;
	}

	/**
	 * Adds two polynomial together
	 * 
	 * @return A new polynomial
	 */
	public static Polynomial add(Polynomial p1, Polynomial p2) {
		Polynomial p = new Polynomial(p1.getA() + p2.getA(), p1.getB() + p2.getB(), p1.getC() + p2.getC());
		return p;
	}

	/**
	 * Adds another polynomial to this one
	 * 
	 * @return A new polynomial
	 */
	public Polynomial add(Polynomial other) {
		Polynomial p = new Polynomial(getA() + other.getA(), getB() + other.getB(), getC() + other.getC());
		return p;
	}

	/**
	 * Multiplies A,B,C of the given polynominal with the given scalar value
	 * 
	 * @param scalar
	 *            The scalar value to multiply with
	 * @param p
	 *            The Polynominal
	 * @return A new polynomial
	 */
	public static Polynomial multiply(double scalar, Polynomial p) {
		Polynomial pn = new Polynomial(scalar * p.getA(), scalar * p.getB(), scalar * p.getC());
		return pn;
	}

	/**
	 * Multiplies A,B,C of this polynominal with the given scalar value
	 * 
	 * @param scalar
	 *            The scalar value to multiply with
	 * @param p
	 *            The Polynominal
	 * @return A new polynomial
	 */
	public Polynomial multiply(double scalar) {
		return multiply(scalar, this);
	}
	
	/**
	 * Returns the zeros of a polynominal
	 * @param p The polynominal
	 * @return A double array that contains all zeros
	 */
	public static double[] zeros(Polynomial p){
		double[] zeros = null;
		if (p.getA() == 0) {
			if (p.getB() == 0) {
				zeros = new double[0];
			} else {
				zeros = new double[]{(-p.getC() / p.getB())};
			}
		} else {
			double d = (p.getB()*p.getB()) - (4 * p.getA() * p.getC());
			if (d >= 0) {
				double x1 = ((-p.getB()) + Math.sqrt(d)) / (2 * p.getA());
				double x2 = ((-p.getB()) - Math.sqrt(d)) / (2 * p.getA());
				zeros = new double[]{x1,x2};
			} else {
				zeros = new double[0];
			}
		}
		return zeros;
	}

	/**
	 * Returns the zeros of a this polynominal
	 * @param p The polynominal
	 * @return A double array that contains all zeros
	 */
	public double[] zeros(){
		return zeros(this);
	}
	
	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public double getC() {
		return c;
	}

	public String toString() {
		return String.format("Function: f(x) = (%3.2fx²) + (%3.2fx) + (%3.2f)", a,b,c);
	}

}
