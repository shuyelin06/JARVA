package objects.geometry;

import engine.Utility;
import objects.GameObject;

public class Polygon {
	private GameObject object;
	private Vector[] vertices;
	
	// Polygons will be used for hitbox detection
	public Polygon(Vector[] vertices) {
		this.object = null;
		this.vertices = vertices;
	}
	
	// Mutator Methods
	public void setObject(GameObject o) { this.object = o; }
	
	// Accessor Methods
	public Vector[] getVertices() { return vertices; }
	
	public float getCenterX() { return object.getX(); }
	public float getCenterY() { return object.getY(); }
	
	// Static Helper Methods
	public static Polygon rectangle(float width, float height) {
		Vector[] edges = new Vector[5];
		
		edges[0] = new Vector(-width / 2, -height / 2);
		edges[1] = new Vector(-width / 2, height / 2);
		edges[2] = new Vector(width / 2, height / 2);
		edges[3] = new Vector(width / 2, -height / 2);
		edges[4] = new Vector(-width / 2, -height / 2);
		
		return new Polygon(edges);
	}
	public static Polygon shape() {
		Vector[] edges = new Vector[7];
		
		edges[0] = new Vector(0, -50f);
		edges[1] = new Vector(-35f, -35f);
		edges[2] = new Vector(-35f, 35f);
		edges[3] = new Vector(0, 50f);
		edges[4] = new Vector(35f, 35f);
		edges[5] = new Vector(35f, -35f);
		edges[6] = new Vector(0, -50f);
		
		return new Polygon(edges);
	}
	
	// https://dyn4j.org/2010/01/sat/#sat-algo
	// Returns true if this intersects another polygon (using the Separating Axis Theorem)
	public boolean intersects(Polygon polygon) {
		// Check all axes for this polygon
		for(int i = 0; i < vertices.length - 1; i++) {
			Vector vertex1 = vertices[i];
			Vector vertex2 = vertices[i + 1];
			
			Vector edge = new Vector(vertex2.x - vertex1.x, vertex2.y - vertex1.y);
			Vector normal = new Vector(edge.y, -edge.x); // Get the perpendicular normal to the edge
			
			// Loop through vertices for both shapes
			float min1 = 0f;
			float max1 = 0f;
			for(Vector v: vertices) {
				Vector relativeVector = new Vector(v.x - vertex1.x, v.y - vertex1.y);
				float projection = Utility.dot(normal, relativeVector);
				
				if(projection < min1 || min1 == 0f) {
					min1 = projection;
				} else if(projection > max1 || max1 == 0f) {
					max1 = projection;
				}
			}
			
			float min2 = 0f;
			float max2 = 0f;
			for(Vector v: polygon.vertices) {
				Vector relativeVector = new Vector(
						v.x - vertex1.x - getCenterX() + polygon.getCenterX(), 
						v.y - vertex1.y - getCenterY() + polygon.getCenterY()
						);
				float projection = Utility.dot(normal, relativeVector);
				
				if(projection < min2 || min2 == 0f) {
					min2 = projection;
				} else if(projection > max2 || max2 == 0f) {
					max2 = projection;
				}
			}
			
			if(max1 < min2 || max2 < min1) return false;
			
		}
		
		// Check all axes for this polygon
		for(int i = 0; i < polygon.vertices.length - 1; i++) {
			Vector vertex1 = polygon.vertices[i];
			Vector vertex2 = polygon.vertices[i + 1];
			
			Vector edge = new Vector(vertex2.x - vertex1.x, vertex2.y - vertex1.y);
			Vector normal = new Vector(edge.y, -edge.x);
			
			// Loop through vertices for both shapes
			float min1 = 0f;
			float max1 = 0f;
			for(Vector v: polygon.vertices) {
				Vector relativeVector = new Vector(v.x - vertex1.x, v.y - vertex1.y);
				float projection = Utility.dot(normal, relativeVector);
				
				if(projection < min1 || min1 == 0f) {
					min1 = projection;
				} else if(projection > max1 || max1 == 0f) {
					max1 = projection;
				}
			}
			
			float min2 = 0f;
			float max2 = 0f;
			for(Vector v: vertices) {
				Vector relativeVector = new Vector(
						v.x - vertex1.x - getCenterX() + polygon.getCenterX(), 
						v.y - vertex1.y - getCenterY() + polygon.getCenterY()
						);
				float projection = Utility.dot(normal, relativeVector);
				
				if(projection < min2 || min2 == 0f) {
					min2 = projection;
				} else if(projection > max2 || max2 == 0f) {
					max2 = projection;
				}
			}
			
			if(max1 < min2 || max2 < min1) return false;
			
		}
		
		return true;
	}	
	private boolean axisIntersections(Polygon polygon) {
		// Check all axes for this polygon
		for(int i = 0; i < vertices.length - 1; i++) {
			Vector vertex1 = vertices[i];
			Vector vertex2 = vertices[i + 1];
			
			Vector edge = new Vector(vertex2.x - vertex1.x, vertex2.y - vertex1.y);
			Vector normal = new Vector(edge.y, -edge.x);
			
			// Loop through vertices for both shapes
			float min1 = 0f;
			float max1 = 0f;
			for(Vector v: vertices) {
				Vector relativeVector = new Vector(v.x - vertex1.x, v.y - vertex1.y);
				float projection = Utility.dot(normal, relativeVector);
				
				if(projection < min1 || min1 == 0f) {
					min1 = projection;
				} else if(projection > max1) {
					max1 = projection;
				}
			}
			
			float min2 = 0f;
			float max2 = 0f;
			for(Vector v: polygon.vertices) {
				Vector relativeVector = new Vector(v.x - vertex1.x, v.y - vertex1.y);
				float projection = Utility.dot(normal, relativeVector);
				
				if(projection < min1) {
					min2 = projection;
				} else if(projection > max1) {
					max2 = projection;
				}
			}
			
			if(max1 < min2 || max2 < min1) return false;
			
		}
		
		return true;
	}
	private static Vector edgeNormal(Vector vertex1, Vector vertex2) {
		return null;
	}
}

class Projection {
	public float min;
	public float max;
	
	public Projection(float min, float max) {
		this.min = min;
		this.max = max;
	}
}