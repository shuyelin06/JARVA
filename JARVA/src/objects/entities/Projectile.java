package objects.entities;

import java.util.ArrayList;

import objects.GameObject;
import objects.geometry.Polygon;

public class Projectile extends GameObject {
	// Piercing Variables
	protected ArrayList<Unit> pierced; 
	protected int pierce; 
	
	// Damage of the Projectile
	protected int damage;
	
	public Projectile(Polygon polygon) {
		super(polygon);
	}
	
	/* --- Inherited Methods --- */
	public void objectUpdate() {}
	public void collision(GameObject o) { 
		this.collision = true;
	}
}