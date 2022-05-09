package components.weapons.guns;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import engine.Settings;
import engine.Utility;
import objects.GameObject;
import objects.entities.Unit;
import objects.entities.projectiles.Bullet;
import objects.geometry.Polygon;
import ui.display.animation.Animation;
import ui.display.images.ImageManager;
import ui.input.InputManager;
import ui.sound.SoundManager;

public class Shotgun extends Gun {
	
	public Shotgun(Unit owner)
	{
		super(owner);
		
		this.w = 20;
		this.h = 3;
		
		useTimer = 30; //30
		baseRecoil = 3; // 3
		maxRecoil = 70;
		recoilRecovery = 8;
		recoilThetaMult = 10; //400
		
		animation = new Animation("shotgunPump", 96, 16);
		animFrame = 0;
		animating = false;
		
		this.sprite = ImageManager.getImageCopy("shotgun");
		this.barrelWidth = sprite.getWidth() / Settings.Scale;
		this.barrelHeight = sprite.getHeight() / Settings.Scale;
		
		barrelWidth = this.w * 0.95f;
		barrelHeight = -this.w * 0.1f;
	}
	
	@Override
	public void equip() {
		this.lastUsed = useTimer;
		
		SoundManager.playSoundEffect("shotguncock", Settings.EffectsVolume);
	}

	@Override
	public void unequip() {}
	
	public void use()
	{
		super.use();
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		g.setColor(Color.white);
		

		this.barrelWidth = w - w * 0.2f;
		this.barrelHeight = h * 0.85f;
		
		final float a = Utility.ConvertToRadians(theta);
		final float t = Utility.atan(barrelHeight, barrelWidth);
		
		final float m = (float) Math.sqrt(barrelWidth * barrelWidth + barrelHeight * barrelHeight );
		
//		
//		g.fill(new Circle(
//				pivotX + m * (float) Math.cos(a + t), 
//				pivotY + m * (float) Math.sin(a + t),
//				0.1f));
		
		final float n = 1f;
		float shift = (float) Math.PI / 2f;
		if( theta % 360 > 180 ) {
			shift = -shift;
		}
		g.fill(new Circle(
				pivotX + barrelWidth * (float) Math.cos(a) - n * (float) Math.cos(a + Math.PI / 2), 
				pivotY + barrelWidth * (float) Math.sin(a) - n * (float) Math.sin(a + Math.PI / 2),
				0.1f));
	}
	
	public void fire()
	{
		for(int i = 0; i < 12; i++)
		{
			((Bullet) new Bullet(owner, 1, 1)
					.build())
					.Style("light")
					.BaseSpeed(150f)
					.Angle(InputManager.getAngleToMouse(owner) + (i - 6 - (float) Math.random()) * 3)
					.Damage(4f)
					.Knockback(50f)
					.Pierce(1)
					.Init()
					.Recoil(currentRecoil)
					.offsetPosition(
							barrelWidth * Utility.cos( Utility.ConvertToRadians(theta) + barrelHeight * Utility.cos(Utility.ConvertToRadians(theta + 180) )), 
							barrelWidth * Utility.sin( Utility.ConvertToRadians(theta) + barrelHeight * Utility.sin( Utility.ConvertToRadians(theta + 180) ))
							);
		}
		SoundManager.playSoundEffect("shotgunshot", Settings.EffectsVolume);
		
		super.fire();
		
		animating = true;
	}
	
	public void update()
	{
		super.update();
		
		if(animation != null)
		{
			if(animating && animTick % 2 == 0) animFrame++;
			if(animFrame > animation.animationSize() - 1)
			{
				 animFrame %= animation.animationSize(); 
				 animating = false;
			}
		}
	}
}
