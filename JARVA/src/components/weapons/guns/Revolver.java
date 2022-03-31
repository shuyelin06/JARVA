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
		baseRecoil = 10;
		
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
	
	public void draw(Graphics g)
	{
		Image tempSprite = sprite;
		
		if(owner.isMirrored()) tempSprite = sprite.getFlippedCopy(false, true);
		
		if(rotationLocked)
		{
			tempSprite.draw(x + recoilX, y + recoilY, w, h);
		}
		else
		{
			g.rotate(pivotX, pivotY, theta);
			tempSprite.draw(x + recoilX, y + recoilY, w, h);
			g.rotate(pivotX, pivotY, -theta);
		}
	}
}
