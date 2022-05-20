package ui.display;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.Image;

import components.Item;
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
	
	public Background getBackground() { return background; }
	
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
		
		crosshair.draw(g);
		sprintbar.render(g);
		playerHealthbar.render(g);
		
		ArrayList<Item> items = Game.Player.getInventory().getItems();

		for (int i = 0; i < items.size(); i++) {
			//make copy of image to fit height
			Image copy = items.get(i).getSprite().getScaledCopy(50f / items.get(i).getSprite().getHeight());
			copy.draw(Settings.Resolution_X, Settings.Resolution_Y - (50 * i * 1.2f) - 100, -copy.getWidth(), copy.getHeight());
			
			if (game.Player.getInventory().getEquippedItem() == items.get(i)) {
				g.setColor(Color.cyan);
				g.setLineWidth(2);
				g.drawRect(Settings.Resolution_X, Settings.Resolution_Y - (50 * i * 1.2f) - 100 - 2, -copy.getWidth(), copy.getHeight() + 4);
			}
		}
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