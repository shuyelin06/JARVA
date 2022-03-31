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
	private double theta;
	
	private int timer;
	private int delay = 40;
	
	private GameObject target;
	
	private float spawnRadius = 70f;
	
	public Dagger(GameObject source, GameObject t) {
		super(Polygon.rectangle(1f, 1f), source);
		
		target = t;
		
		theta = Math.atan2(target.getVelocity().y, target.getVelocity().x);
		
		timer = 0;
		
		this.sprite = ImageManager.getImageCopy("placeholder", 1, 1);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
		telegraph();
		
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
