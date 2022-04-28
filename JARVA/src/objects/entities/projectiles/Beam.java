package objects.entities.projectiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import components.conditions.Condition;
import engine.Settings;
import engine.Utility;
import objects.GameObject;
import objects.GameObject.ObjectType;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import objects.geometry.Vector;
import ui.display.images.ImageManager;

public class Beam extends Projectile {
	
	final public static float Default_Beam_Length = 50f;
	final public static float Default_Beam_Height = 1.5f;
	
	private Unit source;
	
	private int length;
	private int height;
	
	private float sourceX;
	private float sourceY;
	
	private float targetX;
	private float targetY;
	
	public Beam(Unit source, int length, int height) {
		super( Polygon.rectangle(length, height), source );
	
		this.length = length;
		this.height = height;
		
		this.source = source;
		this.sprite = ImageManager.getImageCopy("laser", length, height);
		
		this.length = length;
		
		this.knockback = 0f;
		
		this.damageMultiplier = 0.2f;
		
		this.pierce = Integer.MAX_VALUE;
	}
	
	/* --- Inherited Methods --- */
	public void objectDraw(Graphics g) {}
	
	public void projectileUpdate() {
		if( source.getPercentHealth() <= 0 ) this.remove = true;
		
		// Change x/y position based on source and target
		final float TargetAngle = Utility.atan(targetY - sourceY, targetX - sourceX);
		
		this.x = sourceX + length * Utility.cos(TargetAngle) / 2f;
		this.y = sourceY + length * Utility.sin(TargetAngle) / 2f;
		
		// Update Angle
		final float AngleToSource = Utility.atan( sourceY - y, sourceX - x );
		
		omega = (AngleToSource - angle) * Settings.Frames_Per_Second;
	}

	@Override
	public void objectCollision(GameObject o) {
		if( o.getType() == ObjectType.Unit && source.getTeam() != o.getTeam() ) {
			Unit unit = (Unit) o;
			
			unit.takeDamage(source.getBaseDamage() * damageMultiplier); // Damage
			unit.takeCondition(Condition.Type.Burn, 5f);
		}
	}
	
	/* --- Helper Methods--- */
	// Set the Beam Source Point
	public void setSource(float sourceX, float sourceY) {
		this.sourceX = sourceX;
		this.sourceY = sourceY;
	}
	// Set the Beam Target Point
	public void setTarget(float targetX, float targetY) {
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	// Change size of beam
	public void changeSize(float addLength, float addHeight) {
		this.length += addLength;
		this.height += addHeight;
		
		// Sprite Rescaling
		this.sprite = ImageManager.getImageCopy("laser", length, height);
		this.sprite.rotate( Utility.ConvertToDegrees(angle) );
		
		// Hitbox Rescaling
		Vector[] vertices = hitbox.getVertices();
		
		vertices[0].setXY(- length / 2,  - height / 2);
		vertices[1].setXY(- length / 2,  height / 2);
		vertices[2].setXY(length / 2, height / 2);
		vertices[3].setXY(length / 2,  - height / 2);
		vertices[4].setXY(- length / 2,  - height / 2);
		
		this.hitbox.rotate(angle);
	}
}