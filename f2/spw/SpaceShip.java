package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpaceShip extends Sprite{

	int step = 8;
	private Image b1;
	private String img = "D:\\icon1.gif" ;
	boolean live =true;
	int i=15;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Graphics2D g) {
		try{
			b1 = ImageIO.read(new File(img));
		} catch(IOException e){
			e.printStackTrace();
		}
		g.drawImage(b1, x, y, width, height, null);
		
	}

	public void moveX(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}
	public void moveY(int direction){
		y += (step * direction);
		if(y < 0)
			y = 0;
		if(y > 600)
			y = 600;
	}
	public boolean isalive(){
		i--;
		if(i==0)
			return false;
		else
			return true;
		
	}
}
