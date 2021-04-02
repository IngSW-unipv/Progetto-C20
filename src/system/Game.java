package system;

import graphics.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;



public class Game extends Canvas implements Runnable/*, KeyListener*/{
	
	/**
	 * Game's core class, the main is here.
	 * 
	 * @author Mattia Seravalli
	 * 
	 * @param isRunning boolean that describe if the game is running or not
	 * @param frames 
	 * @param TITLE window's title
	 * @param level facade controller, it will manage game's logic
	 * @param menu will manage the written part of the game
	 * @param state describes the game's state
	 * 
	 */
	
	private static final long serialVersionUID = 4818723322554251764L;

	private boolean isRunning = false;
	
	private int frames=0;

	private  final String TITLE= "Pac-Man";
	
	private Thread thread;
	
	private Level level;  // Level si occupa di gestire il mondo di gioco

	private final Menu menu; // si occupa di gestire la parte scritta
	
	private State state; // Stati del gioco
	
	public Game(){
		
		/**
		 * The constructor, sets the first game's state (menu), creates the menu and add menu's listener. 
		 */
		
		Dimension dimension = new Dimension(this.getWIDTH(), this.getHEIGHT());
		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		
		//Partirò sempre prima dal menù, quindi imposto menù come primo state.
		this.state = State.Menu;
		
		//Creo il menu.
		this.menu = new Menu(this);
		//Aggiungo il listener così che menù possa reagire al mouse.
		this.addMouseListener(menu); 
	}
	
	private synchronized void start(){
		//Inizia il gioco
		
		if(isRunning){

		}else{
			
			isRunning = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private synchronized void stop(){
		//Chiude il gioco
		
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
	
	public void run(){
		
		/**
		 * Game's core, manages the fps system, creating the illusion of a moving picture.
		 * @param lastTime it's the timer of the system (nanoseconds), used for delta's calculation
		 * @param timer it's the time of the system (milliseconds), used for delta's calculation
		 * @param amountOfTicks it's the amount of tick per second
		 * @param ns indicates the amount of nanoseconds in every tick
		 * @param delta when he is increased by one it will indicates that a tick is passed.
		 * @param fps frames per second
		 */
		//Il cuore del gioco, la funzione principale che permette al gioco di aggiornarsi in continuazione
		
		this.requestFocusInWindow(); //Quando viene premuto un tasto, il tasto verrà ascoltato dal rispettivo listener.
		long lastTime = System.nanoTime(); //Mi da il valore del timer di sistema in nanosecondi, serve a calcolare delta.
		long timer = System.currentTimeMillis(); //Mi da il tempo attuale in millisecondi, serve a calcolare delta.
		double amountOfTicks = 60.0; //Indica l'ammontare dei tic al secondo.
		double ns = 1e9/amountOfTicks; //Indica l'ammontare di nanosecondi per ogni tick.
		double delta = 0;
		int fps = 0;
		
		while(isRunning){
	
			long now = System.nanoTime();
			delta += (now - lastTime)/ns; //Così facendo delta ora avrà l'unità di misura di un tick.
			lastTime = now;
			//Ogni volta che delta viene aumentato di uno significa che è passato un tick.
			
			while(delta >=1){
				//dato che è passato un tick chiamo il metodo tick.
				this.tick();
				//Ora riporto delta a 0.
				delta--;
			}
			
			//Aggiorno la finestra renderizzandola.
			this.render();
			//Aumento di 1 gli fps dato che ho aggiornato la finestra.
			fps++;
			
			if(System.currentTimeMillis() - timer >1000){
				//Scrive gli fps una volta ogni secondo, controllando current time.
				timer += 1000; //Aggiorno il timer per essere 1 secondo in ritardo.
				//Aggiorno i frames e riporto fps a 0, ciò avviene una volta ogni secondo.
				this.frames = fps;
				//Frames indica quindi i frames al secondo.
				fps = 0;	
			}
		}

		stop();	//Ferma il gioco.	
	}

	private void tick() {
		// Tick aggiorna continuamente la parte logica di quello che sta succedendo a schermo
		
		if(state == State.Game){
			//Se sto giocando aggiorna la finestra usando le immagini di level.
			level.tick();
		}else if(state == State.End || state == State.Menu || state == State.Tutorial){
			//Se ho finito di giocare, sono nel menu o nel tutorial, aggiorno la finestra usando le immagini del menu.
			menu.tick();
		}
	}

	
	private void render() {
		// Render aggiorna continuamente la parte grafica della finestra.
		//BufferStrategy è utile per organizzare gli elementi in una finestra.
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			//In caso bs fosse nullo vado a creare 3 Buffer.
			this.createBufferStrategy(3);
			return;
		}
		
		//Creo un contesto grafico per il buffer.
		Graphics g = bs.getDrawGraphics();
		
		//Stampo a schermo gli fps dell'applicazione
		do {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		int h2 = g.getFontMetrics().getHeight();
		g.drawString("FPS: " + this.frames, 0, h2);
		
		if(state == State.Game){
			//Se sto giocando renderizzo da level.
			level.Render(g);
		}else if(state == State.End || state == State.Menu || state == State.Tutorial){
			//Se non sto giocando renderizzo da menu.
			menu.render(g);
		}
		
		g.dispose();
		//Il buffer disegnato diventa il buffer per JFrame.
		bs.show(); 
		
		//Chiudo il ciclo do-while con una prevenzione di sicurezza sulla perdita dei frames del buffer.
		}while(bs.contentsLost()); 
	}
	
	//Getter e Setter
	public Level getLevel() {
		/** Level's getter */
		return level;
	}
	
	public State getState() {
		/** State's getter */
		return state;
	}

	public void setState(State state) {
		/** State's setter */
		this.state = state;
	}
	
	public void setLevel(int n) {
		/** Level's setter */
		this.level = new Level(this, n);
	}
	
	public int getHEIGHT() {
		/** Height's getter */
		//Height --> Lunghezza (Y).
		return 480;
	}

	public int getWIDTH() {
		/** Width's getter */
		//Width --> Larghezza (X).
		return 640;
	}
	
	public static void main(String[] args){
		/** Main: creates the game, form the window, start the game */
		
		//Creo un gioco.
		Game game = new Game();
		
		//Impostazioni della finestra.
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
