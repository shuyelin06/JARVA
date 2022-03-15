package ui.sound;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Sound;

import engine.Settings;

import org.newdawn.slick.SlickException;

public class SoundManager {
	final private static HashMap<String, Sound> Sounds = new HashMap<String, Sound>();
	
	// Add a Sound
	public static void addSound(String s, Sound sound) { Sounds.put(s, sound); }
	
	// Get an Image
	public static Sound getSound(String s) { return Sounds.get(s); }
}