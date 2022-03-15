package objects.entities;

import org.lwjgl.Sys;

import objects.GameObject;
import objects.geometry.Polygon;

public class Entity extends GameObject {
	// Invulnerability
	protected boolean invulnerable;
	protected float invulnerability; 
	
	protected float lastDamageTaken;
	
	// Stats
	protected float health;
	protected float maxHealth;
	
	protected float block;
	protected float damage;
	
	public Entity(Polygon polygon) {
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
	public Entity setHealth(float newHealth) { health = newHealth; return this; }
	public Entity setMaxHealth(float newMaxHealth) { maxHealth = newMaxHealth; return this; }
	
	public Entity setBlock(float newBlock) { block = newBlock; return this; }
	public Entity setDamage(float newDamage) { damage = newDamage; return this; }
	
	/* --- Overwritten Methods --- */
	@Override
	public void remove() { 
		super.remove();
	}
	
}