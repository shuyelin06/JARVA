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
		
		recoilX += baseRecoil * (float) -Math.cos(angleToMouse);
		recoilY += baseRecoil * (float) -Math.sin(angleToMouse);
		
		recoilTheta -= baseRecoil * 20f;
	}
	
	public void update()
	{
		super.update();
		
		recoilX *= 0.9f;
		recoilY *= 0.9f;
		recoilTheta *= 0.95f;
		//cleaning up numbers
		if(Math.abs(recoilX) < 0.01f) recoilX = 0;
		if(Math.abs(recoilY) < 0.01f) recoilY = 0;
		if(Math.abs(recoilTheta) < 0.01f) recoilTheta = 0;
		
		lastUsed--;
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
