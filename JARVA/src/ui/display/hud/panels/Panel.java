package ui.display.hud.panels;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import engine.Main;
import ui.display.background.Background;

public class Panel {
	
	private Color outline; // outline color
	private Color background; // background color
	
	protected int outlineWidth;
	
	protected int x;
	protected int y;
	
	protected int w;
	protected int h;
	
	public Panel() {
		this.outline = Color.white;
		this.background = Background.BackgroundColor;
		
		this.outlineWidth = 3;
		this.w = this.h = 1;
		this.x = this.y = 0;
	}
	
	public boolean click(float x, float y) { return isWithin(x, y); }
	protected void panelRender(Graphics g) {}
	
	public void render(Graphics g) {
		// Draw Panel Background
		if( background != null ) {
			g.setColor(background);
			g.fillRect(x - (w / 2), y - (h / 2), w, h);
		}
	
		
		// Draw Outline
		if( outline != null ) {
			g.setLineWidth(outlineWidth);
			g.setColor(outline);
			g.drawRect(x - (w / 2), y - (h / 2), w, h);
			g.resetLineWidth();
		}
		
		// Extra Rendering
		panelRender(g);
	}
	
	// Helper Methods
	public boolean isWithin(float x, float y) {
		if (x > this.x - w / 2 && 
			x < this.x + w / 2 &&
			y > this.y - h / 2 &&
			y < this.y + h / 2 ) return true;
		else return false;
	}
	
	// Mutator Methods
	public Panel setBackground(Color newColor) { this.background = newColor; return this; }
	public Panel setOutlineColor(Color newColor) { this.outline = newColor; return this; }
	
	public Panel setOutlineWidth(int newWidth) { this.outlineWidth = newWidth; return this; }
	
	public Panel setX(int newX) { this.x = newX; return this; }
	public Panel setY(int newY) { this.y = newY; return this; }
	
	public Panel setWidth(int newW) { this.w = newW; return this; }
	public Panel setHeight(int newH) { this.h = newH; return this; }
}