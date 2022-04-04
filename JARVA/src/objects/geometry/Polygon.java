
package objects.geometry;

import org.newdawn.slick.Graphics;

import engine.Utility;
import objects.GameObject;
import objects.collisions.BoundMonitor;

public class Polygon {
	/* --- Instance Variables --- */
	private GameObject object;
	
	private Vector[] vertices;
	private BoundMonitor bounds; // Used for optimized collision detection
	
	// Polygons will be used for hitbox detection
	public Polygon(Vector[] vertices) {
		this.object = null;
		this.vertices = vertices;
	}
	
	/* --- Mutator Methods --- */
	// Set the polgyon's corresponding object
	public void setObject(GameObject o) { 
		this.object = o;
		this.bounds = new BoundMonitor(this);
	}

	/* --- Accessor Methods --- */
	// Returns the polygon's object
	public GameObject getObject() { return object; }
	// Returns if the object was removal marked
	public boolean removalMarked() { return object.removalMarked(); }
	
	// Returns the vertices of the polygon
	public Vector[] getVertices() { return vertices; }
	
	// Return center x and y, respectively
	public float getCenterX() { return object.getX(); }
	public float getCenterY() { return object.getY(); }
	
	/* --- Object Helper Methods --- */	
	// Check for collision with another polygon, and if so, call the object's collision method
	public void checkForCollision(Polygon polygon) {
		if(this.intersects(polygon)) {
			object.collision(polygon.object);
			polygon.object.collision(this.object);
		}
	}
	
	// Rotate every vertex by some radians counterclockwise
	public Polygon rotate(float angle) { 
		for(Vector v: vertices) { v.rotate(angle); }
		return this;
	}
	
	/* --- Static Shape Methods --- */
	// Returns the Edges for a Rectangle
	public static Vector[] rectangleEdges(float width, float height) {
		Vector[] edges = new Vector[5];
		
		float x = width / 2;
		float y = height / 2;
		
		edges[0] = new Vector(-x, -y);
		edges[1] = new Vector(-x, y);
		edges[2] = new Vector(x, y);
		edges[3] = new Vector(x, -y);
		edges[4] = new Vector(-x, -y);
		
		return edges;
	}
	// Creates a new rectangle
	public static Polygon rectangle(float width, float height) {
		Vector[] edges = new Vector[5];
		
		float x = width / 2;
		float y = height / 2;
		
		edges[0] = new Vector(-x, -y);
		edges[1] = new Vector(-x, y);
		edges[2] = new Vector(x, y);
		edges[3] = new Vector(x, -y);
		edges[4] = new Vector(-x, -y);
		
		return new Polygon(edges);
	}
	// Creates a new __ shape
	public static Polygon shape() {
		Vector[] edges = new Vector[6];
		
		edges[0] = new Vector(0, -50f);
		edges[1] = new Vector(-35f, -35f);
		edges[2] = new Vector(-35f, 35f);
		edges[3] = new Vector(0, 50f);
		edges[4] = new Vector(35f, 35f);
		edges[5] = new Vector(0, -50f);
		
		return new Polygon(edges);
	}
	
	/* --- Check for Intersection with Another Polygon (using the SAT) --- */
	// Credits to: https://dyn4j.org/2010/01/sat/#sat-algo
	public boolean intersects(Polygon polygon) { // Main method
		if(!this.axisIntersections(polygon)) return false;
		else if(!polygon.axisIntersections(this)) return false;
		else return true; 
	}
	
	// Checks intersections with the axes of each polygon (the normal vectors)
	private boolean axisIntersections(Polygon polygon) {
		// Check all axes for this polygon
		for(int i = 0; i < vertices.length - 1; i++) {
			Vector baseVertex = vertices[i];
			Vector normal = edgeNormal(baseVertex, vertices[i + 1]);
			
			// Loop through vertices for both shapes
			Projection projection1 = minMaxProjection(normal, baseVertex.flip(), this);
			Projection projection2 = minMaxProjection(normal, 
					new Vector( -baseVertex.x - getCenterX() + polygon.getCenterX(), 
							-baseVertex.y - getCenterY() + polygon.getCenterY() ),
					polygon);
			
			// Return false if any projections don't overlap (indicating no collision)
			if(!projection1.overlaps(projection2)) { return false; }
		}
		// Return true if all projections overlap
		return true;
	}
	// Return the normal vector to an edge
	private static Vector edgeNormal(Vector vertex1, Vector vertex2) { 
		return new Vector(vertex2.y - vertex1.y, vertex1.x - vertex2.x); 
	}
	// Returns the minimum/maximum projection of a polygon to a given edge
	public static Projection minMaxProjection(Vector normal, Vector relative, Polygon shape) {
		float firstProj = Utility.dot(normal, shape.vertices[0].offset(relative));
		Projection projection = new Projection(firstProj, firstProj);
		
		for(Vector vertex: shape.vertices) {
			// Simplified projection (do not need to divide by dot of normal * normal, as it will not affect comparisons)
			float proj = Utility.dot(normal, vertex.offset(relative));
			
			if(proj < projection.min) projection.min = proj;
			else if(proj > projection.max) projection.max = proj;
		}
		
		return projection;
	}
	
	public void draw(Graphics g, float x, float y) {
		for (int i = 0; i < vertices.length - 1; i++) {			
			Vector vertex1 = vertices[i];
			Vector vertex2 = vertices[i + 1];
				
			g.drawLine(x + vertex1.x, y + vertex1.y, x + vertex2.x, y + vertex2.y);
		}
	}

}