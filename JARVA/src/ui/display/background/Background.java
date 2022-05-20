package ui.display.background;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ui.display.background.objects.Casing;
import ui.display.images.ImageManager;

public class Background {
	public static final Color BackgroundColor = new Color(205, 170, 109);
	
	private Color color;
	
	private ArrayList<BackgroundObject> objects;
	private LinkedList<Casing> casings;
	
	public Background() {
		casings = new LinkedList<>();
		objects = new ArrayList<>();
		color = BackgroundColor;
		
		initialize();
	}
	
	public void addBulletCasing(Casing c) { casings.add(c); }
	
	public void initialize() {
		for( int i = 0; i < 8; i++ ) {
			objects.add(
					new BackgroundObject("streak")
					);
		}
		for( int i = 0; i < 36; i++ ) {
			objects.add(
					new BackgroundObject("shrub")
					);
		}
		for( int i = 0; i < 12; i++ ) {
			objects.add(
					new BackgroundObject("cactus")
					);
		}
		for( int i = 0; i < 12; i++ ) {
			objects.add(
					new BackgroundObject("boulder")
					);
		}
		objects.add(
				new BackgroundObject("skull")
				);
	}
	
	public void render(Graphics g) {
		// Set Background Color
		g.setBackground(color);
		
		// Render Background Objects
		for(BackgroundObject o: objects) {
			o.render(g);
		}
		
		while( casings.size() > 35 ) { casings.pop(); }
		for(Casing c: casings) {
			c.render(g);
		}
	}
}