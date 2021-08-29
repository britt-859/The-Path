package com.mycompany.a3;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {
	private ArrayList<Object> theCollection;
	
	/*
	 * Construct a collection of Game objects
	 * using an array list.
	 */
	public GameObjectCollection() {
		theCollection = new ArrayList<>();
	}

	@Override
	public void add(Object newObject) {
		theCollection.add(newObject);
	}

	@Override
	public IIterator getIterator() {
		return new GameObjectIterator(); 
	}
	
	//clear all elements in collection to reset
	public void clearCollection() {
		theCollection.clear();
	}
	
	/*
	 * Removes an object from the collection
	 */
	public boolean remove(Object o) {
		for (int i = theCollection.size()-1; i >= 0; i--) {
			if (theCollection.get(i).equals(o)) {
				theCollection.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Iterator pattern
	 */
	private class GameObjectIterator implements IIterator {
		private int currentElement;
		
		public GameObjectIterator() {
			currentElement = -1;
		}
		
		@Override
		public boolean hasNext() {
			if (theCollection.size() == 0) {
				return false;
			}
			if (currentElement == theCollection.size() - 1) {
				return false;
			}
			return true;
		}

		@Override
		public Object getNext() {
			currentElement++;
			return (theCollection.get(currentElement));
		}
	}
}