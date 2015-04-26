package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Image; 
import java.io.IOException; 
import java.io.File; 
import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Image bg;
	private Image bg1;
	private Image bg2;
	private Image bg3;
	

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.RED);
		try{
			bg = ImageIO.read(new File("D:\\war2.jpg"));
			bg1 = ImageIO.read(new File("D:\\bomb.jpg"));
			bg2 = ImageIO.read(new File("D:\\gameover1.png"));
			bg3 = ImageIO.read(new File("D:\\live1.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void updateGameUI(GameReporter reporter){
		//big.clearRect(0, 0, 400, 600);
		big.drawImage(bg,0,0,400,600, this);
		big.setColor(Color.WHITE);		
		big.drawString(String.format("%08d", reporter.getScore()), 300, 20);
		big.drawString(String.format("%08d", reporter.getScoreMax()), 200, 20);

		if(reporter.getScore()==0){
			//big.drawString(String.format("Level 1"),170, 300);
			//big.drawString(String.format("Game Over"), 170, 300);
			//("Level 1",170, 300);
		}
		if(reporter.getScore()==500){
			big.drawString(String.format("Level 2"),170, 300);
			//big.drawString(String.format("Game Over"), 170, 300);
			//("Level 1",170, 300);
		}
		
		int x=5;
		for(int i=0 ; i < reporter.getlive(); i++){
			big.drawImage(bg3, x , 5, null);
			x+=20;
		}
		
	
		if(reporter.getlive()==0)
			big.drawImage(bg2,0,0,400,600, this);
		
		if(reporter.bomb()==0){
			big.drawImage(bg1,0,0,400,600, this);
		}
		
		
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
