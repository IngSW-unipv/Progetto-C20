package actors;

import system.Level;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public abstract class Actor extends Rectangle {

	private Level level;
	
	public Actor(int x, int y, Level level) {
		
		this.setBounds(x, y, 32, 32);
		this.level = level;

	}
	
	public abstract boolean canMove(int x, int y);
	public abstract void tick();
	public abstract void render(Graphics g);
}
