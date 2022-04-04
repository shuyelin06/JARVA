package maps;

import org.newdawn.slick.Graphics;

import objects.entities.Unit;
import objects.geometry.Polygon;
import objects.geometry.Vector;

public class Border extends Polygon {
	private Arena arena;
	
	@Override
	public float getCenterX() { return arena.getCenterX(); }
	@Override
	public float getCenterY() { return arena.getCenterY(); }
	
	public Arena getArena() { return arena; }
	
	public Border(Arena arena, Vector[] vertices) {
		super( vertices ); 
		this.arena = arena;
	}
}
