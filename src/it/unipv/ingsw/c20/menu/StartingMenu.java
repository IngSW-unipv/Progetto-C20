package it.unipv.ingsw.c20.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import it.unipv.ingsw.c20.constants.State;
import it.unipv.ingsw.c20.system.Game;

public class StartingMenu extends IsMenu{
	
	
	public StartingMenu(Game game){
		 super(game);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int mx = e.getX();
		int my = e.getY();
		
		int x = 200;
		int y = 64;
		
		this.getGame().getMusic().play("res/sounds/mouse.wav", 0);
		
		for(int i=0; i < 5; i++){
			if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (7* y))/2) + (i*y), 200, 64)){

				this.getGame().setState(State.Naming);
				this.getGame().getMenu().setNGiocatori(i+1);

			}
		}
		if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (7* y))/2) + (5*y), 200, 64)){
			
			//how to play
			this.getGame().setState(State.Tutorial);
		}
		if(mouseOver(mx, my, (getGame().getWIDTH()/2)-(x/2), ((getGame().getHEIGHT() - (7* y))/2) + (6*y), 200, 64)){
			
			//how to play
			this.getGame().setState(State.Highscore);
		}
		
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

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
		
	}

}
