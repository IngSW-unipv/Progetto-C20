package actors;

import system.Level;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;


public abstract class Actor extends Rectangle {

	public int x, y;
	public int posizione;
	private Level level;
	
	public Actor(int x, int y, int i, Level level) {
		
		this.x = x;
		this.y = y;
		this.posizione = i;
		this.level = level;
		setBounds(x, y, 32, 32);

	}
}
