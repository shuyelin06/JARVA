package ui.input;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import ui.display.images.ImageManager;

public class Button {
	
	//Center X and Y
	private float centerX, centerY;
	private float w, h;
	private Image image;
	private boolean outline;
	
	// Constructor
	public Button() {
		// Default Variables
		this.centerX = this.centerY = 0f;
		this.w = this.h = 0f;
		this.image = ImageManager.getPlaceholder();
		this.outline = false;
	}
	
	// Mutator Methods
	public Button setCenterX(float newX) { this.centerX = newX; return this; }
	public Button setCenterY(float newY) { this.centerY = newY; return this; }
	public Button setW(float newW) { this.w = newW; return this; }
	public Button setH(float newH) { this.h = newH; return this; }
	public Button setImage(String filepath) { this.image = ImageManager.getImage(filepath); return this; }
	public Button enableOutline() { outline = true; return this; }
	public Button disableOutline() { outline = false; return this; }
	
	// Render Image
	public void render(Graphics g) {
		if (this.image != null) {
			image.draw(centerX - (w / 2), centerY - (h / 2), w, h);
			
		}
		if (outline) {
			g.setColor(Color.white);
			g.drawRect(centerX - (w / 2), centerY - (h / 2), w, h);
		}
	}
	
	// Returns Within Coordinates
	public boolean isWithin(float x, float y) {
		if ((x > centerX - (w / 2))
				&& (x < centerX + (w / 2))
				&& (y > centerY - (h / 2))
				&& (y < centerY + (h / 2))
			) {
			return true;
		}
		return false;
	}
	
	public boolean isWithin(Point p) {
		if (isWithin(p.getCenterX(), p.getCenterY())) { return true; }
		return false;
	}
	
}
