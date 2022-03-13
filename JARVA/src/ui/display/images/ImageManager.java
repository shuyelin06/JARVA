package ui.display.images;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class ImageManager {
	final private static String Image_Folder = "res/images/";
	private static HashMap<String, Image> Images = new HashMap<String, Image>();
	
	public static void main(String[] args) {
		
	}
	
	public static void loadImages(File directory) {
		for(final File file: directory.listFiles()) {
			if(file.isDirectory()) loadImages(directory);
			else {
//				Image im = new Image(file);
			}
		}
	}
	public static void addImage(String s, Image im) { Images.put(s, im); }
	
	// Get an Image
	public static Image getImage(String s) { return Images.get(s).copy(); }
	public static Image getImage(String s, int width, int height) { 
		Image im = Images.get(s);
		return im.getScaledCopy(width, height);
	}
	
}