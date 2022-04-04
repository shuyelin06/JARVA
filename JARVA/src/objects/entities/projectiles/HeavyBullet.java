package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import ui.display.images.ImageManager;

public class HeavyBullet extends Bullet
{
	public HeavyBullet(GameObject source, float recoil, float offsetX, float offsetY) {
		super(source, recoil, offsetX, offsetY);
		
		this.setSprite(ImageManager.getImageCopy("test", 2, 2));
		this.baseSpeed = 15;
		setSpeed(this.baseSpeed);
		
		this.damageMultiplier = 20f;
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
