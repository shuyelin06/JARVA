package weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import engine.Settings;
import objects.GameObject;
import ui.input.InputManager;

public class Weapon 
{
	protected Image sprite; //might wanna change this to something from imageManager later
	protected GameObject owner;
	protected boolean isEquipped;
	
	protected float x;
	protected float y;
	protected float pivotX;
	protected float pivotY;
	
	protected float w;
	protected float h;	
	protected float theta;
	
	public Weapon(GameObject owner)
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
	}
	
	public void update()
	{
		x = owner.getX();
		y = owner.getY();
		
		pivotX = x + (w * 0.2f);
		pivotY = y + (h * 0.5f);
		
		float mouseX = InputManager.getScaledMouseX();
		float mouseY = InputManager.getScaledMouseY();
		
		theta = (float) Math.toDegrees(Math.atan2(mouseY - pivotY, mouseX - pivotX));
	}
	
	public void draw(Graphics g)
	{
		g.rotate(pivotX, pivotY, theta);
		sprite.draw(x, y, w, h);
		//g.fillRect(x, y, w, h);
		g.rotate(pivotX, pivotY, -theta);
	}
}
