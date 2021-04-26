package it.unipv.ingsw.c20.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import it.unipv.ingsw.c20.constants.State;
import it.unipv.ingsw.c20.system.Game;

/**
 * Naming menu
 * @author Filippo Tagliaferri
 *
 */

public class NameMenu extends IsMenu implements KeyListener{
	
	private String nome;
	private List<String> nomi; //lista dei punti piccoli nel gioco
	
	/**
	 * Class constructor
	 * @param game
	 */
	
	public NameMenu(Game game){
		super(game);
		this.nome = "";
		this.getGame().addKeyListener(this);
		this.nomi = new ArrayList<>();
	}

	/**
	 * mouse clicked
	 */
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * mouse entered
	 */
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * mouse exited
	 */
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 *  mouse pressed
	 */
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * mouse released
	 */
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * tick method
	 */
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	/**
	 *  Render the a while that control if the users have entered all the names
	 */
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		//int x = 200;
		int y = 64;

		Font fnt = new Font("arial", Font.BOLD,25);
		g.setFont(fnt);
		g.setColor(Color.WHITE);
		int h2 = g.getFontMetrics().getHeight();
		if( this.nomi.size() < this.getGame().getMenu().getNGiocatori()){
			int w2 = g.getFontMetrics().stringWidth("Nome player " + this.nomi.size()+1 + " :") / 2;
			g.drawString("Nome player " + (this.nomi.size()+1) + " :", (getGame().getWIDTH() / 2) - w2, h2+(0*y)+((getGame().getHEIGHT() - (0* y))/2));
		}else{
			//create level with n player
			this.getGame().setLevel(this.getGame().getMenu().getNGiocatori(), this.nomi);
			this.nome = "";
			this.nomi.clear();
			this.getGame().setState(State.Game);
		}
		int w2 = g.getFontMetrics().stringWidth(nome + 1) / 2;
		g.drawString(nome, (getGame().getWIDTH() / 2) - w2, h2+(1*y)+((getGame().getHEIGHT() - (1* y))/2));
	}

	/**
	 * Control if you have entered a letter,
	 * if you press backspace it eliminate a char 
	 * and pressing enter save the name
	 */
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(getGame().getState()  == State.Naming){
				if(this.nome.toCharArray().length < 10){
					char code = e.getKeyChar();
					if ((code >= 'A' && code <= 'Z') || (code >= 'a' && code <= 'z')){
						this.nome = this.nome + e.getKeyChar();
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if(this.nome.toCharArray().length > 0){
						this.nome = this.nome.substring(0, this.nome.toCharArray().length-1);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					for(int i = 0; i < nomi.size(); i++  ){
						if(nomi.get(i).equals(nome) && !nome.equals("")){
							this.nome = "";
							return;
						}
					}
					this.nomi.add(this.nome);
					this.nome = "";
				}
		}
	}

	/**
	 * key released
	 */
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 *  key typed
	 */
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
