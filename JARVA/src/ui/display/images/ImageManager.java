package ui.display.images;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class ImageManager {
	private static HashMap<String, Image> Images = new HashMap<String, Image>();
	
	public static void loadImages() {
		
	}
	public static void addImage(String s, Image im) { Images.put(s, im); }
	
	// Get an Image
	public static Image getImage(String s) { return Images.get(s).copy(); }
	public static Image getImage(String s, int width, int height) { 
		Image im = Images.get(s);
		return im.getScaledCopy(width, height);
	}
	
}