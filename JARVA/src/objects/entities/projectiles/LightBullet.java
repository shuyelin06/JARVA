package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class LightBullet extends Bullet
{
	public LightBullet(GameObject source, float angle, float recoil)
	{
		super(source, Polygon.rectangle(2f, 1f), angle, recoil);
		
		this.setSprite(ImageManager.getImageCopy("test", 2, 1));
		
		this.baseSpeed = 5;
		
		this.damageMultiplier = 3f;
		
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
