package ui.sound;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Sound;

import engine.Settings;

import org.newdawn.slick.SlickException;

public class SoundManager {
	final private static HashMap<String, Sound> Sounds = new HashMap<String, Sound>();
	private static Sound backgroundMusic = null;
	
	// Add a Sound
	public static void addSound(String s, Sound sound) { Sounds.put(s, sound); }
	
	// Get an Image
	public static Sound getSound(String s) { return Sounds.get(s); }
	
	// Play Background Music
	public static void playBackgroundMusic(String s) {
		stopBackgroundMusic();
		
		try {
			backgroundMusic = Sounds.get(s);
			backgroundMusic.loop();
		} catch (Exception e) {
			backgroundMusic = null;
			System.out.println("Error playing background music");
		}
	}
	public static void stopBackgroundMusic() {
		if( backgroundMusic != null ) backgroundMusic.stop();
	}
	
	// Play Sound Effect
	public static void playSoundEffect(String s, float volume) {
		try {
			Sounds.get(s).play(1f, volume);
		} catch (Exception e) {
			System.out.println("Error playing sound effect");
		}
	}
}