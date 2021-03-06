package it.unipv.ingsw.c20.system;

import it.unipv.ingsw.c20.constants.State;
import it.unipv.ingsw.c20.menu.IsMenu;
import it.unipv.ingsw.c20.menu.StartingMenu;
import it.unipv.ingsw.c20.scores.ScoreReader;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.List;

   /**
	 * Game's core class, the main is here.
	 * 
	 * @author Mattia Seravalli 
	 */
public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 4818723322554251764L;

	private boolean isRunning = false;
	
	private int frames=0;

	private  final String TITLE= "Pac-Man";
	
	private Thread thread;
	
	private Level level;  // it will manage game's logic.

	private IsMenu menu; // it will manage the written part of the game.
	
	private State state; // it describes the game's state.
	
	private int WIDTH, HEIGHT;
	
	private ScoreReader scores; 
	
	private final String mapPath = "res/maps/map.png";

	/** The constructor sets the first game's state (menu), creates the menu, adds menu's listener and creates the score. */
	public Game(){
		
		//The game will start every time from the menu, so the menu will be the first state.
		this.state = State.Menu;

		this.scores = new ScoreReader();
		
		//Creates the menu.
		this.setMenu(new StartingMenu(this));
		
		//The window will modifies his dimension in relation to the map's dimension.
		try {
			this.WIDTH = ImageIO.read(new File(this.mapPath)).getWidth();
			this.HEIGHT  = ImageIO.read(new File(this.mapPath)).getHeight();
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
		
		Dimension dimension = new Dimension(this.getWIDTH(), this.getHEIGHT());
		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		

	}
	
	private synchronized void start(){
		//Starts the game, play music, create the thread.
		
		if(!isRunning){
			
			isRunning = true;
			new Music("res/sounds/background.wav", Clip.LOOP_CONTINUOUSLY);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private synchronized void stop(){
		//Stops the game, stops the thread.
		
		if(!isRunning){
			return;
		}else{
			isRunning = false;
			
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	
  /** Game's core, manages the fps system, creating the illusion of a moving picture, implemented from Runnable 
   *  interface.
   */ 
	public void run(){
		//This is the game's core, the master function that allows the game to be updated continuously.
		
		this.requestFocusInWindow(); 				//When a key is pressed, it will be listened from the proper listener.
		long lastTime = System.nanoTime(); 			//it's the timer of the system (nanoseconds), used for delta's calculation.
		long timer = System.currentTimeMillis(); 	//it's the time of the system (milliseconds), used for delta's calculation.
		double amountOfTicks = 60.0; 				//it's the amount of tick per second.
		double ns = 1e9/amountOfTicks; 				//indicates the amount of nanoseconds in every tick.
		double delta = 0; 							//when delta is increased by one it will indicate that a tick is passed.
		int fps = 0; 								//fps frames per second.
		
		while(isRunning){
	
			long now = System.nanoTime();
			delta += (now - lastTime)/ns; 			//Delta now has a tick as measure unit.
			lastTime = now;							//When delta is increased by one , it means that a tick has passed.
			
			
			while(delta >=1){
				//One tick has passed so I call tick method.
				this.tick();
				
				//Brings back delta to 0.
				delta--;
			}
			
			//Updates window renderizing it.
			this.render();
			
			//I've updated the window so now I will increase fps by one.
			fps++;
			
			if(System.currentTimeMillis() - timer >1000){
				//It writes fps one per second controlling timer.
				timer += 1000; 						//Updates the time to be one second late.
				
				//Updates frames and brings back to 0 fps, this will happen once per second.
				this.frames = fps;
				
				fps = 0;	
			}
		}

		stop();	//stops the game.	
	}

	
	private void tick() {
		// Tick continuously updates the logical side of what is happening on the screen. 
		
		if(state == State.Game){
			//If i'm playing it will update the window using level's images.
			level.tick();
			
		}else {
			//If I'm not playing I'm in the menu or in the tutorial, so I will update the window using the menu's images.
			menu.tick();
		}
	}

	
	private void render() {
		//Render continuously update the graphic side of what it is happening on the screen. 
		
		//BufferStrategy is used to organize window's elements.
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			//If bs is null then it creates 3 buffers.
			this.createBufferStrategy(3);
			return;
		}
		
		//Creates a graphic context for the buffer. 
		Graphics g = bs.getDrawGraphics();
		
		//Prints on the screen game's fps.
		do {
			Font fnt = new Font("arial", Font.BOLD,25);
			g.setFont(fnt);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			if(state == State.Game){
				
				level.Render(g);
				
			}else if(state == State.Menu){
		
				menu.render(g);
				
			}
			g.setColor(Color.white);
			int h2 = g.getFontMetrics().getHeight();
			g.drawString("FPS: " + this.frames, 0, h2);
			
			g.dispose();
			//The drawn buffer will become JFrame's buffer.
			bs.show(); 
			
			//Close the loop do-while with a security prevention, because it could lose some frames in the buffer.
		}while(bs.contentsLost()); 
	}
	

	
	//Getter e Setter
	
	/** Level's getter
	 * @return game's level. */
	public Level getLevel() { return level; }
	
	/** State's getter 
	 * @return game's state. */
	public State getState() { return state; }
	
	/** State's setter 
	 * @param state game's state requested to be modified. */
	public void setState(State state) { this.state = state; }
	
	/** Level's setter 
	 * @param n number of the level requested.
	 * @param nomi name of the players
	 */
	public void setLevel(int n, List<String> nomi) {
		this.removeKeyListener(this.menu);
		this.removeMouseListener(this.menu);
		this.level = new Level(this, n, nomi);
		}
	
	/** Height's getter 
	 * @return window's height. */
	public int getHEIGHT() { return HEIGHT*32; }
	
	/** Width's getter 
	 * @return window's width. */
	public int getWIDTH() {  return WIDTH*32; }
	
	/** return the scores reader
	 * @return the object that read the scores
	 */
	public ScoreReader getScores() { return scores; }

	/** return menu 
	 * @return the menuitem */
	public IsMenu getMenu() { return menu; }
	
	/**
	 * set the menu adjusting the key and the mouse listener
	 * @param menu this new menu
	 */
	
	public void setMenu(IsMenu menu){
		this.removeKeyListener(this.menu);
		this.removeMouseListener(this.menu);
		this.menu = menu;
		//Adds the listener so that the menu can react to the mouse's movements.
		this.addMouseListener(this.menu);
		this.addKeyListener(this.menu);
	}
	
	/** return the path 
	 * @return the path of the map */
	public String getMapPath() { return mapPath; }

	/** 
	 * Main: creates the game, forms the window, starts the game 
	 *
	 * @param args standard main parameter.
	 */
	public static void main(String[] args){
		
		
		//Creates the game.
		Game game = new Game();
		
		//Window settings.
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
