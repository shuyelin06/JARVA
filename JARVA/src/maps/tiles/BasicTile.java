package maps.tiles;

import maps.Tile;
import objects.GameObject;

public class BasicTile extends Tile {
	final public static float Friction = 0.5f;
	
	public BasicTile() {
		super(Friction);
		
		this.sprite = null;
	}
	
	public void applyEffect(GameObject o) {}
}