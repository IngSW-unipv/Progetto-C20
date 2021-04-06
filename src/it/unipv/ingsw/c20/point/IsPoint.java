package it.unipv.ingsw.c20.point;

import java.awt.*;

/**
 * Interface class for creation of any kind of points
 * 
 * @author Enxhi Ismaili, Carlos Reynaga
 *
 */

public interface IsPoint{
	
	/**
	 * Each class need a tick
	 */
	
	public void tick();
	
	/**
	 * Each class need a render
	 * @param g graphics
	 */
	
	public void render(Graphics g);

}
