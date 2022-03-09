package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import engine.states.Loading;
import objects.geometry.Polygon;
import objects.geometry.Projection;
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
			
			
			// Draw Edge Normal
			g.setColor(Color.blue);
			Vector normal = Polygon.edgeNormal(vertex1, vertex2);
			g.drawLine(x + vertex1.x, y + vertex1.y, x + vertex1.x + normal.x, y + vertex1.y + normal.y);
			
			// Draw Projections
			if(i == 0) {
				GameObject other = this;
				if(this.equals(Loading.o1)) { other = Loading.o2;
				} else { other = Loading.o1; }
				
				Projection projection1 = Polygon.minMaxProjection(normal, vertex1.flip(), this.hitbox);
				Projection projection2 = Polygon.minMaxProjection(normal, 
						new Vector(
								-vertex1.x - x + other.x,
								-vertex1.y - y + other.y
								),
						other.hitbox);
				g.setColor(Color.orange);
				g.drawLine(x + vertex1.x, y + vertex1.y, x + vertex1.x + normal.x * projection1.min, y + vertex1.y + normal.y * projection1.min);
				g.drawLine(x + vertex1.x, y + vertex1.y, x + vertex1.x + normal.x * projection1.max, y + vertex1.y + normal.y * projection1.max);
				
				g.setColor(Color.pink);
				g.drawLine(x + vertex1.x, y + vertex1.y, x + vertex1.x + normal.x * projection2.min, y + vertex1.y + normal.y * projection2.min);
				g.drawLine(x + vertex1.x, y + vertex1.y, x + vertex1.x + normal.x * projection2.max, y + vertex1.y + normal.y * projection2.max);
			}
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