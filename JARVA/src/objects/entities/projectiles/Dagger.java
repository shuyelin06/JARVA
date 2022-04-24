package objects.entities.projectiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import components.conditions.Condition;
import components.conditions.Poison;
import objects.GameObject;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Dagger extends Projectile{
	private float baseSpeed = 195f;
	private float theta;
	
	private int timer;
	private int delay = 40;
	
	private GameObject target;
	
	private float spawnRadius = 70f;
	
	public Dagger(GameObject source, GameObject t, float offset, boolean absoluteAngle) {
		super(Polygon.rectangle(2f, 6f), source);
		
		target = t;
		
		if(absoluteAngle) {
			theta = offset;
		}else {
			theta = offset + (float)Math.atan2(target.getVelocityVector().y, target.getVelocityVector().x);

		}
		
		timer = 0;
		
		
		this.sprite = ImageManager.getImageCopy("eagleFeather", 2, 6);
		this.sprite.setImageColor(1f, 1f, 1f);
		
		this.rotate(theta + (float) Math.PI / 2f);
//		this.hitbox.rotate(theta + (float) Math.PI / 2f);
//		sprite.rotate(theta + (float) Math.PI / 2f);
//		
		telegraph();
		
	}

	@Override
	protected void drawSprite(Graphics g) {
		sprite.drawCentered(x, y); 
	}
	
	@Override
	public void projectileUpdate() {
		if(timer > delay) {
			this.setXVelocity((float) Math.cos(theta) * baseSpeed);
			this.setYVelocity((float) Math.sin(theta) * baseSpeed);
		}
		
		timer++;
	}

	public void telegraph() {
		this.setX(target.getX() + (float)Math.cos(theta + Math.PI) * spawnRadius);
		this.setY(target.getY() + (float)Math.sin(theta + Math.PI) * spawnRadius);
	}

	@Override
	public void objectDraw(Graphics g) {
		if(timer < delay) {
			g.setColor(new Color(208, 49, 45, 75));
			g.setLineWidth(1.5f);
			g.drawLine(this.getX(),this.getY(), this.getX() + (float)Math.cos(theta)*2*spawnRadius, this.getY()+ (float)Math.sin(theta)*2*spawnRadius);
		}
	}
	
	@Override
	public void applyCondition(Unit u) {
		u.takeCondition(Condition.Type.Poison, 2);
	}
	

}
