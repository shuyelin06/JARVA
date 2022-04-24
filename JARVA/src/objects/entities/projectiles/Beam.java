package objects.entities.projectiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import engine.Settings;
import engine.Utility;
import objects.GameObject;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import objects.geometry.Vector;

public class Beam extends Projectile {
	
	private static float Beam_Height = 2.5f;
	private static float Beam_Length = 50f;
	
	private Unit source;
	
	private float length;
	
	private float targetX;
	private float targetY;
	
	public Beam(Unit source, float targetX, float targetY) {
		super( Polygon.rectangle(Beam_Length, Beam_Height), source );
		
		this.length = Beam_Length;
		
		this.knockback = 15f;
		
		this.baseDamage = 0f;
		this.damageMultiplier = 0f;
		
		this.source = source;
		
		this.pierce = Integer.MAX_VALUE;
		
		changeTarget(targetX, targetY);
		
		this.build();
	}
	
	public void projectileUpdate() {}

	public void changeLength(float length) {
		this.length += length / 2f;
		
		final float Angle = Utility.atan( targetY - source.getY(), targetX - source.getX() );
		
		final float AddX = Utility.cos(-Angle) * length;
		final float AddY = Utility.sin(-Angle) * length;
		
		final Vector[] Vertices = hitbox.getVertices();
		
		Vertices[2].x += AddX;
		Vertices[2].y += AddY;
		
		Vertices[3].x += AddX;
		Vertices[3].y += AddY;
	}
	public void changeTarget(float targetX, float targetY) {
		// Setting Variables for Drawing
		this.targetX = targetX;
		this.targetY = targetY;
		
		// Change x/y position
		final float TargetAngle = Utility.atan(targetY - source.getY(), targetX - source.getX());
		
		this.x = source.getX() + Beam_Length * Utility.cos(TargetAngle) / 2f;
		this.y = source.getY() + Beam_Length * Utility.sin(TargetAngle) / 2f;
		
		// Update Angle
		final float AngleToSource = Utility.atan( source.getY() - y, source.getX() - x );
		
		float omega = AngleToSource - angle;
		
		angle += omega;
		this.hitbox.rotate(omega);
	}
	
	
	public void objectDraw(Graphics g) {}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		
		final float Angle = Utility.atan( targetY - source.getY(), targetX - source.getX() );
		g.setLineWidth(100f);
		g.drawLine( 
				source.getX(), source.getY(), 
				source.getX() + length * Utility.cos(Angle), 
				source.getY() + length * Utility.sin(Angle)
				);
	}

	
}