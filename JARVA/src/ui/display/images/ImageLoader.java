package ui.display.images;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ImageLoader {
	final private static HashMap<String, Image> Images = new HashMap<String, Image>();
	
	public static void loadImages(File directory) {
		for(final File file: directory.listFiles()) {
			if(file.isDirectory()) loadImages(directory);
			else {
				try {
					Image im = new Image(file.getPath()); 
					String[] split = file.getName().split("\\.");
					
					Images.put(split[0], im);
					System.out.println("Successfully Loaded Image: " + split[0]);
				} catch (SlickException e)
					{ System.out.println("Error Loading Image: " + file.getName()); }
			}
		}
	}
	
	// Get an Image
	public static Image getImage(String s) { return Images.get(s); }
	public static Image getImageCopy(String s) { return Images.get(s).copy(); }
	public static Image getImageCopy(String s, int width, int height) { 
		Image im = Images.get(s);
		return im.getScaledCopy(width, height);
	}
	
}