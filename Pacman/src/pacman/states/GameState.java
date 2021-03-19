package pacman.states;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import pacman.Game;
import pacman.Handler;
import pacman.entities.Dots;
import pacman.entities.EntityManager;
import pacman.entities.Fruits;
import pacman.entities.creatures.Player;
import pacman.entities.creatures.ghosts.BlueGhost;
import pacman.entities.creatures.ghosts.OrangeGhost;
import pacman.entities.creatures.ghosts.PinkGhost;
import pacman.entities.creatures.ghosts.RedGhost;
import pacman.gfx.Assets;
import pacman.worlds.World;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

public class GameState extends State{

	private World world;
	private State gameOverState;

	
	public GameState(Handler handler) {
		super(handler);
		gameOverState = new GameOverState(handler);
		
		world = new World(handler);
		handler.setWorld(world);
		world.makeWalls();
			
	}



	@Override
	public void tick() {

		world.tick();
		changeState();
		
	}

	
	@Override
	public void render(Graphics g) {
		world.render(g);


		
	}
	
	public void changeState() {
		if(world.getEntityManager().getPlayer().getLives()==0) {
			
			State.setState(gameOverState);
		}
		
		
	}
	
	
	

	

	

}
