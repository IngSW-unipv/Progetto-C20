package it.unipv.ingsw.c20.point;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class IsPoint extends Rectangle {

	private static final long serialVersionUID = 1L;
	
	public abstract void tick();
	public abstract void render(Graphics g);
}
