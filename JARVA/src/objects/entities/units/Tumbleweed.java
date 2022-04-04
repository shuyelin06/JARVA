package objects.entities.units;

import engine.states.Game;
import objects.entities.Projectile;
import objects.entities.Unit;
import objects.entities.projectiles.Thorn;
import objects.geometry.Polygon;
import ui.display.images.ImageManager;

public class Tumbleweed extends Unit {
	
	private int timer;
	
	public Tumbleweed() {
		super(Polygon.rectangle(7f, 7f));
		
		this.sprite = ImageManager.getImageCopy("tumbleweed", 7, 7);
		this.sprite.setImageColor(0.5f, 0.5f, 0.5f);
		
		this.baseDamage = 5;
		
		timer = 0;
//		timer = (int) (1000 * Math.random());
		
		this.team = ObjectTeam.Enemy;
	}
	
	protected void unitUpdate() {
		timer++;
		if (timer % 200 == 0) {
			new Thorn(this, Game.Player)
				.setMaxTimer(200)
				.setPierce(1)
				.setKnockback(0)
				.setDamageMultiplier(1)
				.build();
		}
		
	}
}