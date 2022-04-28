package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import components.conditions.Condition;
import objects.GameObject;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Banana extends Projectile {
	private static float baseSpeed = 30f;
	private float theta;
	
	public Banana(GameObject origin, GameObject target) {
		super(Polygon.rectangle(8f, 8f), origin);
		
		this.omega = (float) (2 * Math.PI);
		this.knockback = 30f;
		
		this.sprite = ImageManager.getImageCopy("banana", 8, 8);
		
		this.setX(origin.getX());
		this.setY(origin.getY());
		
		this.setTeam(origin.getTeam());
		
		this.sprite.setImageColor(1f, 1f, 1f);
		
		if (target != null) {
			float targetXAdjusted = target.getX() + (float) (Math.random()*30 - 15);
			float targetYAdjusted = target.getY() + (float) (Math.random()*30 - 15);
			
			theta = (float) Math.atan2(targetYAdjusted - origin.getY(), targetXAdjusted - origin.getX());

			this.setXVelocity((float) Math.cos(theta) * baseSpeed);
			this.setYVelocity((float) Math.sin(theta) * baseSpeed);
		}
		
	}

	

	@Override
	public void projectileUpdate() {}

	@Override
	public void objectDraw(Graphics g) {}
	
	@Override
	public void applyCondition(Unit u) {
		u.takeCondition(Condition.Type.Confusion, 2f);
	}
}
