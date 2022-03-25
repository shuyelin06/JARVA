package ui.display.images;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ImageManager {
	public static boolean Complete = false;
	final private static HashMap<String, Image> Images = new HashMap<String, Image>();

	// Add Image
	public static void addImage(String s, Image im) { Images.put(s, im); }
	
	// Get an Image
	public static Image getPlaceholder() { return Images.get("placeholder");  }
	
	public static Image getImage(String s) { return Images.get(s); }
	
	public static Image getImageCopy(String s) { return Images.get(s).copy(); }
	public static Image getImageCopy(String s, int width, int height) { 
		Image im = Images.get(s);
		if( im != null ) {
			return im.getScaledCopy(width, height);
		} else {
			return getPlaceholder().getScaledCopy(width, height);
		}
	}
	
}