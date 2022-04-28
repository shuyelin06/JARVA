package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import components.conditions.Condition;
import components.conditions.Confusion;
import engine.states.Game;
import objects.GameObject;
import objects.entities.Player;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Thorn extends Projectile {
	
	private float baseSpeed = 10f;
	private double theta;
	
	private GameObject origin;
	private GameObject target;
	
	private int timer;
	private int maxTimer;
	
	public Thorn(GameObject origin, GameObject target) {
		super(Polygon.rectangle(3f, 3f), origin);
		
		this.origin = origin;
		this.target = target;
		
		this.setX(origin.getX());
		this.setY(origin.getY());
		
		this.setTeam(origin.getTeam());
		
		this.sprite = ImageManager.getImageCopy("thorn", 3, 3);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
		if (this.target != null) {
			theta = Math.atan2(target.getY() - origin.getY(), target.getX() - origin.getX());

			this.setXVelocity((float) Math.cos(theta) * baseSpeed);
			this.setYVelocity((float) Math.sin(theta) * baseSpeed);
		}
		
		
	}

	

	@Override
	public void projectileUpdate() {
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
		
		
	}

	@Override
	public void applyCondition(Unit u) {
		u.takeCondition(Condition.Type.Confusion, 0.5f);
	}
	
	@Override
	public void objectDraw(Graphics g) {
		
	}
	
	public Thorn setMaxTimer(int maxTimer) { this.maxTimer = maxTimer; return this; }
	public Thorn setAngle(float theta) { this.theta = theta; return this; }
	public Thorn setBaseSpeed(float baseSpeed) { this.baseSpeed = baseSpeed; return this; }
}