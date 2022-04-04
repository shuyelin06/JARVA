package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import engine.Settings;
import objects.GameObject;
import objects.entities.Projectile;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class Bullet extends Projectile
{
	protected float baseSpeed;
	
	public Bullet(GameObject source, float recoil, float offsetX, float offsetY) {
		super(Polygon.rectangle(2f, 2f), source);
		
		this.setSprite(ImageManager.getImageCopy("test", 2, 2));
		this.baseSpeed = 5;
		
		this.x = source.getX(); //+ offsetX;
		this.y = source.getY(); //+ offsetY;
		
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
	
	public void setSpeed(float speed)
	{
		this.velocity.x =  speed * (float) Math.cos(Math.toRadians(InputManager.getAngleToMouse(source)));
		this.velocity.y =  speed * (float) Math.sin(Math.toRadians(InputManager.getAngleToMouse(source)));
	}
}
