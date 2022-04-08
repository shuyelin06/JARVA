package components.weapons.guns;

import engine.Settings;
import objects.GameObject;
import objects.entities.Unit;
import objects.entities.projectiles.Bullet;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;

public class HeavySniper extends Gun {
	public HeavySniper(Unit owner) 
	{
		super(owner);
		
		this.w = 32;
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
	public void equip() { Settings.Scale *= 0.5f; }

	@Override
	public void unequip() { Settings.Scale *= 2f; }
	
	public void use()
	{
		super.use();
	}
	
	public void fire()
	{	
		((Bullet) new Bullet(owner, 4, 1)
				.build())
				.Style("heavy")
				.BaseSpeed(18f)
				.Angle(InputManager.getAngleToMouse(owner))
				.Damage(30f)
				.Knockback(200f)
				.Pierce(5)
				.Init()
				.Recoil(currentRecoil);
		
		super.fire();
	}
}
