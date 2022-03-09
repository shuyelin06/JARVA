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
	
	// Constructor
	public Loading(int id) { 
		this.id = id;
		
		o1 = new GameObject(50f, 500f, Polygon.shape());
		o2 = new GameObject(150f, 150f, Polygon.shape());
	}
	
	@Override
	public int getID() { return id; }

	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
	}

	public static boolean intersects;
	public static GameObject o1;
	public static GameObject o2;
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		intersects = o1.intersects(o2);
		
		if(!intersects) {
			o1.getHitbox().rotate(0.01f);
			o1.setX(0.15f + o1.getX());
			o2.getHitbox().rotate(-0.01f);
			o2.setY(0.5f + o2.getY());
		}
		
		o1.draw(arg2);
		o2.draw(arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		
	}
}