package objects.entities.units;

import objects.entities.Projectile;
import objects.geometry.Polygon;

public class Tumbleweed extends Unit {
	
	public Tumbleweed() {
		super(Polygon.rectangle(5f, 5f));
	}
	
	@Override
	public void entityUpdate() {
		
	}
}