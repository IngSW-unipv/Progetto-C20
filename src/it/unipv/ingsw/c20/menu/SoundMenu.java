package it.unipv.ingsw.c20.menu;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import it.unipv.ingsw.c20.system.Game;
import it.unipv.ingsw.c20.system.Music;

public class SoundMenu extends IsMenu{

	public SoundMenu(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
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
		String temp = "Audio :";
		if(Music.isMusic()){
			temp = temp + " yes";
		}else{
			temp = temp + " no";
		}
		
		g.drawString(temp, getGame().getWIDTH() - x, getGame().getHEIGHT()- (y/2));
		g.drawRect(getGame().getWIDTH()-x-1, getGame().getHEIGHT() - y-1, x, y);		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int mx = e.getX();
		int my = e.getY();
		
		int x = 200;
		int y = 64;
		
		
		if(mouseOver(mx, my, getGame().getWIDTH()-x, getGame().getHEIGHT() - y, x, y)){
			//menu
			Music.setMusic(!Music.isMusic());
		}
		
	}


}
