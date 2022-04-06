package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class LightBullet extends Bullet
{
	public LightBullet(GameObject source, float angle, float recoil)
	{
		super(source, Polygon.rectangle(1f, 1f), angle, recoil);
		
		this.setSprite(ImageManager.getImageCopy("lightBullet", 1, 1));
		
		this.baseSpeed = 6;
		
		this.damageMultiplier = 5f;
		
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
