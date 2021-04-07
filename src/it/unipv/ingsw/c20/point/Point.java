package it.unipv.ingsw.c20.point;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Abstract class for the creation of points
 * 
 * @author Enxhi Ismaili, Carlos Reynaga
 *
 */
public abstract class Point extends Rectangle implements IsPoint {

	private static final long serialVersionUID = 1L;

	/**
	 * Abstract and standard creator for any points
	 * @param x of this point
	 * @param y of this point
	 * @param width of this point
	 * @param height of this point
	 */
	Point(int x, int y, int width, int height){
		this.setBounds(x, y, width, height);
	}
	/**
	 * Each class need a tick
	 */
	public abstract void tick();
	/**
	 * Each class need a render
	 * @param g graphics
	 */
	public abstract void render(Graphics g);
}
