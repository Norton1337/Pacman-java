package pacman.entities.creatures;

import java.awt.Rectangle;

import pacman.Handler;
import pacman.entities.Entity;
import pacman.worlds.World;

public abstract class Creature extends Entity {
	
	protected static final int DEFAULT_HEALTH = 10;
	protected static final float DEFAULT_SPEED = 1.0f;
	protected static final int DEFAULT_CREATURE_WIDTH = 45,
							DEFAULT_CREATURE_HEIGHT = 45;
	
	private Handler handler;
	
	
	private World world;
	private Rectangle nextMove;
	private Rectangle nextPossibleMove;
	private Rectangle[] collidWalls = new Rectangle[2];
	

	private int health;
	protected float speed;
	protected float xMove, yMove;
	
	public Creature(Handler handler,float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		this.handler = handler;
		health = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		
		
		nextMove = new Rectangle();
		nextPossibleMove = new Rectangle();
		world = handler.getWorld();
	
		//world.makeWalls();

		nextMove.width = this.width;
		nextMove.height = this.height;
		nextPossibleMove.width = this.width;
		nextPossibleMove.height = this.height;
	}

	public void move(float xMove, float yMove) {
		//System.out.println(yMove);
		x+=xMove;
		y+=yMove;
		
		if(x>=686) {
			x=0 - width;
		}
		if(x+width<0) {
			x=685;
		}
		
		this.bounds.x=(int)x;
		this.bounds.y=(int)y;
		
		
	}
	
	private int lastXSpeed;
	private int lastYSpeed;
	
	public boolean testCollisionWorld() {
		nextMove.x = (int) (this.bounds.x + this.xMove);
		nextMove.y = (int) (this.bounds.y + this.yMove);
		lastXSpeed=(int) this.xMove;
		lastYSpeed=(int) this.yMove;
		
		int l=0;
		for(int k=0;k<world.rectArr.length;k++)
			if(nextMove.intersects(world.rectArr[k]))
				l++;
		
		if(l==2){
			l=0;
			for(int k=0;k<world.rectArr.length;k++)
				if(nextMove.intersects(world.rectArr[k])) {
					collidWalls[l]=world.rectArr[k];
					l++;
				}
		}
		
		if(l==1){
			//System.out.println("gay1");
			for(int k=0;k<world.rectArr.length;k++) { 
				 if(nextMove.intersects(world.rectArr[k])){
					 
						 nextMove.x=(int) (this.bounds.x + this.xMove);
						 nextMove.y=(int) (this.bounds.y);
						 
						 if(!nextMove.intersects(world.rectArr[k])) {
							 
							 nextMove.y=(int) (this.bounds.y+ this.yMove);
							 
							 if(nextMove.intersects(world.rectArr[k]))
								 this.yMove = 0;
							 
							 this.xMove = lastXSpeed;
							 
							 nextMove.x=(int) (this.bounds.x + this.xMove);
							 nextMove.y=(int) (this.bounds.y);
							 return true;
							 
						 }
						 else if(nextMove.intersects(world.rectArr[k])){
							 this.xMove = 0;
							 nextMove.x=(int) (this.bounds.x);
							 nextMove.y=(int) (this.bounds.y+ this.yMove);
							 
						 }
						 
						 
						 
						 if(!nextMove.intersects(world.rectArr[k])) {
							 
							 nextMove.x=(int) (this.bounds.x + this.xMove);
							 if(nextMove.intersects(world.rectArr[k]))
								 this.xMove = 0;
							 this.yMove = lastYSpeed;
							 return true;
						 }
						 else if(nextMove.intersects(world.rectArr[k])){
							 this.yMove = 0;
						 } 
				 }
			 }
		}
		
		nextMove.x = (int) (this.bounds.x + this.xMove);
		nextMove.y = (int) (this.bounds.y + this.yMove);
		
		if(l==2) {
			if(nextMove.intersects(collidWalls[0]) && nextMove.intersects(collidWalls[1])) {
				nextMove.x = (int) (this.bounds.x);
				nextMove.y = (int) (this.bounds.y + this.yMove);
				if(nextMove.intersects(collidWalls[0]) || nextMove.intersects(collidWalls[1])) {
					this.yMove=0;
					nextMove.x = (int) (this.bounds.x + this.xMove);
					nextMove.y = (int) (this.bounds.y);
					if(nextMove.intersects(collidWalls[0]) || nextMove.intersects(collidWalls[1])) {
						this.xMove=0;
						return true;
					}
					
					return true;
					
				}
				else {
					this.yMove = lastYSpeed;
				}
				nextMove.x = (int) (this.bounds.x + this.xMove);
				nextMove.y = (int) (this.bounds.y);
				if(nextMove.intersects(collidWalls[0]) || nextMove.intersects(collidWalls[1])) {
					this.xMove=0;
					return true;
				}
				else {
					this.xMove = lastXSpeed;
				}
			}
			
			
			
			this.yMove = 0;
			this.xMove = 0;
		 }
		return false;
	}
	
	
	
	//GETTER SETTERS
	
	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	
	
	

}
