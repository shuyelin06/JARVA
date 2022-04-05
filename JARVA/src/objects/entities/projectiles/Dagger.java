package objects.entities.projectiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Dagger extends Projectile{
	private float baseSpeed = 190f;
	private float theta;
	
	private int timer;
	private int delay = 40;
	
	private GameObject target;
	
	private float spawnRadius = 70f;
	
	public Dagger(GameObject source, GameObject t) {
		super(Polygon.rectangle(1f, 10f), source);
		
		target = t;
		
		theta = (float)Math.atan2(target.getVelocityVector().y, target.getVelocityVector().x);
		
		timer = 0;
		
		
		this.sprite = ImageManager.getImageCopy("placeholder", 1, 10);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
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
			g.setColor(Color.red);
			g.drawLine(this.getX(),this.getY(), this.getX() + (float)Math.cos(theta)*2*spawnRadius, this.getY()+ (float)Math.sin(theta)*2*spawnRadius);
		}
	}

}
