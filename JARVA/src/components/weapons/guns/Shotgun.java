package components.weapons.guns;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Settings;
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
		
		barrelX = this.w * 0.95f;
		barrelY = -this.w * 0.12f;
	}
	
	@Override
	public void equip() {
		SoundManager.playSoundEffect("shotguncock", Settings.EffectsVolume);
	}

	@Override
	public void unequip() {}
	
	public void use()
	{
		super.use();
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
					.Recoil(currentRecoil);
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
