package pacman.entities.creatures.ghosts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import pacman.Handler;
import pacman.entities.creatures.Creature;
import pacman.gfx.Animation;
import pacman.gfx.Assets;

public class RedGhost extends Creature{

	//Animation
		private Animation animRight;
		private Animation animLeft;
		private Animation animUp;
		private Animation animDown;
		private Animation frightenedAnim;
		private Animation frightenedExpiredAnim;
		
		private BufferedImage eatenUp;
		private BufferedImage eatenLeft;
		private BufferedImage eatenDown;
		private BufferedImage eatenRight;
		
		private boolean reviving = false;
		
		private boolean frightened = false;
		private boolean frightenedExpiring = false;
		


		private boolean eaten = false;
		
		
		private boolean chaseMode = false;
		
		private int scatterTargetx = 612;
		private int scatterTargety = 20;
		
		private float lastMoveX = 0;
		private float lastMoveY = 0;
	
		private float targetx = scatterTargetx;
		private float targety = scatterTargety;
		
		private boolean inCage = false;
		private boolean leavingCage = false;
		
		
	
	
		private int redDotsCounter = 0;
		private int dotsLimit=0;
		
		
		public boolean isFrightenedExpiring() {
			return frightenedExpiring;
		}

		public void setFrightenedExpiring(boolean frightenedExpiring) {
			this.frightenedExpiring = frightenedExpiring;
		}
		
		public int getRedDotsCounter() {
			return redDotsCounter;
		}

		public void setRedDotsCounter(int redDotsCounter) {
			this.redDotsCounter = redDotsCounter;
		}

		public int getDotsLimit() {
			return dotsLimit;
		}

		public void setDotsLimit(int dotsLimit) {
			this.dotsLimit = dotsLimit;
		}

		public boolean isInCage() {
			return inCage;
		}

		public void setInCage(boolean inCage) {
			this.inCage = inCage;
		}

	public RedGhost(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		this.bounds.x = (int)x;
		this.bounds.y = (int)y;
		this.bounds.width = 45;
		this.bounds.height = 45;
		Random rand = new Random();
		
		
		
		//Animations
		animRight = new Animation(100,Assets.redGhostR);
		animLeft = new Animation(100,Assets.redGhostL);
		animUp = new Animation(100,Assets.redGhostU);
		animDown = new Animation(100,Assets.redGhostD);
		frightenedAnim = new Animation(100,Assets.scaredGhost);
		frightenedExpiredAnim = new Animation(100,Assets.scaredGhostExpire);
		eatenUp = Assets.deadGhostU;
		eatenLeft = Assets.deadGhostL;
		eatenDown = Assets.deadGhostD;
		eatenRight = Assets.deadGhostR;
		
		
	}
	
	
	int test=0;
	int test2=0;

