package it.unipv.ingsw.c20.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import it.unipv.ingsw.c20.constants.Colors;
import it.unipv.ingsw.c20.constants.Commands;
import it.unipv.ingsw.c20.system.Game;

/**
 *  Menu tutorial
 * @author Filippo Tagliaferri
 *
 */

public class TutorialMenu extends IsMenu{
	
	/**
	 * Class constructor
	 * @param game
	 */
	
	public TutorialMenu(Game game){
		super(game);
	}

	/**
	 * gets if the palyer click on menu
	 */
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int mx = e.getX();
		int my = e.getY();
		
		int x = 200;
		int y = 64;
		
		//this.getGame().getMusic().play("res/sounds/mouse.wav", 0);
		
		if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (6* y))/2) + (5*y), 200, 64)){
			//menu
			this.getGame().setMenu(new StartingMenu(this.getGame()));
		}
	}

	/**
	 * tick method
	 */
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Render the commands of the player getted in the Commands enum
	 */
	
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
		int x = 200;
		int y = 64;
		
		Font fnt = new Font("arial", Font.BOLD,25);
		g.setFont(fnt);
		g.setColor(Color.WHITE);
		int h2 = g.getFontMetrics().getHeight();
		int w2;
		for(int i = 0; i < 5; i++){
			w2 = g.getFontMetrics().stringWidth(Colors.values()[i].getColorName() + ":" + Commands.values()[i].getString()) / 2;
			g.drawString(Colors.values()[i].getColorName() + ":" + Commands.values()[i].getString(), (getGame().getWIDTH() / 2) - w2, h2+(i*y)+((getGame().getHEIGHT() - (6* y))/2));
		}

		w2 = g.getFontMetrics().stringWidth("Menu") / 2;
		g.drawRect((getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (6* y))/2) + (5*y), x, y);
		g.drawString("Menu", (getGame().getWIDTH() / 2) - w2, h2+(5*y)+((getGame().getHEIGHT() - (6* y))/2));
	}

}
