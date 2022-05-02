package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import components.conditions.Condition;
import components.conditions.Confusion;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.entities.Player;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class ThornBall extends Projectile {
	
	private float theta;
	
	private GameObject origin;
	private GameObject target;
	
	private int timer;
	private int maxTimer;
	
	public ThornBall(GameObject origin, GameObject target) {
		super( Polygon.regularPolygon( 2.35f, 8 ), origin);
		
		this.setX(origin.getX());
		this.setY(origin.getY());
		
		this.setTeam(origin.getTeam());
		
		this.sprite = ImageManager.getImageCopy("thornball", 4, 4);
		
		this.pierce = 1; 
		this.knockback = 60f;
		this.damageMultiplier = 1;
		
		this.maxVelocity = 10f;
		
		this.origin = origin;
		this.target = target;
		
		this.omega = Utility.ConvertToRadians( 30 );
	}

	

	@Override
	public void projectileUpdate() {
		this.theta = Utility.atan(target.getY() - origin.getY(), target.getX() - origin.getX());
		this.addXVelocity((float) Utility.cos(theta) * 2f);
		this.addYVelocity((float) Utility.sin(theta) * 2f);
		
		timer++;
		if (timer >= maxTimer) {
			new Thorn( this, (float) (theta + (Math.PI / 6)) )
				.setMaxVelocity(maxVelocity * 3)
				.build();
			
			new Thorn(this, theta)
				.setMaxVelocity(maxVelocity * 3)
				.build();
			
			new Thorn(this, (float) (theta - (Math.PI / 6)))
				.setMaxVelocity(maxVelocity * 3)
				.build();
			
			// Remove primary projectile
			this.remove();
		}	
		
	}

	@Override
	public void applyCondition(Unit u) {
		u.takeCondition(Condition.Type.Confusion, 0.5f);
	}
	
	@Override
	public void objectDraw(Graphics g) {}
	
	public ThornBall setMaxTimer(int maxTimer) { this.maxTimer = maxTimer; return this; }
	public ThornBall setAngle(float theta) { this.theta = theta; return this; }
}