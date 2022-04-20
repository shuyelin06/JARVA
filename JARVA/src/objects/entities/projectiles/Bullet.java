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
	protected int w; protected int h;
	
	public Bullet(GameObject source, int w, int h) {
		super(Polygon.rectangle(w, h), source);
		
		this.x = source.getX(); //+ offsetX;
		this.y = source.getY(); //+ offsetY;
		
		this.w = w;
		this.h = h;
	}
	
	public Bullet Style(String style)
	{
		switch(style)
		{
			case "heavy": 	this.setSprite(ImageManager.getImageCopy("heavyBullet", w, h)); break;
			case "medium": 	this.setSprite(ImageManager.getImageCopy("mediumBullet", w, h)); break;
			case "light": 	this.setSprite(ImageManager.getImageCopy("lightBullet", w, h)); break;
			default: this.setSprite(ImageManager.getImageCopy("test", 2, 2)); break;
		}
		return this; 
	}
	public Bullet BaseSpeed(float baseSpeed)	{ this.baseSpeed = baseSpeed; return this; }
	public Bullet Angle(float angle)	{	this.angle = angle;	 return this; }
	public Bullet Damage(float damage) {   this.damageMultiplier = damage;	return this;  }
	public Bullet Knockback(float knockback) { this.knockback = knockback;	 return this;  }
	public Bullet Pierce(int pierce) { this.pierce = pierce; 	 return this;  }
	public Bullet Init() 
	{
		hitbox.rotate((float)Math.toRadians(angle));
		sprite.setCenterOfRotation(sprite.getWidth() * 0.5f, sprite.getHeight() * 0.5f);
		sprite.rotate(angle);
		this.velocity.x =  baseSpeed * (float) Math.cos(Math.toRadians(angle));
		this.velocity.y =  baseSpeed * (float) Math.sin(Math.toRadians(angle));
		return this;  
	}
	public Bullet Recoil(float recoil)	{	velocity.rotate(recoil * (float) (Math.random() - 0.5f) * 3.1415f / 180f);	return this; }
	
	@Override
	public void projectileUpdate() {}
	@Override
	public void objectDraw(Graphics g) {}
	
	public void draw(Graphics g)
	{
		sprite.setFilter(Image.FILTER_NEAREST);
		sprite.draw(x - sprite.getWidth() * 0.5f, y - sprite.getHeight() * 0.5f);
		
		//hitbox.draw(g, x, y);
	}
}
