package objects.entities;

import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Player extends Unit {

	public Player() {
		super(Polygon.rectangle(150f, 50f));
		
		this.sprite = ImageManager.getImageCopy("Placeholder", 150, 50);
	}
	
	public void unitUpdate() {};
}