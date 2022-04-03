package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.entities.Projectile;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class Bullet extends Projectile
{
	protected float baseSpeed;
	
	public Bullet(GameObject source, float recoil) {
		super(Polygon.rectangle(2f, 2f), source);
		
		this.setSprite(ImageManager.getImageCopy("test", 2, 2));
		this.baseSpeed = 5;
		
		this.x = source.getX();
		this.y = source.getY();
		
		this.velocity.x =  baseSpeed * (float) Math.cos(Math.toRadians(InputManager.getAngleToMouse(source)));
		this.velocity.y =  baseSpeed * (float) Math.sin(Math.toRadians(InputManager.getAngleToMouse(source)));
		
		velocity.rotate(recoil * (float) (Math.random() - 0.5f) * 3.1415f / 180f);
		
		this.damageMultiplier = 1f;
	}

	@Override
	public void projectileUpdate() 
	{
		
	}

	@Override
	public void objectDraw(Graphics g) 
	{
		
	}
	
}
