package it.unipv.ingsw.c20.system;

import it.unipv.ingsw.c20.actor.Player;
import it.unipv.ingsw.c20.graphic.Map;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level implements KeyListener {
	
	private int width, height;
	private List <Player> players;  //Lista di giocatori attivi
	private Map map; // mappa dove si gioca

	private Game game;
	private boolean paused = false;
	private long start = 0;


	public Level(Game game, int n){
		
		this.game = game;
		setPlayers(new ArrayList<>());
		this.game.addKeyListener(this);
		
		this.getPlayers().clear();
		
		// creo i giocatori richiesti dal menu
		
		if(n >= 1){
			
			getPlayers().add(0, new Player(0, 0, this, true, 0));
		}
		for(int i = 1; i<n; i++){
			getPlayers().add(i, new Player(0, 0, this, false, i));
		}
		map = new Map(this, this.getPath());
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
	
	@SuppressWarnings("null")
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
			
			
			this.getTurno().tick();
			
			for(int i = 0 ; i < map.getEnemies().size() ; i++){

				map.getEnemies().get(i).tick();
					
			}
			
			// se vengono mangiati tutti gli oggetti statici in gioco si passa al livello successivo
			
			if(this.map.getPoints().size() == 0 && this.map.getBigPoints().size() == 0){
				//win
				this.getTurno().setLvl(this.getTurno().getLvl() + 1);
				
				this.map = new Map(this, this.getPath());
				
			}
			
			//controllo interazione con punti 
			
			for(int i = 0; i < this.map.getPoints().size(); i++ ){
				
				if(this.getTurno().intersects(this.map.getPoints().get(i))){
					
					this.map.getPoints().remove(i);
					this.getTurno().setScore(this.getTurno().getScore() + 50);
					
				}
				
			}
			
			//controllo le interazioni con la frutta
			
			for(int i = 0; i < this.map.getBigPoints().size(); i++ ){
				
				if(this.getTurno().intersects(this.map.getBigPoints().get(i))){
					
					this.start = System.currentTimeMillis();
					this.getTurno().setKill(true);
					this.map.getBigPoints().remove(i);
					this.getTurno().setScore(this.getTurno().getScore() + 100);
					
				}
				
			}

			//controllo interazione con nemici e in base allo stato di kill decido il risultato
			
			for(int i = 0; i < this.map.getEnemies().size(); i++ ){
				
				if(this.getTurno().intersects(this.map.getEnemies().get(i))){
					
					if(!this.getTurno().isKill()){
						
						if(this.getIndex() < this.getPlayers().size() -1){
							
							this.getPlayers().get(this.getIndex()+1).setTurno(true);
							this.getTurno().setTurno(false);
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
			
			if(this.getTurno().isKill() && (System.currentTimeMillis() - start) >=3000){
				
				// quando il timer arriva a 3 secondi si torna allo stato normale
				
				this.getTurno().setKill(false);
				
			}
			
		}
		
	}
	
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
		
		// mostro a schermo punteggio giovatore attuale e fps
		
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

	public List <Player> getPlayers() { return players; }

	public void setPlayers(List <Player> players) { this.players = players; }

	public int getHeight() { return height; }

	public void setHeight(int height) { this.height = height; }
	
	public int getWidth() { return width; }

	public void setWidth(int width) { this.width = width; }

	public Map getMap() { return map; }
	
	public String getPath(){
		Random ram = new Random();
		return "res/map/map" + (ram.nextInt(3 - 1) + 1) +  ".png";
		
	}

	//gestisco la tastiera
	
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
