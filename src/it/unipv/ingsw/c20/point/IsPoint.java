package it.unipv.ingsw.c20.point;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class IsPoint extends Rectangle {

	public abstract void tick();
	public abstract void render(Graphics g);
}
