package graphic;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import graphic.*;
import actors.*;
import points.*;
import system.*;

public class Tile extends Rectangle{
	
	public Tile(int x, int y){
		
		this.setBounds(x, y, 32, 32);
		
	}
	
	public void Render(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(new Color(0,128,255));
		g2d.drawLine(x, y, x, y + 32 - 1);
		g2d.drawLine(x, y, x + 32 - 1, y);
		g2d.drawLine(x + 32 - 1, y, x + 32 - 1, y + 32 - 1);
		g2d.drawLine(x, y + 32 - 1, x + 32 - 1, y + 32 - 1);

	}
	public void tick(){
		
	}
	
}
