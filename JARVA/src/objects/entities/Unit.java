package objects.entities;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.geometry.Polygon;

public abstract class Unit extends GameObject {
	private final float ContactKnockback = 35f;
	
	// Switches
	protected boolean immovable; // Knockback Switch 
	protected boolean invulnerable; // Invulnerable Switch 
		
	// Invulnerability
	protected float invulnerability; // Invulnerability Timer
	protected float lastDamageTaken; // Damage Last Taken
	
	// Stats
	protected float health; // Current Health
	protected float maxHealth; // Max Health
	
	protected float knockbackBlock; // % Knockback Blocked
	protected float damageBlock; // % Damage Blocked
	
	protected float contactDamage; // Contact Damage
	protected float baseDamage; // Base Damage
	
	public Unit(Polygon polygon) {
		super(polygon);
		
		this.type = ObjectType.Unit;
		this.team = ObjectTeam.Neutral;
		
		// Default Values
		this.invulnerability = 0.25f; // Seconds Invulnerable
		
		this.damageBlock = 0f;
		this.knockbackBlock = 0f;
		
		this.maxHealth = 100f; // Max Health
		this.health = maxHealth; // Current Health
		
		this.contactDamage = 1f; // Contact Damage
		this.baseDamage = 1f; // Base Damage
	}
	
	/* --- Inherited Methods --- */
	protected abstract void unitUpdate();
	
	/* --- Implemented Methods --- */
	public void objectDraw(Graphics g) {
		if(invulnerable) {
			g.setColor(Color.blue);
			g.draw(new Circle(x, y, 25f));
		}
	}
	@Override
	public void objectUpdate() {
		// Entity Dying
		if(health <= 0f) {
			remove();
			return;
		}
		
		// Invulnerability Timer
		if(Game.getTicks() - lastDamageTaken > invulnerability) { invulnerable = false; }
		
		// Entity AI
		unitUpdate();
	}
	
	@Override
	public void collision(GameObject o) {
		super.collision(o);
		
		if( o.getType() == ObjectType.Unit ) {
			Unit unit = (Unit) o;
			unit.takeKnockback(this, ContactKnockback);
			
			if( o.getTeam() != this.getTeam() ) {
				unit.takeDamage(baseDamage);
			}
		}
	}
	
	/* --- Helper Methods --- */
	public void takeHealing(float heal) {
		health += heal;
		
		if(health > maxHealth) { 
			health = maxHealth; 
		}
	}
	public void takeKnockback(GameObject o, float knockback) {
		if(!immovable) {
			float angle = Utility.atan( o.getY() - getY(), o.getX() - getX() );
			angle += Math.PI;
			
			float knockbackReceived = knockback - knockback * knockbackBlock;
			addXVelocity( knockbackReceived * Utility.cos(angle) );
			addYVelocity( knockbackReceived * Utility.sin(angle) );
		}
	}
	public void takeDamage(float damage) { // Overwritable
		if( !invulnerable ) {
			health -= damage - damage * damageBlock;
			
			invulnerable = true;
			lastDamageTaken = Game.getTicks();
		}
	}
	
	/* --- Accessor Methods --- */
	public float getBaseDamage() { return baseDamage; }
	public float getContactDamage() { return contactDamage; }
	
	/* --- Mutator / Construtor Methods --- */	
	public Unit setHealth(float newHealth) { health = newHealth; return this; }
	public Unit setMaxHealth(float newMaxHealth) { maxHealth = newMaxHealth; return this; }
	
	public Unit setDamageBlock(float newBlock) { damageBlock = newBlock; return this; }
	public Unit setKnockbackBlock(float newBlock) { knockbackBlock = newBlock; return this; }
	
	public Unit setContactDamage(float newDamage) { contactDamage = newDamage; return this; }
	public Unit setBaseDamage(float newDamage) { baseDamage = newDamage; return this; }
}