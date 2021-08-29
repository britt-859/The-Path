package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Ant extends Movable implements ISteerable {
	//single global reference to the Ant for singleton design pattern
	private static Ant ant;
	
	private int size;  //size of ant
	private int color;  //color of ant
	private int maximumSpeed;  //upper limit of speed attribute
	private int foodLevel;  //food level, if 0 ant cannot move
	private double foodConsumptionRate;  //how much food level decreases each tick
	private int healthLevel;  //health of ant, decreases when ant collides with spider
	private int lastFlagReached;  //last flag reached for the ant
	private int green;  //color of ant changes as it gets hurt to lighter red
	private static int worldHeight;  //height of GameWorld
	private static int worldWidth;  //width of GameWorld
	
	/*
	 * Construct an Ant.
	 * @param x represents the initial x location of the ant.
	 * @param y represents the initial y location of the ant.
	 */
	private Ant(float x, float y, int worldHeight, int worldWidth) {
		super(0, 5, x, y);
		Ant.worldHeight = worldHeight;
		Ant.worldWidth = worldWidth;
		green = 0;
		size = 40;
		color = ColorUtil.rgb(255, 0, 0);
		maximumSpeed = 15;
		foodLevel = 1000;
		foodConsumptionRate = 2; 
		healthLevel = 10;
		lastFlagReached = 1;  //initially set to 1
	}
	
	/*
	 * Singleton pattern for ant
	 */
	public static Ant getAnt() {
		if (ant == null) {
			ant = new Ant(5, 10, worldHeight, worldWidth);  //ant will be placed at first flag which is x = 5, y = 10
		}
		return ant;
	}
	
	/*
	 * Reset ant when player loses a life
	 */
	public void resetAnt() {
		super.setHeading(0);
		super.setSpeed(5);
		super.setX(5);
		super.setY(10);
		green = 0;
		size = 40;
		color = ColorUtil.rgb(255, 0, 0);  //red
		maximumSpeed = 15;
		foodLevel = 1000;
		foodConsumptionRate = 2;
		healthLevel = 10;
		lastFlagReached = 1;
	}
	
	/*
	 * Activated when spider collides with ant
	 */
	public void collision() {
		healthLevel--;
		if (healthLevel < 10) {
			float speedPercentage = (float) (healthLevel * 10) / 100;  
			maximumSpeed = (int) (speedPercentage * maximumSpeed);  //max speed changes as ant takes damage
			if (super.getSpeed() > maximumSpeed)
				super.setSpeed(maximumSpeed);   //if the ants speed is over the max speed, decrease it to the max speed
			green+= 20;
			color = ColorUtil.rgb(255, green, 17);  //ant's color gets lighter red as it takes damage
		}
	}
	
	@Override
	public void changeHeading(int n) {
		super.setHeading(super.getHeading() + n);
	}

	public int getMaxSpeed() {
		return maximumSpeed;
	}
	
	public int getHealth() {
		return healthLevel;
	}
	
	/*
	 * return last reached flag
	 */
	public int getFlag() {
		return lastFlagReached;
	}
	
	/*
	 * set last flag reached
	 */
	public void setFlag(int n) {
		lastFlagReached = n;
	}
	
	/*
	 * increase the ant's food level
	 */
	public void increaseFoodLevel(int n) {
		foodLevel+= n;
	}
	
	/*
	 * Decrease ant's food level by the food consumption rate
	 */
	public void decreaseFoodLevel() {
		foodLevel-= foodConsumptionRate;
	}
	
	public int getFoodLevel() {
		return foodLevel;
	}

	/* 
	 * update location of ant based on heading, speed and time passed
	 */
	@Override
	public void move(int time) {
		float oldX;
		float deltaX;
		float oldY;
		float deltaY;
		Point oldLocation = super.getLocation();
		oldX = oldLocation.getX();
		oldY = oldLocation.getY();
		deltaX = (float) (Math.cos(Math.toRadians(90 - super.getHeading()))*(super.getSpeed() + .10 * time));
		deltaY = (float) (Math.sin(Math.toRadians(90 - super.getHeading()))*(super.getSpeed() + .10 * time));
		setLocation(oldX + deltaX, oldY + deltaY);
	}
		
	@Override 
	public String toString() {
		Point loc = super.getLocation();
		String s = "Ant: loc = " + Math.round(loc.getX()*10.0)/10.0 + ", " + Math.round(loc.getY()*10.0)/10.0 + 
				" color = [" + ColorUtil.red(color) + ", " + ColorUtil.green(color) + ", " + ColorUtil.blue(color) + "] heading = " + 
				super.getHeading() + " speed = " + super.getSpeed() + " size = " + size + "\n maxSpeed = " 
				+ maximumSpeed + " foodConsumptionRate = " + foodConsumptionRate;
		return s;
	}

	/*
	 * ant represented by filled circle
	 */
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		Point loc = super.getLocation();
		int x = (int) (pCmpRelPrnt.getX() - size/2);  //get left hand corner from center of object
		int y = (int) (pCmpRelPrnt.getY() - size/2);
		g.setColor(color);
		g.fillArc(x + (int) loc.getX(), y + (int) loc.getY(), 2*size, 2*size, 0, 360);
	}

	/*
	 * Check if ant collided with any objects
	 */
	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = false;
		Point loc = this.getLocation();
		Point otherLoc = otherObject.getLocation();
		int thisCenterX = (int) (loc.getX() + (size/2));
		int thisCenterY = (int) (loc.getY() + (size/2));
		int otherCenterX = (int) (otherLoc.getX() + (otherObject.getSize()/2));
		int otherCenterY = (int) (otherLoc.getY() + (otherObject.getSize()/2));
		
		int dx = thisCenterX - otherCenterX;
		int dy = thisCenterY - otherCenterY;
		int distBetweenCentersSqr = (dx*dx + dy*dy);
		
		int thisRadius = size/2;
		int otherRadius = otherObject.getSize()/2;
		int radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);
		if (distBetweenCentersSqr <= radiiSqr) {
			result = true;
		}
		return result;
	}
	
	/*
	 * Handle ant collision with object
	 */
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		if (otherObject instanceof Spider) {
			gw.gotten((Spider) otherObject);
		} else if (otherObject instanceof Flag) {
			gw.collide(((Flag) otherObject).getSeqNum());
		} else {
			//FoodStation
			gw.food((FoodStation) otherObject);
		}
	}
}