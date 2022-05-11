package objects.entities.other;

import org.newdawn.slick.Graphics;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.geometry.Polygon;
import ui.display.background.BackgroundObject;
import ui.display.background.objects.Casing;
import ui.display.images.ImageManager;

public class BulletCasing extends GameObject {
	
	final static float Gravity = 0.05f;
	final static float BulletSpin = 35;
	
	protected float timer;
	
	protected float zVelocity;
	protected float z; // Height of the bullet
	
	public BulletCasing(float baseVelocity, float angle) {
		super(Polygon.rectangle(0, 0));
		
		this.collidable = false;
		this.friction = false;
		
		this.sprite = ImageManager.getImageCopy("casing", 1, 1);
		
		this.omega = BulletSpin + (float) (Math.random() * 15f - 7.5f);
		
		this.timer = 0.85f;
		this.z = 0f;
		
		final float Angle = angle + (float) (Math.random() * Math.PI / 6 - Math.PI / 12);
		this.zVelocity = 0.35f + (float) (Math.random() * 0.07f - 0.035f);
		this.velocity.x = baseVelocity * Utility.cos(Angle);
		this.velocity.y = baseVelocity * Utility.sin(Angle);
		
		if(velocity.y > 0) {
			zVelocity *= 4.25f;
		}
	}

	public GameObject setZ(float newZ) { this.z = newZ; return this; }
	
	@Override
	public void updatePhysics() {
		this.timer -= Game.TicksPerFrame();
		
		// Update Positions
		this.sprite.setCenterOfRotation(sprite.getWidth() / 2, sprite.getHeight() / 2);
		this.sprite.rotate( omega );
		
		this.x += velocity.x / Settings.Frames_Per_Second;
		this.y += velocity.y / Settings.Frames_Per_Second;
		
		this.zVelocity -= Gravity;
		this.z += zVelocity;
		
		if(timer < 0) {
			this.remove = true;
			
			Game.DisplayManager.getBackground().addBulletCasing(
					new Casing(x, y - z, this.sprite)
					);
		}
	}

	@Override
	protected void drawSprite(Graphics g) { sprite.drawCentered(x, y - z); }
	
	@Override
	public void objectDraw(Graphics g) { return; }

	@Override
	protected void objectCollision(GameObject o) { return; }

	@Override
	public void objectUpdate() { return; }
}