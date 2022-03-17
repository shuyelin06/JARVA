package objects.entities;

import org.lwjgl.Sys;

import objects.GameObject;
import objects.geometry.Polygon;

public class Unit extends GameObject {
	// Invulnerability
	protected boolean invulnerable;
	protected float invulnerability; 
	
	protected float lastDamageTaken;
	
	// Stats
	protected float health;
	protected float maxHealth;
	
	protected float block;
	protected float damage;
	
	public Unit(Polygon polygon) {
		super(polygon);
		
		this.type = ObjectType.Entity;
		this.team = ObjectTeam.Neutral;
		
		// Default Values
		this.invulnerability = 0.5f;
				
		this.maxHealth = 100f;
		this.health = maxHealth;
		
		this.damage = 1f;
	}
	
	/* --- Inherited Methods --- */
	public void entityUpdate() {}
	public void collision(GameObject o) {
		this.collision = true;
	}
	
	/* --- Main Methods --- */
	public void objectUpdate() {
		// Entity Dying
		if(health < 0f) {
			this.remove();
			return;
		}
		
		// Invulnerability
		if(lastDamageTaken < invulnerability) { invulnerable = false; }
		
		entityUpdate();
	}
	
	public void takeDamage(GameObject o, float damage) { // Overwritable
		if(invulnerable) health -= damage * block;
	}
	
	/* --- Mutator / Construtor Methods --- */	
	public Unit setHealth(float newHealth) { health = newHealth; return this; }
	public Unit setMaxHealth(float newMaxHealth) { maxHealth = newMaxHealth; return this; }
	
	public Unit setBlock(float newBlock) { block = newBlock; return this; }
	public Unit setDamage(float newDamage) { damage = newDamage; return this; }
	
	/* --- Overwritten Methods --- */
	@Override
	public void remove() { 
		super.remove();
	}
	
}