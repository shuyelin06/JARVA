package ui.display;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ui.display.images.ImageManager;
import ui.input.InputManager;

public class Crosshair 
{
	private Color color;
	private Image sprite;
	
	private float r;
	private float x;
	private float y;
	
	public Crosshair(Color color, float radius)
	{
		this.r = radius;
		
		try {
			sprite = new Image("res/images/crosshair.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		sprite = ImageManager.getImageCopy("crosshair", 50, 50);
		this.color = color;
		
		x = 0;
		y = 0;
	}
	
	//mutators
	public void setColor(Color c)		{	color = c;	}
	public void setRadius(float radius)	{	r = radius;	}
	
	public void update()
	{
		x = InputManager.getScaledMouseX();
		y = InputManager.getScaledMouseY();
	}
	
	public void draw(Graphics g)
	{
		sprite.setImageColor(color.r, color.g, color.b);
		sprite.draw(x - r * 0.5f, y - r * 0.5f, r, r);
	}
}
