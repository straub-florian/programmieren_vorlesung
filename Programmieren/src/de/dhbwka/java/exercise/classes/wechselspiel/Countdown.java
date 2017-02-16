package de.dhbwka.java.exercise.classes.wechselspiel;

/**
 * A simple count-down timer which offers basic kind of usage.
 * 
 * @author Florian Straub
 */
public class Countdown {

	/* *******************  Attributes  ******************* */
		private final double INITIAL_VALUE;
		
		private double timerValue;
		private boolean isRunning;
	/* ******************* /Attributes  ******************* */
	
	/**
	 * Creates a new count-down-timer with the given parameter as inital value
	 * 
	 * @param startValue
	 *            the intial value in seconds
	 */
	public Countdown(double startValue) {
		this.INITIAL_VALUE 	= startValue;
		this.timerValue 	= startValue;
	}
	
	/**
	 * Decrements the timer value by the variable timestep, given as parameter.
	 * 
	 * @param delta
	 *            the variable timestep
	 */
	public void update(double delta){
		// Only decrement if the timer is running
		if(isRunning){
			timerValue-= delta;
			if(timerValue <= 0){
				timerValue = 0;
				isRunning = false;
			}
		}
	}
	
	/**
	 * Starts the count-down process
	 */
	public void start(){
		isRunning = true;
	}
	
	/**
	 * Adds the given amount of seconds to the timer
	 * 
	 * @param seconds
	 *            the amount of seconds to add
	 */
	public void add(double seconds){
		if(seconds > 0)
			this.timerValue += seconds;
	}
	
	/**
	 * @return the current timerValue in seconds
	 */
	public double getTimerValue(){
		return timerValue;
	}

	/**
	 * Resets the countdown to the initial value
	 */
	public void reset() {
		isRunning = false;
		timerValue = INITIAL_VALUE;
	}

	/**
	 * @return if the countdown has hit "0" yet.
	 */
	public boolean hasFinished() {
		return timerValue <= 0;
	}
	
}
