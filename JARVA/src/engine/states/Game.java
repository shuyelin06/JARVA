package engine.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import objects.GameObject;
import objects.collisions.CollisionManager;
import objects.geometry.Polygon;

public class Game extends BasicGameState {
	private int id; // GameState ID
	
	
	public static ArrayList<GameObject> GameObjects; // All Game Objects
	
	/*
	 * TerritoryManager
	 * EntityManager
	 * SoundManager
	 * 
	 */
	
	// Managers
	public static CollisionManager CollisionManager;
	
	// Constructor
	public Game(int id) { 
		this.id = id;
	}
	
	@Override
	public int getID() { return id; }
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		GameObjects = new ArrayList<>();
		
		// Instantiate managers
		CollisionManager = new CollisionManager(this);
		
		// Temp
		GameObject o1 = new GameObject(50f, 200f, Polygon.shape());
		o1.setXVelocity(0.15f * 60);
		GameObject o2 = new GameObject(150f, 150f, Polygon.shape());
		o2.setYVelocity(0.15f * 60);
		GameObject o3 = new GameObject(300f, 150f, Polygon.shape());
		o3.setXVelocity(-0.3f * 60);
		GameObject o4 = new GameObject(300f, 500f, Polygon.shape());
		o4.setYVelocity(-0.5f * 60);
		GameObject o5 = new GameObject(200f, 0f, Polygon.shape());
		o5.setYVelocity(0.25f * 60);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		for(GameObject object: GameObjects) { 
			object.draw(arg2); 
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		for(GameObject object: GameObjects) {
			object.update();
		}
		CollisionManager.update();
	}
}