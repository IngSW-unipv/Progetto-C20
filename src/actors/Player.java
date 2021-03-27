package actors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;
import javax.swing.ImageIcon;
import graphic.*;
import actors.*;
import points.*;
import system.*;

public class Player extends Rectangle {
		
	private boolean direction[];
	private int speed = 4;
	private int temp;
	private boolean kill = false;
	private int score=0, lvl = 1;
	private Level level;
	private boolean turno;
	private Color c;
	public Player(int x, int y, Level level , boolean b,Color c){
		
		this.c = c;
		this.setBounds(x, y, 32, 32);
		direction = new boolean[4]; // right 0, left 1, up 2, down 3
		this.reset();
		this.level = level;
		this.turno = b;
		
	}
	
	public void reset(){
		
		for(int i = 0 ; i < this.direction.length ; i++){
			
			this.direction[i] = false;
			
		}
		
	}
	
	public void moveRight(){
		this.direction[0] = true;
		this.setTemp(0);
		
		if(this.canMove(this.x + this.getSpeed() ,this.y)){
			
			this.direction[1] = false;
			this.direction[2] = false;
			this.direction[3] = false;
			
		}
	}
	
	public void moveLeft(){
		this.direction[1] = true;
		this.setTemp(1);
		
		if(this.canMove(this.x - this.getSpeed() ,this.y)){
			
			this.direction[0] = false;
			this.direction[2] = false;
			this.direction[3] = false;
			
		}
	}
	
	public void moveUp(){
		this.direction[2] = true;
		this.setTemp(2);
		
		if(this.canMove(this.x, this.y - this.getSpeed())){
			
			this.direction[0] = false;
			this.direction[1] = false;
			this.direction[3] = false;
			
		}
	}
	
	public void moveDown(){
		this.direction[3] = true;
		this.setTemp(3);
		
		if(this.canMove(this.x ,this.y + this.getSpeed())){
			
			this.direction[0] = false;
			this.direction[1] = false;
			this.direction[2] = false;
			
		}
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
		
		if(direction[0] && canMove(x + speed,y)){
			
			x += speed;
			
			for(int i=0; i< direction.length; i++){
				
				if(i != 0 && i != temp){
					
					direction[i] = false;
					
				}
				
			}
			
		}
		
		if(direction[1] && canMove(x - speed,y)){
			
			x -= speed;
			
			for(int i=0; i< direction.length; i++){
				
				if(i != 1 && i != temp){
					
					direction[i] = false;
					
				}
				
			}
			
		}
		
		if(direction[2] && canMove(x,y - speed)){
			
			y -= speed;
			
			for(int i=0; i< direction.length; i++){
				
				if(i != 2 && i != temp){
					
					direction[i] = false;
					
				}
				
			}
			
		}
		
		if(direction[3] && canMove(x,y + speed)){
			
			y += speed;
			
			for(int i=0; i< direction.length; i++){
				
				if(i != 3 && i != temp){
					
					direction[i] = false;
					
				}
				
			}
			
		}
		
	}

	public void render(Graphics g){
		if(!this.turno){
			if(this.level.getTurno().isKill()){
			
				g.setColor(Color.WHITE);
				g.drawImage(new ImageIcon("res/players/fantasma1.png").getImage(), this.x, this.y, null);
				String time = Integer.toString(3 - ((int)((System.currentTimeMillis() - this.level.getStart())/1000)));
				int h2 = g.getFontMetrics().getHeight();
				int w2 = g.getFontMetrics().stringWidth(time);
				g.drawString(time, x + 16, y+16);
			}else{
				g.drawImage(new ImageIcon("res/players/fantasma.png").getImage(), this.x, this.y, null);
			}
			
		}else{
			
			//g.setColor(c);
			//g.drawRect(x, y, width, height);
			if(direction[0] && canMove(x + speed,y)){
				g.drawImage(new ImageIcon("res/players/playerR.png").getImage(), this.x, this.y, null);
			}else if(direction[1] && canMove(x - speed,y)){
				g.drawImage(new ImageIcon("res/players/playerL.png").getImage(), this.x, this.y, null);
			}else if(direction[2] && canMove(x,y - speed)){
				g.drawImage(new ImageIcon("res/players/playerU.png").getImage(), this.x, this.y, null);
			}else if(direction[3] && canMove(x,y + speed)){
				g.drawImage(new ImageIcon("res/players/playerD.png").getImage(), this.x, this.y, null);
			}else{
				g.drawImage(new ImageIcon("res/players/playerR.png").getImage(), this.x, this.y, null);
			}
			
			
		}
		
	}
	
	
	public boolean isTurno() {
		
		return turno;
		
	}

	public void setTurno(boolean turno) {
		
		this.turno = turno;
		
	}
	
	public int getSpeed() {
		
		// TODO Auto-generated method stub
		return this.speed;
		
	}
	
	public boolean isKill() {
		
		return kill;
		
	}

	public void setKill(boolean kill) {
		
		this.kill = kill;
		
	}

	public void setSpeed(int speed) {
		
		this.speed = speed;
		
	}

	public int getScore() {
		
		return score;
		
	}

	public void setScore(int score) {
		
		this.score = score;
		
	}

	public int getLvl() {
		
		return lvl;
		
	}

	public void setLvl(int lvl) {
		
		this.lvl = lvl;
		
	}

	public int getTemp() {
		
		return temp;
		
	}

	public void setTemp(int temp) {
		
		this.temp = temp;
		
	}
	
}

