import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Level {
	
	public int width, height;
	public Tile[][] tiles;
	public List <Point> points;
	public List <BigPoint> bigPoints;
	public List <Player> enemies;
	private Random r;
	private int max = 10;
	private Game game;
	public boolean paused = false;

	
	
	public Level(String path, Game game){
		
		this.game = game;
		r = new Random();
		points = new ArrayList<>();
		bigPoints = new ArrayList<>();
		enemies = new ArrayList<>();
		try { 
			
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			this.width = map.getWidth();
			this.height = map.getHeight();
			int[] pixels = new int[width * height];
			tiles = new Tile[width][height];
			map.getRGB(0, 0, width, height, pixels, 0, width);
			int pos = 0;
			for(int xx = 0 ; xx < width ; xx++){
				for(int yy = 0 ; yy < height ; yy++){
					int val = pixels[xx + (yy * width)];
					if(val == 0xFF0000FF){
						//player
						game.getTurno().x = xx*32;
						game.getTurno().y  = yy*32;
						game.getTurno().setSpeed(4);
						game.getTurno().reset();
					}else if(val == 0xFF000000){
						//tile
						tiles[xx][yy] = new Tile(xx*32, yy*32);
					}else if(val == 0xFFFF0000){
						
							if(game.getIndex() == pos){
								pos++;
							}
							if(pos < game.players.size()){
								game.players.get(pos).x = xx*32;
								game.players.get(pos).y = yy*32;
								game.players.get(pos).setSpeed(2 + (int) game.getTurno().getLevel()/2);
								game.players.get(pos).reset();
								enemies.add(game.players.get(pos));
								pos++;
							}else{
								enemies.add(new Enemy(xx*32, yy*32, game.getTurno().getLevel(), this.game));
							}
					}else {
						//point
						int temp = r.nextInt(99-0) + 0;
						if(temp<93 || max == 0){
							points.add(new Point(xx*32, yy*32));
						}else{
							bigPoints.add(new BigPoint(xx*32, yy*32));
							max--;
						}
					}
				
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	
	public void tick(){
		if(!paused){
			
			this.game.getTurno().tick();
			
			for(int i = 0 ; i < enemies.size() ; i++){
				
				enemies.get(i).tick();
					
			}	
			
		}
		
		
		
	}
	
	public void Render(Graphics g){
		
		for(int x = 0 ; x < width ; x++){
			for(int y = 0 ; y < height ; y++){
				if(tiles[x][y] != null) tiles[x][y].Render(g);
			}
		}
		
		for(int i = 0 ; i < points.size() ; i++){
		
		points.get(i).render(g);
			
		}
		
		for(int i = 0 ; i < bigPoints.size() ; i++){
			
		bigPoints.get(i).render(g);
			
		}
		
		for(int i = 0 ; i < enemies.size() ; i++){
			
			enemies.get(i).render(g);
				
		}
		
		this.game.getTurno().render(g);
		
		g.setColor(Color.WHITE);
		g.drawString("Score: " + this.game.getTurno().getScore(), 0, 25);
		g.drawString("Level: " + this.game.getTurno().getLevel(), 0, 40);
		if(this.paused){
			g.drawString("The game is paused", this.game.WIDTH/2, this.game.HEIGHT/2);
		}
		
	}
	
}
