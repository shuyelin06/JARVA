package components.weapons.guns;

import engine.Settings;
import objects.GameObject;
import objects.entities.Unit;
import objects.entities.projectiles.Bullet;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;
import ui.sound.SoundManager;

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
		barrelY = -this.w * 0.1f;
	}

	@Override
	public void equip() { 
		SoundManager.playSoundEffect("snipercock", Settings.EffectsVolume);
		Settings.Scale *= 0.75f; 
	}

	@Override
	public void unequip() { Settings.Scale *= 1 / 0.75f; }
	
	public void use()
	{
		super.use();
	}
	
	public void fire()
	{	
		((Bullet) new Bullet(owner, 4, 1)
				.build())
				.Style("heavy")
				.BaseSpeed(350f)
				.Angle(InputManager.getAngleToMouse(owner))
				.Damage(50f)
				.Knockback(200f)
				.Pierce(5)
				.Init()
				.Recoil(currentRecoil);
		SoundManager.playSoundEffect("snipershot", Settings.EffectsVolume);
		
		super.fire();
	}
}
