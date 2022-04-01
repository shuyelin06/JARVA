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
		
		useTimer = 30;
		baseRecoil = 20;
		
		this.sprite = ImageManager.getImageCopy("revolver");
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
		super.fire();
		
		new MediumBullet(owner).build();
	}
}
