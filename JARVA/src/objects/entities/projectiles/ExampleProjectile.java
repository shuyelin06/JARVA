package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.entities.Player;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;

public class ExampleProjectile extends Projectile {
	
	private float baseSpeed = 5f;
	private double theta;
	
	private Unit origin;
	private GameObject target;
	
	private int timer;
	
	public ExampleProjectile(Unit u, GameObject target) {
		super(Polygon.rectangle(1, 1), u);
		
		
		this.origin = u;
		this.target = target;
		
		theta = Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX());
		this.setXVelocity((float) Math.cos(theta) * baseSpeed);
		this.setYVelocity((float) Math.cos(theta) * baseSpeed);
	}

	

	@Override
	public void projectileUpdate() {
		
		
	}

	@Override
	public void objectDraw(Graphics g) {
		
	}
	
}