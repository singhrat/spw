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
	private ArrayList<Life> enemies3 = new ArrayList<Life>();	
	private SpaceShip v;	
	private Timer timer;
	
	private long score = 0;
	private long scoremax = 0;
	private double difficulty = 0.1;
	public int i;
	private int lifes = 5;
	private boolean check = true;
	
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		gp.sprites.add(v);
			timer = new Timer(70, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
				processEnermy2();
				processLife();
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
	private void life(){
		Life e3 = new Life((int)(Math.random()*390), 30);
		gp.sprites.add(e3);
		enemies3.add(e3);
	}
	
	
	
	private void process(){
		
		if(Math.random() < difficulty/5&&score<=500){
			generateEnemy();
		}
		if(Math.random() < difficulty/3&& score>500&& score<=1000){
			generateEnemy();
		}
		if(Math.random() < difficulty/2&& score>1000&& score<=1500){
			generateEnemy();
		}
		Iterator<Enemy> e_iter = enemies.iterator() ;
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score = score + 100;
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
				lifes--;
				if(lifes == 0){
						die();
						score=0;
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
				die();
				score=0;
				lifes=0;
				gp.updateGameUI(this);
			}
		}
	}
	
	private void processLife(){
		if(Math.random() < difficulty/10){
			life();
		}
		Iterator<Life> e_iter = enemies3.iterator();
		while(e_iter.hasNext()){
			Life e3 = e_iter.next();
			e3.proceed();
			if(!e3.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e3);
			}
		}
		gp.updateGameUI(this);
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Life e3 : enemies3){
			er = e3.getRectangle();
			if(er.intersects(vr)){
				e3.aliveEnemy();
				if(lifes<5)
					lifes++;
				gp.updateGameUI(this);
			}
		}
	}
	
	
	public void die(){
		timer.stop();
		check = false;
	}
	
	public void restart(){
		for(Enemy e : enemies){
			gp.sprites.remove(e);
			e.aliveEnemy();
		}
		for(Enemy2 e2 : enemies2){
			gp.sprites.remove(e2);
			e2.aliveEnemy();
		}
		for(Life e3 : enemies3){
			gp.sprites.remove(e3);
			e3.aliveEnemy();
		}
		check = true;
		lifes = 5;
		v.x = 180;
		v.y = 550;
		gp.sprites.add(v);
		start();
		gp.updateGameUI(this);
		score=0;
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
		case KeyEvent.VK_R:
				restart();
				break;
		case KeyEvent.VK_D:
				difficulty += 0.1;
				break;
		}
	}

	public long getScore(){
		return score;
	}
	
	public long getlive(){
		return lifes;
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
