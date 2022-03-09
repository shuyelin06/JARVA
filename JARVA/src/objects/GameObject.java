package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.states.Loading;
import objects.geometry.Polygon;
import objects.geometry.Projection;
import objects.geometry.Vector;

public class GameObject {
	/* --- Instance Variables --- */
	// Position Variables
	protected float x, y;
	protected float angle;
	
	// Hitbox
	protected Polygon hitbox;
	
	// Rendering
	protected Image sprite;
	protected float width, height;
	
	public GameObject(float x, float y, Polygon polygon) {
		this.x = x;
		this.y = y; 
		
		polygon.setObject(this);
		this.hitbox = polygon;
	}
	
	// Rendering Methods
	public void draw(Graphics g) {
		Vector[] vertices = hitbox.getVertices();
		
		for (int i = 0; i < vertices.length - 1; i++) {
			// Draw Edges
			if(Loading.intersects) { g.setColor(Color.green);
			} else { g.setColor(Color.white);}
			Vector vertex1 = vertices[i];
			Vector vertex2 = vertices[i + 1];
				
			g.drawLine(x + vertex1.x, y + vertex1.y, x + vertex2.x, y + vertex2.y);
		}
	}
	
	/* --- Helper Methods --- */
	public boolean intersects(GameObject o) { return hitbox.intersects(o.hitbox); }
	
	/* --- Mutator Methods --- */
	public void setImage(Image newImage) { sprite = newImage; }
	public void setWidth(float newWidth) { width = newWidth; }
	public void setHeight(float newHeight) { height = newHeight; }
	
	public void setAngle(float newAngle) { angle = newAngle; }
	public void setX(float newX) { x = newX; }
	public void setY(float newY) { y = newY; }
	
	/* --- Accessor Methods --- */
	public float getX() { return x; }
	public float getY() { return y; }
	
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	
	public Polygon getHitbox() { return hitbox; }
}