package pacman.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import pacman.Handler;

	
	
public class MenuState extends State{
	
	Font newfont;
	private State gameState;
	private State creditState;
		
	public MenuState(Handler handler) {
		super(handler);
			
		
		gameState = new GameState(handler);
		creditState = new CreditState(handler);
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, new File("emulogic.tff")).deriveFont(30f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("emulogic.tff")));
		}
		catch(IOException | FontFormatException e) {
			
		}
		}
	
	
		@Override
		public void tick() {
			isCPressed();
			
		}
	
		Font bigText = new Font ("emulogic", 1, 40);
		Font smallText = new Font ("emulogic", 1, 15);
		
		@Override
		public void render(Graphics g) {
			bigText = new Font ("emulogic", 1, 40);
			g.setColor(Color.black);
			g.fillRect(0, 0, 696, 900);
			g.setColor(Color.white);
			g.setFont(bigText);
			g.drawString("INSERT COIN", 120, 400);
			blinkText(g);
			g.setColor(Color.white);
			g.drawString("Press 'C' for credits", 200, 850);
			
		}
		private int k=0;
		private void blinkText(Graphics g) {
			
			g.setFont(smallText);
			if(k<100) {

				g.setColor(Color.white);
				g.drawString("(Press space to start)", 170, 450);
			}
			if(k>100) {

				g.setColor(Color.black);
				g.drawString("(Press space to start)", 170, 450);
			}
			if(k>200)
				k=0;
			k++;
			
		}
		
		private void isCPressed() {
			//System.out.println("test");
			if(handler.getKeyManager().c) {
				State.setState(creditState);
				
			}
			if(handler.getKeyManager().space)
				State.setState(gameState);
		}
		
	}
