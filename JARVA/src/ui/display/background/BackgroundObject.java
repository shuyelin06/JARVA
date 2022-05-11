package ui.display.background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import engine.Utility;
import ui.display.images.ImageManager;

public class BackgroundObject {
	protected static float X_Extent = 500f;
	protected static float Y_Extent = 300f;
	
	protected SpriteSheet spritesheet;
	protected Image sprite;
	protected Image shadow;
	
	protected float x;
	protected float y;
	
	protected float xScale;
	protected float yScale;
	
	public BackgroundObject setSprite( Image sprite ) {this.sprite = sprite; return this; }
	public BackgroundObject setX( float newX) {this.x = newX; return this; }
	public BackgroundObject setY( float newY) {this.y = newY; return this; }
	
	public BackgroundObject(String name) {
		shadow = ImageManager.getImageCopy("shadow");
		
		switch(name)
		{		
		case("shrub"): 
			
			this.spritesheet = new SpriteSheet(ImageManager.getImage("shrub"), 16, 16);
			this.sprite = spritesheet.getSubImage(0, (int)(Math.random() * 6));
			if(Math.random() > 0.5f) sprite = sprite.getFlippedCopy(true, false);
			
			xScale = (float)Math.random() * 0.3f + 0.3f;
			yScale = xScale;
			shadow = shadow.getScaledCopy(sprite.getWidth() * xScale / shadow.getWidth() * 0.8f);
			shadow.setAlpha(0.7f);	
			
			this.x = Utility.random() * (X_Extent - sprite.getWidth()) - X_Extent / 2f;
			this.y = Utility.random() * Y_Extent - 300; // - Y_Extent / 2f;
			
			break;
			
		case("cactus"): 
			
			this.spritesheet = new SpriteSheet(ImageManager.getImage("cactus"), 9, 18);
			this.sprite = spritesheet.getSubImage(0, (int)(Math.random() * 4));
			if(Math.random() > 0.5f) sprite = sprite.getFlippedCopy(true, false);
			
			xScale = (float)Math.random() * .2f + 0.8f;
			yScale = (float)Math.random() * .5f + 0.8f;
			shadow = shadow.getScaledCopy(sprite.getWidth() * xScale / shadow.getWidth());
			
			this.x = Utility.random() * (X_Extent - sprite.getWidth()) - X_Extent / 2f;
			this.y = Utility.random() * Y_Extent - Y_Extent / 2f;
			
			break;
		case("boulder"): 
			
			this.spritesheet = new SpriteSheet(ImageManager.getImage("boulder"), 16, 16);
			this.sprite = spritesheet.getSubImage(0, (int)(Math.random() * 3));
			if(Math.random() > 0.5f) sprite = sprite.getFlippedCopy(true, false);
			
			xScale = (float)Math.random() * .2f + 0.5f;
			yScale = xScale;
			shadow = shadow.getScaledCopy(sprite.getWidth() * xScale / shadow.getWidth());
			
			this.x = Utility.random() * (X_Extent - sprite.getWidth()) - X_Extent / 2f;
			this.y = Utility.random() * Y_Extent - Y_Extent / 2f;
			
			break;
		case("streak"): 
			
			this.spritesheet = new SpriteSheet(ImageManager.getImage("streak"), 48, 16);
			this.sprite = spritesheet.getSubImage(0, (int)(Math.random() * 5));
			if(Math.random() > 0.5f) sprite = sprite.getFlippedCopy(true, false);
			
			xScale = (float)Math.random() * .8f + 0.8f;
			yScale = xScale;
			shadow = shadow.getScaledCopy(sprite.getWidth() * xScale / shadow.getWidth());
			shadow.setAlpha(0f);
			
			this.x = Utility.random() * (X_Extent - sprite.getWidth()) - X_Extent / 2f;
			this.y = Utility.random() * Y_Extent - Y_Extent / 2f;
			
			break;
		case("skull"): 
			
			this.sprite = ImageManager.getImageCopy("skull");
			if(Math.random() > 0.5f) sprite = sprite.getFlippedCopy(true, false);
			
			xScale = 0.7f;
			yScale = 0.7f;
			shadow = shadow.getScaledCopy(sprite.getWidth() * xScale / shadow.getWidth());
			shadow.setAlpha(0f);	
			
			this.x = Utility.random() * (250 - sprite.getWidth()) - 125;
			this.y = Utility.random() * 250 - 125;
			
			break;
		default:
			this.sprite = ImageManager.getPlaceholder();
			break;
		}
		
		this.x = Utility.random() * (250 - sprite.getWidth()) - 125;
		this.y = Utility.random() * 250 - 125;
	}
	
	public void render(Graphics g) {
		if(shadow.getAlpha() != 0)
		{
			shadow.setFilter(Image.FILTER_NEAREST);
			shadow.drawCentered(x + sprite.getWidth() * xScale * 0.5f, y + sprite.getHeight() * yScale - shadow.getHeight() * 0.2f);
		}
		
		sprite.setFilter(Image.FILTER_NEAREST);
		sprite.draw(x, y, sprite.getWidth() * xScale, sprite.getHeight() * yScale);
	}
}