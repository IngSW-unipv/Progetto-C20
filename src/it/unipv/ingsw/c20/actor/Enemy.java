package it.unipv.ingsw.c20.actor;

import it.unipv.ingsw.c20.system.Level;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Player{

	private static final long serialVersionUID = 1L;

	private int dir;
	
	public int speed;
	
	private Random r;
	private Level level;
	private int tmp = 4;
	private List<Integer> t;
	
	public Enemy(int x, int y, int l, Level level){
		
		super(x, y, level, false, 4);
		this.level = level;
		r = new Random();
		this.setBounds(x, y, 32, 32);
		dir = r.nextInt(4);
		this.speed = l;
		this.setFantasmi();
		t = new ArrayList<>();

		
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

		t.clear();

		if(canMove(x + speed,y)){
			t.add(0);
		}
		if(canMove(x - speed,y)){
			t.add(1);
		}
		if(canMove(x,y - speed)){
			t.add(2);
		}
		if (canMove(x, y + speed)) {
			t.add(3);
		}

		if(t.size() > tmp){
			dir = r.nextInt(4);
		}

		//right 0
		if (dir == 0){

			if(canMove(x + speed,y)){

				x += speed;
				//tmp = t.size();
			}else{

				dir = r.nextInt(4);

			}

		}

		//left 1
		if(dir == 1){

			if(canMove(x - speed,y)){

				x -= speed;
				//int tmp = t.size();
			}else{

				dir = r.nextInt(4);

			}

		}

		//up 2
		if(dir == 2){

			if(canMove(x,y - speed)){

				y -= speed;
				//int tmp = t.size();
			}else{

				dir = r.nextInt(4);

			}

		}

		//down 3
		if(dir == 3) {

			if (canMove(x, y + speed)) {

				y += speed;
				//int tmp = t.size();
			} else {

				dir = r.nextInt(4);

			}

		}

	}
	
	public void render(Graphics g){
		
		if(this.level.getTurno().isKill()){
			
			g.setColor(Color.WHITE);
			g.drawImage(this.getFantasma(0), this.x, this.y, null);
			String time = Integer.toString(3 - ((int)((System.currentTimeMillis() - this.level.getStart())/1000)));
			g.drawString(time, x + 16, y+16);
			
		}else{
			
			g.setColor(Color.ORANGE);
			
			g.drawImage(this.getFantasma(1), this.x, this.y, null);
			
		}
		
	}
	
}
