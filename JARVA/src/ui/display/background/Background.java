package ui.display.background;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Background {
	private Color color;
	private ArrayList<BackgroundObject> objects;
	
	public Background() {
		objects = new ArrayList<>();
		color = new Color(205, 170, 109);

		initialize();
	}
	
	public void initialize() {
		for( int i = 0; i < 15; i++ ) {
			objects.add(
					new BackgroundObject("cactus")
					);
		}
		for( int i = 0; i < 15; i++ ) {
			objects.add(
					new BackgroundObject("shrub")
					);
		}
	}
	public void render(Graphics g) {
		// Set Background Color
		g.setBackground(color);
		
		// Render Background Objects
		for(BackgroundObject o: objects) {
			o.render(g);
		}
	}
}