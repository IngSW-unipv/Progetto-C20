package it.unipv.ingsw.c20.actor;

import it.unipv.ingsw.c20.map.Tile;
import it.unipv.ingsw.c20.system.Level;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creator and manager of IA ghosts
 * 
 * @author Daniel Rotaru
 */

public class Enemy extends Player {

	private static final long serialVersionUID = 1L;

	private int dir;

	public int speed;

	private Random r;
	private int tmp = 4;
	private List<Integer> t;
	private String tempo = "";

	/**
	 * Class constructor
	 * 
	 * @param x	starting position of the player that will be immediately updated
	 * @param y	starting position of the player that will be immediately updated
	 * @param l starting speed
	 * @param level	the level in this moment
	 */
	public Enemy(int x, int y, int l, Level level) {

		super(x, y, level, false, 4, "");
		r = new Random();
		this.setBounds(x, y, 32, 32);
		dir = r.nextInt(4);
		this.speed = l;
		this.setFantasmi();
		t = new ArrayList<>();

	}

	/**
	 * Method used to understand if the next rectangle (32x32) is a rectangle in
	 * which you can move (true) or not (false)
	 * 
	 * @param nextX next X
	 * @param nextY next Y
	 * @return boolean
	 */
	public boolean canMove(int nextX, int nextY) {

		Rectangle bounds = new Rectangle(nextX, nextY, this.width, this.height);
		
		for(Tile t : this.getLevel().getMap().getTiles()){
			if (bounds.intersects(t)) {

				return false;

			}
		}

		return true;

	}

	/**
	 * Tick continuously updates the logical side of what it is happening on the screen
	 * in order to manage the movement
	 */
	public void tick() {

		if (this.getLevel().getTurno().isKill()) {
			this.tempo = Integer.toString(3 - ((int) ((System.currentTimeMillis() - this.getLevel().getStart()) / 1000)));
		}
		
		t.clear();

		if (canMove(x + speed, y)) {
			t.add(0);
		}
		if (canMove(x - speed, y)) {
			t.add(1);
		}
		if (canMove(x, y - speed)) {
			t.add(2);
		}
		if (canMove(x, y + speed)) {
			t.add(3);
		}

		if (t.size() > tmp) {
			dir = t.get(r.nextInt(t.size()));
		}

		// direction right 0
		if (dir == 0) {

			if (canMove(x + speed, y)) {

				x += speed;
				tmp = t.size();
			} else {

				dir = t.get(r.nextInt(t.size()));

			}

		}

		// direction left 1
		if (dir == 1) {

			if (canMove(x - speed, y)) {

				x -= speed;
				tmp = t.size();
			} else {

				dir = t.get(r.nextInt(t.size()));

			}

		}

		// direction up 2
		if (dir == 2) {

			if (canMove(x, y - speed)) {

				y -= speed;
				tmp = t.size();
			} else {

				dir = t.get(r.nextInt(t.size()));

			}

		}

		// direction down 3
		if (dir == 3) {

			if (canMove(x, y + speed)) {

				y += speed;
				tmp = t.size();
			} else {

				dir = t.get(r.nextInt(t.size()));

			}

		}

	}

	/**
	 * Graphic rendering of the ghost with the implementation of a timer
	 */
	public void render(Graphics g) {

		if (this.getLevel().getTurno().isKill()) {

			g.setColor(Color.WHITE);
			g.drawImage(this.getFantasma(0), this.x, this.y, null);
			int h2 = g.getFontMetrics().getHeight();
			int w2 = g.getFontMetrics().stringWidth(tempo) / 2;
				
			g.drawString(tempo, x-w2+16 , y + 16 + h2/2);

		} else {

			g.drawImage(this.getFantasma(1), this.x, this.y, null);

		}

	}

}
