package system;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import javax.imageio.ImageIO;
import graphic.*;
import actors.*;
import points.*;
import system.*;

public class Level implements KeyListener {
	
	private int width, height;
	//public Turno turno;
	private Tile[][] tiles;
	private List <Point> points; //lista dei punti piccoli nel gioco
	private List <BigPoint> bigPoints; // lista dei punti(frutta) grossi
	private List <Player> enemies; // lita dei giocatori non attivi e bot
	private List <Player> players;  //Lista di giocatori attivi
	private Map map; // mappa dove si gioca

	private Game game;
	private boolean paused = false;
	private int n;
	private Timer timer;
	private long start = 0;
	private String path;
	
	
	public Level(Game game, int n){
		
		this.game = game;
		
		setPoints(new ArrayList<>());
		setBigPoints(new ArrayList<>());
		setEnemies(new ArrayList<>());;
		setPlayers(new ArrayList<>());
		this.game.addKeyListener(this);
		timer = new Timer();
		this.path = this.getPath();
		
		this.getPlayers().clear();
		
		if(n >= 1){
			
			getPlayers().add(0, new Player(0, 0, this, true, Color.red));
		}
		
		if(n >= 2){
			
			getPlayers().add(1, new Player(0, 0, this, false, Color.green));
			
		}
		
		if(n >= 3){
			
			getPlayers().add(2, new Player(0, 0, this, false, Color.blue));
			
		}
		
		if(n >= 4){
			
			getPlayers().add(3, new Player(0, 0, this, false, Color.white));
			
		}
		
		map = new Map(this, this.path);
		//this.turno = new Turno(this, this.players);
	}
	
	//prendo il giocatore con il turno attuale
	
	public Player getTurno(){
		
		for(int i = 0; i < getPlayers().size(); i++){
			
			if(getPlayers().get(i).isTurno()){
				
				return getPlayers().get(i);
				
			}
			
		}
		
		return null;
		
	}
	
	// prendo l'index del giocatore pacman
	
	public int getIndex(){
		
		for(int i = 0; i < getPlayers().size(); i++){
			
			if(getPlayers().get(i).isTurno()){
				
				return i;
				
			}
			
		}
		
		return (Integer) null;
		
	}
	
	public void tick(){
		
		if(!paused){
			
			//this.turno.tick();
			
			this.getTurno().tick();
			
			for(int i = 0 ; i < getEnemies().size() ; i++){
				
				getEnemies().get(i).tick();
					
			}
			
			// se vengono mangiati tutti gli oggetti statici in gioco si passa al livello successivo
			
			if(this.getPoints().size() == 0 && this.getBigPoints().size() == 0){
				//win
				this.getTurno().setLvl(this.getTurno().getLvl() + 1);
				
				this.map = new Map(this, this.getPath());
				
			}
			
			//controllo interazione con punti 
			
			for(int i = 0; i < this.getPoints().size(); i++ ){
				
				if(this.getTurno().intersects(this.getPoints().get(i))){
					
					this.getPoints().remove(i);
					this.getTurno().setScore(this.getTurno().getScore() + 50);
					
				}
				
			}
			
			//controllo le interazioni con la frutta
			
			for(int i = 0; i < this.getBigPoints().size(); i++ ){
				
				if(this.getTurno().intersects(this.getBigPoints().get(i))){
					
					this.start = System.currentTimeMillis();
					this.getTurno().setKill(true);
					this.getBigPoints().remove(i);
					this.getTurno().setScore(this.getTurno().getScore() + 100);
					
				}
				
			}

			//controllo interazione con nemici e in base allo stato di kill decido il risultato
			
			for(int i = 0; i < this.getEnemies().size(); i++ ){
				
				if(this.getTurno().intersects(this.getEnemies().get(i))){
					
					if(!this.getTurno().isKill()){
						
						if(this.getIndex() < this.getPlayers().size() -1){
							
							this.getPlayers().get(this.getIndex()+1).setTurno(true);
							this.getTurno().setTurno(false);
							this.map = new Map(this, this.getPath());
							
						}else{
							
							this.game.setState(State.End);
							
						}
						
					}else{
						
						this.getEnemies().remove(i);
						this.getTurno().setScore(this.getTurno().getScore() + 200);
						
					}
					
				}
				
			}
			
			if(this.getTurno().isKill() && (System.currentTimeMillis() - start) >=3000){
				
				// quando il timer arriva a 3 secondi si torna allo stato normale
				
				this.getTurno().setKill(false);
				
			}
			
		}
		
	}
	
