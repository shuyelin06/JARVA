package engine.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.Settings;
import maps.ArenaManager;
import objects.GameObject;
import objects.collisions.CollisionManager;
import ui.display.DisplayManager;
import ui.input.InputManager;
import objects.geometry.Polygon;
import ui.input.InputManager;

public class Game extends BasicGameState {
	private int id; // GameState ID
	
	// Game Timer
	public static float Ticks;
	
	// Game Objects
	public static ArrayList<GameObject> GameObjects; 
	public static GameObject Player;
	
	// Managers
	public static DisplayManager DisplayManager;
	public static InputManager InputManager;
	
	public static ArenaManager ArenaManager;
	public static CollisionManager CollisionManager;
	
	// Constructor
	public Game(int id) { this.id = id; }
	
	@Override
	public int getID() { return id; }
	
	public static int getTicks() { return (int) Ticks; }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialize Timers
		Ticks = 0f;
		
		// Initialize GameObjects List
		GameObjects = new ArrayList<>();
		
		// Instantiate managers
		InputManager = new InputManager(this, gc.getInput());
		CollisionManager = new CollisionManager(this);
		InputManager = new InputManager(this, gc.getInput());
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialize Player
		Player = new GameObject(Polygon.shape())
				.setX(300f)
				.setY(400f);
		
		// Other Objects
		new GameObject( Polygon.rectangle(50f, 100f).rotate(1.5f) )
				.setOmega(0.1f)
				.setX(50f)
				.setY(200f)
				.setXVelocity(0.15f);
		
		new GameObject( Polygon.shape() )
				.setX(150f)
				.setY(150f)
				.setYVelocity(0.15f);
		
		new GameObject( Polygon.shape() )
				.setX(300f)
				.setY(150f)
				.setXVelocity(-0.3f);
		
		new GameObject( Polygon.shape() )
				.setX(300f)
				.setY(500f)
				.setYVelocity(-0.5f);
		
		new GameObject( Polygon.shape() )
				.setX(200f)
				.setY(0f)
				.setYVelocity(0.25f);
	}

	@Override // Input Determining
	public void keyPressed(int key, char c) { InputManager.keyPressed(key); }
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for(GameObject object: GameObjects) { 
			object.draw(g); 
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		// Update Timers
		Ticks += Settings.Tick_Speed / Settings.Frames_Per_Second;
		
		// Input Manager
		InputManager.update();
		
		// Update GameObjects
		for(GameObject object: GameObjects) { object.update(); }
		
		// Determine Collisions
		CollisionManager.update();
	}
	
	
}