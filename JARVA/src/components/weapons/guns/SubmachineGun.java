package components.weapons.guns;

import engine.Settings;
import engine.Utility;
import objects.GameObject;
import objects.entities.Unit;
import objects.entities.other.BulletCasing;
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
		baseRecoil = 5;
		maxRecoil = 10;
		recoilRecovery = 1;
		recoilThetaMult = 1;
		recoilPosMult = 0.1f;
		
		this.sprite = ImageManager.getImageCopy("uzi");
		this.relativeBarrelX = w - w * 0.235f;
		this.relativeBarrelY = h * 0.225f;
		
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
				.Damage(10f)
				.Knockback(50f)
				.Pierce(1)
				.Init()
				.Recoil(currentRecoil)
				.setX(barrelX)
				.setY(barrelY);
		final float CasingAngle = (float) ( Utility.ConvertToRadians(InputManager.getAngleToMouse(owner)) + Math.PI);
		new BulletCasing(75f, CasingAngle)
			.setX(pivotX)
			.setY(pivotY)
			.build();
		SoundManager.playSoundEffect("smgshot", Settings.EffectsVolume);
		
		super.fire();
	}
}
