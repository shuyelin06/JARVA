package objects.entities;

import objects.GameObject;
import objects.geometry.Polygon;

public class Entity extends GameObject {
	public Entity(float x, float y, Polygon polygon) {
		super(x, y, polygon);
	}
}