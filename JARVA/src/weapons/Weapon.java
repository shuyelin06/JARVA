package weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import objects.GameObject;

public class Weapon 
{
	private Input tempInput; //too tired to manage the abstraction
	
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
		
		tempInput = new Input(0);
	}
	
	public void update()
	{
		x = owner.getX();
		y = owner.getY();
		
		pivotX = x + (w * 0.5f);
		pivotY = y + (h * 0.5f);
		
		float mouseX = tempInput.getMouseX();
		float mouseY = 1080 + tempInput.getMouseY();
		
		System.out.println("x: " + (mouseX - pivotX));
		System.out.println(mouseX);
		System.out.println("y: " + (mouseY - pivotY));
		System.out.println(mouseY);
		System.out.println("------------");
		
		theta = (float) Math.toDegrees(Math.atan2(mouseY - pivotY, mouseX - pivotX));
	}
	
	public void draw(Graphics g)
	{
		g.rotate(pivotX, pivotY, theta);
		g.fillRect(x, y, w, h);
		g.rotate(pivotX, pivotY, -theta);
	}
}
