package components.weapons.guns;

import objects.GameObject;
import objects.entities.Unit;
import ui.display.images.ImageManager;
import ui.input.InputManager;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import components.weapons.Weapon;
import engine.Utility;

public class Gun extends Weapon
{	
	protected float baseRecoil;
	protected float currentRecoil;
	protected float maxRecoil;
	protected float recoilRecovery; //higher is slower
	
	protected float recoilX;
	protected float recoilY;
	protected float recoilTheta;
	
	protected float recoilThetaMult; //in case you don't want it spinning 1 million cirlces each time
	protected float recoilPosMult; 
	
	protected Image muzzleFlash;
	protected float flashTimer;	
	
	// Absolute Position of the Barrel
	// For Muzzle Flash and Bullets
	protected float barrelX; //for the muzzle flash, and maybe for the bullet
	protected float barrelY;
	
	// Relative Barrel Positions on the Image
	// For calculating BarrelX and Y
	protected float relativeBarrelX;
	protected float relativeBarrelY;
	
	public Gun(Unit owner) 
	{
		super(owner);
		
		lastUsed = 0;
		useTimer = 0;
		
		baseRecoil = 0;
		currentRecoil = 0;
		maxRecoil = 0;
		recoilRecovery = 1;
		recoilPosMult = 1;
		
		recoilX = 0;
		recoilY = 0;
		recoilThetaMult = 1;
		
		sprite = ImageManager.getImageCopy("revolver");
		muzzleFlash = ImageManager.getImageCopy("muzzleFlash");
		flashTimer = 0;
		
		barrelX = 0;
		barrelY = 0;
		
		relativeBarrelX = 0;
		relativeBarrelY = 0;
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
		lastUsed = useTimer;
		
		float angleToMouse = (float)Math.toRadians(owner.getAngleTo(InputManager.getMapMouseX(), InputManager.getMapMouseY()));
		
		recoilX += baseRecoil * (float) -Math.cos(angleToMouse) * recoilPosMult;
		recoilY += baseRecoil * (float) -Math.sin(angleToMouse) * recoilPosMult;
		
		recoilTheta -= baseRecoil * recoilThetaMult;
		currentRecoil += baseRecoil * recoilRecovery;
		
		flashTimer = 2;
	}
	
	public void update()
	{
		super.update();
		
		// Calculate Barrel Position
		float angle = Utility.ConvertToDegrees( Utility.atan(InputManager.getMapMouseY() - owner.getY(), InputManager.getMapMouseX() - owner.getX()) );
		while( angle < 0 ) { angle += 360; }
		
		float shift = (float) Math.PI / 2f;
		if(angle > 90 && angle < 270 ) shift = -shift;
		
		final float ConvertedTheta = Utility.ConvertToRadians(theta);
		barrelX = pivotX + relativeBarrelX * (float) Math.cos(ConvertedTheta) - relativeBarrelY * (float) Math.cos(ConvertedTheta + shift);
		barrelY = pivotY + relativeBarrelX * (float) Math.sin(ConvertedTheta) - relativeBarrelY * (float) Math.sin(ConvertedTheta + shift);
		
		// Dampen Recoil
		recoilX *= 0.95f;
		recoilY *= 0.95f;
		recoilTheta *= 0.9f;
		
		// Cleaning up numbers
		if(Math.abs(recoilX) < 0.1f) recoilX = 0;
		if(Math.abs(recoilY) < 0.1f) recoilY = 0;
		if(Math.abs(recoilTheta) < 0.1f) recoilTheta = 0;
		
		lastUsed--;
		flashTimer--;
		
		if(currentRecoil > 0)	currentRecoil--;
		if(currentRecoil > maxRecoil) currentRecoil = maxRecoil;
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
		
		g.rotate(barrelX, barrelY, theta);
		
		if(flashTimer > 0)
		{
			muzzleFlash.draw(barrelX, barrelY - h * 0.9f, w * 0.8f, h * 1.8f);
		}
		
		g.rotate(barrelX, barrelY, -theta);
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
