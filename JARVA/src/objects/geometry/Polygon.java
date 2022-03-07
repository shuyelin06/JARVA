package objects.geometry;

public class Polygon {
	private Object object;
	private Vector[] edges;
	
	// Polygons will be used for hitbox detection
	public Polygon(Vector[] edges) {
		this.edges = edges;
	}
	
	// Returns true if this intersects another polygon
	public boolean intersects(Polygon polygon) {
		for (int i = 0; i < edges.length; i++) {
			
		}
		
		return false;
	}
	
	
}