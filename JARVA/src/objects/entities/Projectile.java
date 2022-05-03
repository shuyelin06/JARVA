package objects.entities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import components.conditions.Invulnerable;
import components.conditions.Stun;
import engine.states.Game;
import objects.GameObject;
import objects.geometry.Polygon;

public abstract class Projectile extends GameObject {	
	// Projectile Stun
	protected float stun = 0.125f;
	
	// Piercing Variables
	protected ArrayList<Unit> pierced; 
	protected int pierce; 
	
	// Damage of the Projectile
	protected GameObject source;
	
	protected float knockback;
	protected float damageMultiplier;
	
	public Projectile(Polygon polygon, GameObject source) {
		super(polygon);
		
		this.source = source;
		this.type = ObjectType.Projectile;
		
		this.pierced = new ArrayList<Unit>();
		
		// Default Variables
		this.knockback = 50f;
		this.friction = false; // No Friction
		this.pierce = 1; // # Units Projectile can Pierce
		this.damageMultiplier = 1f; // Base Damage
	}
	
	/* --- Inherited Methods --- */
	public abstract void projectileUpdate();

	/* --- Implemented Methods --- */
	@Override
	public void objectUpdate() {
		if(pierce == 0) {
			remove();
			return;
		}
		
		// Projectile AI
		projectileUpdate();
	}
	
	public void applyCondition(Unit u) {
		//apply condition, should be overridden in projectile
	}
	
	public void objectCollision(GameObject o) {
		if( o.getType() == ObjectType.Unit && source.getTeam() != o.getTeam() ) {
			Unit unit = (Unit) o;
			
			if( pierced.contains(unit) ) return;
			else {
				unit.takeDamage(source.getBaseDamage() * damageMultiplier); // Damage
				unit.takeKnockback(this, knockback); // Knockback
//				unit.takeCondition(new Stun(this, unit, Unit.Default_Stun)); // Apply Stun
				
				applyCondition(unit);
				
				pierced.add(unit);
				pierce--;
			}
			
		}
	}
	
	public void draw(Graphics g)
	{
		sprite.setFilter(Image.FILTER_NEAREST);
		super.draw(g);
	}
	
	
	/* --- Mutator / Construtor Methods --- */
	public Projectile setPierce(int newPierce) { pierce = newPierce; return this; }
	public Projectile setKnockback(float newKnockback) { knockback = newKnockback; return this; }
	public Projectile setDamageMultiplier(float newMultiplier) { damageMultiplier = newMultiplier; return this; }
}