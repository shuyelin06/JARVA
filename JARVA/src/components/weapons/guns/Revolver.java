package components.weapons.guns;

import objects.GameObject;
import objects.entities.Unit;
import objects.entities.projectiles.Bullet;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;
import ui.sound.SoundManager;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import components.weapons.Weapon;
import engine.Settings;

public class Revolver extends Gun {	
	private int spinAnimTimer;
	
	public Revolver(Unit owner) 
	{
		super(owner);
		
		this.w = 6;
		this.h = 4;
		spinAnimTimer = 0;
		
		useTimer = 15; //20
		baseRecoil = 3; // 2
		maxRecoil = 70;
		recoilRecovery = 8;
		recoilThetaMult = 40;
		
		this.sprite = ImageManager.getImageCopy("revolver");
		
		barrelX = this.w * 0.95f;
		barrelY = -this.w * 0.5f;
	}

	@Override
	public void equip() { 
		SoundManager.playSoundEffect("revolvercock", Settings.EffectsVolume);
		spinAnimTimer = 0; 
	}

	@Override
	public void unequip() { spinAnimTimer = 0; }
	
	public void use()
	{
		super.use();
	}
	
	public void fire()
	{
		((Bullet) new Bullet(owner, 2, 1)
				.build())
				.Style("medium")
				.BaseSpeed(200f)
				.Angle(InputManager.getAngleToMouse(owner))
				.Damage(20f)
				.Knockback(100f)
				.Pierce(2)
				.Init()
				.Recoil(currentRecoil);
		SoundManager.playSoundEffect("revolvershot", Settings.EffectsVolume);
		
		super.fire();
	}
	
	public void update()
	{
		super.update();
		
		spinAnimTimer++;
		
		if(spinAnimTimer < 60)
		{
			theta += 6;
		}
	}
}
