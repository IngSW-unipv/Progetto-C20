import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;

public class Player extends Rectangle {
		
	public boolean direction[];
	private int speed = 4;
	private int temp;
	private boolean kill = false;
	private Timer timer;
	private long start;
	private int score=0, level = 1;
	private Game game;
	private boolean turno;
	private Color c;

	public Player(int x, int y, Game game, boolean b,Color c){
		this.c = c;
		this.setBounds(x, y, 32, 32);
		direction = new boolean[4]; // right 0, left 1, up 2, down 3
		this.reset();
		timer = new Timer();
		this.game = game;
		this.turno = b;
	}
	
	public void reset(){
		for(int i = 0 ; i < this.direction.length ; i++){
			this.direction[i] = false;
		}
	}

	public boolean canMove(int nextX, int nextY){
		Rectangle bounds = new Rectangle(nextX, nextY, this.width,this.height);
		Level level = this.game.level;
		for(int xx = 0; xx < level.tiles.length; xx++){
			for(int yy = 0; yy < level.tiles[0].length; yy++){
				if(level.tiles[xx][yy] != null){
					if(bounds.intersects(level.tiles[xx][yy])){
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
		
		if(this.turno){
			if(this.game.level.points.size() == 0 && this.game.level.bigPoints.size() == 0){
				//win
				this.level++;
				for(int i = 0; i< this.direction.length ; i++){
					this.direction[i] = false;
				}
				this.game.level = new Level("/map/map.png", this.game);
			}
			
			for(int i = 0; i < this.game.level.points.size(); i++ ){
				if(this.intersects(this.game.level.points.get(i))){
					this.game.level.points.remove(i);
					score += 50;
				}
			}

			
			for(int i = 0; i < this.game.level.enemies.size(); i++ ){
				if(this.intersects(this.game.level.enemies.get(i))){
					if(!kill){
						if(this.game.getIndex() < this.game.players.size() -1){
							this.game.players.get(this.game.getIndex()+1).setTurno(true);
							this.turno = false;
							this.game.level = new Level("/map/map.png", this.game);
						}else{
							this.game.state = State.End;
						}
					}else{
						this.game.level.enemies.remove(i);
						score += 200;
					}
				}
			}
			
			for(int i = 0; i < this.game.level.bigPoints.size(); i++ ){
				if(this.intersects(this.game.level.bigPoints.get(i))){
					this.kill = true;
					start = System.currentTimeMillis();
					this.game.level.bigPoints.remove(i);
					score += 100;
				}
			}
			
			if(this.kill == true && (System.currentTimeMillis() - start) >=3000){
				this.kill = false;
			}
		}
	}
	
	
	
	public void render(Graphics g){
		g.setColor(c);
		g.fillRect(x, y, width, height);
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}
}

