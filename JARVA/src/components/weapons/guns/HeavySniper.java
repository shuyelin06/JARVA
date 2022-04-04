package components.weapons.guns;

import objects.GameObject;
import objects.entities.projectiles.HeavyBullet;
import objects.entities.projectiles.MediumBullet;
import ui.display.images.ImageManager;

public class HeavySniper extends Gun
{
	public HeavySniper(GameObject owner) 
	{
		super(owner);
		
		this.w = 27;
		this.h = 6;
		
		useTimer = 45; 
		baseRecoil = 30;
		maxRecoil = 50;
		recoilRecovery = 2;
		recoilThetaMult = 3;
		
		this.sprite = ImageManager.getImageCopy("50cal");
		
		barrelX = this.w * 0.95f;
		barrelY = -this.w * 0.2f;
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
		new HeavyBullet(owner, currentRecoil, barrelX, barrelY).build();
		
		super.fire();
	}
}
