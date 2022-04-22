package objects.entities.units;

import engine.states.Game;
import objects.GameObject;
import objects.GameObject.ObjectTeam;
import objects.entities.Unit;
import objects.entities.projectiles.Banana;
import objects.entities.projectiles.Thorn;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Monkey extends Unit {
	private boolean active;
	private int timer;
	
	public Monkey() {
		super(Polygon.rectangle(7f, 7f));
		
		this.sprite = ImageManager.getImageCopy("placeholder", 7, 7);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
		this.baseDamage = 5;
		
		timer = 0;
		active = false;
		this.team = ObjectTeam.Enemy;
	}
	
	@Override
	public void objectCollision(GameObject o) {
		if (o instanceof BananaTree) {
			active = true;
		}
		super.objectCollision(o);
	}
	
	protected void unitUpdate() {
		moveTo(nearestBananaTree());
		if (active) {
			timer++;
		}
		if (timer % 200 == 0) {
			new Banana(this, Game.Player)
				.setPierce(3)
				.setKnockback(0)
				.setDamageMultiplier(1)
				.build();
		}
		
	}
	
	private BananaTree nearestBananaTree() {
		BananaTree nearest = null;
		for (BananaTree b: BananaTree.bananaTrees) {
			if (nearest == null) {
				nearest = b;
			}
			if (getDistance(b) < getDistance(nearest)) {
				nearest = b;
			}
		}
		return nearest;
	}
}
