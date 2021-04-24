package it.unipv.ingsw.c20.map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Class for the creation of the tiles that are going to form the game's wall
 * 
 * @author Enxhi Ismaili, Carlos Reynaga
 *
 */

public class Tile extends Rectangle{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Class creator of this tiles
	 * @param x of this tile
	 * @param y of this tile
	 */
	
	public Tile(int x, int y){
		
		this.setBounds(x, y, 32, 32);
		
	}
	
	/**
	 * Render of this tile
	 * @param g graphics
	 */
	
	public void Render(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLUE);
		g.fillRect(x, y, 32, 32);
		g.setColor(new Color(0,128,255));
		g2d.drawLine(x, y, x, y + 32 - 1);
		g2d.drawLine(x, y, x + 32 - 1, y);
		g2d.drawLine(x + 32 - 1, y, x + 32 - 1, y + 32 - 1);
		g2d.drawLine(x, y + 32 - 1, x + 32 - 1, y + 32 - 1);

	}
	
	/**
	 * Tick of the tile 
	 */
	
	public void tick(){
		
	}
	
}
