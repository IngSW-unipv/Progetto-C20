package it.unipv.ingsw.c20.actor;

import java.awt.*;
import java.util.Arrays;
import javax.swing.ImageIcon;

import it.unipv.ingsw.c20.constants.Colors;
import it.unipv.ingsw.c20.constants.Commands;
import it.unipv.ingsw.c20.system.Level;

/**
 * Creator and manager of human player movements
 * 
 * @author Daniel Rotaru
 */

public class Player extends Actor {

	private static final long serialVersionUID = -4955740102332399999L;

	private boolean[] direction;
	private int speed = 4;
	private int temp;
	private int imgtemp;
	private boolean kill = false;
	private int score = 0, lvl = 1;
	private boolean turno;
	private int posizione;
	private Colors color;
	private int[] key;
	private Image[] pacman;
	private Image[] fantasmi;

	/**
	 * Class constructor
	 * 
	 * @param x	starting position of the player that will be immediately updated
	 * @param y	starting position of the player that will be immediately updated
	 * @param level	the level in this moment
	 * @param b	player's turn
	 * @param i number of the player
	 * @param c color of the player
	 */
	public Player(int x, int y, Level level, boolean b, int i, Colors c) {
		super(x, y, level);
		this.posizione = i;
		this.setBounds(x, y, 32, 32);
		this.direction = new boolean[4]; // right 0, left 1, up 2, down 3
		this.reset();
		this.turno = b;
		this.color = c;
		this.key = new int[4];
		this.setKey();
		this.setPacman();
		this.setFantasmi();
	}
	
	/**
	 * Sets the game's commands
	 */
	public void setKey(){
		key[0] = Commands.values()[this.posizione].getRight();
		key[1] = Commands.values()[this.posizione].getLeft();
		key[2] = Commands.values()[this.posizione].getUp();
		key[3] = Commands.values()[this.posizione].getDown();
	}
	
	/**
	 * Resets the direction's vector
	 */
	public void reset() {

		Arrays.fill(this.direction, false);

	}
	
	public void move(int key){
		
		if(key == this.key[0]){
			
			this.moveRight();
			
		}
		
		if(key == this.key[1]){
			
			this.moveLeft();
			
		}
		
		if(key == this.key[2]){
			
			this.moveUp();
			
		}
		
		if(key == this.key[3]){
			
			this.moveDown();
			
		}
	}

	/**
	 * Method used for moving the player to the right
	 */
	public void moveRight() {
		this.direction[0] = true;
		this.setTemp(0); // used to save directions

	}

	/**
	 * Method used for moving the player to the left
	 */
	public void moveLeft() {
		this.direction[1] = true;
		this.setTemp(1); // used to save directions

	}

	/**
	 * Method used for moving the player upwards
	 */
	public void moveUp() {
		this.direction[2] = true;
		this.setTemp(2); // used to save directions

	}

	/**
	 * Method used for moving the player downwards
	 */
	public void moveDown() {
		this.direction[3] = true;
		this.setTemp(3); // used to save the directions
	}

	/**
	 * Method used to understand if the next rectangle (32x32) is a rectangle in
	 * which you can move (true) or not (false)
	 * 
	 * @param nextX
	 * @param nextY
	 * @return boolean
	 */
	public boolean canMove(int nextX, int nextY) {

		Rectangle bounds = new Rectangle(nextX, nextY, this.width, this.height); // Rectangle creation

		for (int xx = 0; xx < this.getLevel().getMap().getTiles().length; xx++) {

			for (int yy = 0; yy < this.getLevel().getMap().getTiles()[0].length; yy++) {

				if (this.getLevel().getMap().getTiles()[xx][yy] != null) {

					if (bounds.intersects(this.getLevel().getMap().getTiles()[xx][yy])) {

						return false;

					}

				}

			}

		}

		return true;

	}

