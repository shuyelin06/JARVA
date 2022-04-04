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
	
	public Bullet(GameObject source, Polygon hitbox, float angle, float recoil) {
		super(hitbox, source);
		
		this.setSprite(ImageManager.getImageCopy("test", 2, 2));
		
		this.hitbox.rotate((float)Math.toRadians(angle));

		this.baseSpeed = 5;
		
		this.x = source.getX(); //+ offsetX;
		this.y = source.getY(); //+ offsetY;
		
		this.angle = angle;
		
		this.velocity.x =  baseSpeed * (float) Math.cos(Math.toRadians(angle));
		this.velocity.y =  baseSpeed * (float) Math.sin(Math.toRadians(angle));
		
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
	
	public void init(float a) //have to call these methods afterwards since the sprite and speed gets overwritten
	{
		sprite.setCenterOfRotation(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
		sprite.rotate(a);
		
		setSpeed(this.baseSpeed);
	}
	
	public void draw(Graphics g)
	{
//		sprite.rotate(angle);
//		sprite.draw(x - 4, y - 1);
//		
//		hitbox.draw(g, x, y);
		
		sprite.draw(x - sprite.getWidth() * 0.5f, y - sprite.getHeight() * 0.5f);
		
		hitbox.draw(g, x, y);
	}
	
	public void setSpeed(float speed)
	{
		this.velocity.x =  speed * (float) Math.cos(Math.toRadians(angle));
		this.velocity.y =  speed * (float) Math.sin(Math.toRadians(angle));
	}
}
