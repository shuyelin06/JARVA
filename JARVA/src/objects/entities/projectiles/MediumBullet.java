package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.entities.Projectile;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class MediumBullet extends Bullet
{
	
	public MediumBullet(GameObject source, float angle, float recoil) {
		super(source, Polygon.rectangle(2f, 1f), angle, recoil);
		
		this.setSprite(ImageManager.getImageCopy("mediumBullet", 2, 1));
		this.baseSpeed = 8;
		
		this.damageMultiplier = 20f;	
		this.knockback = 100f;
		this.pierce = 3;
		
		init(angle);
	}

	public void projectileUpdate() 
	{
		this.x += velocity.x;
		this.y += velocity.y;
	}

	public void objectDraw(Graphics g) 
	{
		
	}

}
