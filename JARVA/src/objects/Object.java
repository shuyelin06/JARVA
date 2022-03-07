package objects;

import objects.geometry.Polygon;

public abstract class Object {
	
	protected Polygon hitbox;
	
	protected float x, y;
	protected float width, height;
	
	public Object() {
		
	}
}