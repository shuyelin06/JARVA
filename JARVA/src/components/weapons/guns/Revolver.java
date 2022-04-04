package components.weapons.guns;

import objects.GameObject;
import objects.entities.projectiles.MediumBullet;
import ui.display.images.ImageManager;
import ui.input.InputManager;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import components.weapons.Weapon;

public class Revolver extends Gun
{	
	
	public Revolver(GameObject owner) 
	{
		super(owner);
		
		this.w = 8;
		this.h = 5;
		
		useTimer = 15; //20
		baseRecoil = 3; // 2
		maxRecoil = 70;
		recoilRecovery = 8;
		recoilThetaMult = 40;
		
		this.sprite = ImageManager.getImageCopy("revolver");
		
		barrelX = this.w * 0.95f;
		barrelY = -this.w * 0.7f;
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
		new MediumBullet(owner, currentRecoil, barrelX, barrelY).build();
		
		super.fire();
	}
}
