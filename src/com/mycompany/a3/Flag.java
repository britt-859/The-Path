package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Flag extends Fixed {
	private int size;  //size of flag
	private int color;  //color of flag
	private int sequenceNumber;  //sequence number of flag
	private boolean selected;
	
	/*
	 * Constructs a Flag.
	 * @param n is an integer representing the flag sequence number.
	 * @param x is a float representing the x location of the flag.
	 * @param y is a float representing the y location of the flag.
	 */
	public Flag(int n, float x, float y, GameWorld gw) {
		super(x, y);  //sets initial location of the flag 
		size = 30;
		color = ColorUtil.LTGRAY;
		sequenceNumber = n;
		selected = false;
	}
	
	public int getSeqNum() {
		return sequenceNumber;
	}

	@Override
	public void changeColor(int c) {}  //cannot change color once created
	
	@Override
	public String toString() {
		Point loc = super.getLocation();
		String s = "Flag: loc = " + Math.round(loc.getX()*10.0)/10.0 + ", " + Math.round(loc.getY()*10.0)/10.0 + 
				" color = [" + ColorUtil.red(color) + ", " + ColorUtil.green(color) + ", " + ColorUtil.blue(color) 
				+ "] size = " + size + " seqNum = " + sequenceNumber;
		return s;
	}

	/*
	 * Filled isosceles triangles
	 */
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		Point loc = super.getLocation();
		int xCenter = (int) pCmpRelPrnt.getX() + (int) loc.getX();
		int yCenter = (int) pCmpRelPrnt.getY() + (int) loc.getY();
		int[] xPoints = {xCenter - size, xCenter + size, xCenter};
		int[] yPoints = {yCenter - size, yCenter - size, yCenter + size};
		g.setColor(color);
		if (isSelected())
			g.drawPolygon(xPoints, yPoints, 3);
		else 
			g.fillPolygon(xPoints, yPoints, 3);
		g.setColor(ColorUtil.BLACK);
		g.drawString(Integer.toString(sequenceNumber), xCenter, yCenter - size/2);
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
		System.out.println("Collision with Flag");
	}

	@Override
	public void setSelected(boolean b) {
		selected = b;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	/*
	 * If Flag contains user location selected 
	 * @return boolean if contains location
	 */
	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int width = size;
		int height = size;
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