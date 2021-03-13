import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener{



	/**
	 * 
	 */
	private static final long serialVersionUID = 4818723322554251764L;

	private boolean isRunning = false;
	private int frames=0;
	
	public static final int WIDTH =640, HEIGHT = 480;
	public static final String TITLE= "Pac-Man";
	
	private Thread thread;
	
	public List <Player> players;  //Lista di giocatori attivi, poi potrà essere cambiata da un menù per ora è 2(il massimo è 4 in caso di modifiche)
	public Level level;  // Level si occupa di gestire il mondo di gioco
	public Menu menu; // si occupa di gestire la parte scritta ma per ora ha solo una simil leaderboard
	
	private int n = 2;
	public State state;
	
	
	
	public Game(){
		Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);
		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		
		players = new ArrayList<>();
		state = State.Game;
		
		// sotto una creazione dei giocatori molto base e stupida visto che dal due in poi son tutti verdi
		
		players.add(0, new Player(0, 0, this, true, Color.RED));
		for(int i = 1; i < n; i++){
			players.add(i, new Player(0, 0, this, false, Color.green));
		}
		
		level = new Level("/map/map.png", this);
		menu = new Menu(this, level);
		
		this.addKeyListener(this);  // reagisce alla tastiera
		this.addMouseListener(menu); // Menu reagisce al mouse
		
	}
	
	// roba da copiare e incollare
	
	private synchronized void start(){
		if(isRunning){
			return;
		}else{
			isRunning = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	// roba da copiare e incollare
	
	private synchronized void stop(){
		if(!isRunning){
			return;
		}else{
			isRunning = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//roba da copiare e incollare
	
	public void run(){
		this.requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double amountOfTicks = 60.0;
		double ns = 1e9/amountOfTicks;
		double delta = 0;
		int fps = 0;
		
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while(delta >=1){
				this.tick();
				delta--;
			}
			this.render();
			fps++;
			
			if(System.currentTimeMillis() - timer >1000){
				timer += 1000;
				//System.out.println(fps);
				this.frames = fps;
				fps = 0;
			}
		}
		stop();
	}

	// i tick gestiscono gli aggiornamenti della situazione

	private void tick() {
		if(state == State.Game){
			level.tick();
		}else if(state == State.End){
			menu.tick();
		}
	}
	

	// aggiornamento parte grafica, insieme a tick è come l'app funziona

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.WIDTH, this.HEIGHT);
		g.setColor(Color.white);
		g.drawString("FPS: " + Integer.toString(this.frames), 0, 10);
		if(state == State.Game){
			level.Render(g);
		}else if(state == State.End){
			menu.render(g);
		}
		g.dispose();
		bs.show();
	}
	
	// prendo il player attivo come pacman
	
	public Player getTurno(){
		for(int i = 0; i < n; i++){
			if(players.get(i).isTurno()){
				return players.get(i);
			}
		}
		return null;
	}
	
	// prendo l'index del giocatore pacman
	
	public int getIndex(){
		for(int i = 0; i < n; i++){
			if(players.get(i).isTurno()){
				return i;
			}
		}
		return (Integer) null;
	}
	
	public static void main(String[] args){
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle(game.TITLE);
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		game.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		// Player 1
		
		if(e.getKeyCode() == KeyEvent.VK_W){
			players.get(0).direction[2] = true;
			players.get(0).setTemp(2);
			if(players.get(0).canMove(players.get(0).x, players.get(0).y - players.get(0).getSpeed())){
				players.get(0).direction[0] = false;
				players.get(0).direction[1] = false;
				players.get(0).direction[3] = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			players.get(0).direction[3] = true;
			players.get(0).setTemp(3);
			if(players.get(0).canMove(players.get(0).x ,players.get(0).y + players.get(0).getSpeed())){
				players.get(0).direction[0] = false;
				players.get(0).direction[1] = false;
				players.get(0).direction[2] = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			players.get(0).direction[1] = true;
			players.get(0).setTemp(1);
			if(players.get(0).canMove(players.get(0).x - players.get(0).getSpeed() ,players.get(0).y)){
				players.get(0).direction[0] = false;
				players.get(0).direction[2] = false;
				players.get(0).direction[3] = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			players.get(0).direction[0] = true;
			players.get(0).setTemp(0);
			if(players.get(0).canMove(players.get(0).x + players.get(0).getSpeed() ,players.get(0).y)){
				players.get(0).direction[1] = false;
				players.get(0).direction[2] = false;
				players.get(0).direction[3] = false;
			}
		}
		
		//Player 2
		
		if(e.getKeyCode() == KeyEvent.VK_UP){
			players.get(1).direction[2] = true;
			players.get(1).setTemp(2);
			if(players.get(1).canMove(players.get(1).x, players.get(1).y - players.get(1).getSpeed())){
				players.get(1).direction[0] = false;
				players.get(1).direction[1] = false;
				players.get(1).direction[3] = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			players.get(1).direction[3] = true;
			players.get(1).setTemp(3);
			if(players.get(1).canMove(players.get(1).x ,players.get(1).y + players.get(1).getSpeed())){
				players.get(1).direction[0] = false;
				players.get(1).direction[1] = false;
				players.get(1).direction[2] = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			players.get(1).direction[1] = true;
			players.get(1).setTemp(1);
			if(players.get(1).canMove(players.get(1).x - players.get(1).getSpeed() ,players.get(1).y)){
				players.get(1).direction[0] = false;
				players.get(1).direction[2] = false;
				players.get(1).direction[3] = false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			players.get(1).direction[0] = true;
			players.get(1).setTemp(0);
			if(players.get(1).canMove(players.get(1).x + players.get(1).getSpeed() ,players.get(1).y)){
				players.get(1).direction[1] = false;
				players.get(1).direction[2] = false;
				players.get(1).direction[3] = false;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P){
			if(level.paused){
				level.paused = false;
			}else if(!level.paused){
				level.paused = true;
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
