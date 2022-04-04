package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.entities.Projectile;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class MediumBullet extends Bullet
{
	
	public MediumBullet(GameObject source, float recoil, float offsetX, float offsetY) {
		super(source, recoil, offsetX, offsetY);
		
		this.setSprite(ImageManager.getImageCopy("test", 2, 2));
		this.baseSpeed = 8;
		setSpeed(this.baseSpeed);
		
		this.damageMultiplier = 10f;
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
