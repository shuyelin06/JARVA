package components.weapons.guns;

import objects.GameObject;
import objects.entities.Unit;
import objects.entities.projectiles.Bullet;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class RealRailgun extends Gun
{
	public RealRailgun(Unit owner) {
		super(owner);
		
		this.w = 16;
		this.h = 4;
		
		useTimer = 1; 
		baseRecoil = 1;
		maxRecoil = 3;
		recoilRecovery = 1f;
		recoilThetaMult = 0;
		recoilPosMult = 0.1f;
		
		this.sprite = ImageManager.getImageCopy("heavyRifle");
		
		barrelX = this.w * 0.95f;
		barrelY = -this.w * 0.2f;
		
		heldUse = true;
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
		//new Bullet(owner, 10, 1, "light", 20, InputManager.getAngleToMouse(owner), currentRecoil, 10f, 1f, 50f).build();
		((Bullet) new Bullet(owner, 10, 1)
		.build())
		.Style("light")
		.BaseSpeed(450f)
		.Angle(InputManager.getAngleToMouse(owner))
		.Damage(50)
		.Knockback(0)
		.Pierce(10)
		.Init()
		.Recoil(currentRecoil);
		
		super.fire();
	}
}
