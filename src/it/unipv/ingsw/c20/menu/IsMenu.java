package it.unipv.ingsw.c20.menu;

import java.awt.Graphics;
import java.awt.event.MouseListener;

import it.unipv.ingsw.c20.system.Game;

/**
 * Abstract class that indicate a menu 
 * @author Filippo Tagliaferri
 *
 */

public abstract class IsMenu implements MouseListener{
	
	private Game game;
	
	/**
	 * Class creator 
	 * @param game This game to change and get parameter
	 */
	
	public IsMenu(Game game){
		this.game = game;
	}
	
	/**
	 * Game getter
	 * @return this game
	 */
	
	public Game getGame() {
		return game;
	}

	/**
	 * game's setter
	 * @param game setter
	 */
	
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Controls if the mouse is over the baloon
	 * 
	 * @param mx mouse's x
	 * @param my mouse's y
	 * @param x rectangle's beginning position
	 * @param y rectangle's beginning position
	 * @param width rectangle's width
	 * @param height rectangle's height
	 * @return the boolean value to confirm if the cursor is on the baloon
	 */
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		
		if(mx >x && mx< x+width){

			return my > y && my < y + height;
			
		}else{
			
			return false;
			
		}
		
	}
	
	/**
	 * tick method
	 */
	
	public abstract void tick();
	
	/**
	 * render method
	 * @param g
	 */
	
	public abstract void render(Graphics g);
}
