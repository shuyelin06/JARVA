package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import components.conditions.Condition;
import components.conditions.Confusion;
import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.entities.Player;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Thorn extends Projectile {
	private float theta;
	
	private GameObject origin;
	
	public Thorn(GameObject origin, float theta) {
		super(Polygon.triangle(1.8f, 50f), origin);
		
		this.theta = theta;
		this.origin = origin;
		
		this.setX(origin.getX());
		this.setY(origin.getY());
		
		this.setTeam(origin.getTeam());
		
		this.sprite = ImageManager.getImageCopy("thorn", 2, 2);

		this.pierce = 1;
		this.knockback = 0;
		this.damageMultiplier = 1;
		
		// Rotate sprite correctly
		this.rotate((float) this.theta + 3 * (float) Math.PI / 2f);
	}

	

	@Override
	public void projectileUpdate() {
		this.addXVelocity((float) Math.cos(theta) * 2.5f);
		this.addYVelocity((float) Math.sin(theta) * 2.5f);
	}

	@Override
	public void applyCondition(Unit u) {
		u.takeCondition(Condition.Type.Confusion, 0.15f);
	}
	
	@Override
	public void objectDraw(Graphics g) {}

	public Thorn setAngle(float theta) { this.theta = theta; return this; }
//	public Thorn setBaseSpeed(float baseSpeed) { this.baseSpeed = baseSpeed; return this; }
}