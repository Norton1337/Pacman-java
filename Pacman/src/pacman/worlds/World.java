package pacman.worlds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import pacman.Handler;
import pacman.entities.Dots;
import pacman.entities.EntityManager;
import pacman.entities.Fruits;
import pacman.entities.creatures.Player;
import pacman.entities.creatures.ghosts.BlueGhost;
import pacman.entities.creatures.ghosts.OrangeGhost;
import pacman.entities.creatures.ghosts.PinkGhost;
import pacman.entities.creatures.ghosts.RedGhost;
import pacman.gfx.ImageLoader;

import java.awt.Rectangle;

public class World {

	private Handler handler;
	private Player player;
	private Dots dots;
	private World world;
	private Fruits fruits;


	private RedGhost redGhost;
	private OrangeGhost orangeGhost;
	private PinkGhost pinkGhost;
	private BlueGhost blueGhost;
	private boolean changingLevel = false;
	//Entities
	private EntityManager entityManager;
	
	

	private BufferedImage mapImage = ImageLoader.loadImage("/textures/map.png");
	private BufferedImage mapImage2 = ImageLoader.loadImage("/textures/mapwhite.png");
	public Rectangle[] rectArr = new Rectangle[48];
	
	
	public World(Handler handler) {
		player = new Player(handler, 326,633);
		fruits = new Fruits(handler, 321, 477, 45, 45);
		dots = new Dots(handler, 0, 0, 0, 0);
		redGhost = new RedGhost(handler,326,331, 45, 45); 
		blueGhost = new BlueGhost(handler, 275,409, 45, 45); 
		pinkGhost = new PinkGhost(handler,326,409, 45, 45);
		orangeGhost = new OrangeGhost(handler,375,409, 45, 45);
		entityManager = new EntityManager(handler, player,fruits,dots,redGhost,blueGhost,pinkGhost,orangeGhost);
	}
	
	
	public void makeWalls() {
		int l = 1;
		rectArr[0] = new Rectangle(0, 70, 12+l, 233+l);
		rectArr[1] = new Rectangle(0, 70, 695+l, 12+l);
		rectArr[2] = new Rectangle(333, 70, 29+l, 112+l);
		rectArr[3] = new Rectangle(683, 70, 12+l, 233+l);
		rectArr[4] = new Rectangle(-5, 303, 142+l, 104+l);
		rectArr[5] = new Rectangle(558, 303, 142+l, 104+l);
		rectArr[6] = new Rectangle(-5, 453, 142+l, 104+l);
		rectArr[7] = new Rectangle(558, 453, 142+l, 104+l);
		
		rectArr[8] = new Rectangle(58, 128, 79+l, 54+l);
		rectArr[9] = new Rectangle(183, 128, 104+l, 54+l);
		rectArr[10] = new Rectangle(408, 128, 104+l, 54+l);
		rectArr[11] = new Rectangle(558, 128, 79+l, 54+l);
		
		rectArr[12] = new Rectangle(58, 228, 79+l, 29+l);
		rectArr[13] = new Rectangle(558, 228, 79+l, 29+l);
		
		rectArr[14] = new Rectangle(183, 228, 29+l, 179+l);
		rectArr[15] = new Rectangle(483, 228, 29+l, 179+l);
		rectArr[16] = new Rectangle(258, 228, 179+l, 29+l);
		rectArr[17] = new Rectangle(333, 257, 29+l, 73+l);
		
		rectArr[18] = new Rectangle(212, 303, 75+l, 27+l);
		rectArr[19] = new Rectangle(408, 303, 75+l, 27+l);
		
		rectArr[20] = new Rectangle(183, 453, 29+l, 104+l);
		rectArr[21] = new Rectangle(483, 453, 29+l, 104+l);

		rectArr[22] = new Rectangle(258, 526, 179+l, 31+l);
		rectArr[23] = new Rectangle(333, 557, 29+l, 75+l);
		
		rectArr[24] = new Rectangle(0, 557, 12+l, 283+l);
		rectArr[25] = new Rectangle(683, 557, 12+l, 283+l);
		rectArr[26] = new Rectangle(0, 828, 695+l, 12+l);
		
		rectArr[27] = new Rectangle(58, 603, 79+l, 29+l);
		rectArr[28] = new Rectangle(183, 603, 104+l, 29+l);
		rectArr[29] = new Rectangle(408, 603, 104+l, 29+l);
		rectArr[30] = new Rectangle(558, 603, 79+l, 29+l);
		
		rectArr[31] = new Rectangle(12, 678, 50+l, 29+l);
		rectArr[32] = new Rectangle(633, 678, 50+l, 29+l);
		
		rectArr[33] = new Rectangle(108,632,29+l,75+l);
		rectArr[34] = new Rectangle(558,632,29+l,75+l);
		
		rectArr[35] = new Rectangle(258, 678, 179+l, 29+l);
		rectArr[36] = new Rectangle(333, 707, 29+l, 75+l);
		
		rectArr[37] = new Rectangle(58, 753, 229+l, 29+l);
		rectArr[38] = new Rectangle(408, 753, 229+l, 29+l);
		
		rectArr[39] = new Rectangle(183, 678, 29+l, 75+l);
		rectArr[40] = new Rectangle(483, 678, 29+l, 75+l);
		
		rectArr[41] = new Rectangle(258, 376, 14+l, 104+l);
		rectArr[42] = new Rectangle(423, 376, 14+l, 104+l);
		rectArr[43] = new Rectangle(272, 466, 151+l, 14+l);
		rectArr[44] = new Rectangle(272, 376, 48+l, 16+l);
		rectArr[45] = new Rectangle(375, 376, 48+l, 16+l);
		
		rectArr[46] = new Rectangle(321,382,53+l,4+l);
		rectArr[47] = new Rectangle(321,376,53+l,16+l);
		
		
		
		
		
	}
	
