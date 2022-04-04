package components.weapons.guns;

import objects.GameObject;
import objects.entities.projectiles.LightBullet;
import objects.entities.projectiles.MediumBullet;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class Shotgun extends Gun
{
	public Shotgun(GameObject owner)
	{
		super(owner);
		
		this.w = 15;
		this.h = 6;
		
		useTimer = 15; //20
		baseRecoil = 3; // 2
		maxRecoil = 70;
		recoilRecovery = 8;
		recoilThetaMult = 40;
		
		this.sprite = ImageManager.getImageCopy("shotgun");
		
		barrelX = this.w * 0.95f;
		barrelY = -this.w * 0.7f;
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
		for(int i = 0; i < 8; i++)
		{
			new LightBullet(owner, InputManager.getAngleToMouse(owner), currentRecoil).build();
		}
		
		super.fire();
	}
}
