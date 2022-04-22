package objects.entities.projectiles;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import engine.Settings;
import engine.Utility;
import objects.GameObject;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;

public class Beam extends Projectile {
	
	private static float Beam_Height = 2.5f;
	private static float Beam_Length = 50f;
	
	private Unit source;
	
	public Beam(Unit source, float targetX, float targetY) {
		super( Polygon.rectangle(Beam_Length, Beam_Height), source );
		
		this.knockback = 0f;
		
		this.baseDamage = 0f;
		this.damageMultiplier = 0f;
		
		this.source = source;
		
		this.pierce = Integer.MAX_VALUE;
		
		changeTarget(targetX, targetY);
		
		this.build();
	}
	
	public void changeTarget(float targetX, float targetY) {
		final float TargetAngle = Utility.atan(targetY - source.getY(), targetX - source.getX());

		final float FurthestX = Beam_Length * Utility.cos(TargetAngle);
		final float FurthestY = Beam_Length * Utility.sin(TargetAngle);
		
		this.x = source.getX() + FurthestX / 2f;
		this.y = source.getY() + FurthestY / 2f;
	}
	
	public void projectileUpdate() {
		final float AngleToSource = Utility.atan( source.getY() - y, source.getX() - x );
		this.omega = (AngleToSource - angle) * Settings.Frames_Per_Second;
	}

	@Override
	public void draw(Graphics g) {
		
	}

	@Override
	public void objectDraw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}