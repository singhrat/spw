package f2.spw;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Image;

public class Life extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 12;
	private boolean alive = true;
	
	public Life(int x, int y) {
		super(x, y, 15, 15);
		
	}

	@Override
	public void draw(Graphics2D g) {
		Image img = Toolkit.getDefaultToolkit().getImage("D:\\live1.png");
      g.drawImage(img,x, y, width, height, null);
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public boolean aliveEnemy(){
		return	alive = false;
	}
	
}