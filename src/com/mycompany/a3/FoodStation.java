package com.mycompany.a3;

import java.util.Random;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class FoodStation extends Fixed {
	private Random rand;
	private int size;  //size of food station is random
	private int color;  //color of food station
	private int capacity;  //amount of food a station contains
	private boolean selected;  //if FoodStation is selected
	private int width;
	private int height;
	
	/*
	 * Constructs a FoodStation.
	 * @param x is a float representing x location of flag.
	 * @param y is a float representing y location of flag.
	 */
	public FoodStation(float x, float y, GameWorld gw) {
		super(x, y);
		rand = new Random();
		capacity = rand.nextInt(400) + 400; 
		color = ColorUtil.GREEN;
		size = capacity / 20;
		selected = false;
		width = 2 * size;
		height = 2 * size;
	}
	
	/*
	 * If ant collides with foodstation
	 */
	public void collision() {
		capacity = 0;
		color = ColorUtil.YELLOW;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	/*
	 * set food capacity
	 */
	public void setCapacity(int n) {
		capacity = n;
	}
	
	@Override
	public String toString() {
		Point loc = super.getLocation();
		String s = "FoodStation: loc = " + Math.round(loc.getX()*10.0)/10.0 + ", " + Math.round(loc.getY()*10.0)/10.0 + 
				" color = [" + ColorUtil.red(color) + ", " + ColorUtil.green(color) + ", " + ColorUtil.blue(color) + "] size = "
						+ size + " capacity = " + capacity;
		return s;
	}

	/*
	 * food stations are filled squares
	 */
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		Point loc = super.getLocation();
		int x = (int) (pCmpRelPrnt.getX() - size/2);  //get left hand corner from center of object
		int y = (int) (pCmpRelPrnt.getY() - size/2);
		int xCenter = (int) pCmpRelPrnt.getX() + (int) loc.getX();
		int yCenter = (int) pCmpRelPrnt.getY() + (int) loc.getY();
		g.setColor(color);
		if (isSelected()) 
			g.drawRect(x + (int) loc.getX(), y + (int) loc.getY(), width, height);
		else 
			g.fillRect(x + (int) loc.getX(), y + (int) loc.getY(), width, height);
		g.setColor(ColorUtil.BLACK);
		g.drawString(Integer.toString(capacity), xCenter, yCenter);
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
	public void handleCollision(GameObject otherObject, GameWorld gw) {}

	@Override
	public void setSelected(boolean b) {
		selected = b;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	/*
	 * If FoodStation contains user location selected 
	 * @return boolean if contains location
	 */
	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		Point loc = this.getLocation();
		int px = (int) pPtrRelPrnt.getX();
		int py = (int) pPtrRelPrnt.getY();
		int xLoc = (int) (pCmpRelPrnt.getX() + loc.getX());
		int yLoc = (int) (pCmpRelPrnt.getY() + loc.getY());
		
		if ((px >= xLoc) && (px <= xLoc + width) && (py >= yLoc) && (py <= yLoc + height)) {
			return true;
		} else {
			return false;
		}
	}
}
