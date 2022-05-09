package components.weapons.guns;

import engine.Settings;
import objects.GameObject;
import objects.entities.Unit;
import objects.entities.projectiles.Bullet;
import ui.display.images.ImageManager;
import ui.input.InputManager;
import ui.sound.SoundManager;

public class SubmachineGun extends Gun {

	public SubmachineGun(Unit owner) {
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
		
		barrelX = this.w * 0.8f;
		barrelY = -this.w * 0.5f;
		
		heldUse = true;
	}

	@Override
	public void equip() {
		this.lastUsed = useTimer;
		
		SoundManager.playSoundEffect("smgcock", Settings.EffectsVolume);
	}

	@Override
	public void unequip() {}
	
	public void use()
	{
		super.use();
	}
	
	public void fire()
	{
		((Bullet) new Bullet(owner, 1, 1)
				.build())
				.Style("light")
				.BaseSpeed(175f)
				.Angle(InputManager.getAngleToMouse(owner))
				.Damage(8f)
				.Knockback(50f)
				.Pierce(1)
				.Init()
				.Recoil(currentRecoil);
		SoundManager.playSoundEffect("smgshot", Settings.EffectsVolume);
		
		super.fire();
	}
}
