package com.mycompany.a3;

public abstract class Movable extends GameObject {
	private int heading;  //direction of object
	private int speed;  //speed of object
	
	/*
	 * Constructs a Movable object.
	 * @param h is the integer value for the heading.
	 * @param s is the integer value for the speed.
	 * @param x is the float value representing the location x.
	 * @param y is the float value representing the location y.
	 */
	public Movable(int h, int s, float x, float y) {
		super(x, y);  //set location
		heading = h;
		speed = s;
	}
		
	public int getSpeed() {
		return speed;
	}
	
	public int getHeading() {
		return heading;
	}
	
	public void setHeading(int n) {
		heading = n;
	}
	
	public void increaseSpeed() {
		speed++;
	}
	
	public void decreaseSpeed() {
		speed--;
	}
	
	public void setSpeed(int n) {
		speed = n;
	}
	
	public abstract void move(int time);
}
