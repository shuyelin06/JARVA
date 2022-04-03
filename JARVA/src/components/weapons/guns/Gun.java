package components.weapons.guns;

import objects.GameObject;
import ui.display.images.ImageManager;
import ui.input.InputManager;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import components.weapons.Weapon;

public class Gun extends Weapon
{	
	protected float baseRecoil;
	protected float currentRecoil;
	protected float maxRecoil;
	protected float recoilRecovery; //higher is slower
	
	protected float recoilX;
	protected float recoilY;
	protected float recoilTheta;
	
	protected Image muzzleFlash;
	protected float flashTimer;	
	protected float barrelX; //for the muzzle flash, and maybe for the bullet
	protected float barrelY;
	
	public Gun(GameObject owner) 
	{
		super(owner);
		
		lastUsed = 0;
		useTimer = 0;
		
		baseRecoil = 0;
		currentRecoil = 0;
		maxRecoil = 0;
		recoilRecovery = 1;
		
		sprite = ImageManager.getImageCopy("revolver");
		muzzleFlash = ImageManager.getImageCopy("muzzleFlash");
		flashTimer = 0;
		barrelX = 0;
		barrelY = 0;
	}

	@Override
	public void equip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unequip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
		if(lastUsed < 0) fire();
	}
	
	public void fire()
	{
		lastUsed += useTimer;
		
		float angleToMouse = (float)Math.toRadians(owner.getAngleTo(InputManager.getMapMouseX(), InputManager.getMapMouseY()));
		
		recoilX += baseRecoil * (float) -Math.cos(angleToMouse);
		recoilY += baseRecoil * (float) -Math.sin(angleToMouse);
		
		recoilTheta -= baseRecoil * 50f;
		currentRecoil += baseRecoil * recoilRecovery;
		
		flashTimer = 2;
	}
	
	public void update()
	{
		super.update();
		
		recoilX *= 0.9f;
		recoilY *= 0.9f;
		recoilTheta *= 0.92f;
		//cleaning up numbers
		if(Math.abs(recoilX) < 0.01f) recoilX = 0;
		if(Math.abs(recoilY) < 0.01f) recoilY = 0;
		if(Math.abs(recoilTheta) < 0.01f) recoilTheta = 0;
		
		lastUsed--;
		flashTimer--;
		
		if(currentRecoil > 0)	currentRecoil--;
		if(currentRecoil > maxRecoil) currentRecoil = maxRecoil;
	}
	
	public void draw(Graphics g)
	{
		g.rotate(pivotX, pivotY, theta);
		
//		if(flashTimer > 0 && owner.isMirrored())
//		{
//			muzzleFlash.draw(x + barrelX, y + barrelY, w * 0.8f, h);
//		} else
			
		if(flashTimer > 0)
		{
			float tempBarrelY = barrelY;
			if(owner.isMirrored()) tempBarrelY *= 0; //huh?? where does the offset even go
			
			muzzleFlash.draw(x + barrelX, y + tempBarrelY, w * 0.8f, h * 1.8f);
		}
		
		g.rotate(pivotX, pivotY, -theta);
		
		super.draw(g);
	}
	
	public void drawSprite(Image s)
	{
		s.draw(x + recoilX, y + recoilY, w, h);
	}
	
	public void rotateSprite(Graphics g, int side)
	{		
		float tempTheta = recoilTheta;
		if(owner.isMirrored()) tempTheta *= -1;
		
		g.rotate(pivotX + recoilX, pivotY + recoilY, (theta + tempTheta) * side);
	}
}
