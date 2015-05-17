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
	private int life = 5;
	
	//add Enemy2	
	private ArrayList<Enemy2> enemies2 = new ArrayList<Enemy2>();
	
	private SpaceShip v;	
	private ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		gp.startUI(this);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
				process2(); //add enemy2
				processBullet(); //add bullet
			}
		});
		timer.setRepeats(true);
		gp.startUI(this);
	}
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score  = 100;
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double br;
		
		for(Enemy e : enemies){
			er = e.getRectangle();
			
			for(Bullet b : bullet){
				br = b.getRectangle();
				if(br.intersects(er)){
					score += 100;
					gp.sprites.remove(b);
					b.bulletCrash();
					e.checkCrash();
				}
			}
			
			if(er.intersects(vr)){
				die();
				e.checkCrash();
				gp.updateGameUI(this);
				return;
			}
		}
	}
	
	// add process2
	private void process2(){
		if(Math.random() < difficulty){
			generateEnemy2();
		}
		
		Iterator<Enemy2> e_iter = enemies2.iterator();
		while(e_iter.hasNext()){
			Enemy2 e2 = e_iter.next();
			e2.proceed();
			
			if(!e2.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e2);
				score += 100;
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double br2;
		for(Enemy2 e2 : enemies2){
			er = e2.getRectangle();
			
			for(Bullet b : bullet){
				br2 = b.getRectangle();
				if(br2.intersects(er)){
					score += 100;
					gp.sprites.remove(b);
					b.bulletCrash();
					e2.checkCrash();
				}
			}
			
			if(er.intersects(vr)){
				die();
				e2.checkCrash();
				gp.updateGameUI(this);
				return;
			}
		}
	}

	//add process3 bullet
	private void processBullet(){
  		Iterator<Bullet> b_iter = bullet.iterator();
 		while(b_iter.hasNext()){
 			Bullet b = b_iter.next();
 			b.proceed();
 			
 			if(!b.isAlive()){
 				b_iter.remove();
 				gp.sprites.remove(b);
 			}
 		}
  		gp.updateGameUI(this);
  		Rectangle2D.Double vr = v.getRectangle();
 		Rectangle2D.Double er;
		for(Bullet b : bullet){
 			er = b.getRectangle();
 			if(er.intersects(vr)){
 				score  = 10;			
 				b.bulletCrash();
 				gp.updateGameUI(this);
 				return;
 			}
 		}
 	}
	
	public void start(){
		timer.start();
	}
	
	public void die(){
			life--;
			if(life < 1)
				timer.stop();
	}
	
	public int getLife(){
		return life;
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1,0);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1,0);
			break;
		case KeyEvent.VK_UP:
			v.move(0,-1);
			break;
		case KeyEvent.VK_DOWN:
			v.move(0,1);
			break;
		/* case KeyEvent.VK_D:
			difficulty += 0.1;
			break; */
		case KeyEvent.VK_ENTER:
			start();
			break;
		case KeyEvent.VK_SPACE:
			fire();
			break;
		}
	}

	public long getScore(){
		return score;
	}
	
	private void fire(){
		Bullet b = new Bullet((v.x) + (v.width/2), v.y);
		gp.sprites.add(b);
		bullet.add(b);
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
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	
	// add generateEnemy2
	private void generateEnemy2(){
		Enemy2 e2 = new Enemy2((int)(Math.random()*390), 30);
		gp.sprites.add(e2);
		enemies2.add(e2);
	}

}
