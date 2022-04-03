package objects.entities.projectiles;

import org.newdawn.slick.Graphics;

import objects.GameObject;
import objects.entities.Projectile;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class MediumBullet extends Bullet
{
	
	public MediumBullet(GameObject source, float recoil) {
		super(source, recoil);
		
		this.setSprite(ImageManager.getImageCopy("test", 2, 2));
		this.baseSpeed = 12;
		
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
