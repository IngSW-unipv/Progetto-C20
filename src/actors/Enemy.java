package actors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;
import system.Level;

public class Enemy extends Player{
	private int random = 0, smart = 1;
	private int state = random;
	private int left = 0, right = 1, up =2, down = 3;
	
	private int dir = -1;
	
	public int speed = 2;
	
	private Random r;
	private Level level;
	
	public Enemy(int x, int y, int l, Level level){
		
		super(x, y, level, false, 4);
		this.level = level;
		r = new Random();
		this.setBounds(x, y, 32, 32);
		dir = r.nextInt(4);
		this.speed = l;
		
	}
	
	public boolean canMove(int nextX, int nextY){
		
		Rectangle bounds = new Rectangle(nextX, nextY, this.width,this.height);
		
		for(int xx = 0; xx < level.getTiles().length; xx++){
			
			for(int yy = 0; yy < level.getTiles()[0].length; yy++){
				
				if(level.getTiles()[xx][yy] != null){
					
					if(bounds.intersects(level.getTiles()[xx][yy])){
						
						return false;
						
					}
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	public void tick(){
		
		if(state == random){
			
			if (dir == left){
				
				if(canMove(x - speed,y)){
					
					x -= speed;
					
				}else{
					
					dir = r.nextInt(4);
					
				}
				
			}
			
			if(dir == right){
				
				if(canMove(x + speed,y)){
					
					x += speed;
					
				}else{
					
					dir = r.nextInt(4);
					
				}
				
			}
			
			if(dir == up){
				
				if(canMove(x,y - speed)){
					
					y -= speed;
					
				}else{
					
					dir = r.nextInt(4);
					
				}
				
			}
			
			if(dir == down){
				
				if(canMove(x,y + speed)){
					
					y += speed;
					
				}else{
					
					dir = r.nextInt(4);
					
				}
				
			}
			
		}else if (state == smart){
			
		}
		
	}
	
	public void render(Graphics g){
		
		if(this.level.getTurno().isKill()){
			
			g.setColor(Color.WHITE);
			g.drawImage(new ImageIcon("res/players/fantasma5.png").getImage(), this.x, this.y, null);
			String time = Integer.toString(3 - ((int)((System.currentTimeMillis() - this.level.getStart())/1000)));
			g.drawString(time, x + 16, y+16);
			
		}else{
			
			g.setColor(Color.ORANGE);
			
			g.drawImage(new ImageIcon("res/players/fantasm5.png").getImage(), this.x, this.y, null);
			
		}
		
	}
	
}
