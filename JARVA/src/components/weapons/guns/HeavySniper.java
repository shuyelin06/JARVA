package components.weapons.guns;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.entities.Unit;
import objects.entities.other.BulletCasing;
import objects.entities.projectiles.Bullet;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;
import ui.sound.SoundManager;

public class HeavySniper extends Gun {
	private static Float SniperSlow = 0.75f;
	
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
		this.relativeBarrelX = w - w * 0.235f;
		this.relativeBarrelY = h * 0.135f;
	}

	@Override
	public void equip() {
		this.lastUsed = useTimer;
		
		SoundManager.playSoundEffect("snipercock", Settings.EffectsVolume);
		Game.Player.addVelocityMultiplier(SniperSlow);
		Settings.Scale *= 0.75f; 
	}

	@Override
	public void unequip() { 
		Game.Player.removeVelocityMultiplier(SniperSlow);
		Settings.Scale *= 1 / 0.75f; 
	}
	
	public void use()
	{
		super.use();
	}
	
	public void fire()
	{	
		new Bullet(owner, 4, 1)
				.Style("heavy")
				.BaseSpeed(350f)
				.Angle(InputManager.getAngleToMouse(owner))
				.Damage(50f)
				.Knockback(225f)
				.Pierce(5)
				.Init()
				.Recoil(currentRecoil)
				.setX(barrelX)
				.setY(barrelY)
				.build();
		final float CasingAngle = (float) ( Utility.ConvertToRadians(InputManager.getAngleToMouse(owner)) + Math.PI);
		new BulletCasing(100f, CasingAngle)
			.setX(pivotX)
			.setY(pivotY)
			.build();
		
		SoundManager.playSoundEffect("snipershot", Settings.EffectsVolume);
		
		super.fire();
	}
}
