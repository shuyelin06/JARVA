package objects.entities.units;

import objects.entities.Projectile;
import objects.entities.Unit;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Tumbleweed extends Unit {
	public Tumbleweed() {
		super(Polygon.rectangle(50f, 50f));
		
		this.sprite = ImageManager.getImageCopy("Placeholder", 50, 50);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
		this.team = ObjectTeam.Enemy;
	}
	
	protected void unitUpdate() {
		
	}
}