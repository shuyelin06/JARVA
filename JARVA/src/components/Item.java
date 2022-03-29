package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import objects.GameObject;
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
	
	protected float w;
	protected float h;	
	protected float theta;
	
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
	}
	
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
			tempSprite.draw(x, y, w, h);
		}
		else
		{
			g.rotate(pivotX, pivotY, theta);
			tempSprite.draw(x, y, w, h);
			g.rotate(pivotX, pivotY, -theta);
		}
	}
}
