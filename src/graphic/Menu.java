package graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import system.Game;
import system.State;

public class Menu extends MouseAdapter{
	
	private Game game;
	
	public Menu(Game game){
		
		this.game = game;
		
	}
	
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		
		if(mx >x && mx< x+width){
			
			if(my >y && my< y+height){
				
				return true;
				
			}else{
				
				return false;
				
			}
			
		}else{
			
			return false;
			
		}
		
	}
	
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
	

	public void tick() {
		
	}

	public void render(Graphics g) {
		
		int x = 200;
		int y = 64;
		
		if(game.getState()  == State.End){
			
			Font fnt = new Font("arial",1,25);
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			int h2 = g.getFontMetrics().getHeight();
			
			for(int i = 0; i < this.game.getLevel().getPlayers().size(); i++ ){
				
				int w2 = g.getFontMetrics().stringWidth("Score " + (i+1)+ ": " + this.game.getLevel().getPlayers().get(i).getScore()) / 2;
				g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size() + 1) * y))/2) +(i*y), x, y);
				g.drawString("Score " + (i+1)+ ": " + this.game.getLevel().getPlayers().get(i).getScore() , (game.getWIDTH() / 2) - w2, h2+(i*y)+((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size()+1)* y))/2) );
			
			}
			
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size() + 1) * y))/2) +(this.game.getLevel().getPlayers().size()*y), x/2, y);
			int w2 = g.getFontMetrics().stringWidth("Retry") / 2;
			g.drawString("Retry", (game.getWIDTH()/2) - (x/2) + (x/4) - w2, h2+((this.game.getLevel().getPlayers().size())*y)+((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size()+1)* y))/2) );
			
			g.drawRect((game.getWIDTH()/2)-(x/2) + x/2, ((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size() + 1) * y))/2) +(this.game.getLevel().getPlayers().size()*y), x/2, y);
			w2 = g.getFontMetrics().stringWidth("Menu") / 2;
			g.drawString("Menu", (game.getWIDTH()/2)+(x/4) - w2, h2+((this.game.getLevel().getPlayers().size())*y)+((game.getHEIGHT() - ((this.game.getLevel().getPlayers().size()+1)* y))/2) );
		
		}else if(game.getState()  == State.Menu){
			
			Font fnt = new Font("arial",1,25);
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
			Font fnt = new Font("arial",1,25);
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			int h2 = g.getFontMetrics().getHeight();
			
			int w2 = g.getFontMetrics().stringWidth("Player 1: WASD") / 2;
			g.drawString("Player 1: WASD", (game.getWIDTH() / 2) - w2, h2+(0*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Player 2: IJKL") / 2;
			g.drawString("Player 2: IJKL", (game.getWIDTH() / 2) - w2, h2+(1*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Player 3: Freccette") / 2;
			g.drawString("Player 3: Freccette", (game.getWIDTH() / 2) - w2, h2+(2*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Player 4: 8456") / 2;
			g.drawString("Player 4: 8456", (game.getWIDTH() / 2) - w2, h2+(3*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Player 5: TFGH") / 2;
			g.drawString("Player 5: TFGH", (game.getWIDTH() / 2) - w2, h2+(4*y)+((game.getHEIGHT() - (6* y))/2));
			
			w2 = g.getFontMetrics().stringWidth("Menu") / 2;
			g.drawRect((game.getWIDTH()/2)-(x/2), ((game.getHEIGHT() - (6* y))/2) + (5*y), x, y);
			g.drawString("Menu", (game.getWIDTH() / 2) - w2, h2+(5*y)+((game.getHEIGHT() - (6* y))/2));
		}
		
	}
	
}
