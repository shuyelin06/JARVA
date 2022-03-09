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
//		Vector[] edges = new Vector[7];
//		
//		edges[0] = new Vector(0, -50f);
//		edges[1] = new Vector(-35f, -35f);
//		edges[2] = new Vector(-35f, 35f);
//		edges[3] = new Vector(0, 50f);
//		edges[4] = new Vector(35f, 35f);
//		edges[5] = new Vector(35f, -35f);
//		edges[6] = new Vector(0, -50f);
		Vector[] edges = new Vector[6];
		
		edges[0] = new Vector(0, -50f);
		edges[1] = new Vector(-35f, -35f);
		edges[2] = new Vector(-35f, 35f);
		edges[3] = new Vector(0, 50f);
		edges[4] = new Vector(35f, 35f);
		edges[5] = new Vector(0, -50f);
		
		return new Polygon(edges);
	}
	
	/* Check for Intersection with Another Polygon (using the SAT)
	 * Credits to: https://dyn4j.org/2010/01/sat/#sat-algo */
	public boolean intersects(Polygon polygon) { // Main method
		if(!this.axisIntersections(polygon)) return false;
		else if(!polygon.axisIntersections(this)) return false;
		else return true; 
	}
	
	// Intersection Helper Methods
	private boolean axisIntersections(Polygon polygon) {
		// Check all axes for this polygon
		for(int i = 0; i < vertices.length - 1; i++) {
			Vector baseVertex = vertices[i];
			Vector normal = edgeNormal(baseVertex, vertices[i + 1]);
			
			// Loop through vertices for both shapes
			Projection projection1 = minMaxProjection(normal, baseVertex.flip(), this);
			Projection projection2 = minMaxProjection(normal, 
					new Vector(
							-baseVertex.x - getCenterX() + polygon.getCenterX(),
							-baseVertex.y - getCenterY() + polygon.getCenterY()
							),
					polygon);
			
			if(!projection1.overlaps(projection2)) { return false; }
		}
		
		return true;
	}
	public static Projection minMaxProjection(Vector normal, Vector relative, Polygon shape) {
		float firstProj = Utility.dot(normal, shape.vertices[0].offset(relative.x, relative.y));
		Projection projection = new Projection(firstProj, firstProj);
		
		for(Vector vertex: shape.vertices) {
			Vector relativeVertex = new Vector(vertex.x + relative.x, vertex.y + relative.y);
			float proj = Utility.dot(normal, relativeVertex);
			
			if(proj < projection.min) {
				projection.min = proj;
			} else if(proj > projection.max) {
				projection.max = proj;
			}
		}
		
		return projection;
	}
	public static Vector edgeNormal(Vector vertex1, Vector vertex2) { return new Vector(vertex2.y - vertex1.y, vertex1.x - vertex2.x); }
}