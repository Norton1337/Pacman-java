package pacman.entities.creatures.ghosts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import pacman.Handler;
import pacman.entities.creatures.Creature;
import pacman.gfx.Animation;
import pacman.gfx.Assets;

public class BlueGhost extends Creature{

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
		
		private int scatterTargetx = 681;
		private int scatterTargety = 852;
		
		private float lastMoveX = 0;
		private float lastMoveY = 0;
	
		private float targetx = scatterTargetx;
		private float targety = scatterTargety;
		
		private float realTargetx;
		private float realTargety;
		
		
		private boolean inCage = true;
		private boolean leavingCage = false;
	
		private int blueDotsCounter = 0;
		private int dotsLimit=30;
	
	

	public BlueGhost(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		this.bounds.x = (int)x;
		this.bounds.y = (int)y;
		this.bounds.width = 45;
		this.bounds.height = 45;
		
		
		
		this.setInCage(true);
		//Animations
		animRight = new Animation(100,Assets.blueGhostR);
		animLeft = new Animation(100,Assets.blueGhostL);
		animUp = new Animation(100,Assets.blueGhostU);
		animDown = new Animation(100,Assets.blueGhostD);
		frightenedAnim = new Animation(100,Assets.scaredGhost);
		frightenedExpiredAnim = new Animation(100,Assets.scaredGhostExpire);
		eatenUp = Assets.deadGhostU;
		eatenLeft = Assets.deadGhostL;
		eatenDown = Assets.deadGhostD;
		eatenRight = Assets.deadGhostR;
		
		
	}
	
	

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
		g.setColor(Color.blue);
		//g.drawRect((int) x, (int) y, 45,45);
		/*
		g.setColor(Color.blue);
		g.fillRect((int)getTargetx(),(int) getTargety(), 4, 4);
		
		g.setColor(Color.blue);
		g.fillRect((int)getRealTargetx(),(int) getRealTargety(), 4, 4);
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
	
	
	public float getRealTargetx() {
		return realTargetx;
	}

	public float getRealTargety() {
		return realTargety;
	}

	public void setRealTarget(float realTargetx, float realTargety) {
		this.realTargetx = realTargetx;
		this.realTargety = realTargety;
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
	
	
	public float[] closestPathValueXYDist(int otherX, int otherY) {
		double ifUp = 9999999;
		double ifLeft = 9999998; 
		double ifDown = 9999997;
		double ifRight = 9999996;
		int number = 0;
		int number2 = 0;
		
		float[] distancesUP = new float[2];
		float[] distancesLEFT = new float[2];
		float[] distancesDOWN = new float[2];
		float[] distancesRIGHT = new float[2];
		
		if(goUp) {
			distancesUP[0] = number  = (int) (Math.abs(targetx)-Math.abs(otherX));
			distancesUP[1] = number2 = (int) (Math.abs(targety)-Math.abs(otherY-1)); 

			ifUp = ((Math.pow(number,2)) + (Math.pow(number2,2)));
			//System.out.println("up: "+ ifUp);
		} 
		if(goLeft) {
			distancesLEFT[0] = number  = (int) (Math.abs(targetx)-Math.abs(otherX-1));
			distancesLEFT[1] =number2 = (int) (Math.abs(targety)-Math.abs(otherY));
			ifLeft =  ((Math.pow(number,2)) + (Math.pow(number2 ,2 )));
			//System.out.println("left: "+ ifLeft);
		}
		if(goDown) {
			distancesDOWN[0] = number  = (int) (Math.abs(targetx)-Math.abs(otherX));
			distancesDOWN[1] = number2 = (int) (Math.abs(targety)-Math.abs(otherY+1));
			ifDown = ((Math.pow(number,2)) + (Math.pow(number2,2)));
			//System.out.println("down: "+ifDown);
		}
		if(goRight) {
			distancesRIGHT[0] = number  = (int) (Math.abs(targetx)-Math.abs(otherX+1));
			distancesRIGHT[1] = number2 = (int) (Math.abs(targety)-Math.abs(otherY));
			ifRight = ((Math.pow(number,2)) + (Math.pow(number2,2)));
			//System.out.println("right: "+ifRight);
		}
			if(ifUp<ifLeft && ifUp<ifDown && ifUp<ifRight) {
				//System.out.println("UP");
				return distancesUP;
			}
			else if(ifLeft<ifUp && ifLeft<ifDown && ifLeft<ifRight) {
				//System.out.println("LEFT");
				return distancesLEFT;
			}
			else if(ifDown<ifLeft && ifDown<ifUp && ifDown<ifRight) {
				//System.out.println("DOWN");
				return distancesDOWN;
			}
			else if(ifRight<ifLeft && ifRight<ifDown && ifRight<ifUp) {
				//System.out.println("RIGHT");
				return distancesRIGHT;
			}
			
			if(ifUp==ifLeft) {
				
				yMove=-1;
				testCollisionWorld();
				if(yMove==-1)
					return distancesUP;
				else return distancesLEFT;
				
			}
			else if(ifUp==ifRight) {
				
				yMove=-1;
				testCollisionWorld();
				if(yMove==-1)
					return distancesUP;
				else
					return distancesRIGHT;
				
			}
			else if(ifUp==ifDown) {
				
				yMove=-1;
				testCollisionWorld();
				if(yMove==-1)
					return distancesUP;
				else
					return distancesDOWN;
				
			}
			else if(ifLeft==ifDown) {
				
				xMove=-1;
				testCollisionWorld();
				if(xMove==-1)
					return distancesLEFT;
				else
					return distancesDOWN;
			}
			else if(ifLeft==ifRight) {
				
				xMove=-1;
				testCollisionWorld();
				if(xMove==-1)
					return distancesLEFT;
				else
					return distancesRIGHT;
			}
			
			else {
				
				yMove=1;
				testCollisionWorld();
				if(yMove==1)
					return distancesDOWN;
				else
					return distancesRIGHT;
			}
			
			
		
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

	public boolean isFrightenedExpiring() {
		return frightenedExpiring;
	}

	public void setFrightenedExpiring(boolean frightenedExpiring) {
		this.frightenedExpiring = frightenedExpiring;
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
	
	public boolean isLeavingCage() {
		return leavingCage;
	}

	public void setLeavingCage(boolean leavingCage) {
		this.leavingCage = leavingCage;
	}
	
	public int getBlueDotsCounter() {
		return blueDotsCounter;
	}

	public void setBlueDotsCounter(int blueDotsCounter) {
		this.blueDotsCounter = blueDotsCounter;
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
	
	public float getTargetx() {
		return targetx;
	}

	public float getTargety() {
		return targety;
	}

}
