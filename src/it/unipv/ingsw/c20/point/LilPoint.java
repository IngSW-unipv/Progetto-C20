package it.unipv.ingsw.c20.point;

import java.awt.*;

public class LilPoint extends Point{
	
	private static final long serialVersionUID = 1L;

	public LilPoint(int x, int y){
		super(x, y);
		this.setBounds(x+16-4, y+16-4, 8, 8);
		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 8, 8);
		
	}

	public void tick() {
		
	}
	
}
