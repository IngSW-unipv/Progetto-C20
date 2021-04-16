package it.unipv.ingsw.c20.point;

import java.awt.*;

/**
 * Class for the creation of normal point
 * 
 * @author Enxhi Ismaili, Carlos Reynaga
 *
 */
public class LilPoint extends Point{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creator of little points
	 * @param x (+16-4) to center the point (32/2 + half the width)
	 * @param y (+16-4) to center the point (32/2 + half the height)
	 */
	
	public LilPoint(int x, int y){
		super(x+16-4, y+16-4, 8, 8);
		
	}
	
	/**
	 * Render this point
	 * @param g graphics
	 */
	public void render(Graphics g){
		
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, width, height);
		
	}

	/**
	 * Tick of this point
	 */
	public void tick() {
		
	}
	
}
