package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Enemy2> enemies2 = new ArrayList<Enemy2>();	
	private SpaceShip v;	
	private Timer timer;
	
	private long score = 0;
	private long scoremax = 0;
	private int hp = 3;
	private int toom = 1;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
			timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
				processEnermy2();
			}
		});
		timer.setRepeats(true);
		
	}
	
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	
	private void generateEnemy2(){
		Enemy2 e2 = new Enemy2((int)(Math.random()*390), 30);
		gp.sprites.add(e2);
		enemies2.add(e2);
	}
	
	
	
	private void process(){
		
		if(Math.random() < difficulty/2){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
				if(score>=scoremax)
					scoremax=score;
			}
		}	
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				e.aliveEnemy();
				score=0;
				hp--;
				if(!v.live()){
						die();
						gp.updateGameUI(this);
				}
			}
		}
	}
	
	
	
	private void processEnermy2(){
		if(Math.random() < difficulty/10){
			generateEnemy2();
		}
		
		Iterator<Enemy2> e_iter = enemies2.iterator();
		while(e_iter.hasNext()){
			Enemy2 e2 = e_iter.next();
			e2.proceed();
			if(!e2.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e2);
			}
		}

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy2 e2 : enemies2){
			er = e2.getRectangle();
			if(er.intersects(vr)){
				e2.aliveEnemy();
				score=0;
				toom=0;
				die();
				gp.updateGameUI(this);
			}
		}
	}
	
	
	public void die(){
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.moveX(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.moveX(1);
			break;
		case KeyEvent.VK_UP:
                v.moveY(-1);
                break;
         case KeyEvent.VK_DOWN:
                v.moveY(1);
                break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		}
	}

	public long getScore(){
		return score;
	}
	
	public long getHpScore(){
		return hp;
	}
	
	public long bomb(){
		return toom;
	}
	
	public long getScoreMax(){
		return scoremax;
	}
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
