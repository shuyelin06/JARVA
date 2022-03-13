package maps;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Settings;
import objects.GameObject;

public abstract class Tile {
	// Sprite Image
	protected Image sprite;
	
	// Tile's coefficient of friction
	protected float friction;
	
	public Tile(float friction) {
		this.friction = friction;
	}
	
	abstract public void applyEffect(GameObject o);
	
	public Image getSprite() { return sprite; }
	public float getFriction() { return friction; }
}