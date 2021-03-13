import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BigPoint extends Rectangle{
	
	public BigPoint(int x, int y){
		this.setBounds(x+16-8, y+16-8, 16, 16);
	}
	public void render(Graphics g){
		
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 16, 16);
		
	}
}

