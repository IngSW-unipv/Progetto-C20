package it.unipv.ingsw.c20.constants;

import java.awt.event.KeyEvent;

/**
 * Enum that indicate the commands of the players
 * @author Filippo Tagliaferri
 *
 */

public enum Commands {
	// right, left, up, down
	Player1(KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_S),
	Player2(KeyEvent.VK_H, KeyEvent.VK_F, KeyEvent.VK_T, KeyEvent.VK_G),
	Player3(KeyEvent.VK_L, KeyEvent.VK_J, KeyEvent.VK_I, KeyEvent.VK_K),
	Player4(KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN),
	Player5(KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD5);
	
	private int right, left, up, down;
	
	/**
	 * Costructor of the commands of the player
	 * @param right key
	 * @param left key
	 * @param up key
	 * @param down key
	 */
	private Commands(int right, int left, int up, int down){
		this.right = right;
		this.left = left;
		this.up = up;
		this.down = down;
	}

	/**
	 * right key getter
	 * @return the key to go right
	 */
	
	public int getRight() {
		return right;
	}

	/**
	 * right key setter
	 * @param right key to go right
	 */
	
	public void setRight(int right) {
		this.right = right;
	}
	
	/**
	 * left key getter
	 * @return the key to go left
	 */
	
	public int getLeft() {
		return left;
	}

	/**
	 * left key setter
	 * @param left key to go left
	 */
	
	public void setLeft(int left) {
		this.left = left;
	}
	
	/**
	 * up key getter
	 * @return the key to go up
	 */
	
	public int getUp() {
		return up;
	}

	/**
	 * up key setter
	 * @param up key to go up
	 */
	
	public void setUp(int up) {
		this.up = up;
	}
	
	/**
	 * down key getter
	 * @return the key to go down
	 */
	
	public int getDown() {
		return down;
	}
	
	/**
	 * down key setter
	 * @param down key to go down
	 */
	
	public void setDown(int down) {
		this.down = down;
	}

	/**
	 * Return the string that contains all the commands
	 * @return all of this commands in String for the menu 
	 */
	
	public String getString(){
		return KeyEvent.getKeyText((int)this.right) + KeyEvent.getKeyText((int)this.left) + KeyEvent.getKeyText((int)this.up) + KeyEvent.getKeyText((int)this.down);
	}


}
