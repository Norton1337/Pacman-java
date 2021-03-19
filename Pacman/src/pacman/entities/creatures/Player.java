package pacman.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import pacman.Handler;
import pacman.gfx.Animation;
import pacman.gfx.Assets;

public class Player extends Creature{

	//Animation
	private Animation animRight;
	private Animation animLeft;
	private Animation animUp;
	private Animation animDown;
	
	public Animation getAnimRight() {
		return animRight;
	}

	public Animation getAnimLeft() {
		return animLeft;
	}

	public Animation getAnimUp() {
		return animUp;
	}

	public Animation getAnimDown() {
		return animDown;
	}
	
	private Animation death;
	
	private boolean dead= false;
	
	private Handler handler;
	
	private int lives=3;
	private boolean restart = false;
	
	private int startingX=326, startingY=633;
	

	
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		this.handler= handler;
		this.bounds.x = (int)x;
		this.bounds.y = (int)y;
		this.bounds.width = 45;
		this.bounds.height = 45;
		startingX=(int)x;
		startingY=(int)y;



		
		//Animations
		animRight = new Animation(100,Assets.pacmanRight);
		animLeft = new Animation(100,Assets.pacmanLeft);
		animUp = new Animation(100,Assets.pacmanUp);
		animDown = new Animation(100,Assets.pacmanDown);
		death = new Animation(100,Assets.pacmanDEATH);
		

	}

	@Override
	public void tick() {
		if(!dead) {
		
		//Animations
		animRight.tick();
		animLeft.tick();
		animUp.tick();
		animDown.tick();
		//Movement
		getInput();
		testCollisionWorld();
		move(this.getxMove(),this.getyMove());
		}
		else {
			death.tick();
			move(0,0);
		}

		
		
	}
	
	

	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y,null);
		//g.drawRect((int) x, (int) y, 45,45);
		g.setColor(Color.orange);
		//g.drawOval((int)x-160,(int)y-160, 360, 360);
		
		
	}
	
	
	private void getInput() {

		
		if(handler.getKeyManager().up)
			this.setyMove(-this.getSpeed());
		if(handler.getKeyManager().down)
			this.setyMove(this.getSpeed());
		if(handler.getKeyManager().left)
			this.setxMove(-this.getSpeed());
		if(handler.getKeyManager().right)
			this.setxMove(this.getSpeed());
	}
	
	
	public void restart() {
		this.x = startingX;
		this.y = startingY;
		this.setxMove(0);
		this.setyMove(0);

	}
	
	public void death() {
		dead=true;
	}
	
	
	public boolean isDead() {
		return dead;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	private int deathFrame=0;
	private Animation lastMove;
	
	public Animation getLastMove() {
		return lastMove;
	}

	private BufferedImage getCurrentAnimationFrame() {
		if(!dead) {
			if(this.getxMove() < 0) {
				lastMove = animLeft;
				return animLeft.getCurrentFrame();
			}
			else if(this.getxMove() > 0) {
				lastMove = animRight;
				return animRight.getCurrentFrame();
			}
			else if(this.getyMove() < 0) {
				lastMove = animUp;
				return animUp.getCurrentFrame();
			}
			else if(this.getyMove() > 0) {
				lastMove = animDown;
				return animDown.getCurrentFrame();
			}
			if(lastMove == animLeft) {
				lastMove = animLeft;
				return animLeft.getCurrentFrame();
			}
			else if(lastMove == animRight) {
				lastMove = animRight;
				return animRight.getCurrentFrame();
			} 
			else if(lastMove == animUp) {
				lastMove = animUp;
				return animUp.getCurrentFrame();
			}
			else {
				lastMove = animDown;
				return animDown.getCurrentFrame();
			}
		}
		else {
			if (deathFrame<175) {
				deathFrame++;
				return death.getCurrentFrame();
			}
			deathFrame++;
			if(deathFrame>200) {
				deathFrame=0;
				lives--;
				dead=false;
				restart();
			}
				
			
			
			
		}
		return null;
	}


	

	
	

}
