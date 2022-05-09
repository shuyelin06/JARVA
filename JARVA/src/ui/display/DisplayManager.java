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
import ui.display.hud.Healthbar;
import ui.display.background.Background;
import ui.display.hud.Crosshair;
import ui.display.hud.Sprintbar;
import ui.input.InputManager;

public class DisplayManager {
	public static boolean Debug = false;
	
	private Game game;
	
	private Arena arena;
	
	private SpriteSheet tileset;
	
	// Background
	private Background background;
	
	// Center of Dispalying
	final private static float Screen_X = Settings.Resolution_X / 2f;
	final private static float Screen_Y = Settings.Resolution_Y / 2f;
	private float cameraX;
	private float cameraY;
	private float displayOffset;
	private Crosshair crosshair;
	private Sprintbar sprintbar;
	private Healthbar playerHealthbar;
	
	public DisplayManager(Game game) {
		this.game = game;
		this.arena = game.getArenaManager().getArena();
		
		this.crosshair = new Crosshair(new Color(255, 255, 255), 20); //change the crosshair colors in the settings
		this.sprintbar = new Sprintbar(Game.Player);
		this.playerHealthbar = new Healthbar(Game.Player);
		this.displayOffset = 0.15f;
		
		this.background = new Background();
	}
	
	private void cameraPosition() {
		float mouseX = InputManager.getMapMouseX();
		float mouseY = InputManager.getMapMouseY();
		
		cameraX = Game.Player.getX() - displayOffset * (Game.Player.getX() - mouseX);
		cameraY = Game.Player.getY() - displayOffset * (Game.Player.getY() - mouseY);
	}
	
	public void update() {
		// Draw HUD
		
		arena = game.getArenaManager().getArena();
		
		crosshair.update();
		
		sprintbar.update();
		
		playerHealthbar.setUnit(Game.Player);
		playerHealthbar.update();
	}
	
	public void render(Graphics g) {
		// Determine Camera Position
		cameraPosition();
		
		// Render Game Elements
		g.scale(Settings.Scale, Settings.Scale); // Scaling
		g.translate( Screen_X / Settings.Scale - cameraX, Screen_Y / Settings.Scale - cameraY ); // Centering
		
		renderBackground(g); // Render Background
		renderArena(g); // Render Arena
		renderObjects(g); // Render All Objects
		renderGun(g); // Render Gun
		
		// Debug Mode
		if ( Debug ) renderDebug(g);
		
		g.resetTransform();
	
		// Draw HUD (on top of everything else)
		renderHUD(g);
	}
	
	public void renderHUD(Graphics g) {
		//render the HUD
		//health bar, item list, tutorial, etc.
		
		g.setColor(Color.white);
		
		g.drawString("Timer: " + ( (Integer) (int) Game.Ticks).toString(), 15f, 15f);
		g.drawString("Difficulty: " + ( (Float) Game.Difficulty).toString(), 15f, 30f);
		g.drawString("Score: " + ( (Integer) (int) Game.GameScore).toString(), 15f, 45f);
		
		crosshair.draw(g);
		sprintbar.render(g);
		playerHealthbar.render(g);
	}
	

	public void renderGun(Graphics g) { Game.Player.getInventory().draw(g); }
	public void renderDebug(Graphics g) {
		for( final GameObject object: game.getGameObjects() ) {
			object.debug(g);
		}
	}
	public void renderBackground(Graphics g) {
		background.render(g);
	}
	public void renderObjects(Graphics g) {
		for ( GameObject object: game.getGameObjects() ) {
			object.draw(g);
		}
	}
	
	public void renderArena(Graphics g) {
		//render arena
		arena.draw(g);

	}
	
	/* --- Mutator Methods */
	public void setDisplayOffset(float displayOffset) { this.displayOffset = displayOffset; }
	
}