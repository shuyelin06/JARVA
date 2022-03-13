package engine.states;

import objects.GameObject;
import objects.geometry.Polygon;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Loading extends BasicGameState {
	private int id;
	
	private String message;
	
	// Constructor
	public Loading(int id) { 
		this.id = id;
		
	}
	
	@Override
	public int getID() { return id; }

	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setMouseGrabbed(true);
		
		this.message = "Loading";
	}


	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString(message, 5f, 5f);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		
	}
}