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
import objects.entities.Player;
import objects.entities.Projectile;
import objects.entities.units.AngryBoulder;
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
	public static Player Player;
	
	/* --- Managers --- */
	public static DisplayManager DisplayManager;
	public static InputManager InputManager;
	public static ArenaManager ArenaManager;
	public static CollisionManager CollisionManager;
	
	// Constructor
	public Game(int id) { this.id = id; }
		
	/* --- Accessor Methods --- */
	@Override
	public int getID() { return id; }
	public static float getTicks() { return Ticks; }
	public static float TicksPerFrame() { return Settings.Tick_Speed / Settings.Frames_Per_Second; }
	
	public ArrayList<GameObject> getGameObjects() { return GameObjects; }
	
	public ArenaManager getArenaManager() { return ArenaManager; }
	public DisplayManager getDisplayManager() { return DisplayManager; }
	public InputManager getInputManager() { return InputManager; }
	public CollisionManager getCollisionManager() { return CollisionManager; }
	
	/* --- Inherited Methods --- */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setMouseGrabbed(true);
		
		// Initialize Timers
		Ticks = 0f;
		
		// Initialize GameObjects List
		GameObjects = new ArrayList<>();
		
		// Instantiate managers
		InputManager = new InputManager(this, gc.getInput());
		CollisionManager = new CollisionManager(this);
		ArenaManager = new ArenaManager(this);
		DisplayManager = new DisplayManager(this);
		
		// Initialize Player
		Player = new Player();
				
		// Other Objects
		new AngryBoulder()
				.setX(2f)
				.setY(5f)
				.build();
	
		new AngryBoulder()
				.setX(2f)
				.setY(1f)
				.build();
		
		new AngryBoulder()
				.setX(300f)
				.setY(150f)
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
	public void mousePressed(int key, int x, int y) { InputManager.mousePressed(key, x, y); }
	
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
		
		// Update Arena
		ArenaManager.update();

		// Update displays
		DisplayManager.update();
	}
	
	/* --- Helper Methods --- */
	private void updateObjects() {
		// Update Objects
		int pointer = GameObjects.size() - 1;
		for(int i = 0; i < GameObjects.size(); i++) {
			GameObject current = GameObjects.get(i);
			
			if( current.removalMarked() ) {
				GameObject last = GameObjects.get(pointer);
				while( last.removalMarked() ) {
					if( current.equals(last) ) break;
					else {
						pointer--;
						last = GameObjects.get(pointer);
					}
				}
				
				if( current.equals(last) ) break;
				else {
					GameObjects.set(i, last);
					GameObjects.set(pointer, current);
					
					current = last;
				}
			}
			
			current.update();
		}
		// Remove Marked Object
		for(int i = GameObjects.size() - 1; i >= 0; i--) {
			GameObject current = GameObjects.get(i);
		
			if( current.removalMarked() ) {
				GameObjects.remove(i);
			} else {
				break;
			}
		}
	}
	
	
}