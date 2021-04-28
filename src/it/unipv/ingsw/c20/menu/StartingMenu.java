package it.unipv.ingsw.c20.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import it.unipv.ingsw.c20.system.Game;

/**
 *  Starting menu that the user will see at first
 * @author Filippo Tagliaferri
 *
 */

public class StartingMenu extends IsMenu{
	
	private IsMenu Music;
	
	
	/**
	 * Class constructor
	 * @param game
	 */
	
	public StartingMenu(Game game){
		 super(game);
		 this.Music = new SoundMenu(this.getGame());
	}

	/**
	 *  gets where the mouse is pressed, in the for create the level
	 *  or it can lead to highscore or tutorial
	 */
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int mx = e.getX();
		int my = e.getY();
		
		int x = 200;
		int y = 64;
				
		for(int i=0; i < 5; i++){
			if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (7* y))/2) + (i*y), 200, 64)){

				this.getGame().setMenu(new NameMenu(this.getGame(), i+1));

			}
		}
		if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (7* y))/2) + (5*y), 200, 64)){
			
			//how to play
			this.getGame().setMenu(new TutorialMenu(this.getGame()));
		}
		if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (7* y))/2) + (6*y), 200, 64)){
			
			//how to play
			this.getGame().setMenu(new HighscoreMenu(this.getGame()));
		}
		
		this.Music.mouseReleased(e);
		
	}

	/**
	 * tick method
	 */
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		this.Music.tick();
	}

	/**
	 * give the option to start a game, control highscore and tutorial
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
		for(int i = 0; i < 5 ; i++){
			w2 = g.getFontMetrics().stringWidth(Integer.toString(i+1)) / 2;
			g.drawRect((getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (7* y))/2) + (i*y), x, y);
			g.drawString(Integer.toString(i+1), (getGame().getWIDTH() / 2) - w2, h2+(i*y)+((getGame().getHEIGHT() - (7* y))/2));
		}

		w2 = g.getFontMetrics().stringWidth("How to play") / 2;
		g.drawRect((getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (7* y))/2) + (5*y), x, y);
		g.drawString("How to play", (getGame().getWIDTH() / 2) - w2, h2+(5*y)+((getGame().getHEIGHT() - (7* y))/2));
		w2 = g.getFontMetrics().stringWidth("Highscore") / 2;
		g.drawRect((getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (7* y))/2) + (6*y), x, y);
		g.drawString("Highscore", (getGame().getWIDTH() / 2) - w2, h2+(6*y)+((getGame().getHEIGHT() - (7* y))/2));
		
		
		this.Music.render(g);
	}

}
