package pacman.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import pacman.Game;
import pacman.Handler;
import pacman.gfx.Assets;
import pacman.states.GameState;

public class Fruits extends Entity{
	
	public Rectangle[] fruit = new Rectangle[0];
	private boolean active = false;
	private long pastTime;
	GameState gs;
	private boolean pass70 = false, pass170 = false;
	private int level=1;
	
	

	public Fruits(Handler handler, int x, int y, int width, int height) {
		super(handler, x, y, width, height);

	}

	public static void init() {
		
	}

	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {
		
		for(int k = 0;k<fruit.length;k++) {
			g.drawImage(getFruit(), (int)x, (int)y , width, height, null);
		}
		
		
	}
	
	public BufferedImage getFruit() {
		if(level==1)
			return Assets.fruits[0];
		if(level==2)
			return Assets.fruits[1];
		if(level==3 || level==4)
			return Assets.fruits[2];
		if(level==5 || level==6)
			return Assets.fruits[3];
		if(level==7 || level==8)
			return Assets.fruits[4];
		if(level==9 || level==10)
			return Assets.fruits[5];
		if(level==11 || level==12)
			return Assets.fruits[6];
		if(level>=13)
			return Assets.fruits[7];
		return null;
	}
	boolean timerOn = false;
	public void makeFruit(int dots) {
		
		if((dots==70 && !pass70)||(dots==170 && !pass170)) {
			if(!pass70)
				pass70=true;
			else
				pass170=true;
			active = true;
		}
		if(active && !timerOn) {
			
			fruit = new Rectangle[1];
			fruit[0] = new Rectangle((int)x, (int)y, width, height);
			timerOn=true;
			pastTime = System.currentTimeMillis();
			
			
		}
		

			
		if(timerOn) {
			long test = System.currentTimeMillis();
			if(test >= (pastTime + 9.5*1000)) { //multiply by 1000 to get milliseconds
			  active = false;
			  timerOn = false;
			  removeFruit();
			}
		
		}
		
		
	}
	
	public void removeFruit() {
		Rectangle[] fruit2 = new Rectangle[0];
		fruit=fruit2;
		active=false;
		timerOn = false;
	}
	
	public void reset() {
		pass70=false;
		pass170=false;
	}

	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
