package ui.display.background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import engine.Utility;
import ui.display.images.ImageManager;

public class BackgroundObject {
	protected static float X_Extent = 250f;
	protected static float Y_Extent = 150f;
	
	private SpriteSheet spritesheet;
	private Image sprite;
	private Image shadow;
	
	private float x;
	private float y;
	
	private float xScale;
	private float yScale;
	
	public BackgroundObject(String name) {
		switch(name)
		{
		case("cactus"): 
			this.spritesheet = new SpriteSheet(ImageManager.getImage("cactus"), 9, 18);
			this.sprite = spritesheet.getSubImage(0, (int)(Math.random() * 4));
			if(Math.random() > 0.5f) sprite = sprite.getFlippedCopy(true, false);
		default:
		}
		
		this.x = Utility.random() * (X_Extent - sprite.getWidth()) - 125; //- X_Extent / 2f;
		this.y = Utility.random() * Y_Extent - 125; // - Y_Extent / 2f;
		
		xScale = (float)Math.random() * .2f + 0.8f;
		yScale = (float)Math.random() * .5f + 0.8f;
		
		shadow = ImageManager.getImageCopy("shadow");
	}
	
	public void render(Graphics g) {
		float tempScale = sprite.getWidth() * xScale / shadow.getWidth();
		shadow.setFilter(sprite.FILTER_NEAREST);
		shadow.draw(x, y + sprite.getHeight() * yScale - shadow.getHeight() * 0.5f, tempScale);
		
		sprite.setFilter(sprite.FILTER_NEAREST);
		sprite.draw(x, y, sprite.getWidth() * xScale, sprite.getHeight() * yScale);
	}
}