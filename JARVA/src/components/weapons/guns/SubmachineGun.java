package components.weapons.guns;

import objects.GameObject;
import objects.entities.projectiles.Bullet;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class SubmachineGun extends Gun
{

	public SubmachineGun(GameObject owner) {
		super(owner);
		
		this.w = 8;
		this.h = 4;
		
		useTimer = 5; 
		baseRecoil = 10;
		maxRecoil = 40;
		recoilRecovery = 1;
		recoilThetaMult = 1;
		recoilPosMult = 0.1f;
		
		this.sprite = ImageManager.getImageCopy("uzi");
		
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
		new Bullet(owner, 1, 1, "light", 7, InputManager.getAngleToMouse(owner), currentRecoil, 10f, 1f, 50f).build();
		
		super.fire();
	}
}
