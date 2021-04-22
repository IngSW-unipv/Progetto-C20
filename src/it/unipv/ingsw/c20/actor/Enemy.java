package it.unipv.ingsw.c20.actor;

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
	 * @param nextX
	 * @param nextY
	 * @return boolean
	 */
	public boolean canMove(int nextX, int nextY) {

		Rectangle bounds = new Rectangle(nextX, nextY, this.width, this.height);

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
			String time = Integer.toString(3 - ((int) ((System.currentTimeMillis() - this.getLevel().getStart()) / 1000)));
			g.drawString(time, x + 16, y + 16);

		} else {

			g.drawImage(this.getFantasma(1), this.x, this.y, null);

		}

	}

}
