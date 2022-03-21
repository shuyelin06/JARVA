package objects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import engine.states.Loading;
import objects.geometry.Polygon;
import objects.geometry.Projection;
import objects.geometry.Vector;
import ui.display.images.ImageManager;

public abstract class GameObject {
	public enum ObjectType { Projectile, Unit, None }
	public enum ObjectTeam { Ally, Enemy, Neutral }
	
	/* --- Instance Variables --- */
	// Removal Mark
	protected boolean remove;
	
	// Physics Variables
	protected float x, y;
	
	protected Vector velocity;
	float angle, omega, maxVelocity;
	
	// Hitbox
	protected Polygon hitbox;
	
	// Rendering
	protected Animation animation;
	protected Image sprite; // Temp
	
	// Object Type and Team
	protected ObjectType type;
	protected ObjectTeam team;
	
	// Debug
	protected boolean collision;
	
	public GameObject(Polygon polygon) {
		// Hitbox
		this.hitbox = polygon;
		polygon.setObject(this);
		
		// Default Variables
		this.x = this.y = 0f; // Positions 
		this.angle = this.omega = 0f; // Angle
		this.velocity = new Vector(0f, 0f); // Velocity
		this.maxVelocity = Float.MAX_VALUE; // Max Velocity
		
		this.type = ObjectType.None; // Type
		this.team = ObjectTeam.Neutral; // Team
		
		this.sprite = ImageManager.getPlaceholder().copy(); // Sprite
		this.collision = false; // Collision
		this.remove = false; // Remove
	}
	
	/* --- Ineherited Methods --- */
	public abstract void objectUpdate();
	public void collision(GameObject o) { 
		this.collision = true;
	}
	
	/* --- Main Methods --- */
	// Update 
	public void update() {
		if( remove ) return;

		objectUpdate();
		updatePhysics();
	}
	
	private void updatePhysics() {
		// Cap Velocity
		final float magnitude = velocity.magnitude();
		if(magnitude > maxVelocity) { velocity.scalarMultiply(maxVelocity / magnitude ); }
		
		// Update Positions
		this.move(velocity.x / Settings.Frames_Per_Second, velocity.y / Settings.Frames_Per_Second);
		this.rotate(omega / Settings.Frames_Per_Second);

		// Friction
		final float Friction = 0.15f;
		velocity.reduce(Friction);
	}
	
	// Rendering
	public void draw(Graphics g) {
		drawSprite(g);
		drawHitbox(g);
	}
	// Rendering Methods
	private void drawSprite(Graphics g) { sprite.drawCentered(x, y); }
	protected void drawHitbox(Graphics g) {
		Vector[] vertices = hitbox.getVertices();
		
		if(collision) { g.setColor(Color.green);
		} else { g.setColor(Color.white);}

		// Draw Edges
		for (int i = 0; i < vertices.length - 1; i++) {			
			Vector vertex1 = vertices[i];
			Vector vertex2 = vertices[i + 1];
				
			g.drawLine(x + vertex1.x, y + vertex1.y, x + vertex2.x, y + vertex2.y);
		}
		
		collision = false;
	}

	/* --- Helper Methods --- */
	public void remove() { this.remove = true; }
	private void move(float xVelocity, float yVelocity) {
		this.x += xVelocity;
		this.y += yVelocity;
	}
	private void rotate(float omega) {
		this.angle += omega;
		this.sprite.rotate( Utility.ConvertToDegrees(omega) );
		this.hitbox.rotate(omega);
	}
	
	/* --- Accessor Methods --- */
	public boolean removalMarked() { return remove; }
	
	public ObjectTeam getTeam() { return team; }
	public ObjectType getType() { return type; }
	
	public float getX() { return x; }
	public float getY() { return y; }
	
	public Polygon getHitbox() { return hitbox; }
	
	/* --- Mutator / Construtor Methods --- */
	public GameObject build() { Game.GameObjects.add(this); return this; }
	
	public GameObject setSprite(Image newImage) { sprite = newImage; return this; }
	public GameObject setTeam(ObjectTeam newTeam) { team = newTeam; return this; }
	
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