	public void Render(Graphics g){
		
		//this.turno.Render();
		
		for(int x = 0 ; x < getWidth() ; x++){
			
			for(int y = 0 ; y < getHeight() ; y++){
				
				if(getTiles()[x][y] != null) getTiles()[x][y].Render(g);
				
			}
			
		}
		
		for(int i = 0 ; i < getPoints().size() ; i++){
		
		getPoints().get(i).render(g);
			
		}
		
		for(int i = 0 ; i < getBigPoints().size() ; i++){
			
		getBigPoints().get(i).render(g);
			
		}
		
		for(int i = 0 ; i < getEnemies().size() ; i++){
			
			getEnemies().get(i).render(g);
				
		}
		
		this.getTurno().render(g);
		
		g.setColor(Color.WHITE);
		g.drawString("Score: " + this.getTurno().getScore(), 0, 25);
		g.drawString("Level: " + this.getTurno().getLvl(), 0, 40);
		
		if(this.paused){
			
			g.drawString("The game is paused", this.game.getWidth()/2, this.game.getHeight()/2);
			
		}
		
	}


	public long getStart() {
		
		return start;
		
	}

	public void setStart(long start) {
		
		this.start = start;
		
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public List <BigPoint> getBigPoints() {
		return bigPoints;
	}

	public void setBigPoints(List <BigPoint> bigPoints) {
		this.bigPoints = bigPoints;
	}

	public List <Point> getPoints() {
		return points;
	}

	public void setPoints(List <Point> points) {
		this.points = points;
	}

	public List <Player> getEnemies() {
		return enemies;
	}

	public void setEnemies(List <Player> enemies) {
		this.enemies = enemies;
	}

	public List <Player> getPlayers() {
		return players;
	}

	public void setPlayers(List <Player> players) {
		this.players = players;
	}
	
	public int getSize(){
		return this.players.size();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public String getPath(){
		Random ram = new Random();
		return "/map/map" +  Integer.toString(ram.nextInt(3-1) + 1)  +  ".png";
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		// Player 1
		if(this.getPlayers().size() >=1 ){
			
			if(e.getKeyCode() == KeyEvent.VK_W){
				
				this.getPlayers().get(0).moveUp();;
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_S){
				
				this.getPlayers().get(0).moveDown();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_A){
				
				this.getPlayers().get(0).moveLeft();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_D){
				
				this.getPlayers().get(0).moveRight();
				
			}
			
		}
		
		//Player 2
		if(this.getPlayers().size() >= 2){
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				
				this.getPlayers().get(1).moveUp();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				
				this.getPlayers().get(1).moveDown();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				
				this.getPlayers().get(1).moveLeft();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				
				this.getPlayers().get(1).moveRight();
				
			}
			
		}
		
		//Player 3
		if(this.getPlayers().size() >= 3){
			
			if(e.getKeyCode() == KeyEvent.VK_I){
				
				this.getPlayers().get(2).moveUp();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_K){
				
				this.getPlayers().get(2).moveDown();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_J){
				
				this.getPlayers().get(2).moveLeft();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_L){
				
				this.getPlayers().get(2).moveRight();
				
			}
			
		}
		
		
		//Player 4
		if(this.getPlayers().size() >= 4){
			
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD8){
				
				this.getPlayers().get(3).moveUp();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD5){
				
				this.getPlayers().get(3).moveDown();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD4){
				
				this.getPlayers().get(3).moveLeft();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD6){
				
				this.getPlayers().get(3).moveRight();
				
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P){
			
			if(this.paused){
				
				this.paused = false;
				
			}else if(!this.paused){
				
				this.paused = true;
				
			}
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
