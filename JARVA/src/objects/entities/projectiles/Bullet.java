package objects.entities.projectiles;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Settings;
import objects.GameObject;
import objects.entities.Projectile;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class Bullet extends Projectile
{
	protected float baseSpeed;
	
	public Bullet(GameObject source, int w, int h, String style, float baseSpeed, float angle, float recoil, float damage, float pierce, float knockback) {
		super(Polygon.rectangle(w, h), source);
		
		switch(style)
		{
		case "heavy": 	this.setSprite(ImageManager.getImageCopy("heavyBullet", w, h)); break;
		case "medium": 	this.setSprite(ImageManager.getImageCopy("mediumBullet", w, h)); break;
		case "light": 	this.setSprite(ImageManager.getImageCopy("lightBullet", w, h)); break;
		
		default: this.setSprite(ImageManager.getImageCopy("test", 2, 2)); break;
		}
		
		this.hitbox.rotate((float)Math.toRadians(angle));

		this.baseSpeed = baseSpeed;
		
		this.x = source.getX(); //+ offsetX;
		this.y = source.getY(); //+ offsetY;
		
		this.angle = angle;
		
		this.velocity.x =  baseSpeed * (float) Math.cos(Math.toRadians(angle));
		this.velocity.y =  baseSpeed * (float) Math.sin(Math.toRadians(angle));
		
		velocity.rotate(recoil * (float) (Math.random() - 0.5f) * 3.1415f / 180f);
		
		this.damageMultiplier = damage;
		
		init(angle);
	}

	@Override
	public void projectileUpdate() 
	{
		this.x += velocity.x;
		this.y += velocity.y;
	}

	@Override
	public void objectDraw(Graphics g) 
	{
		
	}
	
	public void init(float a) //have to call these methods afterwards since the sprite and speed gets overwritten
	{
		sprite.setCenterOfRotation(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
		sprite.rotate(a);
	}
	
	public void draw(Graphics g)
	{
//		sprite.rotate(angle);
//		sprite.draw(x - 4, y - 1);
//		
//		hitbox.draw(g, x, y);
		sprite.setFilter(Image.FILTER_NEAREST);
		sprite.draw(x - sprite.getWidth() * 0.5f, y - sprite.getHeight() * 0.5f);
		
		//hitbox.draw(g, x, y);
	}
	
	public void setSpeed(float speed)
	{
		this.velocity.x =  speed * (float) Math.cos(Math.toRadians(angle));
		this.velocity.y =  speed * (float) Math.sin(Math.toRadians(angle));
	}
}
