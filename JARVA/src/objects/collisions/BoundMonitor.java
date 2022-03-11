package objects.collisions;

import engine.states.Game;
import objects.collisions.Bound.End;
import objects.geometry.Polygon;
import objects.geometry.Projection;
import objects.geometry.Vector;

public class BoundMonitor {
	private Polygon polygon;
	
	private Bound startBound;
	private Bound endBound;
	
	public BoundMonitor(Polygon polygon) {
		this.polygon = polygon;
		
		this.startBound = new Bound(End.START, this);
		this.endBound = new Bound(End.END, this);
		
		// Add BoundMonitor to CollisionManager
		Game.CollisionManager.addBounds(this);
	}
	
	public Polygon getPolygon() { return polygon; }
	public boolean removalMarked() { return polygon.removalMarked(); }
	
	public Bound getStartBound() { return startBound; }
	public Bound getEndBound() { return endBound; }
	
	public void updateBounds() {
		Projection projection = Polygon.minMaxProjection(new Vector(1f, 0), new Vector(polygon.getCenterX(), polygon.getCenterY()), polygon);
		
		startBound.setValue(projection.min);
		endBound.setValue(projection.max);
	}
}