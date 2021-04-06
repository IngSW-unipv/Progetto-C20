package it.unipv.ingsw.c20.graphic;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.unipv.ingsw.c20.system.Game;
import it.unipv.ingsw.c20.system.State;

public class Menu extends MouseAdapter{
	
	private final Game game;

	/**
	 * Class costructor
	 * @author Filippo Tagliaferri
	 * 
	 * @param game changes the enum and the start of the level
	 */
	public Menu(Game game){
		
		this.game = game;
		
	}

	/**
	 * Gets the color based on the "n" of the list
	 * @param n number of List
	 * @return color
	 */
	public String getColore(int n){
		if(n == 0){
			return "Orange";
		}else if(n == 1){
			return "Blue";
		}else if(n == 2){
			return "Red";
		}else if(n == 3){
			return "Green";
		}else if(n == 4){
			return "Yellow";
		}
		return "Error";
	}

	/**
	 * Gets the key based on the "n" of the List
	 * @param n number of List
	 * @return keys
	 */
	public String getTasti(int n){
		if(n == 0){
			return "WASD";
		}else if(n == 1){
			return "TFGH";
		}else if(n == 2){
			return "IJKL";
		}else if(n == 3){
			return "Freccette";
		}else if(n == 4){
			return "8456";
		}
		return "Error";
	}

	/**
	 * Controls if the mouse is over the baloon
	 * 
	 * @param mx mouse's x
	 * @param my mouse's y
	 * @param x rectangle's beginning position
	 * @param y rectangle's beginning position
	 * @param width rectangle's width
	 * @param height rectangle's height
	 * @return the boolean value to confirm if the cursor is on the baloon
	 */
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		
		if(mx >x && mx< x+width){

			return my > y && my < y + height;
			
		}else{
			
			return false;
			
		}
		
	}

	/**
	 * This method describes the logical part of the Menu,
	 * if you click something on the menu, the if method will do something
	 * @param e mouseEvent
	 */

	public void mousePressed(MouseEvent e){
		
		int mx = e.getX();
		int my = e.getY();
		
		int x = 200;
		int y = 64;
		
		if(game.getState() == State.End){
			
			if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size() + 1) * y))/2) +(this.game.getLevel().getPlayers().size()*y), x/2, y)){
				
				// ricreo con gli stessi player
				int n = this.game.getLevel().getPlayers().size();
				this.game.setLevel(n);
				this.game.setState(State.Game);
				
			}
			
			if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2) + x/2, ((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size() + 1) * y))/2) +(this.game.getLevel().getPlayers().size()*y), x/2, y)){

				// menu
				this.game.setState(State.Menu);
				
			}
			
		}else if(game.getState()  == State.Menu){
			for(int i=0; i < 5; i++){
				if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (i*y), 200, 64)){

					//create level con 1 player
					this.game.setLevel(i+1);
					this.game.setState(State.Game);

				}
			}
			if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (5*y), 200, 64)){
				
				//how to play
				this.game.setState(State.Tutorial);
			}
			
		}else if(game.getState() == State.Tutorial){
			if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (5*y), 200, 64)){
				//menu
				this.game.setState(State.Menu);
			}
		}
		
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
		
		int x = 200;
		int y = 64;
		
		if(game.getState()  == State.End){

			Font fnt = new Font("arial", Font.BOLD, 25);
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			int h2 = g.getFontMetrics().getHeight();
			
			for(int i = 0; i < this.game.getLevel().getPlayers().size(); i++ ){
	
				int w2 = g.getFontMetrics().stringWidth(this.getColore(i) + ":" + this.game.getLevel().getPlayers().get(i).getScore()) / 2;
				g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size() + 1) * y))/2) +(i*y), x, y);
				g.drawString(this.getColore(i) + ": " + this.game.getLevel().getPlayers().get(i).getScore() , (game.getWIDTH() / 2) - w2, h2+(i*y)+((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size()+1)* y))/2) );
			
			}
			
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size() + 1) * y))/2) +(this.game.getLevel().getPlayers().size()*y), x/2, y);
			int w2 = g.getFontMetrics().stringWidth("Retry") / 2;
			g.drawString("Retry", (game.getWIDTH()/2) - (x/2) + (x/4) - w2, h2+((this.game.getLevel().getPlayers().size())*y)+((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size()+1)* y))/2) );
			
			g.drawRect((game.getWIDTH()/2)-(x/2) + x/2, ((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size() + 1) * y))/2) +(this.game.getLevel().getPlayers().size()*y), x/2, y);
			w2 = g.getFontMetrics().stringWidth("Menu") / 2;
			g.drawString("Menu", (game.getWIDTH()/2)+(x/4) - w2, h2+((this.game.getLevel().getPlayers().size())*y)+((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size()+1)* y))/2) );
		
		}else if(game.getState()  == State.Menu){
			
			Font fnt = new Font("arial", Font.BOLD,25);
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			int h2 = g.getFontMetrics().getHeight();
			int w2;
			for(int i = 0; i < 5 ; i++){
				w2 = g.getFontMetrics().stringWidth(Integer.toString(i+1)) / 2;
				g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (i*y), x, y);
				g.drawString(Integer.toString(i+1), (game.getWIDTH() / 2) - w2, h2+(i*y)+((game.getHEIGHT() - (6* y))/2));
			}

			w2 = g.getFontMetrics().stringWidth("How to play") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (5*y), x, y);
			g.drawString("How to play", (game.getWIDTH() / 2) - w2, h2+(5*y)+((game.getHEIGHT() - (6* y))/2));
			
		}else if(game.getState()  == State.Tutorial){
			Font fnt = new Font("arial", Font.BOLD,25);
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			int h2 = g.getFontMetrics().getHeight();
			int w2;
			for(int i = 0; i < 5; i++){
				w2 = g.getFontMetrics().stringWidth(this.getColore(i) + ":" + this.getTasti(i)) / 2;
				g.drawString(this.getColore(i) + ":" + this.getTasti(i), (game.getWIDTH() / 2) - w2, h2+(i*y)+((game.getHEIGHT() - (6* y))/2));
			}

			w2 = g.getFontMetrics().stringWidth("Menu") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (5*y), x, y);
			g.drawString("Menu", (game.getWIDTH() / 2) - w2, h2+(5*y)+((game.getHEIGHT() - (6* y))/2));
		}
		
	}
	
}
