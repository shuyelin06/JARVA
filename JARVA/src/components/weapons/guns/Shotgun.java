package components.weapons.guns;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;
import objects.entities.other.BulletCasing;
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
		this.relativeBarrelX = w - w * 0.225f;
		this.relativeBarrelY = h * 0.45f;
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
	
	public void fire()
	{
		for(int i = 0; i < 12; i++)
		{
			new Bullet(owner, 1, 1)
					.Style("light")
					.BaseSpeed(150f)
					.Angle(InputManager.getAngleToMouse(owner) + (i - 6 - (float) Math.random()) * 3)
					.Damage(3f)
					.Knockback(75f)
					.Pierce(1)
					.Init()
					.Recoil(currentRecoil)
					.setX(barrelX)
					.setY(barrelY)
					.build();
		}
		final float CasingAngle = (float) ( Utility.ConvertToRadians(InputManager.getAngleToMouse(owner)) + Math.PI);
		new BulletCasing(50f, CasingAngle)
			.setX(pivotX)
			.setY(pivotY)
			.build();
		
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
