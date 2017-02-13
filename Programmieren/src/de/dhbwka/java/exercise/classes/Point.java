package de.dhbwka.java.exercise.classes;

public class Point {

	private double x, y;

	public Point() {
		this.x = this.y = 0;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point getDistance(Point otherPoint) {
		return new Point((x - otherPoint.x), (y - otherPoint.y));
	}
	
	public double getLength() {
		return Math.sqrt((x)*(x) + (y)*(y));
	}
	
	public Point mirror(){
		return new Point(-x,-y);
	}
	
	public Point mirrorX(){
		return new Point(-x,y);
	}
	
	public Point mirrorY(){
		return new Point(x,-y);
	}
	
	public String toString(){
		return String.format("Point[%2.1f;%2.1f] - Length: %2.3f", x,y,getLength());
	}
}
