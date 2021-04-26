package it.unipv.ingsw.c20.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import it.unipv.ingsw.c20.constants.State;
import it.unipv.ingsw.c20.scores.Scores;
import it.unipv.ingsw.c20.system.Game;

/**
 * Games's naming menu
 * @author Filippo Tagliaferri
 *
 */

public class HighscoreMenu extends IsMenu {
	
	/**
	 * Class constructor
	 * @param game
	 */
	
	public HighscoreMenu(Game game) {
		// TODO Auto-generated constructor stub
		super(game);
	}

	/**
	 * mouse clicked
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
	 *  mouse exited
	 */
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * mouse pressed
	 */
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Controls if the user want go back to the menu
	 */
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int mx = e.getX();
		int my = e.getY();
		
		int x = 200;
		int y = 64;
		
		//this.getGame().getMusic().play("res/sounds/mouse.wav", 0);
		
		if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2) + x/2, ((getGame().getHEIGHT() - ((this.getGame().getScores().getScores().size() + 1) * y))/2) +(this.getGame().getScores().getScores().size()*y), x/2, y)){
			
			// menu
			this.getGame().setState(State.Menu);
			
		}
	}

	/**
	 * standard tick method
	 */
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Show the top 8 score saved in a txt file
	 */
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
		
		int x = 200;
		int y = 64;
		
		Font fnt = new Font("arial", Font.BOLD, 25);
		g.setFont(fnt);
		g.setColor(Color.WHITE);
		int h2 = g.getFontMetrics().getHeight();
		int i = 0;
		
			for(Scores score : this.getGame().getScores().getScores()){
				int w2 = g.getFontMetrics().stringWidth(score.toString()) / 2;
				g.drawRect((getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - ((this.getGame().getScores().getScores().size() + 1) * y))/2) +(i*y), x, y);
				g.drawString(score.toString() , (getGame().getWIDTH() / 2) - w2, h2+(i*y)+((getGame().getHEIGHT() - ((this.getGame().getScores().getScores().size()+1)* y))/2) );
				i++;
			}
		
		int w2 = g.getFontMetrics().stringWidth("Menu") / 2;
		g.drawRect((getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - ((this.getGame().getScores().getScores().size() + 1) * y))/2) +(this.getGame().getScores().getScores().size()*y), x, y);
		g.drawString("Menu" , (getGame().getWIDTH() / 2) - w2, h2+((this.getGame().getScores().getScores().size())*y)+((getGame().getHEIGHT() - ((this.getGame().getScores().getScores().size()+1)* y))/2) );
		
	}

}
