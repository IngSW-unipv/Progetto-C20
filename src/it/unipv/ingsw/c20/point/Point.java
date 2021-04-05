package it.unipv.ingsw.c20.point;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Point extends Rectangle implements IsPoint {

	private static final long serialVersionUID = 1L;
	
	Point(int x, int y){
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}
