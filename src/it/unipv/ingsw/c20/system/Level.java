package it.unipv.ingsw.c20.system;

import it.unipv.ingsw.c20.actor.Player;
import it.unipv.ingsw.c20.graphic.Map;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author Enxhi Ismaili, Carlos Reynaga
 *
 */
public class Level implements KeyListener {
	
	private int width, height;
	private List <Player> players; 			// List of the active players
	private Map map;						//map where the game is played
	
	private Game game;
	private boolean paused = false;
	private long start = 0;

	/**
	 * Constructor sets the players creating a list that will contain them,
	 * adds the listener at the game, creates the players and creates the map
	 * 
	 * @param game 
	 * @param n integer represents the number of the players
	 */
	public Level(Game game, int n){
		
		this.game = game;
		setPlayers(new ArrayList<>());
		this.game.addKeyListener(this);
		
		this.getPlayers().clear();
		
		// creates the players required by the menu
		createplayer(n);
		
		map = new Map(this, this.getPath());
	}
	
	private void createplayer(int n) {
		if(n >= 1){
			
			getPlayers().add(0, new Player(0, 0, this, true, 0));
		}
		for(int i = 1; i<n; i++){
			getPlayers().add(i, new Player(0, 0, this, false, i));
		}
	}
	
	// takes the player with the current round
	
