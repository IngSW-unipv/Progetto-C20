package actors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;
import system.Level;

public class Enemy extends Player{

	private int dir;
	
	public int speed;
	
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
		
		for(int xx = 0; xx < level.getMap().getTiles().length; xx++){
			
			for(int yy = 0; yy < level.getMap().getTiles()[0].length; yy++){
				
				if(level.getMap().getTiles()[xx][yy] != null){
					
					if(bounds.intersects(level.getMap().getTiles()[xx][yy])){
						
						return false;
						
					}
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	public void tick(){

		int left = 0;
		if (dir == left){

			if(canMove(x - speed,y)){

				x -= speed;

			}else{

				dir = r.nextInt(4);

			}

		}

		int right = 1;
		if(dir == right){

			if(canMove(x + speed,y)){

				x += speed;

			}else{

				dir = r.nextInt(4);

			}

		}

		int up = 2;
		if(dir == up){

			if(canMove(x,y - speed)){

				y -= speed;

			}else{

				dir = r.nextInt(4);

			}

		}

		int down = 3;
		if(dir == down) {

			if (canMove(x, y + speed)) {

				y += speed;

			} else {

				dir = r.nextInt(4);

			}

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
