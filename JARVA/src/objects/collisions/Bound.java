package objects.collisions;

import objects.geometry.Polygon;

public class Bound {
	public enum End { START, END }
	
	private Polygon polygon;
	private BoundMonitor boundMonitor;
	
	private End end;
	private float value;
	
	public Bound(End end, BoundMonitor boundMonitor) {
		this.polygon = boundMonitor.getPolygon();
		this.boundMonitor = boundMonitor;
		
		this.end = end;
	}
	
 
	public void setValue(float f) { this.value = f; }
	
	/* --- Accessor Methods --- */
	public Polygon getPolygon() { return polygon; }
	public BoundMonitor getBoundMonitor() { return boundMonitor; }
	
	public boolean removalMarked() { return polygon.removalMarked(); }
	
	public float getValue() { return value; }
	public End getEnd() { return end;}
	
	
}