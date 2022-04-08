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
	public void equip() { Settings.setScale(Settings.BaseScale * 0.7f); }

	@Override
	public void unequip() { Settings.setScale(Settings.BaseScale); }
	
	public void use()
	{
		super.use();
	}
	
	public void fire()
	{
		new Bullet(owner, 4, 1, "heavy", 18, InputManager.getAngleToMouse(owner), currentRecoil, 30f, 5f, 200f).build();
		
		super.fire();
	}
}
