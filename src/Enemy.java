import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends Player{
	private int random = 0, smart = 1;
	private int state = random;
	private int left = 0, right = 1, up =2, down = 3;
	
	private int dir = -1;
	
	private int time = 0;
	private int targetTime = 60*4;
	public int speed = 2;
	
	private Random r;
	private Game game;
	
	public Enemy(int x, int y, int l, Game g){
		super(x, y, g, false, Color.YELLOW);
		this.game = g;
		r = new Random();
		this.setBounds(x, y, 32, 32);
		dir = r.nextInt(4);
		this.speed = this.speed + (int) l/2;
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
		if(this.game.getTurno().isKill()){
			g.setColor(Color.PINK);
		}else{
			g.setColor(Color.ORANGE);
		}
		g.fillRect(x, y, width, height);
		
	}
}
