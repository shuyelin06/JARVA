package objects.collisions;

import java.util.ArrayList;

import engine.states.Game;
import objects.collisions.Bound.End;
import objects.geometry.Polygon;

public class CollisionManager {
	private Game game;
	
	private ArrayList<BoundMonitor> monitors;
	private ArrayList<Bound> bounds;
	
	public CollisionManager(Game game) {
		this.game = game;
		
		this.monitors = new ArrayList<>();
		this.bounds = new ArrayList<>();
	}
	
	public void addBounds(BoundMonitor monitor) {
		monitors.add(monitor);
		
		bounds.add(monitor.getStartBound());
		bounds.add(monitor.getEndBound());
	}
	// Super inefficient; may use binary search to remove later
	public void removeInactiveBounds() {
		// Remove all inactive monitors
		ArrayList<BoundMonitor> activeMonitors = new ArrayList<>();
		for(BoundMonitor monitor: monitors) {
			if(!monitor.getPolygon().removalMarked()) {
				activeMonitors.add(monitor);
			}
		}
		this.monitors = activeMonitors;
		
		// Remove all inactive bounds
		for(int i = bounds.size() - 1; i >= 0; i--) {
			Bound cur = bounds.get(i);
			if(cur.removalMarked()) bounds.remove(i);
			else break;
		}
	}
	
	// Runs a sort and sweep algorithm to optimize collision checking
	public void update() {
		// Remove inactive bounds
		this.removeInactiveBounds();
		
		// Update bounds
		for(BoundMonitor monitor: monitors) { monitor.updateBounds(); }
		
		// Sort bounds list
		insertionSort();
		
		// Perform collision checks
		checkCollisions();
	}
	
	
	private void insertionSort() {		
		for(int i = 1; i < bounds.size(); i++) {
			// Get current bound 
			Bound cur = bounds.get(i);
			if(cur.getPolygon().removalMarked()) continue;
			
			// Find index where the left bound is
			int index = i;
			while(true) {
				if(index == 0) break;
				
				Bound left = bounds.get(index - 1);
				if(left.getPolygon().removalMarked() || left.getValue() > cur.getValue()) index--;	
				else break;
			}
			
			// Move all bounds between designated indices right, and insert cur into the leftmost index
			for(int j = i; j > index; j --) {
				bounds.set(j, bounds.get(j - 1));
			}
			bounds.set(index, cur);		
		}
	}
	
	private void checkCollisions() {
		ArrayList<Bound> activeBounds = new ArrayList<>();
		
		for(Bound bound: bounds) {
			// Start Bounds
			if(bound.getEnd() == End.START) {
				// Check for collision
				for(Bound b: activeBounds) { 
					bound.getPolygon().checkForCollision(b.getPolygon()); 
				}
				activeBounds.add(bound);
			} 
			// End Bounds
			else {
				activeBounds.remove(bound.getBoundMonitor().getStartBound());
			}
		}
		
	}
}