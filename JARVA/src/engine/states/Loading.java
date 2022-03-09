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
	
	
	private GameObject o1;
	private GameObject o2;
	
	// Constructor
	public Loading(int id) { 
		this.id = id;
		
		this.o1 = new GameObject(50f, 500f, Polygon.shape());
		this.o2 = new GameObject(150f, 150f, Polygon.shape());
	}
	
	@Override
	public int getID() { return id; }

	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		if(o1.intersects(o2)) {
			arg2.setColor(Color.green);
		} else {
			arg2.setColor(Color.white);
		}
		
		o1.setX(0.15f + o1.getX());
		o2.setY(0.5f + o2.getY());
		
		o1.draw(arg2);
		o2.draw(arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		
	}
}