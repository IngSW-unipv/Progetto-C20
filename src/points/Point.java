package points;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Point extends Rectangle{
	
	public Point(int x, int y){
		
		this.setBounds(x+16-4, y+16-4, 8, 8);
		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 8, 8);
		
	}
	
}
