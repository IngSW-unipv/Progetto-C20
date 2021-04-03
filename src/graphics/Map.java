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
	Level level;
	/**
	 * Class constructor
	 *@param level to set all the List to play
	 *@param path to get the path to follow for the image
	 */

	public Map(Level level, String path){

		this.level = level;
		this.createMap(path);
	}


	/**
	 * Creations of the map for the game,
	 * the class will take the image from path and divide it in
	 * square 32x32, next it will control the colour of the square
	 * and put wall for black, points for white, enemy for red and player for blue.
	 * @param path to get the image
	 */
	public void createMap(String path){

		Random r = new Random();
		int max = 10; //max punti grossi
		//pulisco le liste dalla precedente mappa
		level.getBigPoints().clear();
		level.getPoints().clear();
		level.getEnemies().clear();

		try {

			//ottengo l'immagine e la divido in caselle da ricreare

			BufferedImage map = ImageIO.read(new File(path));
			level.setWidth(map.getWidth());
			level.setHeight(map.getHeight());
			int[] pixels = new int[level.getWidth() * level.getHeight()];
			level.setTiles(new Tile[level.getWidth()][level.getHeight()]);
			map.getRGB(0, 0, level.getWidth(), level.getHeight(), pixels, 0, level. getWidth());
			int pos = 0;

			for(int xx = 0; xx < level.getWidth() ; xx++){

				for(int yy = 0; yy < level.getHeight() ; yy++){

					int val = pixels[xx + (yy * level.getWidth())];

					if(val == 0xFF0000FF){

						//player
						level.getTurno().x = xx*32;
						level.getTurno().y  = yy*32;
						level.getTurno().setSpeed(4);
						level.getTurno().reset();

					}else if(val == 0xFF000000){

						//tile
						level.getTiles()[xx][yy] = new Tile(xx*32, yy*32);

					}else{

						//point
						int temp = r.nextInt(99-0) + 0;

						if(temp<93 || max == 0){

							level.getPoints().add(new Point(xx*32, yy*32));

						}else{

							level.getBigPoints().add(new BigPoint(xx*32, yy*32));
							max--;

						}

						if(val == 0xFFFF0000){

							if(level.getIndex() == pos){

								pos++;

							}

							if(pos < level.getPlayers().size()){

								level.getPlayers().get(pos).x = xx*32;
								level.getPlayers().get(pos).y = yy*32;
								level.getPlayers().get(pos).setSpeed(this.getSpeed(level.getTurno().getLvl()));
								level.getPlayers().get(pos).reset();
								level.getEnemies().add(level.getPlayers().get(pos));
								pos++;

							}else{

								level.getEnemies().add(new Enemy(xx*32, yy*32, this.getSpeed(level.getTurno().getLvl()), level));

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

	/**
	 * This method will return the speed for the enemy based
	 * on the current level of the active player.
	 * Created because the speed need to be a divider of 32
	 * @param n of level
	 * @return the speed for the enemy
	 */
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
