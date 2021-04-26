package it.unipv.ingsw.c20.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import it.unipv.ingsw.c20.constants.State;
import it.unipv.ingsw.c20.system.Game;

/**
 * Games's ending menu
 * @author Filippo Tagliaferri
 *
 */

public class EndingMenu extends IsMenu{
	
	/**
	 * Class constructor
	 * @param game
	 */
	
	public EndingMenu(Game game){
		super(game);
	}
	
	/**
	 * standard tick method
	 */
	
	public void tick(){
		
	}
	
	/**
	 * Take all of the Player's score and show them in turn order
	 * @param g Graphics
	 */
	
	public void render(Graphics g){

		int x = 200;
		int y = 64;
		
		Font fnt = new Font("arial", Font.BOLD, 25);
		g.setFont(fnt);
		g.setColor(Color.WHITE);
		int h2 = g.getFontMetrics().getHeight();
		int w2;
		for(int i = 0; i < this.getGame().getLevel().getPlayers().size(); i++ ){

			w2 = g.getFontMetrics().stringWidth(this.getGame().getLevel().getPlayers().get(i).getNome() + ":" + this.getGame().getLevel().getPlayers().get(i).getScore()) / 2;
			g.drawRect((getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - ((this.getGame().getLevel().getPlayers().size() + 1) * y))/2) +(i*y), x, y);
			g.drawString(this.getGame().getLevel().getPlayers().get(i).getNome() + ": " + this.getGame().getLevel().getPlayers().get(i).getScore() , (getGame().getWIDTH() / 2) - w2, h2+(i*y)+((getGame().getHEIGHT() - ((this.getGame().getLevel().getPlayers().size()+1)* y))/2) );
		
		}
		
		g.drawRect((getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - ((this.getGame().getLevel().getPlayers().size() + 1) * y))/2) +(this.getGame().getLevel().getPlayers().size()*y), x/2, y);
		w2 = g.getFontMetrics().stringWidth("Retry") / 2;
		g.drawString("Retry", (getGame().getWIDTH()/2) - (x/2) + (x/4) - w2, h2+((this.getGame().getLevel().getPlayers().size())*y)+((getGame().getHEIGHT() - ((this.getGame().getLevel().getPlayers().size()+1)* y))/2) );
		
		g.drawRect((getGame().getWIDTH()/2)-(x/2) + x/2, ((getGame().getHEIGHT() - ((this.getGame().getLevel().getPlayers().size() + 1) * y))/2) +(this.getGame().getLevel().getPlayers().size()*y), x/2, y);
		w2 = g.getFontMetrics().stringWidth("Menu") / 2;
		g.drawString("Menu", (getGame().getWIDTH()/2)+(x/4) - w2, h2+((this.getGame().getLevel().getPlayers().size())*y)+((getGame().getHEIGHT() - ((this.getGame().getLevel().getPlayers().size()+1)* y))/2) );
	
	}

	/**
	 * mouseClicked
	 */
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * mouse entered
	 */
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * mouse exited
	 */
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * mousepressed
	 */
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Controls where the mouse is released and can lead to Menu or a retry
	 */
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int mx = e.getX();
		int my = e.getY();
		
		int x = 200;
		int y = 64;
		
		//this.getGame().getMusic().play("res/sounds/mouse.wav", 0);
		
		if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - ((this.getGame().getLevel().getPlayers().size() + 1) * y))/2) +(this.getGame().getLevel().getPlayers().size()*y), x/2, y)){
			
			// ricreo con gli stessi player
			int n = this.getGame().getLevel().getPlayers().size();
			this.getGame().setLevel(n, this.getGame().getLevel().getNomi());
			this.getGame().setState(State.Game);
			
		}
		
		if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2) + x/2, ((getGame().getHEIGHT() - ((this.getGame().getLevel().getPlayers().size() + 1) * y))/2) +(this.getGame().getLevel().getPlayers().size()*y), x/2, y)){

			// menu
			this.getGame().setState(State.Menu);
			
		}
		
	}
}
