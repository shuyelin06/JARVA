package ui.display.animation;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import engine.states.Game;
import ui.display.images.ImageManager;

public class Animation {
	private SpriteSheet spritesheet;
	private int frame;
	
	public Animation(String str, int tw, int th) {
		this.frame = 0;
		this.spritesheet = new SpriteSheet(ImageManager.getImage(str), tw, th);
	}
	
	// Called by the GameObject for Drawing
	public Image getFrame() {
		return spritesheet.getSubImage(Game.getTicks(), 0);
	}
	
}