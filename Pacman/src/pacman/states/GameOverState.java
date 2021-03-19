package pacman.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import pacman.Handler;

public class GameOverState extends State{

	private State menuState;
	
	private String highScore = "";
	
	Font newfont;
	
	public GameOverState(Handler handler) {
		super(handler);
		
		
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, new File("emulogic.tff")).deriveFont(30f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("emulogic.tff")));
		}
		catch(IOException | FontFormatException e) {
			
		}
		
			if(highScore.equals("")) {
				try {
					highScore = this.getHighScore();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	@Override
	public void tick() {
		isEnterPressed();
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 696, 900);
		try {
			writeText(g);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		blinkText(g);
		
	}
	
	
	
	Font text = new Font ("emulogic", 1, 50);
	Font mediumText = new Font ("emulogic", 1, 30);
	public void writeText(Graphics g) throws FileNotFoundException {
		g.setFont(text);
		g.setColor(Color.white);
		g.drawString("GAME OVER", 105 , 400);
		g.setFont(mediumText);
		g.drawString("SCORE", 265, 100);
		g.drawString(""+handler.getWorld().getEntityManager().getPoints(),265,140);
		//getHighScore();
		checkScore();
		
		
		g.drawString("HighScore", 200, 650);
		g.drawString(highScore, 190, 690);
		
		
	}
	Font smallText = new Font ("emulogic", 1, 15);
	int k = 0;
	private void blinkText(Graphics g) {
		
		g.setFont(smallText);
		if(k<100) {

			g.setColor(Color.white);
			g.drawString("(Press enter to restart)", 160, 450);
		}
		if(k>100) {

			g.setColor(Color.black);
			g.drawString("(Press enter to restart)", 160, 450);
		}
		if(k>200)
			k=0;
		k++;
		
	}
	
	public String getHighScore() throws FileNotFoundException {
		FileReader readFile= null;
		BufferedReader reader = null;
		
		try
		{
		readFile = new FileReader("highscore.dat");
		reader = new BufferedReader(readFile);
		return reader.readLine();
		}
		
		catch (Exception e)
		{
		return "Nobody:0";
		}
		finally
		{
			try {
				if(reader != null)
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void checkScore() {
		if(highScore.equals(""))
			return;
		if(handler.getWorld().getEntityManager().getPoints()>Integer.parseInt((highScore.split(":")[1]))) {
			String name= JOptionPane.showInputDialog("NEW HIGHSCORE! Insert Name: ");
			highScore = name + ":" + handler.getWorld().getEntityManager().getPoints();
			
			File scoreFile = new File("highscore.dat");
			if(!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try 
			{
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(this.highScore);
			}
			catch (Exception e)
			{
				//errors
			}
			finally
			{
				if(writer != null)
					try {
						writer.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
			}
			
		}
	}
	
	private void isEnterPressed() {

		if(handler.getKeyManager().enter) {
			menuState = new MenuState(handler);
			State.setState(menuState);
		}
	}
	
	

}
