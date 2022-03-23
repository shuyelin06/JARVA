package ui.display;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import engine.Main;
import engine.Settings;
import engine.states.Game;
import maps.Arena;
import maps.Tile;
import objects.GameObject;
import objects.entities.Player;

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
	
	public DisplayManager(Game game) {
		this.game = game;
		
		this.arena = game.getArenaManager().getArena();
	}
	
	private void cameraPosition() {
		cameraX = Game.Player.getX();
		cameraY = Game.Player.getY();
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
	}
	
	public void renderHUD(Graphics g) {
		//render the HUD
		//health bar, item list, tutorial, etc.
	}
	

	
	public void renderObjects(Graphics g) {
		for (GameObject object: game.getGameObjects()) {
			object.draw(g);
		}
	}
	
	public void renderArena(Graphics g) {
		//render arena
//		for (Tile[] tileList: arena.getTiles()) {
//			//render all tiles with g.scale?
//		}
	}
}