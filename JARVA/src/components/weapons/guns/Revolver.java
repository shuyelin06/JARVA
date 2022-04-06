package components.weapons.guns;

import objects.GameObject;
import objects.entities.projectiles.Bullet;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;
import ui.input.InputManager;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import components.weapons.Weapon;

public class Revolver extends Gun
{	
	private int spinAnimTimer;
	
	public Revolver(GameObject owner) 
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
		barrelY = -this.w * 0.7f;
	}

	@Override
	public void equip() { spinAnimTimer = 0; }

	@Override
	public void unequip() { spinAnimTimer = 0; }
	
	public void use()
	{
		super.use();
	}
	
	public void fire()
	{
		new Bullet(owner, 2, 1, "medium", 10, InputManager.getAngleToMouse(owner), currentRecoil, 20f, 2f, 100f).build();
		
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
