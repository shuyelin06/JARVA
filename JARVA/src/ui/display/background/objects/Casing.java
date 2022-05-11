package ui.display.background.objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.states.Game;
import ui.display.background.BackgroundObject;

public class Casing {
	
	private float x;
	private float y;
	
	private Image sprite;
	
	public Casing(float x, float y, Image sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		
		sprite.setImageColor(0.85f, 0.85f, 0.85f);
	}
	
	public void render(Graphics g) { 
		sprite.drawCentered(x, y); 
	}
	
}