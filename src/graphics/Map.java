package graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import actors.Enemy;
import points.BigPoint;
import points.Point;
import system.Level;

public class Map {

	public Map(Level l, String path){

		Random r = new Random();
		int max = 10; //max punti grossi
		
		//pulisco le liste dalla precedente mappa
		
		l.getBigPoints().clear();
		l.getPoints().clear();
		l.getEnemies().clear();
		
		try { 
			
			//ottengo l'immagine e la divido incaselle da ricreare
			
			BufferedImage map = ImageIO.read(new File(path));
			l.setWidth(map.getWidth());
			l.setHeight(map.getHeight());
			int[] pixels = new int[l.getWidth() * l.getHeight()];
			l.setTiles(new Tile[l.getWidth()][l.getHeight()]);
			map.getRGB(0, 0, l.getWidth(), l.getHeight(), pixels, 0, l. getWidth());
			int pos = 0;
			
			for(int xx = 0; xx < l.getWidth() ; xx++){
				
				for(int yy = 0; yy < l.getHeight() ; yy++){
					
					int val = pixels[xx + (yy * l.getWidth())];
					
					if(val == 0xFF0000FF){
						
						//player
						l.getTurno().x = xx*32;
						l.getTurno().y  = yy*32;
						l.getTurno().setSpeed(4);
						l.getTurno().reset();
						
					}else if(val == 0xFF000000){
						
						//tile
						l.getTiles()[xx][yy] = new Tile(xx*32, yy*32);
						
					}else{
						
						//point
						int temp = r.nextInt(99-0) + 0;
						
						if(temp<93 || max == 0){
							
							l.getPoints().add(new Point(xx*32, yy*32));
							
						}else{
							
							l.getBigPoints().add(new BigPoint(xx*32, yy*32));
							max--;
							
						}

						if(val == 0xFFFF0000){

							if(l.getIndex() == pos){

								pos++;

							}

							if(pos < l.getPlayers().size()){

								l.getPlayers().get(pos).x = xx*32;
								l.getPlayers().get(pos).y = yy*32;
								l.getPlayers().get(pos).setSpeed(this.getSpeed(l.getTurno().getLvl()));
								l.getPlayers().get(pos).reset();
								l.getEnemies().add(l.getPlayers().get(pos));
								pos++;

							}else{

								l.getEnemies().add(new Enemy(xx*32, yy*32, this.getSpeed(l.getTurno().getLvl()), l));

							}

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
		}else {
			return 32;
		}
	}
	
	private void tick() {
		
	}
	
	private void render() {
		
	}
}
