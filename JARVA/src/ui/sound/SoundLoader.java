package ui.sound;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Sound;

import engine.Settings;

import org.newdawn.slick.SlickException;

public class SoundLoader {
	final private static HashMap<String, Sound> Sounds = new HashMap<String, Sound>();
	
	public static void loadSounds(File directory) {
		for(final File file: directory.listFiles()) {
			if(file.isDirectory()) loadSounds(directory);
			else {
				try {
					Sound im = new Sound(file.getPath()); 
					String[] split = file.getName().split("\\.");
					
					Sounds.put(split[0], im);
					System.out.println("Successfully Loaded Sound: " + split[0]);
				} catch (SlickException e)
					{ System.out.println("Error Loading Sound: " + file.getName()); }
			}
		}
	}
	
	// Get an Image
	public static Sound getSound(String s) { return Sounds.get(s); }
}