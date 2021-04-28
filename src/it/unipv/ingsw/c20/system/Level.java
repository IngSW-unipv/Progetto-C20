package it.unipv.ingsw.c20.system;

import it.unipv.ingsw.c20.actor.Player;
import it.unipv.ingsw.c20.constants.State;
import it.unipv.ingsw.c20.map.Map;
import it.unipv.ingsw.c20.map.Tile;
import it.unipv.ingsw.c20.menu.EndingMenu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Enxhi Ismaili, Carlos Reynaga
 *
 */
public class Level implements KeyListener {
	
	private List <Player> players; 			// List of the active players
	private Map map;						//map where the game is played
	private Game game;
	private boolean paused = false;
	private long start = 0;
	private List<String> nomi;

	/**
	 * Constructor sets the players creating a list that will contain them,
	 * adds the listener at the game, creates the players and creates the map
	 * 
	 * @param game 
	 * @param n integer represents the number of the players
	 */
	public Level(Game game, int n, List<String> nomi){
		this.game = game;
		setPlayers(new ArrayList<>());
		this.game.addKeyListener(this);
		this.nomi = nomi;
		// creates the players required by the menu
		createplayer(n, nomi);
		
		map = new Map(this, this.game.getMapPath());
	}
	
	private void createplayer(int n, List<String> nomi) {
		this.getPlayers().clear();
		if(n >= 1){
			getPlayers().add(0, new Player(0, 0, this, true, 0, nomi.get(0)));
			for(int i = 1; i<n; i++){
				getPlayers().add(i, new Player(0, 0, this, false, i, nomi.get(i)));
			}
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
	@SuppressWarnings("null")
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
		
		if(this.getTurno().getLvl() == 33 || this.getTurno().getScore() >= 200000){
			
			//Controls in order to not make the game last too long
			
			if(this.getIndex() < this.getPlayers().size() -1){
				
				this.getPlayers().get(this.getIndex()+1).setTurno(true); //turn of the next player 
				this.getTurno().setTurno(false);	// die the current player
				this.map = new Map(this, this.game.getMapPath());
				
			}else{
				
				this.game.setMenu(new EndingMenu(this.game));
				this.game.setState(State.Menu);
				
			}
			
		}
		
		if(this.map.getPoints().size() == 0 && this.map.getBigPoints().size() == 0){
			//win
			this.getTurno().setLvl(this.getTurno().getLvl() + 1);
			
			this.map = new Map(this, this.game.getMapPath());
			
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
					new Music("res/sounds/playerDeath.wav", 0);
					
					if(this.getIndex() < this.getPlayers().size() -1){
						this.game.getScores().addScore(this.getTurno().getNome(), this.getTurno().getScore());
						this.getPlayers().get(this.getIndex()+1).setTurno(true); //turn of the next player 
						this.getTurno().setTurno(false);	// die the current player
						this.map = new Map(this, this.game.getMapPath());
						
					}else{
						this.game.getScores().addScore(this.getTurno().getNome(), this.getTurno().getScore());
						this.game.setMenu(new EndingMenu(this.game));
						this.game.setState(State.Menu);
						
					}
					
				}else{
					new Music("res/sounds/enemyDeath.wav", 0);
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
		for(Tile t : this.map.getTiles()){
			t.Render(g);
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
		int h2 = g.getFontMetrics().getHeight();
		
		g.setColor(Color.WHITE);
		int w2 = g.getFontMetrics().stringWidth("Score: " + this.getTurno().getScore());
		g.drawString("Score: " + this.getTurno().getScore(), this.game.getWIDTH()/2 - w2/2 , h2);
		w2 = g.getFontMetrics().stringWidth("Level: " + this.getTurno().getLvl());
		g.drawString("Level: " + this.getTurno().getLvl(), this.game.getWIDTH() - w2, h2);
		
		if(this.paused){
			
			w2 = g.getFontMetrics().stringWidth("The game is paused");
			g.drawString("The game is paused", this.game.getWidth()/2-w2/2, this.game.getHeight()/2);
			
		}
		
	}
	
	/**
	 * Getters and Setters
	 */
	
	/** return the start of this timer
	 * @return the timer of this timer that indicate the vulnerability of the ghost
	 */
	public long getStart() { return start;}
	
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
	 * Map's getter
	 * @return map
	 */
	public Map getMap() { return map; }

	/**
	 * names getter
	 * @return the playe's name
	 */
	
	public List<String> getNomi() { return nomi; }

	/**
	 * names setter
	 * @param player's names
	 */
	
	public void setNomi(List<String> nomi) {this.nomi = nomi;}

	/**
	 * Manages the keyboard
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(!this.paused){
			for(Player p : this.getPlayers()){
				p.move(e.getKeyCode());
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

	/**
	 * Manages the keyboard
	 */
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	/**
	 * Manages the keyboard
	 */
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
}
