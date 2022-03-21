package ui.display;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import engine.Main;
import engine.states.Game;
import maps.Arena;
import maps.Tile;
import objects.GameObject;

public class DisplayManager {
	private Game game;
	private Arena arena;
	
	private SpriteSheet tileset;
	
	private final float scale = 3;
	
	public DisplayManager(Game game) {
		this.game = game;
	}
	
	public void update() {
		// Draw HUD
		
		arena = game.getArenaManager().getArena();
		
	}
	
	public void render(Graphics g) {
		
		renderHUD(g);
		
		g.scale(scale, scale);
		renderObjects(g);
		renderArena(g);
//		if (game.getDebug()) {
//			for (int i = 0; i < 100; i++) {
//				g.drawLine(0, i, (Main.getScreenWidth() / scale) - 30, i);
//				g.drawLine(i, 0, i, (Main.getScreenHeight() / scale) - 30);
//			}
//		}
		
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