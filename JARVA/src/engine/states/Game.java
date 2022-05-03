package engine.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.Main;
import engine.Settings;
import engine.states.levels.LevelManager;
import maps.ArenaManager;
import objects.GameObject;
import objects.GameObject.ObjectTeam;
import objects.GameObject.ObjectType;
import objects.collisions.CollisionManager;
import objects.entities.Unit;
import objects.entities.Player;
import objects.entities.Projectile;
import objects.entities.units.Eagle;
import objects.entities.units.AngryBoulder;
import objects.entities.units.BananaTree;
import objects.entities.units.Tumbleweed;
import ui.display.DisplayManager;
import ui.input.InputManager;
import ui.sound.SoundManager;
import objects.geometry.Polygon;
import ui.input.InputManager;

public class Game extends BasicGameState {
	private int id; // GameState ID
	
	// Player Score
	public static int GameScore;
	
	// Game Timer
	public static float Ticks;
	public static float Difficulty;
	
	// Game Objects
	public static ArrayList<GameObject> GameObjects; 
	public static Player Player;
	
	/* --- Managers --- */
	public static DisplayManager DisplayManager;
	public static InputManager InputManager;
	public static ArenaManager ArenaManager;
	public static CollisionManager CollisionManager;
	public static LevelManager LevelManager;
	
	// Constructor
	public Game(int id) { this.id = id; }
		
	/* --- Accessor Methods --- */
	@Override
	public int getID() { return id; }
	public static float getTicks() { return Ticks; }
	public static float TicksPerFrame() { return Settings.Ticks_Per_Frame / Settings.Frames_Per_Second; }
	
	public ArrayList<GameObject> getGameObjects() { return GameObjects; }
	public ArrayList<GameObject> getEnemies() 
	{ 
		ArrayList<GameObject> enemies = new ArrayList<GameObject>();
		
		for(GameObject u : GameObjects)
		{
			if(u.getTeam() == ObjectTeam.Enemy && u.getType() == ObjectType.Unit) enemies.add(u);
		}
		
		return(enemies);
	}
	
	public ArenaManager getArenaManager() { return ArenaManager; }
	public DisplayManager getDisplayManager() { return DisplayManager; }
	public InputManager getInputManager() { return InputManager; }
	public CollisionManager getCollisionManager() { return CollisionManager; }
	public LevelManager getLevelManager() { return LevelManager; }
	
	/* --- Inherited Methods --- */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setMouseGrabbed(true);
		
		// Reset Game Score
		GameScore = 0;
		
		// Initialize Timer and Difficulty
		Settings.Scale = Settings.BaseScale;
		
		// Initialize Timers
		Ticks = 0f;
		Difficulty = 1f;
		
		// Initialize GameObjects List
		GameObjects = new ArrayList<>();
		
		// Instantiate managers
		InputManager = new InputManager(this, gc.getInput());
		CollisionManager = new CollisionManager(this);
		ArenaManager = new ArenaManager(this);
		DisplayManager = new DisplayManager(this);
		LevelManager = new LevelManager(this);
		
		// Initialize Player
		Player = new Player();
				
		// Other Objects
		BananaTree.bananaTrees.clear();
		
		// Background Music
		SoundManager.playBackgroundMusic("angry desert monkey");
	}
	
	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) {
		SoundManager.stopBackgroundMusic();		
	}


	@Override // Input Determining
	public void keyPressed(int key, char c) { InputManager.keyPressed(key); }
	
	@Override
	public void mousePressed(int key, int x, int y) { InputManager.mousePressed(key, x, y); }
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException { DisplayManager.render(g); }

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int n) throws SlickException {
		// If player is dead, send to end game screen
		if( Game.Player.getPercentHealth() <= 0f ) {
			SoundManager.playSoundEffect("dead", 1f);
			Settings.LastState = Main.GAME_ID;
			sbg.enterState(Main.END_ID);
			return;
		}
		
		// Game Timer
		Ticks += Settings.Ticks_Per_Frame / Settings.Frames_Per_Second;
		
		// Difficulty Scaling
		Difficulty = 1 + (float) Math.floor(Ticks / 60f);
				
		// Input Manager
		InputManager.update();
		
		// Update GameObjects
		updateObjects();
		
		// Determine Collisions
		CollisionManager.update();
		
		// Update Arena
		ArenaManager.update();
		
		LevelManager.update();

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