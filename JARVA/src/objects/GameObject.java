package objects;

import org.newdawn.slick.Graphics;

import objects.geometry.Polygon;
import objects.geometry.Vector;

public class GameObject {
	// Position
	protected float x, y;
	
	// Hitbox
	protected Polygon hitbox;
	
	// Rendering
	protected float width, height;
	
	public GameObject(float x, float y, Polygon polygon) {
		this.x = x;
		this.y = y; 
		
		polygon.setObject(this);
		this.hitbox = polygon;
	}
	
	public void draw(Graphics g) {
		Vector[] vertices = hitbox.getVertices();
		
		for (int i = 0; i < vertices.length - 1; i++) {
			Vector vertex1 = vertices[i];
			Vector vertex2 = vertices[i + 1];
			
			g.drawLine(x + vertex1.x, y + vertex1.y, x + vertex2.x, y + vertex2.y);
		}
	}
	
	// Helper Methods
	public boolean intersects(GameObject o) { return hitbox.intersects(o.hitbox); }
	
	// Mutator Methods
	public void setX(float newX) { x = newX; }
	public void setY(float newY) { y = newY; }
	
	// Accessor Methods
	
	
	public float getX() { return x; }
	public float getY() { return y; }
	
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	
	public Polygon getHitbox() { return hitbox; }
}