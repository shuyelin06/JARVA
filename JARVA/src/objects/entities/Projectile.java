package objects.entities;

import java.util.ArrayList;

import objects.GameObject;
import objects.geometry.Polygon;

public abstract class Projectile extends GameObject {
	public enum ProjectileType {}
	
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
	@Override
	public void collision(GameObject o) {
		super.collision(o);
		
		if( o.getType() == ObjectType.Unit && source.getTeam() != o.getTeam() ) {
			Unit unit = (Unit) o;
			
			if( pierced.contains(unit) ) return;
			else {
				unit.takeDamage(source.getBaseDamage() * damageMultiplier);
				unit.takeKnockback(this, knockback);
				
				pierced.add(unit);
				pierce--;
			}
			
		}
	}
	
	
	
	/* --- Mutator / Construtor Methods --- */
	public Projectile setPierce(int newPierce) { pierce = newPierce; return this; }
	
	public Projectile setKnockback(float newKnockback) { knockback = newKnockback; return this; }
	public Projectile setDamageMultiplier(float newMultiplier) { damageMultiplier = newMultiplier; return this; }
}