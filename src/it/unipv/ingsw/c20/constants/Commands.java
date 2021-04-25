package it.unipv.ingsw.c20.constants;

import java.awt.event.KeyEvent;

public enum Commands {
	// right, left, up, down
	Player1(KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_S),
	Player2(KeyEvent.VK_H, KeyEvent.VK_F, KeyEvent.VK_T, KeyEvent.VK_G),
	Player3(KeyEvent.VK_L, KeyEvent.VK_J, KeyEvent.VK_I, KeyEvent.VK_K),
	Player4(KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN),
	Player5(KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD5);
	
	private int right, left, up, down;
	
	private Commands(int right, int left, int up, int down){
		this.right = right;
		this.left = left;
		this.up = up;
		this.down = down;
	}

	
	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}
	
	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}
	
	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}
	
	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public String getString(){
		return KeyEvent.getKeyText((int)this.right) + KeyEvent.getKeyText((int)this.left) + KeyEvent.getKeyText((int)this.up) + KeyEvent.getKeyText((int)this.down);
	}


}
