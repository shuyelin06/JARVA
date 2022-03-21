package maps.tiles;

import maps.Tile;
import objects.GameObject;

public class PitTile extends Tile {
	final public static float Friction = 1f;
	
	public PitTile() {
		super(Friction);
		
		this.sprite = null; // Temp
	}
	
	public void applyEffect(GameObject o) {
		o.setXVelocity(0f).setYVelocity(0f);
	}
}