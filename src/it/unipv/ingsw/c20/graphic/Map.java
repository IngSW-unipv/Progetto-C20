package it.unipv.ingsw.c20.graphic;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import it.unipv.ingsw.c20.actor.Enemy;
import it.unipv.ingsw.c20.actor.Player;
import it.unipv.ingsw.c20.point.BigPoint;
import it.unipv.ingsw.c20.point.LilPoint;
import it.unipv.ingsw.c20.system.Level;

public class Map {
	Level level;
	private Tile[][] tiles;
	private List<LilPoint> points; //lista dei punti piccoli nel gioco
	private List <BigPoint> bigPoints; // lista dei punti(frutta) grossi
	private List <Player> enemies; // lita dei giocatori non attivi e bot
	
	/**
	 * Class constructor
	 * @author Filippo Tagliaferri
	 * @param level sets all the Lists to play
	 * @param path gets the path to follow to find the image
	 */
	public Map(Level level, String path){

		this.level = level;

		this.setPoints(new ArrayList<>());
		this.setBigPoints(new ArrayList<>());
		this.setEnemies(new ArrayList<>());

		this.createMap(path);

	}


	/**
	 * Creation of the game's map,
	 * the class takes the image from "path" and divides it in a
	 * 32x32 square, then it will control the square's color: 
	 * black = wall, white = point, red = enemy, blue = player.
	 * 
	 * @param path gets the image
	 */
	public void createMap(String path){

		Random r = new Random();
		int max = 10; //max punti grossi
		//pulisco le liste dalla precedente mappa
		this.getBigPoints().clear();
		this.getPoints().clear();
		this.getEnemies().clear();

		try {

			//ottengo l'immagine e la divido in caselle da ricreare

			BufferedImage map = ImageIO.read(new File(path));
			level.setWidth(map.getWidth());
			level.setHeight(map.getHeight());
			int[] pixels = new int[level.getWidth() * level.getHeight()];
			this.setTiles(new Tile[level.getWidth()][level.getHeight()]);
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
						this.getTiles()[xx][yy] = new Tile(xx*32, yy*32);

					}else{

						//point
						int temp = r.nextInt(99-0) + 0;

						if(temp<93 || max == 0){

							this.getPoints().add(new LilPoint(xx*32, yy*32));

						}else{

							this.getBigPoints().add(new BigPoint(xx*32, yy*32));
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
								this.getEnemies().add(level.getPlayers().get(pos));
								pos++;

							}else{

								this.getEnemies().add(new Enemy(xx*32, yy*32, this.getSpeed(level.getTurno().getLvl()), level));

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
	 * This method will return the enemy's speed, according
	 * to the current level of the active player.
	 * Created because the speed needs to be a divider of 32
	 * 
	 * @param n number of levels
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
	
	/*private void tick() {
		
	}
	
	private void render() {
		
	}*/

	public Tile[][] getTiles() { return tiles; }

	public void setTiles(Tile[][] tiles) { this.tiles = tiles; }

	public List <BigPoint> getBigPoints() { return bigPoints; }

	public void setBigPoints(List <BigPoint> bigPoints) { this.bigPoints = bigPoints; }

	public List <LilPoint> getPoints() { return points; }

	public void setPoints(List <LilPoint> points) { this.points = points; }

	public List <Player> getEnemies() { return enemies; }

	public void setEnemies(List <Player> enemies) { this.enemies = enemies; }
}
