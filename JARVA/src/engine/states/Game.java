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
import objects.entities.Unit;
import objects.entities.units.Tumbleweed;
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

	public static boolean debug;
	
	/*
	 * TerritoryManager
	 * EntityManager
	 * SoundManager
	 * 
	 */
	
	// Managers
	public static DisplayManager DisplayManager;
	public static InputManager InputManager;
	public static ArenaManager ArenaManager;
	public static CollisionManager CollisionManager;
	
	// Accessor Methods
	public ArrayList<GameObject> getGameObjects() { return GameObjects; }
	public ArenaManager getArenaManager() { return ArenaManager; }
	public DisplayManager getDisplayManager() { return DisplayManager; }
	public InputManager getInputManager() { return InputManager; }
	public CollisionManager getCollisionManager() { return CollisionManager; }
	public boolean getDebug() { return debug; }
	
	// Constructor
	public Game(int id) { this.id = id; }
	
	@Override
	public int getID() { return id; }
	
	public static int getTicks() { return (int) Ticks; }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialize Timers
		Ticks = 0f;
		
    // Debug Mode
		debug = false;
		
		// Initialize GameObjects List
		GameObjects = new ArrayList<>();
		
		// Instantiate managers
		InputManager = new InputManager(this, gc.getInput());
		CollisionManager = new CollisionManager(this);
    DisplayManager = new DisplayManager(this);
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Initialize Player
		Player = new objects.entities.Player()
				.setX(300f)
				.setY(400f)
				.build();
		
		// Other Objects
		new Tumbleweed()
				.setOmega(0.1f)
				.setX(50f)
				.setY(200f)
				.setXVelocity(0.15f)
				.build();
		
		new Tumbleweed()
				.setX(150f)
				.setY(150f)
				.setYVelocity(0.15f)
				.build();
		
		new Tumbleweed()
				.setX(300f)
				.setY(150f)
				.setXVelocity(-0.3f)
				.build();
		
		new Tumbleweed()
				.setX(300f)
				.setY(500f)
				.setYVelocity(-0.5f)
				.build();
		
		new Tumbleweed()
				.setX(200f)
				.setY(0f)
				.setYVelocity(0.25f)
				.build();
	}

	@Override // Input Determining
	public void keyPressed(int key, char c) { InputManager.keyPressed(key); }
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException { DisplayManager.render(g); }

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int n) throws SlickException {
		// Update Timers
		Ticks += Settings.Tick_Speed / Settings.Frames_Per_Second;
		
		// Input Manager
		InputManager.update();
		
		// Update GameObjects
		updateObjects();
		
		// Determine Collisions
		CollisionManager.update();
	}
	
	private void updateObjects() {
		// Update Objects
		int pointer = GameObjects.size() - 1;
		for(int i = 0; i < GameObjects.size(); i++) {
			GameObject current = GameObjects.get(i);
			if(current.removalMarked()) {
				// Move marked objects to the end of the list
				if(pointer == i) break;
				
				GameObject last = GameObjects.get(pointer);
				
				GameObjects.set(i, last);
				GameObjects.set(pointer, current);
				
				pointer--;
			} else {
				// Else, Update Object
				current.update(); 
			}
		}
		// Remove Marked Object
		for(int i = GameObjects.size() - 1; i >= 0; i--) {
			GameObject current = GameObjects.get(i);
		
			if(current.removalMarked()) {
				GameObjects.remove(i);
			} else break;
		}
	}
	
	
}