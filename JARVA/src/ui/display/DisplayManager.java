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

public class DisplayManager {
	private Game game;
	private Arena arena;
	
	private SpriteSheet tileset;
	
	public DisplayManager(Game game) {
		this.game = game;
	}
	
	public void update() {
		// Draw HUD
		
		arena = game.getArenaManager().getArena();
		
	}
	
	public void render(Graphics g) {
		
		renderHUD(g);
		
		g.scale(Settings.Scale, Settings.Scale);
		renderObjects(g);
		renderArena(g);
		
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