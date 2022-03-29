package ui.display;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import engine.Main;
import engine.Settings;
import engine.states.Game;
import maps.Arena;
import objects.GameObject;
import objects.entities.Player;
import ui.input.InputManager;

public class DisplayManager {
	public static boolean Debug = false;
	
	private Game game;
	
	private Arena arena;
	
	private SpriteSheet tileset;
	
	// Center of Dispalying
	final private static float Screen_X = Settings.Resolution_X / 2f;
	final private static float Screen_Y = Settings.Resolution_Y / 2f;
	private float cameraX;
	private float cameraY;
	private Crosshair crosshair;
	
	public DisplayManager(Game game) {
		this.game = game;
		
		this.arena = game.getArenaManager().getArena();
		this.crosshair = new Crosshair(new Color(255, 255, 255), 20); //change the crosshair colors in the settings
	}
	
	private void cameraPosition() {
		cameraX = Game.Player.getX();
		cameraY = Game.Player.getY();
	}
	
	public void update() {
		// Draw HUD
		
		arena = game.getArenaManager().getArena();
		
		crosshair.update();
	}
	
	public void render(Graphics g) {
		// Determine Camera Position
		cameraPosition();
		
		// Draw HUD
		renderHUD(g);
		
		// Render Game Elements
		g.scale(Settings.Scale, Settings.Scale); // Scaling
		g.translate( Screen_X / Settings.Scale - cameraX, Screen_Y / Settings.Scale - cameraY ); // Centering
		
		renderArena(g); // Render Arena
		renderObjects(g); // Render All Objects
		crosshair.draw(g);
	}
	
	public void renderHUD(Graphics g) {
		//render the HUD
		//health bar, item list, tutorial, etc.
	}
	

	
	public void renderObjects(Graphics g) {
		for (GameObject object: game.getGameObjects()) 
		{
			object.draw(g);
		}
	}
	
	public void renderArena(Graphics g) {
		//render arena

	}
}