package components.weapons.guns;

import objects.GameObject;
import ui.input.InputManager;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import components.weapons.Weapon;

public class Gun extends Weapon
{	
	protected float baseRecoil;
	
	protected float recoilX;
	protected float recoilY;
	protected float recoilTheta;
	
	public Gun(GameObject owner) 
	{
		super(owner);
		
		lastUsed = 0;
		useTimer = 0;
		
		baseRecoil = 0;
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
		
		recoilX += (float) -Math.cos(angleToMouse);
		recoilY += (float) -Math.sin(angleToMouse);
	}
	
	public void update()
	{
		super.update();
		
		recoilX *= 0.8f;
		recoilY *= 0.8f;
		recoilTheta *= 0.8f;
		
		lastUsed--;
	}
}
