package maps;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Settings;
import objects.GameObject;

public class Tile {
	protected Image sprite;
	
	// Tile's x and y tile coordinate
	protected int x;
	protected int y;

	// Tile's coefficient of friction
	protected float friction;
	
	public Tile(int x, int y, float friction) {
		this.x = x;
		this.y = y;
		
		this.friction = friction;
	}
	
	public void draw(Graphics g) { sprite.drawCentered(x * Settings.Tile_Size, y * Settings.Tile_Size); }
	public float getFriction() { return friction; }
	
	public void applyEffect(GameObject o) {}
}