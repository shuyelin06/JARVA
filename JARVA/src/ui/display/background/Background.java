package ui.display.background;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Background {
	Color color;
	
	ArrayList<BackgroundObject> objects;
	
	public void render(Graphics g) {
		for(BackgroundObject o: objects) {
			o.render(g);
		}
	}
	
	
}