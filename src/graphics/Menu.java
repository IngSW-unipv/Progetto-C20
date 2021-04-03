package graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import system.Game;
import system.State;

public class Menu extends MouseAdapter{
	
	private final Game game;

	/**
	 * Class costructor
	 * @param game to change the enum and start of the level
	 */

	public Menu(Game game){
		
		this.game = game;
		
	}

	/**
	 * this get the colour based on the n of the list
	 * @param n of List
	 * @return colour
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
		}else{
			return "Error";
		}
	}

	/**
	 * Control if the mouse is over the baloon
	 * @param mx x mouse
	 * @param my y mouse
	 * @param x start of the rectangle
	 * @param y start of the rectangle
	 * @param width of the rectangle
	 * @param height of the the rectangle
	 * @return the boolean value to confirm the mouseover
	 */

	public boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		
		if(mx >x && mx< x+width){

			return my > y && my < y + height;
			
		}else{
			
			return false;
			
		}
		
	}

	/**
	 * This method is the logic parte of the Menu,
	 * if you click on something in the if the method will do something
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
			
			if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (0*y), 200, 64)){
				
				//create level con 1 player
				this.game.setLevel(1);
				this.game.setState(State.Game);
				
			}else if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (1*y), 200, 64)){
				
				// 2
				this.game.setLevel(2);
				this.game.setState(State.Game);
				
			}else if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (2*y), 200, 64)){
				
				//3
				this.game.setLevel(3);
				this.game.setState(State.Game);
				
			}else if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (3*y), 200, 64)){
				
				//4
				this.game.setLevel(4);
				this.game.setState(State.Game);
				
			}else if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (4*y), 200, 64)){
				
				//5
				this.game.setLevel(5);
				this.game.setState(State.Game);
				
			}else if(mouseOver(mx, my, (game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (5*y), 200, 64)){
				
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
	 * tick method present in all the class
	 */
	public void tick() {
		
	}

	/**
	 * Control the graphics part of the Menu,
	 * The ending menu is dynamic in base of the number of player
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
			
			int w2 = g.getFontMetrics().stringWidth("1") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (0*y), x, y);
			g.drawString("1", (game.getWIDTH() / 2) - w2, h2+(0*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("2") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2)+(1*y), x, y);
			g.drawString("2", (game.getWIDTH() / 2) - w2, h2+(1*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("3") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2)+(2*y), x, y);
			g.drawString("3", (game.getWIDTH() / 2) - w2, h2+(2*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("4") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (3*y), x, y);
			g.drawString("4", (game.getWIDTH() / 2) - w2, h2+(3*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("5") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (4*y), x, y);
			g.drawString("5", (game.getWIDTH() / 2) - w2, h2+(4*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("How to play") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (5*y), x, y);
			g.drawString("How to play", (game.getWIDTH() / 2) - w2, h2+(5*y)+((game.getHEIGHT() - (6* y))/2));
			
		}else if(game.getState()  == State.Tutorial){
			Font fnt = new Font("arial", Font.BOLD,25);
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			int h2 = g.getFontMetrics().getHeight();
			
			int w2 = g.getFontMetrics().stringWidth("Orange: WASD") / 2;
			g.drawString("Orange: WASD", (game.getWIDTH() / 2) - w2, h2+(0*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Blue: TFGH") / 2;
			g.drawString("Blue: TFGH", (game.getWIDTH() / 2) - w2, h2+(1*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Red: IJKL") / 2;
			g.drawString("Red: IJKL", (game.getWIDTH() / 2) - w2, h2+(2*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Green: Freccette") / 2;
			g.drawString("Green: Freccette", (game.getWIDTH() / 2) - w2, h2+(3*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Yellow: 8456") / 2;
			g.drawString("Yellow: 8456", (game.getWIDTH() / 2) - w2, h2+(4*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Menu") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (5*y), x, y);
			g.drawString("Menu", (game.getWIDTH() / 2) - w2, h2+(5*y)+((game.getHEIGHT() - (6* y))/2));
		}
		
	}
	
}
