package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Settings;
import engine.states.Game;
import engine.states.Loading;
import objects.geometry.Polygon;
import objects.geometry.Projection;
import objects.geometry.Vector;

public class GameObject {
	public enum ObjectType { Projectile, Entity, None }
	public enum ObjectTeam { Ally, Enemy, Neutral }
	
	/* --- Instance Variables --- */
	// Removal Mark
	protected boolean remove;
	
	// Physics Variables
	protected float x, y, angle;
	protected float xVelocity, yVelocity, omega;
	
	// Hitbox
	protected Polygon hitbox;
	
	// Rendering
	protected Image sprite;
	protected float width, height;
	
	// Object Type and Team
	protected ObjectType type;
	protected ObjectTeam team;
	
	// Temp
	protected boolean collision;
	
	public GameObject(float x, float y, Polygon polygon) {
		// Default Variables
		this.omega = this.xVelocity = this.yVelocity = 0f;
		
		this.type = ObjectType.None;
		this.team = ObjectTeam.Neutral;
		
		this.remove = false;
		
		// Setting Position
		this.x = x;
		this.y = y; 
		
		// Setting Hitbox
		this.hitbox = polygon;
		polygon.setObject(this);
		
		// Add to GameObject list
		Game.GameObjects.add(this);
	}
	
	/* --- Ineherited Methods --- */
	public void objectUpdate() {}
	public void collision(GameObject o) { 
		this.collision = true;
	}
	
	/* --- Main Methods --- */
	// Update 
	public void update() {
		objectUpdate();
		updatePhysics();
	}
	
	private void updatePhysics() {
		// Update Positions
		this.move(xVelocity / Settings.Frames_Per_Second, yVelocity / Settings.Frames_Per_Second);
		this.rotate(omega / Settings.Frames_Per_Second);

		// Friction
		this.friction();
	}
	
	// Rendering
	public void draw(Graphics g) {
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
	private void friction() {
		final float Friction = 0.5f;
		this.omega -= omega * Friction;
		this.xVelocity -= xVelocity * Friction;
		this.yVelocity -= yVelocity * Friction;
	}
	private void move(float xVelocity, float yVelocity) {
		this.x += this.xVelocity;
		this.y += this.yVelocity;
	}
	private void rotate(float omega) {
		this.angle += omega;
//		this.sprite.rotate(omega);
		this.hitbox.rotate(omega);
	}
	
	/* --- Mutator Methods --- */
	public void setImage(Image newImage) { sprite = newImage; }
	public void setWidth(float newWidth) { width = newWidth; }
	public void setHeight(float newHeight) { height = newHeight; }
	
	public void setX(float newX) { x = newX; }
	public void setY(float newY) { y = newY; }
	
	public void addOmega(float newOmega) { omega += newOmega; }
	public void addXVelocity(float newXVelocity) { xVelocity += newXVelocity; }
	public void addYVelocity(float newYVelocity) { yVelocity += newYVelocity; }
	
	public void setOmega(float newOmega) { omega = newOmega; }
	public void setXVelocity(float newXVelocity) { xVelocity = newXVelocity; }
	public void setYVelocity(float newYVelocity) { yVelocity = newYVelocity; }
	public void setVelocities(float newXVelocity, float newYVelocity) {
		xVelocity = newXVelocity;
		yVelocity = newYVelocity;
	}
	
	/* --- Accessor Methods --- */
	public boolean removalMarked() { return remove; }
	
	public float getX() { return x; }
	public float getY() { return y; }
	
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	
	public Polygon getHitbox() { return hitbox; }
}