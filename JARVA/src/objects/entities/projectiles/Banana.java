package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import components.conditions.Condition;
import engine.Utility;
import objects.GameObject;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Banana extends Projectile {
	private static float baseSpeed = 15f;
	private float theta;
	
	public Banana(GameObject origin, GameObject target) {
		super(Polygon.rectangle(4f, 4f), origin);
		
		this.omega = (float) Math.PI;
		this.knockback = 30f;
		
		this.sprite = ImageManager.getImageCopy("placeholder", 4, 4);
		
		this.setX(origin.getX());
		this.setY(origin.getY());
		
		this.setTeam(origin.getTeam());
		
		this.sprite = ImageManager.getImageCopy("thorn", 3, 3);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
		if (target != null) {
			theta = (float) Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX());

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
