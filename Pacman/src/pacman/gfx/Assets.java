package pacman.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	private static final int width = 45, height = 45;
	

	public static BufferedImage[] pacmanRight, pacmanLeft, pacmanUp, pacmanDown, pacmanDEATH;
	public static BufferedImage[] redGhostR, redGhostL, redGhostU, redGhostD;
	public static BufferedImage[] pinkGhostR, pinkGhostL, pinkGhostU, pinkGhostD;
	public static BufferedImage[] blueGhostR, blueGhostL, blueGhostU, blueGhostD;
	public static BufferedImage[] orangeGhostR, orangeGhostL, orangeGhostU, orangeGhostD;
	public static BufferedImage[] scaredGhost,scaredGhostExpire;
	public static BufferedImage deadGhostR, deadGhostL, deadGhostU, deadGhostD;
	public static BufferedImage[] fruits;
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/spritesheet.png"));
		
		
		//PACMAN
		pacmanRight = new BufferedImage[4];
		pacmanRight[0] = sheet.crop(46*0, 46*0, width, height);
		pacmanRight[1] = sheet.crop(46*1, 46*0, width, height);
		pacmanRight[2] = sheet.crop(46*2, 46*0, width, height);
		pacmanRight[3] = sheet.crop(46*1, 46*0, width, height);
		pacmanDown = new BufferedImage[4];
		pacmanDown[0] = sheet.crop(46*0, 46*1, width, height);
		pacmanDown[1] = sheet.crop(46*1, 46*1, width, height);
		pacmanDown[2] = sheet.crop(46*2, 46*1, width, height);
		pacmanDown[3] = sheet.crop(46*1, 46*1, width, height);
		pacmanLeft = new BufferedImage[4];
		pacmanLeft[0] = sheet.crop(46*0, 46*2, width, height);
		pacmanLeft[1] = sheet.crop(46*1, 46*2, width, height);
		pacmanLeft[2] = sheet.crop(46*2, 46*2, width, height);
		pacmanLeft[3] = sheet.crop(46*1, 46*2, width, height);
		pacmanUp = new BufferedImage[4];
		pacmanUp[0] = sheet.crop(46*0, 46*3, width, height);
		pacmanUp[1] = sheet.crop(46*1, 46*3, width, height);
		pacmanUp[2] = sheet.crop(46*2, 46*3, width, height);
		pacmanUp[3] = sheet.crop(46*1, 46*3, width, height);
		pacmanDEATH = new BufferedImage[11];
		pacmanDEATH[0] = sheet.crop(46*4, 46*0, width, height);
		pacmanDEATH[1] = sheet.crop(46*4, 46*1, width, height);
		pacmanDEATH[2] = sheet.crop(46*4, 46*2, width, height);
		pacmanDEATH[3] = sheet.crop(46*4, 46*3, width, height);
		pacmanDEATH[4] = sheet.crop(46*5, 46*0, width, height);
		pacmanDEATH[5] = sheet.crop(46*5, 46*1, width, height);
		pacmanDEATH[6] = sheet.crop(46*5, 46*2, width, height);
		pacmanDEATH[7] = sheet.crop(46*5, 46*3, width, height);
		pacmanDEATH[8] = sheet.crop(46*6, 46*0, width, height);
		pacmanDEATH[9] = sheet.crop(46*6, 46*1, width, height);
		pacmanDEATH[10] = sheet.crop(46*6, 46*2, width, height);
		
		
		//RedGhost
		redGhostR = new BufferedImage[2];
		redGhostR[0] = sheet.crop(46*0, 46*4, width, height);
		redGhostR[1] = sheet.crop(46*1, 46*4, width, height);
		redGhostD = new BufferedImage[2];
		redGhostD[0] = sheet.crop(46*2, 46*4, width, height);
		redGhostD[1] = sheet.crop(46*3, 46*4, width, height);
		redGhostL = new BufferedImage[2];
		redGhostL[0] = sheet.crop(46*4, 46*4, width, height);
		redGhostL[1] = sheet.crop(46*5, 46*4, width, height);
		redGhostU = new BufferedImage[2];
		redGhostU[0] = sheet.crop(46*6, 46*4, width, height);
		redGhostU[1] = sheet.crop(46*7, 46*4, width, height);

		//PinkGhost
		pinkGhostR = new BufferedImage[2];
		pinkGhostR[0] = sheet.crop(46*0, 46*5, width, height);
		pinkGhostR[1] = sheet.crop(46*1, 46*5, width, height);
		pinkGhostD = new BufferedImage[2];
		pinkGhostD[0] = sheet.crop(46*2, 46*5, width, height);
		pinkGhostD[1] = sheet.crop(46*3, 46*5, width, height);
		pinkGhostL = new BufferedImage[2];
		pinkGhostL[0] = sheet.crop(46*4, 46*5, width, height);
		pinkGhostL[1] = sheet.crop(46*5, 46*5, width, height);
		pinkGhostU = new BufferedImage[2];
		pinkGhostU[0] = sheet.crop(46*6, 46*5, width, height);
		pinkGhostU[1] = sheet.crop(46*7, 46*5, width, height);
		//BlueGhost
		blueGhostR = new BufferedImage[2];
		blueGhostR[0] = sheet.crop(46*0, 46*6, width, height);
		blueGhostR[1] = sheet.crop(46*1, 46*6, width, height);
		blueGhostD = new BufferedImage[2];
		blueGhostD[0] = sheet.crop(46*2, 46*6, width, height);
		blueGhostD[1] = sheet.crop(46*3, 46*6, width, height);
		blueGhostL = new BufferedImage[2];
		blueGhostL[0] = sheet.crop(46*4, 46*6, width, height);
		blueGhostL[1] = sheet.crop(46*5, 46*6, width, height);
		blueGhostU = new BufferedImage[2];
		blueGhostU[0] = sheet.crop(46*6, 46*6, width, height);
		blueGhostU[1] = sheet.crop(46*7, 46*6, width, height);
		//OrangeGhost
		orangeGhostR = new BufferedImage[2];
		orangeGhostR[0] = sheet.crop(46*0, 46*7, width, height);
		orangeGhostR[1] = sheet.crop(46*1, 46*7, width, height);
		orangeGhostD = new BufferedImage[2];
		orangeGhostD[0] = sheet.crop(46*2, 46*7, width, height);
		orangeGhostD[1] = sheet.crop(46*3, 46*7, width, height);
		orangeGhostL = new BufferedImage[2];
		orangeGhostL[0] = sheet.crop(46*4, 46*7, width, height);
		orangeGhostL[1] = sheet.crop(46*5, 46*7, width, height);
		orangeGhostU = new BufferedImage[2];
		orangeGhostU[0] = sheet.crop(46*6, 46*7, width, height);
		orangeGhostU[1] = sheet.crop(46*7, 46*7, width, height);
		
		//ScaredGhost
		scaredGhost = new BufferedImage[2];
		scaredGhost[0] = sheet.crop(46*0, 46*8, width, height);
		scaredGhost[1] = sheet.crop(46*1, 46*8, width, height);
		
		scaredGhostExpire = new BufferedImage[4];
		scaredGhostExpire[0] = sheet.crop(46*0, 46*8, width, height);
		scaredGhostExpire[1] = sheet.crop(46*1, 46*8, width, height);
		scaredGhostExpire[2] = sheet.crop(46*2, 46*8, width, height);
		scaredGhostExpire[3] = sheet.crop(46*3, 46*8, width, height);

		
		//DeadGhost
		deadGhostR = sheet.crop(46*8, 46*4, width, height);
		deadGhostD = sheet.crop(46*8, 46*5, width, height);
		deadGhostL = sheet.crop(46*8, 46*6, width, height);
		deadGhostU = sheet.crop(46*8, 46*7, width, height);
		
		//Fruits
		fruits = new BufferedImage[8];
		fruits[0] = sheet.crop(10*46, 46*0, width, height);
		fruits[1] = sheet.crop(10*46, 46*1, width, height);
		fruits[2] = sheet.crop(10*46, 46*2, width, height);
		fruits[3] = sheet.crop(10*46, 46*3, width, height);
		fruits[4] = sheet.crop(10*46, 46*4, width, height);
		fruits[5] = sheet.crop(10*46, 46*5, width, height);
		fruits[6] = sheet.crop(10*46, 46*6, width, height);
		fruits[7] = sheet.crop(10*46, 46*7, width, height);


		
		

		
		
	
	}
}
