package objects.entities;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import components.conditions.Condition;
import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.geometry.Polygon;

public abstract class Unit extends GameObject {
	private final float ContactKnockback = 25f;
	
	// Switches
	protected boolean immovable; // Knockback Switch 
	protected boolean invulnerable; // Invulnerable Switch 
	protected boolean stunned; // Stunned Switch
	
	// Effects
	protected ArrayList<Condition> conditions;
	
	// Stunning
	protected static float Default_Stun = 0.10f; // Default Stun Timer
	protected float stun;
	protected float lastStunned;
	
	// Invulnerability
	protected static float Default_Invulnerability = 0.10f; // Default Invulnerability Timer
	protected float invulnerability; // Invulnerability Timer
	protected float lastDamageTaken; // Damage Last Taken
	
	// Stats
	protected float health; // Current Health
	protected float maxHealth; // Max Health
	
	protected float knockbackBlock; // % Knockback Blocked
	protected float damageBlock; // % Damage Blocked
	
	public Unit(Polygon polygon) {
		super(polygon);
		
		this.type = ObjectType.Unit;
		this.team = ObjectTeam.Neutral;
		
		// Switches
		this.immovable = false;
		this.invulnerable = false;
		this.stunned = false;
		
		// Unit Conditions
		this.conditions = new ArrayList<>();
				
		// Default Values
		this.invulnerability = Default_Invulnerability; // Seconds Invulnerable
		
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
		// Apply Conditions
		for (Condition c: conditions) {
			c.apply();
		}
		
		// Entity Dying
		if(health <= 0f) {
			remove();
			return;
		}
		
		// Invulnerability Timer
		if(Game.getTicks() - lastDamageTaken > invulnerability) { invulnerable = false; }
		
		// Stun Timer
		if(Game.getTicks() - lastStunned > stun) { stunned = false; }
		
		// Entity AI
		if(!stunned) unitUpdate();
	}
	
	@Override
	public void objectCollision(GameObject o) {
		if( o.getType() == ObjectType.Unit ) {
			Unit unit = (Unit) o;
			unit.takeKnockback(this, ContactKnockback);
			
			if( o.getTeam() != this.getTeam() ) {
				unit.takeDamage(contactDamage);
			}
		}
	}
	
	/* --- Helper Methods --- */
	protected void stunned() { stunned(Default_Stun); }
	protected void stunned(float time) {
		stunned = true;
		stun = time;
		lastStunned = Game.getTicks();
	}
	
	
	protected void invulnerable() { invulnerable(Default_Invulnerability); }
	protected void invulnerable(float time) {
		invulnerable = true;
		invulnerability = time;
		lastDamageTaken = Game.getTicks();
	}
	public void takeCondition(Condition condition) {
		conditions.add(condition);
	}
	public void takeHealing(float heal) {
		health += heal;
		
		if(health > maxHealth) { 
			health = maxHealth; 
		}
	}
	
	public void takeKnockback(GameObject o, float knockback) {
		if(!immovable) {
			float angle = Utility.atan( y - o.getY(), x - o.getX() );
			takeKnockback(angle, knockback);
		}
	}
	
	public void takeKnockback(float angle, float knockback) {
		if(!immovable) {
			float knockbackReceived = knockback - knockback * knockbackBlock;
			addXVelocity( knockbackReceived * Utility.cos(angle) );
			addYVelocity( knockbackReceived * Utility.sin(angle) );
		}
	}
	
	public void takeDamage(float damage) { // Overwritable
		if( !invulnerable ) {
			health -= damage - damage * damageBlock;
			velocity.reduce(0.75f);
		}
	}
	
	/* --- Accessor Methods --- */	
	public float getMaxHealth() { return maxHealth; }
	public float getCurHealth() { return health; }
	public float getPercentHealth() { return health / maxHealth; }
	
	/* --- Mutator / Construtor Methods --- */	
	public Unit setHealth(float newHealth) { health = newHealth; return this; }
	public Unit setMaxHealth(float newMaxHealth) { maxHealth = newMaxHealth; return this; }
	
	public Unit setDamageBlock(float newBlock) { damageBlock = newBlock; return this; }
	public Unit setKnockbackBlock(float newBlock) { knockbackBlock = newBlock; return this; }
	
	public Unit setContactDamage(float newDamage) { contactDamage = newDamage; return this; }
	public Unit setBaseDamage(float newDamage) { baseDamage = newDamage; return this; }
}