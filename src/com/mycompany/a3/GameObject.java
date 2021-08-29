package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class GameObject implements IDrawable, ICollider{
	private int size;  //length of object's shape's bounding square
	private Point location;  //location of game object
	private int color;
	
	/*
	 * Constructs a game object.
	 * Accepts a float x and a float y to
	 * set the game object's location 
	 */
	public GameObject(float x, float y) {
		location = new Point();
		location.setX(x);
		location.setY(y);
	}
	
	public int getSize() {
		return size;
	}
	
	public void setX(float x) {
		location.setX(x);
	}
	
	public void setY(float y) {
		location.setY(y);
	}
	
	public Point getLocation() {
		return location;
	}
	
	public int getColor() {
		return color;
	}
	
	public void changeColor(int c) {
		color = c;
	}
	
	public void setLocation(float x, float y) {
		location.setX(x);
		location.setY(y);
	}
}
