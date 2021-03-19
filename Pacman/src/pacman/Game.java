package pacman;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import pacman.display.Display;
import pacman.gfx.Assets;
import pacman.gfx.ImageLoader;
import pacman.gfx.SpriteSheet;
import pacman.input.KeyManager;
import pacman.states.CreditState;
import pacman.states.GameOverState;
import pacman.states.GameState;
import pacman.states.MenuState;
import pacman.states.State;



public class Game implements Runnable {

	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	

	//States
	private State gameState;
	private State menuState;
	private State creditState;
	private State gameOverState;
	//Input
	private KeyManager keyManager;
	
	//Handler
	private Handler handler;
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		
	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		Assets.init();
		handler = new Handler(this);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		creditState = new CreditState(handler);
		gameOverState = new GameOverState(handler);
		
		State.setState(menuState);
		
	}
	

	
	private void tick() {
		keyManager.tick();
		
		if(State.getState() != null);
			State.getState().tick();
		
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here
		if(State.getState() != null)
			State.getState().render(g);
		
		
		

		
		
		//End Drawing
		bs.show();
		g.dispose();
	}
	

	
	public void run() {
		
		init();
		
		int fps = 170;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000) {
				System.out.println("Ticks and Frames: "+ ticks);
				ticks=0;
				timer=0;
			}
		}
		
		stop();
		
	}
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	
	public synchronized void start() {
		if(running)
			return;
		running=true;
		thread = new Thread(this);
		thread.start();
		
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public synchronized void stop() {
		if(!running)
			return;
		running=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
