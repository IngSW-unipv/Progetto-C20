package it.unipv.ingsw.c20.actor;

import it.unipv.ingsw.c20.system.Level;

import java.awt.*;


public abstract class Actor extends Rectangle {

	private static final long serialVersionUID = 1L;
	
	private Level level;
	
	public Actor(int x, int y, Level level) {
		
		this.setBounds(x, y, 32, 32);
		this.level = level;

	}
	
	public abstract boolean canMove(int x, int y);
	public abstract void tick();
	public abstract void render(Graphics g);
}
