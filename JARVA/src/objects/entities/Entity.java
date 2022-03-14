package objects.entities;

import objects.GameObject;
import objects.geometry.Polygon;

public class Entity extends GameObject {
	protected float health;
	protected float maxHealth;
	
	protected float damage;
	
	public Entity(float x, float y, Polygon polygon) {
		super(x, y, polygon);
		
		this.type = ObjectType.Entity;
		this.team = ObjectTeam.Neutral;
	}
}