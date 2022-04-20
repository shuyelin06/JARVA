package ui.display.background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.Utility;
import ui.display.images.ImageManager;

public class BackgroundObject {
	protected static float X_Extent = 200f;
	protected static float Y_Extent = 100f;
	
	private Image sprite;
	
	private float x;
	private float y;
	
	public BackgroundObject(String name) {
		this.sprite = ImageManager.getImage(name);
		
		this.x = Utility.random() * X_Extent - X_Extent / 2f;
		this.y = Utility.random() * Y_Extent - Y_Extent / 2f;
	}
	
	public void render(Graphics g) {
		sprite.drawCentered(x, y);
	}
}