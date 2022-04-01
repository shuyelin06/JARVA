package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import objects.GameObject;
import objects.entities.projectiles.MediumBullet;
import ui.input.InputManager;

public class Item 
{
	protected Image sprite; //might wanna change this to something from imageManager later
	protected GameObject owner;
	
	protected boolean isEquipped;
	protected boolean rotationLocked;
	
	protected float x;
	protected float y;
	protected float pivotX;
	protected float pivotY;
	
	protected float tempX;
	protected float tempY;
	protected float tempTheta;
	
	protected float w;
	protected float h;	
	protected float theta;
	
	protected boolean isWeapon; //temporary cheese method to get the use method, sowwy
	
	public Item(GameObject owner)
	{
		this.owner = owner;
		
		this.x = owner.getX();
		this.y = owner.getY();
		this.w = 0;
		this.h = 0;
		this.pivotX = 0;
		this.pivotY = 0;
		
		this.theta = 0;
		
		this.isEquipped = true;
		this.rotationLocked = false;
		
		this.isWeapon = false;
	}
	
	public boolean isWeapon() { return isWeapon; }
	
	public void update()
	{
		x = owner.getX();
		y = owner.getY();
		
		if(!rotationLocked)
		{
			pivotX = x + (w * 0.2f);
			pivotY = y + (h * 0.5f);
			
			theta = (float) Math.toDegrees(
											Math.atan2(
														InputManager.getMapMouseY() - pivotY, InputManager.getMapMouseX() - pivotX
																																			)
																																					); //lol formatting
		}
	}
	
	public void draw(Graphics g)
	{
		Image tempSprite = sprite;
		
		if(owner.isMirrored()) tempSprite = sprite.getFlippedCopy(false, true);
		
		if(rotationLocked)
		{
			drawSprite(tempSprite);
		}
		else
		{
			g.rotate(pivotX, pivotY, theta);
			drawSprite(tempSprite);
			g.rotate(pivotX, pivotY, -theta);
		}
	}
	
	public void drawSprite(Image s)
	{
		s.draw(x, y, w, h);
	}
}
