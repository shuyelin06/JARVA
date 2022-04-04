package components.weapons.guns;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import objects.GameObject;
import objects.entities.projectiles.LightBullet;
import objects.entities.projectiles.MediumBullet;
import ui.display.animation.Animation;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class Shotgun extends Gun
{
	public Shotgun(GameObject owner)
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
		barrelY = -this.w * 0.3f;
	}
	
	@Override
	public void equip() {}

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
			new LightBullet(owner, InputManager.getAngleToMouse(owner) + (i - 4 - (float) Math.random()) * 3, currentRecoil).build();
		}
		
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
