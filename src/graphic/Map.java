package graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import actors.Enemy;
import points.BigPoint;
import points.Point;
import system.Level;

public class Map {
	
	private Level level;
	
	public Map(Level l, String path){
		
		this.level = l;
		Random r = new Random(); 
		int max = 10; //max punti grossi
		
		//pulisco le liste dalla precedente mappa
		
		this.level.getBigPoints().clear();
		this.level.getPoints().clear();
		this.level.getEnemies().clear();
		
		try { 
			
			//ottengo l'immagine e la divido incaselle da ricreare
			
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			this.level.setWidth(map.getWidth());
			this.level.setHeight(map.getHeight());
			int[] pixels = new int[this.level.getWidth() * this.level.getHeight()];
			this.level.setTiles(new Tile[this.level.getWidth()][this.level.getHeight()]);
			map.getRGB(0, 0, this.level.getWidth(), this.level.getHeight(), pixels, 0,this.level. getWidth());
			int pos = 0;
			
			for(int xx = 0 ; xx < this.level.getWidth() ; xx++){
				
				for(int yy = 0 ; yy < this.level.getHeight() ; yy++){
					
					int val = pixels[xx + (yy * this.level.getWidth())];
					
					if(val == 0xFF0000FF){
						
						//player
						this.level.getTurno().x = xx*32;
						this.level.getTurno().y  = yy*32;
						this.level.getTurno().setSpeed(4);
						this.level.getTurno().reset();
						
					}else if(val == 0xFF000000){
						
						//tile
						this.level.getTiles()[xx][yy] = new Tile(xx*32, yy*32);
						
					}else if(val == 0xFFFF0000){
						
							if(this.level.getIndex() == pos){
								
								pos++;
								
							}
							
							if(pos < this.level.getPlayers().size()){
								
								this.level.getPlayers().get(pos).x = xx*32;
								this.level.getPlayers().get(pos).y = yy*32;
								this.level.getPlayers().get(pos).setSpeed(this.getSpeed(this.level.getTurno().getLvl()));
								this.level.getPlayers().get(pos).reset();
								this.level.getEnemies().add(this.level.getPlayers().get(pos));
								pos++;
								
							}else{
								
								this.level.getEnemies().add(new Enemy(xx*32, yy*32, this.getSpeed(this.level.getTurno().getLvl()), this.level));
							
							}
							
					}else{
						
						//point
						int temp = r.nextInt(99-0) + 0;
						
						if(temp<93 || max == 0){
							
							this.level.getPoints().add(new Point(xx*32, yy*32));
							
						}else{
							
							this.level.getBigPoints().add(new BigPoint(xx*32, yy*32));
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
	
	private int getSpeed(int n){
		if(n<=2){
			return 2;
		}else if(n<=4){
			return 4;
		}else if(n<=8){
			return 8;
		}else if(n<=16){
			return 16;
		}else if(n<=32){
			return 32;
		}else if(n>32){
			return 32;
		}else {
			return 2;
		}
	}
	
	private void tick() {
		
	}
	
	private void render() {
		
	}
}
