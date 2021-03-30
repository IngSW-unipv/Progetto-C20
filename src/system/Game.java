package system;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import graphics.Menu;

public class Game extends Canvas implements Runnable/*, KeyListener*/{



	/**
	 * 
	 */
	private static final long serialVersionUID = 4818723322554251764L;

	private boolean isRunning = false;
	private int frames=0;
	
	private final int WIDTH =640, HEIGHT = 480; // widht larghezza x -> height lunghezza y 
	
	private  final String TITLE= "Pac-Man";
	
	private Thread thread;
	
	private Level level;  // Level si occupa di gestire il mondo di gioco

	private Menu menu; // si occupa di gestire la parte scritta 
	
	private State state; // Stati del gioco
	
	public Game(){
		Dimension dimension = new Dimension(this.getWIDTH(), this.getHEIGHT());
		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		
		this.state = State.Menu;
		
		this.menu = new Menu(this);
		this.addMouseListener(menu); // Menu reagisce al mouse
		
	}
	
	private synchronized void start(){
		
		if(isRunning){
			
			return;
			
		}else{
			
			isRunning = true;
			thread = new Thread(this);
			thread.start();
			
		}
		
	}
	
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

	// tick aggiorna continuamente la parte logica di quello che ta succedendo a schermo

	private void tick() {
		
		if(state == State.Game){
			
			level.tick();
			
		}else if(state == State.End || state == State.Menu || state == State.Tutorial){
			
			menu.tick();
			
		}
		
	}
	

	// render aggiorna continuamente la parte grafica di ciò che accade a schermo

	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			
			this.createBufferStrategy(3);
			return;
			
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//Stampo a schermo gli fps dell'applicazione
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		int h2 = g.getFontMetrics().getHeight();
		g.drawString("FPS: " + Integer.toString(this.frames), 0, h2);
		
		if(state == State.Game){
			
			level.Render(g);
			
		}else if(state == State.End || state == State.Menu || state == State.Tutorial){
			
			menu.render(g);
			
		}
		
		g.dispose();
		bs.show();
		
	}
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public void setLevel(int n) {
		this.level = new Level(this, n);
	}
	
	public int getHEIGHT() {
		return HEIGHT;
	}

	public int getWIDTH() {
		return WIDTH;
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

}
