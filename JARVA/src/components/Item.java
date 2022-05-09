package components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Settings;
import objects.GameObject;
import objects.entities.Player;
import objects.entities.Unit;
import ui.display.animation.Animation;
import ui.input.InputManager;

public abstract class Item 
{
	protected Image sprite; //might wanna change this to something from imageManager later
	protected Unit owner;
	
	protected Animation animation;
	protected boolean animating;
	protected int animFrame;
	protected int animTick;
	
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
	protected boolean heldUse;
	
	public Item(Unit owner)
	{
		this.owner = owner;
		
		this.animFrame = 0;
		this.animating = false;
		this.animTick = 0;
		
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
		this.heldUse = false;
	}
	
	public boolean isWeapon() { return isWeapon; }
	public boolean isHeldUse() { return heldUse; }
	
	public float getHeight() { return h; }
	public Image getSprite() { return sprite; }
	
	abstract public void equip();
	abstract public void unequip();
	
	abstract public void use();
	
	public void update()
	{
		x = owner.getX();
		y = owner.getY();
		
		animTick++;
	}
	
	public void rotateTo(float x, float y)
	{
		if(!rotationLocked)
		{
			pivotX = this.x + (w * 0.2f);
			pivotY = this.y + (h * 0.5f);
			
			theta = (float) Math.toDegrees( Math.atan2( y - pivotY, x - pivotX ) ); //lol formatting
		}
	}
	
	public void draw(Graphics g)
	{
		Image tempSprite = setDrawSprite();
		if(owner.isMirrored()) tempSprite = tempSprite.getFlippedCopy(false, true);
		
		tempSprite.setFilter(Image.FILTER_NEAREST);
		
		if(rotationLocked)
		{
			drawSprite(tempSprite);
		}
		else
		{
			rotateSprite(g, 1);
			drawSprite(tempSprite);
			rotateSprite(g, -1);
		}
	}
	
	public Image setDrawSprite()
	{
		if(animation != null)
		{
			return animation.getFrame(animFrame);
		}
		
		return sprite;
	}
	
	public void drawSprite(Image s)
	{
		s.draw(x, y, w, h);
	}
	
	public void rotateSprite(Graphics g, int side)
	{
		g.rotate(pivotX, pivotY, theta * side);
	}
}
