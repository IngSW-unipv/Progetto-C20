import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter{
	
	private Game game;
	private Level level;
	
	public Menu(Game game, Level level){
		this.game = game;
		this.level = level;
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
		if(game.state == State.End){
			if(mouseOver(mx, my, (Game.WIDTH/2)-(150/2), 300, 200, 64)){
				int n = this.game.players.size();
				this.game.players.clear();
				this.game.players.add(0, new Player(0, 0, this.game, true, Color.RED));
				for(int i = 1; i < n; i++){
					this.game.players.add(i, new Player(0, 0, this.game, false, Color.green));
				}
				this.game.level = new Level("/map/map.png", this.game);
				this.game.state = State.Game;
			}
		}
		
	}
	

	public void tick() {
		
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if(game.state == State.End){
			for(int i = 0; i < this.game.players.size(); i++ ){
				Font fnt = new Font("arial",1,25);
				g.setFont(fnt);
				g.setColor(Color.WHITE);
				g.drawString("Score: " + this.game.players.get(i).getScore() , 50, 200 + (i * 50));
				g.drawRect((Game.WIDTH/2)-(200/2), 300, 200, 64);
				g.drawString("Retry", (Game.WIDTH/2)-(150/2), 350);
			}
		}
	}
}
