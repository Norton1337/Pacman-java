package pacman.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import pacman.Game;
import pacman.Handler;
import pacman.gfx.ImageLoader;

public class CreditState extends State{
	
	private BufferedImage grandpa;
	private State menuState;

	
	Font newfont;
	private float y=1000;
	private float y2=y;
	public CreditState(Handler handler) {
		super(handler);
		
		
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, new File("emulogic.tff")).deriveFont(30f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("emulogic.tff")));
		}
		catch(IOException | FontFormatException e) {
			
		}
		grandpa = ImageLoader.loadImage("/textures/Grandpa.JPG");
		
	}

	@Override
	public void tick() {
		textGoUp();
		keyPressed();
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 696, 900);
		writeText(g);
		
	}
	Font text = new Font ("emulogic", 1, 20);
	Font text2 = new Font ("emulogic", 1, 10);
	private void writeText(Graphics g) {
		g.setFont(text);
		g.setColor(Color.white);
		g.drawString("Made by: Paulo Norton", 20, (int)y);
		g.drawString("Started in: 17/10/2020", 20, (int)y+40);
		g.drawString("Ended in: (TBD)", 20, (int)y+80);
		g.drawString("In Loving Memory of:",145, (int)y2+200);
		g.drawString("Antonio Lomba Antunes", 130, (int)y2+230);
		//501 548
		g.drawImage(grandpa, 93 , (int)y2+250 ,null);
		g.drawString("Born: 15 February 1936", 100,(int) y2+835);
		g.drawString("Died: 21 October 2020", 100, (int)y2+865);
	}
	
	private void textGoUp() {
		y-=.3;
		y2=y;
		if(y2<=-70) {
			y2=-70;
		}
	}
	
	private void keyPressed() {
		//System.out.println("test");
		
		if(handler.getKeyManager().enter) {
			menuState = new MenuState(handler);
			State.setState(menuState);
		}
			
	}

}
