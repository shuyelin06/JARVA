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
		super(Polygon.triangle(1.8f, 50f).rotate((float) Math.PI), origin);
		
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
		this.rotate((float) this.theta + (float) Math.PI / 2f);
	}

	

	@Override
	public void projectileUpdate() {
		this.addXVelocity((float) Math.cos(theta) * 2.5f);
		this.addYVelocity((float) Math.sin(theta) * 2.5f);
		this.knockback = 60f;
		timer++;
		
		// if this is a primary source thorn, it will home for a bit and then split in 3
		if (origin instanceof Thorn) {
			if (this.getXVelocity() == 0) {
				this.setXVelocity((float) Math.cos(theta) * baseSpeed);
				this.setYVelocity((float) Math.sin(theta) * baseSpeed);
			}
		} else {
			if (timer < maxTimer) {
				theta = Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX());
				this.setXVelocity((float) Math.cos(theta) * baseSpeed);
				this.setYVelocity((float) Math.sin(theta) * baseSpeed);
			} else if (timer == maxTimer) {
				new Thorn(this, null)
					.setMaxTimer(500)
					.setBaseSpeed(this.baseSpeed * 3)
					.setAngle((float) (theta + (Math.PI / 6)))
					.setPierce(1)
					.setKnockback(0)
					.setDamageMultiplier(1)
					.build();
				
				new Thorn(this, null)
					.setMaxTimer(500)
					.setBaseSpeed(this.baseSpeed * 3)
					.setAngle((float) (theta))
					.setPierce(1)
					.setKnockback(0)
					.setDamageMultiplier(1)
					.build();
				
				new Thorn(this, null)
					.setMaxTimer(500)
					.setBaseSpeed(this.baseSpeed * 3)
					.setAngle((float) (theta - (Math.PI / 6)))
					.setPierce(1)
					.setKnockback(0)
					.setDamageMultiplier(1)
					.build();
				
				//remove primary projectile
				this.remove();
			}
		}
		
		sprite.setCenterOfRotation(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
		sprite.rotate((float) Math.toDegrees(theta));
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