	/**
	 * Tick continuously updates the logical side of what it is happening on the screen
	 * in order to manage the movement
	 */
	public void tick() {

		if (direction[0] && canMove(x + speed, y)) {
			// Each time you press a key it sets the corresponding value to true to figure
			// out where to go and if it can go right

			x += speed; // increase the variable x for the movement right
			this.imgtemp = 0; // variable for the position of the pacman direction

			for (int i = 0; i < direction.length; i++) {

				if (i != 0 && i != temp) {

					direction[i] = false;

				}

			}

		}

		if (direction[1] && canMove(x - speed, y)) {

			x -= speed; // decrease the variable x for the movement left
			this.imgtemp = 1; // variable for the position of the pacman direction

			for (int i = 0; i < direction.length; i++) {

				if (i != 1 && i != temp) {

					direction[i] = false;

				}

			}

		}

		if (direction[2] && canMove(x, y - speed)) {

			y -= speed; // increase the variable y for the movement down
			this.imgtemp = 2; // variable for the position of the pacman direction

			for (int i = 0; i < direction.length; i++) {

				if (i != 2 && i != temp) {

					direction[i] = false;

				}

			}

		}

		if (direction[3] && canMove(x, y + speed)) {

			y += speed; // increase the variable y for the movement up
			this.imgtemp = 3; // variable for the position of the pacman direction

			for (int i = 0; i < direction.length; i++) {

				if (i != 3 && i != temp) {

					direction[i] = false;

				}

			}

		}

	}

	/**
	 * Graphic rendering of the player with differentiation in case of ghost or pacman
	 */
	public void render(Graphics g) {
		if (!this.turno) {
			if (this.getLevel().getTurno().isKill()) {

				g.setColor(Color.WHITE);
				g.drawImage(fantasmi[0], this.x, this.y, null);
				String time = Integer
						.toString(3 - ((int) ((System.currentTimeMillis() - this.getLevel().getStart()) / 1000)));
				g.drawString(time, x + 16, y + 16);
			} else {
				g.drawImage(fantasmi[1], this.x, this.y, null);
			}

		} else {
			g.drawImage(this.pacman[this.imgtemp], this.x, this.y, null);
		}

	}

	// Getter and Setters

	/**
	 * Turno's getter
	 * 
	 * @return player's turno.
	 */
	public boolean isTurno() {

		return turno;

	}

	/**
	 * Turno's setter
	 * 
	 * @param turno of the player.
	 */
	public void setTurno(boolean turno) {

		this.turno = turno;

	}

	/**
	 * Kill's getter
	 * 
	 * @return player's kill.
	 */
	public boolean isKill() {

		return kill;

	}

	/**
	 * Kill's setter
	 * 
	 * @param kill if can eat.
	 */
	public void setKill(boolean kill) {

		this.kill = kill;

	}

	/**
	 * Speed's setter
	 * 
	 * @param speed is the value how far can i move.
	 */
	public void setSpeed(int speed) {

		this.speed = speed;

	}

	/**
	 * Score's getter
	 * 
	 * @return player's score.
	 */
	public int getScore() {

		return score;

	}

	/**
	 * Turno's setter
	 * 
	 * @param score is the points the player earned.
	 */
	public void setScore(int score) {

		this.score = score;

	}

	/**
	 * Lvl's getter
	 * 
	 * @return player's lvl.
	 */
	public int getLvl() {

		return lvl;

	}

	/**
	 * Turno's setter
	 * 
	 * @param lvl is the current level.
	 */
	public void setLvl(int lvl) {

		this.lvl = lvl;

	}

	/**
	 * Turno's setter
	 * 
	 * @param temp.
	 */
	public void setTemp(int temp) {

		this.temp = temp;

	}

	/**
	 * Color's getter
	 * 
	 * @return player's color
	 */
	public Colors getColor() {
		return color;
	}

	/**
	 * Color's setter
	 * 
	 * @param color is the color of the player.
	 */
	public void setColor(Colors color) {
		this.color = color;
	}

	/**
	 * Pacman's setter set the vector to load images of the pacman into memory
	 */
	public void setPacman() {
		pacman = new Image[this.direction.length];
		pacman[0] = new ImageIcon("res/players/playerR" + (this.posizione + 1) + ".png").getImage();
		pacman[1] = new ImageIcon("res/players/playerL" + (this.posizione + 1) + ".png").getImage();
		pacman[2] = new ImageIcon("res/players/playerU" + (this.posizione + 1) + ".png").getImage();
		pacman[3] = new ImageIcon("res/players/playerD" + (this.posizione + 1) + ".png").getImage();
		this.imgtemp = 0;
	}

	/**
	 * Fantasma's setter sets the vector to load images of the ghost
	 */
	public void setFantasmi() {
		fantasmi = new Image[2];
		fantasmi[0] = new ImageIcon("res/players/fantasma" + (this.posizione + 1) + ".png").getImage();
		fantasmi[1] = new ImageIcon("res/players/fantasm" + (this.posizione + 1) + ".png").getImage();
	}

	/**
	 * Fantasma's getter
	 * 
	 * @param n
	 * @return the n-th ghost.
	 */
	public Image getFantasma(int n) {
		return this.fantasmi[n];
	}

}
