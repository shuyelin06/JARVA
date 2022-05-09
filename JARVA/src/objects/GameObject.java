package objects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import engine.states.Loading;
import maps.Arena;
import objects.entities.Player;
import objects.geometry.Polygon;
import objects.geometry.Projection;
import objects.geometry.Vector;
import ui.display.images.ImageManager;

public abstract class GameObject {
	// Reference Variables
	protected Arena arena;
	
	public enum ObjectType { Projectile, Unit, None }
	public enum ObjectTeam { Ally, Enemy, Neutral }
	
	/* --- Instance Variables --- */
	// Switches
	protected boolean collidable; // Collision Switch
	protected boolean remove; // Removal Switch
	protected boolean friction; // Friction Switch
	protected boolean confused; // Confused Switch
	
	// Physics Variables
	protected float x, y;
	
	protected Vector velocity;
	protected float angle, omega, maxVelocity;
	
	// Hitbox
	protected Polygon hitbox;
	
	// Rendering
	protected Image sprite; // Temp
	
	// Object Type and Team
	protected ObjectType type;
	protected ObjectTeam team;
	
	// Object Damage
	protected float contactDamage; // Contact Damage
	protected float baseDamage; // Base Damage
	
	// Debug
	protected boolean collision;
	
	public GameObject(Polygon polygon) {
		// Reference Variables
		this.arena = Game.ArenaManager.getArena();
		
		// Hitbox
		this.hitbox = polygon;
		polygon.setObject(this);
		
		// Switches
		this.friction = true; // Friction
		this.collidable = true; // Collidable
		this.remove = false; // Remove
		
		// Default Variables
		this.x = this.y = 0f; // Positions 
		this.angle = this.omega = 0f; // Angle
		this.velocity = new Vector(0f, 0f); // Velocity
		this.maxVelocity = Float.MAX_VALUE; // Max Velocity
		
		this.type = ObjectType.None; // Type
		this.team = ObjectTeam.Neutral; // Team
		
		this.sprite = ImageManager.getPlaceholder().copy(); // Sprite
		this.collision = false; // Collision
		
		this.contactDamage = 0;
		this.baseDamage = 0;
	}
	
	/* --- Ineherited Methods --- */
	public abstract void objectUpdate();
	public abstract void objectDraw(Graphics g);
	public void objectDebug(Graphics g) {}
	protected abstract void objectCollision(GameObject o);
	
	/* --- Main Methods --- */
	// Update 
	public void update() {
		if( remove ) return;
		
		objectUpdate();
		updatePhysics();
		
	}
	
	protected void updatePhysics() {
		// Cap Velocity
		final float magnitude = velocity.magnitude();
		if(magnitude > maxVelocity) { velocity.scalarMultiply(maxVelocity / magnitude ); }
		
		// Update Positions
		int sign = 1;
		if(confused) sign = -1;
		
		final float xDisplacement = velocity.x / Settings.Frames_Per_Second * sign;
		final float yDisplacement = velocity.y / Settings.Frames_Per_Second * sign;
		final float thetaDisplacement = omega / Settings.Frames_Per_Second * sign;
		
		this.move(xDisplacement, yDisplacement);
		this.rotate(thetaDisplacement);

		// Friction
		if( friction ) velocity.reduce( arena.getFriction() );
	}
	
	// Rendering
	public void draw(Graphics g) {
		drawSprite(g);
		objectDraw(g);
	}
	
	protected void drawSprite(Graphics g)  { sprite.drawCentered(x, y);  }
		
	// Debug
	public void debug(Graphics g) {
		drawHitbox(g);
	}
	
	protected void drawHitbox(Graphics g) {
		if(collision) { g.setColor(Color.green);
		} else { g.setColor(Color.white);}

		// Draw Edges
		g.setLineWidth(1f);
		hitbox.draw(g, x, y);
		
		collision = false;
	}

	// Collisions
	public void collision(GameObject o) { 
		if( !collidable || !o.collidable ) return;
		else {
			collision = true;
			objectCollision(o);
		}
	}
	
	/* --- Helper Methods --- */
	public void confused(boolean confused) { this.confused = confused; }
	
	public void remove() { this.remove = true; }
	private void move(float xVelocity, float yVelocity) {
		this.x += xVelocity;
		this.y += yVelocity;
	}
	protected void rotate(float omega) {
		this.angle += omega;
		this.sprite.rotate( Utility.ConvertToDegrees(omega) );
		this.hitbox.rotate(omega);
	}
	
	/* --- Accessor Methods --- */
	public boolean removalMarked() { return remove; }
	
	public ObjectTeam getTeam() { return team; }
	public ObjectType getType() { return type; }
	
	public float getMaxVelocity() { return maxVelocity; }
	
	public float getX() { return x; }
	public float getY() { return y; }

	public float getXVelocity() { return velocity.x; }
	public float getYVelocity() { return velocity.y; }

	public float getVelocity() { return velocity.magnitude(); }
	
	public Vector getVelocityVector() { return velocity; }
	
	public float getDistance(float x, float y) { return Utility.distance(this, x, y); }
	public float getDistance(GameObject o) { return Utility.distance(this, o); }
	
	public float getAngleTo(float x, float y) { return Utility.angleBetween(this, x, y); }
	public float getAngleTo(GameObject o) { return Utility.angleBetween(this, o); }
	
	public float getBaseDamage() { return baseDamage; }
	public float getContactDamage() { return contactDamage; }
	
	public Polygon getHitbox() { return hitbox; }
	public Image getSprite() { return sprite; }
	
	/* --- Mutator / Construtor Methods --- */
	public GameObject build() { Game.GameObjects.add(this); return this; }
	
	public GameObject setCollidable(boolean b) { collidable = b; return this; }
	
	public GameObject setSprite(Image newImage) { sprite = newImage; return this; }
	public GameObject setTeam(ObjectTeam newTeam) { team = newTeam; return this; }
	
	public GameObject offsetPosition(float offsetX, float offsetY) { x += offsetX; y += offsetY; return this; }
	public GameObject setX(float newX) { x = newX; return this; }
	public GameObject setY(float newY) { y = newY; return this; }
	
	public GameObject setMaxVelocity(float newMaxVelocity) { maxVelocity = newMaxVelocity; return this; }
	
	public GameObject addOmega(float newOmega) { omega += newOmega; return this; }
	public GameObject addXVelocity(float newXVelocity) { velocity.addX(newXVelocity); return this; }
	public GameObject addYVelocity(float newYVelocity) { velocity.addY(newYVelocity); return this; }
	
	public GameObject setOmega(float newOmega) { omega = newOmega; return this; }
	public GameObject setXVelocity(float newXVelocity) { velocity.setX(newXVelocity); return this; }
	public GameObject setYVelocity(float newYVelocity) { velocity.setY(newYVelocity); return this; }
}