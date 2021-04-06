package it.unipv.ingsw.c20.point;

import javax.swing.*;
import java.awt.*;

/**
 * Class for the creation of fruit point
 * 
 * @author Enxhi Ismaili, Carlos Reynaga
 *
 */

public class BigPoint extends Point{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creator of this fruit points
	 * @param x (+16-8) to center the point (32/2 + half the width)
	 * @param y (+16-8) to center the point (32/2 + half the height)
	 */
	
	public BigPoint(int x, int y){
		super(x+16-8, y+16-8, 16, 16);
		
	}
	

	/**
	 * Render this fruit point 
	 * @param g graphics
	 */
	
	public void render(Graphics g){
		
		g.drawImage(new ImageIcon("res/players/apple.png").getImage(), this.x-8, this.y-8, null);
		
	}

	/**
	 * Tick this fruit point 
	 */
	
	public void tick() {
		
	}
	
}

