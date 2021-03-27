package points;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import graphic.*;
import actors.*;
import points.*;
import system.*;

import javax.swing.ImageIcon;

public class BigPoint extends Rectangle{
	
	public BigPoint(int x, int y){
		
		this.setBounds(x+16-8, y+16-8, 16, 16);
		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.YELLOW);
		g.drawImage(new ImageIcon("res/players/apple.png").getImage(), this.x-8, this.y-8, null);
		
	}
	
}