	/**
	 * Getter
	 * @return player
	 */
	public Player getTurno(){
		
		for(int i = 0; i < getPlayers().size(); i++){
			
			if(getPlayers().get(i).isTurno()){
				
				return getPlayers().get(i);
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Getter
	 * @return integer that is the index of the player pacman
	 */
	public int getIndex(){
		
		for(int i = 0; i < getPlayers().size(); i++){
			
			if(getPlayers().get(i).isTurno()){
				
				return i;
				
			}
			
		}
		
		return (Integer) null;
		
	}
	
	/**
	 * Tick continuously updates the logical side of what it is happening on the screen
	 */
	public void tick(){
		
		if(!paused){
			
			
			this.getTurno().tick();		// pacman's movement
			
			for(int i = 0 ; i < map.getEnemies().size() ; i++){

				map.getEnemies().get(i).tick();		// ghosts's movement
					
			}
			
			updateLvl();
			
			interactionPoints();
			
			interactionFruits();
			
			interactionEnemies();
			
			// when the timer is up to 3 seconds return to the normal state
			if(this.getTurno().isKill() && (System.currentTimeMillis() - start) >=3000){
				
				this.getTurno().setKill(false);
				
			}
			
		}
		
	}
	
	/**
	 * If the static objects are eaten (yellow points and apples),
	 * it passes at the following level
	 */
	public void updateLvl() {
		
		if(this.map.getPoints().size() == 0 && this.map.getBigPoints().size() == 0){
			//win
			this.getTurno().setLvl(this.getTurno().getLvl() + 1);
			
			this.map = new Map(this, this.getPath());
			
		}
	}
	
	/** Manages the points's interaction */
	public void interactionPoints() {
		
		for(int i = 0; i < this.map.getPoints().size(); i++ ){
			
			if(this.getTurno().intersects(this.map.getPoints().get(i))){
				
				this.map.getPoints().remove(i);
				this.getTurno().setScore(this.getTurno().getScore() + 50);
				
			}
		}
	}
	
	/** Control of the apples's interaction */
	public void interactionFruits() {
		
		for(int i = 0; i < this.map.getBigPoints().size(); i++ ){
			
			if(this.getTurno().intersects(this.map.getBigPoints().get(i))){
				
				this.start = System.currentTimeMillis(); // starting time to be able to eat enemies
				this.getTurno().setKill(true);	// chance to eat enemies
				this.map.getBigPoints().remove(i);
				this.getTurno().setScore(this.getTurno().getScore() + 100);
				
			}
		}
	}
	
	/**
	 * Control of the enemys's interaction and in accord with the state of the kill 
	 * it decides the result
	 */
	public void interactionEnemies() {
		
		for(int i = 0; i < this.map.getEnemies().size(); i++ ){
			
			if(this.getTurno().intersects(this.map.getEnemies().get(i))){
				
				if(!this.getTurno().isKill()){	// can kill the enemies or not 
					
					if(this.getIndex() < this.getPlayers().size() -1){
						
						this.getPlayers().get(this.getIndex()+1).setTurno(true); //turn of the next player 
						this.getTurno().setTurno(false);	// die the current player
						this.map = new Map(this, this.getPath());
						
					}else{
						
						this.game.setState(State.End);
						
					}
					
				}else{
					
					this.map.getEnemies().remove(i);
					this.getTurno().setScore(this.getTurno().getScore() + 200);
					
				}
				
			}
			
		}
		
	}
	
	
	/**
	 * Render continuously updates the graphic side of what it is happening on the screen.
	 */
	public void Render(Graphics g){
		
		for(int x = 0 ; x < getWidth() ; x++){
			
			for(int y = 0 ; y < getHeight() ; y++){
				
				if(this.map.getTiles()[x][y] != null) this.map.getTiles()[x][y].Render(g);
				
			}
			
		}
		
		for(int i = 0 ; i < this.map.getPoints().size() ; i++){
		
		this.map.getPoints().get(i).render(g);
			
		}
		
		for(int i = 0 ; i < this.map.getBigPoints().size() ; i++){
			
		this.map.getBigPoints().get(i).render(g);
			
		}
		
		for(int i = 0 ; i < this.map.getEnemies().size() ; i++){
			
			this.map.getEnemies().get(i).render(g);
				
		}
		
		this.getTurno().render(g);
		
		// show on screen current player's score and fps
		
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
	
	/**
	 * Getters and Setters
	 */
	
	/**
	 * Getter's Player
	 * @return list of the players 
	 */
	public List <Player> getPlayers() { return players; }
	
	/**
	 * Setter
	 * @param players
	 */
	public void setPlayers(List <Player> players) { this.players = players; }
	
	/**
	 * Getter
	 * @return height 
	 */
	
	public int getHeight() { return height; }
	
	/**
	 * Setter
	 * @param height
	 */
	public void setHeight(int height) { this.height = height; }
	
	/**
	 * Getter
	 * @return width
	 */
	public int getWidth() { return width; }
	
	/**
	 * Setter
	 * @param width
	 */
	public void setWidth(int width) { this.width = width; }
	
	/**
	 * Map's getter
	 * @return map
	 */
	public Map getMap() { return map; }
	
	/**
	 * Randomly generates a map
	 * @return String that contains the location of a random map 
	 * present among the resources
	 */
	public String getPath(){
		Random ram = new Random();
		return "res/map/map" + (ram.nextInt(3 - 1) + 1) +  ".png";
		
	}

	/**
	 * Manages the keyboard
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		// Player 1
		if(this.getPlayers().size() >=1 ){
			
			if(e.getKeyCode() == KeyEvent.VK_W){
				
				this.getPlayers().get(0).moveUp();
				
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
			
			if(e.getKeyCode() == KeyEvent.VK_T){
				
				this.getPlayers().get(1).moveUp();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_G){
				
				this.getPlayers().get(1).moveDown();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_F){
				
				this.getPlayers().get(1).moveLeft();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_H){
				
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
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				
				this.getPlayers().get(3).moveUp();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				
				this.getPlayers().get(3).moveDown();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				
				this.getPlayers().get(3).moveLeft();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				
				this.getPlayers().get(3).moveRight();
				
			}
			
		}
		
		//Player 5
		if(this.getPlayers().size() >= 5){
			

			if(e.getKeyCode() == KeyEvent.VK_NUMPAD8){
				
				this.getPlayers().get(4).moveUp();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD5){
				
				this.getPlayers().get(4).moveDown();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD4){
				
				this.getPlayers().get(4).moveLeft();
				
			}
			
			if(e.getKeyCode() == KeyEvent.VK_NUMPAD6){
				
				this.getPlayers().get(4).moveRight();
				
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
