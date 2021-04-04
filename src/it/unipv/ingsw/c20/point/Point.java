package it.unipv.ingsw.c20.point;

import java.awt.*;

public class Point extends IsPoint{
	
	public Point(int x, int y){
		
		this.setBounds(x+16-4, y+16-4, 8, 8);
		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 8, 8);
		
	}

	public void tick() {
		
	}
	
}
