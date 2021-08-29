package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;

public class MapView extends Container implements Observer {
	private GameWorld gw;
	private GameObjectCollection theWorld;
	private boolean positionClicked;

	public MapView(GameWorld gw) {
		this.gw = gw;  //reference to game world
		theWorld = gw.getCollection();
		positionClicked = false;
	}
	
	@Override
	public void update(Observable observable, Object data) {
		((GameWorld) observable).map();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Point pCmpRelPrnt = new Point(getX(), getY());
		IIterator itr = theWorld.getIterator();
		while (itr.hasNext()) {
			GameObject obj = (GameObject)itr.getNext();
			if (!gw.isPaused()) {
				if (obj instanceof ISelectable) {
					((ISelectable) obj).setSelected(false);
				}
			}
			obj.draw(g, pCmpRelPrnt);
		}
	}
	
	/*
	 * Activated if user clicks on position button.
	 */
	public void positionClicked() {
		positionClicked = !positionClicked;
	}
	
	/*
	 * Pointer pressed event takes place on MapView
	 */
	@Override
	public void pointerPressed(int x, int y) {
		IIterator itr = theWorld.getIterator();
		//if the game is paused and the user has not clicked position button
		if (gw.isPaused() && !positionClicked) {
			x = x - getParent().getAbsoluteX();
			y = y - getParent().getAbsoluteY();
			Point pPtrRelPrnt = new Point(x, y);
			Point pCmpRelPrnt = new Point(getX(), getY());
			while (itr.hasNext()) {
				GameObject obj = (GameObject)itr.getNext();
				if (obj instanceof ISelectable) {
					if (((ISelectable) obj).contains(pPtrRelPrnt, pCmpRelPrnt)) {
						((ISelectable) obj).setSelected(true);
					} else {
						((ISelectable) obj).setSelected(false);
					}
				}
			}
		//if game is paused and user has clicked position button
		} else {
			if (gw.isPaused() && positionClicked) {
				while (itr.hasNext()) {
					GameObject obj = (GameObject)itr.getNext();
					if (obj instanceof ISelectable) {
						if (((ISelectable) obj).isSelected()) {
							obj.setLocation(x - getX(), y - getY() - 120);
						}
					}
				}
				positionClicked();
			}
		}
		repaint();
	}
}
