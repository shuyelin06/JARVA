package engine.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class State extends BasicGameState {
	private int id;
	
	// Constructor
	public State(int id) { 
		this.id = id;
	}
	
	@Override
	public int getID() { return id; }
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		
	}
}