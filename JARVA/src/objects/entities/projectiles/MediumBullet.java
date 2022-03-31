package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.entities.Projectile;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class MediumBullet extends Projectile
{
	private float baseSpeed;
	
	public MediumBullet(GameObject source) {
		super(Polygon.rectangle(2f, 2f), source);
		
		this.setSprite(ImageManager.getImageCopy("test", 2, 2));
		this.baseSpeed = 12;
		
		this.x = source.getX();
		this.y = source.getY();
		
		this.velocity.x =  baseSpeed * (float) Math.cos(Math.toRadians(source.getAngleTo(InputManager.getMapMouseX(), InputManager.getMapMouseY())));
		this.velocity.y =  baseSpeed * (float) Math.sin(Math.toRadians(source.getAngleTo(InputManager.getMapMouseX(), InputManager.getMapMouseY())));
		
		this.damageMultiplier = 10f;
	}

	public void projectileUpdate() 
	{
		this.x += velocity.x;
		this.y += velocity.y;
	}

	public void objectDraw(Graphics g) 
	{
		System.out.println("test");
	}

}