	@Override
	public void tick() {
		animRight.tick();
		animLeft.tick();
		animUp.tick();
		animDown.tick();
		frightenedAnim.tick();
		frightenedExpiredAnim.tick();
		
		
		
 
		//testCollisionWorld();
		move(xMove,yMove);
		lastMoveX=xMove;
		lastMoveY=yMove;
		
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y,null);
		g.setColor(Color.red);
		//g.drawRect((int) x, (int) y, 45,45);
		/*
		g.setColor(Color.red);
		g.fillRect((int)getTargetx(),(int) getTargety(), 4, 4);
		*/
		
	}
	
	
	
	public void death() {
		this.frightened=false;
		this.eaten = true;
		
	}
	
	public void randomMovement() {
		int[] intArray = new int[4];
		int availablePaths=0;
		this.testMovement();
		if(goUp==true) {
			intArray[availablePaths]=1;
			availablePaths++;
		}
		if(goLeft==true) {
			intArray[availablePaths]=2;
			availablePaths++;
		}
		if(goDown==true) {
			intArray[availablePaths]=3;
			availablePaths++;
		}
		if(goRight==true) {
			intArray[availablePaths]=4;
			availablePaths++;
		}
		if(availablePaths==0)
			availablePaths=1;
		int randomPath =  intArray[(int)(System.currentTimeMillis() % availablePaths)];
		
		if(randomPath==1) {
			this.setyMove(-speed);
			this.setxMove(0);
		}
		else if(randomPath==2) {
			this.setyMove(0);
			this.setxMove(-speed);
		}
		else if(randomPath==3) {
			this.setyMove(speed);
			this.setxMove(0);
		}
		else if(randomPath==4) {
			this.setyMove(0);
			this.setxMove(speed);
		}
	}
	
	public void ghostMovement(int xCoord, int yCoord) {
		int path=0;
		this.setTarget(xCoord, yCoord);
		this.testMovement();
		path=this.closestPath();
		
		if(path==1) {
			this.setyMove(-speed);
			this.setxMove(0);
		}
		else if(path==2) {
			this.setyMove(0);
			this.setxMove(-speed);
		}
		else if(path==3) {
			this.setyMove(speed);
			this.setxMove(0);
		}
		else if(path==4) {
			this.setyMove(0);
			this.setxMove(speed);
		}
	}
	
	public void setFrightened(boolean frightened) {
		this.frightened = frightened;
	}

	public float getTargetx() {
		return targetx;
	}

	public float getTargety() {
		return targety;
	}

	public void setTarget(float x, float y) {
		targetx=x;
		targety=y;
	}
	
	public float getLastMoveX() {
		return lastMoveX;
	}

	public float getLastMoveY() {
		return lastMoveY;
	}

	public int getScatterTargetx() {
		return scatterTargetx;
	}

	public int getScatterTargety() {
		return scatterTargety;
	}
	
		
	public boolean isChaseMode() {
		return chaseMode;
	}

	public void setChaseMode(boolean chaseMode) {
		this.chaseMode = chaseMode;
	}
	
	private boolean goUp = false;
	private boolean goLeft = false;
	private boolean goDown = false;
	private boolean goRight = false;
	
	public void testMovement(){
		goUp = false;
		goLeft = false;
		goDown = false;
		goRight = false;

		if(xMove!=0)
		lastMoveX=xMove;
		if(yMove!=0)
		lastMoveY=yMove;
		xMove=0;
		yMove=0;
		
		if(lastMoveY!=1 && lastMoveY!=0.5) {
			
			yMove=-1;
			xMove=0;
			testCollisionWorld();
			if(yMove==-1)
				goUp = true;
			}
		if(lastMoveX!=1 && lastMoveX !=0.5) {
			yMove=0;
			xMove=-1;
			testCollisionWorld();
			if(xMove==-1)
				goLeft = true;
			}
		if(lastMoveY!=-1 && lastMoveY!=-0.5) {
			yMove=1;
			xMove=0;
			testCollisionWorld();
			if(yMove==1)
				goDown = true;
			}
		if(lastMoveX!=-1 && lastMoveX !=-0.5) {
			yMove=0;
			xMove=1;
			testCollisionWorld();
			if(xMove==1)
				goRight = true;
			}
		if((this.x>=286&&this.x<=410)&&(this.y>=331&&this.y<=374))
			goUp = false;
		if((this.x>=286&&this.x<=410)&&(this.y>=633&&this.y<=677))
			goUp = false;
		xMove=0;
		yMove=0;
		
		
		
		
	}
	
	
	public int closestPath() {
		double ifUp = 9999999;
		double ifLeft = 9999998;
		double ifDown = 9999997;
		double ifRight = 9999996;
		int number = 0;
		int number2 = 0;
		
		if(goUp) {
			number  = (int) (Math.abs(targetx)-Math.abs(this.bounds.x));
			number2 = (int) (Math.abs(targety)-Math.abs(this.bounds.y-speed)); 
			ifUp = ((Math.pow(number,2)) + (Math.pow(number2,2)));
			//System.out.println("up: "+ ifUp);
		} 
		if(goLeft) {
			number  = (int) (Math.abs(targetx)-Math.abs(this.bounds.x-speed));
			number2 = (int) (Math.abs(targety)-Math.abs(this.bounds.y));
			ifLeft =  ((Math.pow(number,2)) + (Math.pow(number2 ,2 )));
			//System.out.println("left: "+ ifLeft);
		}
		if(goDown) {
			number  = (int) (Math.abs(targetx)-Math.abs(this.bounds.x));
			number2 = (int) (Math.abs(targety)-Math.abs(this.bounds.y+speed));
			ifDown = ((Math.pow(number,2)) + (Math.pow(number2,2)));
			//System.out.println("down: "+ifDown);
		}
		if(goRight) {
			number  = (int) (Math.abs(targetx)-Math.abs(this.bounds.x+speed));
			number2 = (int) (Math.abs(targety)-Math.abs(this.bounds.y));
			ifRight = ((Math.pow(number,2)) + (Math.pow(number2,2)));
			//System.out.println("right: "+ifRight);
		}
		
		
		
		
		
		
		if(ifUp<ifLeft && ifUp<ifDown && ifUp<ifRight) {
			//System.out.println("UP");
			return 1;
		}
		else if(ifLeft<ifUp && ifLeft<ifDown && ifLeft<ifRight) {
			//System.out.println("LEFT");
			return 2;
		}
		else if(ifDown<ifLeft && ifDown<ifUp && ifDown<ifRight) {
			//System.out.println("DOWN");
			return 3;
		}
		else if(ifRight<ifLeft && ifRight<ifDown && ifRight<ifUp) {
			//System.out.println("RIGHT");
			return 4;
		}


		
		if(ifUp==ifLeft) {
			
			yMove=-speed;
			testCollisionWorld();
			if(yMove==-speed)
				return 1;
			else return 2;
			
		}
		else if(ifUp==ifRight) {
			
			yMove=-speed;
			testCollisionWorld();
			if(yMove==-speed)
				return 1;
			else
				return 4;
			
		}
		else if(ifUp==ifDown) {
			
			yMove=-speed;
			testCollisionWorld();
			if(yMove==-speed)
				return 1;
			else
				return 3;
			
		}
		else if(ifLeft==ifDown) {
			
			xMove=-speed;
			testCollisionWorld();
			if(xMove==-speed)
				return 2;
			else
				return 3;
		}
		else if(ifLeft==ifRight) {
			
			xMove=-speed;
			testCollisionWorld();
			if(xMove==-1)
				return 2;
			else
				return 4;
		}
		
		else if(ifDown==ifRight) {
			
			yMove=speed;
			testCollisionWorld();
			if(yMove==speed)
				return 3;
			else
				return 4;
		}
		
		
		return -1;

		

		
	}
	
	
	
	private Animation lastMove;
	private BufferedImage getCurrentAnimationFrame() {
		if(!eaten) {
			if(!frightened) {
				if(xMove < 0) {
					lastMove = animLeft;
					return animLeft.getCurrentFrame();
				}
				else if(xMove > 0) {
					lastMove = animRight;
					return animRight.getCurrentFrame();
				}
				else if(yMove < 0) {
					lastMove = animUp;
					return animUp.getCurrentFrame();
				}
				else if(yMove > 0) {
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
				if(!frightenedExpiring) {
					return frightenedAnim.getCurrentFrame();
				}
				else {
					return frightenedExpiredAnim.getCurrentFrame();
				}
			}
		}
		else {
			if(yMove<0)
				return eatenUp;
			else if(xMove < 0)
				return eatenLeft;
			else if(yMove > 0)
				return eatenDown;
			else
				return eatenRight;
		}
	}
	
	public boolean isLeavingCage() {
		return leavingCage;
	}

	public void setLeavingCage(boolean leavingCage) {
		this.leavingCage = leavingCage;
	}
	
	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}

	public boolean getReviving() {
		return reviving;
	}
	
	public void setReviving(boolean reviving) {
		this.reviving = reviving;
	}
	
	public boolean isFrightened() {
		return frightened;
	}
	
	public boolean isGoUp() {
		return goUp;
	}

	public boolean isGoLeft() {
		return goLeft;
	}

	public boolean isGoDown() {
		return goDown;
	}

	public boolean isGoRight() {
		return goRight;
	}
}
