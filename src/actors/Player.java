package actors;
import java.awt.*;
import java.util.Arrays;
import javax.swing.ImageIcon;

import system.Level;

public class Player extends Actor {
		
	private boolean[] direction;
	private int speed = 4;
	private int temp;
	private int imgtemp;
	private boolean kill = false;
	private int score=0, lvl = 1;
	private Level level;
	private boolean turno;
	private int posizione;
	private Image[] pacman;
	private Image[] fantasmi;

	public Player(int x, int y, Level level , boolean b, int i){
		super(x, y, level);
		this.posizione = i;
		this.setBounds(x, y, 32, 32);
		direction = new boolean[4]; // right 0, left 1, up 2, down 3
		this.reset();
		this.level = level;
		this.turno = b;
		this.setPacman();
		this.setFantasmi();
	}
	
	public void reset(){

		Arrays.fill(this.direction, false);
		
	}
	
	public void moveRight(){
		this.direction[0] = true;
		this.setTemp(0);

	}
	
	public void moveLeft(){
		this.direction[1] = true;
		this.setTemp(1);

	}
	
	public void moveUp(){
		this.direction[2] = true;
		this.setTemp(2);

	}
	
	public void moveDown(){
		this.direction[3] = true;
		this.setTemp(3);
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
		
		if(direction[0] && canMove(x + speed,y)){
			
			x += speed;
			this.imgtemp = 0;

			for(int i=0; i< direction.length; i++){
				
				if(i != 0 && i != temp){
					
					direction[i] = false;
					
				}
				
			}
			
		}
		
		if(direction[1] && canMove(x - speed,y)){
			
			x -= speed;
			this.imgtemp = 1;

			for(int i=0; i< direction.length; i++){
				
				if(i != 1 && i != temp){
					
					direction[i] = false;
					
				}
				
			}
			
		}
		
		if(direction[2] && canMove(x,y - speed)){
			
			y -= speed;
			this.imgtemp = 2;

			for(int i=0; i< direction.length; i++){
				
				if(i != 2 && i != temp){
					
					direction[i] = false;
					
				}
				
			}
			
		}
		
		if(direction[3] && canMove(x,y + speed)){
			
			y += speed;
			this.imgtemp = 3;

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
				g.drawImage(fantasmi[0], this.x, this.y, null);
				String time = Integer.toString(3 - ((int)((System.currentTimeMillis() - this.level.getStart())/1000)));
				g.drawString(time, x + 16, y+16);
			}else{
				g.drawImage(fantasmi[1], this.x, this.y, null);
			}
			
		}else{
			g.drawImage(this.pacman[this.imgtemp], this.x, this.y, null);
		}
		
	}
	
	
	public boolean isTurno() {
		
		return turno;
		
	}

	public void setTurno(boolean turno) {
		
		this.turno = turno;
		
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

	public void setTemp(int temp) {
		
		this.temp = temp;
		
	}

	public void setPacman() {
		pacman = new Image[this.direction.length];
		pacman[0]= new ImageIcon("res/players/playerR" + (this.posizione + 1) + ".png").getImage();
		pacman[1]=new ImageIcon("res/players/playerL" + (this.posizione + 1) + ".png").getImage();
		pacman[2]=new ImageIcon("res/players/playerU" + (this.posizione + 1) + ".png").getImage();
		pacman[3]=new ImageIcon("res/players/playerD" + (this.posizione + 1) + ".png").getImage();
		this.imgtemp = 0;
	}

	public void setFantasmi(){
		fantasmi = new Image[2];
		fantasmi [0] = new ImageIcon("res/players/fantasma" + (this.posizione + 1) + ".png").getImage();
		fantasmi [1] = new ImageIcon("res/players/fantasm" + (this.posizione + 1) + ".png").getImage();
	}

	public Image getFantasma(int n){
		return this.fantasmi[n];
	}
	
}

