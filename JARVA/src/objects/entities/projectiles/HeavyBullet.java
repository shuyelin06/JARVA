package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class HeavyBullet extends Bullet
{
	public HeavyBullet(GameObject source, float angle, float recoil) {
		super(source, Polygon.rectangle(4f, 1f), angle, recoil);
		
		this.setSprite(ImageManager.getImageCopy("heavyBulletComplete", 4, 1));
		
//		this.hitbox.rotate((float)Math.toRadians(angle));
//		
//		sprite.setCenterOfRotation(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
//		sprite.rotate(angle);
		
		this.baseSpeed = 15f;
		this.damageMultiplier = 20f;
		
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
	
//	public void draw(Graphics g)
//	{
////		sprite.rotate(angle);
//		sprite.draw(x - sprite.getWidth() * 0.5f, y - sprite.getHeight() * 0.5f);
//		
//		hitbox.draw(g, x, y);
//	}
}