	public void tick() {
		if(!changingLevel)
			entityManager.tick();
	}
	
	public void render(Graphics g) {
		
		if(!changingLevel) {
			
		g.drawImage(mapImage,0,0,null);
		entityManager.render(g);
		}
		else {
			nextLevel(g);
			player.render(g);
		}
		/*
		g.setColor(Color.white);
		int l = 1;
		for(int k=0;k<rectArr.length;k++) {
			
			g.drawRect(rectArr[k].x+l, rectArr[k].y+l, rectArr[k].width, rectArr[k].height);
		
		}
		*/
		
		g.fillRect(rectArr[46].x, rectArr[46].y, rectArr[46].width, rectArr[46].height);
	
	
	}
		
		
	public World getWorld() {
		return this;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	boolean timerOn = false;
	private long pastTime;
	
	
	public void nextLevel(Graphics g) {
		
		if(!timerOn) {
					
			timerOn=true;
			pastTime = System.currentTimeMillis();

		}
		

		if(timerOn) {
			long test = System.currentTimeMillis();
			if(test >= (pastTime)){
				g.drawImage(mapImage,0,0,null);
			}
			if(test >= (pastTime + 1000)){
				g.drawImage(mapImage2,0,0,null);
			}
			if(test >= (pastTime + 1250)){
				g.drawImage(mapImage,0,0,null);
			}
			if(test >= (pastTime + 1500)){
				g.drawImage(mapImage2,0,0,null);
			}
			if(test >= (pastTime + 1750)){
				g.drawImage(mapImage,0,0,null);
			}
			if(test >= (pastTime + 2000)){
				g.drawImage(mapImage2,0,0,null);
			}
			if(test >= (pastTime + 2250)){
				g.drawImage(mapImage,0,0,null);
			}
			if(test >= (pastTime + 2500)){
				g.drawImage(mapImage2,0,0,null);
			}
			if(test >= (pastTime + 2750)){
				g.drawImage(mapImage,0,0,null);
			}
			
			
			
			
			
			if(test >= (pastTime + 3*1000)) { //multiply by 1000 to get milliseconds
			  timerOn = false;
			  nextLevel();
			}
		}
		
		
		
		
		
		
	}
	
	public void changeLevelState() {
		changingLevel = true;
	}
	
	public void nextLevel() {
		entityManager.reset();
		player.restart();
		dots.makeDots();
		changingLevel=false;
	}

	
	
}
