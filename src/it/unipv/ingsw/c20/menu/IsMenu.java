package it.unipv.ingsw.c20.menu;

import java.awt.Graphics;
import java.awt.event.MouseListener;

import it.unipv.ingsw.c20.system.Game;

public abstract class IsMenu implements MouseListener{
	
	private Game game;
	
	public IsMenu(Game game){
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}

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
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
}
