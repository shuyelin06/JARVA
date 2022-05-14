package objects.entities;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import components.conditions.Burn;
import components.conditions.Condition;
import components.conditions.Confusion;
import components.conditions.Invulnerable;
import components.conditions.Poison;
import components.conditions.Stun;
import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.geometry.Polygon;
import ui.display.animation.Animation;
import ui.display.images.ImageManager;;

public abstract class Unit extends GameObject {
	private final float ContactKnockback = 25f;
	
	// Switches
	protected boolean immovable; // Knockback Switch 
	protected boolean invulnerable; // Invulnerable Switch 
	protected boolean stunned; // Stunned Switch
	protected boolean mirrored; // Sprite Mirroring
	
	// Animations
	private static final float DamageFlash = 0.15f;
	private static final int Default_Frame = 0;
	private static final int Attack_Frame = 1;
	
	protected Animation animation;
	
	protected float damageFlash;
	
	protected Image shadow;
	protected boolean hasShadow;
	protected boolean shadowScaled;
	
	protected int width, height;
	protected boolean attacking;
	
	// Effects
	protected HashMap<Condition.Type, Condition> conditions;
	
	// Score
	protected int score;
	
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
		this.mirrored = false;
		
		// Animation
		this.animation = null;
		
		// Unit Conditions
		this.conditions = new HashMap<>();
		this.initializeConditions();
		
		this.damageBlock = 0f;
		this.knockbackBlock = 0f;
		
		this.maxHealth = 100f; // Max Health
		this.health = maxHealth; // Current Health
		
		this.contactDamage = 1f; // Contact Damage
		this.baseDamage = 1f; // Base Damage
		
		this.hasShadow = true;
		this.shadowScaled = false;
		this.shadow = ImageManager.getImageCopy("shadow");
	}
	
	/* --- Inherited Methods --- */
	protected abstract void unitUpdate();
	protected abstract void onDeath();
	protected void unitDraw(Graphics g) {}
	
	/* --- Implemented Methods --- */
	public void objectDraw(Graphics g) {
		if(this.conditionActive(Condition.Type.Stun)) {
			g.setColor(Color.orange);
			g.draw(new Circle(x, y, 24f));
		}
		if(this.conditionActive(Condition.Type.Invulnerable)) {
			g.setColor(Color.blue);
			g.draw(new Circle(x, y, 25f));
		}
		if(this.conditionActive(Condition.Type.Confusion)) {
			g.setColor(Color.pink);
			g.draw(new Circle(x, y, 26f));
		}
		if(this.conditionActive(Condition.Type.Poison)) {
			g.setColor(Color.red);
			g.draw(new Circle(x, y, 27f));
		}
		if(this.conditionActive(Condition.Type.Burn)) {
			g.setColor(Color.green);
			g.draw(new Circle(x, y, 28f));
		}
		unitDraw(g);
	}
	
	@Override
	protected void drawSprite(Graphics g) {
		sprite.setFilter(Image.FILTER_NEAREST);
		
		shadowShenanigans();
		
		if( animation != null ) {
			if( attacking ) sprite = animation.getFrame(Attack_Frame);
			else sprite = animation.getFrame(Default_Frame);
			
			sprite = sprite.getScaledCopy(width, height);
			sprite.rotate( Utility.ConvertToDegrees(angle) );
		}
		
		if(angle == 0 && mirrored) {
			if( damageFlash > 0 ) {
				damageFlash -= Game.TicksPerFrame(); 
				sprite.drawFlash(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
			} else {
				sprite.getFlippedCopy(true, false)
					.drawCentered(x, y);
			}
		}
		else {
			if( damageFlash > 0 ) {
				damageFlash -= Game.TicksPerFrame();
				sprite.drawFlash(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
			} else {
				sprite.drawCentered(x, y);
			}
		}
	}
	@Override
	public void objectUpdate() {
		// Apply Conditions
		for (Condition c: conditions.values()) {
			c.apply();
		}
		
		// Entity Dying
		if(health <= 0f) {
			Game.GameScore += score;
			onDeath();
			remove();
			return;
		}
		
		// Entity AI
		if(!stunned) unitUpdate();
		
		// Sprite Mirroring
		mirroredCheck();
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
	private void initializeConditions() {
		conditions.put(Condition.Type.Invulnerable, new Invulnerable(this));
		conditions.put(Condition.Type.Confusion, new Confusion(this));
		
		conditions.put(Condition.Type.Burn, new Burn(this));
		conditions.put(Condition.Type.Poison, new Poison(this));
		conditions.put(Condition.Type.Stun, new Stun(this));
	}
	
	public void shadowShenanigans()
	{
		if(!shadowScaled && this.sprite.getName() != "placeholder" && sprite.getWidth() < 80)
		{
			this.shadow = shadow.getScaledCopy((float)sprite.getWidth() / (float)shadow.getWidth());

			shadowScaled = true;
		}
		
		if(shadowScaled && shadow != null)
		{
			if(shadow.getAlpha() != 0)
			{
				shadow.setFilter(Image.FILTER_NEAREST);
				shadow.drawCentered(x , y + sprite.getHeight() * 0.5f - shadow.getHeight() * 0.2f);	
			}
		}
	}
	
	protected void mirroredCheck() {
		if(velocity.x < 0) mirrored = true;
		else mirrored = false;
	}
	
	public void stunned(boolean stunned) { this.stunned = stunned; }
	public void invulnerable(boolean invulnerable) { this.invulnerable = invulnerable; }
	
	public void takeCondition(Condition.Type type, float timer) {
		conditions.get(type).addTimer(timer);
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
			if(damage == 0) return;
			
			damageFlash = DamageFlash;
			health -= damage - damage * damageBlock;
			velocity.reduce(0.75f);
		}
	}
	
	/* --- Accessor Methods --- */	
	public HashMap<Condition.Type, Condition> getConditions() { return conditions; }
	public Condition getCondition(Condition.Type type) { return conditions.get(type); }
	public boolean conditionActive(Condition.Type type) { return getCondition(type).isActive(); }
	
	public boolean isMirrored() { return mirrored; }
	
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