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
	
	private Crosshair crosshair;
	
	public DisplayManager(Game game) {
		this.game = game;
		
		crosshair = new Crosshair(new Color(255, 255, 255), 20); //change the crosshair colors in the settings
	}
	
	public void update() {
		// Draw HUD
		
		arena = game.getArenaManager().getArena();
		
		crosshair.update();
	}
	
	public void render(Graphics g) {
		
		renderHUD(g);
		
		g.scale(Settings.Scale, Settings.Scale);
		renderObjects(g);
		renderArena(g);
		
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