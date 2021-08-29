package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Spider extends Movable {
	private Random rand;
	private int size;  //size of spider
	private int color;  //color of spider
	private int worldHeight;
	private int worldWidth;
	
	/*
	 * Construct a spider.
	 * @param heading is the heading initialized for spider.
	 * @param speed is the speed initialized for spider.
	 * @param x is a float for location x of the spider.
	 * @param y is a float for location y of the spider.
	 */
	public Spider(int heading, int speed, float x, float y, int height, int width, GameWorld gw) {
		super(heading, speed, x, y);
		rand = new Random();
		size = rand.nextInt(11) + 10;  //random size between 30 and 50 -> 
		color = ColorUtil.BLACK;
		worldHeight = height;
		worldWidth = width;
	}

	@Override
	public void changeColor(int c) {}

	/*
	 * update location based on heading and speed.
	 * Random values added or subtracted to heading
	 * to move spider.
	 */
	@Override
	public void move(int time) {
		float oldX;
		float oldY;
		float deltaX;
		float deltaY;
		do {
			int headingAmount = rand.nextInt(11) - 5; //random number between -5 to 5
			super.setHeading(super.getHeading() + headingAmount);
			Point oldLocation = super.getLocation();
			oldX = oldLocation.getX();
			oldY = oldLocation.getY();
			deltaX = (float) (Math.cos(Math.toRadians(90 - super.getHeading()))*(super.getSpeed() + .10 * time));
			deltaY = (float) (Math.sin(Math.toRadians(90 - super.getHeading()))*(super.getSpeed() + .10 * time));
		} while (!isValidMove(oldX + deltaX, oldY + deltaY));  
		setLocation(oldX + deltaX, oldY + deltaY);
	}
	
	/*
	 * Checks to make sure spider does not leave boundary of world.
	 */
	private boolean isValidMove(float x, float y) {
		if ((x < 0 || x > worldWidth) || ( y < 0 || y > worldHeight)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public String toString() {
		Point loc = super.getLocation();
		String s = "Spider: loc = " + Math.round(loc.getX()*10.0)/10.0 + ", " + Math.round(loc.getY()*10.0)/10.0 + 
				" color = [" + ColorUtil.red(color) + ", " + ColorUtil.green(color) + ", " + ColorUtil.blue(color) 
				+ "] heading = " + super.getHeading() + " speed = " + super.getSpeed() + " size = " + size;
		return s;
	}

	//unfilled isosceles triangle 
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		Point loc = super.getLocation();
		int xCenter = (int) pCmpRelPrnt.getX() + (int) loc.getX();
		int yCenter = (int) pCmpRelPrnt.getY() + (int) loc.getY();
		int[] xPoints = {xCenter - size, xCenter + size, xCenter};
		int[] yPoints = {yCenter - size, yCenter - size, yCenter + size};
		g.setColor(color);
		g.drawPolygon(xPoints, yPoints, 3);
	}

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

	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {
		System.out.println("Collision with spider");
	}
}