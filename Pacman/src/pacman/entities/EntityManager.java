package pacman.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import pacman.Handler;
import pacman.entities.creatures.Player;
import pacman.entities.creatures.ghosts.BlueGhost;
import pacman.entities.creatures.ghosts.OrangeGhost;
import pacman.entities.creatures.ghosts.PinkGhost;
import pacman.entities.creatures.ghosts.RedGhost;
import pacman.gfx.Assets;
import pacman.states.GameOverState;
import pacman.states.State;

public class EntityManager {

	private Handler handler;
	private Player player;
	private Fruits fruits;
	private Dots dots;
	private RedGhost redGhost;
	private OrangeGhost orangeGhost;
	private PinkGhost pinkGhost;
	private BlueGhost blueGhost;
	private int points = 0;
	private int dotsEaten=0;
	private ArrayList<Entity> entities;
	private int LevelCount=1;
	
	private long fear;
	
	private long roundTimer = System.currentTimeMillis();
	private long stopTime = 0;
	
	private long timeSinceLastDot = System.currentTimeMillis();
	
	private boolean readyToUp = false;
	private boolean playerLost=false;
	private boolean eating=false;
	
	private int phase = 1;
	
	
	
	Font newfont;
	
	public EntityManager(Handler handler, Player player, Fruits fruits, Dots dots, RedGhost redGhost, BlueGhost blueGhost, PinkGhost pinkGhost, OrangeGhost orangeGhost) {
		this.handler = handler;
		this.player = player;
		this.fruits = fruits;
		this.dots = dots;
		this.redGhost = redGhost;
		this.blueGhost = blueGhost;
		this.pinkGhost = pinkGhost;
		this.orangeGhost = orangeGhost;
		entities = new ArrayList<Entity>();
		fruits.setLevel(getLevelCount());
		
		
		addEntity(dots);
		addEntity(fruits);
		addEntity(redGhost);
		addEntity(blueGhost);
		addEntity(pinkGhost);
		addEntity(orangeGhost);
		addEntity(player);
		
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, new File("emulogic.tff")).deriveFont(30f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("emulogic.tff")));
		}
		catch(IOException | FontFormatException e) {
			
		}
		
	}
	
	long eatTimer;
	public void tick() {
		if(!eating) {
			eatTimer = System.currentTimeMillis();
			if(!playerLost) {
				redGhost();
				pinkGhost();
				orangeGhost();
				blueGhost();
				
			for(int k = 0;k < entities.size();k++) {
				Entity e = entities.get(k);
				e.tick();
			}
	
			
			
			fruits.makeFruit(dotsEaten);
			isScared();
			testCollisionPoints();
			updateGhostMovement();
			releaseGhosts();
			
			
			if(dotsEaten>=dots.getAllDots()) {
				setLevelCount(getLevelCount()+1);
				dotsEaten=0;
				fruits.reset();
				handler.getWorld().changeLevelState();
				
			}
			//System.out.println(getRoundTime());
			
			pacmanDeath();
			}
			else {
				player.tick();
				if(!player.isDead()) {
					reset();
	
					playerLost = false;
		
				}
			}
		}
		else {
			long timer = System.currentTimeMillis();
			if(redGhost.isEaten()) {
				redGhost();
				redGhost.tick();
			}
			if(pinkGhost.isEaten()) {
				pinkGhost();
				pinkGhost.tick();
			}
			if(orangeGhost.isEaten()) {
				orangeGhost();
				orangeGhost.tick();
			}
			if(blueGhost.isEaten()) {
				blueGhost();
				blueGhost.tick();
			}
			if(timer >= (eatTimer + 0.5*1000))
				setEating(false);
			
			
		}
		
	}
	

	
	Font myFont = new Font ("emulogic", 1, 25);
	
	public void render(Graphics g) {
		for(Entity e : entities) {
			e.render(g);
		}
		g.setFont(myFont);
		g.setColor(Color.white);
		g.drawString("Points: "+points, 5, 30);
		g.drawString("Lives: "+player.getLives(), 420, 880);

	}
	int killStreak=0;
	int killStreakPoints=200;
	boolean ghostScared = false;
	public void pacmanDeath() {
		if(redGhost.isFrightened()||orangeGhost.isFrightened()||pinkGhost.isFrightened()||blueGhost.isFrightened()) {
			ghostScared=true;
		}
		else {
			killStreak=0;
			killStreakPoints=200;
			ghostScared=false;
		}
			if(player.bounds.intersects(redGhost.bounds)&&!redGhost.isFrightened()&&!redGhost.isEaten())
			{
				player.death();
				playerLost = true;
			}
			if(player.bounds.intersects(orangeGhost.bounds)&&!orangeGhost.isFrightened()&&!orangeGhost.isEaten())
			{
				player.death();
				playerLost = true;
			}
			if(player.bounds.intersects(pinkGhost.bounds)&&!pinkGhost.isFrightened()&&!pinkGhost.isEaten())
			{
				player.death();
				playerLost = true;
			}
			if(player.bounds.intersects(blueGhost.bounds)&&!blueGhost.isFrightened()&&!blueGhost.isEaten())
			{
				player.death();
				playerLost = true;
			}
		

			
			if(player.bounds.intersects(redGhost.bounds)&&redGhost.isFrightened())
			{
				redGhost.death();
				setEating(true);
				if(ghostScared)
					killStreak++;
				points += killStreakPoints;
				killStreakPoints=killStreakPoints*2;
				
			}
			if(player.bounds.intersects(orangeGhost.bounds)&&orangeGhost.isFrightened())
			{
				orangeGhost.death();
				setEating(true);
				if(ghostScared)
					killStreak++;
				points += killStreakPoints;
				killStreakPoints=killStreakPoints*2;
			}
			if(player.bounds.intersects(pinkGhost.bounds)&&pinkGhost.isFrightened())
			{
				pinkGhost.death();
				setEating(true);
				if(ghostScared)
					killStreak++;
				points += killStreakPoints;
				killStreakPoints=killStreakPoints*2;
			}
			if(player.bounds.intersects(blueGhost.bounds)&&blueGhost.isFrightened())
			{
				blueGhost.death();
				setEating(true);
				if(ghostScared)
					killStreak++;
				points += killStreakPoints;
				killStreakPoints=killStreakPoints*2;
			}
			
			
		
		
	}
	
	public void setEating(boolean eating) {
		this.eating = eating;
	}


	public void setFrightened() {
		redGhost.setFrightened(true);
		pinkGhost.setFrightened(true);
		blueGhost.setFrightened(true);
		orangeGhost.setFrightened(true);
	}
	long test;
	long lastTime;
	public void isScared() {
		long timer = System.currentTimeMillis();
		
		
		if(redGhost.isFrightened()||orangeGhost.isFrightened()||pinkGhost.isFrightened()||blueGhost.isFrightened())
			stopTime = (timer - test)/1000 + lastTime;
		else {
			test  = System.currentTimeMillis();
			lastTime = stopTime;
		}
	
			if(timer>=(fear+7*1000)) {
				if(redGhost.isFrightened()) {
					redGhost.setFrightened(false);
					redGhost.setFrightenedExpiring(false);
				}
				if(orangeGhost.isFrightened()) {
					orangeGhost.setFrightened(false);
					orangeGhost.setFrightenedExpiring(false);	
				}
				if(pinkGhost.isFrightened()) {
					pinkGhost.setFrightened(false);
					pinkGhost.setFrightenedExpiring(false);
				}
				if(blueGhost.isFrightened()) {
					blueGhost.setFrightened(false);
					blueGhost.setFrightenedExpiring(false);
				}
			}
			else if(timer>=(fear+5*1000)) {
			
				if(redGhost.isFrightened())
					redGhost.setFrightenedExpiring(true);
				if(blueGhost.isFrightened())
					blueGhost.setFrightenedExpiring(true);
				if(pinkGhost.isFrightened())
					pinkGhost.setFrightenedExpiring(true);
				if(orangeGhost.isFrightened())
					orangeGhost.setFrightenedExpiring(true);
				

			}
		}
	
	public void releaseGhosts() {
		long timer = System.currentTimeMillis();
		//System.out.println((timer-timeSinceLastDot)/1000);
		//System.out.println(""+(timer-timeSinceLastDot));
		if(redGhost.isInCage()) {
			
			if(getLevelCount()>=1) {
				redGhost.setDotsLimit(0);
			}
			
			redGhost.setRedDotsCounter(dotsEaten);
			if(dotsEaten>=redGhost.getDotsLimit()) {
				redGhost.setLeavingCage(true);
			}
			if(timer>=(timeSinceLastDot+0*1000)) {
				timeSinceLastDot = System.currentTimeMillis();
				redGhost.setLeavingCage(true);
			}
		}
		
		if(pinkGhost.isInCage()) {
			
			if(getLevelCount()>=1) {
				pinkGhost.setDotsLimit(0);
			}
			
			pinkGhost.setPinkDotsCounter(dotsEaten);
			if(dotsEaten>=pinkGhost.getDotsLimit()) {
				pinkGhost.setLeavingCage(true);
			}
			if(timer>=(timeSinceLastDot+0*1000)) {
				timeSinceLastDot = System.currentTimeMillis();
				pinkGhost.setLeavingCage(true);
			}
		}
		
		else if(blueGhost.isInCage()) {
			//System.out.println("IS IN CAGE BLUE");
			
			if(getLevelCount()==1) {
				blueGhost.setDotsLimit(30);
			}
			if(getLevelCount()>=2) {
				blueGhost.setDotsLimit(0);
			}
			
			blueGhost.setBlueDotsCounter(dotsEaten-pinkGhost.getPinkDotsCounter());
			if(dotsEaten>=blueGhost.getDotsLimit()) {
				blueGhost.setLeavingCage(true);
			}
			if(timer>=(timeSinceLastDot+4*1000)) {
				timeSinceLastDot = System.currentTimeMillis();
				blueGhost.setLeavingCage(true);
			}
		}
		
		else if(orangeGhost.isInCage()) {
			
			if(getLevelCount()==1) {
				orangeGhost.setDotsLimit(60);
			}
			if(getLevelCount()==2) {
				orangeGhost.setDotsLimit(50);
			}
			if(getLevelCount()>=3) {
				orangeGhost.setDotsLimit(0);
			}
			
			orangeGhost.setOrangeDotsCounter((dotsEaten)-blueGhost.getBlueDotsCounter());
			if(dotsEaten>=orangeGhost.getDotsLimit()) {
				orangeGhost.setLeavingCage(true);
			}
			if(timer>=(timeSinceLastDot+4*1000)) {
				timeSinceLastDot = System.currentTimeMillis();
				orangeGhost.setLeavingCage(true);
			}
		}
		
		if(redGhost.isLeavingCage()) {
			if(!readyToUp) {
				redGhost.ghostMovement(326, 409);	
				if((int)redGhost.getX()==326)
					readyToUp = true;

			}
			else {
				if((int)redGhost.getY()<=331) {
					readyToUp=false;
					redGhost.setLeavingCage(false);
					redGhost.setInCage(false);
				}
				else {
					redGhost.setyMove(-redGhost.getSpeed());
					redGhost.setxMove(0);
				}
					
			}
			
		}
		
		if(pinkGhost.isLeavingCage()) {
			if(!readyToUp) {
				pinkGhost.ghostMovement(326, 409);	
				//System.out.println(pinkGhost.getX()+" "+pinkGhost.getY());
				if((int)pinkGhost.getX()==326)
					readyToUp = true;

			}
			else {
				if((int)pinkGhost.getY()<=331) {
					readyToUp=false;
					pinkGhost.setLeavingCage(false);
					pinkGhost.setInCage(false);
				}
				else {
					pinkGhost.setyMove(-pinkGhost.getSpeed());
					pinkGhost.setxMove(0);
				}
					
			}
			
		}
		if(blueGhost.isLeavingCage()) {
			if(!readyToUp) {
				blueGhost.ghostMovement(326, 409);	
				if((int)blueGhost.getX()==326)
					readyToUp = true;

			}
			else {
				if((int)blueGhost.getY()<=331) {
					readyToUp=false;
					blueGhost.setLeavingCage(false);
					blueGhost.setInCage(false);
				}
				else {
					blueGhost.setyMove(-blueGhost.getSpeed());
					blueGhost.setxMove(0);
				}
					
			}
			
		}
		if(orangeGhost.isLeavingCage()) {
			if(!readyToUp) {
				orangeGhost.ghostMovement(326, 409);	
				if((int)orangeGhost.getX()==326)
					readyToUp = true;

			}
			else {
				if((int)orangeGhost.getY()<=331) {
					readyToUp=false;
					orangeGhost.setLeavingCage(false);
					orangeGhost.setInCage(false);
				}
				else {
					orangeGhost.setyMove(-orangeGhost.getSpeed());
					orangeGhost.setxMove(0);
				}
					
			}
			
		}

		
	}
	
	public void updateGhostMovement() {
		//System.out.println(getRoundTime());
		
		if((int)getRoundTime()-stopTime>=84&&phase==7) {
			
			phase=8;
			redGhost.setChaseMode(true);
			orangeGhost.setChaseMode(true);
			pinkGhost.setChaseMode(true);
			blueGhost.setChaseMode(true);
			
		}
		else if((int)getRoundTime()-stopTime>=79&&phase==6) {
			
			phase=7;
			redGhost.setChaseMode(false);
			orangeGhost.setChaseMode(false);
			pinkGhost.setChaseMode(false);
			blueGhost.setChaseMode(false);
			
		}
		else if((int)getRoundTime()-stopTime>=59&&phase==5) {
			
			phase=6;
			redGhost.setChaseMode(true);
			orangeGhost.setChaseMode(true);
			pinkGhost.setChaseMode(true);
			blueGhost.setChaseMode(true);
			
		}
		else if((int)getRoundTime()-stopTime>=54&&phase==4) {
		
			phase=5;
			redGhost.setChaseMode(false);
			orangeGhost.setChaseMode(false);
			pinkGhost.setChaseMode(false);
			blueGhost.setChaseMode(false);
		
		}
		else if((int)getRoundTime()-stopTime>=34&&phase==3) {
		
			phase=4;
			redGhost.setChaseMode(true);
			orangeGhost.setChaseMode(true);
			pinkGhost.setChaseMode(true);
			blueGhost.setChaseMode(true);
		
		}
		else if((int)getRoundTime()-stopTime>=27&&phase==2) {
		
			phase=3;
			redGhost.setChaseMode(false);
			orangeGhost.setChaseMode(false);
			pinkGhost.setChaseMode(false);
			blueGhost.setChaseMode(false);
		
		}
		else if((int)getRoundTime()-stopTime>=7&&phase==1) {
		
			phase=2;
			redGhost.setChaseMode(true);
			orangeGhost.setChaseMode(true);
			pinkGhost.setChaseMode(true);
			blueGhost.setChaseMode(true);
		
		}
			
		
	}
	
	boolean redSwitch = false;
	boolean redToggle=false;
	public void redGhost() {
		
		if(redGhost.isInCage()||redGhost.isLeavingCage()) 
			redGhost.setSpeed((float) 0.5);
		else if(redGhost.x<=93&&(redGhost.y>=408&&redGhost.y<=452)) {

			redGhost.setSpeed((float) 0.5);
		}
		else if(redGhost.x>=558&&(redGhost.y>=408&&redGhost.y<=452)) {
			redGhost.setSpeed((float) 0.5);
		}
		else {
			redGhost.setSpeed((float) 1);
			redGhost.setX((int)redGhost.getX());
		}
		
		
		if(!redGhost.isEaten()) {
			
				if(!redGhost.isInCage()) {
					if(!redGhost.isFrightened()) {
						redSwitch=false;
					
						if(redGhost.isChaseMode()==false) {
							redGhost.ghostMovement(redGhost.getScatterTargetx(), redGhost.getScatterTargety());	
						}
						else {
							redGhost.ghostMovement((int)player.bounds.getX(), (int)player.bounds.getY());
						}
						
					}
						else {
							if(!redSwitch) {
								redGhost.setxMove(-redGhost.getxMove());
								redGhost.setyMove(-redGhost.getyMove());
								redSwitch=true;
							}
								
							redGhost.randomMovement();
						}
						
						
					}
					else {
						if(!redGhost.isLeavingCage()) {
							redGhost.setxMove(0);
							if(!redToggle) {
								redGhost.setyMove(redGhost.getSpeed());
								redGhost.testCollisionWorld();
								if(redGhost.getyMove()==0)
									redToggle=true;
							}
							else {
								redGhost.setyMove(-redGhost.getSpeed());
								redGhost.testCollisionWorld();
								if(redGhost.getyMove()==0)
									redToggle=false;
								}
							}
					}
				
			
			
			
		}
		else {
			if((int)redGhost.getX()==326 && (int)redGhost.getY()==331) {
				redGhost.setReviving(true);
			}
			if(!redGhost.getReviving()) {
				redGhost.ghostMovement(326, 331);
				
			}
			else {
				redGhost.setxMove(0);
				redGhost.setyMove(redGhost.getSpeed());
				if(redGhost.getY()>=409) {
					redGhost.setxMove(redGhost.getSpeed());
					redGhost.setyMove(0);
					
				}
				if((int)redGhost.getX()==326&&(int)redGhost.getY()>=409) {
					redGhost.setReviving(false);
					redGhost.setEaten(false);
					redGhost.setInCage(true);
					redGhost.setFrightened(false);
					
				}
			}
		}
		
		
		
	}
	boolean orangeSwitch=false;
	boolean orangeToggle=false;
	public void orangeGhost() {
		
		if(orangeGhost.isInCage()||orangeGhost.isLeavingCage()) 
			orangeGhost.setSpeed((float) 0.5);
		else if(orangeGhost.x<=93&&(orangeGhost.y>=408&&orangeGhost.y<=452)) {

			orangeGhost.setSpeed((float) 0.5);
		}
		else if(orangeGhost.x>=558&&(orangeGhost.y>=408&&orangeGhost.y<=452)) {
			orangeGhost.setSpeed((float) 0.5);
		}
		else {
			orangeGhost.setSpeed((float) 1);
			orangeGhost.setX((int)orangeGhost.getX());
		}
		
		//System.out.println("is revivng: "+orangeGhost.getReviving());
		if(!orangeGhost.isEaten()) {
			
			
			
				if(!orangeGhost.isInCage()) {
					if(!orangeGhost.isFrightened()) {
						orangeSwitch=false;
					
						if(phase==2||phase==4||phase==6||phase==8)
							if(Math.sqrt((double)orangeGhost.closestPathValue(player.getX(),player.getY()))>(double)300) {
								orangeGhost.setChaseMode(true);
							}
				
						if(Math.sqrt((double)orangeGhost.closestPathValue(player.getX(),player.getY()))<=(double)300) {
							orangeGhost.setChaseMode(false);
						}
						if(orangeGhost.isChaseMode()==false) {
							orangeGhost.ghostMovement(orangeGhost.getScatterTargetx(), orangeGhost.getScatterTargety());
							
						}
						else {
							orangeGhost.ghostMovement((int)player.bounds.getX(), (int)player.bounds.getY());
							
							
						}
					
					}
					else {
						if(!orangeGhost.isInCage()) {
							if(!orangeSwitch) {
								orangeGhost.setxMove(-orangeGhost.getxMove());
								orangeGhost.setyMove(-orangeGhost.getyMove());
								orangeSwitch=true;
							}
								
							orangeGhost.randomMovement();
						}
					}
					
					
				}
				else {
					if(!orangeGhost.isLeavingCage()) {
						orangeGhost.setxMove(0);
						if(!orangeToggle) {
							orangeGhost.setyMove(orangeGhost.getSpeed());
							orangeGhost.testCollisionWorld();
							if(orangeGhost.getyMove()==0)
								orangeToggle=true;
						}
					else {
						orangeGhost.setyMove(-orangeGhost.getSpeed());
						orangeGhost.testCollisionWorld();
						if(orangeGhost.getyMove()==0)
							orangeToggle=false;
						}
					}
				}

			
			
			
		}
		else {
			
			if((int)orangeGhost.getX()==326 && (int)orangeGhost.getY()==331) {
				orangeGhost.setReviving(true);
			}
			if(!orangeGhost.getReviving()) {
				orangeGhost.ghostMovement(326, 331);
				
			}
			else {
				orangeGhost.setxMove(0);
				orangeGhost.setyMove(orangeGhost.getSpeed());
				if(orangeGhost.getY()>=409) {
					orangeGhost.setxMove(orangeGhost.getSpeed());
					orangeGhost.setyMove(0);
					
				}
				if((int)orangeGhost.getX()>=375&&(int)orangeGhost.getY()>=409) {
					orangeGhost.setReviving(false);
					orangeGhost.setEaten(false);
					orangeGhost.setInCage(true);
					orangeGhost.setFrightened(false);
					
				}
			}
			
		}
			

	}
	
	
	boolean blueSwitch=false;
	boolean blueToggle=false;
	public void blueGhost() {
	
		if(blueGhost.isInCage()||blueGhost.isLeavingCage()) 
			blueGhost.setSpeed((float) 0.5);
		else if(blueGhost.x<=93&&(blueGhost.y>=408&&blueGhost.y<=452)) {

			blueGhost.setSpeed((float) 0.5);
		}
		else if(blueGhost.x>=558&&(blueGhost.y>=408&&blueGhost.y<=452)) {

			blueGhost.setSpeed((float) 0.5);
		}
		else {
			blueGhost.setSpeed((float) 1);
			blueGhost.setX((int)blueGhost.getX());
		}
		
		if(!blueGhost.isEaten()) {
			
			
				if(!blueGhost.isInCage()) {
					if(!blueGhost.isFrightened()) {
						blueSwitch=false;
						if(blueGhost.isChaseMode()==false) {
							blueGhost.ghostMovement(blueGhost.getScatterTargetx(), blueGhost.getScatterTargety());
							
							
						}
						else {
							float[] distances = new float[2];
							if(player.getLastMove()==player.getAnimUp()) {
								blueGhost.setTarget(player.getX()-22,player.getY()-45);
							}
							if(player.getLastMove()==player.getAnimLeft()) {
								blueGhost.setTarget(player.getX()-45,player.getY());
							}
							if(player.getLastMove()==player.getAnimDown()) {
								blueGhost.setTarget(player.getX(),player.getY()+45);
							}			
							if(player.getLastMove()==player.getAnimRight()) {
								blueGhost.setTarget(player.getX()+45,player.getY());
							}
							distances = blueGhost.closestPathValueXYDist((int)redGhost.getX(), (int)redGhost.getY());
							blueGhost.setRealTarget(blueGhost.getTargetx()+distances[0], blueGhost.getTargety()+distances[1]);
							blueGhost.ghostMovement((int)blueGhost.getRealTargetx(), (int)blueGhost.getRealTargety());
							
						}
					}
					else {
						if(!blueSwitch) {
							blueGhost.setxMove(-blueGhost.getxMove());
							blueGhost.setyMove(-blueGhost.getyMove());
							blueSwitch=true;
						}
							
						blueGhost.randomMovement();

					}
				}
				else {
				if(!blueGhost.isLeavingCage()) {
					blueGhost.setxMove(0);
					
					if(!blueToggle) {
						blueGhost.setyMove(blueGhost.getSpeed());
						blueGhost.testCollisionWorld();
						if(blueGhost.getyMove()==0)
							blueToggle=true;
					}
					else {
						
						blueGhost.setyMove(-blueGhost.getSpeed());
						blueGhost.testCollisionWorld();
						if(blueGhost.getyMove()==0)
							blueToggle=false;
						}
					}
				}
			}
			
			
	
		else {
			if((int)blueGhost.getX()==326 && (int)blueGhost.getY()==331) {
				blueGhost.setReviving(true);
			}
			if(!blueGhost.getReviving()) {
				blueGhost.ghostMovement(326, 331);
				
			}
			else {
				blueGhost.setxMove(0);
				blueGhost.setyMove(blueGhost.getSpeed());
				if(blueGhost.getY()>=409) {
					blueGhost.setxMove(-blueGhost.getSpeed());
					blueGhost.setyMove(0);
					
				}
				if((int)blueGhost.getX()<=275&&(int)blueGhost.getY()>=409) {
					blueGhost.setReviving(false);
					blueGhost.setEaten(false);
					blueGhost.setInCage(true);
					blueGhost.setFrightened(false);
					
				}
			}
		}
		
	}
	boolean pinkSwitch=false;
	boolean pinkToggle=true;
	public void pinkGhost() {
		if(pinkGhost.isInCage()||pinkGhost.isLeavingCage()) 
			pinkGhost.setSpeed((float) 0.5);
		else if(pinkGhost.x<=93&&(pinkGhost.y>=408&&pinkGhost.y<=452)) {
			pinkGhost.setSpeed((float) 0.5);
		}
		else if(pinkGhost.x>=558&&(pinkGhost.y>=408&&pinkGhost.y<=452)) {
			pinkGhost.setSpeed((float) 0.5);
		}
		else {
			pinkGhost.setSpeed((float) 1);
			pinkGhost.setX((int)pinkGhost.getX());
		}
		if(!pinkGhost.isEaten()) {
			
			
				if(!pinkGhost.isInCage()) {
					if(!pinkGhost.isFrightened()) {
						pinkSwitch=false;
						if(pinkGhost.isChaseMode()==false) {
							
							pinkGhost.ghostMovement(pinkGhost.getScatterTargetx(), pinkGhost.getScatterTargety());
							
							
						}
						else {
							
							if(player.getLastMove()==player.getAnimUp()) {
			
								pinkGhost.ghostMovement((int)player.getX()-135,(int)player.getY()-180);
							}
							if(player.getLastMove()==player.getAnimLeft()) {
								if(pinkGhost.x<=93&&(pinkGhost.y>=408&&pinkGhost.y<=452)) {
									pinkGhost.ghostMovement((int)player.getX()-135,(int)player.getY()-180);
								}
								else if(pinkGhost.x>=603&&(pinkGhost.y>=408&&pinkGhost.y<=452)) {
									pinkGhost.ghostMovement((int)player.getX()-135,(int)player.getY()-180);
								}
								else
									pinkGhost.ghostMovement((int)player.getX()-180,(int)player.getY());
							}
							if(player.getLastMove()==player.getAnimDown()) {
								
								pinkGhost.ghostMovement((int)player.getX(),(int)player.getY()+180);
								
							}			
							if(player.getLastMove()==player.getAnimRight()) {
								
								if(pinkGhost.x<=93&&(pinkGhost.y>=408&&pinkGhost.y<=452)) {
									
									pinkGhost.ghostMovement((int)player.getX()-135,(int)player.getY()-180);
								}
								else if(pinkGhost.x>=603&&(pinkGhost.y>=408&&pinkGhost.y<=452)) {
									pinkGhost.ghostMovement((int)player.getX()-135,(int)player.getY()-180);
								}
								else
									pinkGhost.ghostMovement((int)player.getX()+180,(int)player.getY());
							}
						}
						
						}
					else {
						if(!pinkSwitch) {
							pinkGhost.setxMove(-pinkGhost.getxMove());
							pinkGhost.setyMove(-pinkGhost.getyMove());
							pinkSwitch=true;
						}
							
						pinkGhost.randomMovement();
					}
				} 
				else {
					if(!pinkGhost.isLeavingCage()) {
						pinkGhost.setxMove(0);
						
						if(!pinkToggle) {
							pinkGhost.setyMove(pinkGhost.getSpeed());
							pinkGhost.testCollisionWorld();
							if(pinkGhost.getyMove()==0)
								pinkToggle=true;
						}
						else {
							pinkGhost.setyMove(-pinkGhost.getSpeed());
							pinkGhost.testCollisionWorld();
							if(pinkGhost.getyMove()==0)
								pinkToggle=false;
							}
		
					}
				}

		}
		else {
			if((int)pinkGhost.getX()==326 && (int)pinkGhost.getY()==331) {
				pinkGhost.setReviving(true);
			}
			if(!pinkGhost.getReviving()) {
				pinkGhost.ghostMovement(326, 331);
				
			}
			else {
				pinkGhost.setxMove(0);
				pinkGhost.setyMove(pinkGhost.getSpeed());
				if(pinkGhost.getY()==409) {
					pinkGhost.setxMove(-pinkGhost.getSpeed());
					pinkGhost.setyMove(0);
					
				}
				if((int)pinkGhost.getX()==326&&(int)pinkGhost.getY()>=409) {
					pinkGhost.setReviving(false);
					pinkGhost.setEaten(false);
					pinkGhost.setInCage(true);
					pinkGhost.setFrightened(false);
					
				}
			}
			
		}
		
	}
	
	
	
	public boolean testCollisionPoints() {
		
		for(int k =0; k<dots.dots.length;k++)
			if(player.bounds.intersects(dots.dots[k])) {
				dots.removeDots(dots.dots[k]);
				points +=10;
				dotsEaten++;
				timeSinceLastDot = System.currentTimeMillis();
				return true;
			}
		for(int k =0; k<dots.bigdots.length;k++)
			if(player.bounds.intersects(dots.bigdots[k])) {
				dots.removeDots(dots.bigdots[k]);
				points +=50;
				dotsEaten++;
				setFrightened();
				fear = System.currentTimeMillis();
				timeSinceLastDot = System.currentTimeMillis();
				return true;
			}
		for(int k = 0; k<fruits.fruit.length;k++) {
			if(player.bounds.intersects(fruits.fruit[k])) {
				if(fruits.getFruit()==Assets.fruits[0])
					points+=100;
				else if(fruits.getFruit()==Assets.fruits[1])
					points+=300;
				else if(fruits.getFruit()==Assets.fruits[2])
					points+=500;
				else if(fruits.getFruit()==Assets.fruits[3])
					points+=700;
				else if(fruits.getFruit()==Assets.fruits[4])
					points+=1000;
				else if(fruits.getFruit()==Assets.fruits[5])
					points+=2000;
				else if(fruits.getFruit()==Assets.fruits[6])
					points+=3000;
				else if(fruits.getFruit()==Assets.fruits[7])
					points+=5000;
				fruits.removeFruit();
			}
		}	
		return false;
		
	}
	

	public int getPoints() {
		return points;
	}
	
	public int getDots() {
		return dotsEaten;
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	

	
	public void reset() {
		redGhost.setX(326);
		redGhost.setY(331);
		blueGhost.setX(275); 
		blueGhost.setY(409);
		pinkGhost.setX(326);
		pinkGhost.setY(409);
		orangeGhost.setX(375);
		orangeGhost.setY(409);
		
		orangeGhost.setInCage(true);
		pinkGhost.setInCage(true);
		blueGhost.setInCage(true);
		
		redGhost.setEaten(false);
		orangeGhost.setEaten(false);
		blueGhost.setEaten(false);
		pinkGhost.setEaten(false);
		
		redGhost.setFrightened(false);
		orangeGhost.setFrightened(false);
		blueGhost.setFrightened(false);
		pinkGhost.setFrightened(false);
		
		redGhost.setReviving(false);
		orangeGhost.setReviving(false);
		blueGhost.setReviving(false);
		pinkGhost.setReviving(false);
		
	}
	

	
	//getters setters
	
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}


	public int getLevelCount() {
		return LevelCount;
	}


	public void setLevelCount(int levelCount) {
		fruits.setLevel(levelCount);
		LevelCount = levelCount;
	}

	public long getRoundTime() {
		long test = System.currentTimeMillis();
		return(test-roundTimer)/1000;
	}
	
	
}
