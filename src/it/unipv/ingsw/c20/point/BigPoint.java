package it.unipv.ingsw.c20.point;

import javax.swing.*;
import java.awt.*;

public class BigPoint extends IsPoint{
	
	private static final long serialVersionUID = 1L;

	public BigPoint(int x, int y){
		
		this.setBounds(x+16-8, y+16-8, 16, 16);
		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.YELLOW);
		g.drawImage(new ImageIcon("res/players/apple.png").getImage(), this.x-8, this.y-8, null);
		
	}

	public void tick() {
		
	}
	
}

