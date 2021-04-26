package it.unipv.ingsw.c20.menu;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import it.unipv.ingsw.c20.constants.State;
import it.unipv.ingsw.c20.system.Game;
import it.unipv.ingsw.c20.system.Music;

/**
 *  manage all the menus
 * @author Filippo Tagliaferri
 *
 */

public class Menu extends IsMenu{
	
	private EndingMenu end;
	private TutorialMenu tutorial;
	private StartingMenu menu;
	private HighscoreMenu high;
	private NameMenu name;
	private int NGiocatori;

	/**
	 * Number of player getter
	 * @return the number of player
	 */
	
	public int getNGiocatori() {
		return NGiocatori;
	}

	/**
	 * set the number of player
	 * @param nGiocatori number of player
	 */
	
	public void setNGiocatori(int nGiocatori) {
		NGiocatori = nGiocatori;
	}

	/**
	 * Class costructor
	 * @param game changes the enum and the start of the level
	 */
	public Menu(Game game){
		
		super(game);
		this.end = new EndingMenu(getGame());
		this.tutorial = new TutorialMenu(getGame());
		this.menu = new StartingMenu(getGame());
		this.high = new HighscoreMenu(getGame());
		this.name = new NameMenu(getGame());
	}

	/**
	 * Tick method present in all the classes
	 */
	public void tick() {
		
	}

	/**
	 * Controls the graphic part of the Menu,
	 * The ending menu is dynamic according to the number of players
	 * @param g Graphics
	 */
	public void render(Graphics g) {
		
		if(getGame().getState()  == State.End){
			this.end.render(g);
		}else if(getGame().getState()  == State.Menu){
			this.menu.render(g);
		}else if(getGame().getState()  == State.Tutorial){
			this.tutorial.render(g);;
		}else if(getGame().getState()  == State.Highscore){
			this.high.render(g);;
		}else if(getGame().getState()  == State.Naming){
			this.name.render(g);
		}
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
	
	/**
	 * mouse pressed
	 */
	
	@Override
	public void mousePressed(MouseEvent e){
		// TODO Auto-generated method stub
	}

	/**
	 * mouse clicked
	 */
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 *  mouse entered
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
	 * This method describes the logical part of the Menu,
	 * if you click something on the menu, the if method will do something
	 * @param e mouseEvent
	 */
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int mx = e.getX();
		int my = e.getY();
		
		int x = 200;
		int y = 64;
		
		if(getGame().getState() == State.End){
			
			this.end.mouseReleased(e);
			
		}else if(getGame().getState()  == State.Menu){
			
			this.menu.mouseReleased(e);
			
		}else if(getGame().getState() == State.Tutorial){
			
			this.tutorial.mouseReleased(e);
			
		}else if(getGame().getState() == State.Highscore){
			
			this.high.mouseReleased(e);
			
		}else if(getGame().getState()  == State.Naming){
			
			this.name.mouseReleased(e);
			
		}
		
		if(mouseOver(mx, my, getGame().getWIDTH()-x, getGame().getHEIGHT() - y, x, y)){
			//menu
			Music.setMusic(!Music.isMusic());
		}
		
	}
	
}
