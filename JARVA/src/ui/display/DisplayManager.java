package ui.display;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import engine.states.Game;
import maps.Arena;
import maps.Tile;
import objects.GameObject;

public class DisplayManager {
	private Game game;
	private Arena arena;
	
	private Graphics graphics;
	
	private SpriteSheet tileset;
	
	private Point center;
	
	public DisplayManager(Game game) {
		this.game = game;
	}
	
	public void update() {
		// Draw HUD
		
		arena = game.getArenaManager().getArena();
	}
	
	public void render(Graphics g) {
		
		
		renderObjects(g);
		renderArena(g);
	}
	
	public void renderObjects(Graphics g) {
		for (GameObject object: game.getGameObjects()) {
			object.draw(g);
		}
	}
	
	public void renderArena(Graphics g) {
		for (Tile[] tileList: arena.getTiles()) {
			//render all tiles with g.scale?
		}
	}
}