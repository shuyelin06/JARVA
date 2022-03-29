package maps;

import org.newdawn.slick.Graphics;

import objects.entities.Unit;
import objects.geometry.Polygon;
import objects.geometry.Vector;

public class Border extends Polygon {
	
	@Override
	public float getCenterX() { return 0f; }
	@Override
	public float getCenterY() { return 0f; }
		
	
	public Border() {
		super( Polygon.rectangleEdges(1000f, 1000f) ); 
	}

	
	
	public void draw(Graphics g) {
	}
}
