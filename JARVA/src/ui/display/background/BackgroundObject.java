package ui.display.background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class BackgroundObject {
	private Image sprite;
	
	private float x;
	private float y;
	
	public BackgroundObject(Image sprite, float x, float y) {
		this.sprite = sprite;
		
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics g) {
		sprite.drawCentered(x, y);
	}
}