package com.mycompany.a3;

public abstract class Fixed extends GameObject implements ISelectable {
	
	/*
	 * Constructs a fixed game object.
	 * @param x is the float value representing location x.
	 * @param y is the float value representing location y.
	 */
	public Fixed(float x, float y) {
		super(x, y);
	}
}
